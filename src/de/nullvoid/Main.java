package de.nullvoid;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import java.util.*;

public class Main extends BaseGameActivity implements IOnSceneTouchListener {

	private int counter = 0;
	private int wCamera;
	private int hCamera;
	private float xTouch = 200;
	private float yTouch = 200;
	private float xLineStart;
	private float yLineStart;
	private float tourMaxLenght = 400;

	private boolean drawTour = false;

	// Fighter fighter;
	ArrayList<Tour> tours = new ArrayList<Tour>();

	Tour currentTour;

	private ZoomCamera mCamera;
	private Scene mScene;
	private BitmapTextureAtlas bitmap;
	private TiledTextureRegion fighterTex;
	private TiledTextureRegion motherTex;
	private final static String TAG = "Debug";

	@Override
	public void onLoadComplete() {

	}

	@Override
	public Engine onLoadEngine() {
		Log.d(TAG, "onLoadEngine");
		final Display display = getWindowManager().getDefaultDisplay();
		wCamera = display.getWidth();
		hCamera = display.getHeight();
		this.mCamera = new ZoomCamera(0, 0, wCamera, hCamera);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(wCamera, hCamera), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		Log.d(TAG, "onLoadResources");

		this.bitmap = new BitmapTextureAtlas(16, 16,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mEngine.getTextureManager().loadTexture(this.bitmap);

		loadStuff();
	}

	@Override
	public Scene onLoadScene() {

		Log.d(TAG, "onLoadScene");
		this.mEngine.registerUpdateHandler(new FPSLogger());
		/*
		 * this.mEngine.registerUpdateHandler(new IUpdateHandler() {
		 * 
		 * @Override public void onUpdate(float pSecondsElapsed) { counter +=1;
		 * GAME LOOP
		 * 
		 * @Override public void reset() { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
		this.mScene = new Scene();
		this.mScene.setBackground(new ColorBackground(0.0f, 0.0f, 0.0f));

		this.mScene.setOnSceneTouchListenerBindingEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

		initStuff();

		return this.mScene;
	}

	// @Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event) {
		// tours.get(tours.size() - 1) IS LAST tour OF tours LIST

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if(finger on  dockingbay) {
			drawTour = true;
			xLineStart = event.getMotionEvent().getX();
			yLineStart = event.getMotionEvent().getY();

			tours.add(new Tour(xLineStart, yLineStart));
			}

			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (drawTour) {
				xTouch = event.getMotionEvent().getX();
				yTouch = event.getMotionEvent().getY();
				currentTour = tours.get(tours.size() - 1);

				if (currentTour.getLenght() < tourMaxLenght) {
					// add Line
					currentTour.addLine(xTouch, yTouch);
					// Drawing Line
					Line line = currentTour
							.getLine(currentTour.getNumLines() - 1);
					mScene.attachChild(line);
				}
			}

			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (drawTour) {
				Fighter fighter = new Fighter(xLineStart, yLineStart,
						this.fighterTex, currentTour);
				final PhysicsHandler physics = new PhysicsHandler(fighter);
				fighter.registerUpdateHandler(physics);
				mScene.attachChild(fighter);
				drawTour = false;
			}
			return true;
		}
		return false;
	}

	public void loadStuff() {
		this.fighterTex = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bitmap, this, "stuff.png", 0, 0, 1,
						1);
		this.motherTex = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bitmap, this, "stuff.png", 0, 0, 1,
						1);
	}

	public void initStuff() {
		// INCOMING TEST
		// Incoming(float x, float y, float v, float dir, final
		// TiledTextureRegion textureRegio
		Asteroid asteroid = new Asteroid(30, 30, 20.0f, 95.0f, this.fighterTex);
		final PhysicsHandler physics = new PhysicsHandler(asteroid);
		asteroid.registerUpdateHandler(physics);
		mScene.attachChild(asteroid);
		// //
	}

	public void iterateLine() {

	}
}