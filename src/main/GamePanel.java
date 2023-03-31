package main;


import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import inputs.KeyBoardInputs;

import inputs.MouseInputs;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

import static utliz.Contants.PlayerContants.*;
import static utliz.Contants.Direction.*;
import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;;

public class GamePanel extends JPanel{
	private Game game;
	private MouseInputs mouseInputs;
	private KeyBoardInputs keyBoardInputs;
	
	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		keyBoardInputs = new KeyBoardInputs(this);
		
		this.game = game;
		this.setPanelSize();
		this.addKeyListener(keyBoardInputs);
		this.addMouseListener(mouseInputs);
		this.addMouseMotionListener(mouseInputs);
//		this.requestFocus();
	}
	

	private void setPanelSize() {
//		chia màn hình thành các ô vuông 32x32
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
//		System.out.println(GAME_WIDTH + " " + GAME_HEIGHT);
	}
	
//	public void updateGame() {
//		
//	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
		
	}

	public Game getGame() {
		return game;
	}
	
}
