package utliz;

import static utliz.Contants.Enemy.CREP;
import static utliz.Contants.ObjectConstants.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crep;
import main.Game;
import object.Potion;
import object.gameContainer;

public class HelpMethods {
	
//	kiểm tra 4 góc
	public static boolean CanMoveHere(double x, double y, double width, double height, int[][] lvlData) {
		if(!IsSolid(x, y, lvlData)) {
			if(!IsSolid(x+width, y+height, lvlData)) {
				if(!IsSolid(x+width, y, lvlData)) {
					if(!IsSolid(x, y+height, lvlData)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
//	kiểm tra vị trí có phải là map hay không
	private static boolean IsSolid(double x, double y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILE_SIZE;
		if(x < 0 || x >= maxWidth) return true;
		if(y < 0 || y >= Game.GAME_HEIGHT) return true;
		
		double xIndex = x / Game.TILE_SIZE;
		double yIndex = y / Game.TILE_SIZE;
		
		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}
	
//	kiếm tra có phải là ảnh map hay không
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];
//		value == 11 là ảnh thứ 11 trong map.png ( ảnh bị khuyết phải ngoài cùng)
		if(value > 48 || value < 0 || value != 11) return true;
		
		return false;
	}
	
//	check position x
	public static double GetEntityPosNextToWall(Rectangle2D.Double hitBox, double xSpeed) {
		int currenTile = (int) Math.round((hitBox.x / Game.TILE_SIZE));
		if(xSpeed > 0) {
//			Right
			int tileXPos = currenTile*Game.TILE_SIZE;
			int xOffset = (int) (Game.TILE_SIZE - hitBox.width);
			return tileXPos + xOffset - 1;
		}
		else {
//			Left
//			currenTile -= 1;
			return (currenTile * Game.TILE_SIZE);
		}
		
	}
//	check position y
	public static double GetEntityYPosUnderRoofForAboveFloor(Rectangle2D.Double hitBox, double airSpeed) {
		int currenTile = (int) Math.round((hitBox.y / Game.TILE_SIZE));
		if(airSpeed > 0) {
//			Falling
			int tileYPos = currenTile * Game.TILE_SIZE;
			int yOffset = (int) (Game.TILE_SIZE - hitBox.height);
			return tileYPos + yOffset - 1;
		}
		else {
//			Jumping
			return currenTile * Game.TILE_SIZE;
		}
	}
//	kiểm tra vị trí của player có đang đứng trên mặt đất hay không
	public static boolean IsEntityOnFloor(Rectangle2D.Double hitBox, int[][] lvlData) {
		if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
			if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
				return false;

		return true;
	}
	
	public static boolean IsFloor(Rectangle2D.Double hitBox, double xSpeed, int[][] lvlData) {
		return IsSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
	}
	
//	kiểm tra tất cả các ô tính từ vị trí creps đến player có phải map hay không
	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 1; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if (!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}
		
		return true;
	}
	
//	kiểm tra từ vị trí của creps đến player có bị gì chắn hay không
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Double firstHitBox, Rectangle2D.Double secondHitBox, int tileY) {
		int firstXTile = (int) (firstHitBox.x / Game.TILE_SIZE);
		int secondXTile = (int) (secondHitBox.x / Game.TILE_SIZE);
		
		if(firstXTile >= secondXTile) 
			return IsAllTileWalkable(secondXTile, firstXTile, tileY, lvlData);
		else 
			return IsAllTileWalkable(firstXTile, secondXTile, tileY, lvlData);
	}
	
//	create map
	public static int[][] GetMapData(BufferedImage img) {
		
		int[][] mapData = new int[img.getHeight()][img.getWidth()];
		
		for(int i = 0; i < img.getHeight(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if(value >= 48) value = 0;
				mapData[i][j] = value;
			}
		}
		return mapData;
	}
	
//	create creps
	public static ArrayList<Crep> GetCreps(BufferedImage img) {
		ArrayList<Crep> crepList = new ArrayList<>();
		for(int i = 0; i < img.getHeight(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getGreen();
				if(value == CREP) 
					crepList.add(new Crep(j*Game.TILE_SIZE, i*Game.TILE_SIZE));
				
			}
		}
		return crepList;
	}
	
	public static ArrayList<Potion> GetPotions(BufferedImage img) {
		ArrayList<Potion> potions = new ArrayList<>();
		for(int i = 0; i < img.getHeight(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if(value == RED_POTION || value == BLUE_POTION) 
					potions.add(new Potion(j*Game.TILE_SIZE, i*Game.TILE_SIZE, value));
			}
		}
		return potions;
	}
	
	public static ArrayList<gameContainer> GetContainers(BufferedImage img) {
		ArrayList<gameContainer> containers = new ArrayList<>();
		for(int i = 0; i < img.getHeight(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if(value == BARREL || value == BOX) 
					containers.add(new gameContainer(j*Game.TILE_SIZE, i*Game.TILE_SIZE, value));
			}
		}
		return containers;
	}
}
