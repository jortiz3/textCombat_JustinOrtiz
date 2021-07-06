package com.textcombat.characters;

import java.util.Random;
import com.textcombat.input.Input;

public class NPC extends Character {

	public NPC(String name, int hp, int maxhp, int stamina, int maxstamina, int strength, int dexterity, int will) {
		super(name, hp, maxhp, stamina, maxstamina, strength, dexterity, will);
	}
	
	@Override
	public void takeTurn(Random random) {
		super.takeTurn(random);
		useAbility(random.nextInt(getNumberOfAbilities()), random); //use a random ability
		System.out.print("Press ENTER to continue...");
		Input.getString();
	}
}
