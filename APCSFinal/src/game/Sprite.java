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
		angle=PApplet.PI/4;
	}
	
	public void draw(PApplet drawer) {
		drawer.imageMode(PApplet.CENTER);
		drawer.rectMode(PApplet.CENTER);
		drawer.strokeWeight(10);
		//drawer.image(img, x,y,width,height);
		drawer.pushMatrix();
		drawer.rotate(angle);
			Vector v = new Vector(x,y);
			v.rotate(-angle);
			Vector v2 = new Vector(x,y);
			v2.subtract(v);
			v2.multiply(-1);
			drawer.translate(v2.x, v2.y);
			drawer.image(img, x,y,width,height);
			drawer.point(x, y);
			drawer.noFill();
			drawer.strokeWeight(1);
			drawer.rect(x,y,width,height);
		drawer.popMatrix();
		drawer.strokeWeight(10);
		drawer.point(x, y);
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
	
	
	
	
}
