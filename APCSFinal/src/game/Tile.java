package game;

import processing.core.PApplet;
import processing.core.PImage;



public class Tile {
	
		
	private PImage image;
	private int type;
	private String fs = System.getProperty("file.separator");
		
	public Tile(PImage image, int type ) { 
		this.type = type;
		this.image = image;
	}
		
	/*public void assignIndices(PApplet p) {
		tileIndices[0] = new Tile(p.loadImage("resources"+fs+"images"+fs+"Tiles-1.png"),1);
		tileIndices[1] = new Tile(p.loadImage("resources"+fs+"images"+fs+"Tiles-2.png"),1);
		tileIndices[2] = new Tile(p.loadImage("resources"+fs+"images"+fs+"Tiles-3.png"),1);
		tileIndices[3] = new Tile(p.loadImage("resources"+fs+"images"+fs+"Tiles-4.png"),1);

	}	*/
	public int getType() {
		return type;
	}
		
	public void draw(PApplet p, float x, float y, int size) {
		p.image(image, x+size/2, y+size/2, size, size);
	}
		
}
