package utliz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crep;
import static utliz.Contants.Enemy.*;
import main.Game;

public class LoadSave {
//	mảng lưu file ảnh của player
	public static final String[] PLAYER_ATLAS = {
			"res/player/idle_right38x28.png",
			"res/player/attack_right38x28.png",
			"res/player/run_right38x28.png",
			"res/player/death38x28.png",
			"res/player/hit38x28.png",
			"res/player/jump38x28.png",
			"res/player/fall38x28.png",
			"res/player/ground38x28.png"
	};
//	crep
	public static final String[] CREP_ATLAS = {
			"res/crep/idle34x28.png",
			"res/crep/attack34x28.png",
			"res/crep/run34x28.png",
			"res/crep/dead34x28.png",
			"res/crep/hit34x28.png",
			"res/crep/jump34x28.png",
			"res/crep/fall34x28.png",
			"res/crep/ground34x28.png"
	};
	
	public static final String LEVEL_ATLAS = "res/map/map.png";
	public static final String MENU_BUTTONS = "res/UI/button_atlas.png";
	public static final String MENU_BACKGROUND = "res/UI/menu_background.png";
	public static final String BACKGROUND_AFTER_MENU = "res/UI/bg_menu.png";
	public static final String PAUSE_BACKGROUND = "res/UI/pause_menu.png";
	public static final String SOUND_BUTTON = "res/UI/sound_button.png";
	public static final String URM_BUTTON = "res/UI/urm_buttons.png";
	public static final String VOLUME_BUTTON = "res/UI/volume_buttons.png";
	public static final String PLAYING_BG_IMG = "res/map/playing_bg_img.png";
	public static final String BIG_CLOUD = "res/map/big_clouds.png";
	public static final String STATUS_BAR = "res/UI/health_power_bar.png";
	public static final String LEVEL_COMPLETED = "res/UI/completed_sprite.png";
	public static final String LEVEL_ONE_DATA = "res/map/level_one_data_long.png";
	public static final String POTION_ATLAS = "res/object/potions_sprites.png";
	public static final String CONTAINER_ATLAS = "res/object/objects_sprites.png";
	
//	đọc ảnh character
	public static BufferedImage[] GetSpritesAtlas(String[] fileName) {
		
		BufferedImage[] img = null;
		try {
			img = new BufferedImage[8];
			img[0] = ImageIO.read(new FileInputStream(fileName[0]));
			img[1] = ImageIO.read(new FileInputStream(fileName[1]));
			img[2] = ImageIO.read(new FileInputStream(fileName[2]));
			img[3] = ImageIO.read(new FileInputStream(fileName[3]));
			img[4] = ImageIO.read(new FileInputStream(fileName[4]));
			img[5] = ImageIO.read(new FileInputStream(fileName[5]));
			img[6] = ImageIO.read(new FileInputStream(fileName[6]));
			img[7] = ImageIO.read(new FileInputStream(fileName[7]));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
//	read image
	public static BufferedImage GetImgAtlas(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage[] GetAllLevels(){
		String[] levels = {
				"res/lvls/1.png",
				"res/lvls/2.png",
				"res/lvls/3.png",
		};
		
		BufferedImage[] imgs = new BufferedImage[3];
		for(int i = 0 ;i < 3; i++) {
			try {
				imgs[i] = ImageIO.read(new FileInputStream(levels[i]));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imgs;
	}

}
