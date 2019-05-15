package game;

import processing.core.PApplet;
import processing.core.PImage;



public class Tile {
	
		
	private PImage image;
	private int type;
	private String fs = System.getProperty("file.separator");
	private Tile[] tileIndices = new Tile[20];
		
	public Tile(PImage image, int type ) { 
		this.type = type;
		this.image = image;
	}
		
	public void assignIndices(PApplet p) {
		tileIndices[0] = new Tile(p.loadImage("Images"+fs+"LeftRockWall.gif"),1);
	}	
	public int getType() {
		return type;
	}
		
	public void draw(PApplet p, int size) {
		p.image(image, 0, 0, size, size);
	}
		
}
