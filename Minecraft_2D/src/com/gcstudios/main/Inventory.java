package com.gcstudios.main;

import java.awt.Color;
import java.awt.Graphics;

import com.gcstudios.world.Camera;
import com.gcstudios.world.FloorTile;
import com.gcstudios.world.Tile;
import com.gcstudios.world.WallTile;
import com.gcstudios.world.World;

public class Inventory {
	
	public int selected = 0;
	public boolean isPressed = false;
	public int mx,my;
	
	public boolean isPlaceItem = false;
	public int inventoryBoxSize = 45;
	
	public String[] items = {"grass", "ground", "snow", "sand", "air", ""};
	
	public int initialPosition = ((Game.WIDTH*Game.SCALE)/2) - ((items.length*inventoryBoxSize)/2);
	
	public void tick() {
		if(isPressed) {
			isPressed = false;
			if(mx >= initialPosition && mx < initialPosition + (inventoryBoxSize*items.length)) {
				if(my >= (Game.HEIGHT*Game.SCALE)-inventoryBoxSize -1 && my < (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +inventoryBoxSize ) {
				selected = (int)((mx-initialPosition)/inventoryBoxSize);
				}
			}
		}
		
		if(isPlaceItem) {
			isPlaceItem = false;			
			mx = (int)mx/Game.SCALE + Camera.x;
			my = (int)my/Game.SCALE + Camera.y;
			
			// mx/ tile size
			int tilex = mx/16;
			int tiley = my/16;
			
			if(World.tiles[tilex + tiley * World.WIDTH].solid == false) {
				if(items[selected] == "grass") {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_GRASS);
				}else if (items[selected] == "ground") {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_GROUND);
				}else if (items[selected] == "air") {
					World.tiles[tilex + tiley * World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AIR);
				}else if (items[selected] == "snow") {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_SNOW);
				}else if (items[selected] == "sand") {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_SAND);
				}
				
				if(World.isFree(Game.player.getX(), Game.player.getY()) == false) {
					World.tiles[tilex + tiley * World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AIR);
				}
			}
		}
	}

	public void render (Graphics g) {
		for(int i = 0; i < items.length; i++) {			
			g.setColor(Color.gray);
			g.fillRect(initialPosition + (i*inventoryBoxSize) + 1 , (Game.HEIGHT*Game.SCALE)-inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);
			g.setColor(Color.black);
			g.drawRect(initialPosition + (i*inventoryBoxSize) + 1, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);
			if(items[i] == "grass") {
				
				g.drawImage(Tile.TILE_GRASS,initialPosition + (i*inventoryBoxSize)+7, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +7, 32,32,null);
			}else if(items[i] == "ground") {
				g.drawImage(Tile.TILE_GROUND,initialPosition + (i*inventoryBoxSize)+7, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +7, 32,32,null);
			}else if (items [i] == "air") {
				g.drawImage(Tile.TILE_AIR,initialPosition + (i*inventoryBoxSize)+7, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +7, 32,32,null);
			}else if(items [i] == "snow") {
				g.drawImage(Tile.TILE_SNOW,initialPosition + (i*inventoryBoxSize)+7, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +7, 32,32,null);
			}else if(items [i] == "sand") {
				g.drawImage(Tile.TILE_SAND,initialPosition + (i*inventoryBoxSize)+7, (Game.HEIGHT*Game.SCALE)-inventoryBoxSize +7, 32,32,null);
			}
			if(selected == i) {
				g.setColor(Color.red);
				g.drawRect(initialPosition + (i*inventoryBoxSize) , (Game.HEIGHT*Game.SCALE)-inventoryBoxSize -1, inventoryBoxSize, inventoryBoxSize);
			}
		}
	}
}
