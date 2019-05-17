package game;
import processing.core.PImage;


enum State {
	WANDERING,ATTACKING,IDLING;
}
public class Enemy extends  Character {
	private State state;
	private int framesWandered;
	
	public Enemy(float x, float y, PImage img, int hp) {
		super(hp,x, y, img);
		// TODO Auto-generated constructor stub
	}
	
	public void updateState() {
		if(state==State.WANDERING||state==State.IDLING) {
			
		}
	}
	
	public void attack(Level l) {
		Projectile p = new Projectile(0,0,null,null);
		
		l.getEnemyProjectiles().add(p);
	}
	
	public void act(Level l) {
		framesWandered++;
		if(framesWandered>60) {
			vel=new Vector(0,5);
			vel.rotate((float)(Math.random()*Math.PI*2));
			framesWandered=0;
		}
	}
	
}


