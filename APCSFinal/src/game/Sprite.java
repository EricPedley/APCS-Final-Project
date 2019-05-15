package game;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x, y, angle;
	public Vector vel,acc;
	protected float width, height;
	private PImage img;

	public Sprite(float x, float y, PImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		width = img.width;
		height = img.height;
		angle = 0;
		vel=new Vector();
		acc=new Vector();
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

	public void moveBy(float dX, float dY) {
		x += dX;
		y += dY;
	}
	
	public void updatePos(Level l,PApplet debugger) {
		vel.add(acc);
		int[][] tiles = l.getTileArray();
		debugger.noFill();
		debugger.strokeWeight(1);
		for(int i=0;i<45;i++) {
			for(int j=0;j<11;j++) {
				debugger.fill(255);
				if(tiles[i][j]==0)
					debugger.fill(0,0,255);
				debugger.rect(i*Level.TILE_SIZE, j*Level.TILE_SIZE, Level.TILE_SIZE, Level.TILE_SIZE);
			}
		}
		debugger.fill(0);
		ArrayList<Vector> scaledCoords = getCoordsScaledToTiles();
//		for(Vector v: scaledCoords) {
//			debugger.rect(v.x*Level.TILE_SIZE,v.y*Level.TILE_SIZE,Level.TILE_SIZE,Level.TILE_SIZE);
//		}
		Vector v = scaledCoords.get(4);
		Vector[] transformations = {new Vector(-1,-1),new Vector(0,-1),new Vector(1,-1),new Vector(-1,0),new Vector(1,0),new Vector(-1,1),new Vector(0,1),new Vector(1,1)};
		for(int i=0;i<8;i++) {
			Vector v2 = v.addN(transformations[i]);
			if(tiles[(int) v2.x][(int) v2.y]==0) {
				debugger.rect(v2.x*Level.TILE_SIZE, v2.y*Level.TILE_SIZE, Level.TILE_SIZE, Level.TILE_SIZE);
			}
		}
		debugger.rect(v.x*Level.TILE_SIZE,v.y*Level.TILE_SIZE,Level.TILE_SIZE,Level.TILE_SIZE);

//		Vector[] topTiles = new Vector[] {scaledCoords.get(0),null,null};
//		for(Vector v: scaledCoords) {
//			if(v.y<topTiles[0].y) {
//				topTiles[0]=v;
//				topTiles[1]=null;
//				topTiles[2]=null;
//			} else if(v.y==topTiles[0].y) {
//				if(topTiles[1]==null)
//					topTiles[1]=v;
//				else {
//					topTiles[2]=v;
//				}
//			}
//		}
//		debugger.fill(255,0,0);
//		for(Vector v: topTiles) {
//			if(v!=null)
//				debugger.rect(v.x*Level.TILE_SIZE,v.y*Level.TILE_SIZE,Level.TILE_SIZE,Level.TILE_SIZE);
//		}

		x+=vel.x;
		y+=vel.y;
	}
	
	public ArrayList<Vector> getCoordsScaledToTiles() {
		Vector[] edges = new Vector[2];
		edges[0] = new Vector(width * PApplet.cos(angle), width * PApplet.sin(angle));
		edges[1] = new Vector(-height * PApplet.sin(angle), height * PApplet.cos(angle));
		Vector[] points = new Vector[5];
		float l = (float) (Math.sqrt(width * width + height * height))
				/ 2;// length of half the diagonal of this rectangle
		float a = -PApplet.atan(height / width);
		points[0] = new Vector(x - l * PApplet.cos(a - angle),
				y + l * PApplet.sin(a - angle));
		points[1] = points[0].addN(edges[0]);
		points[2] = points[0].addN(edges[1]);
		points[3] = points[0].addN(edges[0]).addN(edges[1]);
		points[4] = new Vector(x,y);
		ArrayList<Vector> result = new ArrayList<Vector>();
		for(Vector v: points) {
			v.x=(int)(v.x/Level.TILE_SIZE);
			v.y=(int)(v.y/Level.TILE_SIZE);
			result.add(v);
		}
		return result;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean intersects(Sprite other) {
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
			//drawer.stroke(255,0,0);
			//points[i][0].draw(drawer);
			points[i][1] = points[i][0].addN(edges[0 + 2 * i]);
			//drawer.stroke(255,255,0);
			//points[i][1].draw(drawer);
			points[i][2] = points[i][0].addN(edges[1 + 2 * i]);
			//drawer.stroke(0,0,255);
			//points[i][2].draw(drawer);
			points[i][3] = points[i][0].addN(edges[0+ 2 * i]).addN(edges[1+ 2 * i]);
			//drawer.stroke(0,255,255);
			//points[i][3].draw(drawer);
		}
		//int count =0;
		for (Vector v : edges) {// for each edge of both rectangles
			//v = v.rotateN(PApplet.PI / 2);// v is the perpendicular vector to the edge
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
//			drawer.strokeWeight(20);
//			drawer.stroke(255,0,0);
//			drawer.point(maxThis, 200+count);
//			drawer.stroke(255,255,0);
//			drawer.point(minThis, 200+count);
//			drawer.stroke(0,0,255);
//			drawer.point(maxOther, 200+count);
//			drawer.stroke(0,255,255);
//			drawer.point(minOther, 200+count);
//			count+=100;
			if (!(Math.min(maxThis, maxOther) >= Math.max(minThis, minOther)))
				return false;
		}
		return true;
	}
	

		
	
}
