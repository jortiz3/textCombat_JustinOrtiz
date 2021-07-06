package com.textcombat.characters;

import java.util.Random;
import com.textcombat.input.Input;

public class Player extends Character {
	private int abilityPageNumber;

	public Player(String name, int hp, int maxhp, int stamina, int maxstamina, int strength, int dexterity, int will) {
		super(name, hp, maxhp, stamina, maxstamina, strength, dexterity, will);
		abilityPageNumber = 1;
	}
	
	@Override
	public void reset() {
		super.reset();
		super.clearAbilities();
	}
	
	@Override
	public void takeTurn(Random random) {
		super.takeTurn(random);
		System.out.println("Options: ability index #, print, next, or prev");
		
		String input;
		int abilityIndex = -1;
		while (abilityIndex < 0) {
			input = Input.getString();
			switch(input) {
				case "print":
					super.printAbilities(abilityPageNumber);
					break;
				case "prev":
					if (1 < abilityPageNumber)
						super.printAbilities(--abilityPageNumber);
					break;
				case "next":
					if (abilityPageNumber < super.getNumberOfAbilitiesPages())
						super.printAbilities(++abilityPageNumber);
					break;
				default:
					try {
						abilityIndex = Integer.parseInt(input);
					} catch (NumberFormatException e) {
						System.out.println("'" + input + "' is not a valid option.");
					}
					break;
			}
		}
		useAbility(abilityIndex, random); //change to get string -- check for "prev", "next", or integer
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
