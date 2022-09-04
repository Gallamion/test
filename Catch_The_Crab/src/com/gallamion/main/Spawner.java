package com.gallamion.main;

import java.util.Random;

public class Spawner {

	public int curTime = 0, targetTime = (60*2);
	public Random random;
	
	
	public Spawner() {
		random = new Random();
	}
	
	public void update() {
		curTime++;
		if(curTime == targetTime) {
			curTime = 0;
			if(random.nextInt(100) < 50) {
			Game.crab.add(new Crab(random.nextInt(Game.WIDTH - 16), -16));
			}else {
				Game.crab.add(new Crab(random.nextInt(Game.WIDTH - 16), Game.HEIGHT-16));
			}
		}
	}
	
	
}
