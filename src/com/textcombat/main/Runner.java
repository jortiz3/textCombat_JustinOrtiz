package com.textcombat.main;

public class Runner {

	public static void main(String[] args) {
		Game.initialize();
		while(Game.update());
	}

}
