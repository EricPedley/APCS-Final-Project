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
		p.scale(0.25f);
	}
	
	public void draw() {
		background(255);
		p.draw(this);
		noFill();
		rect(p.x,p.y,p.getWidth(),p.getHeight());
	}
	
	public void mouseClicked() {
		p.melee(atan((mouseY-p.y)/(mouseX-p.x))+((mouseX<p.x)? PI:0));
	}
	
	public void keyPressed() {
		p.x=mouseX;
		p.y=mouseY;
	}
	
	public static void main(String[] args) {
		PApplet.main("testers.EricTester");
	}
}
