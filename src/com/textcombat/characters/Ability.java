package com.textcombat.characters;

public class Ability {
	private String name;
	private String range;
	private int power;
	private int cost;
	private Buff buff;
	private Status status;
	
	public Ability(String name, int power, Buff buff, Status status) {
		this(name, "self", power, 10, buff, status); //constructor chain to reduce room for error
	}
	
	public Ability(String name, String range, int power, int cost, Buff buff, Status status) {
		this.name = name;
		this.range = range;
		this.power = power;
		this.cost = cost;
		this.buff = buff;
		this.status = status;
	}

	public String getName() {
		return name;
	}
	
	public String getRange() {
		return range;
	}

	public int getPower() {
		return power;
	}
	
	public int getCost() {
		return cost;
	}

	public Buff getBuff() {
		return buff.getCopy();
	}

	public Status getStatus() {
		return status.getCopy();
	}
	
	public boolean hasBuff() {
		return buff != null;
	}
	
	public boolean hasStatus() {
		return status != null;
	}
}
