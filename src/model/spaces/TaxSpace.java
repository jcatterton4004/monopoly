package model.spaces;

/**
 * Represents a tax space.
 * 
 * This class represents a tax space that charges a player
 * a fee if they land on it. It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public class TaxSpace extends Space {
	private int taxAmount;

	/**
	 * Builds a new TaxSpace object.
	 * 
	 * @param name the name of the space
	 * @param taxAmount the amount the player must pay after landing on this
	 * Author: Tyler Ohme
	 */
	public TaxSpace(String name, int taxAmount) {
		super(name);
		this.taxAmount = taxAmount;
	}
	
	/**
	 * Returns the value of the taxAmount variable.
	 * 
	 * @return the amount a player owes the bank after landing on this space
	 * Author: Tyler Ohme
	 */
	public int getTaxAmount() {
		return taxAmount;
	}
}
