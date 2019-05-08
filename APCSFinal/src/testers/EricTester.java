package testers;

import game.Sprite;
import processing.core.PApplet;

public class EricTester extends PApplet {
	private Sprite p;
	
	public void settings() {
		size(500,500);
	}
	
	public void setup() {
		p=new Sprite(200,200,loadImage("resources\\images\\duck.png"));
	}
	
	public void draw() {
		background(255);
		p.draw(this);
		p.x=mouseX;
		p.y=mouseY;
	}
	
	public static void main(String[] args) {
		PApplet.main("testers.EricTester");
	}
}
