package csc335monopoly;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.spaces.Property;
import model.spaces.Street;
import model.spaces.Railroad;
import model.spaces.Space;
import model.spaces.Utility;
/**
 * Purpose: Creates the Player class.
 * @author Sophia Harms
 * @author Jesse Catterton
 * @author Tyler Ohme
 * CSC 355 Team Project: Monopoly
 * This class stores all the information about a specific Player.
 * Their location, balance, piece, owned properties, and cards are 
 * stored in this class. Getter methods exist for all the attributes and methods
 * that directly change the Player's attributes.
 */
public class Player {
	private int balance;
	private int location;
	private Color pieceColor;
	private int playerId;
	private int houses;
	private int hotels;
	private ArrayList<Property> ownedProperties;
	private ArrayList<Card> heldCards;
	private boolean isBankrupt;
	private boolean isJailed;
	private int jailRollCount;
	private boolean doubles;
	private int lastRoll;
	private GridPane playerProfile;
	
	/**
	 * Purpose: Constructor method for Player class.
	 * Initializes the balance to 0.
	 * Initializes the location to 0 because the player is starting on the 0th position of the board
	 * Author: Sophia Harms
	 */
	public Player(){
		balance = 0;
		location = 0;
		houses = 0;
		hotels = 0;
		lastRoll = 0;
		ownedProperties = new ArrayList<Property>();
		heldCards = new ArrayList<Card>();
		isBankrupt = false;
		isJailed = false;
		jailRollCount = 0;
	}
	
	/**
	 * Purpose: Getter method for the amount of houses a Player owns.
	 * Returns the houses attribute to achieve O(1) time.
	 * @return houses: int of how many houses the Player has
	 * Author: Sophia Harms
	 */
	public int getHouses() {
		return houses;
	}
	
	/**
	 * Purpose: Getter method for the amount of hotels a Player owns.
	 * Returns the hotels attribute to achieve O(1) time.
	 * @return hotels: int of how many hotels the Player has
	 * Author: Sophia Harms
	 */
	public int getHotels() {
		return hotels;
	}
	
	/**
	 * Purpose: Adds a house to the Player's amount of owned houses.
	 * This allows the Player's amount of houses to be adjusted if they buy a house.
	 * Author: Sophia Harms
	 */
	public void addHouse() {
		houses += 1;
	}
	
	/**
	 * Purpose: Adds a hotel to the Player's amount of owned hotels.
	 * This allows the Player's amount of hotels to be adjusted if they buy a hotel.
	 * Author: Sophia Harms
	 */
	public void addHotel() {
		hotels += 1;
	}
	
	/**
	 * Purpose: Removes a house to the Player's amount of owned houses.
	 * This allows the Player's amount of houses to be adjusted if they sell a house.
	 * Author: Sophia Harms
	 */
	public void sellHouse() {
		houses -= 1;
	}
	
	/**
	 * Purpose: Removes a hotel to the Player's amount of owned hotels.
	 * This allows the Player's amount of hotels to be adjusted if they sell a hotel.
	 * Author: Sophia Harms
	 */
	public void sellHotel() {
		hotels -= 1;
	}
	
	/**
	 * Purpose: Sets the Player's color.
	 * The Player can choose their color in the view and then the color is sent 
	 * here as an int and then the pieceColor fields is set. In another class, when
	 * the Player picks their color, the public color fields are used to set the argument
	 * for this method so that the color numbering system stays intact.
	 * @param color: Color of which color the Player choose. The numbering system is in the fields.
	 * Author: Sophia Harms
	 */
	public void setPieceColor(Color color) {
		pieceColor = color;
	}
	
	/**
	 * Purpose: Getter method for the piece color.
	 * Returns the pieceColor attribute to achieve O(1) time.
	 * @return pieceColor: Color of the color the Player's piece is. 0 means they have not
	 * chosen a color yet.
	 * Author: Sophia Harms
	 */
	public Color getPieceColor() {
		return pieceColor;
	}
	
