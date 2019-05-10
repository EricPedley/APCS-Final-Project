package testers;

import game.Enemy;
import game.Player;
import game.Sprite;
import processing.core.PApplet;

public class EricTester extends PApplet {
	private Player p;
	private Enemy e;
	
	public void settings() {
		size(700,700);
	}
	
	public void setup() {
		p=new Player(200,200,loadImage("resources\\images\\duck.png"));
		e=new Enemy(200,200,loadImage("resources\\images\\god.png"));
		p.scale(0.25f);
		e.scale(0.5f);
	}
	
	public void draw() {
		background(255);
		p.draw(this);
		e.draw(this);
		if(e.intersects(p,this))
			System.out.println("yayy");
	}
	
	public void mouseClicked() {
		p.melee(atan((mouseY-p.y)/(mouseX-p.x))+((mouseX<p.x)? PI:0));
	}
	
	public void mouseWheel() {
		p.angle+= PApplet.PI/12;
		if(p.angle>PApplet.PI*2)
			p.angle=0;
	}
	
	public void keyPressed() {
		p.x=mouseX;
		p.y=mouseY;
	}
	
	public static void main(String[] args) {
		PApplet.main("testers.EricTester");
	}
}
