package gameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import UI.MenuButton;
import main.Game;
import utliz.LoadSave;

public class Menu extends State implements StateMethods{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backGroundImg, bgMenu;
	private int menuX, menuY, menuWidth, menuHeight;
	
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadMenuBackground();
	}

	private void loadMenuBackground() {
		backGroundImg = LoadSave.GetImgAtlas(LoadSave.MENU_BACKGROUND);
		bgMenu = LoadSave.GetImgAtlas(LoadSave.BACKGROUND_AFTER_MENU);
		menuWidth = (int) (backGroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backGroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH/2 - menuWidth/2;
		menuY = (int) (45*Game.SCALE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH/2, (int) (150 * Game.SCALE), 0, GameStates.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int) (220 * Game.SCALE), 1, GameStates.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int) (290 * Game.SCALE), 2, GameStates.QUIT);
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons) {
			mb.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgMenu, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(backGroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : buttons) {
			mb.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed()) mb.applyGameState();
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			GameStates.state = GameStates.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
