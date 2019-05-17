package game;

import processing.core.PImage;


public class Character extends Sprite{
	private int hp;

	public Character(int hp, int x, int y, PImage img) {
		super(x,y,img);
		this.hp = hp;
	}
	
	public void loseHP() {
		hp--;
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
	
}
