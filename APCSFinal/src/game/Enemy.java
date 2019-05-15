package game;
import processing.core.PImage;

public class Enemy extends Sprite {

	public Enemy(float x, float y, PImage img) {
		super(x, y, img);
		// TODO Auto-generated constructor stub
	}
	
	public void attack(Level l) {
		Projectile p = new Projectile(0,0,null,null);
		//TODO need to code this
	}

}
