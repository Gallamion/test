package com.gallamion.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Crab {
	public Random random;
	public double x,y,dx,dy;
	public double spd = 0;
	
	
	public Crab(int x, int y) {
		this.x = x;
		this.y = y;
		double radius = Math.atan2((Game.HEIGHT/2-20) - y, (Game.WIDTH/2 - 20) - x);
		this.dx = Math.cos(radius);
		this.dy = Math.sin(radius);
		this.random = new Random();		
	}
	
	
	public void update() {
		spd = random.nextInt(10 - 2) + 2;
		x+=dx*spd;
		y+=dy*spd;
		
		if(new Rectangle ((int)x, (int)y, 20,20).intersects(Game.maskHole)) {
			Game.crab.remove(this);
		}
		
		checkCollision();
		
	}
	
	public void checkCollision() {
		if(Game.isPressed) {
			Game.isPressed = false;		
		if(Game.mx >= x && Game.mx <= x +30) {
			if(Game.my >= y && Game.my <= y + 30) {
				Game.crab.remove(this);	
				Game.score++;
				return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)(x),(int) y, 16, 16);
	}
	
}
