package com.gallamion.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.gallamion.main.Game;
import com.gallamion.main.Sound;
import com.gallamion.world.Camera;
import com.gallamion.world.World;

public class Player extends Entity {
	
	//player move
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.5;
	
	//player experience
	public static int level = 1;
	public static double currentXP = 0;
	public static double nextLevelXP = 1;
	public static double lastLevelXP = 0;
	public static double xpBetweenLevels;
	public static double xpSinceLevelUp;
	public static double percentageOfXP;
	
	//player animation
	private int frames = 0, maxFrames = 10, index =0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	//shoot
	private boolean hasGun = false;
	public int ammo = 0;
	public boolean shoot = false;
    public boolean mouseShoot = false;
    	
    //shoot CD
    private boolean canShoot = true;
	private int shootCD = 0; 
	private int maxShootCD = 70;
    		
	//shoot sound
	private boolean canPlay = false;
	private int wait = 0; 
	private int maxWait = 1;
	
	
	//life
	public double life = 100, maxlife =100;
	public int mx,my;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);		
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer =  new BufferedImage[4];
		downPlayer =  new BufferedImage[4];
		
		//xpBetweenLevels = nextLevelXP - lastLevelXP;
		//xpSinceLevelUp = currentXP - lastLevelXP;
		//percentageOfXP = xpSinceLevelUp/xpBetweenLevels;
		
		
		for(int i = 0; i < 4; i++) {
		    rightPlayer[i] = Game.character.getSprite(0 + (i*32), (32*3), 32, 32); 
		    }
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.character.getSprite(0 + (i*32), (32*1), 32, 32); 
			}
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.character.getSprite(0 + (i*32), (32*2), 32, 32); 
		    }
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.character.getSprite(0 + (i*32), (32*0), 32, 32);
	        }
	}
	public void tick() {	
				
		depth = 1;
		moved = false;
		if (right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if (left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		else if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;	
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
	
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index ++;
				if(index > maxIndex) {
					index = 0;
				}
			}					
		}
		this.checkCollisionLifePack();
		this.checkCollisionAmmo();
		this.checkCollisionGun();
		this.levelUp();
		this.expBar();
		
		if(shoot) {			
			shoot = false;
			if(hasGun && ammo > 0 && canShoot == true) {
			ammo --;
			canShoot = false;
			if (canPlay) {
			Sound.shoot.play();
			}else {
				wait++;
				if (wait == maxWait) {
				wait = 0;
				canPlay = true;
			}
		}
			int dx = 0;		
			int px = 0;
			int py = 0;
			if (dir == right_dir) {	
				px = 28;
				py = 16;
				dx = 1;
			} else {
				px = -5;
				py = 16;
				dx = -1;				
			}			
			BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py,4,4, null, dx, 0);
			Game.bullets.add(bullet);
			
			}
		}else {
			shootCD++;
			if (shootCD == maxShootCD) {
			shootCD = 0;
			canShoot = true;
		}
	}
		
		if(mouseShoot){				
			mouseShoot = false;
			if(hasGun && ammo > 0 && canShoot == true) {
			ammo --;
			canShoot = false;
			if (canPlay) {
			Sound.shoot.play();
			}else {
				wait++;
				if (wait == maxWait) {
				wait = 0;
				canPlay = true;
			}
		}		
			double angle = 0;			
			int px = 0;
			int py = 0;
			if (dir == right_dir) {								
				px = 28;	
				py = 16;
				angle = Math.atan2(my - (this.getY()+ py - Camera.y), mx - (this.getX()+px - Camera.x));
			} else {				
				px = -5;
				py = 16;
				angle = Math.atan2(my - (this.getY()+ py - Camera.y), mx - (this.getX()+px - Camera.x));
			}
			
			double dx = Math.cos(angle);		
			double dy = Math.sin(angle);
					
			BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py,4,4, null, dx, dy);
			Game.bullets.add(bullet);
			}
		}else {
			shootCD++;
			if (shootCD == maxShootCD) {
			shootCD = 0;
			canShoot = true;
		}
	}
		
		if(life <= 0) {
			life = 0;
			Game.gameState = "GAME_OVER";
		}
				
		
		
		
		Camera.x = Camera.clamp( this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp( this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if (Entity.isColidding(this, atual)) {
					hasGun = true;					
					Game.entities.remove(atual);
				}
			}
		}
	}	
	
	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if (Entity.isColidding(this, atual)) {
					ammo+=50;					
					Game.entities.remove(atual);
				}
			}
		}
	}	
	
	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifePack) {
				if (Entity.isColidding(this, atual)) {
					life+= 15;
					if(life > 100)
						life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}		
	
		public void levelUp() {
			if(currentXP - nextLevelXP >= 0) {
				level++;				
				nextLevelXP =(int)(((4*(Math.pow(level, 3))/5))) +1;
				lastLevelXP = (int)(((4*(Math.pow((level-1), 3))/5))) +1;
				
				//System.out.println(lastLevelXP);
				//System.out.println(percentageOfXP);
				//System.out.println(level);				
			}
		}
	
		public void expBar() {
			xpBetweenLevels = nextLevelXP - lastLevelXP;
			xpSinceLevelUp = currentXP - lastLevelXP;
			percentageOfXP = xpSinceLevelUp/xpBetweenLevels;
		}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
		    g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		    if(hasGun) {
		    	//desenhar arma para direita
		    	g.drawImage(Entity.GUN_RIGHT,this.getX() +8 - Camera.x, this.getY() +4 -Camera.y,null);
		    }
		}else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasGun){
				//desenhar arma para esquerda
				g.drawImage(Entity.GUN_LEFT,this.getX() -8 - Camera.x, this.getY() +4 -Camera.y,null);
			}
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		//super.render(g);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 16, 16);
	}   
}
