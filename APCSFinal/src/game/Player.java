package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {

	private int hp;
	private boolean inMeleeAttack;
	private int meleeAnimationFrames;
	private float meleeAttackDirectionAngle;
	private final int maxMeleeFrames=10;
	private Sprite meleeSword;
	public static boolean isDeflecting;
	
	public Player(float x, float y, PImage img, int hp) {
		super(x, y, img,hp);
		hitboxMode=1;
		meleeSword = new Sprite(x,y,img);
		meleeSword.scale(0.1f);
		hp=10;
		// TODO Auto-generated constructor stub
	}

	public void startMeleeAnimation(float angle) {
		meleeAttackDirectionAngle = angle;
		meleeAnimationFrames=0;
		inMeleeAttack=true;
	}
	
	public void draw(PApplet drawer) {
		
		super.draw(drawer);
		if(inMeleeAttack) {
			float a = meleeAttackDirectionAngle;
			float m = meleeAnimationFrames;
			//drawer.line(x, y, x+200*PApplet.cos(a), y+200*PApplet.sin(a));
			//drawer.line(x,y,x+200*PApplet.cos(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2),y+200*PApplet.sin(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2));
			meleeAnimationFrames++;
			if(m>maxMeleeFrames)
				inMeleeAttack=false;
			meleeSword = new Sprite(0,0,drawer.loadImage("resources\\images\\sword.png"));
			meleeSword.x=x+50*PApplet.cos(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2);
			meleeSword.y=y+50*PApplet.sin(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2);
			meleeSword.angle=PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2;
			meleeSword.draw(drawer);
		}
	}
	
	public float getMeleeCurrentAngle() {
		return PApplet.PI*meleeAnimationFrames/maxMeleeFrames+meleeAttackDirectionAngle-PApplet.PI/2;
	}
	
	public Sprite getMeleeHitbox() {
		return meleeSword;
	}
	
	public boolean meleeing() {
		return inMeleeAttack;
	}
	
	public void damage() {
		if(hp<0)
		hp--;
	}
	
	public int getHP() {
		return hp;
	}
}
