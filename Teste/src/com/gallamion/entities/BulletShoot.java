package com.gallamion.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;
import com.gallamion.world.Camera;
import com.gallamion.world.World;

public class BulletShoot extends Entity {

	
	private double dx;
	private double dy;
	private double spd = 3;
	
	private int life = 100, curLife = 0;
	
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);	
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {		
		if(World.isFreeDynamic((int)(x+(dx*spd)),(int)(y+(dy*spd)), 4, 4)) {
		x+= dx*spd;
		y+= dy*spd;
		}else {		 
			Game.bullets.remove(this);
			World.generateParticles(100,(int)x,(int)y);
			return;
		}		
		curLife++;
		if (curLife == life) {
			Game.bullets.remove(this);
			return;
			}								
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX()- Camera.x,this.getY() - Camera.y , (int)(width), (int)(height));
	}
	
}
