package game;
import processing.core.PImage;


enum State {
	WANDERING,ATTACKING,IDLING;
}
public class Enemy extends Sprite {
	private State state;
	public Enemy(float x, float y, PImage img) {
		super(x, y, img);
		// TODO Auto-generated constructor stub
	}
	
	public void updateState() {
		if(state==State.WANDERING||state==State.IDLING) {
			
		}
	}
	
	public void attack(Level l) {
		Projectile p = new Projectile(0,0,null,null);
		//TODO need to code this
	}
	
	public void act() {
		
	}
	
}


