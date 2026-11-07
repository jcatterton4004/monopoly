package model.spaces;

/**
 * Represents a railroad space.
 * 
 * This class represents a railroad space that can be
 * purchased by the player. It has both Property and
 * Space as its superclasses.
 * 
 * @author Tyler Ohme
 */
public class Railroad extends Property {
	private final int rentPrices[] = {25, 50, 100, 200};
	
	/**
	 * Builds a new Railroad object.
	 * 
	 * @param name the name of the slot
	 * @param buyValue the price to mortgage the slot
	 * Author: Tyler Ohme
	 */
	public Railroad(String name, int buyValue) {
		super(name, buyValue);
	}
	
	/**
	 * Returns the rent price of the current slot. This is dependant on how many
	 * railroads the owner of this one owns.
	 * 
	 * @param diceRoll is the number rolled by the player
	 * @return the price determined by the number of railroads owned
	 * Author: Tyler Ohme
	 */
	@Override
	public int getRent(int diceRoll) {
		int numOwned = this.getOwner().getRailroadsOwned();
		
		return rentPrices[numOwned - 1];
	}
}
