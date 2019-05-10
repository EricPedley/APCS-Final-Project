package testers;

import game.Player;
import game.Sprite;
import processing.core.PApplet;

public class EricTester extends PApplet {
	private Player p;
	
	public void settings() {
		size(500,500);
	}
	
	public void setup() {
		p=new Player(200,200,loadImage("resources\\images\\duck.png"));
		p.scale(0.25f);
	}
	
	public void draw() {
		background(255);
		p.draw(this);
	}
	
	public void mouseClicked() {
		p.melee(atan((mouseY-p.y)/(mouseX-p.x))+((mouseX<p.x)? PI:0));
	}
	
	public void keyPressed() {
		p.angle+= PApplet.PI/12;
		if(p.angle>PApplet.PI*2)
			p.angle=0;
		p.x=mouseX;
		p.y=mouseY;
	}
	
	public static void main(String[] args) {
		PApplet.main("testers.EricTester");
	}
}
