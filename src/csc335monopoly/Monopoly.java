/**
 * @author Jesse Catterton
 * @author Sophia Harms
 * @author Tyler Ohme
 * 
 * CSC 335 Spring 26 Final Project: Monopoly
 * Purpose: This program has the capability of running a game
 * of Monopoly between a combination of 2-8 human and computer
 * players. The game is played through a JavaFX GUI launched by
 * the MonopolyView class. During the game, players compete to
 * purchase properties and charge rent in order to attempt to
 * bankrupt other players. The game has an automated banking 
 * system and will continue until all players are bankrupt except
 * for one, which the program will declare the winner and end the
 * game. 
 * 
 */

/*
 * Run using these VM arguments:
 * --module-path /path/to/javafx/lib
 * --add-modules javafx.controls,javafx.fxml,javafx.media
 * --enable-native-access=javafx.graphics
 * --enable-native-access=javafx.media
 * 
 * Uncheck the Use the -XstartOnFirstThread argument box
 */

package csc335monopoly;
import javafx.application.Application;

/**
 * Main class for Monopoly program.
 * 
 * This class acts as the main class for the Monopoly program. It
 * launces the GUI for the game of Monopoly by calling launch on 
 * the MonopolyView class. It catches an exception if launching the
 * GUI fails.
 * 
 * @author Jesse Catterton
 * @author Sophia Harms
 */
public class Monopoly {
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public Monopoly() {
		
	}

	/**
	 * Launches a new game of Monopoly.
	 * 
	 * This method calls Applcation.launch() on the MonopolyView class to
	 * launch a game of Monopoly in a GUI view. It catches an exception if
	 * launching the GUI fails.
	 * 
	 * @param args is a String array of command line arguments.
	 * Author: Jesse Catterton
	 */
	public static void main(String[] args) {
		try {
			Application.launch(MonopolyView.class, args);
		} catch (Exception e) { // catches generic exception automatically thrown by start
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
