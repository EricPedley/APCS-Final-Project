package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {


	private boolean inMeleeAttack;
	private int meleeAnimationFrames,deflectCount;
	private float meleeAttackDirectionAngle;
	private final int maxMeleeFrames=10;
	private Sprite meleeSword;
	public boolean isDeflecting;
	private PImage otherImage;
	private final int deflectFrames=240;
	
	public Player(float x, float y, int hp) {
		super(x, y, ImageLoader.Player,hp);
		hitboxMode=1;
		meleeSword = new Sprite(x,y,ImageLoader.Player_Sword);
		meleeSword.scale(2f);
		hp=10;
		deflectCount=deflectFrames;
		maxSpeed=6;
		// TODO Auto-generated constructor stub
	}

	public void startMeleeAnimation(float angle) {
		if(!inMeleeAttack&&!isDeflecting) {
			meleeAttackDirectionAngle = angle;
			meleeAnimationFrames=0;
			inMeleeAttack=true;
		}
	}
	
	public void startDeflecting() {
		
		if(deflectCount>=deflectFrames) {
			isDeflecting=true;
		}
	}
	
	public void draw(PApplet drawer) {
		if(inMeleeAttack) {
			drawMelee(drawer);
		}
		if(isDeflecting) {
			changeImage(ImageLoader.Player_Deflecting);
			deflectCount-=2;
		} else {
			changeImage(ImageLoader.Player);
			if(deflectCount<deflectFrames)
				deflectCount++;
		}
		if(deflectCount<=0) {
			isDeflecting=false;
		}
		drawer.noFill();
		drawer.rect(x-20, y+height/2+10, 40, 10);
		drawer.fill(0,0,255);
		drawer.rect(x-20, y+height/2+10, 40*deflectCount/deflectFrames, 10);
		super.draw(drawer);
	}
	
	private void drawMelee(PApplet drawer) {
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
	
	public float getMeleeCurrentAngle() {
		return PApplet.PI*meleeAnimationFrames/maxMeleeFrames+meleeAttackDirectionAngle-PApplet.PI/2;
	}
	
	public Sprite getMeleeHitbox() {
		return meleeSword;
	}
	
	public boolean isMeleeing() {
		return inMeleeAttack;
	}
	
	@Override
	public void updatePos(Level l) {
		vel.add(acc);
		
		if(maxSpeed>0&&vel.length()>maxSpeed) 
			vel.scaleMagnitudeTo(maxSpeed);
		if (vel.length() > 0) {
			Vector orth = vel.getOrthogonalComponentTo(acc);
			vel.subtract(orth.multiplyN(0.5f));
			this.angle = vel.getAngle();
		}
		
		x += vel.x;
		y += vel.y;
		
		super.checkTileCollisions(l);
	}
	

}
