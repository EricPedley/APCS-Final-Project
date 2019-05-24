package game;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Superclass for sprites that have health; player and enemies
 * @author epedley885
 *
 */
public class Character extends Sprite{
	private int hp;
	private final int maxHp;
	
	public Character(float x, float y, PImage img,int hp) {
		super(x,y,img);
		this.hp = hp;
		maxHp=hp;
	}
	
	public void loseHP(int dmg) {//push pls again
		
		hp-=dmg;
	}
	
	public void addHP(int heal) {
		hp+=heal;
		if(hp>maxHp)
			hp=maxHp;
	}
	
	public boolean isDead() {
		if(hp <= 0)
			return true;
		else
			return false;
	}
	
	public int getHP() {
		return hp;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void draw(PApplet drawer) {
		super.draw(drawer);
		drawer.rect(x-25, y-height-20, 50, 10);
		drawer.fill(0,255,0);
		drawer.rect(x-25,y-height-20,50*hp/maxHp,10);
	}
}
