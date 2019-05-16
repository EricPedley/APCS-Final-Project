package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Sprite {

	private boolean inMeleeAttack;
	private int meleeAnimationFrames;
	private float meleeAttackDirectionAngle;
	public Player(float x, float y, PImage img) {
		super(x, y, img);
		hitboxMode=1;
		// TODO Auto-generated constructor stub
	}

	public void melee(float angle) {
		meleeAttackDirectionAngle = angle;
		meleeAnimationFrames=0;
		inMeleeAttack=true;
	}
	
	public void draw(PApplet drawer) {
		super.draw(drawer);
		if(inMeleeAttack) {
			int maxFrames = 10;
			float a = meleeAttackDirectionAngle;
			float m = meleeAnimationFrames;
			drawer.line(x, y, x+200*PApplet.cos(a), y+200*PApplet.sin(a));
			drawer.line(x,y,x+200*PApplet.cos(PApplet.PI*m/maxFrames+a-PApplet.PI/2),y+200*PApplet.sin(PApplet.PI*m/maxFrames+a-PApplet.PI/2));
			meleeAnimationFrames++;
			if(m>maxFrames)
				inMeleeAttack=false;
		}
	}
}
