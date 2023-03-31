package object;

import static utliz.Contants.*;
import static utliz.Contants.ObjectConstants.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {
	protected int x, y, objType;
	protected Rectangle2D.Double hitBox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= ANISPEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;	
				if(objType == BARREL || objType == BOX) {
					doAnimation = false;
					active = false;
				}
			}
		}
	}
	
	public void reset() {
		aniIndex = aniTick = 0;
		active = true;
		
		if(objType == BARREL || objType == BOX) 
			doAnimation = false;
		else 
			doAnimation = true;
	}
	
	public void drawHitBox(Graphics g, int xLvlOffset) {
		Color cl = new Color(0, 0, 220);
		g.setColor(cl);
		g.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
	}

	protected void initHitBox(int width, int height) {
		hitBox = new Rectangle2D.Double(x, y, (int)(width * Game.SCALE), (int) (height * Game.SCALE));
	}
	
	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getObjType() {
		return objType;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public Rectangle2D.Double getHitBox() {
		return hitBox;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}


	
	
}
