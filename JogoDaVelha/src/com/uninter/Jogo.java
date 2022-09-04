package com.uninter;

import java.util.Scanner;

public class Jogo {

	//condição para iniciar o jogo
	static boolean start = false;	
	//escolha da dificuldade
	static int dif = 0;
	
	//instanciando cada ator
	static Tabuleiro tab = new Tabuleiro();
	static Jogador jogador = new Jogador();	
	static ComputadorA computadorA = new ComputadorA();
	static ComputadorB computadorB = new ComputadorB();
	static ComputadorC computadorC = new ComputadorC();
	
	//chamando os métodos
	public static void main(String[] args) {	
		dificuldade();
		turnos();		
	}

	//escolha da dificuldade do computador
	private static void dificuldade() {
		System.out.println("Bem-vindo ao jogo da velha");		
		while (start == false) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Escolha a dificuldade do computador (1-3)");			
			int dificuldade = scan.nextInt();
			
			if(dificuldade == 1) {
				dif = 1;
				start = true;
			}
			else if(dificuldade == 2) {
				dif = 2;
				start = true;
			}
			else if(dificuldade == 3) {
				dif = 3;
				start = true;
			}
			else {
				System.out.println("Dificuldade inválida");			
			}
		}		
	}
	
	//lógica de turnos para realizar as jogadas
	private static void turnos() {
		if(start == true) {
			while(tab.jogoAcabou == false) {
				if(tab.atual == tab.jogador) {						
					jogador.jogar();	
					if (0 < jogador.pos && jogador.pos < 10) {
						if (jogador.playerPos.contains(jogador.pos) || computadorA.computadorPos.contains(jogador.pos) || 
								computadorB.computadorPos.contains(jogador.pos) || computadorC.computadorPos.contains(jogador.pos)) {
							System.out.println("Jogada inválida");							
						}else {
						tab.jogada(tab.tabuleiro, jogador.pos, "jogador");	
						tab.situacao();					
						tab.atual = tab.computador;		
						jogador.playerPos.add(jogador.pos);
							if(tab.jogoAcabou == false) {
								System.out.println("Vez do computador");
							}
						}	
					}else {
						System.out.println("Jogada inválida");
					}
				}	
				if (tab.jogoAcabou == false) {					
					if(tab.atual == tab.computador && dif == 1) 
					{							
						computadorA.jogar();
						if (jogador.playerPos.contains(computadorA.jogada) || computadorA.computadorPos.contains(computadorA.jogada)) {							
							}	
						else {
						tab.jogada(tab.tabuleiro, computadorA.jogada, "computador");						
						computadorA.computadorPos.add(computadorA.jogada);
						tab.situacao();
						tab.atual = tab.jogador;						
						}
					}
					if(tab.atual == tab.computador && dif == 2) 
					{				
						computadorB.jogar();
						if (jogador.playerPos.contains(computadorB.jogada) || computadorB.computadorPos.contains(computadorB.jogada)) {							
							}	
						else {
						tab.jogada(tab.tabuleiro, computadorB.jogada, "computador");						
						computadorB.computadorPos.add(computadorB.jogada);
						tab.situacao();
						tab.atual = tab.jogador;						
						}						
					}
					if(tab.atual == tab.computador && dif == 3) 
					{				
						computadorC.jogar();
						if (jogador.playerPos.contains(computadorC.jogada) || computadorC.computadorPos.contains(computadorC.jogada)) {							
							}	
						else {
						tab.jogada(tab.tabuleiro, computadorC.jogada, "computador");						
						computadorC.computadorPos.add(computadorC.jogada);
						tab.situacao();
						tab.atual = tab.jogador;						
						}								
					}				
				}				
			}
		}		
	}
}
