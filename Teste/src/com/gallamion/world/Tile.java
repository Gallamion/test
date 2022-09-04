package com.gallamion.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR1 = Game.character.getSprite((32*9), (32*1), 32, 32);
	public static BufferedImage TILE_FLOOR2 = Game.robo.getSprite((32*6), (32*6), 32, 32);	
	public static BufferedImage TILE_WALL_WATER = Game.water.getSprite(0,0 ,32, 32);
	public static BufferedImage TILE_WALL1 = Game.character.getSprite((32*9), (32*4), 32, 32);
	public static BufferedImage TILE_WALL2 = Game.robo.getSprite((32*5), (32*6), 32, 32);
	
	private BufferedImage sprite;
	private int x,y;			
	
	public int maskx, masky, mwidth, mheight;
	
	private static int frames = 0;
	private static int maxFrames = 15;
	private static int index =0;
	private static int maxIndex = 3;
	private BufferedImage[] water;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;	
	
		water = new BufferedImage[4];
		water[0] = Game.water.getSprite((32*0), (32*0), 32, 32);
		water[1] = Game.water.getSprite((32*1), (32*0), 32, 32);
		water[2] = Game.water.getSprite((32*2), (32*0), 32, 32);
		water[3] = Game.water.getSprite((32*3), (32*0), 32, 32);
	}
	
	public static void tick() {
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index ++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		/*if() {
			g.drawImage(water[index], x - Camera.x, y - Camera.y, null);
		}else*/
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
}
