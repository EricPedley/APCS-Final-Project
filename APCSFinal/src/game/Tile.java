package game;

import processing.core.PApplet;
import processing.core.PImage;


/**
 * Represents a tile of the background or walls of the level in the game
 * @author rkoka209
 *
 */
public class Tile {
	
		
	private PImage image;
	private int type;
		
	public Tile(PImage image, int type ) { 
		this.type = type;
		this.image = image;
	}
	
	public int getType() {
		return type;
	}
		
	public void draw(PApplet p, float x, float y, int size) {
		p.image(image, x+size/2, y+size/2, size, size);
	}
		
}
