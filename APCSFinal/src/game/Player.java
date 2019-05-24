package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {

	private int meleeAnimationFrames, deflectCount, dashFrames, timeFrames;
	private float meleeAttackDirectionAngle;
	private final int maxMeleeFrames = 10;
	private Sprite meleeSword;
	public boolean isDeflecting, isDashing, inMeleeAttack, inBulletTime;
	private PImage otherImage;
	private final int deflectMaxFrames = 240;
	private final int dashMaxFrames = 120, timeMaxFrames = 240;
	private Vector dashVector;
	private float dashDist;

	public Player(float x, float y, int hp) {
		super(x, y, ImageLoader.Player, hp);
		hitboxMode = 1;
		meleeSword = new Sprite(x, y, ImageLoader.Player_Sword);
		meleeSword.scale(2f);
		hp = 10;
		deflectCount = deflectMaxFrames;
		dashFrames = dashMaxFrames;
		timeFrames = timeMaxFrames;
		maxSpeed = 6;
	}

	public int getMeleeDmg() {
		if (isDashing) {
			return 20;
		} else {
			return 10;
		}
	}

	public void startMeleeAnimation(float angle) {
		if (!inMeleeAttack && !isDeflecting) {
			meleeAttackDirectionAngle = angle;
			meleeAnimationFrames = 0;
			inMeleeAttack = true;
		}
	}

	public void startDeflecting() {

		if (deflectCount >= deflectMaxFrames) {
			isDeflecting = true;
		}
	}

	public void draw(PApplet drawer) {
		if (inMeleeAttack) {
			drawMelee(drawer);
		}
		if (isDeflecting) {
			changeImage(ImageLoader.Player_Deflecting);
			deflectCount -= 2;
		} else {
			changeImage(ImageLoader.Player);
			if (deflectCount < deflectMaxFrames)
				deflectCount++;
		}
		if (deflectCount <= 0) {
			isDeflecting = false;
		}
		if (isDashing) {
			dashFrames -= dashMaxFrames / 10;
		} else {
			if (dashFrames < dashMaxFrames)
				dashFrames++;
		}
		if (inBulletTime) {
			timeFrames -= timeMaxFrames / 80;
		} else {
			if (timeFrames < timeMaxFrames)
				timeFrames++;
		}
		if (timeFrames <= 0) {
			inBulletTime = false;
		}

		if (dashFrames <= 0) {
			isDashing = false;
		}
		drawer.noFill();
		drawer.rect(x - 20, y + height / 2 + 10, 40, 10);
		drawer.rect(x - 20, y + height / 2 + 20, 40, 10);
		drawer.rect(x - 20, y + height / 2 + 30, 40, 10);
		drawer.fill(0, 255, 255);
		drawer.rect(x - 20, y + height / 2 + 10, 40 * deflectCount / deflectMaxFrames, 10);
		drawer.fill(255, 130, 0);
		drawer.rect(x - 20, y + height / 2 + 20, 40 * dashFrames / dashMaxFrames, 10);
		drawer.fill(255, 0, 255);
		drawer.rect(x - 20, y + height / 2 + 30, 40 * timeFrames / timeMaxFrames, 10);
		super.draw(drawer);
	}

	private void drawMelee(PApplet drawer) {
		float a = meleeAttackDirectionAngle;
		float m = meleeAnimationFrames;
		float startPos = PApplet.PI / 2;
		float endPos = -PApplet.PI / 2;
		// drawer.line(x, y, x+200*PApplet.cos(a), y+200*PApplet.sin(a));
		// drawer.line(x,y,x+200*PApplet.cos(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2),y+200*PApplet.sin(PApplet.PI*m/maxMeleeFrames+a-PApplet.PI/2));
		meleeAnimationFrames++;
		if (m > maxMeleeFrames)
			inMeleeAttack = false;
		// meleeSword = new
		// Sprite(0,0,drawer.loadImage("resources\\images\\Hero_Sword.gif"));
		// meleeSword.scale(2f);
		float angle = PApplet.PI * -m / maxMeleeFrames + a + PApplet.PI / 2;
		meleeSword.x = x + meleeSword.getWidth() / 2 * PApplet.cos(angle);
		meleeSword.y = y + meleeSword.getWidth() / 2 * PApplet.sin(angle);
		meleeSword.angle = angle;
		meleeSword.draw(drawer);
	}

	public float getMeleeCurrentAngle() {
		return PApplet.PI * meleeAnimationFrames / maxMeleeFrames + meleeAttackDirectionAngle - PApplet.PI / 2;
	}

	public Sprite getMeleeHitbox() {
		return meleeSword;
	}

	public boolean isMeleeing() {
		return inMeleeAttack;
	}

	@Override
	public void updatePos(Level l) {

		if (isDashing) {
			if (dashVector.length() * (dashMaxFrames - dashFrames) / 10 >= dashDist) {
				isDashing = false;
			}
			vel = dashVector;
		}

		if (inBulletTime)
			vel.add(acc.multiplyN(1.5f));
		else
			vel.add(acc);
		if (maxSpeed > 0 && vel.length() > maxSpeed && !isDashing)
			vel.scaleMagnitudeTo(maxSpeed);
		if (vel.length() > 0) {
			Vector desiredDirection = (isDashing) ? dashVector : acc;
			Vector orth = vel.getOrthogonalComponentTo(desiredDirection);
			vel.subtract(orth.multiplyN(0.5f));
			this.angle = vel.getAngle();
		}
		if (inBulletTime) {
			x += 1.5 * vel.x;
			y += 1.5 * vel.y;
		} else {
			x += vel.x;
			y += vel.y;
		}

		super.checkTileCollisions(l);
	}

	public void dash(Vector direction) {
		if (!isDashing && dashFrames >= dashMaxFrames/2) {
			isDashing = true;
			dashVector = new Vector(direction.x, direction.y);
			dashDist = dashVector.length();
			dashVector.scaleMagnitudeTo(30f);
		}
	}

	public void timeSlow() {
		if (!inBulletTime && timeFrames >= timeMaxFrames) {
			inBulletTime = true;
		}
	}

	public float timeSlowBarPercentage() {
		return (float) timeFrames / timeMaxFrames;
	}

	@Override
	public void handleTileCollisions(Sprite tile) {// simpler collisions method because tiles are
		if(inBulletTime) {
		// always axis-aligned(at 90deg angles) and
		// square
		float r = width / 2;
		float r2 = tile.width / 2;
		if (x + r >= tile.x - r2 && y >= tile.y - r2 && y <= tile.y + r2 && x - r <= tile.x + r2) {
			x -= 1.5*vel.x;
		}
		if (y + r >= tile.y - r2 && x >= tile.x - r2 && x <= tile.x + r2 && y - r <= tile.y + r2) {
			y -= 1.5*vel.y;
		}
		int[][] transformations = { { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 } };
		for (int[] i : transformations) {
			Vector corner = new Vector(tile.x + r2 * i[0], tile.y + r2 * i[1]);
			if (corner.asPointIntersectsCircle(new Vector(x, y), r)) {
				Vector v = vel.getParallelComponentTo(new Vector(x, y).subtractN(corner));
				if (new Vector(x, y).subtractN(corner).dot(v) > 0)
					v.multiply(-1f);
				x -= 1.5*v.x;
				y -= 1.5*v.y;
			}
		}
		} else {
			super.handleTileCollisions(tile);
		}

	}
}
