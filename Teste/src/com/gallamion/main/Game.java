package com.gallamion.main;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import javax.swing.JFrame;

import com.gallamion.entities.BulletShoot;
import com.gallamion.entities.Enemy;
import com.gallamion.entities.Entity;
import com.gallamion.entities.NPC;
import com.gallamion.entities.Player;
import com.gallamion.graphics.Spritesheet;
import com.gallamion.graphics.UI;
import com.gallamion.world.Tile;
import com.gallamion.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
		
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	public static int SCALE = 2;
	
	public static int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private BufferedImage image;
	
	public static List<Entity> entities;	
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	public static Spritesheet character;
	public static Spritesheet robo;
	public static Spritesheet water;
	
		
	public static World world;	
	public static Player player;	
	public static Random rand;
	
	public UI ui;
		
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public Menu menu;
	public Inventory inventory;
	
	public NPC npc;
	
	
	public static int[] pixels;
	public static int[] minimapPixels;
	
	public static BufferedImage minimap;
	
		
	public boolean saveGame = false;
	
	public Game() {		
		//Sound.music.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);	
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();		
		
		spritesheet = new Spritesheet("/spritesheet.png");	
		character = new Spritesheet("/character.png");	
		robo = new Spritesheet("/robo.png");
		water = new Spritesheet ("/water_tile.png");
		player = new Player(0,0, 32, 32, character.getSprite(0, 0, 32, 32));
		entities.add(player);
		world = new World("/level1.png");		
		minimap = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt) minimap.getRaster().getDataBuffer()).getData();		
		npc = new NPC(1000,32, 32, 32, character.getSprite((32*4), 0, 32, 32));
		
		entities.add(npc);
		
		menu = new Menu();
		inventory = new Inventory();
		
	}
	
	public void initFrame() {
		frame = new JFrame("Pafu Pafu");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		//icone da janela 
		/*
		Image icon1 = null;
		try {
			icon1 = ImageIO.read(getClass().getResource("/arquivo.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image icone2 = toolkit.getImage(getClass().getResource("/arquivo2.png"));
		Cursor c = toolkit.createCustomCursor(icone2, new Point(0,0) , "img");
		frame.setCursor(c);
		frame.setIconImage(icon1);
		frame.setAlwaysOnTop(true);
		//fim dos cursores e icones 
		*/
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
		public static void main(String args[]) {
			Game game = new Game();
			game.start();
		}
		
		
		public void tick() {			
			
			Tile.tick();
			if(gameState == "NORMAL") {				
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {CUR_LEVEL};
				Menu.saveGame(opt1,opt2,10);
				System.out.println("Jogo Salvo");
			}
			this.restartGame = false;	
			for(int i = 0; i < entities.size(); i++) {				
				Entity e = entities.get(i);
				e.tick();						
			}
			
			for(int i =0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			
			if(enemies.size() == 0) {				
				//prox fase
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				String newWorld = "level" +CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}
			}else if(gameState == "GAME_OVER") {
				this.framesGameOver++;
				if(this.framesGameOver == 50) {
					this.framesGameOver = 0;
					if(this.showMessageGameOver)
						this.showMessageGameOver = false;
					else
						this.showMessageGameOver = true;
				}
				
				if(restartGame) {					
					this.restartGame = false;
					gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level" +CUR_LEVEL + ".png";
					World.restartGame(newWorld);
				}
			}else if(gameState == "MENU") {
				menu.tick();
			}else if(gameState == "INVENTORY") {
				inventory.tick();
			}
		}
		
		/*
		   public void drawRectangleExample(int xoff, int yoff) {
			for(int xx =0; xx < 32; xx++) {
				for(int yy = 0; yy < 32; yy++) {
					int xOff = xx + xoff;
					int yOff = yy + yoff;
					if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
						continue;
					pixels[xOff + (yOff*WIDTH)] = 0xff0000; 
				}
			}
		}
		*/
		
		public void render() {
			BufferStrategy bs = this.getBufferStrategy();
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			g.setColor(new Color(0,0,0));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			/*renderização do joog*/
			//Graphics2D g2 = (Graphics2D) g;
			world.render(g);
			Collections.sort(entities, Entity.nodeSorter);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
			for(int i =0; i < bullets.size(); i++) {
				bullets.get(i).render(g);
			}
			ui.render(g);
			/***/			
			g.dispose();
			g = bs.getDrawGraphics();			
			g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
			
			//munição
			g.setFont(new Font("arial", Font.BOLD,17));
			g.setColor(Color.white);
			g.drawString("Munição: " +player.ammo, 800, 35);
			
			//States
			if(gameState == "GAME_OVER") {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(0,0,0,100));
				g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
				g.setFont(new Font("arial", Font.BOLD,40));
				g.setColor(Color.red);
				g.drawString("Game Over", (WIDTH*SCALE)/2 - 80, (HEIGHT*SCALE)/2+ 25);
				g.setFont(new Font("arial", Font.BOLD,30));
				g.setColor(Color.white);
				if(showMessageGameOver)
					g.drawString(">Pressione Enter para reiniciar", (WIDTH*SCALE)/2 - 180, (HEIGHT*SCALE)/2+ 90);
			}else if (gameState == "MENU") {
				menu.render(g);
			} if (gameState == "INVENTORY") {
				inventory.render(g);
			}
			
			//Minimap
			World.renderMiniMap();
			if (Game.gameState == "NORMAL") {
			g.drawImage(minimap, 750, 50, World.WIDTH*5,World.HEIGHT*5, null);
			}
			
			//Geral
			bs.show();
			
		}
	  	
		
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
				if (System.currentTimeMillis() - timer >= 1000) {
					System.out.println("FPS "+ frames);
					frames = 0;
					timer+=1000;
				}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;

			if(gameState == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
			if(gameState == "MENU") {
				menu.down = true;
			}
		}		
		
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			Menu.pause = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(gameState == "NORMAL") {
			this.saveGame = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F) {
			if(npc.distanceMsg == true) {
		  	if(npc.showMessage == true) {
				npc.showMessage = false;
		  	}else
				npc.showMessage = true;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;				
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;		
		}			
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			
			if(gameState == "MENU") {				
				menu.enter = true;
				
			}
		}
		
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		player.mouseShoot = true;		
		player.mx = (e.getX()/SCALE);
		player.my = (e.getY()/SCALE);		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
