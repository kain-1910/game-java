package inputs;
// Lắng nghe inputs từ bàn phím
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameStates.GameStates;
import main.GamePanel;
//import static utliz.Contants.Direction.*;

public class KeyBoardInputs implements KeyListener{
	private GamePanel gamePanel;
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameStates.state) {
			case MENU:
				gamePanel.getGame().getMenu().keyPressed(e);
				break;
			case PLAYING:
				gamePanel.getGame().getPlaying().keyPressed(e);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameStates.state) {
			case MENU:
				gamePanel.getGame().getMenu().keyReleased(e);
				break;
			case PLAYING:
				gamePanel.getGame().getPlaying().keyReleased(e);
				break;
			default:
				break;
		}
		
	}

}
