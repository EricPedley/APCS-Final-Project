package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x,y;
	protected float width,height;
	private PImage img;
	
	public Sprite(float x, float y,PImage img) {
		this.x=x;
		this.y=y;
		this.img=img;
		width=img.width;
		height=img.height;
	}
	
	public void draw(PApplet drawer) {
		drawer.image(img, x,y,width,height);
	}
	
	public void scale(float scaleFactor) {
		width*=scaleFactor;
		height*=scaleFactor;
	}
	
	public void move(float dX, float dY) {
		x+=dX;
		y+=dY;
	}
	
	public void melee(float directionAngle) {
		
	}
}
