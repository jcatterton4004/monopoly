package model.spaces;

/**
 * Represent the go to jail space.
 * 
 * This class represents the go to jail  space on the 
 * board where a player is sent to jail if they land on
 * it. It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public class GoToJailSpace extends Space {
	/**
	 * Builds a new GoToJailSpace object.
	 * 
	 * @param name the name of the space
	 * Author: Tyler Ohme
	 */
	public GoToJailSpace(String name) {
		super(name);
	}
}
