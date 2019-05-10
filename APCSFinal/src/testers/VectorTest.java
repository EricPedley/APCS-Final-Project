package testers;

import game.Vector;
import processing.core.PApplet;

public class VectorTest extends PApplet {
	private Vector v1, v2;
	
	public void settings() {
		size(800,800);
	}
	
	public void setup() {
		v2 = new Vector(0,200);
	}
	
	public void draw() {
		background(255);
		v1 = new Vector(mouseX-width/2,mouseY-height/2);
		stroke(255,0,0);
		v1.draw(this, width/2, height/2);
		stroke(0,255,0);
		v2.draw(this, width/2, height/2);
		stroke(0,0,255);
		v1.getParallelComponentTo(v2).draw(this, width/2, height/2);
		stroke(0);
		line(mouseX,mouseY,0,mouseY);
	}
	
	
	public static void main(String[] args) {
		PApplet.main("testers.VectorTest");
	}
}
