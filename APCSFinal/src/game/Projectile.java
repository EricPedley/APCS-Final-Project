package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile extends Sprite {

	public boolean deleteMe = false;
	
	public Projectile(float x, float y, PImage img, Vector velocity) {
		super(x, y, img);
		hitboxMode=0;
		vel=velocity;
	}
	
	@Override
	public void handleTileCollisions(Sprite tile,PApplet debugger) {
		if(tile.intersects(this))
			deleteMe=true;
	}
	
	
}
