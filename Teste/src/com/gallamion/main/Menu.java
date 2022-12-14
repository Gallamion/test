package com.gallamion.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.gallamion.entities.Entity;
import com.gallamion.world.World;


public class Menu {
	
	public String[] options = {"Novo Jogo","Carregar Jogo", "Inventário", "Comandos", "Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	
	public boolean up,down, enter;
	
	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	

	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		if(up) {
			up = false;
			currentOption--;
			if(currentOption<0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(enter) {			
			enter = false;
			if(options[currentOption] == "Novo Jogo" || options[currentOption] == "Continuar") {
				Game.gameState = "NORMAL";
				pause = false;	
				file = new File("save.txt");
				file.delete();
			}else if(options[currentOption] == "Carregar Jogo") {
			file = new File("save.txt");
			if(file.exists()) {
				String saver = loadGame(10);
				applySave(saver);
			}
			}else if(options[currentOption] == "Inventário") {
				Game.gameState = "INVENTORY";
			}else if (options[currentOption] == "Sair") {
				System.exit(1);
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
			case"level":
				World.restartGame("level"+spl2[1]+".png");
				Game.gameState = "NORMAL";
				pause = false;
				break;							
			}
		}
	}
	
	public static String loadGame (int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i =0; i < val.length; i++) {
							val [i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="";
					}
				}catch(IOException e){}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i =0; i < val1.length; i++) {
			String current = val1[i];
			current +=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();					
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public void render(Graphics g) {		
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.drawImage(Entity.NPC1_1, 150, 180, null);
		g.drawImage(Entity.NPC2_1, 600, 180, null);
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD,50));
		g.drawString("*Pafu Pafu*", (Game.WIDTH*Game.SCALE) /2 -135, (Game.HEIGHT*Game.SCALE)/2 - 200);
		
		//menu de opçőes
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,20));
		if (pause == false)
			g.drawString("Novo Jogo", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 - 50);
		else 
		g.drawString("Continuar", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 - 50);
		g.drawString("Carregar Jogo", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 - 20);
		g.drawString("Inventário", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 + 10);
		g.drawString("Comandos", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 + 40);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) /2 -70, (Game.HEIGHT*Game.SCALE)/2 + 70);
		
		if(options[currentOption] == "Novo Jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) /2 -100, (Game.HEIGHT*Game.SCALE)/2 - 50);
		}else if(options[currentOption] == "Carregar Jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) /2 -100, (Game.HEIGHT*Game.SCALE)/2 - 20);
		}else if(options[currentOption] == "Inventário") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) /2 -100, (Game.HEIGHT*Game.SCALE)/2 + 10);
		}else if(options[currentOption] == "Comandos") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) /2 -100, (Game.HEIGHT*Game.SCALE)/2 + 40);
		}else if(options[currentOption] == "Sair") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) /2 -100, (Game.HEIGHT*Game.SCALE)/2 + 70);
		}
	}
	
}
