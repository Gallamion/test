package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile{

	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	public void render(Graphics g) {
		if(World.CICLE == World.day) {
			g.drawImage(Tile.TILE_AIR, x - Camera.x, y - Camera.y, null);
		}else if(World.CICLE == World.night) {
			g.drawImage(Tile.TILE_NIGHT, x - Camera.x, y - Camera.y, null);
		}
	}
	
}
