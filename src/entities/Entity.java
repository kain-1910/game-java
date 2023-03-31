package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
	protected double x, y;
	protected int width, height;
//	animation
	protected int aniTick, aniIndex;
//	trạng thái
	protected int state;
//	jump and fall
	protected double airSpeed;
	protected boolean inAir = false;
//	walkSpeed
	protected double walkSpeed;
//	health
	protected int maxHealth;
	protected int currentHealth;
//	hitBox
	protected Rectangle2D.Double hitBox;
	
//	attack box
	protected Rectangle2D.Double attackBox;
	
	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitBox(Graphics g, int xLvlOffset) {
		Color cl = new Color(0, 0, 220);
		g.setColor(cl);
		g.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
	}

	protected void initHitBox(int width, int height) {
		hitBox = new Rectangle2D.Double(x, y, (int)(width * Game.SCALE), (int) (height * Game.SCALE));
	}
	
	
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.darkGray);
		g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	
	public Rectangle2D.Double getHitBox() {
		return hitBox;
	}
	
	public int getAniIndex() {
		return aniIndex;
	}
	
}
