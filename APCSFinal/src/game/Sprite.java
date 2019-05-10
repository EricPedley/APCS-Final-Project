package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x,y, angle;
	protected float width,height;
	private PImage img;
	
	public Sprite(float x, float y,PImage img) {
		this.x=x;
		this.y=y;
		this.img=img;
		width=img.width;
		height=img.height;
		angle=0;
	}
	
	public void draw(PApplet drawer) {
		drawer.imageMode(PApplet.CENTER);
		drawer.pushMatrix();
			drawer.rotate(angle);
			Vector v = new Vector(x,y);
			v.rotate(-angle);
			Vector v2 = new Vector(x,y);
			v2.subtract(v);
			v2.multiply(-1);
			drawer.translate(v2.x, v2.y);
			drawer.image(img, x,y,width,height);
		drawer.popMatrix();
	}
	
	public void scale(float scaleFactor) {
		width*=scaleFactor;
		height*=scaleFactor;
	}
	
	public void move(float dX, float dY) {
		x+=dX;
		y+=dY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public boolean intersects(Sprite other) {
		Vector[] thisEdges = new Vector[2];
		thisEdges[0] = new Vector(width*PApplet.cos(angle),width*PApplet.sin(angle));
		thisEdges[1] = new Vector(height*PApplet.cos(angle),-height*PApplet.sin(angle));
		Vector[][] points = new Vector[2][4];
		Sprite[] sprites  = new Sprite[] {this,other};
		for(int i:new int[]{0,1}) {
			points[i][0] = new Vector(sprites[i].x,sprites[i].y);
			points[i][1] = points[i][0].addN(thisEdges[0]);
			points[i][2] = points[i][0].addN(thisEdges[1]);
			points[i][3] = points[i][0].addN(thisEdges[0]).addN(thisEdges[1]);
		}
		for(int i:new int[] {0,1}) {
			for(int j=0;j<4;j++) {
				
			}
		}
		return false;
	}
	
	
}
