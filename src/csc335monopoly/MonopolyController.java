package csc335monopoly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import model.MonopolyModel;
import model.spaces.Property;
import model.spaces.Space;
import model.spaces.Street;
import savesystem.GameSave;
import savesystem.SettingsSave;
import model.spaces.GoSpace;

/**
 * Controls the game behavior of Monopoly.
 * 
 * This class acts as a controller to hold the non-GUI
 * related actions in the game of Monopoly. It bridges
 * the gap between the view and the model and controls
 * major game actions.
 * 
 * @author Jesse Catterton
 * @author Sophia Harms
 * @author Tyler Ohme
 */
public class MonopolyController {
	// File variables
	private final static Map<String, String> themes = Map.of(
		"Atlantic City", "src/input_files/default_board.csv",
		"Marvel Comics", "src/input_files/Marvel.csv",
		"DC Comics", 	 "src/input_files/DC_Comics.csv"
	);
	private final File SETTINGS_FILE = new File("src/save_files/settings.json");
	private boolean isLoadedFromSave;
	private int saveSlot;
	
	private Space[] spaces;
	private Player[] players;
	private int playerCount;
	private MonopolyModel model;
	private int curPlayerIndex;
	
	private int humanPlayerCount;
	private int computerPlayerCount;
	private int goAmount;
	private int propertyScale;
	private int startingBalance;
	private boolean freeParking;
	private boolean skipPurchases;
	private boolean trading;
	private String theme;

	/**
	 * Constructs a new controller.
	 * 
	 * This method creates a new instance of the controller
	 * by reading the settings from the current settings file, and
	 * initializing a new game.
	 * 
	 * @throws IOException if there is an error reading the settings file
	 * Author: Jesse Catterton
	 */
	public MonopolyController() throws IOException {
		isLoadedFromSave = false;
		saveSlot = 0; // unset value, valid values are 1-3
		
		// Get settings from default settings file
		SettingsSave settingsHandler = new SettingsSave(SETTINGS_FILE);
		settingsHandler.read(this);
		
		players = addPlayers(humanPlayerCount, computerPlayerCount);
		playerCount = humanPlayerCount + computerPlayerCount;
		model = new MonopolyModel(themes.get(theme));
		spaces = model.getBoard();
	}
	
	/**
	 * Constructs a new controller from an existing save file.
	 * 
	 * This method creates a new instance of the controller by reading the
	 * settings from a specified game save file and continuing the saved
	 * game at its existing state.
	 * 
	 * @param fileName is the name of the saved file
	 * @throws IOException
	 * Author: Tyler Ohme
	 */
	public MonopolyController(String fileName) throws IOException {
		isLoadedFromSave = true;
		saveSlot = Integer.parseInt(fileName.split("_")[3].replace(".json", ""));
		
		// Open existing save file specified by file slot
		File saveFile = new File(fileName);
		if (!saveFile.exists()) {
			throw new IOException("Save file not found: " + saveFile.getAbsolutePath());
		}
		
		// Load settings from previous game session
		SettingsSave settingsHandler = new SettingsSave(saveFile);
		settingsHandler.read(this);
		
		playerCount = humanPlayerCount + computerPlayerCount;
		players = new Player[playerCount];
		
		model = new MonopolyModel(themes.get(theme));
		spaces = model.getBoard();
		
		GameSave gameHandler = new GameSave(saveFile, this);
		gameHandler.read();
	}
	
