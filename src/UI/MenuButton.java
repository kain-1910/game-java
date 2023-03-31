package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameStates.GameStates;
import utliz.LoadSave;

import static utliz.Contants.UI.Buttons.*;

public class MenuButton {
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = BTN_WIDTH/2;
	private GameStates state;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	private BufferedImage[] img;
	
	public MenuButton(int xPos, int yPos, int rowIndex, GameStates state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImg();
		initBounds();
	}

	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, BTN_WIDTH, BTN_HEIGHT);
		
	}

	private void loadImg() {
		img = new BufferedImage[3];
		BufferedImage tmp = LoadSave.GetImgAtlas(LoadSave.MENU_BUTTONS);
		
		for(int i = 0; i < img.length; i++) {
			img[i] = tmp.getSubimage(i*BTN_WIDTH_DEFAULT, rowIndex*BTN_HEIGHT_DEFAULT, BTN_WIDTH_DEFAULT , BTN_HEIGHT_DEFAULT);
		}
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(img[index], xPos -xOffsetCenter, yPos, BTN_WIDTH, BTN_HEIGHT, null);
	}
	
	public void update() {
		index = 0;
		if(mouseOver) 
			index = 1;
		
		if(mousePressed) 
			index = 2;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void applyGameState() {
		GameStates.state = state;
	}
	
	public void resetBools() {
		mouseOver = mousePressed = false;
	}
	
}
