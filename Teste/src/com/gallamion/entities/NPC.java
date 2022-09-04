package com.gallamion.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;

public class NPC extends Entity {

	public String[] frases = new String[2];
	
	public boolean showMessage = false;
	public boolean distanceMsg = false;
	
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	
	public int time = 0;
	public int maxTime = 5;
	
	 int xPlayer = Game.player.getX();
	 int yPlayer = Game.player.getY();
	
	 int xNPC = (int)x;
	 int yNPC = (int)y;
	
	public NPC(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);	
		frases[0] = "Oni-chan baaaaka *w*      ";				
		frases[1] = "Vá matar estes monstros ";
	}
	
	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNPC = (int)x;
		int yNPC = (int)y;
		
		/*if(Math.abs(xPlayer - xNPC) < 20 && Math.abs(yPlayer - yNPC) < 20) {
			showMessage = true;
		}else {
			showMessage = false;
		}*/
		
		
		this.time++;
		if(Math.abs(xPlayer - xNPC) < 30 && Math.abs(yPlayer - yNPC) < 30) {
			distanceMsg = true;
		if(showMessage) {	
			if(this.time >= this.maxTime) {		
				this.time = 0;
			if(curIndexMsg < frases[fraseIndex].length() -1) {
				curIndexMsg++;				
		}else {			
			if(fraseIndex < frases.length -1) {
				fraseIndex++;
				curIndexMsg = 0;
				
					}
				}
			}
		  }
		}else {
			distanceMsg = false;
			showMessage = false;
		}
	}

	
	
	public void render(Graphics g) {
		super.render(g);
		if(showMessage == true && distanceMsg == true) {
			g.setColor(Color.black);
			g.fillRect(20, 180, Game.WIDTH - 100, Game.HEIGHT - 200);
			g.setFont(new Font ("Arial", Font.BOLD,15));
			g.setColor(Color.white);
			g.drawString("Mariah:", Game.WIDTH -450, (Game.HEIGHT - 115));
			g.setFont(new Font ("Arial", Font.BOLD,9));
			g.drawString(frases[fraseIndex].substring(0, curIndexMsg), Game.WIDTH -450, (Game.HEIGHT - 90));
		}
	}
}
