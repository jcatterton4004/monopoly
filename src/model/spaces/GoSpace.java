package model.spaces;

/**
 * Represent the Go space.
 * 
 * This class represents the go space on the Monopoly
 * board where a player collects money when they land on 
 * or pass it. It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public class GoSpace extends Space {
	private int collectAmount;

	/**
	 * Builds a new GoSpace object.
	 * 
	 * @param name the name of this space
	 * @param collectAmount the amount to be collected when this space is passed
	 * Author: Tyler Ohme
	 */
	public GoSpace(String name, int collectAmount) {
		super(name);
		this.collectAmount = collectAmount;
	}
	
	/**
	 * Sets the collectAmount variable.
	 * 
	 * @param value is the amount to collect on passing go
	 * Author: Tyler Ohme
	 */
	public void setCollectAmount(int value) {
		collectAmount = value;
	}
	
	/**
	 * Returns the value of the collectAmount variable.
	 * 
	 * @return the amount to be collected after passing go
	 * Author: Tyler Ohme
	 */
	public int getCollectAmount() {
		return collectAmount;
	}
}
