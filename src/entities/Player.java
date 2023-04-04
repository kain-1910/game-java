package entities;

import static utliz.Contants.Direction.*;
import static utliz.Contants.*;
import static utliz.Contants.PlayerContants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameStates.Playing;
import main.Game;
import utliz.LoadSave;
import static utliz.HelpMethods.*;

public class Player extends Entity{
	private BufferedImage[][] animations;
//	private int playerDirection = -1;
	private boolean moving = false, attack = false;
	private boolean left, right, jump;
	private int[][] levelData;
	private double xDrawOffSet = 14*Game.SCALE;
	private double yDrawOffSet = 3.2f*Game.SCALE;

//	jump and fall
	private double jumpSpeed = -2.5f * Game.SCALE;
	private double fallSpeedAfterCollision = 0.25f * Game.SCALE;
	
//	status bar
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	
	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
//	health
	private int healthWidth = healthBarWidth;
	
//	vẽ chuyển hướng
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked;
	private Playing playing;
	
	public Player(double x, double y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = this.maxHealth;
		this.walkSpeed = 0.75f * Game.SCALE;
		loadAnimation();
		initHitBox(32, 35);
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Double(x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
		
	}

	public void update() {
		updateHealthBar();
		
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		updateAttackBox();
		
		updatePos();
		if(moving) {
			checkPotionTouched();
			checkSpikesTouched();
		}
		if(attack)
			checkAttack();
		
		updateAnimation();
		setAnimation();
	}
	
	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);
		
	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitBox);
		
	}

	private void checkAttack() {
		if(attackChecked || aniIndex != 2) {
			return;
		}
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE*2);
		}
		else if(left) {
			attackBox.x = hitBox.x - (int)(Game.SCALE*18);
		}
		attackBox.y = hitBox.y + (Game.SCALE * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int)((currentHealth/(double)maxHealth) * healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex],
				(int)(hitBox.x - xDrawOffSet) - lvlOffset + flipX,
				(int)(hitBox.y - yDrawOffSet),
				width * flipW,
				height, null);
		drawStatus(g);
//		drawAttackBox(g, lvlOffset);
//		drawHitBox(g, lvlOffset);
//		System.out.println("Player: " + getHitBox().x);
	}

	private void drawStatus(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void setAnimation() {
		
		int startAnimation = state;
//		Di chuyển
		if(moving) state = RUN;
		else state = IDLE;
		
		if(inAir) {
			if(airSpeed < 0) state = JUMP;
			else state = FALL;
		}
		
//		Tấn công
		if(attack) {
			state = ATTACK;
			if(startAnimation != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		
//		reset lại animation mỗi khi chuyển sang animation khác
		if(startAnimation != state) 
			resetAnimation();
		
	}
	
	private void resetAnimation() {
		aniTick = 0;
		aniIndex = 0;
	}
	
	private void updateAnimation() {
		aniTick++;
		if(aniTick >= ANISPEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
//				sau khi hiện animations attack thì set attack = false
				attack = false;
				attackChecked = false;
			}
		}
		
	}
	
	private void updatePos() {
		moving = false;
		
		if(jump) jump();
		
		if(!left && !right && !inAir) return;
		
		double xSpeed = 0;
		if(left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		
		if(right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		
		
		if(!inAir) 
			if(!IsEntityOnFloor(hitBox, levelData)) 
				inAir = true;
		
		if(inAir) {
			if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
				hitBox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} 
			else {
				hitBox.y = GetEntityYPosUnderRoofForAboveFloor(hitBox, airSpeed);
				if(airSpeed > 0) 
					resetInAir();
				else 
					airSpeed = fallSpeedAfterCollision;
				
				updateXPos(xSpeed);
				
			}
		}else 
			updateXPos(xSpeed);
		
		
		moving = true;

	}
	
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void jump() {
		if(inAir) return;
		inAir = true;
		airSpeed = jumpSpeed;
	
	}

	private void updateXPos(double xSpeed) {
		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
			hitBox.x += xSpeed;
		}
		else {
			hitBox.x = GetEntityPosNextToWall(hitBox, xSpeed);
		}
//		System.out.println(hitBox.x);
	}

	public void changeHealth(int value) {
		currentHealth += value;
		if(currentHealth <= 0) {
			currentHealth = 0;
		}
		else if(currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	public void kill() {
		currentHealth = 0;
	}

	
//	nếu không focus vào màn hình thì tự động dừng di chuyển
	public void resetDirBoolean() {
		left = right = false;
	}
	

	public void setAttack(boolean attack) {
		this.attack = attack;
	}
	
//	đọc ảnh và tạo animations
	private void loadAnimation() {
//		đọc ảnh
		BufferedImage[] img = LoadSave.GetSpritesAtlas(LoadSave.PLAYER_ATLAS);
			
//		tạo animations
		animations = new BufferedImage[8][12];
//			IDLE
		for(int j = 0; j < 12; j++) {
			animations[0][j] = img[0].getSubimage(j*38, 0, 38, 28);
		}
//			ATTACK
		for(int j = 0; j < 5; j++) {
			animations[1][j] = img[1].getSubimage(j*38, 0, 38, 28);
		}
//			RUN
		for(int j = 0; j < 6; j++) {
			animations[2][j] = img[2].getSubimage(j*38, 0, 38, 28);
		}
//			DEATH
		for(int j = 0; j < 4; j++) {
			int index = 3-j;
			animations[3][index] = img[3].getSubimage(j*38, 0, 38, 28);
		}
//			HIT
		for(int j = 0; j < 2; j++) {
			int index = 1-j;
			animations[4][index] = img[4].getSubimage(j*38, 0, 38, 28);
		}
//			JUMP
		animations[5][0] = img[5];
//			FALL
		animations[6][0] = img[6];
//			GROUND
		animations[7][0] = img[7];

//		Status bar
		statusBarImg = LoadSave.GetImgAtlas(LoadSave.STATUS_BAR);
	}
	
	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
		if(!IsEntityOnFloor(hitBox, levelData)) 
			inAir = true;
	}
	
	public void resetAll() {
		resetDirBoolean();
		inAir = false;
		attack = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;
		
		hitBox.x = x;
		hitBox.y = y;
		
		if(!IsEntityOnFloor(hitBox, levelData)) 
			inAir = true;
	}
	
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
