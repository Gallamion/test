package com.gallamion.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gallamion.entities.Player;
//import com.gallamion.entities.Player;
import com.gallamion.main.Game;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 8, 100, 8);
		g.setColor(Color.green);
		g.fillRect(8, 8, (int)((Game.player.life/Game.player.maxlife)*100), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,9));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife, 15, 16);
		g.setFont(new Font("arial", Font.BOLD,11));
		g.drawString("Level: " +Player.level, 10, 32);		
		g.setColor(Color.black);
		g.fillRect(8, 35, 100, 5);
		g.setColor(Color.yellow);
		g.fillRect(8, 35, (int)(Player.percentageOfXP*100), 5);
		//g.setFont(new Font("arial", Font.BOLD,11));
		//g.drawString("FPS: " +Game.frames, 10, 50);
	}
	
}
