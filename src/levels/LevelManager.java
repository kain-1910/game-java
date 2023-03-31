package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.ArrayList;

import gameStates.GameStates;
import gameStates.Menu;
import main.Game;
import utliz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	public LevelManager(Game game) {
		this.game = game;
		importMapAtlas();
		levels = new ArrayList<>();
		buildAllLevels();
		
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for(BufferedImage img : allLevels) {
			levels.add(new Level(img));
		}
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("COMPLETED");
			GameStates.state = GameStates.MENU;
		}
		
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLevelData(newLevel.getLvlData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}

//	Load ảnh map
	private void importMapAtlas() {
		BufferedImage map = LoadSave.GetImgAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 12; j++) {
				int index = i*12 + j;
				levelSprite[index] = map.getSubimage(j*32, i*32, 32, 32);
			}
		}
	}

//	draw map
	public void draw(Graphics g, int LvlOffset) {
		for(int i = 0; i < Game.TILE_HEIGHT; i++) {
			for(int j = 0; j < levels.get(lvlIndex).getLvlData()[0].length; j++) {
				int index = levels.get(lvlIndex).getSpriteIndex(j, i);
				g.drawImage(levelSprite[index], Game.TILE_SIZE*j - LvlOffset, Game.TILE_SIZE*i, Game.TILE_SIZE, Game.TILE_SIZE, null);
			}
		}
		
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLvl() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}
	
	public void setLvlIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
}
