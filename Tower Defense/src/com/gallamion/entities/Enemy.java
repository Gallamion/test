package com.gallamion.entities;

	import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gallamion.main.Game;
import com.gallamion.world.AStar;
import com.gallamion.world.Vector2i;
import com.gallamion.world.World;

	public class Enemy extends Entity {
		
		public boolean right = true, left = false;
		
		public int life = 20;

		public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
			super(x, y, width, height, speed, sprite);		
			path = AStar.findPath(Game.world, new Vector2i(World.xINITIAL,World.yINITIAL), new Vector2i(World.xFINAL, World.yFINAL));
			
		}

		public void tick() {
			followPath(path);
			if( x >= Game.WIDTH) {
				Game.life-=1;
				Game.entities.remove(this);		
				
				return;
			}
			if(life <=0) {
				Game.entities.remove(this);
				Game.money+=10;
				return;
			}
		}
		
		public void render(Graphics g) {
			super.render(g);		
			
			g.setColor(Color.red);
			g.fillRect((int)x-2,(int) y-5, 20, 5);
			
			g.setColor(Color.green);
			g.fillRect((int)x-2, (int)y-5, (int)(life/20)*20, 5);
			
			
		}
}		
	


