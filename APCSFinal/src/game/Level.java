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
	// private Tile[] tileIndices = new Tile[20];
	private ArrayList<Projectile> friendlyProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Enemy> enemies;
	// private Enemy testBaddie;
	public static final int TILE_SIZE = 64;
	private String fs = System.getProperty("file.separator");
	private int numEnemies;
	private Player p;
	private boolean[] keys;
	private float mouseX,mouseY;

	// Constuctors
	public Level(int levelNumber, int numEnemies, PApplet loader) {
		readData("Levels" + fs + "Test_Level.txt");
		p = new Player(200, 200, ImageLoader.Player, 100);
		p.scale(2f);
		friendlyProjectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		Enemy baddie = new Enemy(500, 200,ImageLoader.Enemy , 100);
		baddie.scale(2f);
		enemies.add(baddie);
	}

	/**
	 * Stores the boolean array that represents which keycodes have been pressed in
	 * the window in a field so that they can be responded to in this class
	 * 
	 * Runs just once when the program is initialized
	 * 
	 * @param keys
	 */
	public void passKeysReference(boolean[] keys) {
		this.keys = keys;
	}

	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
		if (mouseButton == 37) {// left mouse button
			p.startMeleeAnimation(new Vector(mouseX - p.x, mouseY - p.y).getAngle());
//			System.out.println("wtf");
//			p.vel=new Vector(mouseX-p.x,mouseY-p.y);
//			p.vel.scaleMagnitudeTo(5f);
//			p.angle=p.vel.getAngle()+PApplet.PI/2;
		}
	}
	//
	public void handleProjectileCollisions() {
		if(Player.isDeflecting) {
			for(int i = 0; i<enemyProjectiles.size();i++) {
				if(p.intersects(enemyProjectiles.get(i))) {
				System.out.println("colliding and deflecting");
					enemyProjectiles.get(i).vel = new Vector(mouseX-enemyProjectiles.get(i).x,mouseY-enemyProjectiles.get(i).y);
					enemyProjectiles.get(i).vel.scaleMagnitudeTo(3);
					friendlyProjectiles.add(enemyProjectiles.remove(i));
				}
			}
		}
		else
		{
			for(int i = 0; i<enemyProjectiles.size();i++) {
				if(p.intersects(enemyProjectiles.get(i))) {
					enemyProjectiles.remove(i);
					p.loseHP();
				}
			}
		}
		for(Enemy e: enemies) {
			for(int i = 0; i<friendlyProjectiles.size();i++) {
				if(e.intersects(friendlyProjectiles.get(i))) {
					friendlyProjectiles.remove(i);
					e.loseHP();
					
				}
			}
		}
	}
	public int[][] getTileArray() {
		return tileType;
	}

	// Methods
	public void assignIndices(PApplet p) {

	}

	/**
	 * 
	 * @param filename
	 */
	private void readData(String filename) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
				reader = new FileReader(dataFile);
				in = new Scanner(reader);
				String s = in.nextLine();
				System.out.println(s);
				x = Integer.parseInt(s.substring(0, s.indexOf(',')));
				s = s.substring(s.indexOf(',') + 1);
				y = Integer.parseInt(s);// s.substring(s.indexOf(',')));
				tileType = new int[x][y];

				while (in.hasNext()) {
					String line = in.nextLine();
					for (int i = 0; line.indexOf(',') != -1; line = line.substring(line.indexOf(',') + 1)) {

						tileType[i][count] = Integer.parseInt(line.substring(0, line.indexOf(',')));
						i++;
						// System.out.println(gameData[i][count]);
					}
					count++;
				}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}

		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
	}

	/**
	 *
	 * @param drawer
	 */
	public void draw(PApplet drawer) {
		this.mouseX = drawer.mouseX;
		this.mouseY = drawer.mouseY;
		Level l = this;
		int[][] tiles = l.getTileArray();
		drawer.noFill();
		drawer.strokeWeight(1);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				drawer.fill(255);
				if (tiles[i][j] == 0)
					drawer.fill(0, 0, 255);
				drawer.rect(i * Level.TILE_SIZE, j * Level.TILE_SIZE, Level.TILE_SIZE, Level.TILE_SIZE);
			}
		} // draws grid overlay to debug stuff cuz tiles arent actually finished yet
		handleKeys();
		p.updatePos(this);
		handleEnemies(drawer);
		p.draw(drawer);
		drawProjectiles(drawer);
		handleMelee();
		handleProjectileCollisions();
	}
	
	public void handleEnemies(PApplet drawer) {
		for(int i=0;i<enemies.size();i++) {
			if(enemies.get(i).isDead())
			{
				enemies.remove(i);
			}
		}
		for (Enemy baddie : enemies) {
			baddie.act(this, drawer);
			baddie.updatePos(this);
			baddie.draw(drawer);
		}
	}
	
	public void drawProjectiles(PApplet drawer) {
		ArrayList<Projectile> toDelete = new ArrayList<Projectile>();
		for (Projectile p : enemyProjectiles) {
			if (p.deleteMe)
				toDelete.add(p);
			else {
				p.updatePos(this);
				p.draw(drawer);
			}
		}
		for (Projectile p : friendlyProjectiles) {
			if (p.deleteMe)
				toDelete.add(p);
			else {
				p.updatePos(this);
				p.draw(drawer);
			}
		}
		for (Projectile p : toDelete) {
			enemyProjectiles.remove(p);
		}
	}

	public void handleKeys() {
		Vector v = new Vector();
		if(keys[81]) {
			Player.isDeflecting = true;
		}
		if(keys[69]) {
			Player.isDeflecting = false;
		}
		if (keys[65]) {
			v.x--;
		}
		if (keys[87]) {
			v.y--;
		}
		if (keys[68]) {
			v.x++;
		}
		if (keys[83]) {
			v.y++;
		}
		if (v.length() > 0) {
			v.scaleMagnitudeTo(1f);
			p.angle = v.getAngle() + PApplet.PI / 2;
		}
		p.vel = v.multiplyN(5f);
	}

	public ArrayList<Projectile> getEnemyProjectiles() {
		return enemyProjectiles;
	}

	public Player getPlayer() {
		return p;
	}

	public void handleMelee() {
		if (p.meleeing()) {
			for (Enemy e : enemies) {
				if (e.intersects(p.getMeleeHitbox())) {
					e.loseHP();
					System.out.println("yipee!");
				}
			}
		}
	}
	/**
	 * Returns -1 for game lost, 0 for game in progress, and 1 for game won
	 * @return the state of the game
	 */
	public int getGameStatus() {
		if(enemies.size()==0)
		{
			return 1;
		}
		else if(p.getHP()<=0)
		{
			return -1;
		}
			
		return 0;
	} 
	/*
	 * public ArrayList<Projectile> getMyProjectiles() { // TODO Auto-generated
	 * method stub return myProjectiles; }
	 */
}
