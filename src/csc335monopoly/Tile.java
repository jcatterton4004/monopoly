package csc335monopoly;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.spaces.Property;
import model.spaces.Railroad;
import model.spaces.Space;
import model.spaces.Street;
import model.spaces.Utility;

/**
 * Creates a space on the game board.
 * 
 * This class holds the visual representation of single space/tile on the 
 * monopoly game board. It is an abstract class that is extended and implemented
 * fully by the RectangleTile and CornerTile classes. It holds general information
 * about the tiles like spaceId, player spaces, players on the space, and the 
 * space object. It also has general methods like moving on and off the space.
 * 
 * @author Jesse Catterton
 */
public abstract class Tile {

	protected int spaceId;
	protected GridPane playerSpaces;
		
	protected ArrayList<Player> players; // players on the space currently
	protected ObservableList<Node> spots; // each player has their own spot on the board (by index/playerId)
	protected Space space; // its corresponding space object
	
	/**
	 * Constructs a Tile object.
	 * 
	 * This method creates a new Tile object. It takes in
	 * a spaceId which represents where it is located on 
	 * the game board. Go starts at 0 and increments clockwise.
	 * 
	 * @param spaceId
	 * Author: Jesse Catterton
	 */
	public Tile(int spaceId) {
		this.spaceId = spaceId;
		players = new ArrayList<Player>();
	}
	
	/**
	 * Abstract method to set space's name.
	 * 
	 * This method allows the name of the space to be updated.
	 * 
	 * @param name is the String name of the tile.
	 * Author: Jesse Catterton
	 */
	public abstract void setName(String name);
	
	/**
	 * Abstract method to set space's logo.
	 * 
	 * This method allows the logo of the space to be updated.
	 * 
	 * @param tileNum is the tile's spaceId
	 * @param theme is a string theme of the board
	 * Author: Jesse Catterton
	 */
	public abstract void addLogo(int tileNum, String theme);
	
	/** 
	 * Returns the spaceId variable.
	 * 
	 * This method returns the id of the space so that 
	 * its position on the board can be identified.
	 * 
	 * @return an integer spaceId identifying the space
	 * Author: Jesse Catterton
	 */
	public int getSpaceId() {
		return spaceId;
	}
	
	/**
	 * Sets the space object variable.
	 * 
	 * This method sets the variable that holds the Space
	 * object for the tile. This holds more detailed information
	 * about the tile's name, price, and rent.
	 * 
	 * @param boardSpace is a Space object for the board space
	 * Author: Jesse Catterton
	 */
	public void setSpace(Space boardSpace) {
		space = boardSpace;
	}
	
	/**
	 * Returns the space object variable.
	 * 
	 * This method returns the tile's Space object for the
	 * tile. It holds more detailed information about the
	 * type of space that can be used to determine what 
	 * actions need to be taken if a player lands on it.
	 * 
	 * @return space is the tile's corresponding Space object
	 * Author: Jesse Catterton
	 */
	public Space getSpace() {
		return space;
	}
	
	/**
	 * Moves a player off of the tile.
	 * 
	 * This method is called when a player is moving off
	 * the current space to a new space. It removes the 
	 * player from the tile's list of current players and
	 * clears the spot the player's piece was on.
	 * 
	 * @param player is the Player object moving off of this space
	 * Author: Jesse Catterton
	 */
	public void moveFrom(Player player) {
		spots = playerSpaces.getChildren(); // updates the spots list
		players.remove(player);
	    int id = player.getPlayerId();
	    Circle spot = (Circle) spots.get(id); // finds player's corresponding spot
	    spot.setFill(Color.TRANSPARENT); // clears player piece from spot
	}
	
	/**
	 * Moves a player to the tile.
	 * 
	 * This method is called when a player is moving onto
	 * this space. It adds the player to the tile's list of 
	 * current players and fills in the player's corresponding
	 * spot with the player's piece color.
	 * 
	 * @param player is the Player object moving to this space
	 * Author: Jesse Catterton
	 */
	public void moveTo(Player player) {
		spots = playerSpaces.getChildren(); // updates the spots list
		players.add(player);
	    int id = player.getPlayerId();
	    Circle spot = (Circle) spots.get(id); // finds player's corresponding spot
	    spot.setFill(player.getPieceColor()); // fills spot with player's color
	}
}
