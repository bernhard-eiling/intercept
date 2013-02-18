package de.nullvoid;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Asteroid extends Incoming{
	Asteroid(float x, float y, float s, float dir, final TiledTextureRegion textureRegion)  {
		super(x, y, s, dir, textureRegion);
	}
}
