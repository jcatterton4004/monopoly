package model.spaces;
import csc335monopoly.Player;

/**
 * Represents a property space.
 * 
 * This class represents a property space that can be
 * purchased by the player. It is abstract because there
 * are many different types of properties that implement
 * the proper methods depending on the type of property.
 * It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public abstract class Property extends Space {
	private int buyValue;
	private int mortgageValue;
	
	private Player owner = null;
	
	/**
	 * Builds a new Property object.
	 * 
	 * @param name the name of the property
	 * @param buyValue the price to buy the property
	 * Author: Tyler Ohme
	 */
	public Property(String name, int buyValue) {
		super(name);
		this.buyValue = buyValue;
		this.mortgageValue = buyValue / 2;
	}
	
	/**
	 * Abstract method to get rent for a property.
	 * 
	 * @param diceRoll is the value of the player's roll
	 * @return the price of rent for that property
	 * Author: Tyler Ohme
	 */
	public abstract int getRent(int diceRoll);
	
	/**
	 * Sets the buyValue variable
	 * 
	 * @param price is the price of the property.
	 * Author: Tyler Ohme
	 */
	public void setBuyValue(int price) {
		buyValue = price;
	}
	
	/**
	 * Returns the value of the buyValue variable.
	 * 
	 * @return the purchase cost of this property
	 * Author: Tyler Ohme
	 */
	public int getBuyValue() {
		return buyValue;
	}
	
	/**
	 * Returns the cost to mortgage this property.
	 * 
	 * @return the mortgage cost of this property
	 * Author: Tyler Ohme
	 */
	public int getMortgageValue() {
		mortgageValue = buyValue / 2;
		return mortgageValue;
	}
	
	/**
	 * Sets the value of the property owner.
	 * 
	 * @param newOwner the new owner of this property
	 * Author: Tyler Ohme
	 */
	public void setOwner(Player newOwner) {
		owner = newOwner;
	}
	
	/**
	 * Returns the current owner.
	 * 
	 * @return the Player object of the current owner, or null if there isn't one
	 * Author: Tyler Ohme
	 */
	public Player getOwner() {
		return owner;
	}
}

