package game;
import processing.core.PApplet;
import processing.core.PImage;


enum State {
	WANDERING,ATTACKING,IDLING;
}
public class Enemy extends  Character {
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

	public void attack(Level l, PApplet drawer) {
		Projectile p = new Projectile(0, 0, drawer.loadImage("resources\\images\\bullet.png"), new Vector(5,0));
		p.scale(0.05f);
		p.x=x;
		p.y=y;
		l.getEnemyProjectiles().add(p);
	}

	public void act(Level l, PApplet drawer) {
		framesWandered++;
		if (framesWandered > 60) {
			vel = new Vector(0, 5);
			vel.rotate((float) (Math.random() * Math.PI * 2));
			framesWandered = 0;
			attack(l,drawer);
		}
	}

}
