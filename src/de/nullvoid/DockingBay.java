package de.nullvoid;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class DockingBay extends AnimatedSprite {
	
	int capacity;
	final int maxCapacity = 3;
	final int minCapacity = 1;

	DockingBay(float x, float y, final TiledTextureRegion textureRegion) {
		super(x, y, textureRegion);
		capacity = minCapacity;
	}
	
	public boolean increaseCapacity() {
		if(capacity < maxCapacity) {
			this.capacity++;
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean decreaseCapacity() {
		if(capacity > minCapacity) {
			this.capacity--;
			return true;
		} else {
			return false;
		}
	}
}
