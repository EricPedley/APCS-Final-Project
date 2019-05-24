package game;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageLoader {
	
	private static String fs = System.getProperty("file.separator");
	public static PImage Enemy,Player,Projectile,Player_Sword,Player_Deflecting; 
	public static Tile[] tileIndices = new Tile[6];

	
	public static void setup(PApplet p) {
		Enemy = p.loadImage("resources" + fs + "images" + fs + "Enemy.gif");
		Player = p.loadImage("resources" + fs + "images" + fs + "Hero.gif");
		Player_Sword = p.loadImage("resources" + fs + "images" + fs + "Hero_Sword.gif");
		Player_Deflecting = p.loadImage("resources" + fs + "images" + fs + "Hero_Deflecting.gif");
		Projectile = p.loadImage("resources" + fs + "images" + fs + "Projectile.gif");
		tileIndices[0] = new Tile(p.loadImage("resources" + fs + "images" + fs + "Wall.gif"),0);
		tileIndices[1] = new Tile(p.loadImage("resources" + fs + "images" + fs + "Floor.gif"),1);
<<<<<<< HEAD
		tileIndices[2] = new Tile(p.loadImage("resources" + fs + "images" + fs + "BottomLeft.gif"),2);
		tileIndices[3] = new Tile(p.loadImage("resources" + fs + "images" + fs + "BottomRight.gif"),3);
=======
>>>>>>> branch 'master' of https://github.com/EricPedley/APCS-Final-Project.git
		tileIndices[4] = new Tile(p.loadImage("resources" + fs + "images" + fs + "TopLeft.gif"),4);
		tileIndices[5] = new Tile(p.loadImage("resources" + fs + "images" + fs + "TopRight.gif"),5);
		tileIndices[2] = new Tile(p.loadImage("resources" + fs + "images" + fs + "BottomRight.gif"),3);
		tileIndices[3] = new Tile(p.loadImage("resources" + fs + "images" + fs + "BottomLeft.gif"),2);
		
	}
}