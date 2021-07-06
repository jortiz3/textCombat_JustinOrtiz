package com.textcombat.main;

import java.util.Random;
import com.textcombat.characters.Database;
import com.textcombat.characters.Character;
import com.textcombat.characters.Player;
import com.textcombat.input.*;

public class Game {
	private static Random random;
	private static Character firstCharacter;
	private static Character secondCharacter;
	
	private static boolean checkGameState(Character characterWhoFainted) {
		if (characterWhoFainted instanceof Player) {
			return restartGame();
		}
		return continueGame();
	}
	
	private static boolean continueGame() {
		if (!Database.goToNextFoe()) { //no foes remaining
			System.out.println("Congratulations! " + Database.getPlayer().getName() + " has defeated all of the foes!");
			return endGame();
		}
		
		System.out.println("Would you like to continue playing? y/n");
		String input = Input.getString();
		if (input.contains("n") || input.contains("N"))
			return endGame();
		initiateEncounter();
		return true;
	}
	
	private static boolean endGame() {
		System.out.println("----- Game Over -----");
		return false;
	}
	
	public static void initialize() {
		random = new Random();
		
		Input.initialize();
		Database.initialize(random);
		
		System.out.println("----- Text Combat Game -----\n");
		
		System.out.println("Please enter your character's name: ");
		Database.setPlayerName(Input.getString());
		
		System.out.println("Name registered!");
		System.out.println("----------------------------\n");
		
		initiateEncounter();
	}
	
	private static void initiateEncounter() {
		System.out.println("\n" + Database.getFoe().getName() + " appeared! Roll initiative!");
		
		int playerInitiative = Database.getPlayer().rollInitiative(random);
		int foeInitiative = Database.getFoe().rollInitiative(random);
		if (playerInitiative < foeInitiative) {
			firstCharacter = Database.getFoe();
			secondCharacter = Database.getPlayer();
			
		} else {
			firstCharacter = Database.getPlayer();
			secondCharacter = Database.getFoe();
		}
		
		System.out.println(firstCharacter.getName() + "(" + firstCharacter.getInitiative() +
				") goes before " +
				secondCharacter.getName() + "(" + secondCharacter.getInitiative() + ")!");
	}
	
	private static boolean restartGame() {
		System.out.println("----- Game Over -----\n\nWould you like to restart? y/n");
		String input = Input.getString();
		if (input.contains("n") || input.contains("N"))
			return false;
		Database.reset();
		return true;
	}
	
	private static boolean takeTurn(Character character) {
		if (!character.isAlive()) {
			System.out.println(character.getName() + " has fainted!");
			return false;
		}
		character.takeTurn(random);
		return true;
	}
	
	public static boolean update() {
		if (!takeTurn(firstCharacter))
			return checkGameState(firstCharacter);
		if (!takeTurn(secondCharacter))
			return checkGameState(secondCharacter);
		
		return true;
	}
}