	/**
	 * Creates Player or AIPlayer objects for each player.
	 * 
	 * This method creates an object for each player, whether they are
	 * a human or AI player. It creates either a Player or AIPlayer object
	 * and assigns it a piece color and player ID. It stores all of the
	 * player objects in an array and returns it.
	 * 
	 * @param humanPlayerCount is the number of human players participating.
	 * @param computerPlayerCount is the number of computer players participating.
	 * @return an array of Player objects who are participating in the game.
	 * Author: Jesse Catterton
	 */
	public Player[] addPlayers(int humanPlayerCount, int computerPlayerCount) {
		Player[] players = new Player[humanPlayerCount + computerPlayerCount];
		Color[] pieceColors = {Color.RED, Color.GOLD, Color.BLUE, Color.ORANGE, Color.GREEN, Color.PURPLE, Color.PINK, Color.BROWN};
		
		for (int i = 0; i < humanPlayerCount; i++) { // loops to create however many human players are in the game
			Player player = new Player();
			player.setPieceColor(pieceColors[i]);
			player.setPlayerId(i);
			players[i] = player;
		}
		for (int i = 0; i < computerPlayerCount; i++) { // loops to create however many computer players are in the game
			ComputerPlayer player = new ComputerPlayer();
			player.setPieceColor(pieceColors[i + humanPlayerCount]);
			player.setPlayerId(i + humanPlayerCount);
			players[i + humanPlayerCount] = player;
		}
		playerCount = humanPlayerCount + computerPlayerCount;
		return players;
	}

	/**
	 * Alters the property prices by a certain percentage.
	 * 
	 * Since the property prices are a configurable amount, this method
	 * is used to adjust those prices if the player wants to increase
	 * or decrease them by a certain percentage. It goes through each
	 * property and adjusts its price by the value.
	 * 
	 * Author: Jesse Catterton
	 */
	public void adjustPropertyPrices() {
		for (int i = 0; i < 40; i++) { // loops to edit each space's price
			if (spaces[i] instanceof Property) { // only updates price if the space has a price (is a property)
				int currPrice = ((Property) spaces[i]).getBuyValue();
				int newPrice = (int) (currPrice * (propertyScale / 100.0));
				((Property) spaces[i]).setBuyValue(newPrice);
			}
		}
	}

	/**
	 * Sets each player's initial balance.
	 * 
	 * Since the starting balance for each player is configurable, this
	 * method is used to set that balance to the value specified by the
	 * user. It iterates through each player in the game and sets their
	 * starting balance to value.
	 * 
	 * @param players is the array of player objects in the game
	 * Author: Jesse Catterton
	 */
	public void setPlayerBalance(Player[] players) {
		for (int i = 0; i < players.length; i++) { // loops to set each player's balance
			players[i].adjustBalance(startingBalance);
		}
	}
	
