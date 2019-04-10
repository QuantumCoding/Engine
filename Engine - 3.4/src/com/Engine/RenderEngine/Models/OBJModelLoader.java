package com.Engine.RenderEngine.Models;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Engine.RenderEngine.Models.OBJModelLoader.Vertex._Vertex;
import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Deferred.High.DeferredShader;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class OBJModelLoader {
	private static final Pattern COMMENT_PATTERN = Pattern.compile("#.*");
	
	private static final String NUM_PAT = "(-?[0-9]\\d*(?:\\.\\d+)?)";
	private static final Pattern POSITION_PATTERN = Pattern.compile("v " + NUM_PAT + " " + NUM_PAT + " " + NUM_PAT);
	private static final Pattern TEX_COORD_PATTERN = Pattern.compile("vt " + NUM_PAT + " " + NUM_PAT);
	private static final Pattern NORMAL_PATTERN = Pattern.compile("vn " + NUM_PAT + " " + NUM_PAT + " " + NUM_PAT);
	
	private static final String VERT_PAT = "(\\d*\\/\\d*\\/\\d*)";
	private static final Pattern FACE_PATTERN = Pattern.compile("f " + VERT_PAT + " " + VERT_PAT + " " + VERT_PAT);
	private static final Pattern VERTEX_PATTERN = Pattern.compile("(\\d*)\\/(\\d*)\\/(\\d*)");
	
	public static class OBJLoaderSettings {
		public boolean useIndices = true;

		public Attribute positionsAttribute = Shader.ATTRIBUTE_LOC_POSITIONS;
		public Attribute texCoordsAttribute = Shader.ATTRIBUTE_LOC_TEXCOORDS;
		public Attribute normalsAttribute 	 = Shader.ATTRIBUTE_LOC_NORMALS;

		public Attribute biTangentsAttribute = null; // TODO: Change location of Tangent Attribute
		public Attribute tangentsAttribute = DeferredShader.ATTRIBUTE_LOC_TANGENT; 
	}
	
	public static ModelData load(String resourcePath, OBJLoaderSettings settings) {
		if(!resourcePath.toLowerCase().endsWith(".obj"))
			throw new IllegalArgumentException("Provided Resource is not a '.obj' File, Resource: " + resourcePath);
		
		if(settings == null) settings = new OBJLoaderSettings();
		
//	================================================ Extract All Data ================================================ \\
		
		String file;
		
		try {
			InputStream in = OBJModelLoader.class.getResourceAsStream(resourcePath);
			StringBuilder fullFile = new StringBuilder(in.available());
			byte[] buffer = new byte[1024];
			
			int read = 0;
			while((read = in.read(buffer)) != -1) {
				fullFile.append(new String(buffer, 0, read));
			}
			
			file = fullFile.toString().trim().toLowerCase();
		} catch(IOException e) {
			throw new IllegalArgumentException("Failed to load resource as OBJ, Resource: " + resourcePath, e);
		}
		
		// Remove Comments
		file = COMMENT_PATTERN.matcher(file).replaceAll("");
		
		// Positions
		ArrayList<Vector3f> positions = new ArrayList<>(); {
			Matcher matcher = POSITION_PATTERN.matcher(file);
			
			while(matcher.find()) {
				positions.add(new Vector3f(
						Double.parseDouble(matcher.group(1)),
						Double.parseDouble(matcher.group(2)),
						Double.parseDouble(matcher.group(3))
					));
			}
			
			matcher.reset().replaceAll(""); // Shorter String for the rest of the Pattern Matchers
		}
		
		// Texture Coords.
		ArrayList<Vector2f> texCoords = new ArrayList<>(); {
			Matcher matcher = TEX_COORD_PATTERN.matcher(file);
			
			while(matcher.find()) {
				texCoords.add(new Vector2f(
						Double.parseDouble(matcher.group(1)),
						1 - Double.parseDouble(matcher.group(2))
					));
			}
			
			matcher.reset().replaceAll(""); // Shorter String for the rest of the Pattern Matchers
		}
		
		// Normals
		ArrayList<Vector3f> normals = new ArrayList<>(); {
			Matcher matcher = NORMAL_PATTERN.matcher(file);
			
			while(matcher.find()) {
				normals.add(new Vector3f(
						Double.parseDouble(matcher.group(1)),
						Double.parseDouble(matcher.group(2)),
						Double.parseDouble(matcher.group(3))
					));
			}
			
			matcher.reset().replaceAll(""); // Shorter String for the rest of the Pattern Matchers
		}
		
		// Faces 
		ArrayList<Face> faces = new ArrayList<>(); {
			Matcher matcher = FACE_PATTERN.matcher(file);
			
			while(matcher.find()) {
				faces.add(new Face(
						Vertex.fromPattern(matcher.group(1)),
						Vertex.fromPattern(matcher.group(2)),
						Vertex.fromPattern(matcher.group(3))
					));
			}
			
			matcher.reset().replaceAll(""); // Shorter String for the rest of the Pattern Matchers
		}
		
//	=============================================== Calculate Tangents =============================================== \\
		
		ArrayList<Vector3f> tangents = new ArrayList<>(normals.size());
		ArrayList<Vector3f> bitangents = new ArrayList<>(normals.size());
		for(int i = 0; i < normals.size(); i ++) { tangents.add(null); bitangents.add(null); }
		
		for(Face face : faces) {
			Vector3f tangent = calculateTangent(
					positions.get(face.v0.positionIndex), texCoords.get(face.v0.texCoordIndex), 
					positions.get(face.v1.positionIndex), texCoords.get(face.v1.texCoordIndex),
					positions.get(face.v2.positionIndex), texCoords.get(face.v2.texCoordIndex)
				).normalize();
			
			tangents.set(face.v0.normalIndex, tangent);
			tangents.set(face.v1.normalIndex, tangent);
			tangents.set(face.v2.normalIndex, tangent);
			
			bitangents.set(face.v0.normalIndex, calculateBitangent(normals.get(face.v0.normalIndex), tangent));
			bitangents.set(face.v1.normalIndex, calculateBitangent(normals.get(face.v1.normalIndex), tangent));
			bitangents.set(face.v2.normalIndex, calculateBitangent(normals.get(face.v2.normalIndex), tangent));
		}
		
//	=========================================== Smooth Normals / Tangents  =========================================== \\
		

//	============================================= Data Manipulation Done ============================================= \\
		
		ModelData modelData = new ModelData();
		float[] _positions;
		float[] _texCoords;
		float[] _normals;

		float[] _tangents;
		float[] _bitangents;
		
		if(settings.useIndices) {
			faces.forEach(f -> {
				f.v0 = f.v0.setSources(positions, texCoords, normals, tangents, bitangents);
				f.v1 = f.v1.setSources(positions, texCoords, normals, tangents, bitangents);
				f.v2 = f.v2.setSources(positions, texCoords, normals, tangents, bitangents);
			});
			
			Set<_Vertex> vertices = faces.stream()
					.flatMap(face -> Arrays.asList(face.v0, face.v1, face.v2).stream())
					.map(v -> (_Vertex) v)
				.collect(
					HashMap<_Vertex, Integer>::new, 
					(m, v) ->  v.index = m.computeIfAbsent(v, _v -> m.size()),
					HashMap::putAll
			).keySet();
			
			int[] indices = new int[faces.size() * 3];
			for(int i = 0, size = faces.size(); i < size; i ++) {
				Face face = faces.get(i);
				
				indices[i * 3 + 0] = face.v0.index;
				indices[i * 3 + 1] = face.v1.index;
				indices[i * 3 + 2] = face.v2.index;
			}

			modelData.loadIndices(indices);
			
			ArrayList<_Vertex> sortedVerts = new ArrayList<>(vertices);
			sortedVerts.sort((a, b) -> Integer.compare(a.index, b.index));
			
//			AtomicInteger k = new AtomicInteger(-1);
//			sortedVerts.stream().sorted((a, b) -> {
//				int r;
//				if((r = Float.compare(a.position.x, b.position.x)) != 0) return r;
//				if((r = Float.compare(a.position.y, b.position.y)) != 0) return r;
//				if((r = Float.compare(a.position.z, b.position.z)) != 0) return r;
//
//				if((r = Float.compare(a.texCoord.x, b.texCoord.x)) != 0) return r;
//				if((r = Float.compare(a.texCoord.y, b.texCoord.y)) != 0) return r;
//
//				if((r = Float.compare(a.normal.x, b.normal.x)) != 0) return r;
//				if((r = Float.compare(a.normal.y, b.normal.y)) != 0) return r;
//				if((r = Float.compare(a.normal.z, b.normal.z)) != 0) return r;
//
//				if((r = Float.compare(a.tangents.x, b.tangents.x)) != 0) return r;
//				if((r = Float.compare(a.tangents.y, b.tangents.y)) != 0) return r;
//				if((r = Float.compare(a.tangents.z, b.tangents.z)) != 0) return r;
//
//				if((r = Float.compare(a.bitangents.x, b.bitangents.x)) != 0) return r;
//				if((r = Float.compare(a.bitangents.y, b.bitangents.y)) != 0) return r;
//				if((r = Float.compare(a.bitangents.z, b.bitangents.z)) != 0) return r;
//				
//				return Integer.compare(a.index, b.index);
//			}).forEach(v -> {
//				if(k.incrementAndGet() % 3 == 0) System.out.println();
//				System.out.printf("%2d\t%18s\t%12s\t%18s\t%32s\t%132s\t%n", v.index, v.position.toString(), v.texCoord.toString(), v.normal.toString(), v.tangents.toString(), v.bitangents.toString());
//			});
			
			_positions = new float[sortedVerts.size() * 3];
			_texCoords = new float[sortedVerts.size() * 2];
			_normals   = new float[sortedVerts.size() * 3];
			
			_tangents  = new float[sortedVerts.size() * 3];
			_bitangents= new float[sortedVerts.size() * 3];
			
			for(int i = 0; i < sortedVerts.size(); i ++) {
				_Vertex vertex = sortedVerts.get(i);
				
				_positions[i * 3 + 0] = vertex.position.x;
				_positions[i * 3 + 1] = vertex.position.y;
				_positions[i * 3 + 2] = vertex.position.z;
				
				_texCoords[i * 2 + 0] = vertex.texCoord.x;
				_texCoords[i * 2 + 1] = vertex.texCoord.y;

				_normals[i * 3 + 0] = vertex.normal.x;
				_normals[i * 3 + 1] = vertex.normal.y;
				_normals[i * 3 + 2] = vertex.normal.z;
				
				_tangents[i * 3 + 0] = vertex.normal.x;
				_tangents[i * 3 + 1] = vertex.normal.y;
				_tangents[i * 3 + 2] = vertex.normal.z;
				
				_bitangents[i * 3 + 0] = vertex.normal.x;
				_bitangents[i * 3 + 1] = vertex.normal.y;
				_bitangents[i * 3 + 2] = vertex.normal.z;
			}
			
		} else {
			_positions = new float[faces.size() * 3 * 3];
			_texCoords = new float[faces.size() * 3 * 2];
			_normals   = new float[faces.size() * 3 * 3];

			_tangents  = new float[faces.size() * 3 * 3];
			_bitangents= new float[faces.size() * 3 * 3];
			
			for(int i = 0, size = faces.size(); i < size; i ++) {
				Face face = faces.get(i);
				
				Vector3f p0 = positions.get(face.v0.positionIndex);
				Vector3f p1 = positions.get(face.v1.positionIndex);
				Vector3f p2 = positions.get(face.v2.positionIndex);
				_positions[i * 9 + 0] = p0.x; _positions[i * 9 + 1] = p0.y; _positions[i * 9 + 2] = p0.z;
				_positions[i * 9 + 3] = p1.x; _positions[i * 9 + 4] = p1.y; _positions[i * 9 + 5] = p1.z;
				_positions[i * 9 + 6] = p2.x; _positions[i * 9 + 7] = p2.y; _positions[i * 9 + 8] = p2.z;
				
				Vector2f t0 = texCoords.get(face.v0.texCoordIndex);
				Vector2f t1 = texCoords.get(face.v1.texCoordIndex);
				Vector2f t2 = texCoords.get(face.v2.texCoordIndex);
				_texCoords[i * 6 + 0] = t0.x; _texCoords[i * 6 + 1] = t0.y;
				_texCoords[i * 6 + 2] = t1.x; _texCoords[i * 6 + 3] = t1.y;
				_texCoords[i * 6 + 4] = t2.x; _texCoords[i * 6 + 5] = t2.y;
				
				Vector3f n0 = normals.get(face.v0.normalIndex);
				Vector3f n1 = normals.get(face.v1.normalIndex);
				Vector3f n2 = normals.get(face.v2.normalIndex);
				_normals[i * 9 + 0] = n0.x; _normals[i * 9 + 1] = n0.y; _normals[i * 9 + 2] = n0.z;
				_normals[i * 9 + 3] = n1.x; _normals[i * 9 + 4] = n1.y; _normals[i * 9 + 5] = n1.z;
				_normals[i * 9 + 6] = n2.x; _normals[i * 9 + 7] = n2.y; _normals[i * 9 + 8] = n2.z;
				
				Vector3f tn0 = tangents.get(face.v0.normalIndex);
				Vector3f tn1 = tangents.get(face.v1.normalIndex);
				Vector3f tn2 = tangents.get(face.v2.normalIndex);
				_tangents[i * 9 + 0] = tn0.x; _tangents[i * 9 + 1] = tn0.y; _tangents[i * 9 + 2] = tn0.z;
				_tangents[i * 9 + 3] = tn1.x; _tangents[i * 9 + 4] = tn1.y; _tangents[i * 9 + 5] = tn1.z;
				_tangents[i * 9 + 6] = tn2.x; _tangents[i * 9 + 7] = tn2.y; _tangents[i * 9 + 8] = tn2.z;
				
				Vector3f bt0 = bitangents.get(face.v0.normalIndex);
				Vector3f bt1 = bitangents.get(face.v1.normalIndex);
				Vector3f bt2 = bitangents.get(face.v2.normalIndex);
				_bitangents[i * 9 + 0] = bt0.x; _bitangents[i * 9 + 1] = bt0.y; _bitangents[i * 9 + 2] = bt0.z;
				_bitangents[i * 9 + 3] = bt1.x; _bitangents[i * 9 + 4] = bt1.y; _bitangents[i * 9 + 5] = bt1.z;
				_bitangents[i * 9 + 6] = bt2.x; _bitangents[i * 9 + 7] = bt2.y; _bitangents[i * 9 + 8] = bt2.z;
			}
		}
		
		if(settings.positionsAttribute != null) 
			modelData.storeDataInAttributeList(settings.positionsAttribute, 3, _positions, BufferUsage.Static_Draw);

		if(settings.texCoordsAttribute != null) 
			modelData.storeDataInAttributeList(settings.texCoordsAttribute, 2, _texCoords, BufferUsage.Static_Draw);
		
		if(settings.normalsAttribute != null) 
			modelData.storeDataInAttributeList(settings.normalsAttribute,   3,   _normals, BufferUsage.Static_Draw);
		
		if(settings.tangentsAttribute != null) 
			modelData.storeDataInAttributeList(settings.tangentsAttribute,  3,  _tangents, BufferUsage.Static_Draw);
		
		if(settings.biTangentsAttribute != null) 
			modelData.storeDataInAttributeList(settings.biTangentsAttribute,3,_bitangents, BufferUsage.Static_Draw);
		
		return modelData;
	}
	
	private static Vector3f calculateTangent(Vector3f p0, Vector2f t0, Vector3f p1, Vector2f t1, Vector3f p2, Vector2f t2) {
		Vector3f deltaPos1 = p1.subtract(p0);
		Vector3f deltaPos2 = p2.subtract(p0);

		Vector2f deltaUV1 = t1.subtract(t0);
		Vector2f deltaUV2 = t2.subtract(t1);
		
		float r = 1 / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
		return deltaPos1.multiply(deltaUV2.y).subtract(deltaPos2.multiply(deltaUV1.y)).multiply(r);
	}
	
	private static Vector3f calculateBitangent(Vector3f normal, Vector3f tangent) {
		return normal.crossProduct(tangent).normalize();
	}
	
	private static class Face {
		private Vertex v0, v1, v2;

		public Face(Vertex v0, Vertex v1, Vertex v2) {
			this.v0 = v0;
			this.v1 = v1;
			this.v2 = v2;
		}
		
//		private void addAllVertices(HashMap<Integer, HashSet<Vertex>> vertByPosition) {
//			HashSet<Vertex> verts = vertByPosition.computeIfAbsent(v0.positionIndex, __ -> new HashSet<>());
//			verts.add(v0);
//			
//			verts = vertByPosition.computeIfAbsent(v1.positionIndex, __ -> new HashSet<>());
//			verts.add(v1);
//			
//			verts = vertByPosition.computeIfAbsent(v2.positionIndex, __ -> new HashSet<>());
//			verts.add(v2);
//		}
	}
	
	static class Vertex {
		private int positionIndex;
		private int texCoordIndex;
		private int normalIndex;
			
		int index;
		
		private Vertex() { }
		public Vertex(int positionIndex, int texCoordIndex, int normalIndex) {
			this.positionIndex = positionIndex;
			this.texCoordIndex = texCoordIndex;
			this.normalIndex = normalIndex;
		}
		
		public static Vertex fromPattern(String src) {
			Matcher matcher = VERTEX_PATTERN.matcher(src);
			String n = "";
			
			if(matcher.find()) {
				return new Vertex(
						Integer.parseInt((n = matcher.group(1)).isEmpty() ? "0" : n) - 1,
						Integer.parseInt((n = matcher.group(2)).isEmpty() ? "0" : n) - 1,
						Integer.parseInt((n = matcher.group(3)).isEmpty() ? "0" : n) - 1
					);
			}
			
			throw new IllegalArgumentException("'" + src + "' is not a valid vertext Pattern");
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + index;
			result = prime * result + normalIndex;
			result = prime * result + positionIndex;
			result = prime * result + texCoordIndex;
			
			return result;
		}

		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null) return false;
			if(!(obj instanceof Vertex))
				return false;
			
			Vertex other = (Vertex) obj;
			
			if(index != other.index) 				 return false;
			if(normalIndex != other.normalIndex) 	 return false;
			if(positionIndex != other.positionIndex) return false;
			if(texCoordIndex != other.texCoordIndex) return false;
			
			return true;
		}
		
		public _Vertex setSources(
				ArrayList<Vector3f> positions, 
				ArrayList<Vector2f> texCoords, 
				ArrayList<Vector3f> normals,
				ArrayList<Vector3f> tangents,
				ArrayList<Vector3f> bitangents
		) {
			return new _Vertex(
					positionIndex < 0 ? new Vector3f() : positions.get(positionIndex), 
					texCoordIndex < 0 ? new Vector2f() : texCoords.get(texCoordIndex), 
					normalIndex < 0 ? new Vector3f() : normals.get(normalIndex),

					normalIndex < 0 ? new Vector3f() : tangents.get(normalIndex),
					normalIndex < 0 ? new Vector3f() : bitangents.get(normalIndex)
				);
		}
		
		public static class _Vertex extends Vertex {
			private Vector3f position;
			private Vector2f texCoord;
			private Vector3f normal;

			private Vector3f tangents;
			private Vector3f bitangents;
			
			public _Vertex(Vector3f position, Vector2f texCoord, Vector3f normal, Vector3f tangent, Vector3f bitangents) {
				this.position = position;
				this.texCoord = texCoord;
				this.normal = normal;
			
				this.tangents = tangent;
				this.bitangents = bitangents;
			}

			public int hashCode() {
				final int prime = 31;
				int result = 1;

				result = prime * result + ((tangents == null) ? 0 : tangents.hashCode());
				result = prime * result + ((bitangents == null) ? 0 : bitangents.hashCode());
				
				result = prime * result + ((normal == null) ? 0 : normal.hashCode());
				result = prime * result + ((position == null) ? 0 : position.hashCode());
				result = prime * result + ((texCoord == null) ? 0 : texCoord.hashCode());
				
				return result;
			}

			public boolean equals(Object obj) {
				if(this == obj) return true;
				if(obj == null) return false;
				if(!(obj instanceof _Vertex)) return false;
				
				_Vertex other = (_Vertex) obj;
				
				if(tangents == null) { if(other.tangents != null) return false; }
				else if(!tangents.equals(other.tangents)) return false;
				
				if(bitangents == null) { if(other.bitangents != null) return false; }
				else if(!bitangents.equals(other.bitangents)) return false;
				
				if(normal == null) { if(other.normal != null) return false; }
				else if(!normal.equals(other.normal)) return false;
				
				if(position == null) { if(other.position != null) return false; }
				else if(!position.equals(other.position)) return false;
				
				if(texCoord == null) { if(other.texCoord != null) return false; }
				else if(!texCoord.equals(other.texCoord)) return false;
				
				return true;
			}
		}
	}
}
