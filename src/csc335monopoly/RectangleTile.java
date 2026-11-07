package csc335monopoly;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.spaces.Property;
import model.spaces.Railroad;
import model.spaces.Street;
import model.spaces.Utility;

/**
 * Builds and represents a rectangular tile on the board.
 * 
 * This class holds the methods to build and maintain a rectangular
 * tile space on the Monopoly board. It extends the Tile class and
 * contains a method to create the rectangular tile to be placed on
 * the board. It holds the methods to updates the tile's details like
 * name, price, color, and logo along with methods to add and remove
 * houses from properties.
 * 
 * @author Jesse Catterton
 */
public class RectangleTile extends Tile {
	
	private GridPane rectGrid;
	private Rectangle colorBar;
	private GridPane houseSpaces;
	private StackPane playerSpace;
	private Label propertyName;
	private Label price;
	private StackPane colorBarPane;

	/**
	 * Creates an instance of RectangleTile.
	 * 
	 * This method is a constructor that constructs an instance of
	 * the RectangleTile class. It calls super() to call the constructor
	 * of its superclass Tile.
	 * 
	 * @param spaceId is the identification number for the space
	 * Author: Jesse Catterton
	 */
	public RectangleTile(int spaceId) {
		super(spaceId);
	}
	
	/**
	 * Creates a rectangular space.
	 * 
	 * This method creates a new rectangular tile to be added
	 * to the game board. It holds its components in a GridPane
	 * which contains a color bar at the top, the property name,
	 * another GridPane to hold the player pieces, and a price
	 * at the bottom. It also sets the instance fields.
	 * 
	 * @return a GridPane holding the tile's components.
	 * Author: Jesse Catterton
	 */
	public GridPane createRectTile() {
		GridPane rectTile = new GridPane();
		rectTile.setOnMouseClicked((event) -> showPropertyInfo());
		rectTile.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		rectTile.setPrefSize(68, 85);
		rectTile.setVgap(1);
		this.rectGrid = rectTile;
		
		
		colorBarPane = new StackPane(); // stackpane to add houses to color bar
		Rectangle colorBar = new Rectangle(); // color bar at top of property
		colorBar.setWidth(66);
		colorBar.setHeight(17);
		colorBar.setFill(Color.TRANSPARENT);
		colorBarPane.getChildren().add(colorBar);
		GridPane houseSpaces = new GridPane();
		houseSpaces.setAlignment(Pos.CENTER);
		houseSpaces.setPrefSize(68, 17);
		houseSpaces.setHgap(3); 
		houseSpaces.setVgap(3);
		for (int i = 0; i < 4; i++) {
			Rectangle houseSpace = new Rectangle(10, 10, Color.TRANSPARENT);
			houseSpaces.add(houseSpace, i, 0);
		}
		colorBarPane.getChildren().add(houseSpaces);
		rectTile.add(colorBarPane, 0, 0);
		this.houseSpaces = houseSpaces;
		this.colorBar = colorBar;
		
		Label propertyName = new Label("Property"); // property name
		propertyName.setWrapText(true);
		propertyName.setPrefHeight(30);
		propertyName.setPrefWidth(60);
		propertyName.setMaxWidth(60);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setStyle("-fx-font-family: 'Trebuchet MS'; -fx-font-size: 7px;");
		rectTile.add(propertyName, 0, 1);
		GridPane.setHalignment(propertyName, HPos.CENTER);
		this.propertyName = propertyName;
		
		playerSpace = new StackPane();
		GridPane playerSpaces = new GridPane(); // place to hold player pieces
		playerSpaces.setAlignment(Pos.CENTER);
		playerSpaces.setPrefSize(68, 17);
		playerSpaces.setHgap(5); 
		playerSpaces.setVgap(5);
		for (int i = 0; i < 4; i++) { // loops to add 4 columns
			for (int j = 0; j < 2; j++) { // loops to add two rows
				Circle space = new Circle(5);
				space.setFill(Color.TRANSPARENT);
				playerSpaces.add(space, i, j);
			}
		}
		playerSpace.getChildren().add(playerSpaces);
		rectTile.add(playerSpace, 0, 2);
		this.playerSpaces = playerSpaces;
		
		Label price = new Label(""); // property's price
		rectTile.add(price, 0, 3);
		price.setStyle(" -fx-font-family: 'Trebuchet MS'; -fx-font-size: 10px;");
		GridPane.setHalignment(price, HPos.CENTER);
		this.price = price;
		return rectGrid;
	}
	
	/**
	 * Shows a detailed view of property's information.
	 * 
	 * This method creates an Alert object to show the user extra information
	 * about a given property. It displays the properties price, its current
	 * owner, the current rent price, the current number of houses and hotels 
	 * if its a street, and its mortgage value.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showPropertyInfo() {
		if (space instanceof Property) { // excludes tax and community chest spaces
			Alert msg = new Alert(Alert.AlertType.INFORMATION);
			msg.setTitle("Property Information"); 
			msg.setHeaderText(space.getName());
			
			String message = "Price: " + ((Property) space).getBuyValue();
			if (((Property) space).getOwner() == null) message += "\nCurrent Owner: Unowned ";
			else message += "\nCurrent Owner: Player " + (((Property) space).getOwner().getPlayerId() + 1);
			if (space instanceof Street) { // only Streets have a set rent price and the ability to have houses/hotels
				message += "\nCurrent Rent Price: " + ((Street) space).getRent(0);
				message += "\nCurrent Number of Houses: " + ((Street) space).getNumHouses();
				message += "\nCurrent Number of Hotels: " + ((Street) space).getNumHotels();
			}
			else if (space instanceof Utility) message += "\nCurrent Rent Price: Determined by roll";
			else if (space instanceof Railroad) message += "\nCurrent Rent Price: Determined by number of railroads owned";
			message += "\nMortgage Value: " + ((Property) space).getMortgageValue();
			
			msg.setContentText(message);
			msg.showAndWait(); 
		}
	}
	
	/**
	 * Sets the propertyName variable.
	 * 
	 * This method allows the name of the space to be updated.
	 * 
	 * @param name is the String name of the tile.
	 * Author: Jesse Catterton
	 */
	@Override
	public void setName(String name) {
		propertyName.setText(name);
	}
	
