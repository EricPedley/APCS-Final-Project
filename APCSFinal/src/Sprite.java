import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
	protected float x,y,width,height;
	private PImage img;
	
	public Sprite()
	
	public void draw(PApplet drawer) {
		drawer.image(img, x,y,width, height);
	}
}
