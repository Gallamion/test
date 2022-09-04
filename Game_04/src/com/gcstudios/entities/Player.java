package com.gcstudios.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{

	public boolean right, left;
	
	public static double life = 100;
	
	public static int currentCoins = 0;
	public static int maxCoins = 0;
	
	private double vspd = 0;
	private double gravity = 2;	
	public int dir = 1;
	
	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	
	private int maxSprite = 2;
	private int curSprite = 0;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;		
		/*vspd+=gravity;
		if(!World.isFree((int)x,(int) (y + 1)) && jump){
			vspd = -6;
			jump = false;
		}
		
		if(!World.isFree((int)x,(int) (y + vspd))) {
			int signVsp = 0;
			if(vspd >= 0) {
				signVsp = 1;
			}else {
				signVsp = -1;
			}
			while(World.isFree((int)x,(int) (y + signVsp))) {
				y = y+signVsp;
			}
			vspd = 0;
		}
		
		y = y + vspd;*/
		
		
		
		
		
		if(World.isFree((int)x,(int) (y + gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
			if(Entity.isColidding(this, e)) {
				//aplicar o pulo!					
					isJumping = true;	
				//remorver vida
					((Enemy) e).vida--;
					if(((Enemy) e).vida == 0){
						Game.entities.remove(i);
						break;
					}
			 }
			}
		  }
		}
		
		if(right && World.isFree((int)(x + speed),(int) (y))) {
			x+=speed;
			dir = 1;
		}
		else if (left && World.isFree((int)(x - speed),(int) (y))) {
			x-= speed;
			dir = -1;
		}
		
		if(jump) {
			if(!World.isFree(this.getX(), this.getY() +1)) {
			isJumping = true;
			}else {
				jump = false;
			}
		}
		
		if (isJumping) {
			if(World.isFree(this.getX(), this.getY() -2)) {
				y-=2;
				jumpFrames +=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		//Detectar dano
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
		if(Entity.isColidding(this, e)) {
			if(Entity.rand.nextInt(100)<85)
				life--;
			}
		 }
		}
	  
		//detectar colisão moeda
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Coin) {
		if(Entity.isColidding(this, e)) {
			Game.entities.remove(i);
			Player.currentCoins++;
				break;
			}
		 }
		}
		
		//Camera seguindo o personagem
		Camera.x = Camera.clamp((int)(x - Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)(y - Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
		
	}
	
	public void render(Graphics g) {
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		}else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);;
	}
}
	
	

	