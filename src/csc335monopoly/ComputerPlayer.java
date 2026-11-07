package csc335monopoly;

import java.util.Random;

import model.spaces.Property;
import model.spaces.Street;

/**
 * Represents a non-human player.
 * 
 * This class is used to represent a computer player in
 * the game of Monopoly. It holds the decision making that
 * the computer has for buying properties and other game 
 * actions. It extends the Player class.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 */
public class ComputerPlayer extends Player {
	
	/**
	 * Constructs an instance of ComputerPlayer.
	 * 
	 * This method creates a new computer player to 
	 * play in Monopoly. It calls super() to call the
	 * constructor from the Player class.
	 * 
	 * Author: Jesse Catterton
	 */
	public ComputerPlayer() {
		super();
	}
	
	/**
	 * Decides if a property should be purchased.
	 * 
	 * This method makes a decision for the computer player
	 * on whether or not they should purcahse a property. It
	 * has simple decision making, buying the property only
	 * if they have enough money for it.
	 * 
	 * @param propertyPrice is the price of the property.
	 * @return a boolean whether to buy the property or not
	 * Author: Jesse Catterton
	 */
	public boolean makePropertyDecision(int propertyPrice) {
		return this.getBalance() >= propertyPrice;
	}

	/**
	 * Decides if a trade should be accepted or declined.
	 * 
	 * @param property the property wanting to be bought
	 * @param offer the money offer on the property
	 * @return true if accepted, false if declined
	 * Author: Tyler Ohme
	 */
	public boolean makeTradeDecision(Property property, int offer) {
		double minMultiplier = 0.8;
		double maxMultiplier = 1.2;
		
		if (property instanceof Street) { // can't sell property if it has houses on it
			Street street = (Street) property;
			if (street.getNumHotels() > 0 || street.getNumHouses() > 0) return false; 
		}
		
		// Add a random element to the computer decision making to make it less predictable
		Random rand = new Random();
		double impulsivenessModifier = rand.nextDouble(minMultiplier, maxMultiplier);
		return offer >= property.getBuyValue() * impulsivenessModifier;
	}
}
