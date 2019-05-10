package game;

import processing.core.PApplet;

public class Obstacle {
	private int x, y;
	private int width, height;
	/**
	 * Constructs an Obstacle with center x,y and dimensions width by height
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isInside(int x, int y) {
		if (Math.abs(this.x - x) < width / 2) {
			if (Math.abs(this.y - y) < height / 2) {
				return true;
			}
		}
		return false;
	}

	public void draw(PApplet drawer) {
		drawer.rect(x - width / 2, y - height / 2, x + width / 2, y + width / 2);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
