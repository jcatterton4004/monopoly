package csc335monopoly;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * Builds and represents a corner tile on the board.
 * 
 * This class holds the methods to build and maintain a corner
 * tile space on the Monopoly board. It extends the Tile class and
 * contains a method to create the corner tile to be placed on
 * the board. It holds the methods to updates the tile's details like
 * name and logo along with methods to add and remove players from jail.
 * 
 * @author Jesse Catterton
 */
public class CornerTile extends Tile {
	
	private BorderPane cornerGrid;
	private Label cornerName;
	private GridPane jailSpaces;
	private StackPane playerSpace;
	private StackPane jail;

	/**
	 * Creates an instance of CornerTile.
	 * 
	 * This method is a constructor that constructs an instance of
	 * the CornerTile class. It calls super() to call the constructor
	 * of its superclass Tile.
	 * 
	 * @param spaceId is the identification number for the space
	 * Author: Jesse Catterton
	 */
	public CornerTile(int spaceId) {
		super(spaceId);
	}
	
	/**
	 * Creates a corner space.
	 * 
	 * This method creates a new corner tile to be added
	 * to the game board. It holds its components in a GridPane
	 * which contains the name of the corner and another 
	 * GridPane to hold the player pieces. All of this is 
	 * contained in a BorderPane.
	 * 
	 * @param rotation is the amount to rotate the tile's components
	 * @return a BorderPane holding the tile's components.
	 * Author: Jesse Catterton
	 */
	public BorderPane createCornerTile(double rotation) {
		BorderPane cornerTile = new BorderPane();
		cornerTile.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		cornerTile.setPrefSize(85, 85);
		GridPane components = new GridPane();
		cornerTile.setCenter(components);
		components.setRotate(rotation);
		this.cornerGrid = cornerTile;
		
		Rectangle topSpaceFiller = new Rectangle(20, 20, Color.TRANSPARENT); // spaces the text away from the top
		Rectangle sideSpaceFiller = new Rectangle(5, 5, Color.TRANSPARENT); // spaces the text into the center of the corner
		components.add(topSpaceFiller, 1, 0);
		components.add(sideSpaceFiller, 0, 0);
		
		Label name = new Label("Corner"); // title of corner
		name.setWrapText(true);
		name.setTextAlignment(TextAlignment.CENTER);
		name.setAlignment(Pos.CENTER);
		name.setStyle("-fx-font-family: 'Trebuchet MS'; -fx-font-size: 10px;");
		GridPane.setHalignment(name, HPos.CENTER);
		components.add(name, 1, 1);
		this.cornerName = name;
		
		playerSpace = new StackPane();
		GridPane playerSpaces = new GridPane(); // place to hold player pieces
		playerSpaces.setAlignment(Pos.CENTER);
		playerSpaces.setPrefSize(68, 17);
		playerSpaces.setHgap(5);
		playerSpaces.setVgap(5);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				Circle space = new Circle(5);
				space.setFill(Color.TRANSPARENT);
				playerSpaces.add(space, i, j);
			}
		}
		playerSpace.getChildren().add(playerSpaces);
		components.add(playerSpace, 1, 2);
		this.playerSpaces = playerSpaces;
		return cornerGrid;
	}
	
	/**
	 * Creates the jail space.
	 * 
	 * This method creates the jail tile to be added
	 * to the game board. It holds its components in a GridPane
	 * which contains the just visiting space, jail space, and  
	 * another GridPane to hold the player pieces. All of this 
	 * is  contained in a BorderPane.
	 * 
	 * @return a BorderPane holding the tile's components.
	 * Author: Jesse Catterton
	 */
	public BorderPane createJailCorner() {
		BorderPane cornerTile = new BorderPane();
		cornerTile.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		cornerTile.setPrefSize(85, 85);
		StackPane layout = new StackPane();
		cornerTile.setCenter(layout);
		
		GridPane components = new GridPane(); // holds the just visitng and jail components
		layout.getChildren().add(components);
		this.cornerGrid = cornerTile;
		
		Label name = new Label("Corner");
		this.cornerName = name;
		
		GridPane playerSpaces = new GridPane(); // place to hold player pieces
		playerSpaces.setAlignment(Pos.CENTER);
		playerSpaces.setPrefSize(85, 85);
		playerSpaces.setHgap(5);
		playerSpaces.setVgap(5);
		for (int i = 3; i < 7; i++) { // adds 4 player spaces on one side of just visiting
			Circle space = new Circle(5);
			space.setFill(Color.TRANSPARENT);
			playerSpaces.add(space, i, 0);
		}
		for (int i = 3; i < 7; i++) { // adds 4 player spaces on the other side of just visiting
			Circle space = new Circle(5);
			space.setFill(Color.TRANSPARENT);
			playerSpaces.add(space, 0, i);
		}
		layout.getChildren().add(playerSpaces);
		this.playerSpaces = playerSpaces;
		
		Rectangle justVisitingFiller = new Rectangle(28, 28, Color.TRANSPARENT);
		components.add(justVisitingFiller, 0, 0);
		
		BorderPane just = new BorderPane();
		just.setPrefSize(28, 57);
		Label justText = new Label("JUST");
		justText.setStyle("-fx-font-family: 'Trebuchet MS'; -fx-font-size: 7px;");
		justText.setRotate(180);
		just.setCenter(justText);
		components.add(just, 1, 0);
		
		BorderPane visiting = new BorderPane();
		visiting.setPrefSize(57, 28);
		Label visitingText = new Label("VISITING");
		visitingText.setStyle(" -fx-font-family: 'Trebuchet MS'; -fx-font-size: 7px;");
		visitingText.setRotate(90);
		visiting.setCenter(visitingText);
		components.add(visiting, 0, 1);
		
		jail = new StackPane();
		jailSpaces = new GridPane();
		jailSpaces.setAlignment(Pos.CENTER);
		jailSpaces.setPrefSize(57, 57);
		jailSpaces.setHgap(3);
		jailSpaces.setVgap(3);
		for (int i = 0; i < 4; i++) { // adds player spaces within the jail
			for (int j = 0; j < 2; j++) {
				Circle space = new Circle(5);
				space.setFill(Color.TRANSPARENT);
				jailSpaces.add(space, i, j);
			}
		}
		jail.getChildren().add(jailSpaces);
		components.add(jail, 1, 1);
		
		return cornerTile;
	}
	
	/**
	 * Sets the cornerName variable.
	 * 
	 * This method allows the name of the space to be updated.
	 * 
	 * @param name is the String name of the tile.
	 * Author: Jesse Catterton
	 */
	@Override
	public void setName(String name) {
		cornerName.setText(name);
	}
	
	/**
	 * Places a player in jail.
	 * 
	 * This method places a player in jail visually by filling in one
	 * of the jail spaces with the player's piece color.
	 * 
	 * @param player is the player being sent to jail
	 * Author: Jesse Catterton
	 */
	public void sendToJail(Player player) {
		ObservableList<Node> jailSpots = jailSpaces.getChildren();
	    int id = player.getPlayerId();
		Circle spot = (Circle) jailSpots.get(id); // space determined by playerId
		spot.setFill(player.getPieceColor()); // adds player to jail
	}
	
	/**
	 * Removes a player fromt jail.
	 * 
	 * This method removes a player from jail visually by setting the 
	 * player's space in jail to transparent.
	 * 
	 * @param player is the player being removed from jail
	 * Author: Jesse Catterton
	 */
	public void removeFromJail(Player player) {
		ObservableList<Node> jailSpots = jailSpaces.getChildren();
	    int id = player.getPlayerId();
		Circle spot = (Circle) jailSpots.get(id); // space determined by playerID
		spot.setFill(Color.TRANSPARENT); // removes player from jail
	}

	/**
	 * Adds an image logo to the corner tile.
	 * 
	 * This method overrides Tile's abstract addLogo() method. It is called
	 * to add an image to a corner space that is a logo corresponding to the name
	 * of the space. This image will change depending on what the theme of
	 * the board is. It adds logos for all the corner spaces.
	 * 
	 * Author: Jesse Catterton.
	 */
	@Override
	public void addLogo(int tileNum, String theme) {
		// changes image folder path depending on theme
		String path = "file:src/default_images/";
		if (theme.equals("Marvel Comics")) path = "file:src/marvel_images/";
		else if (theme.equals("DC Comics")) path = "file:src/dc_images/";
		
		// determines what image needs to be added based on space
		if (tileNum == 0) {
			path += "go.png";
		} else if (tileNum == 10) {
			path += "jail.png";
		} else if (tileNum == 20) {
			path += "free_parking.png";
		} else if (tileNum == 30) {
			path += "go_to_jail.png";
		} 
		
		Image image = new Image(path);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(45);
		imageView.setFitHeight(45);
		
		if (tileNum == 0) { // changes size of go space
			imageView.setFitWidth(50);
			imageView.setFitHeight(50);
			imageView.setRotate(tileNum);
			playerSpace.getChildren().add(0, imageView);
		} else if (tileNum == 10) { // changes size of jail space
			imageView.setFitWidth(57);
			imageView.setFitHeight(57);
			imageView.setRotate(180);
			jail.getChildren().add(0, imageView);
		} else {
			playerSpace.getChildren().add(0, imageView);
		}
	}

}
