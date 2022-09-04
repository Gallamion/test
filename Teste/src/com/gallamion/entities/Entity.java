package com.gallamion.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.gallamion.main.Game;
import com.gallamion.world.Camera;
import com.gallamion.world.Node;
//import com.gallamion.world.Tile;


public class Entity {
	
	public static BufferedImage LIFEPACK_EN1 = Game.spritesheet.getSprite(0, 0, 32,32);
	public static BufferedImage LIFEPACK_EN2 = Game.robo.getSprite((32*8), (32*3), 32,32);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite((32*3), 0, 32,32);
	public static BufferedImage BULLET_EN1 = Game.spritesheet.getSprite(32, 0, 32,32);
	public static BufferedImage BULLET_EN2 = Game.robo.getSprite((32*9), (32*3), 32,32);
	public static BufferedImage ENEMY_EN1 = Game.character.getSprite((32*9), (32*2), 32, 32);
	public static BufferedImage ENEMY_EN2 = Game.robo.getSprite((32*7), (32*0), 32, 32);
	public static BufferedImage ENEMY_FEEDBACK = Game.character.getSprite((32*9), (32*3), 32, 32);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite((32*3), 0, 32,32);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite((32*2), 0, 32,32);
	public static BufferedImage NPC1 = Game.character.getSprite((32*0), (32*5), 32,32);
	public static BufferedImage NPC2 = Game.character.getSprite((32*0), (32*4), 32,32);
	
	//redimensionados
	public static Image NPC1_1 = NPC1.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
	public static Image NPC2_1 = NPC2.getScaledInstance(160, 160, Image.SCALE_SMOOTH);

	public double x;
	public double y;
	public double width;
	public double height;	
	
	public int depth;
	
	protected List<Node> path;
	
	public double speed = 1;
	
	private BufferedImage sprite;	
	
	public int maskx, masky, mwidth, mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext, ynext, 32, 32);		
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
		Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), 32, 32);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}		
		return false;
	}
	
	
	
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);	}
	

	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
}
