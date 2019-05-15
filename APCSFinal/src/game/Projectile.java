package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile extends Sprite {

	public Vector v;//Velocity
	
	public Projectile(float x, float y, PImage img, Vector velocity) {
		super(x, y, img);
		v=velocity;
	}
	
	public void updatePos() {
		x+=v.x;
		y+=v.y;
		angle=v.getAngle();
	}
	
	
}
