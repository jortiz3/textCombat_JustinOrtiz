package com.textcombat.characters;

public class Buff extends Effect {
	private int str;
	private int dex;
	private int will;
	
	public Buff(String name, int strengthModifier, int dexterityModifier, int willModifier, int numberOfTurns) {
		super(name, numberOfTurns);
		str = strengthModifier;
		dex = dexterityModifier;
		will = willModifier;
	}

	public int getStrengthModifier() {
		return str;
	}

	public int getDexterityModifier() {
		return dex;
	}

	public int getWillModifier() {
		return will;
	}
	
	public Buff getCopy() {
		return new Buff(name, str, dex, will, this.getTurns());
	}
}
