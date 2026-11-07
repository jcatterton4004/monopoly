package model.spaces;

/**
 * Represents a space on the board.
 * 
 * This class represents a space on the Monopoly
 * board. It is abstract because there are lots of
 * different types of spaces on the board, so it is
 * extended by many subclasses.
 * 
 * @author Tyler Ohme
 */
public abstract class Space {
	private String name;
	
	/**
	 * Builds a new Space object.
	 * 
	 * @param name the name of this space
	 * Author: Tyler Ohme
	 */
	public Space(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the current Space.
	 * 
	 * @return this space's name
	 * Author: Tyler Ohme
	 */
	public String getName() {
		return name;
	}
}
