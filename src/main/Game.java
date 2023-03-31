package main;

import java.awt.Graphics;


import gameStates.GameStates;
import gameStates.Menu;
import gameStates.Playing;
import utliz.LoadSave;


public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS = 120;
	private final int UPS = 200;

	private Playing playing;
	private Menu menu;
	
//	set kích thước màn hình cho game
	public static final int TILE_DEFAULT_SIZE = 32; // kích thước 1 ô ban đầu
	public static final double SCALE = 1.5f; // phóng to 1 ô thành 48x48
	public static final int TILE_WIDTH = 26;  
	public static final int TILE_HEIGHT = 14;
	public static final int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE); // kích thước 1 ô sau khi scale
	public static final int GAME_WIDTH = TILE_SIZE*TILE_WIDTH;	
	public static final int GAME_HEIGHT = TILE_SIZE*TILE_HEIGHT;
	
	
	
	public Game(){
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		gameLoop();
	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	private void gameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
			
		switch(GameStates.state) {
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
			case QUIT:
			default:
				System.exit(0);
				break;
		}
		
	}
	
	public void render(Graphics g) {
		
		
		switch(GameStates.state) {
			case MENU:
				menu.draw(g);
				break;
			case PLAYING:
				playing.draw(g);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void run() {
//		biến lưu trữ thời gian của 1 khung hình   
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;
		
//		long lastFrame = System.nanoTime();
//		long now = System.nanoTime();
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int update = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true){
			long currentTime = System.nanoTime();
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if(deltaU >= 1) {
				update();
				update++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + update);
				frames = 0;
				update = 0;
			}
			
		}
	}

//	kiểm tra có đang focus màn hình hay không
	public void windowFocusLost() {
		if(GameStates.state == GameStates.PLAYING) 
			playing.getPlayer().resetDirBoolean();
	}

	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
}
