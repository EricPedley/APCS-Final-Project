package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	public float x,y;
	protected float width,height;
	private PImage img;
	private boolean inMeleeAttack;
	private int meleeAnimationFrames;
	private float meleeAttackDirectionAngle;
	
	public Sprite(float x, float y,PImage img) {
		this.x=x;
		this.y=y;
		this.img=img;
		width=img.width;
		height=img.height;
	}
	
	public void draw(PApplet drawer) {
		drawer.image(img, x,y,width,height);
		if(inMeleeAttack) {
			int maxFrames = 10;
			float a = meleeAttackDirectionAngle;
			float m = meleeAnimationFrames;
			float cx = x+width/2f;
			float cy = y+height/2f;
			drawer.line(cx, cy, cx+200*PApplet.cos(a), cy+200*PApplet.sin(a));
			drawer.line(cx,cy,cx+200*PApplet.cos(PApplet.PI*m/maxFrames+a-PApplet.PI/2),cy+200*PApplet.sin(PApplet.PI*m/maxFrames+a-PApplet.PI/2));
			meleeAnimationFrames++;
			if(m>maxFrames)
				inMeleeAttack=false;
		}
	}
	
	public void scale(float scaleFactor) {
		width*=scaleFactor;
		height*=scaleFactor;
	}
	
	public void move(float dX, float dY) {
		x+=dX;
		y+=dY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void melee(float angle) {
		meleeAttackDirectionAngle = angle;
		meleeAnimationFrames=0;
		inMeleeAttack=true;
	}
	
	
}
