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
	private Tile[] tileIndices = new Tile[20];
	private ArrayList<Projectile> myProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Enemy> enemies;
	public static final int TILE_SIZE = 64; 
	private String fs = System.getProperty("file.separator");
	private int numEnemies;
	//public final PImage bulletImage;
	private boolean upDoor, downDoor, rightDoor, leftDoor;
	//private Map map;
	private Player p;
	
	//Constuctors
	public Level(int levelNumber, int numEnemies) {
		//tileIndices = TileImageLoader.tileIndices;
		myProjectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		PImage[] enemyImages = {};
		
		//bulletImage = ImageLoader.BLUE_PROJECTILE;
		readData("Levels" + fs + "Boss.txt");
		for(int c = 0 ; c < numEnemies ; c++) {
			
			//enemies.add(new Enemy(Math.random()*(x-4)*TILE_SIZE  + 2 * TILE_SIZE,Math.random()*(y-4)*TILE_SIZE  + 2 * TILE_SIZE,(int) (Math.random() * 2), this));
		}

		
	}
	
	public void setPlayer(Player p) {
		this.p = p;
	}
	
	// Methods
	public void assignIndices(PApplet p) {
		tileIndices[0] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"LeftRockWall.gif"),1);
		tileIndices[1] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"RightRockWall.gif"),1);
		tileIndices[2] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"RockTopFront.gif"),1);
		tileIndices[3] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"RockBottomFront.gif"),1);
		tileIndices[4] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"RockWallBack.gif"),1);
		tileIndices[5] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"InwardRockCornerBottomLeft.gif"),1);
		tileIndices[6] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"InwardRockCornerBottomRight.gif"),1);
		tileIndices[7] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"InwardRockCornerTopLeft.gif"),1);
		tileIndices[8] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"InwardRockCornerTopRight.gif"),1);
		tileIndices[9] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"OutwardRockCornerBottomLeft.gif"),1);
		tileIndices[10] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"OutWardRockCornerBottomRight.gif"),1);
		tileIndices[11] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"OutwardRockCornerTopLeft.gif"),1);
		tileIndices[12] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Wall"+fs+"OutwardRockCornerTopRight.gif"),1);
		tileIndices[13] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"1Tile.gif"),0);
		tileIndices[14] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"4Tile.gif"),0);
		tileIndices[15] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileBottomLeft.gif"),0);
		tileIndices[16] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileBottomRight.gif"),0);
		tileIndices[17] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileTopLeft.gif"),2);tileIndices[17] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileTopLeft.gif"),0);
		tileIndices[18] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileTopRight.gif"),0);
		tileIndices[19] = new Tile(p.loadImage("Images"+fs+"Map"+fs+"Floor"+fs+"BigTileTopLeft.gif"),2);

	}

	
	private void readData (String filename) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
					reader = new FileReader(dataFile);
					in = new Scanner(reader);
					String s = in.nextLine();
					x = Integer.parseInt(s.substring(0, s.indexOf(',')));
					s = s.substring(s.indexOf(',') + 1); 
					y = Integer.parseInt(s.substring(0, s.indexOf(',')));
					tileType = new int[x][y];
					

					while (in.hasNext()) {
						String line = in.nextLine();
						for(int i = 0; line.indexOf(',') != -1; line = line.substring(line.indexOf(',') + 1) ) {
							
							
							tileType[i][count] = Integer.parseInt(line.substring(0,line.indexOf(',')));
							i++;
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
	
	
	public Tile tileAt(int x, int y) {
		return tileIndices[tileType[x][y]];
	}
	
	public void deleteProjectile(Projectile p) {
		if(myProjectiles.contains(p))
			myProjectiles.remove(p);
		
		if(enemyProjectiles.contains(p))
			enemyProjectiles.remove(p);
	}
	
	public void addProjectile(Projectile p,boolean isPlayer) {
		if(isPlayer)
			myProjectiles.add(p);
		else
			enemyProjectiles.add(p);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ArrayList<Projectile> getEnemyProjectiles(){
		return this.enemyProjectiles;
	}

	

	public void draw(PApplet p) {
		
		p.pushMatrix();
		//System.out.println(TileImageLoader.tileIndices[0]);
		TileImageLoader.tileIndices[0].draw(p, TILE_SIZE);
		for(int cols = 0; cols < y;cols++) {
			
			for(int rows = 0; rows < x; rows++) {
				//System.out.println(tileType[rows][cols]);
				tileIndices[tileType[rows][cols]].draw(p, TILE_SIZE);
				p.translate(TILE_SIZE, 0);
			}
			p.translate(-x * TILE_SIZE, TILE_SIZE);
		}
		p.popMatrix();
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).act(h, myProjectiles);

			
			if(enemies.get(i).isDead()) {
				enemies.remove(i);
				p.setHp(p.getHp() + 1);
			} else
				enemies.get(i).draw(p);
		}
		
		
		for(int i = 0; i < enemyProjectiles.size(); i++) {
			p.checkHit(enemyProjectiles.get(i));
		}
		
		for(int c=0;c<myProjectiles.size();c++) {
			Projectile proj = myProjectiles.get(c);
			proj.draw(p);
			if(proj.getXPos()<0||proj.getXPos()>x*TILE_SIZE||proj.getYPos()<0||proj.getYPos()>y*TILE_SIZE)
				myProjectiles.remove(c);
		}
		for(int c=0;c<enemyProjectiles.size();c++) {
			Projectile proj = enemyProjectiles.get(c);
			proj.draw(p);
			if(proj.getXPos()<0||proj.getXPos()>x*TILE_SIZE||proj.getYPos()<0||proj.getYPos()>y*TILE_SIZE)
				enemyProjectiles.remove(c);
		}
		
	}
	
	
	
	
	public Player getPlayer() {
		return p;
	}
	

/*	public ArrayList<Projectile> getMyProjectiles() {
		// TODO Auto-generated method stub
		return myProjectiles;
	}*/
}

