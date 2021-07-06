package com.textcombat.characters;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public final class Database {
	private static List<Buff> buffs;
	private static List<Status> statuses;
	private static List<Ability> abilities;
	private static List<NPC> npcs;
	
	private static Player player;
	private static NPC foe;
	
	private static final int numberOfFoes = 10;
	private static int foeIndex;
	
	public static Buff getBuff(String buffName) {
		for(Buff buff : buffs) {
			if (buff.getName().equals(buffName))
				return buff;
		}
		return null;
	}
	
	public static Status getStatus(String statusName) {
		for(Status status : statuses) {
			if (status.getName().equals(statusName))
				return status;
		}
		return null;
	}
	
	public static Ability getAbility(String abilityName) {
		for(Ability ability : abilities) {
			if (ability.getName().equals(abilityName))
				return ability;
		}
		return null;
	}
	
	public static Character getCharacter(String characterName) {
		for(Character character : npcs) {
			if (character.getName().equals(characterName))
				return character;
		}
		return null;
	}
	
	public static Character getFoe() {
		return foe;
	}
	
	public static int getFoeIndex() {
		return foeIndex;
	}
	
	public static Character getPlayer() {
		return player;
	}
	
	public static boolean goToNextFoe() {
		if (foeIndex < numberOfFoes - 1) {
			foe = npcs.get(++foeIndex);
			return true;
		}
		return false;
	}
	
	public static void initialize(Random random) {
		initializeBuffs();
		initializeStatuses();
		initializeAbilities();
		initializeNPCs(random);
		initializePlayer();
	}
	
	private static void initializeBuffs() {
		buffs = new ArrayList<>();
		
		//+strength -dexterity
		buffs.add(new Buff("Weakness", -5, 0, -5, 2));
		buffs.add(new Buff("Greater Weakness", -10, 0, -10, 3));
		buffs.add(new Buff("Absolute Weakness", -20, 0, -20, 4));
		buffs.add(new Buff("Bear Strength", 5, -2, 0, 2));
		buffs.add(new Buff("Goliath's Strength", 10, -5, 0, 3));
		buffs.add(new Buff("Titan's Strength", 20, -10, 0, 4));
		buffs.add(new Buff("Godly Strength", 40, -20, 0, 5));
		
		//+dexterity -strength
		buffs.add(new Buff("Slow", 0, -5, 0, 3));
		buffs.add(new Buff("Cripple", 0, -10, 0, 2));
		buffs.add(new Buff("Hold", 0, -15, 0, 2));
		buffs.add(new Buff("Quicken", -1, 5, 0, 3));
		buffs.add(new Buff("Swift", -5, 10, 0, 2));
		buffs.add(new Buff("Haste", -10, 30, 0, 1));
		
		//will
		buffs.add(new Buff("Forsaken", 0, 0, -5, 2));
		buffs.add(new Buff("Condemned", 0, 0, -10, 3));
		buffs.add(new Buff("Punished", 0, 0, -15, 4));
		buffs.add(new Buff("Pig-headed", 0, 0, 5, 4));
		buffs.add(new Buff("Headstrong", 0, 0, 15, 3));
		buffs.add(new Buff("Obstinate", 0, 0, 25, 2));
		
		//+strength -dexterity +will
		buffs.add(new Buff("Reckless", 10, -5, 10, 1));
		buffs.add(new Buff("Enraged", 20, -10, 20, 1));
		buffs.add(new Buff("Ignorant", 30, -30, 30, 1));
	}
	
	private static void initializeStatuses() {
		statuses = new ArrayList<>();
		
		//hp
		statuses.add(new Status("Bleed", -2, 0, 5));
		statuses.add(new Status("Hemorrhage", -5, 0, 5));
		statuses.add(new Status("Regenerate", 1, 0, 5));
		statuses.add(new Status("Revivify", 3, 0, 5));
		
		//stamina
		statuses.add(new Status("Drain", 0, -10, 1));
		statuses.add(new Status("Energize", 0, 5, 5));
		statuses.add(new Status("Ability Surge", 0, 10, 5));
		
		//hp & stamina
		statuses.add(new Status("Sap", -2, -2, 5));
		statuses.add(new Status("Wither", -10, -10, 5));
		statuses.add(new Status("Recover", 3, 10, 5));
	}
	
	private static void initializeAbilities() {
		abilities = new ArrayList<>();
		
		//attacks
		abilities.add(new Ability("Punch", "foe", 0, 1, null, null));
		abilities.add(new Ability("Power Punch", "foe", 5, 8, null, null));
		abilities.add(new Ability("Power Punch Plus", "foe", 10, 20, null, null));
		abilities.add(new Ability("Gut Punch", "foe", 5, 10, getBuff("Slow"), null));
		abilities.add(new Ability("Throat Punch", "foe", 15, 40, getBuff("Cripple"), getStatus("Sap")));
		abilities.add(new Ability("Choke Hold", "foe", 25, 60, getBuff("Hold"), getStatus("Drain")));
		abilities.add(new Ability("Demoralize", "foe", 5, 20, getBuff("Weakness"), getStatus("Drain")));
		abilities.add(new Ability("Malice", "foe", 10, 60, getBuff("Greater Weakness"), getStatus("Hemorrhage")));
		abilities.add(new Ability("Annihilation", "foe", 25, 100, getBuff("Absolute Weakness"), getStatus("Wither")));
		abilities.add(new Ability("Forsake", "foe", 10, 20, getBuff("Forsaken"), getStatus("Drain")));
		abilities.add(new Ability("Condemnation", "foe", 15, 40, getBuff("Condemned"), getStatus("Sap")));
		abilities.add(new Ability("Smite", "foe", 25, 100, getBuff("Punished"), getStatus("Hemorrhage")));
		
		//buffs
		abilities.add(new Ability("Bear Stance", "self", 0, 5, getBuff("Bear Strength"), null));
		abilities.add(new Ability("Goliath Might", "self", 0, 10, getBuff("Goliath's Strength"), null));
		abilities.add(new Ability("Titan Tower", "self", 0, 15, getBuff("Titan's Strength"), null));
		abilities.add(new Ability("Divine Intervention", "self", 0, 25, getBuff("Godly Strength"), null));
		
		abilities.add(new Ability("Quicken", "self", 0, 5, getBuff("Quicken"), null));
		abilities.add(new Ability("Swiften", "self", 0, 10, getBuff("Swift"), null));
		abilities.add(new Ability("Hasten", "self", 0, 15, getBuff("Haste"), null));
		
		abilities.add(new Ability("Combative", "self", 0, 5, getBuff("Pig-Headed"), null));
		abilities.add(new Ability("Unyielding", "self", 0, 10, getBuff("Headstrong"), null));
		abilities.add(new Ability("Never Surrender", "self", 15, 5, getBuff("Obstinate"), null));
		
		abilities.add(new Ability("Wind Up", "self", 0, 5, getBuff("Reckless"), null));
		abilities.add(new Ability("Escalate", "self", 0, 5, getBuff("Enraged"), null));
		abilities.add(new Ability("Hatred", "self", 0, 5, getBuff("Ignorant"), null));
	}
	
	private static void initializeNPCs(Random random) {
		npcs = new ArrayList<>();
		NPC currNPC;
		for(int i = 1; i <= numberOfFoes; i++) {
			int randomBound = 9 + i;
			int hp = random.nextInt(randomBound) + 5;
			int stamina = random.nextInt(20 * i) + 20;
			currNPC = new NPC("Foe #" + i, hp, hp, stamina, stamina,
					random.nextInt(randomBound) + randomBound, random.nextInt(randomBound * 2) + 1, random.nextInt(randomBound * 3) + 1);
			
			for(int numAbilities = random.nextInt(5); 0 <= numAbilities; numAbilities--)
				currNPC.addAbility(abilities.get(random.nextInt(abilities.size())));
			
			npcs.add(currNPC);
		}
		
		foeIndex = 0;
		foe = npcs.get(foeIndex);
	}
	
	private static void initializePlayer() {
		player = new Player("Player", 10, 10, 25, 25, 10, 10, 10);
		initializePlayerAbilities();
	}
	
	private static void initializePlayerAbilities() {
		player.addAbility(getAbility("Punch"));
		
		for(int i = 1; i < 20; i++) {
			player.addAbility(abilities.get(i));
		}
	}
	
	public static void reset() {
		for (int i = npcs.indexOf(foe); 0 <= i; i--)
			npcs.get(i).reset();
		
		player.reset();
		initializePlayerAbilities();
	}
	
	public static void setPlayerName(String name) {
		player.setName(name);
	}
}
