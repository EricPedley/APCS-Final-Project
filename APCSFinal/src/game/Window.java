package game;


import processing.core.PApplet;
import processing.core.PImage;


import processing.core.PApplet;
import processing.core.PImage;

enum GameState {
	START,PAUSE,INGAME,GAMEOVER;
}

public class Window extends PApplet{

	private boolean[] keys = new boolean[500];
	private static String fS = System.getProperty("file.separator");
	private Player p;
	private float startX = 300, startY = 200;
	private Level currentLevel;
	private Menu startScreen,pauseScreen,gameOverScreen;
	private GameState state;
	//set up
	public void setup() {
		state = GameState.INGAME;
		currentLevel = new Level(1, 2,this);
		currentLevel.passKeysReference(keys);
	}
	
	public void draw() {
		background(255);
		switch(state) {
		case START:
			//startScreen.draw(this);
			break;
		case PAUSE:
			//pauseScreen.draw(this);
			break;
		case INGAME:
			currentLevel.draw(this);
			break;
		case GAMEOVER:
			//gameOverScreen.draw(this);
			break;
		}
		
	}
	
	public void keyPressed() {
		keys[keyCode]=true;
	}
	
	public void keyReleased() {
		keys[keyCode]=false;
	}
	
	public void mousePressed() {//mousebutton 37 is left, 39 is right, 3 is middle
		currentLevel.mouseClicked(mouseX,mouseY, mouseButton);
	}
	

	
}
