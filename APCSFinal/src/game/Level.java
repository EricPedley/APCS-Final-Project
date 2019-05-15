package game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class Level {
	private int[][] tileType;
	private int x = 42, y = 15;
	//private Tile[] tileIndices = new Tile[20];
	private ArrayList<Projectile> myProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Enemy> enemies;
	public static final int TILE_SIZE = 64; 
	private String fs = System.getProperty("file.separator");
	private int numEnemies;
	private Player p;
	private boolean[] keys;
	
	//Constuctors
	public Level(int levelNumber, int numEnemies,PApplet loader) {
		p= new Player(200,200,loader.loadImage("resources"+fs+"images"+fs+"knight.png"));
		p.scale(0.1f);
		myProjectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		PImage[] enemyImages = {};
	}
	
	/**
	 * Stores the boolean array that represents which keycodes have been pressed in the window in a field so that 
	 * they can be responded to in this class
	 * 
	 * Runs just once when the program is initialized
	 * @param keys
	 */
	public void passKeysReference(boolean[] keys) {
		this.keys=keys;
	}
	
	public int[][] getTileArray() {
		return tileType;
	}
	
	public void setPlayer(Player p) {
		this.p = p;
	}
	
	// Methods
	public void assignIndices(PApplet p) {
		
	}


	

	public void draw(PApplet drawer) {
		
		handleKeys();
		p.updatePos(this,drawer);
		p.draw(drawer);
	}
	
	public void handleKeys() {
		Vector v=new Vector();
		if(keys[65]) {
			v.x--;
		}
		if(keys[87]) {
			v.y--;
		}
		if(keys[68]) {
			v.x++;
		}
		if(keys[83]) {
			v.y++;
		}
		if(v.length()>0) {
			v.scaleMagnitudeTo(1f);
			p.angle=v.getAngle()+PApplet.PI/2;
		}
		p.vel=v.multiplyN(5f);
	}
	
	
	public Player getPlayer() {
		return p;
	}
	

/*	public ArrayList<Projectile> getMyProjectiles() {
		// TODO Auto-generated method stub
		return myProjectiles;
	}*/
}