	/**
	 * Sets the variable playerId.
	 * 
	 * This method sets the playerId variable so that it can be
	 * used to identify the player from other players.
	 * 
	 * @param id is an int representing an identification number
	 * Author: Jesse Catterton
	 */
	public void setPlayerId(int id) {
		playerId = id;
	}
	
	/**
	 * Returns the variable playerId.
	 * 
	 * This method returns the playerId variable so we can see
	 * which player it is (e.g. player 1, player 2, etc.).
	 * 
	 * @return an int playerId
	 * Author: Jesse Catterton
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	/**
	 * Purpose: Getter method for the Player's balance.
	 * Returns the balance attribute to achieve O(1) time.
	 * @return int which is the Player's balance 
	 * Author: Sophia Harms
	 */
	public int getBalance(){
		return balance;
	}
	
	/**
	 * Purpose: Getter method for the Player's location.
	 * Returns the location attribute to achieve O(1) time.
	 * @return int which is the Player's location 
	 * Author: Sophia Harms
	 */
	public int getLocation() {
		return location;
	}
	
	/**
	 * Sets the variable location.
	 * 
	 * This is used to set the location of the player when
	 * they are moved by something other than the dice. Like
	 * if they draw an advance to go card or get sent to jail.
	 * 
	 * @param value is the location value
	 * Author: Jesse Catterton
	 */
	public void setLocation(int value) {
		location = value;
	}
	
	/**
	 * Purpose: Adjusts the Player's balance.
	 * Adds the amount to the balance attribute. Adding it account for the amount
	 * being negative or positive. The method will return a boolean denoting if the
	 * Player is bankrupt or not.
	 * @param amount: int of the amount being changed from the Player's balance.
	 * @return boolean: true if the Player is bankrupt and false if not
	 * Author: Sophia Harms
	 */
	public boolean adjustBalance(int amount) {
		balance += amount;
		if(balance < 0) {
			return true; 
		}
		return false;
	}
	
	/**
	 * Purpose: Moves the Player's location in respect to the dice roll.
	 * The location attribute is added to the dice roll result. If the location
	 * ever gets bigger than the board size (0 to 39 tiles), the location is set back to 0 and the 
	 * remainder is added. 
	 * @param diceRoll: int representing what number the dice landed on.
	 * Author: Sophia Harms
	 */
	public void move(int diceRoll) {
		location += diceRoll;
		if(location > 39) {
			location -= 40;
		}
	}
	
	/**
	 * Purpose: Simulates a random dice roll for the Player's movement.
	 * Random is used to pick a random number 0 to 5 then adds one to get 1 to 6
	 * of a dice roll. It rolls two dice and combines the total of the two. Then 
	 * the move() method is called to change the Player's location depending on 
	 * the roll.
	 * @return roll array of the results of the dice roll
	 * Author: Sophia Harms
	 */
	public int[] diceRoll() {
		int result[] = new int[2];
		
		Random random = new Random();
		result[0] = random.nextInt(6) + 1;
		result[1] = random.nextInt(6) + 1;
		if (result[0] == result[1]) doubles = true;
		else doubles = false;
		if (!isJailed) move(result[0] + result[1]);
		lastRoll = result[0] + result[1];
		
		return result;
	}
	
	/**
	 * Returns the value of the last dice roll by this player.
	 * 
	 * @return the value of the last dice roll
	 * Author: Tyler Ohme
	 */
	public int getLastRoll() {
		return lastRoll;
	}
	
	/**
	 * Purpose: Getter method for the Player's held cards.
	 * This will be used later so a Player can see what cards they have and
	 * choose them while playing.
	 * @return heldCards: ArrayList of cards which is the Player's cards 
	 * Author: Sophia Harms
	 */
	public ArrayList<Card> getHeldCards() {
		return heldCards;
	}
	