	/**
	 * Checks if player can escape bankrupcy.
	 * 
	 * This method sees if a player can get out of bankrupcy by 
	 * selling all of their assets. If selling all their assets 
	 * would give them enough money to reach a balance above 0,
	 * then it returns true. Returns false otherwise.
	 * 
	 * @param player is the player who needs to escape bankrupcy
	 * @return a boolean indicating if they can get out of bankrupcy
	 * Author: Jesse Catterton
	 */
	public boolean canEscapeBankrupcy(Player player) {
		ArrayList<Property> properties = player.getProperties();
		int amount = 0;
		for (Property property : properties) { // loops to add each property's value
			amount += property.getMortgageValue();
			if (property instanceof Street) { // adds value of houses and hotels
				amount += ((Street) property).getNumHotels() * ((Street) property).getBuildCost();
				amount += ((Street) property).getNumHouses() * ((Street) property).getBuildCost();
			}
		}

		return amount + player.getBalance() >= 0;
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * This method checks if the game is over by seeing if there is one
	 * player remaining that isn't bankrupt. If there is more than one
	 * player that isn't bankrupt, then it returns null. If there is only
	 * one player that isn't bankrupt, it returns that player.
	 * 
	 * @return the Player who has won or null if the game isn't over
	 * Author: Jesse Catterton
	 */
	public Player checkGameOver() {
		ArrayList<Player> playersInGame = new ArrayList<Player>(); // will hold active players
		for (Player player : players) { // adds all players who aren't bankrupt
			if (!player.checkBankrupt()) playersInGame.add(player);
		}
		
		if (playersInGame.size() == 1) return playersInGame.get(0); // returns player if they are only one who isn't bankrupt
		else return null; // returns null if more than one player isn't bankrupt
		
	}
	
	/**
	 * Purpose: Getter method for the Player array.
	 * This allows the array of Players to be accessed in other classes 
	 * while maintaining encapsulation.
	 * @return players: Array of Player objects
	 * Author: Sophia Harms
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Updates the amount to collect on go.
	 * 
	 * Since the amount of money a player collects when they land on
	 * go is configurable, this method is necessary to update go's
	 * amount. It updates the GoSpace object's collectAmount
	 * 
	 * Author: Jesse Catterton
	 */
	public void updateGoAmount() {
		((GoSpace) spaces[0]).setCollectAmount(goAmount);
	}
	
	/**
	 * Purpose: Getter method for the playerCount.
	 * This allows the number of Players to be accessed
	 * while maintaining encapsulation.
	 * @return int: Array of Player objects
	 * Author: Sophia Harms
	 */
	public int getPlayerCount() {
		return playerCount;
	}
	
	/**
	 * Sets humanPlayerCount variable.
	 * 
	 * This method sets the amount of human players in
	 * the game. 
	 * 
	 * @param count is the amount of human players
	 * Author: Jesse Catterton
	 */
	public void setHumanPlayerCount(int count) {
		humanPlayerCount = count;
	}
	
	/**
	 * Returns humanPlayerCount variable.
	 * 
	 * This method returns the amount of human players in
	 * the game. 
	 * 
	 * @return the amount of human players
	 * Author: Jesse Catterton
	 */
	public int getHumanPlayerCount() {
		return humanPlayerCount;
	}
	
	/**
	 * Sets computerPlayerCount variable.
	 * 
	 * This method sets the amount of computer players in
	 * the game. 
	 * 
	 * @param count is the amount of computer players
	 * Author: Jesse Catterton
	 */
	public void setComputerPlayerCount(int count) {
		computerPlayerCount = count;
	}
	
	/**
	 * Returns computerPlayerCount variable.
	 * 
	 * This method returns the amount of computer players in
	 * the game. 
	 * 
	 * @return the amount of computer players
	 * Author: Jesse Catterton
	 */
	public int getComputerPlayerCount() {
		return computerPlayerCount;
	}
	
	/**
	 * Sets the amount to be collected on go.
	 * 
	 * This method sets the amount of money to be collected on
	 * passing go.
	 * 
	 * @param value is the amount to be collected on go.
	 * Author: Jesse Catterton
	 */
	public void setGoAmount(int value) {
		goAmount = value;
	}
	
	/**
	 * Returns goAmount variable.
	 * 
	 * This method returns the amount of money to be collected on
	 * passing go
	 * 
	 * @return the amount to be collected when passing go
	 * Author: Jesse Catterton
	 */
	public int getGoAmount() {
		return goAmount;
	}
	
	/**
	 * Sets propertyScale variable.
	 * 
	 * This method sets the amount that the property prices should
	 * be scaled by, in units of percentage.
	 * 
	 * @param value is the percent to scale the property prices
	 * Author: Jesse Catterton
	 */
	public void setPropertyScale(int value) {
		propertyScale = value;
	}
	
	/**
	 * Returns propertyScale variable.
	 * 
	 * This method returns the amount that the property prices 
	 * should be scaled by, in units of percentage.
	 * 
	 * @return the percent to scale the property prices
	 * Author: Jesse Catterton
	 */
	public int getPropertyScale() {
		return propertyScale;
	}
	
	/**
	 * Sets freeParking variable.
	 * 
	 * This method sets the boolean indicating whether free parking
	 * rewards should be enabled or not.
	 * 
	 * @param status is the boolean indicating enabled free parking or not
	 * Author: Jesse Catterton
	 */
	public void setFreeParking(boolean status) {
		freeParking = status;
	}
	
	/**
	 * Returns freeParking variable.
	 * 
	 * This method returns the boolean indicated whether free parking
	 * rewards should be enabled or not.
	 * 
	 * @return the boolean indicating enabled free parking or not
	 * Author: Jesse Catterton
	 */
	public boolean getFreeParking() {
		return freeParking;
	}
	
	/**
	 * Returns startingBalance variable.
	 * 
	 * This method sets the amount that each player should start
	 * with when the game starts
	 * 
	 * @param value is the int amount each player should start the game with
	 * Author: Jesse Catterton
	 */
	public void setStartingBalance(int value) {
		startingBalance = value;
	}
	
	/**
	 * Returns startingBalance variable.
	 * 
	 * This method returns the amount that each player should start
	 * with when the game starts
	 * 
	 * @return the int amount each player should start the game with
	 * Author: Jesse Catterton
	 */
	public int getStartingBalance() {
		return startingBalance;
	}
	
	/**
	 * Sets skipPurchases variable.
	 * 
	 * This method sets the boolean indicating whether players can
	 * skip purchasing properties or not
	 * 
	 * @param status is the boolean indicating enabled skip properties
	 * Author: Jesse Catterton
	 */
	public void setSkipPurchases(boolean status) {
		skipPurchases = status;
	}
	
	/**
	 * Returns freeParking variable.
	 * 
	 * This method returns the boolean indicating whether players can
	 * skip purchasing properties or not
	 * 
	 * @return the boolean indicating enabled skip properties
	 * Author: Jesse Catterton
	 */
	public boolean getSkipPurchases() {
		return skipPurchases;
	}
	
	/**
	 * Sets trading variable.
	 * 
	 * This method sets the boolean indicating whether players can
	 * trade or not
	 * 
	 * @param status is the boolean indicating enabled trading
	 * Author: Jesse Catterton
	 */
	public void setTrading(boolean status) {
		trading = status;
	}
	
	/**
	 * Returns trading variable.
	 * 
	 * This method returns the boolean indicating whether players can
	 * trade or not
	 * 
	 * @return the boolean indicating enabled trading
	 * Author: Jesse Catterton
	 */
	public boolean getTrading() {
		return trading;
	}
	
	/**
	 * Returns the theme variable.
	 * 
	 * @return the value of the theme string variable
	 * Author: Tyler Ohme
	 */
	public String getTheme() {
		return theme;
	}
	
	/**
	 * Sets the value of the theme variable to the input.
	 * 
	 * Strips any extra quotations from the inputted value.
	 * 
	 * @param input the new value of the theme variable
	 * Author: Tyler Ohme
	 */
	public void setTheme(String input) {
		theme = input.replace("\"", "");
	}
	
	/**
	 * Gets the value of the spaces instance variable.
	 * 
	 * @return the spaces variable
	 * Author: Tyler Ohme
	 */
	public Space[] getSpaces() {
		return spaces;
	}
	
	/**
	 * Gets the value of the model instance variable.
	 * 
	 * @return the model variable
	 * Author: Tyler Ohme
	 */
	public MonopolyModel getModel() {
		return model;
	}
	
	/**
	 * Adds a Player object to the player array.
	 * 
	 * @param input is the Player object
	 * @param index is the player's index in the player array
	 * Author: Tyler Ohme
	 */
	public void addPlayer(Player input, int index) {
		players[index] = input;
	}
	
	/**
	 * Gets the value of the isLoadedFromSave instance variable.
	 * 
	 * @return the isLoadedFromSave variable
	 * Author: Tyler Ohme
	 */
	public boolean isLoadedFromSave() {
		return isLoadedFromSave;
	}
	
	/**
	 * Gets the value of the curPlayerIndex instance variable.
	 * 
	 * @return the curPlayerIndex variable
	 * Author: Tyler Ohme
	 */
	public int getCurPlayerIndex() {
		return curPlayerIndex;
	}
	
	/**
	 * Sets the value of the curPlayerIndex instance variable.
	 * 
	 * @param input is the value curPlayerIndex
	 * Author: Tyler Ohme
	 */
	public void setCurPlayerIndex(int input) {
		curPlayerIndex = input;
	}
	
	/**
	 * Gets the value of the curPlayerIndex instance variable plus 1.
	 * 
	 * @return curPlayerIndex plus 1 to get the next player to go
	 * Author: Tyler Ohme
	 */
	public int nextPlayerIndex() {
		return ++curPlayerIndex;
	}
	
	/**
	 * Gets the value of the saveSlot instance variable.
	 * 
	 * @return the saveSlot variable
	 * Author: Tyler Ohme
	 */
	public int getSaveSlot() {
		return saveSlot;
	}
	
	/**
	 * Sets the value of the saveSlot instance variable.
	 * 
	 * @param input is the integer value of the saveSlot
	 * Author: Tyler Ohme
	 */
	public void setSaveSlot(int input) {
		saveSlot = input;
	}
}