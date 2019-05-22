package game;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageLoader {
	
	private static String fs = System.getProperty("file.separator");
	public static PImage Enemy,Player,Projectile,Player_Sword,Player_Deflecting; 
	
	public static void setup(PApplet p) {
		
		Enemy = p.loadImage("resources" + fs + "images" + fs + "Enemy.gif");
		Player = p.loadImage("resources" + fs + "images" + fs + "Hero.gif");
		Player_Sword = p.loadImage("resources" + fs + "images" + fs + "Hero_Sword.gif");
		Player_Deflecting = p.loadImage("resources" + fs + "images" + fs + "Hero_Deflecting.gif");
	
	
	}
}