package de.nullvoid;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import static java.lang.Math.*;

public class Fighter extends AnimatedSprite {

	private final PhysicsHandler physics;
	Tour tour;
	boolean move = false;
	boolean newLine = true;
	final static float maxSpeed = 80.0f;

	double currentDeg = 0;
	double lastDeg = 0;
	double degAlt = 90;
	double dRot;
	double dRotFrag;
	double speedRotMax = 10; // higher == slower
	double speedRot = speedRotMax;
	float rolling = 2;	
	float xPos;
	float yPos;
	final static float maxSize = 1.0f;
	float xSize = maxSize;
	float ySize = maxSize;
	float scaleStep = (maxSize/rolling) / (float)speedRotMax;
	float offPos = this.getBaseWidth()/2;
	float lastXPos;
	float xStart;
	float yStart;
	float xEnd;
	float yEnd;
	float xSpeed = maxSpeed;
	float ySpeed = maxSpeed;
	float xSlope = 1;
	float ySlope = 1;
	float xD;
	float yD;
	int line = 0;
	private final static String TAG = "Debug";

	Fighter(float x, float y, final TiledTextureRegion textureRegion, Tour tour) {
		super(x, y, textureRegion);
		this.physics = new PhysicsHandler(this);
		this.registerUpdateHandler(this.physics);
		this.tour = tour;

		xPos = xStart = tour.getLine(line).getX1();
		yPos = yStart = tour.getLine(line).getY1();
		xEnd = tour.getLine(line).getX2();
		yEnd = tour.getLine(line).getY2();
		xD = xStart - xEnd;
		yD = yStart - yEnd;
		if (xD >= 0) {
			degAlt = 270;
		}
		currentDeg = Math.toDegrees(Math.atan((double) (yD / xD)));
		this.setRotation((float) (currentDeg + degAlt));
		Log.d(TAG, "offPos : " + offPos);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		// BEGINN OF NEW LINE SEGMENT
		if (newLine && tour.getNumLines() > line) {

			newLine = false;

			xPos = xStart = tour.getLine(line).getX1() - offPos;
			yPos = yStart = tour.getLine(line).getY1() - offPos;
			xEnd = tour.getLine(line).getX2() - offPos;
			yEnd = tour.getLine(line).getY2() - offPos;
			xD = xStart - xEnd;
			yD = yStart - yEnd;
			xSpeed = maxSpeed;
			ySpeed = maxSpeed;
			degAlt = 90;
			if (xD >= 0) {
				xSpeed = -xSpeed;
				degAlt = 270;
			}
			if (yD >= 0) {
				ySpeed = -ySpeed;
			}
			if (abs(xD) >= abs(yD)) {
				ySlope = abs(yD) / abs(xD);
				xSlope = 1;
			} else if (abs(xD) < abs(yD)) {
				xSlope = abs(xD) / abs(yD);
				ySlope = 1;
			}
			this.setPosition(xPos, yPos);
			speedRot = speedRotMax;
			lastDeg = currentDeg;

			currentDeg = Math.toDegrees(Math.atan((double) (yD / xD)));
			Log.d(TAG, "lastDeg : " + lastDeg);
			Log.d(TAG, "currentDeg : " + currentDeg);
			dRot = lastDeg - currentDeg;
			dRotFrag = dRot / speedRot;
		}

		// SETTING POSITION
		lastXPos = xPos;
		xPos += xSlope * xSpeed * pSecondsElapsed;
		yPos += ySlope * ySpeed * pSecondsElapsed;
		this.setPosition(xPos, yPos);
		
		// SMOOTH ROTATION
		if (speedRot > 0) {
			// SCALE SPRITE
			if (speedRot > speedRotMax/2) {
				// ROLLING IN
				this.setScaleX(xSize -= scaleStep);
			} else {
				// ROLLING OUT
				this.setScaleX(xSize += scaleStep);
			}
			lastDeg -= dRotFrag;
			if (lastXPos > xPos) {
				degAlt = 270;
			} else {
				degAlt = 90;
			}
			this.setRotation((float) (lastDeg + degAlt));
			speedRot--;
		}

		// END OF LINE SEGMENT
		if (this.getX() >= xEnd && this.getY() >= yEnd && xD <= 0 && yD <= 0) {
			endLine();
		} else if (this.getX() <= xEnd && this.getY() <= yEnd && xD >= 0 && yD >= 0) {
			endLine();
		} else if (this.getX() <= xEnd && this.getY() >= yEnd && xD >= 0 && yD <= 0) {
			endLine();
		} else if (this.getX() >= xEnd && this.getY() <= yEnd && xD <= 0 && yD >= 0) {
			endLine();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	void endLine() {
		speedRot = 0;
		xSize = maxSize;
		this.setScaleX(1.0f);
		line++;
		newLine = true;
	}

	void setTour(Tour tour) {
		this.tour = tour;
	}
}
