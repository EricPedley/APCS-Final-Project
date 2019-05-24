package game;
import processing.core.PApplet;
import processing.core.PImage;


enum State {
	WANDERING,ATTACKING,IDLING;
}
public class Enemy extends  Character {
	private State state;
	private int framesWandered;
	private int framesWithoutAttacking;
	
	public Enemy(float x, float y, PImage img, int hp) {
		super(x, y, img,hp);
		// TODO Auto- generated constructor stub
	}
	
	public void updateState() {
		if(state==State.WANDERING||state==State.IDLING) {
			
		}
	}

	public void attack(Level l) {
		Projectile p = new Projectile(0, 0,ImageLoader.Projectile, new Vector(l.getPlayer().x-x,l.getPlayer().y-y));
		//p.scale(0.05f);
		p.x=x;
		p.y=y;
		p.vel.scaleMagnitudeTo(20f);
		l.getEnemyProjectiles().add(p);
	}
	
	@Override
	public void draw(PApplet drawer) {
		super.draw(drawer);
		drawer.fill(255,0,0,10);
		drawer.ellipseMode(PApplet.CENTER);
		drawer.stroke(255,0,0);
		drawer.ellipse(x, y,1000, 1000);
		drawer.stroke(0);
	}

	public void act(Level l) {
		framesWandered++;
		framesWithoutAttacking++;
		if (framesWandered > 60) {
			vel = new Vector(0, 5);
			vel.rotate((float) (Math.random() * Math.PI * 2));
			framesWandered = 0;
		}
		if(framesWithoutAttacking>45&&this.distance(l.getPlayer())<500) {
			vel = new Vector();
			attack(l);
			framesWithoutAttacking=0;
		}
	}

}
