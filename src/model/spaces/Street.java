package model.spaces;

import javafx.scene.paint.Color;

/**
 * Represents a street space.
 * 
 * This class represents a street space that can be
 * purchased by the player. It has both Property and
 * Space as its superclasses.
 * 
 * @author Tyler Ohme
 */
public class Street extends Property {
	private int rentPrices[];
	private int buildCost;
	private Color color;
	
	private int numHouses;
	private int numHotels;
	
	/**
	 * Builds a new Street object with full necessary data and no buildings.
	 * 
	 * @param name the name of the street slot
	 * @param buyValue the cost to buy a street
	 * @param buildCost the cost to build an addition house/hotel upgrade on the street
	 * @param rentEmpty the rent with no houses
	 * @param rent1House the rent with one house
	 * @param rent2House the rent with two houses
	 * @param rent3House the rent with three houses
	 * @param rent4House the rent with four houses
	 * @param rentHotel the rent with a hotel
	 * @param color is the color of the Street
	 * Author: Tyler Ohme
	 */
	public Street(String name, int buyValue, int buildCost,
			int rentEmpty, int rent1House, int rent2House,
			int rent3House, int rent4House, int rentHotel,
			Color color) {
		// Assign superclass values
		super(name, buyValue);
		
		// Assign base variables
		numHouses = 0;
		numHotels = 0;
		rentPrices = new int[6];
		
		// Assign "constant" values
		this.buildCost = buildCost;
		rentPrices[0] = rentEmpty;
		rentPrices[1] = rent1House;
		rentPrices[2] = rent2House;
		rentPrices[3] = rent3House;
		rentPrices[4] = rent4House;
		rentPrices[5] = rentHotel;
		
		this.color = color;
	}
	
	/**
	 * Returns the current number of houses on the street.
	 * 
	 * @return the amount of houses currently built between 0-4
	 * Author: Tyler Ohme
	 */
	public int getNumHouses() {
		return numHouses;
	}
	
	/**
	 * Returns the current number of hotels on the street.
	 * 
	 * @return the amount of hotels currently build between 0-1
	 * Author: Tyler Ohme
	 */
	public int getNumHotels() {
		return numHotels;
	}
	
	/**
	 * Returns the current rent price of the street depending on the buildings
	 * that are currently present.
	 * 
	 * @return the price of rent at the current state
	 * Author: Tyler Ohme
	 */
	@Override
	public int getRent(int diceRoll) {
		if (numHotels == 1) return rentPrices[5];
		else return rentPrices[numHouses];
	}
	
	/**
	 * Returns the cost to build an additional house or upgrade 4 into a hotel.
	 * 
	 * @return the cost to build/upgrade an additional structure
	 * Author: Tyler Ohme
	 */
	public int getBuildCost() {
		return buildCost;
	}
	
	/**
	 * Handles building a property on a street. Will add an additional house if
	 * possible, will upgrade 4 houses into a hotel, and will do nothing if there
	 * is already a hotel. Returns a boolean representing its success status.
	 * 
	 * @return true if the operation was successful, false if not
	 * Author: Tyler Ohme
	 */
	public boolean build() {
		if (numHotels == 1) {
			return false;
		} else if (numHouses == 4) {
			numHouses = 0;
			numHotels = 1;
			return true;
		} else {
			numHouses++;
			return true;
		}
	}
	
	/**
	 * Handles selling  a property on a street. Will remove a house from the street
	 * if possible, will downgrade a hotel into 4 houses, and will do nothing if there
	 * isn't anything built. Returns a boolean representing its success status.
	 * 
	 * @return true if the operation was successful, false if not
	 * Author: Tyler Ohme
	 */
	public boolean sell() {
		if (numHotels == 1) {
			numHouses = 4;
			numHotels = 0;
			return true;
		} else if (numHouses > 0) {
			numHouses--;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the current number of houses on the street.
	 * 
	 * @param input is the amount of houses currently built between 0-4
	 * Author: Tyler Ohme
	 */
	public void setNumHouses(int input) {
		numHouses = input;
	}
	
	/**
	 * Sets the current number of hotels on the street.
	 * 
	 * @param input is the amount of hotels currently built between 0-1
	 * Author: Tyler Ohme
	 */
	public void setNumHotels(int input) {
		numHotels = input;
	}
	
	/**
	 * Returns the current color of the street.
	 * 
	 * @return the color of the street.
	 * Author: Tyler Ohme
	 */
	public Color getColor() {
		return color;
	}
}
