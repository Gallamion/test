package com.gallamion.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.gallamion.enemies.Robot1;
import com.gallamion.enemies.Robot2;
import com.gallamion.entities.Bullet;
import com.gallamion.entities.Enemy;
import com.gallamion.entities.Entity;
import com.gallamion.entities.LifePack;
import com.gallamion.entities.Particle;
import com.gallamion.entities.Player;
import com.gallamion.entities.Weapon;
import com.gallamion.graphics.Spritesheet;
import com.gallamion.main.Game;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
		    int[] pixels = new int[map.getWidth() * map.getHeight()];
		    WIDTH = map.getWidth();
		    HEIGHT = map.getHeight();
		    tiles = new Tile[map.getWidth() * map.getHeight()];
		    map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
		    for(int xx = 0; xx < map.getWidth(); xx++) {
		    	for (int yy = 0; yy < map.getHeight(); yy++) {
		    		int pixelAtual = pixels[xx + (yy*map.getWidth())];
		    		
		    		
		    		//floor general
		    		if(Game.CUR_LEVEL == 1) {
		    			tiles[xx +(yy *WIDTH)] = new FloorTile(xx*32, yy*32, Tile.TILE_FLOOR1);
		    			}else if (Game.CUR_LEVEL == 2) {
		    				tiles[xx +(yy *WIDTH)] = new FloorTile(xx*32, yy*32, Tile.TILE_FLOOR2);
		    			}
		    		
		    		
		    		
		    		
		    		
		    		//Floor
		    		if (pixelAtual == 0xFF000000) {		    			
		    			if(Game.CUR_LEVEL == 1) {
		    			tiles[xx +(yy *WIDTH)] = new FloorTile(xx*32, yy*32, Tile.TILE_FLOOR1);
		    			
		    			}else if (Game.CUR_LEVEL == 2) {
		    				tiles[xx +(yy *WIDTH)] = new FloorTile(xx*32, yy*32, Tile.TILE_FLOOR2);
		    				
		    		}
		    		}	
		    		
		    		
		    		
		    		
		    			
		    			//Wall
		    		else if (pixelAtual == 0xFFFFFFFF){
		    			if(Game.CUR_LEVEL == 1) {
		    			tiles[xx +(yy *WIDTH)] = new WallTile(xx*32, yy*32, Tile.TILE_WALL1);		    		
		    		}else if (Game.CUR_LEVEL == 2) {
		    			tiles[xx +(yy *WIDTH)] = new WallTile(xx*32, yy*32, Tile.TILE_WALL2);
		    		}
		    		}
		    		
		    		
		    		
		    		
		    		
		    		
		    			//Player
		    		else if(pixelAtual == 0xFF0026FF) {		    			
		    			Game.player.setX(xx*32);
		    			Game.player.setY(yy*32);
		    		}	
		    		
		    		
		    		
		    			
		    			//enemy
		    		else if(pixelAtual == 0xFFFF0000) {	
		    			//if(Game.CUR_LEVEL == 1) {
		    			Enemy enemy = new Robot1(xx*32, yy*32, 32, 32, Entity.ENEMY_EN1);
		    			Game.entities.add(enemy);
		    			Game.enemies.add(enemy);
		    		//}	
		    		}	else if(pixelAtual == 0xFFFF0018) {	
		    			//if(Game.CUR_LEVEL == 1) {
		    			Enemy enemy = new Robot2(xx*32, yy*32, 32, 32, Entity.ENEMY_EN2);
		    			Game.entities.add(enemy);
		    			Game.enemies.add(enemy);
		    		}
		    		
		    		
		    		
		    		
		    		
		    			//weapon
		    		else if(pixelAtual == 0xFF7F3300) {	
		    			//if(Game.CUR_LEVEL == 1) {
		    			Game.entities.add(new Weapon(xx*32, yy*32, 32, 32, Entity.WEAPON_EN));
		    		//}	
		    		}	
		    			//life pack  
		    		else if(pixelAtual == 0xFFFF7FED) {	
		    			if(Game.CUR_LEVEL == 1) {
		    			LifePack pack = new LifePack(xx*32, yy*32, 32, 32, Entity.LIFEPACK_EN1);
		    			Game.entities.add(pack);
		    		}else if (Game.CUR_LEVEL == 2) {
		    			LifePack pack = new LifePack(xx*32, yy*32, 32, 32, Entity.LIFEPACK_EN2);
		    			Game.entities.add(pack);
		    		}
		    		}	
		    			//bullet
		    		else if(pixelAtual == 0xFFFFD800) {	
		    			if(Game.CUR_LEVEL == 1) {
		    			Game.entities.add(new Bullet(xx*32, yy*32, 32, 32, Entity.BULLET_EN1));
		    		}else if (Game.CUR_LEVEL == 2) {
		    			Game.entities.add(new Bullet(xx*32, yy*32, 32, 32, Entity.BULLET_EN2));
		    		}
		    		}
		         }
		    }
		    
		 } catch (IOException e) {
			e.printStackTrace();
		}
	 }
	
	public static void generateParticles(int amount, int x, int y) {
		for(int i = 0; i < amount; i++){
			Game.entities.add(new Particle(x,y,1,1,null));
		}
	}
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height-1) / TILE_SIZE;
		
		int x4 = (xnext + width -1) / TILE_SIZE;
		int y4 = (ynext + height-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));		
	}
	
	 
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));		
	}
	
	public static void restartGame(String level) {
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");	
		Game.character = new Spritesheet("/character.png");
		Game.player = new Player(0,0, 32, 32, Game.character.getSprite(0, 0, 32, 32));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		Game.minimap = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.minimapPixels = ((DataBufferInt) Game.minimap.getRaster().getDataBuffer()).getData();
	}
	
	 public void render(Graphics g) {			
				int xstart = (Camera.x / 32);
				int ystart = (Camera.y / 32);
				
				//rever +10 gambiarra
				int xfinal = xstart + (10 + Game.WIDTH / 32);
				int yfinal = ystart + (Game.HEIGHT / 32);
				
				for(int xx = xstart; xx <= xfinal; xx++) {
					for(int yy = ystart; yy <= yfinal; yy++) {
						if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
							continue;
						Tile tile = tiles[xx + (yy*WIDTH)];
						tile.render(g);

			 }
		 }
	 }
	 
	 public static void renderMiniMap() {
		 if (Game.gameState == "NORMAL"){
		 for(int i =0; i < Game.minimapPixels.length; i++) {
			 Game.minimapPixels[i] = 0;
		 }
		 for(int xx =0; xx < WIDTH; xx++) {
			 for(int yy = 0; yy < HEIGHT; yy++) {
				 if(tiles[xx + (yy*WIDTH)] instanceof WallTile) {
					 Game.minimapPixels[xx + (yy*WIDTH)] = 0xffffff;
				 }	
				 
			 }
		 }		 
		 int xPlayer = Game.player.getX()/32;
		 int yPlayer = Game.player.getY()/32;
		 
		
		 Game.minimapPixels[xPlayer + (yPlayer*WIDTH)] = 0x00ffff;
		 
		 for(int i=0; i < Game.entities.size(); i++) {
			 Entity e = Game.entities.get(i);
			 if(Game.entities.get(i) instanceof Enemy) {
				 int xEntity = e.getX()/32;
				 int yEntity = e.getY()/32;
				 
			Game.minimapPixels[xEntity + (yEntity*WIDTH)] = 0xff0000;
			 }
			 else if (Game.entities.get(i) instanceof Bullet) {
				 int xEntity = e.getX()/32;
				 int yEntity = e.getY()/32;
				 
			Game.minimapPixels[xEntity + (yEntity*WIDTH)] = 0xffff00;
			 }
			 else if (Game.entities.get(i) instanceof Weapon) {
				 int xEntity = e.getX()/32;
				 int yEntity = e.getY()/32;
				 
			Game.minimapPixels[xEntity + (yEntity*WIDTH)] = 0xFF7F3300;
			 }
			 else if (Game.entities.get(i) instanceof LifePack) {
				 int xEntity = e.getX()/32;
				 int yEntity = e.getY()/32;
				 
			Game.minimapPixels[xEntity + (yEntity*WIDTH)] = 0xFFFF7FED;
			 }
		 }
	 }
	 }
}

