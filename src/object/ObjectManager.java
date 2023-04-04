package object;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;

import static utliz.Contants.ObjectConstants.*;
import gameStates.Playing;
import levels.Level;
import utliz.LoadSave;

public class ObjectManager {
	private Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage spikeImg;
	private ArrayList<Potion> potions;
	private ArrayList<gameContainer> containers;
	private ArrayList<Spike> spikes;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void loadObjects(Level newlevel) {
		potions = new ArrayList<>(newlevel.getPotions());
		containers = new ArrayList<>(newlevel.getContainers());
		spikes = newlevel.getSpikes();
	}
	
	public void checkSpikesTouched(Player player) {
		for (Spike sp : spikes)
			if (sp.getHitBox().intersects(player.getHitBox()))
				player.kill();
	}
	
	public void checkObjectTouched(Rectangle2D.Double hitBox) {
		for(Potion p : potions) {
			if(p.isActive()) {
				if(hitBox.intersects(p.getHitBox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		}
	}
	
	public void applyEffectToPlayer(Potion p) {
		if(p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else 
			playing.getPlayer().changeHealth(BLUE_POTION_VALUE);
	}
	
	public void checkObjectHit(Rectangle2D.Double attackBox) {
		for(gameContainer gc : containers) {
			if(gc.isActive() && !gc.doAnimation) {
				if(gc.getHitBox().intersects(attackBox)) {
					gc.setAnimation(true);
					
					int type = 0;
					if(gc.getObjType() == BARREL) type = 1;
					potions.add(new Potion((int)(gc.getHitBox().x + gc.getHitBox().width/2),
							(int)(gc.getHitBox().y + gc.getHitBox().height/4),
							type));
					
					return;
				}
			}
		}
	}

	private void loadImgs() {
		BufferedImage potionSprites = LoadSave.GetImgAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[2][7];
		for(int i = 0; i < potionImgs.length; i++) {
			for(int j = 0; j < potionImgs[i].length; j++) {
				potionImgs[i][j] = potionSprites.getSubimage(12*j, 16*i, 12, 16);
			}
		}
		
		BufferedImage containerSprites = LoadSave.GetImgAtlas(LoadSave.CONTAINER_ATLAS);
		containerImgs = new BufferedImage[2][8];
		for(int i = 0; i < containerImgs.length; i++) {
			for(int j = 0; j < containerImgs[i].length; j++) {
				containerImgs[i][j] = containerSprites.getSubimage(40*j, 30*i, 40, 30);
			}
		}
		
		spikeImg = LoadSave.GetImgAtlas(LoadSave.SPIKE_ATLAS);
	}
	
	public void update() {
		for(Potion p : potions) {
			if(p.isActive())
				p.update();
		}
		
		for(gameContainer gc : containers) {
			if(gc.isActive())
				gc.update();
		}
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawContainers(g, xLvlOffset);
		drawSpikes(g, xLvlOffset);
	}

	private void drawSpikes(Graphics g, int xLvlOffset) {
		for(Spike sp : spikes) 
			g.drawImage(spikeImg, (int)(sp.getHitBox().x - xLvlOffset),
					(int)(sp.getHitBox().y - sp.getyDrawOffset()),
					SPIKE_WIDTH,
					SPIKE_HEIGHT, null);
		
	}

	private void drawContainers(Graphics g, int xLvlOffset) {
		for(gameContainer gc : containers) {
			if(gc.isActive()) {
				int type = 0;
				if(gc.getObjType() == BARREL) type = 1;
				g.drawImage(containerImgs[type][gc.getAniIndex()],
						(int)(gc.getHitBox().x - gc.getxDrawOffset() - xLvlOffset),
						(int)(gc.getHitBox().y - gc.getyDrawOffset()),
						CONTAINER_WIDTH,
						CONTAINER_HEIGHT, null);
			}	
		}
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for(Potion p : potions) {
			if(p.isActive()) {
				int type = 0;
				if(p.getObjType() == RED_POTION) type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()],
						(int)(p.getHitBox().x - p.getxDrawOffset() - xLvlOffset),
						(int)(p.getHitBox().y - p.getyDrawOffset()),
						POTION_WIDTH,
						POTION_HEIGHT, null);
			}
				
		}
		
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLvl());
		
		for(Potion p : potions) 
			p.reset();
		
		for(gameContainer gc : containers) 
			gc.reset();
		
	}

}
