package com.Engine.PhysicsEngine.Render.Octree;

import java.util.ArrayList;
import java.util.HashMap;

import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Models.ModelData.VBO.BufferUsage;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Shaders.Render.Shader;
import com.Engine.Util.Vectors.Vector3f;

public class PhysicsCubeRender extends Model {

	public PhysicsCubeRender(Vector3f center, Vector3f radius) {
		super(null);
		
		modelData = new ModelData(radius.max(), 1000, center);
		
		HashMap<Vector3f, Integer> map = new HashMap<>();
		ArrayList<Vector3f> vertices = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		
		addAABB(radius, center, map, vertices, indices);
		
		float[] data = new float[vertices.size() * 3];
		for(int i = 0; i < vertices.size(); i ++) {
			Vector3f pos = vertices.get(i);
			
			data[i * 3 + 0] = pos.getX();
			data[i * 3 + 1] = pos.getY();
			data[i * 3 + 2] = pos.getZ();
		}
		
		int[] ind = new int[indices.size()];
		for(int i = 0; i < indices.size(); i ++)
			ind[i] = indices.get(i).intValue();
		
		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, data, BufferUsage.Static_Draw);
		modelData.loadIndices(ind);
	}
	
	private void addAABB(Vector3f r, Vector3f c, HashMap<Vector3f, Integer> map, ArrayList<Vector3f> v, ArrayList<Integer> i) {
		Vector3f nnn = c.add(-r.x, -r.y, -r.z);
		Vector3f nnp = c.add(-r.x, -r.y,  r.z);
		Vector3f pnp = c.add( r.x, -r.y,  r.z);
		Vector3f pnn = c.add( r.x, -r.y, -r.z);
		
		Vector3f npn = c.add(-r.x,  r.y, -r.z);
		Vector3f npp = c.add(-r.x,  r.y,  r.z);
		Vector3f ppp = c.add( r.x,  r.y,  r.z);
		Vector3f ppn = c.add( r.x,  r.y, -r.z);
		
		addQuad(nnn, nnp, npp, npn, map, v, i);
		addQuad(pnn, pnp, ppp, ppn, map, v, i);
		
		addQuad(nnn, pnn, ppn, npn, map, v, i);
		addQuad(nnp, pnp, ppp, npp, map, v, i);

		addQuad(nnn, pnn, pnp, nnp, map, v, i);
		addQuad(npn, ppn, ppp, npp, map, v, i);
	}
	
	private void addQuad(Vector3f a, Vector3f b, Vector3f c, Vector3f d, HashMap<Vector3f, Integer> map, ArrayList<Vector3f> v, ArrayList<Integer> i) {
		addPoint(a, map, v, i); addPoint(c, map, v, i); addPoint(b, map, v, i);
		addPoint(a, map, v, i); addPoint(c, map, v, i); addPoint(d, map, v, i);
	}
	
	private void addPoint(Vector3f pos, HashMap<Vector3f, Integer> map, ArrayList<Vector3f> v, ArrayList<Integer> i) {
		if(!map.containsKey(pos)) {
			map.put(pos, v.size());
			v.add(pos);
		}
		
		i.add(map.get(pos));
	}
}