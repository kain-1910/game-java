package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameStates.Playing;
import levels.Level;
import utliz.LoadSave;
import static utliz.Contants.Enemy.*;


public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] crepArr;
	private ArrayList<Crep> creps = new ArrayList<>();
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level level) {
		creps = level.getCreps();
		System.out.println("size of crep: " + creps.size());
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for(Crep c : creps) 
			if(c.isActive()) {
				c.update(lvlData, player);
				isAnyActive = true;
			}
		
		if(!isAnyActive)
			playing.setLevelCompleted(true);
		
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCreps(g, xLvlOffset);
	}
	
	private void drawCreps(Graphics g, int xLvlOffset) {
		for(Crep c : creps) {
			if(c.isActive()) {
				g.drawImage(crepArr[c.getState()][c.getAniIndex()],
						(int) c.getHitBox().x - xLvlOffset -CREP_DRAWOFFSET_X + c.flipX(),
						(int) c.getHitBox().y - CREP_DRAWOFFSET_Y,
						CREP_WIDTH * c.flipW(),
						CREP_HEIGHT, null);
//				System.out.println(c.getHitBox().x);
//				c.drawHitBox(g, xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);
			}
		}
		
	}
	
	public void checkEnemyHit(Rectangle2D.Double attackBox) {
		for(Crep c : creps) {
			if(c.isActive()) {
				if(attackBox.intersects(c.getHitBox())) {
					c.hurt(10);
					return;
				}
			}
		}
	}

	private void loadEnemyImgs() {
		BufferedImage[] img = LoadSave.GetSpritesAtlas(LoadSave.CREP_ATLAS);
		crepArr = new BufferedImage[5][11];
//		idle
		for(int i = 0; i < 11; i++) {
			crepArr[0][i] = img[0].getSubimage(i*34, 0, 34, 28);
		}
//		attack
		for(int i = 0; i < 5; i++) {
			crepArr[1][i] = img[1].getSubimage(i*34, 0, 34, 28);
		}
//		run
		for(int i = 0; i < 6; i++) {
			crepArr[2][i] = img[2].getSubimage(i*34, 0, 34, 28);
		}
//		death
		for(int i = 0; i < 4; i++) {
			crepArr[3][i] = img[3].getSubimage(i*34, 0, 34, 28);
		}
//		hit
		for(int i = 0; i < 2; i++) {
			crepArr[4][i] = img[4].getSubimage(i*34, 0, 34, 28);
		}
	}

	public void resetAllEnemies() {
		for(Crep c : creps)
			c.resetEnemy();
		
	}
	
}
