package entities;
import static utliz.Contants.Enemy.*;
import static utliz.HelpMethods.*;
import static utliz.Contants.*;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import static utliz.Contants.Direction.*;

import main.Game;

public abstract class Enemy extends Entity{
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = LEFT;
	protected int tileY;
	protected double attackDistance = Game.TILE_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(double x, double y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(width, height);
		this.maxHealth = getMaxHealth(enemyType);
		this.currentHealth = maxHealth;
		this.walkSpeed = 0.45f * Game.SCALE;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitBox, lvlData)) 
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		}
		else {
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofForAboveFloor(hitBox, airSpeed);
			tileY = (int) (hitBox.y / Game.TILE_SIZE) - 1;
		}
		
	}
	
	protected void move(int[][] lvlData) {
		double xSpeed = 0;
		if(walkDir == LEFT) {
			xSpeed = -walkSpeed;
			if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
				if(IsFloor(hitBox, xSpeed, lvlData)) {
					hitBox.x += xSpeed;
					return;
				}
			}
		}
		else {
			xSpeed = walkSpeed;
			if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
				if(IsFloor(hitBox, xSpeed + hitBox.width, lvlData)) {
					hitBox.x += xSpeed;
					return;
				}
			}
		}
		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if(player.hitBox.x > hitBox.x) 
			walkDir = RIGHT;
		else walkDir = LEFT;
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)(player.getHitBox().y / Game.TILE_SIZE);
//		System.out.println("P"+playerTileY);
//		System.out.println(tileY);
//		System.out.println(IsSightClear(lvlData, hitBox, player.hitBox, tileY));
		if(playerTileY == tileY) 
			if(isPlayerInRange(player)) 
				if(IsSightClear(lvlData, hitBox, player.hitBox, tileY))
					return true;
			
		return false;
	}
	
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance * 6;
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance;
	}

	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= ANISPEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, state)) {
				aniIndex = 0;
				
				switch(state) {
				case ATTACK, HIT -> state = IDLE;
				case DEATH -> active = false;
				}
				
				if(state == ATTACK)
					state = IDLE;
				else if(state == HIT)
					state = HIT;
				else if(state == DEATH)
					active = false;
			}
		}
	}

	protected void changeWalkDir() {
		if(walkDir == LEFT) walkDir = RIGHT;
		else walkDir = LEFT;
		
	}
	
	public int getState() {
		return state;
	}

	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0) 
			newState(DEATH);
		else 
			newState(HIT);
	}
	
	protected void checkPlayerHit(Rectangle2D.Double attackBox, Player player) {
		if(attackBox.intersects(player.hitBox)) 
			player.changeHealth(-getEnemyDmg(enemyType));
		attackChecked = true;
	}

	public boolean isActive() {
		return active;
	}

}
