package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Menu {
//	private ArrayList<String> buttons = new ArrayList<String>();
	private PImage image;
	
	public Menu( PImage img) {
//		buttons=buttonText;
		image=img;
	}
	public void draw(PApplet drawer) {
		drawer.imageMode(PApplet.CORNER);
		drawer.image(image,0,0,drawer.width,drawer.height);
	}
}