	/**
	 * Purpose: Adds card to the Player's heldCards.
	 * This will add a card for the player to use on a later turn.
	 * @param card: Card that holds the card they drew from the Community Chest.
	 * Author: Sophia Harms
	 */
	public void addCard(Card card) {
		heldCards.add(card);
	}
	
	/**
	 * Purpose: Removes a card from the Player's heldCards.
	 * When a player uses/trades their card in the game, it will be discarded from
	 * their hand.
	 * @param card: Card the player is removing from their hand.
	 * Author: Sophia Harms
	 */
	public void removeCard(Card card) {
		for(int i = 0; i < heldCards.size(); i++) {
			if(heldCards.get(i).getMessage().equals(card.getMessage())) {
				heldCards.remove(i);
				break;
			}
		}
	}

	/**
	 * Sets the player profile's open on click ability.
	 * 
	 * This method takes in a GridPane that holds the player's profile 
	 * on the board and adds a mouse click handler to it. This is done
	 * so that when someone clicks on the player's profile, it callss
	 * the showPlayerInfo() method to show an expanded view of it.
	 * 
	 * @param playerProfile is a GridPane holding basic player info on board.
	 * Author: Jesse Catterton
	 */
	public void setPlayerProfile(GridPane playerProfile) {
		this.playerProfile = playerProfile;
		playerProfile.setOnMouseClicked((event) -> showPlayerInfo());
	}
	
	/**
	 * Turns the player profile red to indicate bankrupcy and lets go of properties.
	 * 
	 * This method turns the player profile red to indicate that the player
	 * has gone bankrupt and is out of the game. It also releases all of the 
	 * players properties and held cards.
	 * 
	 * @param tiles is the array of the board's tile objects
	 * Author: Jesse Catterton
	 */
	public void playerIsBankrupt(Tile[] tiles) {
		playerProfile.setStyle("-fx-background-color: #FFCCBB; -fx-border-color: black; -fx-border-width: 2px;");
		
		 while (!ownedProperties.isEmpty()) { // releases all player's properties
		        Property property = ownedProperties.get(0);
		        if (property instanceof Street) {
		            while (((Street) property).sell()) {}
		            for (int i = 0; i < 40; i++) { // removes all houses/hotels from player's properties
		                if (tiles[i].getSpace() == property) {
		                    while (((Street) property).getNumHouses() > 0 || ((Street) property).getNumHotels() > 0) {
		                        ((RectangleTile) tiles[i]).sell();
		                    }
		                    break;
		                }
		            }
		        }
		        ownedProperties.remove(0);
		        property.setOwner(null);
		 }
		 while (!heldCards.isEmpty()) heldCards.remove(0); // releases all player's community chest cards
	}
	
	/**
	 * Shows the player's expanded profile.
	 * 
	 * This method creates an Alert object to show the user extra information
	 * about a given player. It displays the player's id number, current 
	 * balance, current list of properties with house/hotel counts, and the
	 * current cards held by the player. It uses showAndWait() to display
	 * this information.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showPlayerInfo() {
		Alert msg = new Alert(Alert.AlertType.INFORMATION);
		msg.setTitle("Player Information"); 
		msg.setHeaderText("Player " + (playerId + 1));
		
		String message = "Current Balance: " + String.valueOf(balance);
	
		message += "\nCurrent Properties: ";
		for (int i = 0; i < ownedProperties.size(); i++) { // loops to add each of the player's properties to list
			message += "\n	- " + ownedProperties.get(i).getName();
			if (ownedProperties.get(i) instanceof Street) { // adds house counts if property is a street
				message += " with " + ((Street) ownedProperties.get(i)).getNumHouses() + " house(s) and " +
							((Street) ownedProperties.get(i)).getNumHotels() + " hotel(s)";
			}
		}
		
		message += "\nCurrent Cards: ";
		for (int i = 0; i < heldCards.size(); i++) { // loops to add each card held by the player
			message += "\n	- " + heldCards.get(i).getMessage();
		}
		
		msg.setContentText(message);
		msg.showAndWait(); 
	}
	
	/**
	 * Sets isBankrupt variable.
	 * 
	 * This method is called to set a player's bankrupt
	 * status to set if they're out of the game or not.
	 * 
	 * @param status is their bankrupt status.
	 * Author: Jesse Catterton
	 */
	public void setBankrupt(boolean status) {
		isBankrupt = status;
	}
	
