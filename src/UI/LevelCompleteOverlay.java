package UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameStates.GameStates;
import gameStates.Playing;
import main.Game;
import utliz.Contants.UI.URMButtons;
import utliz.LoadSave;
import static utliz.Contants.UI.URMButtons.*;

public class LevelCompleteOverlay {
	Playing playing;
	private UrmButtons menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompleteOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		next = new UrmButtons(nextX, y,URM_SIZE , URM_SIZE, 0);
		menu = new UrmButtons(menuX, y,URM_SIZE , URM_SIZE, 2);
	}

	private void initImg() {
		img = LoadSave.GetImgAtlas(LoadSave.LEVEL_COMPLETED);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH /2 - bgW/2;
		bgY = (int) (75*Game.SCALE);
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
	}
	
	public void update() {
		menu.update();
		next.update();
	}
	
	private boolean isIn(UrmButtons b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(next, e)) 
			next.setMouseOver(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
				playing.resetAll();
				GameStates.state = GameStates.MENU;
			}
		}
		else if(isIn(next, e)) 
			if(next.isMousePressed()) {
				playing.loadNextLevel();
			}
		
		
		menu.resetBools();
		next.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(next, e)) 
			next.setMousePressed(true);
	}
	
}
