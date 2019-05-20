package game;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x, y, angle;
	public Vector vel, acc;
	protected float width, height;
	private PImage img;
	protected int hitboxMode;// 0 is rectangle and 1 is circle

	public Sprite(float x, float y, PImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		width = img.width;
		height = img.height;
		angle = 0;
		vel = new Vector();
		acc = new Vector();
	}

	public Sprite(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		angle = 0;
		vel = new Vector();
		acc = new Vector();
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
		drawer.ellipseMode(PApplet.CENTER);
		drawer.ellipse(x, y, Math.min(width, height), Math.min(width, height));
		// drawer.rect(x,y,width,height);
		drawer.popMatrix();
	}

	/**
	 * Makes the width and height of this sprite smaller/larger by multiplying both
	 * of them by scaleFactor
	 * 
	 * @param scaleFactor
	 */
	public void scale(float scaleFactor) {
		width *= scaleFactor;
		height *= scaleFactor;
	}

	/**
	 * Makes the sprite move based on its velocity
	 * 
	 * @param l
	 * @param debugger
	 */
	public void updatePos(Level l) {
		vel.add(acc);
		int[][] tiles = l.getTileArray();
		if (vel.length() > 0)
			this.angle = vel.getAngle();
		x += vel.x;
		y += vel.y;
		//debugger.fill(0);
		Vector v = new Vector((int) (x / Level.TILE_SIZE), (int) (y / Level.TILE_SIZE));
		Vector[] transformations = { new Vector(-1, -1), new Vector(0, -1), new Vector(1, -1), new Vector(-1, 0),
				new Vector(1, 0), new Vector(-1, 1), new Vector(0, 1), new Vector(1, 1) };
		for (int i = 0; i < 8; i++) {
			Vector v2 = v.addN(transformations[i]);
			if (v2.x >= 0 && v2.y >= 0 && v2.x < tiles.length && v2.y < tiles[0].length
					&& tiles[(int) v2.x][(int) v2.y] == 0) {
				Sprite tile = new Sprite(v2.x * Level.TILE_SIZE + Level.TILE_SIZE / 2,
						v2.y * Level.TILE_SIZE + Level.TILE_SIZE / 2, Level.TILE_SIZE, Level.TILE_SIZE);
				handleTileCollisions(tile);
			}
		}

		//debugger.rect(v.x * Level.TILE_SIZE, v.y * Level.TILE_SIZE, Level.TILE_SIZE, Level.TILE_SIZE);

	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	/**
	 * Checks to see if this sprite is intersecting another sprite
	 * 
	 * @param other
	 * @return
	 */
	public boolean intersects(Sprite other) {
		if (hitboxMode + other.hitboxMode == 0)
			return intersectsRectToRect(other);
		else if (hitboxMode == 0 && other.hitboxMode == 1)
			return intersectsRectToCircle(other);
		else if (hitboxMode == 1 && other.hitboxMode == 0)
			return other.intersectsRectToCircle(this);
		else
			return false;
	}

	private boolean intersectsRectToRect(Sprite other) {
		Vector[] edges = new Vector[4];

		edges[0] = new Vector(width * PApplet.cos(angle), width * PApplet.sin(angle));
		edges[1] = new Vector(-height * PApplet.sin(angle), height * PApplet.cos(angle));
		edges[2] = new Vector(other.width * PApplet.cos(other.angle), other.width * PApplet.sin(other.angle));
		edges[3] = new Vector(-other.height * PApplet.sin(other.angle), other.height * PApplet.cos(other.angle));

		Vector[][] points = new Vector[2][4];
		Sprite[] sprites = new Sprite[] { this, other };
		for (int i : new int[] { 0, 1 }) {
			float l = (float) (Math.sqrt(sprites[i].width * sprites[i].width + sprites[i].height * sprites[i].height))
					/ 2;// length of half the diagonal of this rectangle
			float a = -PApplet.atan(sprites[i].height / sprites[i].width);
			points[i][0] = new Vector(sprites[i].x - l * PApplet.cos(a - sprites[i].angle),
					sprites[i].y + l * PApplet.sin(a - sprites[i].angle));
			// drawer.stroke(255,0,0);
			// points[i][0].draw(drawer);
			points[i][1] = points[i][0].addN(edges[0 + 2 * i]);
			// drawer.stroke(255,255,0);
			// points[i][1].draw(drawer);
			points[i][2] = points[i][0].addN(edges[1 + 2 * i]);
			// drawer.stroke(0,0,255);
			// points[i][2].draw(drawer);
			points[i][3] = points[i][0].addN(edges[0 + 2 * i]).addN(edges[1 + 2 * i]);
			// drawer.stroke(0,255,255);
			// points[i][3].draw(drawer);
		}
		// int count =0;
		for (Vector v : edges) {// for each edge of both rectangles
			// v = v.rotateN(PApplet.PI / 2);// v is the perpendicular vector to the edge
			float maxThis = 0, minThis = 1000000, maxOther = 0, minOther = 1000000;
			for (Vector b : points[0]) {// for each point of the 1st rectangle
				Vector parallel = b.getOrthogonalComponentTo(v);
				float l = parallel.length();// l is the projection of the point onto v
				if (l > maxThis)
					maxThis = l;
				if (l < minThis)
					minThis = l;
			}
			for (Vector b : points[1]) {// for each point of the 2nd rectangle
				Vector parallel = b.getOrthogonalComponentTo(v);
				float l = parallel.length();// l is the projection of the point onto v
				if (l > maxOther)
					maxOther = l;
				if (l < minOther)
					minOther = l;
			}

			if (!(Math.min(maxThis, maxOther) >= Math.max(minThis, minOther)))
				return false;
		}
		return true;
	}

	private boolean intersectsRectToCircle(Sprite other) {// this hitbox is a rect and other's is a circle
		Vector[] edges = new Vector[6];
		edges[0] = new Vector(width * PApplet.cos(angle), width * PApplet.sin(angle));
		edges[1] = new Vector(-height * PApplet.sin(angle), height * PApplet.cos(angle));

		Vector[] points = new Vector[4];
		float l = (float) (Math.sqrt(width * width + height * height)) / 2;// length of half the diagonal of this
																			// rectangle
		float a = -PApplet.atan(height / width);
		points[0] = new Vector(x - l * PApplet.cos(a - angle), y + l * PApplet.sin(a - angle));
		points[1] = points[0].addN(edges[0]);
		points[2] = points[0].addN(edges[1]);
		points[3] = points[0].addN(edges[0]).addN(edges[1]);
		Vector center = new Vector(other.x, other.y);
		float radius = other.width / 2;
		edges[2] = center.subtractN(points[0]);
		edges[3] = center.subtractN(points[1]);
		edges[4] = center.subtractN(points[2]);
		edges[5] = center.subtractN(points[3]);
		float maxThis = 0, minThis = 1000000;
		for (Vector v : edges) {
			for (Vector b : points) {// for each point of this rectangle
				Vector parallel = b.getOrthogonalComponentTo(v);
				float l2 = parallel.length();// l is the projection of the point onto v
				if (l2 > maxThis)
					maxThis = l2;
				if (l2 < minThis)
					minThis = l2;

			}
			float l3 = center.getOrthogonalComponentTo(v).length();
			if (!(Math.min(maxThis, l3 + radius) >= Math.max(minThis, l3 - radius)))
				return false;
		}
		return true;

	}

	/**
	 * Makes this sprite not move inside another sprite, as long as tile's hitbox is
	 * a rectangle and is axis-aligned(edges are parallel to x or y axes) and this
	 * sprite's hitbox is a circle
	 * 
	 * @pre hitboxMode must be 1
	 * @param tile     hitboxMode has to be 0 and angle must be an integer multiple
	 *                 of PI/2
	 * @param debugger
	 */
	public void handleTileCollisions(Sprite tile) {// simpler collisions method because tiles are
																		// always axis-aligned(at 90deg angles) and
																		// square
		float r = width / 2;
		float r2 = tile.width / 2;
		if (x + r >= tile.x - r2 && y >= tile.y - r2 && y <= tile.y + r2 && x - r <= tile.x + r2) {
			x -= vel.x;
		}
		if (y + r >= tile.y - r2 && x >= tile.x - r2 && x <= tile.x + r2 && y - r <= tile.y + r2) {
			y -= vel.y;
		}
		int[][] transformations = { { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 } };
		for (int[] i : transformations) {
			Vector corner = new Vector(tile.x + r2 * i[0], tile.y + r2 * i[1]);
			if (corner.asPointIntersectsCircle(new Vector(x, y), r)) {
//				System.out.println("eyyy gang gang gang");
				//debugger.strokeWeight(10);
//				Vector test = vel.getOrthogonalComponentTo(new Vector(x,y).subtractN(corner));
//				test.multiply(100f);
//				test.draw(debugger,x,y);
				Vector v = vel.getParallelComponentTo(new Vector(x, y).subtractN(corner));
				if (new Vector(x, y).subtractN(corner).dot(v) > 0)
					v.multiply(-1f);
				//v.multiplyN(100).draw(debugger, x, y);
				x -= v.x;
				y -= v.y;
			}
		}

	}

}
