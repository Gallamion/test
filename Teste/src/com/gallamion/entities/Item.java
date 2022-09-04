package com.gallamion.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Handler;

import com.gallamion.world.Camera;

public class Item {

	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32, PICKED_UP = -1;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected int x , y, count;
	
	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
	}
	
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		render(g, (int)(x - Camera.x), (int)(y - Camera.y));
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, 	ITEMWIDTH, ITEMHEIGHT, null);
	}
}
