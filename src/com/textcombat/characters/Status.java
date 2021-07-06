package com.textcombat.characters;

public class Status extends Effect {
	private int hp;
	private int stam;
	
	public Status(String name, int hpModifier, int staminaModifier, int numberOfTurns) {
		super(name, numberOfTurns);
		hp = hpModifier;
		stam = staminaModifier;
	}

	public int getHpModifier() {
		return hp;
	}

	public int getStaminaModifier() {
		return stam;
	}
	
	public Status getCopy() {
		return new Status(name, hp, stam, this.getTurns());
	}
}
