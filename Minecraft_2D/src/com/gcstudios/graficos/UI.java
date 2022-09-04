package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.gcstudios.main.Game;
import com.gcstudios.world.World;

public class UI {

	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;
	
	public void tick() {
		frames++;
		if(frames == 60) {
			//passou 1 seg
			frames = 0;
			seconds++;
			if(seconds == 60) {
				seconds = 0;
				minutes++;
				if(UI.minutes % 1 == 0) {
					World.CICLE++;
					if(World.CICLE > World.night) {
						World.CICLE = 0;
					}
				}			
			}
		}		
	}
	
	public void render(Graphics g) {
		int curLife = (int)((Game.player.life/100)*200);
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH*Game.SCALE - 220, 10, 200, 30);				
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH*Game.SCALE - 220, 10, curLife, 30);		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,20));
		g.drawString((int)(Game.player.life)+"/"+"100", Game.WIDTH*Game.SCALE -200, 35);
		
		String formatTime = "";
		if(minutes < 10) {
			formatTime+="0"+minutes+":";
		}else {
			formatTime+=minutes+":";
		}
		
		if(seconds < 10) {
			formatTime+="0"+seconds;
		}else {
			formatTime+=seconds;
		}
		
		g.setFont(new Font("arial", Font.BOLD,23));
		g.drawString(formatTime, 30, 30);
	}
}
