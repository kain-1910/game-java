package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utliz.HelpMethods.*;
import entities.Crep;
import main.Game;
import object.Potion;
import object.gameContainer;
import utliz.HelpMethods;


public class Level {
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crep> creps;
	private ArrayList<Potion> potions;
	private ArrayList<gameContainer> containers;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemy();
		createPotions();
		createContainers();
		calcLvlOffset();
	}
	
	private void createContainers() {
		containers = HelpMethods.GetContainers(img);
	}

	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
		
	}

	private void createLevelData() {
		lvlData = GetMapData(img);
		
	}
	
	private void createEnemy() {
		creps = GetCreps(img);

	}
	public ArrayList<Crep> getCreps() {
		return creps;
	}

	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILE_WIDTH;
		maxLvlOffsetX = Game.TILE_SIZE * maxTilesOffset;
	}


	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLvlData() {
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Potion> getPotions() {
		return potions;
	}
	
	public ArrayList<gameContainer> getContainers() {
		return containers;
	}
	
}
