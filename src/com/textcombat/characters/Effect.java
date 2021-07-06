package com.textcombat.characters;

public abstract class Effect {
	protected String name;
	private int turns;
	
	public Effect(String name, int numberOfTurns) {
		this.name = name;
		turns = numberOfTurns;
	}
	
	public String getName() {
		return name;
	}

	public int getTurns() {
		return turns;
	}
	
	public void setTurns(int turns) {
		this.turns = turns;
	}
	
	public boolean isActive() {
		if (--turns <= 0)
			return false;
		return true;
	}
	
	public boolean equals(Effect effect) {
		if (this == effect)
			return true;
		if (name.equals(effect.name))
			return true;
		return false;
	}
}
