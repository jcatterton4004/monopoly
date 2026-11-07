package model;
import model.spaces.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the MVC model for the Monopoly game.
 * 
 * Manages the game board, which consists of 40 Space objects parsed from a CSV
 * input file. Supported space types include Street, Railroad, Utility, GoSpace,
 * JailSpace, GoToJailSpace, FreeParkingSpace, TaxSpace, and CardDrawSpace.
 * 
 * @author Tyler Ohme
 */
public class MonopolyModel {
	private Space[] board;
	
	/**
	 * Builds a new MonopolyModel object. Initializes the board, card decks, and other
	 * necessary game data.
	 * todo: create better error handling, it's pretty bad right now
	 * @param fileName the name of the CSV file the board data is stored in
	 */
	public MonopolyModel(String fileName) {
		// Initialize variables
		board = new Space[40];
		
		File inputFile = new File(fileName);
		Scanner fileScanner = null;
		
		// Open file
		try {
			fileScanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND ERROR:" + e);
			System.exit(1);
		}
		
		// Read through each line of the file
		int i = 0;
		while (fileScanner.hasNextLine() && i <= 39) {
			String line = fileScanner.nextLine();
			String[] values = line.strip().split(",");
			
			// Parse tags into function calls and arguments
			String name = values[1].toUpperCase();
			switch(values[0]) {
				case "GoSpace":
					if (values.length < 3) System.out.println("Error: invalid file format");
					int collectAmount = Integer.valueOf(values[2]);
					board[i] = new GoSpace(name, collectAmount);
					break;
					
				case "Street":
					if (values.length < 11) System.out.println("Error: invalid file format");
					int streetBuyCost = Integer.valueOf(values[2]);
					int buildCost = Integer.valueOf(values[3]);
					Color tileColor = Color.valueOf(values[10]);
					
					board[i] = new Street(name, streetBuyCost, buildCost,
							// Fill in values for rent at various house count
							Integer.valueOf(values[4]), Integer.valueOf(values[5]), 
							Integer.valueOf(values[6]), Integer.valueOf(values[7]), 
							Integer.valueOf(values[8]), Integer.valueOf(values[9]),
							tileColor);
					break;
				
				case "CardDrawSpace":
					board[i] = new CardDrawSpace(name);
					break;
				
				case "TaxSpace":
					if (values.length < 3) System.out.println("Error: invalid file format");
					int taxAmount = Integer.valueOf(values[2]);
					board[i] = new TaxSpace(name, taxAmount);
					break;
					
				case "Railroad":
					if (values.length < 3) System.out.println("Error: invalid file format");
					int railroadBuyCost = Integer.valueOf(values[2]);
					board[i] = new Railroad(name, railroadBuyCost);
					break;
					
				case "JailSpace":
					board[i] = new JailSpace(name);
					break;
					
				case "Utility":
					if (values.length < 3) System.out.println("Error: invalid file format");
					int utilityCost = Integer.valueOf(values[2]);
					board[i] = new Utility(name, utilityCost);
					break;
					
				case "FreeParkingSpace":
					board[i] = new FreeParkingSpace(name);
					break;
				
				case "GoToJailSpace":
					board[i] = new GoToJailSpace(name);
					break;
				
				default:
					System.out.println("Error: invalid file format");
					continue;
			}
			i++;
		}
		fileScanner.close();
		
		// FILE INPUT FORMAT ERROR, MAKE BETTER LATER
		if (i != 40) System.out.println("Error: invalid input file");
	}
	
	/**
	 * Returns the index of an inputted property, or -1 if it doesnt exist.
	 * 
	 * Used for storing the index of a property in the game save system.
	 * 
	 * @param input the property being searched for
	 * @return the index if found, -1 if not
	 * Author: Tyler Ohme
	 */
	public int getPropertyIndex(Property input) {
		for (int i = 0; i < 40; i++) {
			if (board[i] == input) return i;
		}
		return -1;
	}
	
	/**
	 * Gets the array of Space objects
	 * 
	 * @return the array of Space objects
	 */
	public Space[] getBoard() {
		return board;
	}
	
	/**
	 * Gets the Space object at a specific board index
	 * 
	 * @param index the index being queried
	 * @return the Space if valid, null if out of bounds
	 */
	public Space getSpace(int index) {
		if (index < 0 || index > 39) return null;
		else return board[index];
	}
	
	/**
	 * Gets a list of all Street objects in the same color group.
	 * 
	 * @param street a street belonging to the group being queried
	 * @return a list of all streets belonging to the same group as the input
	 * Author: Tyler Ohme
	 */
	public ArrayList<Street> getPropertyGroup(Street street) {
		Color streetColor = street.getColor();
		ArrayList<Street> result = new ArrayList<>();
		
		// Check each board slot for matching streets
		for (int i = 0; i < 40; i++) {
			if (board[i] instanceof Street) {
				if 	(((Street) board[i]).getColor() == streetColor) {
					result.add((Street) board[i]);
				}
			}
		}
		
		return result;
	}
}
