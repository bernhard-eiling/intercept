package de.nullvoid;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class Mothership extends AnimatedSprite {

	Mothership(float x, float y, final TiledTextureRegion motherTex, final TiledTextureRegion bayTex) {		 {
		super(x, y, motherTex);
		ArrayList<DockingBay> bays = new ArrayList<DockingBay>();
		bays.add(new DockingBay());
	}
}
