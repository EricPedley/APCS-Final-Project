package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {

	private int meleeAnimationFrames,deflectCount,dashFrames;
	private float meleeAttackDirectionAngle;
	private final int maxMeleeFrames=10;
	private Sprite meleeSword;
	public boolean isDeflecting,isDashing,inMeleeAttack;
	private PImage otherImage;
	private final int deflectMaxFrames=240;
	private final int dashMaxFrames = 120;
	private Vector dashVector;
	
	public Player(float x, float y, int hp) {
		super(x, y, ImageLoader.Player,hp);
		hitboxMode=1;
		meleeSword = new Sprite(x,y,ImageLoader.Player_Sword);
		meleeSword.scale(2f);
		hp=10;
		deflectCount=deflectMaxFrames;
		dashFrames=dashMaxFrames;
		maxSpeed=6;
	}

	public void startMeleeAnimation(float angle) {
		if(!inMeleeAttack&&!isDeflecting) {
			meleeAttackDirectionAngle = angle;
			meleeAnimationFrames=0;
			inMeleeAttack=true;
		}
	}
	
	public void startDeflecting() {
		
		if(deflectCount>=deflectMaxFrames) {
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
			if(deflectCount<deflectMaxFrames)
				deflectCount++;
		}
		if(deflectCount<=0) {
			isDeflecting=false;
		}
		if(isDashing) {
			dashFrames-=dashMaxFrames/10;
		} else {
			if(dashFrames<dashMaxFrames)
				dashFrames++;
		}
		
		if(dashFrames<=0) {
			isDashing=false;
		}
		drawer.noFill();
		drawer.rect(x-20, y+height/2+10, 40, 10);
		drawer.rect(x-20, y+height/2+20, 40, 10);
		drawer.fill(0,255,255);
		drawer.rect(x-20, y+height/2+10, 40*deflectCount/deflectMaxFrames, 10);
		drawer.fill(255,130,0);
		drawer.rect(x-20, y+height/2+20, 40*dashFrames/dashMaxFrames, 10);
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
		if(isDashing) {
			vel=dashVector;
		}
		vel.add(acc);
		System.out.println(vel);
		if(maxSpeed>0&&vel.length()>maxSpeed&&!isDashing) 
			vel.scaleMagnitudeTo(maxSpeed);
		if (vel.length() > 0) {
			Vector desiredDirection = (isDashing)? dashVector:acc;
			Vector orth = vel.getOrthogonalComponentTo(desiredDirection);
			vel.subtract(orth.multiplyN(0.5f));
			this.angle = vel.getAngle();
		}
		x += vel.x;
		y += vel.y;
		
		super.checkTileCollisions(l);
	}
	
	public void dash(Vector direction) {
		if(!isDashing&&dashFrames>=dashMaxFrames) {
			isDashing=true;
			dashVector = new Vector(direction.x,direction.y);
			dashVector.scaleMagnitudeTo(30f);
		}
	}
	

}
