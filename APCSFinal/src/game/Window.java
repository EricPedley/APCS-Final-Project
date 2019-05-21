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
	private static String fs = System.getProperty("file.separator");
	private Player p;
	private float startX = 300, startY = 200;
	private Level currentLevel;
	private Menu startScreen,pauseScreen,gameOverScreen;
	private GameState state;
	//set up
	public void setup() {
		state = GameState.START;
		currentLevel = new Level(1, 2,this);
		currentLevel.passKeysReference(keys);
		startScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "startSample.png"));
		pauseScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "pauseSample.png"));
		gameOverScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "gameoverSample.png"));
	}
	
	public void draw() {
		background(255);
		switch(state) {
		case START:
			startScreen.draw(this);
			break;
		case PAUSE:
			pauseScreen.draw(this);
			break;
		case INGAME:
			currentLevel.draw(this);
			break;
		case GAMEOVER:
			gameOverScreen.draw(this);
			break;
		}
		
	}
	
	public void keyPressed() {
		keys[keyCode]=true;
		if(keys[83])
		{
			if(state==GameState.START)
			{
				state=GameState.INGAME;
				System.out.println("yIpeE");
			}
		}
		if(keys[82])
		{
			if(state==GameState.INGAME)
			{
				state=GameState.PAUSE;
			}
			else if(state==GameState.PAUSE)
			{
				state=GameState.INGAME;
			}
			else if(state==GameState.GAMEOVER)
			{
				restart();
			}
		}
		//program spec key codes for pausing, resuming, restarting, instructions, credits, etc.
	}
	
	public void keyReleased() {
		keys[keyCode]=false;
	}
	
	public void mousePressed() {//mousebutton 37 is left, 39 is right, 3 is middle
		currentLevel.mouseClicked(mouseX,mouseY, mouseButton);
	}
	
	public void restart() {
		state = GameState.START;
		currentLevel = new Level(1, 2,this);
		currentLevel.passKeysReference(keys);
	}
	
	
	
}
