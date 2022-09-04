package com.uninter;

public class ComputadorC extends Computador{		
		
	int jogada = 10;	
	//joga no último slot vazio
	public void jogar() {				
		jogada -= 1;		
	}
}
