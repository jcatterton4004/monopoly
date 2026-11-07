package model.spaces;

/**
 * Represents a utility space.
 * 
 * This class represents a utility space that can be
 * purchased by the player. It has both Property and
 * Space as its superclasses.
 * 
 * @author Tyler Ohme
 */
public class Utility extends Property {
	/**
	 * Builds a new Utility object.
	 * 
	 * @param name the name of the utility space
	 * @param buyValue the price to buy this space
	 * Author: Tyler Ohme
	 */
	public Utility(String name, int buyValue) {
		super(name, buyValue);
	}
	
	/**
	 * Calculates the rent price of the current player who landed here. This
	 * function will probably be updated to better fit with the Player class,
	 * but for now it follows the Monopoly rules decently well.
	 * 
	 * Brief explanation of rules: Utility rent is dependant on both the dice
	 * roll of the player who landed here, and the amount of Utility slots owned
	 * by the player who owns this (4 * roll for 1, 10 * roll for 2). For example,
	 * if a player landed here after rolling a 10, and the owner owned both utilities
	 * that exist in the base game, the first player would owe $100.
	 * 
	 * @param diceRoll the dice roll that caused a player to land on this
	 * @return the rent of the player who landed here
	 * Author: Tyler Ohme
	 */
	@Override
	public int getRent(int diceRoll) {
		if (this.getOwner() == null) return 0;
		int numOwned = this.getOwner().getUtilitiesOwned();
		
		if (numOwned == 1) {
			return diceRoll * 4;
		} else { // numOwned = 2
			return diceRoll * 10;
		} 
	}
}
