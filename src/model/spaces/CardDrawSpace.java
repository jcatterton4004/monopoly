package model.spaces;

/**
 * Represent a community chest space.
 * 
 * This class represents a community chest space on the
 * board where a player must draw a community chest card 
 * upon landing on it. It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public class CardDrawSpace extends Space {

	/**
	 * Creates a new instance of CardDrawSpace.
	 *
	 * This method constructs a new instance of CardDrawSpace
	 * by calling super() to call Space's constructor.
	 *
	 * @param name is the name of the space
	 * Author: Tyler Ohme
	 */
	public CardDrawSpace(String name) {
		super(name);
	}
}
