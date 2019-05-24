package game;


import processing.core.PApplet;
import processing.core.PImage;


import processing.core.PApplet;
import processing.core.PImage;

enum GameState {
	START,PAUSE,INGAME,GAMEOVER,INSTRUCTIONS,WON,LEVEL;
}

public class Window extends PApplet{

	private boolean[] keys = new boolean[500];
	private static String fs = System.getProperty("file.separator");
	private Level currentLevel;
	private Menu startScreen,pauseScreen,gameOverScreen,instructionScreen, winScreen,levelSelect;
	private GameState state;
	//set up
	public void setup() {
		ImageLoader.setup(this);
		state = GameState.START;
//		currentLevel = new Level(1,2,this);
//		currentLevel.passKeysReference(keys);
		startScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "title1.png"));
		pauseScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "pauseSample.png"));
		gameOverScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "gameoverSample.png"));
		instructionScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "instructions2.png"));
		winScreen = new Menu(this.loadImage("resources" + fs + "images" + fs + "winSample.png"));
		levelSelect= new Menu(this.loadImage("resources" + fs + "images" + fs + "levelSample.png"));
	}
	
	public void draw() {
		if(currentLevel!=null&&currentLevel.getGameStatus()==1)
		{
			state=GameState.WON;
		}
		else if(currentLevel!=null&&currentLevel.getGameStatus()==-1)
		{
			state=GameState.GAMEOVER;
		}
		switch(state) {
		case START:
			startScreen.draw(this);
			break;
		case LEVEL:
			levelSelect.draw(this);
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
		case INSTRUCTIONS:
			instructionScreen.draw(this);
			break;
		case WON:
			winScreen.draw(this);
			break;
		}
		
	}
	 
	public void keyPressed() {
		keys[keyCode]=true;
		if(keys[83])//s
		{
			if(state==GameState.START||state==GameState.INSTRUCTIONS)
			{
				state=GameState.LEVEL;
				//System.out.println("yIpeE");
			}
		}
		if(keys[82])//r
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
		if(keys[73]) {//i
			if(state==GameState.START)
			{
				state=GameState.INSTRUCTIONS;
			}
		}
		if(state==GameState.LEVEL)
		{
			if(keys[49])//1
			{
				currentLevel=new Level(1,1,this);
				currentLevel.passKeysReference(keys);
				state=GameState.INGAME;
			}
			if(keys[50])//2
			{
				currentLevel=new Level(2,2,this);
				currentLevel.passKeysReference(keys);
				state=GameState.INGAME;
			}
			if(keys[51])//3
			{
				currentLevel=new Level(3,3,this);
				currentLevel.passKeysReference(keys);
				state=GameState.INGAME;
			}
		}
		if(state==GameState.WON&&keys[82])
		{
			restart();
		}
		//program spec key codes for pausing, resuming, restarting, instructions, credits, etc.
	}
	
	public void keyReleased() {
		keys[keyCode]=false;
	}
	
	public void mousePressed() {//mousebutton 37 is left, 39 is right, 3 is middle
		if(currentLevel!=null)
			currentLevel.mouseClicked(mouseButton);
	}
	
	public void restart() {
		state = GameState.START;
		currentLevel = new Level(1, 2,this);
		currentLevel.passKeysReference(keys);
	}
	
	
	
}
