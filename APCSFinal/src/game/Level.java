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
	private float mouseX, mouseY;
	private float startX = 650, startY = 350;

	// Constuctors
	public Level(int levelNumber, int numEnemies, PApplet loader) {
		if (levelNumber == 1) {
			readData("Levels" + fs + "level" + levelNumber + ".txt");
		} else
			readData("Levels" + fs + "Test_Level.txt");
		p = new Player(200, 200, 100);
		p.scale(2f);
		p.isDeflecting = false;
		friendlyProjectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < numEnemies; i++) {
			Enemy baddie = new Enemy(500, 200, ImageLoader.Enemy, 100);
			baddie.scale(2f);
			enemies.add(baddie);
		}
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

	public void mouseClicked(int mouseButton) {
		if (mouseButton == 37) {// left mouse button
			p.startMeleeAnimation(new Vector(mouseX - p.x, mouseY - p.y).getAngle());
		}
	}

	//
	public void handleProjectileCollisions() {
		if (p.isDeflecting) {
			for (int i = 0; i < enemyProjectiles.size(); i++) {
				if (p.intersects(enemyProjectiles.get(i))) {
					// System.out.println("wtf");
					enemyProjectiles.get(i).vel = new Vector(mouseX - enemyProjectiles.get(i).x,
							mouseY - enemyProjectiles.get(i).y);
					enemyProjectiles.get(i).vel.scaleMagnitudeTo(20f);
					friendlyProjectiles.add(enemyProjectiles.remove(i));
				}
			}
		} else {
			for (int i = 0; i < enemyProjectiles.size(); i++) {
				if (p.intersects(enemyProjectiles.get(i))) {
					enemyProjectiles.remove(i);
					p.loseHP(35);
				}
			}
		}
		for (Enemy e : enemies) {
			for (int i = 0; i < friendlyProjectiles.size(); i++) {
				if (e.intersects(friendlyProjectiles.get(i))) {
					friendlyProjectiles.remove(i);
					e.loseHP(35);

				}
			}
		}
	}

	public int[][] getTileArray() {
		return tileType;
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
		drawer.background(255);
		Vector trans = new Vector(0,-20);//moves the camera to be centered on the player but not go past the edges of the map
		trans.add(new Vector(startX - (float) p.x, startY - (float) p.y));
		if (p.x < drawer.width / 2)
			trans.add(new Vector((float) (p.x - drawer.width / 2), 0));
		if (p.y < drawer.height / 2)
			trans.add(new Vector(0, (float) (p.y - drawer.height / 2)));
		if (tileType.length * TILE_SIZE - p.x < drawer.width / 2) 
			trans.add(new Vector((float) (-(tileType.length * TILE_SIZE - p.x) + drawer.width / 2), 0));
		if (tileType[0].length * TILE_SIZE - p.y < drawer.height / 2) 
			trans.add(new Vector(0, (float) (-(tileType[0].length * TILE_SIZE - p.y) + drawer.height / 2)));
		this.mouseX = drawer.mouseX-trans.x;
		this.mouseY = drawer.mouseY-trans.y;
		drawer.translate(trans.x, trans.y);
		
		Level l = this;
		int[][] tiles = l.getTileArray();
		drawer.noFill();
		drawer.strokeWeight(1);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				int t = Level.TILE_SIZE;
				//drawer.rect(i*t, j*t, t, t);
				if(i*t+trans.x>-t&&i*t+trans.x<drawer.width+t&&j*t+trans.y>-t&&j*t+trans.y<drawer.height+t) {
					if(ImageLoader.tileIndices[tiles[i][j]].getType()==1) {
						drawer.fill(200);
						drawer.stroke(190);
						drawer.rect(i*t, j*t,t,t);
					} else {
						ImageLoader.tileIndices[tiles[i][j]].draw(drawer,  i*t, j*t,t);
					}
				}
//				if(tiles[i][j]==0) {
//					drawer.image(ImageLoader.Wall, i*t+t/2, j*t+t/2,t,t);
//				} else {
//					drawer.image(ImageLoader.Floor, i*t+t/2, j*t+t/2);
//				}
			}
		} // draws grid overlay to debug stuff cuz tiles arent actually finished yet
		drawer.stroke(0);
		handleKeys();
		p.updatePos(this);
		handleEnemies(drawer);
		p.draw(drawer);
		handleProjectileCollisions();
		drawProjectiles(drawer);
		handleMelee();
		drawer.strokeWeight(20);
		drawer.point(mouseX, mouseY);
		drawer.strokeWeight(1);
	}

	public void handleEnemies(PApplet drawer) {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDead()) {
				enemies.remove(i);
			}
		}
		for (Enemy baddie : enemies) {
			baddie.act(this);
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
		if (keys[81]) {
			p.startDeflecting();
		}
		if(keys[32]) {
			p.dash(new Vector(mouseX-p.x,mouseY-p.y));
		}
		Vector v = new Vector();// player direction for movement
		if (keys[65]) {// a
			v.x--;
		}
		if (keys[87]) {// w
			v.y--;
		}
		if (keys[68]) {// d
			v.x++;
		}
		if (keys[83]) {// s
			v.y++;
		}
		v.scaleMagnitudeTo(0.5f);
		p.acc = v;
//		}
	}

	public ArrayList<Projectile> getEnemyProjectiles() {
		return enemyProjectiles;
	}

	public Player getPlayer() {
		return p;
	}

	public void handleMelee() {
		if (p.isMeleeing()) {
			for (Enemy e : enemies) {
				if (e.intersects(p.getMeleeHitbox())) {
					e.loseHP(p.getMeleeDmg());
				}
			}
		}
	}

	/**
	 * Returns -1 for game lost, 0 for game in progress, and 1 for game won
	 * 
	 * @return the state of the game
	 */
	public int getGameStatus() {
		if (enemies.size() == 0) {
			return 1;
		} else if (p.getHP() <= 0) {
			return -1;
		}

		return 0;
	}
	/*
	 * public ArrayList<Projectile> getMyProjectiles() { // TODO Auto-generated
	 * method stub return myProjectiles; }
	 */
}
