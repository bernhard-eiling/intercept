package de.nullvoid;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import static java.lang.Math.*;


public class Incoming extends AnimatedSprite {
	
	private final PhysicsHandler physics;
	
	float vel;
	double xVel;
	double yVel;
	float dir;
	float xPos;
	float yPos;
	
	Incoming(float x, float y, float v, float dir, final TiledTextureRegion textureRegion) {
		super(x, y, textureRegion);
		this.vel = v;
		this.xVel = 0.0f;
		this.yVel = 0.0f;
		this.dir = dir;
		this.physics = new PhysicsHandler(this);
		this.registerUpdateHandler(this.physics);
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.xVel = vel * cos(dir);
		this.yVel = vel * sin(dir);
		this.physics.setVelocity((float)xVel, (float)yVel);
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	void setSpeed(float v) {
		this.vel = v;
	}
	
	void setDir(float dir) {
		this.dir = dir;
	}
}
