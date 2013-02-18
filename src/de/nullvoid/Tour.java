package de.nullvoid;

import org.anddev.andengine.entity.primitive.Line;

import static java.lang.Math.*;

public class Tour {

	Line[] lines = new Line[100];

	static final String TAG = "Debug";
	float xCurrent;
	float yCurrent;
	float xLast;
	float yLast;
	int numLines = 0;
	float lenght = 0;

	Tour(float xFirst, float yFirst) {
		this.xCurrent = xFirst;
		this.yCurrent = yFirst;
	}

	void addLine(float x, float y) {
		xLast = xCurrent;
		yLast = yCurrent;
		xCurrent = x;
		yCurrent = y;
		float xD = abs(xLast - xCurrent);
		float yD = abs(yLast - yCurrent);
		lines[numLines] = new Line(xLast, yLast, xCurrent, yCurrent);
		numLines++;
		lenght += sqrt(xD * xD + yD * yD);
	}

	Line getLine(int i) {
		return lines[i];

	}
	float getLenght() {
		return lenght;
	}
	int getNumLines() {
		return numLines;
	}
}
