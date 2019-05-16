package game;
import processing.core.PImage;


enum State {
	WANDERING,ATTACKING,IDLING;
}
public class Enemy extends Sprite {
	private State state;
	private int framesWandered;
	
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
		framesWandered++;
		System.out.println(framesWandered);
		if(framesWandered>60) {
			vel=new Vector(0,5);
			vel.rotate((float)(Math.random()*Math.PI*2));
			framesWandered=0;
		}
	}
	
}


