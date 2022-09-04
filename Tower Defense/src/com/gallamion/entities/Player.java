package com.gallamion.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;


public class Player extends Entity{
	
	
	public int xTarget;
	public int yTarget;
	public boolean isAttacking = false;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		Enemy enemy = null;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				int xEnemy = e.getX();
				int yEnemy = e.getY();
				if(Entity.calculateDistance(this.getX(),this.getY(), xEnemy, yEnemy) < 40) {
					enemy = (Enemy)e;
				}
			}
		}
		if(enemy != null) {
			isAttacking = true;
			xTarget = enemy.getX();
			yTarget = enemy.getY();
			if(Entity.rand.nextInt(100) < 10) {
				enemy.life-=Entity.rand.nextDouble();
			}
		}else {
			isAttacking = false;
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(isAttacking) {
			g.setColor(Color.red);
			g.drawLine((int)x+7, (int)y+2, xTarget+6, yTarget+6);
		}
	}
	
}
	
	
	

