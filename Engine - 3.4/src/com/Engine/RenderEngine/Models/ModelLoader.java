package com.Engine.RenderEngine.Models;

import static com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage.Static_Draw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Deferred.High.DeferredShader;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.RenderEngine.Util.RenderStructs.Vertex;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class ModelLoader {
	public static ModelData loadOBJ(String filePath) {
		if(!filePath.toLowerCase().endsWith(".obj")) 
			throw new InvalidParameterException("loadOBJ can only load \"*.obj\" files!");
		
		try {
			BufferedReader read = new BufferedReader(new FileReader(new File(filePath)));
			
			ArrayList<String> verticiesRaw = new ArrayList<>();
			ArrayList<String> texCoordsRaw = new ArrayList<>();
			ArrayList<String> normalsRaw = new ArrayList<>();
			ArrayList<String> facesRaw = new ArrayList<>();
			
			String line = null;
			while((line = read.readLine()) != null) {
				if(line.startsWith("v ")) verticiesRaw.add(line);
				else if(line.startsWith("vt ")) texCoordsRaw.add(line);
				else if(line.startsWith("vn ")) normalsRaw.add(line);
				else if(line.startsWith("f ")) facesRaw.add(line);
			} read.close();		
			
			HashSet<Vertex> vertexsCheck = new HashSet<>();
			ArrayList<Vertex> vertexs = new ArrayList<>();
			ArrayList<Integer> indicies = new ArrayList<>();
			
			boolean hasTexCoords = texCoordsRaw.size() > 0;
			boolean hasNormals = normalsRaw.size() > 0;			
			
			for(String face : facesRaw) {
				String[] faceData = face.split(" ");
				for(String vertexData : faceData) {
					if(vertexData.startsWith("f")) continue;
					
					String[] data = vertexData.split("/");

					Vector2f texCoord = new Vector2f();
					Vector3f normal = new Vector3f();
										
					Vector3f position = new Vector3f(toFloatArray(verticiesRaw.get(Integer.parseInt(data[0]) - 1).split(" ")));
					if(hasTexCoords) 
						texCoord = new Vector2f(toFloatArray(texCoordsRaw.get(Integer.parseInt(data[1]) - 1).split(" ")));
					if(hasNormals)   
						normal = new Vector3f(toFloatArray(normalsRaw.get(Integer.parseInt(data[2]) - 1).split(" ")));
					
					Vertex vertex = new Vertex(position, texCoord, normal);
					if(vertexsCheck.add(vertex)) {
						vertexs.add(vertex);
					} 
					
					indicies.add(vertexs.indexOf(vertex));
				}
			}
			
			float[] vertecies = new float[vertexs.size() * 3];
			float[] texCoords = new float[vertexs.size() * 2 * (hasTexCoords ? 1 : 0)];
			float[] normals   = new float[vertexs.size() * 3 * (hasNormals ? 1 : 0)];
			
			float radius = 0;
			Vector3f center = new Vector3f();
			
			for(int i = 0; i < vertexs.size(); i ++) {
				Vertex vertex = vertexs.get(i);
				
				vertecies[i*3 + 0] = vertex.getPosition().x;
				vertecies[i*3 + 1] = vertex.getPosition().y;
				vertecies[i*3 + 2] = vertex.getPosition().z;
				
				if(hasTexCoords) {
					texCoords[i*2 + 0] = vertex.getTexCoord().x;
					texCoords[i*2 + 1] = 1.0f - vertex.getTexCoord().y;
				}
				
				if(hasNormals) {
					normals[i*3 + 0] = vertex.getNormal().x;
					normals[i*3 + 1] = vertex.getNormal().y;
					normals[i*3 + 2] = vertex.getNormal().z;
				}		
				
				center = center.add(vertex.getPosition());
				radius = radius < vertex.getPosition().abs().max() ? vertex.getPosition().abs().max() : radius;
			}
			
			center = center.divide(vertexs.size());
			radius = Math.abs(radius - center.max());

			float[] tangents = new float[vertexs.size() * 3 * (hasTexCoords ? 1 : 0)];
			if(hasTexCoords) {
				Vector3f[] vertexTangents = new Vector3f[vertexs.size()];
				for(int i = 0; i < indicies.size(); i += 3) {
					int index0 = indicies.get(i + 0);
					int index1 = indicies.get(i + 1);
					int index2 = indicies.get(i + 2);
					
					Vertex v0 = vertexs.get(index0);
					Vertex v1 = vertexs.get(index1);
					Vertex v2 = vertexs.get(index2);
					
					Vector3f deltaPos1 = v1.getPosition().subtract(v0.getPosition());
					Vector3f deltaPos2 = v2.getPosition().subtract(v0.getPosition());

					Vector2f deltaUV1 = v1.getTexCoord().subtract(v0.getTexCoord());
					Vector2f deltaUV2 = v2.getTexCoord().subtract(v0.getTexCoord());
					
					float r = 1 / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
					Vector3f tangent = deltaPos1.multiply(deltaUV2.y).subtract(deltaPos2.multiply(deltaUV1.y)).multiply(r);

					vertexTangents[index0] = vertexTangents[index0] == null ? tangent : vertexTangents[index0].add(tangent);
					vertexTangents[index1] = vertexTangents[index1] == null ? tangent : vertexTangents[index1].add(tangent);
					vertexTangents[index2] = vertexTangents[index2] == null ? tangent : vertexTangents[index2].add(tangent);
				}
				
				for(int i = 0; i < vertexTangents.length; i ++) {
					Vector3f tanget = vertexTangents[i].normalize();
					
					tangents[i * 3 + 0] = tanget.x;
					tangents[i * 3 + 1] = tanget.y;
					tangents[i * 3 + 2] = tanget.z;
				}
			}
			
			int[] indiciesArray = new int[indicies.size()];
			for(int i = 0; i < indicies.size(); i ++) indiciesArray[i] = indicies.get(i);
			
			return loadModel(vertecies, texCoords, normals, tangents, indiciesArray, 
					new BufferUsage[] { Static_Draw, Static_Draw, Static_Draw, Static_Draw },
					radius, 10000, center
				);

		} catch(IOException e) {
			System.err.println("Failed to load Model from: " + filePath);
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ModelData loadModel(float[] vertices, float[] texCoords, float[] normals, float[] tangents, int[] indicies,
			BufferUsage[] usages, float radius, float renderDistance, Vector3f center) {
		
		ModelData modelData = new ModelData(radius, renderDistance, center);

		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, vertices, usages[0]);
		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_TEXCOORDS, 2, texCoords, usages[1]);
		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_NORMALS, 3, normals, usages[2]);
		modelData.storeDataInAttributeList(DeferredShader.ATTRIBUTE_LOC_TANGENT, 3, tangents, usages[3]);
		modelData.loadIndices(indicies);
		
		return modelData;
	}
	
	private static float[] toFloatArray(String[] strings) {
		float[] floats = new float[strings.length - 1];
		for(int i = 0; i < floats.length; i ++) {
			floats[i] = Float.parseFloat(strings[i + 1]);
		} return floats;
	}
}
