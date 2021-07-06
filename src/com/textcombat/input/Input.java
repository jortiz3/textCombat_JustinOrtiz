package com.textcombat.input;

import java.util.Scanner;

public final class Input {
	private static Scanner inputScanner;
	
	public static int getInt() {
		return inputScanner.nextInt();
	}
	
	public static String getString() {
		return inputScanner.nextLine();
	}
	
	public static void initialize() {
		inputScanner = new Scanner(System.in);
	}
}
