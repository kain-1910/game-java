package entities;

import static utliz.Contants.Enemy.*;
import static utliz.HelpMethods.CanMoveHere;
import static utliz.HelpMethods.GetEntityYPosUnderRoofForAboveFloor;
import static utliz.HelpMethods.IsEntityOnFloor;
import static utliz.HelpMethods.IsFloor;
import static utliz.Contants.Direction.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;


import main.Game;
public class Crep extends Enemy{

	private int attackBoxOffsetX;
	public Crep(double x, double y) {
		super(x, y, CREP_WIDTH, CREP_HEIGHT, CREP);
		initHitBox(29, 28);
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Double(x, y, (int)(55*Game.SCALE), (int)(28*Game.SCALE));
		attackBoxOffsetX = (int) (12*Game.SCALE);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(lvlData);
		
		if(inAir) 
			updateInAir(lvlData);
		
		else {
			switch(state) {
			case IDLE:
				newState(RUN);
				break;
			case RUN:
				if(canSeePlayer(lvlData, player)) 
					turnTowardsPlayer(player);
				
				if(canSeePlayer(lvlData, player) && isPlayerCloseForAttack(player))
					newState(ATTACK);
				
				move(lvlData);
				break;
			case ATTACK: 
				if(aniIndex == 0)
					attackChecked = false;
				if(aniIndex == 2 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case HIT: 
				break;
			}
		}
		
	}
	
	public int flipX() {
		if(walkDir == RIGHT)
			return width;
		else return 0;
	}
	
	public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else return 1;
	}

	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		currentHealth = maxHealth;
		firstUpdate = true;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}

}
