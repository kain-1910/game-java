package UI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameStates.GameStates;
import gameStates.Playing;
import gameStates.StateMethods;
import main.Game;
import utliz.Contants.UI.URMButtons;
import utliz.LoadSave;
import static utliz.Contants.UI.PauseButtons.*;
import static utliz.Contants.UI.URMButtons.*;
import static utliz.Contants.UI.VolumeButtons.*;

public class PauseOverlay {
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWidth, bgHeight;
	private SoundButton musicButton, sfxButton;
	private UrmButtons  menuB, replayB, unpauseB;
	private VolumeButton volumeButton;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createURMButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int volumeX = (int) (309 * Game.SCALE);
		int volumeY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void createURMButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int unpauseX = (int) (462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);
		
		menuB = new UrmButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
	}

	private void createSoundButtons() {
		int soundX = (int) (450 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186*Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton =  new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetImgAtlas(LoadSave.PAUSE_BACKGROUND);
		bgWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH/2 - bgWidth/2;
		bgY = (int) (25 * Game.SCALE);
 	}

	public void update() {
//		Sound Buttons
		musicButton.update();
		sfxButton.update();
		
//		URM Buttons
		menuB.update();
		replayB.update();
		unpauseB.update();
		
//		volume button
		volumeButton.update();
		
	}
	
	public void draw(Graphics g) {
//		draw pause background
		g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
		
//		draw sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
		
//		URM Buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
//		volume slider
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}

	public void mousePressed(MouseEvent e) {
		if(isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}


	public void mouseReleased(MouseEvent e) {
		
		if(isIn(e, musicButton)) {
			if(musicButton.isMousePressed()) 
				musicButton.setMuted(!musicButton.isMuted());
			
		} else if(isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed()) 
				sfxButton.setMuted(!sfxButton.isMuted());
		} else if(isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				GameStates.state = GameStates.MENU;	
				playing.unpauseGame();
			}
		} else if(isIn(e, replayB)) {
			if(replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
				
		} else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed()) 
				playing.unpauseGame();
		}
		
		
		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeButton.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}	
	
	private boolean isIn(MouseEvent e, PauseButton pb) {
		return pb.getBounds().contains(e.getX(), e.getY());
	}
}
