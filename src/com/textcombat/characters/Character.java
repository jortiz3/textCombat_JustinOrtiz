package com.textcombat.characters;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public abstract class Character {
	protected String name;
	private int hp;
	private int maxhp;
	private double stamina;
	private int maxstamina;
	private int baseStr;
	private int baseDex;
	private int baseWill;
	protected int currStr;
	protected int currDex;
	protected int currWill;
	private int initiative;
	private List<Ability> abilities;
	private List<Buff> buffs;
	private List<Status> statuses;
	
	protected static int numberOfAbilitiesPerPage = 10;
	
	public Character(String name, int hp, int maxhp, int stamina, int maxstamina, int strength, int dexterity, int will) {
		this.name = name;
		this.hp = hp <= maxhp ? hp : maxhp;
		this.maxhp = maxhp;
		this.stamina = stamina <= maxstamina ? stamina : maxstamina;
		this.maxstamina = maxstamina;
		baseStr = strength;
		baseDex = dexterity;
		baseWill = will;
		
		currStr = baseStr;
		currDex = baseDex;
		currWill = baseWill;
		
		abilities = new ArrayList<>();
		buffs = new ArrayList<>();
		statuses = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	private String getHP() {
		return "hp: (" + hp + "/" + maxhp + ")";
	}
	
	private String getStamina() {
		return "stamina: (" + stamina + "/" + maxstamina + ")";
	}
	
	private String getStats() {
		return  "str: " + currStr + "(" + baseStr + ") " +
				"dex: " + currDex + "(" + baseDex + ") " + 
				"will: " + currWill + "(" + baseWill + ")";
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public int rollInitiative(Random random) {
		initiative = random.nextInt(20) + baseDex;
		return initiative;
	}
	
	protected int getNumberOfAbilities() {
		return abilities.size();
	}
	
	protected int getNumberOfAbilitiesPages() {
		return getNumberOfAbilities() / numberOfAbilitiesPerPage;
	}
	
	public final void addAbility(Ability abilityToAdd) {
		if (abilities.contains(abilityToAdd))
			return;
		abilities.add(abilityToAdd);
	}
	
	protected final void clearAbilities() {
		abilities.clear();
	}
	
	private void attack(Character target, Ability ability, Random random) {
		double dodgeChance = (10.0 * target.currDex) / currDex; //equivalent dexterity == 10% dodge chance
		int dodgeRoll = random.nextInt(100);
		//System.out.println("dodgeRoll(" + dodgeRoll + ") < dodgeChance(" + dodgeChance + ")");
		if (dodgeRoll < dodgeChance) {
			System.out.println(target.name + " dodged!");
			return;
		}
		
		int damage = Math.max(1, ability.getPower() + currStr - (target.currStr / 10)); //enforce a minimum of 1 damage
		target.hp -= damage;
		System.out.println(target.name + " received " + damage + " damage!");
	}
	
	protected final String addBuff(Buff buffToAdd) {
		for(Buff buff : buffs) {
			if (buff.equals(buffToAdd)) {
				buff.setTurns(buff.getTurns() + buffToAdd.getTurns());
				return buff.getName() + "'s duration has been extended to " + buff.getTurns() + " turns!";
			}
		}
		buffs.add(buffToAdd);
		return name + " has received " + buffToAdd.getName() + " for " + buffToAdd.getTurns() + " turns!";
	}
	
	protected final String addStatus(Status statusToAdd) {
		for(Status status : statuses) {
			if (status.equals(statusToAdd)) {
				status.setTurns(status.getTurns() + statusToAdd.getTurns());
				return status.getName() + "'s duration has been extended to " + status.getTurns() + " turns!";
			}
		}
		statuses.add(statusToAdd);
		return name + " has received " + statusToAdd.getName() + " for " + statusToAdd.getTurns() + " turns!";
	}
	
	public boolean isAlive() {
		return 0 < hp;
	}
	
	protected void printAbilities(int page) {
		int start = (page - 1) * numberOfAbilitiesPerPage;
		int end = page * numberOfAbilitiesPerPage;
		Ability ability;
		for(int i = start; i < abilities.size() && i < end; i++) {
			ability = abilities.get(i);
			System.out.println(i + ": " + ability.getName() + " | " + ability.getCost());
		}
		System.out.println("Page " + page + "/" + getNumberOfAbilitiesPages());
	}
	
	protected final void refreshEffects() {
		currStr = baseStr;
		currDex = baseDex;
		currWill = baseWill;
		
		System.out.println("Buffs & Debuffs:");
		String feedback = "";
		Buff buff;
		for (int buffIndex = buffs.size() - 1; 0 <= buffIndex; buffIndex--) {
			buff = buffs.get(buffIndex);
			if (buff.isActive()) {
				currStr += buff.getStrengthModifier();
				currDex += buff.getDexterityModifier();
				currWill += buff.getWillModifier();
				feedback = buff.getName() + " will be active for " + buff.getTurns() + " more turns.";
			} else {
				feedback = buff.getName() + " expires this turn.";
				buffs.remove(buff);
			}
			System.out.println(feedback);
		}
		
		System.out.println("Status Effects:");
		Status status;
		for (int statusIndex = statuses.size() - 1; 0 <= statusIndex; statusIndex--) {
			status = statuses.get(statusIndex);
			if (status.isActive()) {
				hp += status.getHpModifier();
				stamina += status.getStaminaModifier();
				feedback = status.getName() + " will be active for " + status.getTurns() + " more turns.";
			} else {
				feedback = status.getName() + " expires this turn.";
				statuses.remove(status);
			}
			System.out.println(feedback);
		}
		System.out.println(getHP() + " " + getStamina() + "\n" + getStats());
	}
	
	protected final void regenerate() {
		hp += currWill / 25;
		stamina += currWill / 8.0;
		
		if (maxhp < hp)
			hp = maxhp;
		if (maxstamina < stamina)
			stamina = maxstamina;
	}
	
	public void reset() {
		buffs.clear();
		statuses.clear();
		hp = maxhp;
		stamina = maxstamina;
	}
	
	public void takeTurn(Random random) {
		System.out.println("\n--- " + name + "'s turn ---");
		refreshEffects();
		regenerate();
	}
	
	public final void useAbility(int abilityIndex, Random random) {
		if (abilityIndex < 0 || abilities.size() <= abilityIndex) {
			System.out.println("There is no ability in slot #" + abilityIndex + "!");
			return;
		}
		
		Ability ability = abilities.get(abilityIndex);
		int cost = ability.getCost();
		if (stamina < cost) {
			System.out.println(name + " attempted to use " + ability.getName() + " but they didn't have enough stamina!");
			return;
		}
		
		stamina -= cost;
		System.out.println(name + " used " + ability.getName() + "!");
		
		Character target;
		if (ability.getRange().equals("self")) {
			target = this;
		} else {
			if (Database.getPlayer().equals(this))
				target = Database.getFoe();
			else
				target = Database.getPlayer();
			
			attack(target, ability, random);
		}
		
		String feedback = "";
		if (ability.hasBuff())
			feedback += target.addBuff(ability.getBuff());
		if (ability.hasStatus())
			feedback += "\n" + target.addStatus(ability.getStatus());
		System.out.println(feedback);
	}
}
