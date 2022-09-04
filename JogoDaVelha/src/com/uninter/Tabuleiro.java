package com.uninter;

public class Tabuleiro {

	//tabuleiro do jogo da velha formado por 9 slots em branco e desenhado pelos outros caracteres
	public char tabuleiro [][] = {{' ','|',' ','|',' '}, {'-','+','-','+','-'}, {' ','|',' ','|',' '}, {'-','+','-','+','-'}, {' ','|',' ','|',' '}};
	//atribuição de um número para cada jogador e o player começa a partida.
	public int jogador = 1,computador = -1, empate = 0,atual = jogador;
	//verificação para continuar jogando
	public boolean jogoAcabou = false;
	//contagem de turnos
	public int turno = 0;
	
	//confere todas possibilidades do jogo
	public int situacao() {
		//Verificar se o player ganhou horizontal
				if(tabuleiro[0][0] == 'X' && tabuleiro[0][2] == 'X' && tabuleiro[0][4] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				if(tabuleiro[2][0] == 'X' && tabuleiro[2][2] == 'X' && tabuleiro[2][4] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				if(tabuleiro[4][0] == 'X' && tabuleiro[4][2] == 'X' && tabuleiro[4][4] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				
				//Verificar se o player ganhou na vertical
				if(tabuleiro[0][0] == 'X' && tabuleiro[2][0] == 'X' && tabuleiro[4][0] == 'X') {
					//jogo acabou e player ganhou					
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				if(tabuleiro[0][2] == 'X' && tabuleiro[2][2] == 'X' && tabuleiro[4][2] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				if(tabuleiro[0][4] == 'X' && tabuleiro[2][4] == 'X' && tabuleiro[4][4] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}	
				
				
				//Verificar se o player ganhou na diagonal
				if(tabuleiro[0][0] == 'X' && tabuleiro[2][2] == 'X' && tabuleiro[4][4] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				if(tabuleiro[0][4] == 'X' && tabuleiro[2][2] == 'X' && tabuleiro[4][0] == 'X') {
					//jogo acabou e player ganhou
					jogoAcabou = true;
					System.out.println("Você ganhou!");
					return jogador;
				}
				
				
				//Verificar se o NPC ganhou horizontal
				if(tabuleiro[0][0] == 'O' && tabuleiro[0][2] == 'O' && tabuleiro[0][4] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				if(tabuleiro[2][0] == 'O' && tabuleiro[2][2] == 'O' && tabuleiro[2][4] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				if(tabuleiro[4][0] == 'O' && tabuleiro[4][2] == 'O' && tabuleiro[4][4] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				
				//Verificar se o NPC ganhou na vertical
				if(tabuleiro[0][0] == 'O' && tabuleiro[2][0] == 'O' && tabuleiro[4][0] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				if(tabuleiro[0][2] == 'O' && tabuleiro[2][2] == 'O' && tabuleiro[4][2] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				if(tabuleiro[0][4] == 'O' && tabuleiro[2][4] == 'O' && tabuleiro[4][4] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}	
								
				//Verificar se o NPC ganhou na diagonal
				if(tabuleiro[0][0] == 'O' && tabuleiro[2][2] == 'O' && tabuleiro[4][4] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}
				if(tabuleiro[0][4] == 'O' && tabuleiro[2][2] == 'O' && tabuleiro[4][0] == 'O') {
					//jogo acabou e NPC ganhou
					jogoAcabou = true;
					System.out.println("Você perdeu!");
					return computador;
				}	
				
				//empate
				else if(turno == 9 && jogoAcabou == false) {					
					jogoAcabou = true;
					System.out.println("O jogo empatou!");
					return empate;
				}
											
				//Ninguém ganhou, retorna um valor diferente de -1,0 e 1
				return 100;
	}
	
	/*Aqui são usados 2 print para mostrar os caracteres na tela
	um para imprimir o conjunto de caracteres da linha e o outro para quebrar a linha*/
	public void visualizar() {
		for (char[] linha : tabuleiro) {
			for (char c : linha) {
				System.out.print(c);
			}
			System.out.println();
		}
	}
	
	//método para marcar a jogada no tabuleiro
	public void jogada(char[][] tab, int lugar, String vez) {
		this.tabuleiro = tab;
		char marca = 'X';
		
		if(vez.equals("jogador")) {
			marca = 'X';	
			
		}
		else if(vez.equals("computador")) {
			marca = 'O';
		}	
		
					
		switch(lugar) {
			case 1: 
				tabuleiro[0][0] = marca;
				turno += 1;				
				break;
			case 2:
				tabuleiro[0][2] = marca;
				turno += 1;
				break;
			case 3:
				tabuleiro[0][4] = marca;
				turno += 1;
				break;
			case 4:
				tabuleiro[2][0] = marca;
				turno += 1;
				break;
			case 5:
				tabuleiro[2][2] = marca;
				turno += 1;
				break;
			case 6:
				tabuleiro[2][4] = marca;
				turno += 1;
				break;
			case 7:
				tabuleiro[4][0] = marca;
				turno += 1;
				break;
			case 8:
				tabuleiro[4][2] = marca;
				turno += 1;
				break;
			case 9:
				tabuleiro[4][4] = marca;
				turno += 1;
				break;
		}		
		visualizar();
	}
}