	/**
	 * Sets the variable colorBar's fill.
	 * 
	 * This method allows the color at the top of the property to be updated.
	 * 
	 * @param color is a Color object with the property's color.
	 * Author: Jesse Catterton
	 */
	public void setColor(Color color) {
		colorBar.setFill(color);
	}
	
	/**
	 * Returns the fill color of colorBar.
	 * 
	 * This method allows the color at the top of a property to be retrieved.
	 * 
	 * @return the color of the tile's colorBar.
	 * Author: Jesse Catterton
	 */
	public Color getColor() {
		return (Color) colorBar.getFill();
	}
	
	/**
	 * Sets the variable price.
	 * 
	 * This method allows the price of the property to be updated.
	 * 
	 * @param amount is an int amount of how much the property cost.
	 * Author: Jesse Catterton
	 */
	public void setPrice(int amount) {
		if (price != null) { // doesn't set the price for community chest
			if (amount != 0) price.setText(String.valueOf(amount));
			else price.setText("");
		}
	}

	/**
	 * Builds a house on the tile.
	 * 
	 * This method is called when a player buys a house on a property. It
	 * will set the fill of one of the tile's house spaces to green to add
	 * a house or set one of them to red to add a hotel. It counts how many
	 * houses are already on the property and builds a hotel if there are 4
	 * or builds one house if there are less than 4
	 * 
	 * Author: Jesse Catterton
	 */
	public void build() {
		ObservableList<Node> spaces = houseSpaces.getChildren();
		if (((Rectangle) spaces.get(0)).getFill() == Color.RED) return; // doesn't build if hotel is already built
		
		int numHouses = 0;
	    for (Node space : spaces) { // counts how many houses on spacs
	        if (((Rectangle) space).getFill() == Color.GREEN) numHouses++;
	    }
		
		if (numHouses == 4) { // adds single hotel and removes houses 
			((Rectangle) spaces.get(0)).setFill(Color.RED);
			((Rectangle) spaces.get(1)).setFill(Color.TRANSPARENT);
			((Rectangle) spaces.get(2)).setFill(Color.TRANSPARENT);
			((Rectangle) spaces.get(3)).setFill(Color.TRANSPARENT);
		} else { // adds house
			((Rectangle) spaces.get(numHouses)).setFill(Color.GREEN);
		}
	}
	
	/**
	 * Sells a house on the tile.
	 * 
	 * This method is called when a player sells a house on a property. It
	 * will set the fill of one of the tile's house spaces to transparet to 
	 * remove a house or set all of them to green to remove a hotel. It adds
	 * 4 houses if there is a hotel or removes one house if there isn't a hotel.
	 * 
	 * Author: Jesse Catterton
	 */
	public void sell() {
		ObservableList<Node> spaces = houseSpaces.getChildren();
		
		if (((Rectangle) spaces.get(0)).getFill() == Color.RED) { // sells hotel and adds 4 houses
			((Rectangle) spaces.get(0)).setFill(Color.GREEN);
		    ((Rectangle) spaces.get(1)).setFill(Color.GREEN);
		    ((Rectangle) spaces.get(2)).setFill(Color.GREEN);
		    ((Rectangle) spaces.get(3)).setFill(Color.GREEN);
		    return;
		}

		int numHouses = 0;
	    for (Node space : spaces) { // counts how many houses on spacs
	        if (((Rectangle) space).getFill() == Color.GREEN) numHouses++;
	    }
		
	    if (numHouses != 0) ((Rectangle) spaces.get(numHouses - 1)).setFill(Color.TRANSPARENT); // removes a house
	}
	
	/**
	 * Adds an image logo to the tile.
	 * 
	 * This method overrides Tile's abstract addLogo() method. It is called
	 * to add an image to a space that is a logo corresponding to the name
	 * of the space. This image will change depending on what the theme of
	 * the board is. It adds logos for all non-property spaces along with
	 * water works and electric company.
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
		if (tileNum == 2 || tileNum == 7 || tileNum == 17 || tileNum == 22 || tileNum == 33 || tileNum == 36) {
			path += "community_chest.png";
		} else if (tileNum == 4) {
			path += "income_tax.png";
			price.setText("PAY " + price.getText());
		} else if (tileNum == 5 || tileNum == 15 || tileNum == 25 || tileNum == 35) {
			path += "railroad.png";
		} else if (tileNum == 12) {
			path += "electric_company.png";
		} else if (tileNum == 28) {
			path += "water_works.png";
		} else if (tileNum == 38) {
			path += "luxury_tax.png";
			price.setText("PAY " + price.getText());
		} else return;
		
		colorBarPane.getChildren().clear();
		colorBarPane.setPrefHeight(0);

		Image image = new Image(path);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(45);
		imageView.setFitHeight(45);
		playerSpace.getChildren().add(0, imageView); // adds logo under player spaces
		
		if (path.endsWith("community_chest.png")) { // makes community chest image bigger
			imageView.setFitWidth(55);
			imageView.setFitHeight(55);
			price.setStyle(" -fx-font-family: 'Trebuchet MS'; -fx-font-size: 1px;");
		}
	}
	
}
