package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utliz.LoadSave;
import static utliz.Contants.UI.URMButtons.*;

public class UrmButtons extends PauseButton {
	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;
	
	public UrmButtons(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		LoadImgs();
	}
	
	private void LoadImgs() {
		BufferedImage tmp = LoadSave.GetImgAtlas(LoadSave.URM_BUTTON);
		imgs = new BufferedImage[3];
		for(int i = 0; i < imgs.length; i++) {
			imgs[i] = tmp.getSubimage(i*URM_SIZE_DEFAULT, rowIndex*URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
		}
	}
	
	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
		
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
	}
	
	public void resetBools() {
		mouseOver = mousePressed = false;
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
	
	
}
