package utliz;

import main.Game;

public class Contants {
	
	public static final double GRAVITY = 0.04f * Game.SCALE;
	public static final int ANISPEED = 10;
	
	public static class ObjectConstants {

		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_POTION, BLUE_POTION:
				return 7;
			case BARREL, BOX:
				return 8;
			}
			return 1;
		}
	}
	
	public static class Enemy {
		public static final int  CREP = 0;
		
		public static final int IDLE = 0;
		public static final int ATTACK = 1;
		public static final int RUN = 2;
		public static final int DEATH = 3;
		public static final int HIT = 4;
//		public static final int JUMP = 5;
//		public static final int FALL = 6;
//		public static final int GROUND = 7;
		
		public static final int CREP_WIDTH_DEFAULT = 50;
		public static final int CREP_HEIGHT_DEFAULT = 38;
		public static final int CREP_WIDTH = (int) (CREP_WIDTH_DEFAULT*Game.SCALE);
		public static final int CREP_HEIGHT = (int) (CREP_HEIGHT_DEFAULT*Game.SCALE);
		
		public static final int CREP_DRAWOFFSET_X = (int) (15 * Game.SCALE);
		public static final int CREP_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch(enemyType) {
			case CREP:
				switch(enemyState) {
				case IDLE:
					return 11;
				case ATTACK:
					return 5;
				case RUN:
					return 6;
				case DEATH:
					return 4;
				case HIT:
					return 2;
				}
			}
			return 0;
		}
		
		public static int getMaxHealth(int enemyType) {
			switch(enemyType) {
			case CREP:
				return 10;
			default:
				return 1;
			}
		}
		
		public static int getEnemyDmg(int enemyType) {
			switch(enemyType) {
			case CREP:
				return 15;
			default:
				return 0;
			}
		}
		
		
	}
	
	public static class Environment{
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI{
		
		public static class Buttons{
			public static final int BTN_WIDTH_DEFAULT = 140;
			public static final int BTN_HEIGHT_DEFAULT = 56;
			public static final int BTN_WIDTH = (int) (BTN_WIDTH_DEFAULT*Game.SCALE);
			public static final int BTN_HEIGHT = (int) (BTN_HEIGHT_DEFAULT*Game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE =(int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class URMButtons {
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE =(int) (URM_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class VolumeButtons {
			public static final int VOLUME_WIDTH_DEFAULT = 28;
			public static final int VOLUME_HEIGHT_DEFAULT = 44;
			public static final int SLIDER_WIDTH_DEFAULT = 215; 
			public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT*Game.SCALE);
		}
	}
	
	public static class PlayerContants{
		public static final int IDLE = 0;
		public static final int ATTACK = 1;
		public static final int RUN = 2;
		public static final int DEATH = 3;
		public static final int HIT = 4;
		public static final int JUMP = 5;
		public static final int FALL = 6;
		public static final int GROUND = 7;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
				case IDLE:
					return 12;
				case ATTACK:
					return 5;
				case RUN:
					return 6;
				case DEATH:
					return 4;
				case HIT:
					return 2;
				default:
					return 1;
			}
		}
	}
	
	public static class Direction{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
//		public static final int DOWN = 3;
	}
}
	
