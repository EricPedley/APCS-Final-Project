package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {


	private boolean inMeleeAttack;
	private int meleeAnimationFrames,deflectCount,deflectRchrg;
	private float meleeAttackDirectionAngle;
	private final int maxMeleeFrames=10;
	private Sprite meleeSword;
	public boolean isDeflecting;
	private PImage otherImage;
	
	
	public Player(float x, float y, int hp) {
		super(x, y, ImageLoader.Player,hp);
		hitboxMode=1;
		meleeSword = new Sprite(x,y,ImageLoader.Player_Sword);
		meleeSword.scale(2f);
		hp=10;
		// TODO Auto-generated constructor stub
	}

	public void startMeleeAnimation(float angle) {
		meleeAttackDirectionAngle = angle;
		meleeAnimationFrames=0;
		inMeleeAttack=true;
	}
	
	public void startDeflecting() {
		if(deflectRchrg==0) {
			isDeflecting=true;
			deflectCount=200;
		}
	}
	
	public void draw(PApplet drawer) {
		
		if(inMeleeAttack) {
			float a = meleeAttackDirectionAngle;
			float m = meleeAnimationFrames;
			float startPos = PApplet.PI/2;
			float endPos = -PApplet.PI/2;
			//drawer.line(x, y, x+200*PApplet.cos(a), y+200*PApplet.sin(a));
			//drawer.line(x,y,x+200*PApplet.cos(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2),y+200*PApplet.sin(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2));
			meleeAnimationFrames++;
			if(m>maxMeleeFrames)
				inMeleeAttack=false;
			//meleeSword = new Sprite(0,0,drawer.loadImage("resources\\images\\Hero_Sword.gif"));
			//meleeSword.scale(2f);
			float angle = PApplet.PI*-m/maxMeleeFrames+a+PApplet.PI/2;
			meleeSword.x=x+meleeSword.getWidth()/2*PApplet.cos(angle);
			meleeSword.y=y+meleeSword.getWidth()/2*PApplet.sin(angle);
			meleeSword.angle=angle;
			meleeSword.draw(drawer);
		}
		if(isDeflecting)
			changeImage(ImageLoader.Player_Deflecting);
		else
			changeImage(ImageLoader.Player);
		super.draw(drawer);
	}
	
	public float getMeleeCurrentAngle() {
		return PApplet.PI*meleeAnimationFrames/maxMeleeFrames+meleeAttackDirectionAngle-PApplet.PI/2;
	}
	
	public Sprite getMeleeHitbox() {
		return meleeSword;
	}
	
	public boolean isMeleeing() {
		return inMeleeAttack;
	}
	

}
