package com.Engine.Demo.particles;

import com.Engine.RenderEngine.Particles.Particle;
import com.Engine.RenderEngine.Particles.ParticleManager;
import com.Engine.RenderEngine.Particles.Texture.ParticleTexture;
import com.Engine.Util.Vectors.Vector3f;

public class ParticleEmitter {
	private Vector3f position;
	private ParticleTexture texture;
	
	private float emitionDelay;
	private float elapsedTime;
	
	private ParticleManager manager;
	
	public ParticleEmitter(ParticleManager manager, Vector3f position, ParticleTexture texture, float emitionDelay) {
		this.manager = manager;
		
		this.position = position;
		this.texture = texture;
		this.emitionDelay = emitionDelay;
	}

	public void update(float delta) {
		if(delta > 2) return;
		elapsedTime += delta;
		
		while(elapsedTime >= emitionDelay) {
			if(manager.getParticleCount() < ParticleManager.PARTICLE_CREATION_CAP) {
				
//				Particle particle = new Particle(position, (float) (Math.random() * 360),
//						 Vector3f.random(.5f).add(0.5f), Vector3f.random(.5f).subtract(.25f),
//						 new Vector3f(0, -3, 0).add(0, -2, 0), 2.5f, texture);
				
				Particle particle = new Particle(position, (float) (Math.random() * 360),
						 Vector3f.random(.5f).add(0.5f), Vector3f.random(.5f).subtract(.25f),
						 Vector3f.random(1).subtract(0.5f).addY(0.5f), (float) Math.random() * 1.5f, texture);
				manager.addParticle(particle);
				
			} else {
				elapsedTime = 0;
				break;
			}
			
			elapsedTime -= emitionDelay;
		}
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setTexture(ParticleTexture texture) {
		this.texture = texture;
	}

	public Vector3f getPosition() { return position; }
	public ParticleTexture getTexture() { return texture; }
}
