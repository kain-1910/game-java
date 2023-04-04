package gameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import UI.GameOverOverLay;
import UI.LevelCompleteOverlay;
import UI.PauseOverlay;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import object.ObjectManager;
import utliz.LoadSave;
import static utliz.Contants.Environment.*;

public class Playing extends State implements StateMethods{
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverLay gameOverOverLay;
	private LevelCompleteOverlay levelCompletedOverlay;
	private boolean paused = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
//	private int lvlTilesWide = LoadSave.GetMapData()[0].length;
//	private int maxTilesOffset = lvlTilesWide - Game.TILE_WIDTH;
	private int maxLvlOffsetX;
	
	private BufferedImage backgroundImg, bigCloud;
	private boolean gameOver;
	private boolean lvlCompleted;

	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundImg = LoadSave.GetImgAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetImgAtlas(LoadSave.BIG_CLOUD);
		
		calcLvlOffset();
		loadStartLevel();
	}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLvl());
		objectManager.loadObjects(levelManager.getCurrentLvl());
	}



	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLvl().getLvlOffset();
		
	}



	private void initClasses() {
		levelManager = new LevelManager(game);
//		crep
		enemyManager = new EnemyManager(this);
//		object
		objectManager = new ObjectManager(this);
		
//		player
		player = new Player(200, 200, (int) (Game.SCALE*64), (int) (Game.SCALE*40), this);
//		map
		player.loadLevelData(levelManager.getCurrentLvl().getLvlData());
		
//		pause menu
		pauseOverlay = new PauseOverlay(this);
//		game over
		gameOverOverLay = new GameOverOverLay(this);
//		level completed
		levelCompletedOverlay = new LevelCompleteOverlay(this);
	}

	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		}else if(lvlCompleted) {
			levelCompletedOverlay.update();
		}else if(!gameOver){
			levelManager.update();
			objectManager.update();
			
//			player update
			player.update();
			enemyManager.update(levelManager.getCurrentLvl().getLvlData(), player);
			checkCloseBorder();
		}
	}
	
	@Override
	public void draw(Graphics g) {
		//draw background in playing game
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		drawClouds(g);
		
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
		else if(gameOver) {
			gameOverOverLay.draw(g);
		}
		else if(lvlCompleted)
			levelCompletedOverlay.draw(g);
	}
	
	private void drawClouds(Graphics g) {
		//	draw big cloud	
		for(int i = 0; i < 3; i++) {
			g.drawImage(bigCloud, 0 + i*BIG_CLOUD_WIDTH - (int) (xLvlOffset*0.3), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		}
	}

	private void checkCloseBorder() {
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset; // lưu độ chêch lệch so với border screen
		
		if(diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if(diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		
		if(xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
	}
	
	public void checkEnemyHit(Rectangle2D.Double attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void checkPotionTouched(Rectangle2D.Double hitBox) {
		objectManager.checkObjectTouched(hitBox);
	}
	
	public void checkObjectHit(Rectangle2D.Double attackBox) {
		objectManager.checkObjectHit(attackBox);
	}
	
	public void checkSpikesTouched(Player player) {
		objectManager.checkSpikesTouched(player);
		
	}
	
	public void resetAll() {
		// reset after die
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		kiểm tra có click chuột trái không
		if(!gameOver)
			if(e.getButton() == MouseEvent.BUTTON1) {  // BUTTON1 là chuột trái
				player.setAttack(true);
			}
	}

	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused) pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverLay.keyPressed(e);
		else
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				player.setJump(true);
				System.out.println("W");
				break;
			case KeyEvent.VK_A:
				player.setLeft(true);
				System.out.println("A");
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				System.out.println("D");
				break;
			case KeyEvent.VK_G:
				paused = !paused;
				break;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				player.setJump(false);
				break;
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			}
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void setLevelCompleted(boolean lvlCompleted) {
		this.lvlCompleted = lvlCompleted;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void windowFocusLost() {
		player.resetDirBoolean();
	}
	
	public Player getPlayer() {
		return player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

	
}
