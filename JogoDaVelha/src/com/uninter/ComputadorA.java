package com.uninter;

import java.util.Random;

public class ComputadorA extends Computador {
	
	//escolhe aleat�riamente um slot para jogar
	public void jogar(){		
		Random r = new Random();
		jogada = r.nextInt(9) + 1;		
	}
}

	
	
	