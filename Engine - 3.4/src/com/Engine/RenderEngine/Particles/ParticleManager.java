package com.Engine.RenderEngine.Particles;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.Engine.RenderEngine.Particles.Render.ParticleInstanceRender;
import com.Engine.RenderEngine.Particles.Render.ParticleRenderProperties;
import com.Engine.RenderEngine.Particles.Render.ParticleShader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Time;
import com.Engine.Util.Vectors.Vector3f;

public class ParticleManager {
	public static final int PARTICLE_CREATION_CAP = 1_000;
	public static final ParticleShader ParticleShader = new ParticleShader();
	
	private List<Particle> particles;
	private ParticleInstanceRender model;
	
	public static double avg;
	private static int count;
	private static long sum;
	
	public ParticleManager() {
		particles = new LinkedList<>();
		model = new ParticleInstanceRender(ParticleShader);
	}
	
	public int getParticleCount() { return particles.size(); }
	
	public void addParticle(Particle particle) { 
		if(getParticleCount() < PARTICLE_CREATION_CAP)
			particles.add(particle); 
	}
	
	public void update(float delta, Camera camera) {
		Iterator<Particle> iterator = particles.iterator();
		while(iterator.hasNext()) {
			if(!iterator.next().update(delta, camera)) {
				iterator.remove();
			}
		}
		
		long endTime, startTime = Time.getSystemTime();
		ParticleSorter.sort(particles);
		endTime = Time.getSystemTime();
		
		float last = Float.MAX_VALUE;
		for(iterator = particles.iterator(); iterator.hasNext();) {
			Particle particle = iterator.next();
			if(particle.getDistance() > last) System.err.println("ERRORR");
			last = particle.getDistance();
		}
		
		sum += endTime - startTime;
		avg = (double) sum / ++ count;
//		System.out.println(avg);
	}
	
	public void render() {
		model = new ParticleInstanceRender(ParticleShader);
		for(Particle particle : particles) {
			float normalTime = particle.getElapsedTime() / particle.getTimeAlive();
			int indexCount = particle.getTexture().getNumberOfRows() * particle.getTexture().getNumberOfRows();
			float textureIndex = indexCount * normalTime;
			
			int index1 = (int) Math.floor(textureIndex);
			int index2 = index1 < indexCount - 1 ? index1 + 1 : index1;
			float blend = textureIndex % 1;
			
			ParticleRenderProperties properties = new ParticleRenderProperties(
					new Transform(particle.getPosition(), new Vector3f(0, 0, particle.getRotation()), particle.getScale()),
					particle.getTexture(), particle.usingAdditive(), index1, index2, blend
				);
			
			model.render(properties);
		}
	}
}
