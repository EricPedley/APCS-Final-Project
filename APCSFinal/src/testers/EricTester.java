package testers;

import game.ImageLoader;
import game.Player;
import game.Projectile;
import game.Vector;
import processing.core.PApplet;

public class EricTester extends PApplet {
	private Player p;
	private Projectile e;
	
	public void settings() {
		size(700,700);
	}
	
	public void setup() {
		ImageLoader.setup(this);
		p=new Player(300,200,10);
		e=new Projectile(200,200,loadImage("resources\\images\\bullet.gif"),new Vector(0,0));
		e.scale(0.05f);
		p.scale(2f);
	}
	
	public void draw() {
		background(255);
		p.draw(this);
		e.draw(this);
		if(p.intersects(e))
			rect(500,0,100,100);
	}
	
	public void mouseClicked() {
		p.startMeleeAnimation(atan((mouseY-p.y)/(mouseX-p.x))+((mouseX<p.x)? PI:0));
	}
	
	public void mouseWheel() {
		e.angle+= PApplet.PI/12;
		if(e.angle>=PApplet.PI*2)
			e.angle=0;
	}
	
	public void keyPressed() {
		e.x=mouseX;
		e.y=mouseY;
	}
	
	public static void main(String[] args) {
		PApplet.main("testers.EricTester");
	}
}
