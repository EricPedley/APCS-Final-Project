package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile extends Sprite {

	
	public Projectile(float x, float y, PImage img, Vector velocity) {
		super(x, y, img);
		hitboxMode=0;
		vel=velocity;
	}
	
	
}
