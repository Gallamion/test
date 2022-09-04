package com.gallamion.entities;

import java.awt.Color;
//import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.gallamion.main.Game;
//import com.gallamion.main.Sound;
import com.gallamion.world.AStar;
import com.gallamion.world.Camera;
import com.gallamion.world.Node;
import com.gallamion.world.Vector2i;
//import com.gallamion.world.World;

public class Enemy extends Entity {
	
	

	private int frames = 0, maxFrames = 15;
	private int index =0;
	private int maxIndex = 1;
	private BufferedImage[] sprites;
	private BufferedImage[] damagedSprites;
	
	public double life = 10;
	public double maxLife = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);	
		sprites = new BufferedImage[2];
		sprites[0] = Game.character.getSprite((32*0), (32*6), 32, 32);
		sprites[1] = Game.character.getSprite((32*1), (32*6), 32, 32);
		
		damagedSprites = new BufferedImage[2];
		damagedSprites[0] = Game.character.getSprite((32*2), (32*6), 32, 32);
		damagedSprites[1] = Game.character.getSprite((32*3), (32*6), 32, 32);
		
	
	}

	public void tick() {
		depth = 0;
		/*
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 300) {
		if(isColiddingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			x+=speed;
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColidding((int)(x-speed), this.getY())) {
			x-=speed;
		}
		
	    if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
	    		&& !isColidding(this.getX(), (int)(y+speed))) {
			y+=speed;
		}
		else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			y-=speed;
		}			
		}	else {//estamos colidindo
			if(Game.rand.nextInt(100) <10) {
				Sound.hurtEffect.play();
			Game.player.life -= Game.rand.nextInt(3);
			}			
			//System.out.println("Vida: "+Player.life);
		}
		
		}
		*/
		maskx = 0;
		masky = 0;
		mwidth = 32;
		mheight = 32;
		
		if(!isColiddingWithPlayer()) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/32)),((int)(y/32)));
				Vector2i end = new Vector2i(((int)(Game.player.x/32)),((int)(Game.player.y/32)));
				path = AStar.findPath(Game.world, start, end);
			}
		}else {
			if(new Random().nextInt(100) < 5) {
				//Sound.hurtEffect.play();
				Game.player.life-=Game.rand.nextInt(3);				
			}
		}
			if(new Random().nextInt(100) < 70)
				followPath(path);
			
			
				if(new Random().nextInt(100) < 30) {
					Vector2i start = new Vector2i(((int)(x/32)),((int)(y/32)));
					Vector2i end = new Vector2i(((int)(Game.player.x/32)),((int)(Game.player.y/32)));
					path = AStar.findPath(Game.world, start, end);
				}
			
			
		
	       frames++;
			if(frames == maxFrames) {
				frames = 0;
				index ++;
				if(index > maxIndex) {
					index = 0;
				}
			}	
			
			collidingBullet();
			
			if(life <=0) {
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}
		}   
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);					
					return;
				}
				
			}
		}		
		
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;				
				if(x < target.x * 32) {
					x+=speed;
				}else if(x > target.x * 32) {
					x-=speed;
				}
				
				if(y < target.y * 32) {
					y+=speed;
				}else if(y > target.y * 32) {
					y-=speed;
				}
				
				if(x == target.x * 32 && y == target.y * 32) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() +maskx, this.getY() +masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),32,32);
		
		return enemyCurrent.intersects(player);
	}
	
	
	
	public void render(Graphics g) {
		/*if(life >= 5) {		
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else   {
				g.drawImage(damagedSprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}*/
		
		int curLife = (int)((life/maxLife)*30);
		g.setColor(Color.red);
		g.fillRect(this.getX()  -Camera.x,this.getY()  -Camera.y, (int)maxLife+20, 3);
		g.setColor(Color.green);
		g.fillRect(this.getX()  -Camera.x,this.getY()  -Camera.y,curLife, 3);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() +maskx -Camera.x, this.getY() +masky - Camera.y, mwidth, mheight);
	}
}
