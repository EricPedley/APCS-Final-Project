package game;

import processing.core.PApplet;

public class Obstacle {
	private int x, y;
	private int width, height;

	/**
	 * Constructs a rectangular Obstacle with center x,y and dimensions width by
	 * height
	 * 
	 * @param x      x coordinate of center
	 * @param y      y coordinate of center
	 * @param width  width of Obstacle
	 * @param height height of Obstacle
	 */
	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns true if point (x,y) is inside the obstacle
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return whether the point is inside the obstacle
	 */
	public boolean isInside(int x, int y) {
		if (Math.abs(this.x - x) < width / 2) {
			if (Math.abs(this.y - y) < height / 2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Draws the Obstacle
	 * 
	 * @param drawer the PApplet on which the obstacle is drawn
	 */
	public void draw(PApplet drawer) {
		drawer.rect(x - width / 2, y - height / 2, x + width / 2, y + width / 2);
	}

	/**
	 * Returns the X coordinate of the center
	 * 
	 * @return center's x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the Y coordinate of the center
	 * 
	 * @return center's y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the obstacle's width
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the obstacle's height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
}
