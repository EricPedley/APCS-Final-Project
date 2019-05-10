package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x, y, angle;
	protected float width, height;
	private PImage img;

	public Sprite(float x, float y, PImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		width = img.width;
		height = img.height;
		angle = 0;
	}

	/**
	 * Draws this sprite on drawer
	 * 
	 * @param drawer
	 * @post drawer's imageMode is set to CENTER
	 */
	public void draw(PApplet drawer) {
		drawer.imageMode(PApplet.CENTER);
		// drawer.rectMode(PApplet.CENTER);
		drawer.pushMatrix();
		drawer.rotate(angle);
		Vector v = new Vector(x, y);
		v.rotate(-angle);
		Vector v2 = new Vector(x, y);
		v2.subtract(v);
		v2.multiply(-1);
		drawer.translate(v2.x, v2.y);
		drawer.image(img, x, y, width, height);
		drawer.noFill();
		// drawer.rect(x,y,width,height);
		drawer.popMatrix();
	}

	public void scale(float scaleFactor) {
		width *= scaleFactor;
		height *= scaleFactor;
	}

	public void move(float dX, float dY) {
		x += dX;
		y += dY;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean intersects(Sprite other, PApplet drawer) {
		Vector[] edges = new Vector[4];
		edges[0] = new Vector(width * PApplet.cos(angle), width * PApplet.sin(angle));
		edges[1] = new Vector(-height * PApplet.sin(angle), height * PApplet.cos(angle));
		edges[2] = new Vector(other.width * PApplet.cos(other.angle), other.width * PApplet.sin(other.angle));
		edges[3] = new Vector(-other.height * PApplet.sin(other.angle), other.height * PApplet.cos(other.angle));
		edges[2].draw(drawer, other.x - other.width / 2, other.y - other.height / 2);
		//edges[3].draw(drawer, other.x - other.width / 2, other.y - other.height / 2);
		Vector[][] points = new Vector[2][4];
		Sprite[] sprites = new Sprite[] { this, other };
		for (int i : new int[] { 0, 1 }) {
			points[i][0] = new Vector(sprites[i].x - sprites[i].getWidth() / 2,
					sprites[i].y - sprites[i].getHeight() / 2);
			points[i][1] = points[i][0].addN(edges[0+2*i]);
			points[i][2] = points[i][0].addN(edges[1+2*i]);
			points[i][3] = points[i][0].addN(edges[0]).addN(edges[1]);
			points[i][0].draw(drawer, 0, 0);
		}
		for (Vector v : edges) {//for each edge of both rectangles
			v.rotate(PApplet.PI / 2);//v is the perpendicular vector to the edge
			v.draw(drawer, 100, 100);
			float maxThis = 0, minThis = 1000000, maxOther = 0, minOther = 1000000;
			for (Vector b : points[0]) {//for each point of the 1st rectangle
				float l = b.getParallelComponentTo(v).length();//l is the projection of the point onto v
				//System.out.println(l+"|"+b.y);
				if (l > maxThis)
					maxThis = l;
				else if (l < minThis)
					minThis = l;
			}
			for (Vector b : points[1]) {
				float l = b.getParallelComponentTo(v).length();
				if (l > maxOther)
					maxOther = l;
				else if (l < minOther)
					minOther = l;
			}
			drawer.strokeWeight(20);
			drawer.stroke(255,0,0);
			drawer.point(maxThis, 200);
			drawer.stroke(255,125,0);
			drawer.point(minThis, 200);
			drawer.stroke(0,0,255);
			drawer.point(maxOther, 200);
			drawer.stroke(0,255,255);
			drawer.point(minOther, 200);
			drawer.strokeWeight(1);
			if (!(Math.min(maxThis, maxOther) >= Math.max(minThis, minOther)))
				return false;
		}
		return true;
	}

}