	/**
	 * Returns isBankrupt variable.
	 * 
	 * This method is called to see a player's bankrupt
	 * status to see if they're out of the game or not.
	 * 
	 * @return a boolean indicating if the player is bankrupt
	 * Author: Jesse Catterton
	 */
	public boolean checkBankrupt() {
		return isBankrupt;
	}
	
	/**
	 * Sets isJailed variable.
	 * 
	 * This method is called to set a player's jailed
	 * status to set if they're in jail or not.
	 * 
	 * @param status is their jailed status.
	 * Author: Jesse Catterton
	 */
	public void setJailed(boolean status) {
		isJailed = status;
	}
	
	/**
	 * Returns isJailed variable.
	 * 
	 * This method is called to see a player's jailed
	 * status to see if they're in jail or not
	 * 
	 * @return a boolean indicating if the player is jailed
	 * Author: Jesse Catterton
	 */
	public boolean checkJailed() {
		return isJailed;
	}
	
	/**
	 * Adds a property to a player.
	 * 
	 * This method adds a property to a player's list
	 * of owned properties. This may be called when a
	 * player buys the property or is traded it.
	 * 
	 * @param property is the property being acquired by the player
	 * Author: Jesse Catterton
	 */
	public void addProperty(Property property) {
		property.setOwner(this);
		ownedProperties.add(property);
	}
	
	/**
	 * Removes a property to from a player.
	 * 
	 * This method removes a property from a player's list
	 * of owned properties. This may be called when a player
	 * sells or trades a property away.
	 * 
	 * @param property is the property being lost by the player
	 * Author: Jesse Catterton
	 */
	public void removeProperty(Property property) {
		ownedProperties.remove(property);
	}
	
	/**
	 * Returns the player's list of properties.
	 * 
	 * This method returns player's list of owned properties.
	 * 
	 * @return an ArrayList of Property objects
	 * Author: Jesse Catterton
	 */
	public ArrayList<Property> getProperties() {
		return ownedProperties;
	}
	
	/**
	 * Gets the amount of railroads owned by the player.
	 * 
	 * This method is used when calculating the rent of someone who lands on a
	 * railroad space owned by this player, since the rent is dependant on the
	 * amount owned.
	 * 
	 * Author: Tyler Ohme
	 * @return the count of railroads owned by current player
	 */
	public int getRailroadsOwned() {
		int result = 0;
		
		// Check all properties for railroads
		for (Property property : ownedProperties) {
			if (property instanceof Railroad) result++;
		}
		
		return result;
	}
	
	/**
	 * Gets the amount of utility spaces owned by the player.
	 * 
	 * This method is used when calculating the rent of someone who lands on a
	 * utility space owned by this player, since the rent is dependant on the
	 * amount owned.
	 * 
	 * Author: Tyler Ohme
	 * @return the count of utilities owned by current player
	 */
	public int getUtilitiesOwned() {
		int result = 0;
		
		// Check all properties for utilites
		for (Property property : ownedProperties) {
			if (property instanceof Utility) result++;
		}
		
		return result;
	}
	
	/**
	 * Handles a player purchasing a property.
	 * 
	 * This method will take the appropriate funds from a player, and assign
	 * the space to belong to them
	 * 
	 * @param property the property being purchased
	 * Author: Tyler Ohme
	 */
	public void buyProperty(Property property) {
		int cost = property.getBuyValue();
		
		// Handle purchase
		if (cost > balance) return;
		else {
			balance -= cost;
			property.setOwner(this);
			ownedProperties.add(property);
		}
	}

