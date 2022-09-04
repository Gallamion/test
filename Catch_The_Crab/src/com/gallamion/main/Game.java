package com.gallamion.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable, MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 480;
	public static int HEIGHT = 480;
	
	public static List<Crab> crab;
	public Spawner spawner;
	
	public static Rectangle maskHole;
	public static int mx,my;
	public static boolean isPressed = false;
	
	public static int score = 0;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		crab = new ArrayList<>();
		spawner = new Spawner();
		
		maskHole = new Rectangle(Game.WIDTH/2 -35, Game.HEIGHT/2 -35, 60, 60);
	}
	
	public void update() {
		spawner.update();
		for(int i = 0; i < crab.size(); i++) {
			crab.get(i).update();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(255,229,102));
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.black);
		g.fillOval(Game.WIDTH/2 -35, Game.HEIGHT/2 -35, 70, 70);
		for(int i = 0; i < crab.size(); i++) {
			crab.get(i).render(g);
		}
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 19));
		g.drawString("Score: "+Game.score, 10, 15);
		g.dispose();
		bs.show();
		
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle("Catch the crab");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();	
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	public void run() {
		double fps = 60.0;
		while(true) {
			update();
			render();
			try {
				Thread.sleep((int)(1000/fps));
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isPressed = true;
		mx = e.getX();
		my = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
