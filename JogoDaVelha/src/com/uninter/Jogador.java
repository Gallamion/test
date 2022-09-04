package com.uninter;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogador{
	
	//posição da jogada
	public int pos;
	//armazena as jogadas feitas pelo player
	ArrayList<Integer> playerPos = new ArrayList<Integer>();	
	
	//lê a jogada pelo teclado
	public void jogar() {		
		Scanner scan = new Scanner(System.in);
		System.out.println("Escolha a posição (1-9)");
		pos = scan.nextInt();						
		}
	}

