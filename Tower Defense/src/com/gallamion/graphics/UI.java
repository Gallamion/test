package com.gallamion.graphics;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;


public class UI {
	
	public BufferedImage HEARTH = Game.spritesheet.getSprite(0, 16, 16, 16);

	public void render(Graphics g) {
		for(int i = 0; i < Game.life; i++) {			
			g.drawImage(HEARTH,20+(i*30), 10,50,50,null);
		}
		
		g.setFont(new Font("arial", Font.BOLD,24));
		g.setColor(Color.white);
		g.drawString("Money:" +Game.money, (Game.WIDTH*Game.SCALE) -150, 30);
	}
}