	/**
	 * Checks to see if player owns complete set.
	 * 
	 * This method checks to see if a player owns the complete set
	 * of properties in order to see if they can build a house/hotel 
	 * on that set. It takes the color of the owned space and checks
	 * to see if the player owns all the other properties of that color. 
	 * 
	 * @param ownedStreet is the space the player wants to build on
	 * @param tiles is an array of Tile objects to search
	 * @param colors is the color scheme for the board
	 * @return a boolean indicating whether the player owns a complete set
	 * Author: Jesse Catterton
	 */
	public ArrayList<Street> checkForCompleteSet(Street ownedStreet, Tile[] tiles, Color[] colors) {
		Color color = ownedStreet.getColor();
		ArrayList<Street> completeSet = new ArrayList<Street>();
		int colorCount = 0;
		for (Tile tile : tiles) { // loops for all tiles on the board
			
			// checks if tile is a Street of the same color as the owned tile
			if (tile instanceof RectangleTile && ((RectangleTile) tile).getSpace() instanceof Street && ((RectangleTile) tile).getColor().equals(color)) {
				Space space = ((RectangleTile) tile).getSpace();
				if (((Street) space).getOwner() == this) { // increments count if player owns same color space
					colorCount++;
					completeSet.add((Street) space);
					if (colorCount > 2) break;
				} else { // returns false if player doesn't own all properties of same color
					return null;
				}
			}
		}
		if (color.equals(colors[1]) || color.equals(colors[39])) { // checks if owned tile is part of a two-property set
			if (colorCount == 2) return completeSet; // returns set if it is complete
		} else {
			if (colorCount == 3) return completeSet; // returns set if it is complete
		}
		return null;
	}
	
	/**
	 * Sets jailRollCount variable.
	 * 
	 * This method is used to set the jailRollCount variable
	 * to keep track of how many times a player has attempted
	 * to roll doubles in jail.
	 * 
	 * @param count is the number of times they have rolled
	 * Author: Jesse Catterton
	 */
	public void setJailRollCount(int count) {
		jailRollCount = count;
	}
	
	/**
	 * Returns jailRollCount variable.
	 * 
	 * This method is used to get the jailRollCount variable
	 * to see how many times a player has attempted to roll 
	 * doubles in jail.
	 * 
	 * @return number of times they have rolled
	 * Author: Jesse Catterton
	 */
	public int getJailRollCount() {
		return jailRollCount;
	}
	
	/**
	 * Returns true if roll was doubles.
	 * 
	 * A player gets to roll again if their last turn was
	 * doubles, so this method returns true if it was doubles
	 * so their turn can be repeated.
	 * 
	 * @return a boolean indicating if the roll was doubles
	 * Author: Jesse Catterton
	 */
	public boolean getRollWasDoubles() {
		return doubles;
	}
	
	/**
	 * Sets the value of the balance instance variable.
	 * 
	 * @param input is the integer value of the balance
	 * Author: Tyler Ohme
	 */
	public void setBalance(int input) {
		balance = input;
	}
	
	/**
	 * Sets the value of the houses instance variable.
	 * 
	 * @param input is the integer value of the number of houses
	 * Author: Tyler Ohme
	 */
	public void setHouses(int input) {
		houses = input;
	}
	
	/**
	 * Sets the value of the hotels instance variable.
	 * 
	 * @param input is the integer value of the number of hotels
	 * Author: Tyler Ohme
	 */
	public void setHotels(int input) {
		hotels = input;
	}
	
	/**
	 * Sets the value of the lastRoll instance variable.
	 * 
	 * @param input is the integer value of the player's last roll
	 * Author: Tyler Ohme
	 */
	public void setLastRoll(int input) {
		lastRoll = input;
	}
}
