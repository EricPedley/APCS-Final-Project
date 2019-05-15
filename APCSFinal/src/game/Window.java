package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Window extends PApplet{

	private boolean[] keys = new boolean[500];
	private static String fS = System.getProperty("file.separator");
	private Player p;
	private float startX = 300, startY = 200;
	
	public void setup() {
		ImageLoader.setUp(this);
		PImage leftImage = loadImage("Images"+fS+"Characters"+fS+"Main Character"+fS+"Main Character Left.gif");
		PImage rightImage = loadImage("Images"+fS+"Characters"+fS+"Main Character"+fS+"Main Character Right.gif");
		p = new Player(startX,startY,m.getActiveRoom());
		p.setHp(10);
		p.setSpeed(5);
		
	}
	
	public void draw() {
		System.out.println("t");
		translate(0,-11);
		handleKeys();
		translate(startX-(float)h.getHitbox().x,startY-(float)h.getHitbox().y);
		Level l = m.getActiveRoom();
		if(p.getHitbox().x<width/2)
			translate((float)(h.getHitbox().x-width/2),0);
		if(p.getHitbox().y<height/2)
			translate(0,(float)(h.getHitbox().y-height/2));
		if(l.getX()*Room.TILE_SIZE-h.getHitbox().x<width/2) {
			translate((float)(-(r.getX()*Room.TILE_SIZE-h.getHitbox().x)+width/2),0);
		}
		if(l.getY()*Room.TILE_SIZE-h.getHitbox().y<height/2) {
			translate(0,(float)(-(r.getY()*Room.TILE_SIZE-h.getHitbox().y)+height/2));
		}
		background(255);
		l.draw(this);
		p.draw(this);
		
	}
	
	public void handleKeys() {
		
		if(keys[65]) {
			p.moveLeft();
		}  if(keys[87]) {
			p.moveUp();
		}  if(keys[68]) {
			p.moveRight();
		}  if(keys[83]) {
			p.moveDown();
		}
	}
	
	public void keyPressed() {
		//System.out.println(keyCode);
		keys[keyCode]=true;
	}
	
	public void keyReleased() {
		keys[keyCode]=false;
	}
	
	public void mouseClicked() {
		p.shoot(-startX+(float)h.getHitbox().x+mouseX, -startY+(float)h.getHitbox().y+mouseY, true);
	}
	
}
