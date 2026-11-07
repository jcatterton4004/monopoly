package csc335monopoly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.spaces.CardDrawSpace;
import model.spaces.FreeParkingSpace;
import model.spaces.GoToJailSpace;
import model.spaces.Property;
import model.spaces.Railroad;
import model.spaces.Space;
import model.spaces.Street;
import model.spaces.TaxSpace;
import savesystem.GameSave;
import savesystem.SettingsSave;

/**
 * Represents the Monopoly GUI view.
 * 
 * This class holds the methods to maintain and launch
 * the GUI version of Monopoly. It builds the board utilizing
 * a GridPane to hold Tile objects that represent the spaces
 * on the board. The players interact with the board by
 * clicking on player profiles and properties to view their
 * information or clicking on buttons to complete actions in
 * the game.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 * @author Sophia Harms
 */
public class MonopolyView extends Application {
	
	private GridPane window = new GridPane();
	private StackPane boardOverlay = new StackPane();
	private GridPane board = new GridPane();
	private Tile[] tiles = new Tile[40];
	private Button rollDice;
	private Button buyProperty;
	private Button proposeTrade;
	private Button build;
	private Button sell;
	private Button endTurn;
	private Button save;
	private Player[] players;
	private MonopolyController controller;
	private CommunityChest cardDeck;
	private GridPane sellList = new GridPane();
	private Stage sellStage;
	
	private int humanPlayerCount;
	private int computerPlayerCount;
	
	private String theme;
	private Color[] themeColors; 
	private Color[] spaceColors = {Color.TRANSPARENT, Color.BROWN, Color.TRANSPARENT, Color.BROWN, Color.TRANSPARENT, Color.TRANSPARENT, Color.LIGHTBLUE, Color.TRANSPARENT, Color.LIGHTBLUE, Color.LIGHTBLUE, Color.TRANSPARENT, Color.PINK, Color.TRANSPARENT, Color.PINK, Color.PINK, Color.TRANSPARENT, Color.ORANGE, Color.TRANSPARENT, Color.ORANGE, Color.ORANGE, Color.TRANSPARENT, Color.RED, Color.TRANSPARENT, Color.RED, Color.RED, Color.TRANSPARENT, Color.YELLOW, Color.YELLOW, Color.TRANSPARENT, Color.YELLOW, Color.TRANSPARENT, Color.GREEN, Color.GREEN, Color.TRANSPARENT, Color.GREEN, Color.TRANSPARENT, Color.TRANSPARENT, Color.DARKBLUE, Color.TRANSPARENT, Color.DARKBLUE};
	private Color[] marvelColors = {null, Color.DARKRED, null, Color.DARKRED, null, null, Color.BLACK, null, Color.BLACK, Color.BLACK, null, Color.DARKBLUE, null, Color.DARKBLUE, Color.DARKBLUE, null, Color.GOLD, null, Color.GOLD, Color.GOLD, null, Color.GREY, null, Color.GREY, Color.GREY, null, Color.PURPLE, Color.PURPLE, null, Color.PURPLE, null, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, null, Color.DARKOLIVEGREEN, null, null, Color.ORANGE, null, Color.ORANGE};
	private Color[] dcColors = {null, Color.AQUAMARINE, null, Color.AQUAMARINE, null, null, Color.RED, null, Color.RED, Color.RED, null, Color.GREY, null, Color.GREY, Color.GREY, null, Color.YELLOW, null, Color.YELLOW, Color.YELLOW, null, Color.DARKBLUE, null, Color.DARKBLUE, Color.DARKBLUE, null, Color.DARKVIOLET, Color.DARKVIOLET, null, Color.DARKVIOLET, null, Color.LIGHTBLUE, Color.LIGHTBLUE, null, Color.LIGHTBLUE, null, null, Color.DARKSLATEGREY, null, Color.DARKSLATEGREY};
	private ObservableList<Node> playerProfiles;
	private Label[] playerBalances;
	
	private final Image[] dieFaces = new Image[6];
	private ImageView dieView1 = new ImageView();
	private ImageView dieView2 = new ImageView();
	
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public MonopolyView() {
		
	}

	/**
	 * Builds the primary board and starts the game.
	 * 
	 * This method is called first when the MonopolyView class
	 * is launched. It adds a Scene to the Stage object that 
	 * consists of the board and playerBar. This method calls
	 * the methods to build the board, prepare the game through
	 * collecting settings and assigning tile names, and start
	 * the game.
	 * 
	 * Author: Jesse Catterton
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Monopoly");
		window.add(boardOverlay, 1, 0);
		boardOverlay.getChildren().add(board);
		board.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		addTopRow();
		addBottomRow();
		addSideRow(90, 0);
		addSideRow(270, 10); 
		
		showMenuPage();
		assignTiles(); // assigns space names, colors, and prices
		startGame();
		initializeDice(); // load dice images
		
		Scene scene = new Scene(window, 865, 785);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Builds the top row of the Monopoly board.
	 * 
	 * This method creates the top row of the Monopoly board
	 * by creating the top two corner tiles and a row of upside
	 * down rectangular tiles. It adds each tile to the tiles
	 * array in their corresponding spots (in order clockwise 
	 * around the board from go).
	 * 
	 * Author: Jesse Catterton
	 */
	public void addTopRow() {
		CornerTile corner2 = new CornerTile(10); // jail corner
		BorderPane grid2 = corner2.createJailCorner();
		tiles[10] = corner2;
		board.add(grid2, 0, 0);
		
		CornerTile corner3 = new CornerTile(20); // free parking corner
		BorderPane grid3 = corner3.createCornerTile(225);
		tiles[20] = corner3;
		board.add(grid3, 10, 0);
		
		for (int i = 1; i < 10; i++) { // adds each upside down space on top of board
			RectangleTile newTile = new RectangleTile(10 + i);
			GridPane grid = newTile.createRectTile();
			tiles[10 + i] = newTile;
			grid.setRotate(180);
			board.add(grid, i, 0);
		}

	}
	
	/**
	 * Builds the bottom row of the Monopoly board.
	 * 
	 * This method creates the bottom row of the Monopoly board
	 * by creating the bottom two corner tiles and a row of 
	 * rectangular tiles. It adds each tile to the tiles array
	 * in their corresponding spots (in order clockwise around 
	 * the board from go).
	 * 
	 * Author: Jesse Catterton
	 */
	public void addBottomRow() {
		CornerTile corner1 = new CornerTile(0); // go corner
		BorderPane grid1 = corner1.createCornerTile(45);
		tiles[0] = corner1;
		board.add(grid1, 0, 10);
		
		CornerTile corner4 = new CornerTile(30); // go to jail corner
		BorderPane grid4 = corner4.createCornerTile(315);
		tiles[30] = corner4;
		board.add(grid4, 10, 10);
		
		for (int i = 1; i < 10; i++) { // adds each space on bottom of board
			RectangleTile newTile = new RectangleTile(40 - i);
			GridPane grid = newTile.createRectTile();
			tiles[40 - i] = newTile;
			board.add(grid, i, 10);
		}
	}
	
	/**
	 * Builds the side columns of Monopoly board.
	 * 
	 * This method creates two sides of the Monopoly board
	 * by creating rectangular tiles and then rotating them
	 * either 90 or 270 degrees depending on which side of the
	 * board they're on. In order to rotate without messing up
	 * format, I had to wrap them in a StackPane like we did in
	 * the Mastermind class. It adds each tile to the tiles array
	 * in their corresponding spots (in order clockwise around 
	 * the board from go).
	 * 
	 * @param rotation is a double of 90 or 270 for the left or right side of board
	 * @param column is the column of the GridPane each tile should be added to (0 or 10)
	 * Author: Jesse Catterton
	 */
	public void addSideRow(double rotation, int column) {
		for (int i = 1; i < 10; i++) { // loops to add 9 tiles on a side
			RectangleTile newTile;
	        if (rotation == 90) { // assigns left side spaces in proper place in tiles array
	        	newTile = new RectangleTile(10 - i);
	        	tiles[10 - i] = newTile;
	        } else { // assigns right side spaces in proper place in tiles array
	        	newTile = new RectangleTile(20 + i);
	        	tiles[20 + i] = newTile;
	        }
			GridPane grid = newTile.createRectTile();
			grid.setMinSize(68, 85);
			grid.setPrefSize(68, 85);
			grid.setMaxSize(68, 85);
			
	        StackPane wrapper = new StackPane(grid); // used so space can be rotated easily
	        grid.setRotate(rotation);
	        wrapper.setMinSize(85, 68);
	        wrapper.setPrefSize(85, 68);
	        wrapper.setMaxSize(85, 68);
	        board.add(wrapper, column, i);
		}
	}
	
	/**
	 * Gives Tile objects names, colors, and prices.
	 * 
	 * This method assigns each Tile object the name of their space,
	 * the color of their color bar, and their price. It also sets
	 * each Tile's corresponding Space object. 
	 * 
	 * @throws FileNotFoundException if the theme file can't be found/does not exist
	 * Author: Jesse Catterton
	 */
	public void assignTiles() throws FileNotFoundException {
		Space[] spaces = controller.getSpaces(); // gets array of Space objects

		controller.adjustPropertyPrices(); // accounts for property price adjustment
		controller.updateGoAmount();
		Color[] colorTheme;
		if (theme.equals("Atlantic City")) {
			colorTheme = spaceColors;
			board.setStyle("-fx-background-color: #D2E5D2;");
		} else if (theme.equals("Marvel Comics")) {
			colorTheme = marvelColors;
			board.setStyle("-fx-background-color: #92B1CC;");
		} else {
			colorTheme = dcColors;
			board.setStyle("-fx-background-color: #E7DDFF;");
		}
		themeColors = colorTheme;
		
		for (int i = 0; i < 40; i++) { // loops to assign each tile its information
			// Assign tiles the data stored in the game board
			if (spaces[i] instanceof Street) ((RectangleTile) tiles[i]).setColor(((Street) spaces[i]).getColor());
			if (spaces[i] instanceof Property) ((RectangleTile) tiles[i]).setPrice(((Property) spaces[i]).getBuyValue());
			if (spaces[i] instanceof TaxSpace) ((RectangleTile) tiles[i]).setPrice(((TaxSpace) spaces[i]).getTaxAmount());
			tiles[i].setName(spaces[i].getName());
			tiles[i].setSpace(spaces[i]);
			tiles[i].addLogo(i, theme);
			if (tiles[i].getSpace() instanceof Street) {
				Street street = (Street) tiles[i].getSpace();
				for (int j = 0; j < street.getNumHouses() + street.getNumHotels(); j++) {
					((RectangleTile) tiles[i]).build();
				}
			}
		}
		tiles[0].setName("COLLECT $" + controller.getGoAmount());
	}
	
	/**
	 * Adds logos to the board.
	 * 
	 * This method adds the proper logos to the middle of the board
	 * and to the community chest deck on the board based on what the
	 * theme is.
	 * 
	 * Author: Jesse Catterton
	 */
	public void addLogos() {
		String folder = "default_images";
		if (theme.equals("Marvel Comics")) folder = "marvel_images";
		else if (theme.equals("DC Comics")) folder = "dc_images";
		Image logoImage = new Image("file:src/" + folder + "/logo.png"); // adds monopoly logo to middle of board
		ImageView logoImageView = new ImageView(logoImage);
		logoImageView.setFitWidth(400);
		logoImageView.setFitHeight(105);
		if (theme.equals("Marvel Comics")) {
			logoImageView.setFitWidth(365);
			logoImageView.setFitHeight(157);
		} else if (theme.equals("DC Comics")){
			logoImageView.setFitWidth(348);
			logoImageView.setFitHeight(178);
		}
		boardOverlay.getChildren().add(logoImageView);
		
		StackPane boardDeck = new StackPane();
		Rectangle cards = new Rectangle(65, 40, Color.WHITE);
		cards.setStroke(Color.BLACK);
		cards.setStrokeWidth(1);
		boardDeck.getChildren().add(cards);
		Image ccImage = new Image("file:src/" + folder + "/community_chest.png"); // adds community chest logo to card deck
		ImageView ccImageView = new ImageView(ccImage);
		ccImageView.setFitWidth(40);
		ccImageView.setFitHeight(40);
		boardDeck.getChildren().add(ccImageView);
		board.add(boardDeck, 5, 8);
	}
	
	/**
	 * Adds player profiles to side of board.
	 * 
	 * This method adds the player's icons to the side of the 
	 * board so that the number of players and their balances
	 * can be viewed during the game. These profiles can also
	 * be clicked on to view more detailed information about
	 * the player.
	 * 
	 * Author: Jesse Catterton
	 */
	public void addPlayerProfiles() {
		GridPane playerBar = new GridPane();
		playerBar.setPadding(new Insets(5));
		playerBar.setVgap(10);
		window.add(playerBar, 0, 0);
		playerBalances = new Label[players.length]; // holds playerBalances in an array so they can be updated throughout the game
		
		for (int i = 0; i < players.length; i++) { // loops to add a profile for each player
			GridPane playerProfile = new GridPane();
			players[i].setPlayerProfile(playerProfile); // adds players mouse click handler to show profile
			playerProfile.setPadding(new Insets(10));
			playerProfile.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
			playerProfile.setVgap(10);
			playerProfile.setHgap(10);
			
			Label playerId = new Label("Player " + (players[i].getPlayerId() + 1));
			Rectangle playerColor = new Rectangle(10, 10, players[i].getPieceColor());
			Label playerBalance = new Label("$" + players[i].getBalance());
			playerBalances[i] = playerBalance; 
			playerProfile.add(playerId, 0, 0);
			playerProfile.add(playerColor, 0, 1);
			playerProfile.add(playerBalance, 0, 2);
			playerBar.add(playerProfile, 0, i); // adds player profile to player bar
		}
		playerProfiles = playerBar.getChildren();
	}
	
	/**
	 * Initializes the array of die faces
	 * 
	 * This method loads the 6 image files each representing the face of a die
	 * so that it can be displayed after rolls, and initializes the viewing objects.
	 * 
	 * Author: Tyler Ohme
	 */
	private void initializeDice() {
		// Load files
		dieFaces[0] = new Image("file:src/image_files/monopoly_dice_1.png");
		dieFaces[1] = new Image("file:src/image_files/monopoly_dice_2.png");
		dieFaces[2] = new Image("file:src/image_files/monopoly_dice_3.png");
		dieFaces[3] = new Image("file:src/image_files/monopoly_dice_4.png");
		dieFaces[4] = new Image("file:src/image_files/monopoly_dice_5.png");
		dieFaces[5] = new Image("file:src/image_files/monopoly_dice_6.png");
		
		// Initialize ImageView object settings
		dieView1.setFitWidth(60);
		dieView1.setFitHeight(60);
		dieView1.setPreserveRatio(true);

		dieView2.setFitWidth(60);
		dieView2.setFitHeight(60);
		dieView2.setPreserveRatio(true);
		
		// Add image viewers to board
		board.add(dieView1, 4, 3);
		board.add(dieView2,  6, 3);
	}
	
	/**
	 * Shows the menu window upon launching the program.
	 * 
	 * This method creates a window with two buttons, new game
	 * and load game. Users can click new game to start a new
	 * game of monopoloy or click load game to resume a previous
	 * gane.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showMenuPage() {
		Stage menuStage = new Stage();
		menuStage.setTitle("Start Menu");
		BorderPane menuWindow = new BorderPane();
		GridPane menuList = new GridPane();
		menuList.setHgap(10);
		menuList.setVgap(20);
		menuList.setPadding(new Insets(20));
		menuWindow.setCenter(menuList);
		
		Image image = new Image("file:src/default_images/logo.png"); // monopoly logo
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(560);
		imageView.setFitHeight(147);
		menuList.add(imageView, 0, 0);
		
		Button startGame = new Button("New game");
		startGame.setOnAction((event) -> { // starts new game
			try {
				controller = new MonopolyController();
				theme = controller.getTheme();
				humanPlayerCount = controller.getHumanPlayerCount();
				computerPlayerCount = controller.getComputerPlayerCount();
			} catch (IOException e) {
				System.out.println("ERROR CREATING CONTROLLER: " + e);
				e.printStackTrace();
			}
			menuStage.close();
		});
		menuList.add(startGame, 0, 1);
		GridPane.setHalignment(startGame, HPos.CENTER);
		
		Button loadGame = new Button("Load game");
		loadGame.setOnAction((event) -> {showLoadWindow(menuStage);});
		menuList.add(loadGame, 0, 2);
		GridPane.setHalignment(loadGame, HPos.CENTER);
		
		Button openSettings = new Button("Settings");
		openSettings.setOnAction((event) -> {
		try {
			showSettings();
		} catch (IOException e) {
			System.out.println("ERROR SHOWING SETTINGS: " + e);
			e.printStackTrace();
		}});
		menuList.add(openSettings, 0, 3);
		GridPane.setHalignment(openSettings, HPos.CENTER);
		
		Button exitGame = new Button("Exit");
		exitGame.setOnAction((event) -> {System.exit(0);}); // close game
		menuList.add(exitGame, 0, 4);
		GridPane.setHalignment(exitGame, HPos.CENTER);
		
		menuStage.setOnCloseRequest((event) -> {System.exit(0);}); // terminates program if user closes start menu
		
		// Set button width to be consistent
		int buttonWidth = 80;
		startGame.setPrefWidth(buttonWidth);
		loadGame.setPrefWidth(buttonWidth);
		openSettings.setPrefWidth(buttonWidth);
		exitGame.setPrefWidth(buttonWidth);
		
		Scene menuScene = new Scene(menuWindow, 600, 400);
		menuStage.setScene(menuScene);
		menuStage.showAndWait(); 
	}
	
	/**
	 * Launches the settings window.
	 * 
	 * This method launches the settings window so that the user
	 * can configure the game settings. I know I'm breaking the 4th
	 * wall a bit but I want to apologize for the really long method as
	 * I couldn't find a way to shorten it without just breaking it
	 * up into showSettingsPart2(). It sets the result of each setting
	 * to a class variable that can be used throughout the class for
	 * reference.
	 * 
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	public void showSettings() throws IOException {
		// Get default settings from file
		SettingsSave settingsHandler = new SettingsSave(new File("src/save_files/settings.json"));
		HashMap<String, String> settings = settingsHandler.fileToMap();
		
		Stage settingsStage = new Stage();
		settingsStage.setTitle("Settings");
		BorderPane settingsWindow = new BorderPane();
		GridPane settingsList = new GridPane();
		settingsList.setHgap(10);
		settingsList.setVgap(10);
		settingsWindow.setCenter(settingsList);
		
		settingsList.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Label title = new Label("Settings");
		title.setStyle("-fx-font-size: 20px;");
		settingsList.add(title, 0, 0);
		
		Label playerSetting = new Label("Enter the amount of human players: ");
		settingsList.add(playerSetting, 0, 1);
		Spinner playerInput = new Spinner<Integer>(1, 8, Integer.valueOf(settings.get("humanPlayerCount")), 1); // spinner allows user to cycle through different amounts of players
		settingsList.add(playerInput, 1, 1);
		
		Label aiSetting = new Label("Enter the amount of computer players: ");
		settingsList.add(aiSetting, 0, 2);
		Spinner aiInput = new Spinner<Integer>(0, 8, Integer.valueOf(settings.get("computerPlayerCount")), 1); // spinner allows user to cycle through different amounts of players
		settingsList.add(aiInput, 1, 2);
		
		Label goSetting = new Label("Enter the amount of money to be collected when a player passes go: ");
		settingsList.add(goSetting, 0, 3);
		Spinner goInput = new Spinner<Integer>(0, Integer.MAX_VALUE, Integer.valueOf(settings.get("goAmount")), 1); // spinner allows user to cycle through different amounts of money
		goInput.setEditable(true);
		settingsList.add(goInput, 1, 3);
		
		Label priceSetting = new Label("Enter the percentage increase/decrease in property prices: ");
		settingsList.add(priceSetting, 0, 4);
		Spinner priceInput = new Spinner<Integer>(0, Integer.MAX_VALUE, Integer.valueOf(settings.get("propertyScale")), 1); // spinner allows user to cycle through different percentages
		priceInput.setEditable(true);
		settingsList.add(priceInput, 1, 4);
		
		Label moneySetting = new Label("Enter the amount of money each player will start with: ");
		settingsList.add(moneySetting, 0, 5);
		Spinner moneyInput = new Spinner<Integer>(0, Integer.MAX_VALUE, Integer.valueOf(settings.get("startingBalance")), 1); // spinner allows user to cycle through different amounts of money
		moneyInput.setEditable(true);
		settingsList.add(moneyInput, 1, 5);
		
		Label fpSetting = new Label("Check the box to enable free parking rewards: ");
		settingsList.add(fpSetting, 0, 6);
		CheckBox fpInput = new CheckBox(); // checkbox allows user to enable or disable free parking rewards
		fpInput.setSelected(Boolean.parseBoolean(settings.get("freeParking")));
		settingsList.add(fpInput, 1, 6);
		
		Label skipPurchaseSetting = new Label("Check the box to enable skipping property purchases: ");
		settingsList.add(skipPurchaseSetting, 0, 7);
		CheckBox skipPurchaseInput = new CheckBox(); // checkbox allows user to enable or disable skipping property purchases
		skipPurchaseInput.setSelected(Boolean.parseBoolean(settings.get("skipPurchases")));
		settingsList.add(skipPurchaseInput, 1, 7);
		
		Label tradingSetting = new Label("Check the box to enable trading: ");
		settingsList.add(tradingSetting, 0, 8);
		CheckBox tradingInput = new CheckBox(); // checkbox allows user to enable or disable trading
		tradingInput.setSelected(Boolean.parseBoolean(settings.get("trading")));
		settingsList.add(tradingInput, 1, 8);
		
		Label themeSetting = new Label("Select theme: ");
		settingsList.add(themeSetting, 0, 9);
		ComboBox<String> themeInput = new ComboBox<String>(); // dropdown allows user to select from different themes
		themeInput.getItems().add("Atlantic City");
		themeInput.getItems().add("Marvel Comics");
		themeInput.getItems().add("DC Comics");
		themeInput.setValue(settings.get("theme").replace("\"", ""));
		settingsList.add(themeInput, 1, 9);
		
		// Define group of buttons for bottom of window
		HBox buttonRow = new HBox(10);
		buttonRow.setAlignment(Pos.CENTER);
		
		Button confirmSettings = new Button("Confirm");
		confirmSettings.setOnAction((event) -> {
			int totalPlayers = (int) playerInput.getValue() + (int) aiInput.getValue();
			if (totalPlayers > 8 || totalPlayers < 2) { // shows error message if there are too many playres
				showPlayerError();
				return;
			}
			// Reads all data into a HashMap to write to the settings file
			settings.clear();
			settings.put("humanPlayerCount", String.valueOf(playerInput.getValue()));
			settings.put("computerPlayerCount", String.valueOf(aiInput.getValue()));
			settings.put("goAmount", String.valueOf(goInput.getValue()));
			settings.put("startingBalance", String.valueOf(moneyInput.getValue()));
			settings.put("propertyScale", String.valueOf(priceInput.getValue()));
			settings.put("freeParking", String.valueOf(fpInput.isSelected()));
			settings.put("skipPurchases", String.valueOf(skipPurchaseInput.isSelected()));
			settings.put("trading", String.valueOf(tradingInput.isSelected()));
			settings.put("theme", "\"" + String.valueOf(themeInput.getValue()) + "\"");
			
			try {
				settingsHandler.write(settings);
			} catch (IOException e) {
				System.out.println("ERROR WRITING TO SETTINGS: " + e);
				e.printStackTrace();
			}
			settingsStage.close(); // closes window 
		});
		buttonRow.getChildren().add(confirmSettings);
		
		Button resetSettings = new Button("Reset");
		resetSettings.setOnAction((event) -> {
			File defaultSettings = new File("src/save_files/default_settings.json");
			try {
				settingsHandler.copyContents(defaultSettings);
				settingsStage.close();
			} catch (IOException e) {
				System.out.println("ERROR COPYING SETTINGS: " + e);
				e.printStackTrace();
			}
		});
		buttonRow.getChildren().add(resetSettings);
		
		Button cancelSettings = new Button("Cancel");
		cancelSettings.setOnAction((event) -> {
			settingsStage.close(); // closes window 
		});
		buttonRow.getChildren().add(cancelSettings);
		
		settingsList.add(buttonRow, 0, 10);
		Scene settingsScene = new Scene(settingsWindow, 600, 400);
		settingsStage.setScene(settingsScene);
		settingsStage.showAndWait(); 
	}

	/**
	 * Indicates too many players.
	 * 
	 * This method is used to show the user an error indicating
	 * that they tried to start a game with too many players. The
	 * total number of human and computer players should sum to 8,
	 * so this message is shown if the submitted total exceeds 8.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showPlayerError() {
		Alert msg = new Alert(Alert.AlertType.ERROR);
		msg.setTitle("Error Message"); 
		msg.setHeaderText("Improper number of players");
		msg.setContentText("The number of total players must be between 2 and 8 (inclusive).");
		msg.showAndWait(); 
	}
	
	/**
	 * Starts the game loop.
	 * 
	 * This method generates the Player objects and places the
	 * players on the board. It adds the buttons that the player
	 * will use to complete game actions like rolling the dice
	 * and ending their turn.
	 * 
	 * Author: Jesse Catterton
	 */
	public void startGame() {
		players = controller.getPlayers();
		
		if (!controller.isLoadedFromSave()) {
			controller.setPlayerBalance(players); // sets starting balance
			controller.setCurPlayerIndex(0); // sets player index to first slot
		}
		
		addPlayerProfiles(); // add player bar on side
		((GridPane) playerProfiles.get(controller.getCurPlayerIndex())).setStyle("-fx-background-color: #949494; -fx-border-color: black; -fx-border-width: 2px;"); // selects first player's profile
		updatePlayerBalances();
		for (int i = 0; i < humanPlayerCount + computerPlayerCount; i++) { // loops to move each player to go or to starting location from save
			if (players[i].getLocation() == 10 && players[i].checkJailed()) { // places player in jail if they were in jail on loaded game
				CornerTile jailTile = (CornerTile) tiles[10];
				tiles[30].moveFrom(players[i]); 
				jailTile.sendToJail(players[i]);
			} else tiles[players[i].getLocation()].moveTo(players[i]);
		}
		
		cardDeck = new CommunityChest(theme);
		addLogos();

		rollDice = new Button("Roll dice");
		rollDice.setOnAction((event) -> {movePlayer();}); 
		rollDice.setPrefWidth(65);
		board.add(rollDice, 2, 7);
		
		buyProperty = new Button("Buy");
		buyProperty.setOnAction((event) -> {showBuyWindow();});
		buyProperty.setPrefWidth(65);
		board.add(buyProperty, 3, 7);
		buyProperty.setDisable(true); // buy property is disabled until player rolls and lands on property

		proposeTrade = new Button("Trade");
		proposeTrade.setOnAction((event) -> {showTradeWindow();});
		proposeTrade.setPrefWidth(65);
		board.add(proposeTrade, 4, 7);
		proposeTrade.setDisable(true); // trade button is disabled until player rolls the dice
		
		build = new Button("Build");
		build.setOnAction((event) -> {showBuildWindow();});
		build.setPrefWidth(65);
		board.add(build, 5, 7);
		build.setDisable(true); // build is disabled until player rolls the dice
		
		sell = new Button("Sell");
		sell.setOnAction((event) -> {showSellWindow();});
		sell.setPrefWidth(65);
		board.add(sell, 6, 7);
		sell.setDisable(true); // sell is disabled until player rolls the dice
		
		endTurn = new Button("End turn");
		endTurn.setOnAction((event) -> {endTurn();});
		endTurn.setPrefWidth(65);
		board.add(endTurn, 7, 7);
		endTurn.setDisable(true); // end turn is disabled until player rolls the dice
		
		save = new Button("Save");
		save.setOnAction((event) -> {
			if (controller.getSaveSlot() == 0) {
				// Select a new save slot
				showSaveWindow();
			} else {
				// Overwrite existing selected save slot
				String fileName = "src/save_files/game_save_" + controller.getSaveSlot() + ".json";
				File saveFile = new File(fileName);
				
				GameSave saveHandler = new GameSave(saveFile, controller);
				try {
					saveHandler.write();
				} catch (IOException e) {
					System.out.println("ERROR WRITING TO SAVE HANDLER: " + e);
					e.printStackTrace();
				}
				
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Game Saved");
				msg.setHeaderText(null);
				msg.setContentText("Game saved to slot " + controller.getSaveSlot() + ".");
				msg.showAndWait();
			}
		});
		save.setPrefWidth(65);
		board.add(save, 8, 7);
	}

	/**
	 * Moves player when dice are rolled.
	 * 
	 * This method is called when the player presses the "roll
	 * dice" button. It moves the player off of their current
	 * location, gets a random dice roll, and moves the player
	 * to the new spot. It disables the roll dice button so the
	 * player cannot roll again and enables the end turn button
	 * so the player can indicate when they are done with their turn.
	 * 
	 * Author: Jesse Catterton
	 * Author: Tyler Ohme
	 */
	public void movePlayer() {
		Player currentPlayer = players[controller.getCurPlayerIndex()];
		save.setDisable(true); // disable button to prevent abusing save/load to roll multiple times
		
		boolean disableEndTurn = false;
		if (currentPlayer.checkJailed()) { // completes a jail turn if player is in jail
			completeJailedTurn(currentPlayer);
			rollDice.setDisable(true);
			if (controller.getTrading()) proposeTrade.setDisable(false); // only enables trading button if trading is allowed
			build.setDisable(false);
			sell.setDisable(false);
			endTurn.setDisable(disableEndTurn);
		} else { // completes normal turn
			int oldLocation = currentPlayer.getLocation();
			int diceRoll[] = currentPlayer.diceRoll(); // display dice
			AudioClip rolling = new AudioClip(getClass().getResource("/diceRoll.wav").toExternalForm());
			rolling.play();
			dieView1.setImage(dieFaces[diceRoll[0] - 1]);
			dieView2.setImage(dieFaces[diceRoll[1] - 1]);
			animatePlayer(currentPlayer, diceRoll[0] + diceRoll[1], oldLocation);	
		}
	}
	
	/**
	 * Animates player moving across spaces.
	 * 
	 * This method animates a player's movement when they roll
	 * the dice. It steps on each space along the way for half a
	 * second until it reaches its destination. Upon finishing, it
	 * updates the player's location and interprets the space.
	 * 
	 * @param currPlayer is the player currently moving
	 * @param roll is the amount that they rolled on the dice
	 * @param oldLocation is the location the player is starting before the roll
	 * Author: Jesse Catterton
	 * Author: Sophia Harms
	 */
	public void animatePlayer(Player currPlayer, int roll, int oldLocation) {
		rollDice.setDisable(true);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		int currLocation = oldLocation;
		
		int seconds = 250;
		for (int i = currLocation; i < (currLocation + roll); i++) { // loops to step for each roll
			int step = i;
			KeyFrame keyframe = new KeyFrame(Duration.millis(seconds), (event) -> {
				if (step > 39) tiles[step - 40].moveFrom(currPlayer);
				else tiles[step].moveFrom(currPlayer);
				
				if (step + 1 > 39) tiles[step + 1 - 40].moveTo(currPlayer);
				else tiles[step + 1].moveTo(currPlayer);
			});
			timeline.getKeyFrames().add(keyframe);
			seconds += 250;
		}
		timeline.setOnFinished((event) -> Platform.runLater(() -> { // credit to ta jacob for this line
			
			int newLocation = currPlayer.getLocation();
			
			if (newLocation < oldLocation) { // checks if player passes go
				currPlayer.adjustBalance(controller.getGoAmount());
				updatePlayerBalances();
				AudioClip passGo = new AudioClip(getClass().getResource("/go.wav").toExternalForm());
				passGo.play();
			}
			
			boolean disableEndTurn = interpretSpace(currPlayer);
		
			if (controller.getTrading()) proposeTrade.setDisable(false); // only enables trading button if trading is allowed
			build.setDisable(false);
			sell.setDisable(false);
			endTurn.setDisable(disableEndTurn);
			
			if (currPlayer instanceof ComputerPlayer) {
			        endTurn();
			}
		}));
		timeline.play();
	}
	
	/**
	 * Completes a player's turn from jail.
	 * 
	 * This method is called when a player who is in jail needs to
	 * take their turn. They are broken out of jail if they roll doubles,
	 * if they have a get out of jail free card, or if they have already
	 * tried to roll doubles three times (they pay $50). 
	 * 
	 * @param currentPlayer is the player in jail 
	 * Author: Jesse Catterton
	 */
	public void completeJailedTurn(Player currentPlayer) {
		int diceRoll[] = currentPlayer.diceRoll(); // rolls but does not move
		AudioClip rolling = new AudioClip(getClass().getResource("/diceRoll.wav").toExternalForm());
		rolling.play();
		dieView1.setImage(dieFaces[diceRoll[0] - 1]);
		dieView2.setImage(dieFaces[diceRoll[1] - 1]);
		
		if (currentPlayer.getRollWasDoubles()) { // breaks player out if they rolled doubles
			((CornerTile) tiles[10]).removeFromJail(currentPlayer);
			tiles[10].moveTo(currentPlayer);
			currentPlayer.setLocation(10);
			currentPlayer.setJailed(false);
		} else if (currentPlayer.getHeldCards().size() > 0) { // breaks player out if they have a get out of jail free card
			((CornerTile) tiles[10]).removeFromJail(currentPlayer);
			tiles[10].moveTo(currentPlayer);
			currentPlayer.setLocation(10);
			currentPlayer.getHeldCards().remove(0);
			currentPlayer.setJailed(false);
		} else if (currentPlayer.getJailRollCount() == 3) { // breaks player out and makes them pay $50 if they've tried to roll doubles 3 times
			((CornerTile) tiles[10]).removeFromJail(currentPlayer);
			tiles[10].moveTo(currentPlayer);
			currentPlayer.setLocation(10);
			currentPlayer.adjustBalance(-50);
			currentPlayer.setJailed(false);
			updatePlayerBalances();
		} else { // increments roll count if they didn't break out
			currentPlayer.setJailRollCount(currentPlayer.getJailRollCount() + 1);
		}
	}

	/**
	 * Determines what space a player landed on.
	 * 
	 * This method determines what type of space the player
	 * landed on and takes action depending on that space. It
	 * makes them buy a property or pay rent if they land on
	 * a property, draw a card if they land on a community chest
	 * space, collect rewards on free parking if its enabled, go
	 * to jail if they land on go to jail, or pay taxes if they
	 * landed on a tax space.
	 * 
	 * @param currentPlayer is the player who landed on the space
	 * @return a boolean indicating whether end turn needs to be disabled
	 * Author: Jesse Catterton
	 */
	public boolean interpretSpace(Player currentPlayer) {
		Tile currTile = tiles[currentPlayer.getLocation()];
		Space space = currTile.getSpace();
		
		if (space instanceof Property) { // handles landing on a property space
			return handlePropertySpace(space, currentPlayer);
		} else if (space instanceof CardDrawSpace) {  // handles landing on a community chest space
			drawCard(currentPlayer);
		} else if (space instanceof FreeParkingSpace) { // collects free parking if its enabled
			if (controller.getFreeParking()) {
				FreeParkingSpace freeParking = (FreeParkingSpace) space;
				int reward = freeParking.collectRewards();
				currentPlayer.adjustBalance(reward);
			}
			AudioClip carHonk = new AudioClip(getClass().getResource("/freeParking.wav").toExternalForm());
			carHonk.play();
		} else if (space instanceof GoToJailSpace) { // sends player to jail
			CornerTile jailTile = (CornerTile) tiles[10];
			tiles[30].moveFrom(currentPlayer); 
			jailTile.sendToJail(currentPlayer);
			currentPlayer.setLocation(10);
			currentPlayer.setJailed(true);
			currentPlayer.setJailRollCount(0);
			AudioClip jailDoor = new AudioClip(getClass().getResource("/jail.wav").toExternalForm());
			jailDoor.play();
		} else if (space instanceof TaxSpace) { // pays taxes
			int taxAmount = ((TaxSpace) space).getTaxAmount();
			currentPlayer.adjustBalance(-taxAmount);
			if (controller.getFreeParking()) { // puts tax money in free parking if its enabled
				FreeParkingSpace freeParking = (FreeParkingSpace) tiles[20].getSpace();
				freeParking.addRewards(taxAmount);
			}
		}
		updatePlayerBalances();
		return false;
	}
	
	/**
	 * Handles a player landing on a property space.
	 * 
	 * This method interprets and performs whatever action is needed
	 * when a player lands on a property space. If the player owns the
	 * property, it does nothing. If the property is owned, it enables
	 * the buy button to allow the player to buy the property. If the
	 * property is owned, it charges the player rent.
	 * 
	 * @param space is the space the player landed
	 * @param currentPlayer is the player who landed on the space
	 * @return a boolean indicating if the end turn button should be disabled or enabled
	 * Author: Tyler Ohme
	 */
	public boolean handlePropertySpace(Space space, Player currentPlayer) {
		Player owner = ((Property) space).getOwner();
		if (space instanceof Railroad) {
			AudioClip railroad = new AudioClip(getClass().getResource("/railroad.wav").toExternalForm());
			railroad.play();
		}
		if (owner == currentPlayer) { // do nothing if player owns the property
			return false; 
		} else if (owner == null) { // allow player to purchase property
			if (currentPlayer instanceof ComputerPlayer && ((ComputerPlayer) currentPlayer).makePropertyDecision(((Property) space).getBuyValue())) {
				currentPlayer.buyProperty((Property) space); // computer buys property if it can afford it
			}
			buyProperty.setDisable(false);
			if (!controller.getSkipPurchases() && currentPlayer.getBalance() >= ((Property) space).getBuyValue()) return true; // returns true if endTurn should be disabled
		} else { // make player pay rent to ownder
			int diceNum = currentPlayer.getLastRoll();
			int rentPrice = ((Property) space).getRent(diceNum);
			
			// Have current player pay rent to the owner as long as the owner isn't in jail
			if (!owner.checkJailed()) {
				currentPlayer.adjustBalance(-rentPrice);
				owner.adjustBalance(rentPrice);
			}
		}
		updatePlayerBalances();
		return false;
	}
	
	/**
	 * Completes a community chest card action.
	 * 
	 * This method is called when a player lands on a community
	 * chest space. They draw a card, the effect is applied to
	 * their balance or location, and an Alert object is displayed
	 * to show the message of the card.
	 * 
	 * @param currentPlayer is the player who drew the card
	 * Author: Jesse Catterton
	 */
	public void drawCard(Player currentPlayer) {
		Card card = cardDeck.drawCard(currentPlayer, humanPlayerCount + computerPlayerCount, players);
		updatePlayerBalances();
		
		if (card.getMessage().equals("Advance to Go")) { // moves player to go
			tiles[currentPlayer.getLocation()].moveFrom(currentPlayer);
			tiles[0].moveTo(currentPlayer);
			currentPlayer.setLocation(0);
			currentPlayer.adjustBalance(200);
		} else if (card.getMessage().equals("Go to jail. Do not pass Go, Do not collect $200.")) { // moves player to jail
			tiles[currentPlayer.getLocation()].moveFrom(currentPlayer);
			((CornerTile) tiles[10]).sendToJail(currentPlayer);
			currentPlayer.setLocation(10);
			currentPlayer.setJailed(true);
			currentPlayer.setJailRollCount(0);
			AudioClip jailDoor = new AudioClip(getClass().getResource("/jail.wav").toExternalForm());
			jailDoor.play();
		} else if (card.getEffect() < 0 && controller.getFreeParking()) { // puts money in free parking if it is enabled
			FreeParkingSpace freeParking = (FreeParkingSpace) tiles[20].getSpace();
			freeParking.addRewards(-(card.getEffect()));
		}
		updatePlayerBalances();
		
		AudioClip chaChing = new AudioClip(getClass().getResource("/community_chest.wav").toExternalForm());
		chaChing.play();
		
		Alert msg = new Alert(Alert.AlertType.INFORMATION);
		if(theme.equals("Atlantic City")) {
			msg.setTitle("Community Chest"); 
			msg.setHeaderText("Community Chest Card");
		} else if(theme.equals("Marvel Comics")) {
			msg.setTitle("S.H.I.E.L.D Chest"); 
			msg.setHeaderText("S.H.I.E.L.D Card");
		} else {
			msg.setTitle("Wayne Tech Chest"); 
			msg.setHeaderText("Wayne Tech Card");
		}
		msg.setContentText(card.getMessage());
		msg.showAndWait(); 
	}
	

	/**
	 * Performs a computer player's turn.
	 * 
	 * This method calls the methods necessary to perform a
	 * computer player's turn automatically since the computer
	 * does not possess the ability to press buttons.
	 * 
	 * Author: Jesse Catterton
	 */
	public void computerPlayerTurn() {
		movePlayer();
	}
	
	/**
	 * Ends a player's turn.
	 * 
	 * This method is called when a player presses the "end turn"
	 * button. It checks if the current player is bankrupt and needs
	 * to get out of it by selling properties. Then it checks to see
	 * if the player has won. It increments the index that keeps track 
	 * of the current player to move it to the next player, reenables
	 * necessary buttons for next player, and disables the end turn button 
	 * until the new player has rolled. It also calls updatePlayerBalances()
	 * to reflect any balance changes made during this turn.
	 * 
	 * Author: Jesse Catterton
	 */
	public void endTurn() {	
		save.setDisable(false); // disable button to prevent abusing save/load to roll multiple times
		
		if (players[controller.getCurPlayerIndex()].checkBankrupt() && controller.canEscapeBankrupcy(players[controller.getCurPlayerIndex()])) { // checks if player is bankrupt and can get out
			if (players[controller.getCurPlayerIndex()] instanceof ComputerPlayer) { // sells properties automatically for computer
				while (!players[controller.getCurPlayerIndex()].getProperties().isEmpty()) { // loops to sell as many properties as necessary to get out of bankrupcy
					    Property property = players[controller.getCurPlayerIndex()].getProperties().get(0);
					    sellProperty(property, sellList, false);
					    if (!players[controller.getCurPlayerIndex()].checkBankrupt()) break; // breaks once computer gets out of bankrupcy
				}
			} else { // shows message for player to manually sell properties
				Alert msg = new Alert(Alert.AlertType.ERROR);
			     msg.setHeaderText("Bankrupt Player");
			     msg.setContentText("You may not end your turn with a negative balance. Sell properties to continue.");
			     msg.showAndWait();
			     return;
			}
		}
		
		Player winner = controller.checkGameOver();
		if (winner != null) { // checks if winner is present
			Alert msg = new Alert(Alert.AlertType.INFORMATION);
			msg.setTitle("Game Over"); 
			msg.setHeaderText("Game Over");
			msg.setContentText("Player " + (winner.getPlayerId() + 1) + " wins!");
			msg.showAndWait(); 
			System.exit(0); // ends program once they exit out of win message
		}
		
		((GridPane) playerProfiles.get(controller.getCurPlayerIndex())).setStyle("-fx-background-color: #00000000; -fx-border-color: black; -fx-border-width: 2px;");
		if (!players[controller.getCurPlayerIndex()].getRollWasDoubles() || players[controller.getCurPlayerIndex()].checkBankrupt()) { // doesn't move to next player if roll was doubles
			controller.nextPlayerIndex();
			if (controller.getCurPlayerIndex() >= players.length) controller.setCurPlayerIndex(0); // loops back to first player
			while (players[controller.getCurPlayerIndex()].checkBankrupt()) {
				controller.nextPlayerIndex();
				if (controller.getCurPlayerIndex() >= players.length) controller.setCurPlayerIndex(0); // loops back to first player
			}
		}
		((GridPane) playerProfiles.get(controller.getCurPlayerIndex())).setStyle("-fx-background-color: #949494; -fx-border-color: black; -fx-border-width: 2px;");
		rollDice.setDisable(false);
		build.setDisable(true);
		proposeTrade.setDisable(true);
		endTurn.setDisable(true);
		buyProperty.setDisable(true);
		sell.setDisable(true);
		updatePlayerBalances();
		if (players[controller.getCurPlayerIndex()] instanceof ComputerPlayer)  {
			rollDice.setDisable(true);
			computerPlayerTurn();
		}
	}
	
	/**
	 * Brings up a buy window to purchase a property.
	 * 
	 * This method allows a player to purchase a property. It
	 * displays the property name and price. A buy button is 
	 * added, it is disabled if the player doesn't have enough
	 * money to buy the property. It purchses the property if
	 * they do have enough money and clicks the button.
	 * 
	 * Author: Tyler Ohme
	 */
	public void showBuyWindow() {
		Player currentPlayer = players[controller.getCurPlayerIndex()];
		Space currentSpace = tiles[currentPlayer.getLocation()].getSpace();
		
		// Don't do anything if player already owns space
		if (((Property) currentSpace).getOwner() == currentPlayer) return;
		
		Stage buyStage = new Stage();
		buyStage.setTitle("Buy Window");
		BorderPane buyWindow = new BorderPane();
		GridPane buyList = new GridPane();
		buyList.setPadding(new Insets(10));
		buyList.setHgap(10);
		buyList.setVgap(10);
		buyWindow.setCenter(buyList);
		
		buyList.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Label title = new Label("Buy current property");
		title.setStyle("-fx-font-size: 20px;");
		buyList.add(title,  0, 0);
		
		Label information = new Label("Shown below is the details of the property you are attempting to buy.");
		information.setWrapText(true);
		buyList.add(information, 0, 1);
		
		Label propertyInfo = new Label(currentSpace.getName() + " - $" + ((Property) currentSpace).getBuyValue());
		buyList.add(propertyInfo, 0, 2);
		
		Button buy = new Button("Buy");
		buy.setDisable(currentPlayer.getBalance() < ((Property) currentSpace).getBuyValue());
		buy.setOnAction((event) -> {
			currentPlayer.buyProperty((Property) currentSpace);
			updatePlayerBalances();
			buyProperty.setDisable(true);
			endTurn.setDisable(false);
			buyStage.close();
		});
		buyList.add(buy, 0, 3);
		
		Button closeWindow = new Button("Cancel");
		closeWindow.setOnAction((event) -> {
			buyStage.close();
			});
		buyList.add(closeWindow, 1, 3);
		
		Scene buyScene = new Scene(buyWindow, 600, 400);
		buyStage.setScene(buyScene);
		buyStage.showAndWait();
	}
	
	/**
	 * Displays the trade window allowing for the current player to propose a
	 * trade with another player.
	 * 
	 * The player selects the target player from a dropdown, chooses on of that
	 * Player's properties to trade request, and enters a monetary offer. The
	 * offer is then sent via the offerTrade() method which handles both computer
	 * and human responses.
	 * 
	 * Author: Tyler Ohme
	 */
	public void showTradeWindow() {
		Player fromPlayer = players[controller.getCurPlayerIndex()];
		
		// Set up window
		Stage tradeStage = new Stage();
		tradeStage.setTitle("Trade Window");
		BorderPane tradeWindow = new BorderPane();
		GridPane tradeList = new GridPane();
		tradeList.setPadding(new Insets(10));
		tradeList.setHgap(10);
		tradeList.setVgap(10);
		tradeWindow.setCenter(tradeList);
		
		tradeList.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Label title = new Label("Trading");
		
		title.setStyle("-fx-font-size: 20px;");
		tradeList.add(title, 0, 0);
		
		
		// Let player pick what other player to trade with
		Label playerPrompt = new Label("Choose player to trade with:");
		tradeList.add(playerPrompt, 0, 1);
		
		ComboBox<String> playerOptions = new ComboBox<String>();
		for (int i = 0; i < controller.getPlayerCount(); i++) {
			if (i == controller.getCurPlayerIndex()) continue; // skip current player
			
			playerOptions.getItems().add("Player " + (i+1));
		}
		tradeList.add(playerOptions, 1, 1);
		
		// List properties to request
		Label propertyPrompt = new Label("Choose property to request:");
		tradeList.add(propertyPrompt, 0, 2);
		
		ComboBox<String> propertyOptions = new ComboBox<>();
		propertyOptions.setDisable(true);
		tradeList.add(propertyOptions, 1, 2);
		
		// Enable and populate property options once a player is selected
		playerOptions.setOnAction((event) -> {
			propertyOptions.getItems().clear();
			propertyOptions.setDisable(false);
			
			String selected = playerOptions.getValue();
			if (selected == null) return; // failsafe if there wasn't actually a player selected
			int index = Integer.parseInt(selected.split(" ")[1]) - 1;
			ArrayList<Property> properties = players[index].getProperties();
			
			if (properties.size() <= 0) {
				// Handle player has no properties
				propertyOptions.getItems().add("(No properties available)");
				propertyOptions.setDisable(true);
			} else {
				// Add all properties to list
				for (Property property : properties) {
					propertyOptions.getItems().add(property.getName());
				}
			}
		});
		
		
		Label moneyPrompt = new Label("Enter money offer: ");
		tradeList.add(moneyPrompt, 0, 3);
		
		Spinner<Integer> moneyOffer = new Spinner<>(0, fromPlayer.getBalance(), 0, 10);
		moneyOffer.setEditable(true);
		tradeList.add(moneyOffer, 1, 3);
		
		
		HBox buttonRow = new HBox();
		buttonRow.setAlignment(Pos.CENTER);
		tradeList.add(buttonRow, 0, 4);
		
		Button sendButton = new Button("Send Offer");
		sendButton.setOnAction(event ->  {
			String playerSelected = playerOptions.getValue();
			int playerIndex = Integer.parseInt(playerSelected.split(" ")[1]) - 1;
			Player toPlayer = players[playerIndex];
			
			ArrayList<Property> properties = toPlayer.getProperties();
			int propertyIndex = propertyOptions.getSelectionModel().getSelectedIndex();
			Property targetProperty = properties.get(propertyIndex);
			
			int offer = moneyOffer.getValue();
			
			offerTrade(fromPlayer, toPlayer, targetProperty, offer, tradeStage);
		});
		buttonRow.getChildren().add(sendButton);
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(event -> {
			tradeStage.close();
		});
		buttonRow.getChildren().add(closeButton);
		
		Scene tradeScene = new Scene(tradeWindow, 500, 350);
		tradeStage.setScene(tradeScene);
		tradeStage.showAndWait();
	}
	
	/**
	 * Handles the user and computer player queries from trade requests. Will
	 * evaluate the request and transfer money and properties if successful.
	 * 
	 * @param playerFrom the player who initiated the trade
	 * @param playerTo the player who was selected by the first player
	 * @param property the property being traded for
	 * @param offer the money offered for the property
	 * @param tradeStage the trade window, passed in to close here
	 * Author: Tyler Ohme
	 */
	private void offerTrade(Player playerFrom, Player playerTo, Property property, int offer, Stage tradeStage) {
		if (playerTo instanceof ComputerPlayer) {
			if (((ComputerPlayer) playerTo).makeTradeDecision(property, offer)) {
				playerTo.adjustBalance(offer);
				playerFrom.adjustBalance(-offer);
				
				playerFrom.addProperty(property);
				playerTo.removeProperty(property);
				updatePlayerBalances();
				proposeTrade.setDisable(true);
				
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Trade Successful");
				msg.setHeaderText(null);
				msg.setContentText("The trade was successful!");
				msg.showAndWait();
			} else {
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Trade Declined");
				msg.setHeaderText(null);
				msg.setContentText("The trade was declined.");
				msg.showAndWait();
			}
			tradeStage.close();
			return;
		} else {
			if (property instanceof Street) {
				// For players with houses built, block the purchase until the receiving player sells
				Street street = (Street) property;
				if (street.getNumHouses() > 0 || street.getNumHotels() > 0) {
					Alert msg = new Alert(Alert.AlertType.INFORMATION);
					msg.setTitle("Trading Error");
					msg.setHeaderText(null);
					msg.setContentText("Street " + property.getName() + " has building(s) present. They must be sold first before trading this property.");
					msg.showAndWait();
					return;
				}
			}
			// Create simple prompt window
			Stage promptStage = new Stage();
			promptStage.setTitle("Trade Offer");
			BorderPane promptWindow = new BorderPane();
			
			GridPane details = new GridPane();
			details.setPadding(new Insets(20));
		    details.setHgap(10);
		    details.setVgap(10);
		    promptWindow.setCenter(details);

			Label title = new Label("Incoming Trade Offer");
			title.setStyle("-fx-font-size: 20px;");
			details.add(title, 0, 0);
			
			Label info = new Label("Player " + (playerTo.getPlayerId() + 1) + " has a trade offer from Player " + (playerFrom.getPlayerId() + 1) + ".");
			details.add(info, 0, 1);
			
			Label propertyPrompt = new Label("Property requested: ");
			details.add(propertyPrompt, 0, 2);
			Label propertyInfo = new Label(property.getName());
			details.add(propertyInfo, 1, 2);
			
			Label offerPrompt = new Label("Currency offered: ");
			details.add(offerPrompt, 0, 3);
			Label offerInfo = new Label("$" + offer);
			details.add(offerInfo, 1, 3);
			
			Button accept = new Button("Accept");
			accept.setOnAction((event) -> {
				playerTo.adjustBalance(offer);
				playerFrom.adjustBalance(-offer);
				
				playerFrom.addProperty(property);
				playerTo.removeProperty(property);
				updatePlayerBalances();
				proposeTrade.setDisable(true);
				
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Trade Successful");
				msg.setHeaderText(null);
				msg.setContentText("The trade was successful!");
				msg.showAndWait();
				
				promptStage.close();
				tradeStage.close();
			});
			details.add(accept, 0, 4);
			
			Button decline = new Button("Decline");
			decline.setOnAction((event) -> {
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Trade Declined");
				msg.setHeaderText(null);
				msg.setContentText("The trade was declined.");
				msg.showAndWait();
				
				promptStage.close();
				tradeStage.close();
			});
			details.add(decline, 1, 4);
			
			Scene promptScene = new Scene(promptWindow, 450, 200);
			promptStage.setScene(promptScene);
			promptStage.showAndWait();
		}
	}
	
	/**
	 * Shows building options to user.
	 * 
	 * This method is used to show a window that allows the player
	 * to build houses and hotels on their properties. It shows when
	 * the player hits the build button. The window shows all of the
	 * player's properties that are a part of a complete set. Each
	 * proeprty has a button that the player can press to add a house
	 * to each property in that property's complete set.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showBuildWindow() {
		Stage buildStage = new Stage();
		buildStage.setTitle("Build Window");
		BorderPane buildWindow = new BorderPane();
		GridPane buildList = new GridPane();
		buildList.setPadding(new Insets(10));
		buildList.setHgap(10);
		buildList.setVgap(10);
		buildWindow.setCenter(buildList);
		
		Label title = new Label("Build Houses & Hotels");
		title.setStyle("-fx-font-size: 20px;");
		buildList.add(title, 0, 0);
		
		Label information = new Label("Shown below are all properties you own that are part of a complete set. "
				+ "Clicking build on a propety will add one house to all properties in that set. "
				+ "If the complete set contains 4 houses, it will build a hotel.");
		information.setWrapText(true);
		buildList.add(information, 0, 1);
		
		Player currentPlayer = players[controller.getCurPlayerIndex()];
		ArrayList<Property> properties = currentPlayer.getProperties();
		int row = 2;
		for (int i = 0; i < properties.size(); i++) { // goes through each property the player has to check for complete sets
			Property property = properties.get(i);
			if (property instanceof Street) { // only streets can have houses built on them
				ArrayList<Street> completeSet = currentPlayer.checkForCompleteSet((Street) property, tiles, themeColors);
				if (completeSet != null) { // if the property is a part of a complete set, it adds it to the list of ones to build on
					Label propertyName = new Label(property.getName());
					buildList.add(propertyName, 0, row);
					Button build = new Button("Build");
					build.setMinWidth(100);
					build.setPrefWidth(100);
					build.setMaxWidth(100);
					build.setOnAction((event) -> {attemptToBuild(completeSet);});
					buildList.add(build, 1, row);
					row++;
				}
			}
		}
		
		Button closeWindow = new Button("Close");
		closeWindow.setOnAction((event) -> {buildStage.close();});
		buildList.add(closeWindow, 0, row + 1);
		
		Scene settingsScene = new Scene(buildWindow, 600, 400);
		buildStage.setScene(settingsScene);
		buildStage.showAndWait(); 
	}
	
	/**
	 * Tries to build a house on a property set.
	 * 
	 * This method is called when the player clicks build on a 
	 * property in the build window. It checks to see that player can
	 * afford to build a house on the entire property set. If they 
	 * cannot, it shows an error telling them. It attempts to build a 
	 * house on all properties in the complete set. It will add one 
	 * house or one hotel if there are already 4 houses. It makes 
	 * changes to the GUI to reflect the added houses. It shows an Alert
	 * object to reflect the success or failure of the build attempt.
	 * 
	 * @param completeSet is the set of properties to try to build on
	 * Author: Jesse Catterton
	 */
	public void attemptToBuild(ArrayList<Street> completeSet) {
		if (players[controller.getCurPlayerIndex()].adjustBalance(-(completeSet.get(0).getBuildCost() * completeSet.size()))) { // sees if player can't afford houses
			players[controller.getCurPlayerIndex()].adjustBalance(completeSet.get(0).getBuildCost() * completeSet.size()); // adds money back if player can't afford to build houses
			Alert msg = new Alert(Alert.AlertType.ERROR);
			msg.setTitle("Building Error"); 
			msg.setHeaderText("Building Error");
			msg.setContentText("You cannot afford to build houses on this property set.");
			msg.showAndWait();
			return;
		} 
		updatePlayerBalances();
		
		boolean successfulBuild = true;
		for (int i = 0; i < completeSet.size(); i++) {
			if (!completeSet.get(i).build()) { // builds a house on all properties, breaks if can't build on a property because of a hotel
				successfulBuild = false;
				break;
			}
		}
		if (successfulBuild) { // only puts house on GUI if build was successful
			int built = 0;
			for (int i = 0; i < 40; i++) { // loops to check if tile is a part of the complete set
				for (int j = 0; j < completeSet.size(); j++) {
					if (tiles[i].getSpace() == completeSet.get(j)) { // builds a house on tile if it's a part of a complete set
						((RectangleTile) tiles[i]).build();
						built++;
						if (built == completeSet.size()) { // shows success message if all houses were built
							Alert msg = new Alert(Alert.AlertType.INFORMATION);
							msg.setTitle("Building Success"); 
							msg.setHeaderText("Building Success");
							msg.setContentText("Successfully added one house to this complete set.");
							msg.showAndWait();
							return;
						};
					}
				}
			}
		} else { // shows error message if complete set already has a hotel on it
			Alert msg = new Alert(Alert.AlertType.ERROR);
			msg.setTitle("Building Error"); 
			msg.setHeaderText("Building Error");
			msg.setContentText("Cannot build on this set as there is already a hotel built.");
			msg.showAndWait();
		}
		
	}

	/**
	 * Allows user to sell properties.
	 * 
	 * This method is used to show a window that allows the player
	 * to sell properties and sell houses and hotels off of their
	 * properties. It allows the user to sell things one at a time,
	 * the houses and hotels sell back for full price and the properties
	 * themselves sell back for half price.
	 * 
	 * Author: Jesse Catterton
	 */
	public void showSellWindow() {
		sellStage = new Stage();
		sellStage.setTitle("Sell Window");
		BorderPane sellWindow = new BorderPane();
		sellList.setPadding(new Insets(10));
		sellList.setHgap(10);
		sellList.setVgap(10);
		sellWindow.setCenter(sellList);
		
		Player currentPlayer = players[controller.getCurPlayerIndex()];
		ArrayList<Property> properties = currentPlayer.getProperties();
		updatePropertyList(properties, sellList); // calls method to fill sell window
		
		Scene settingsScene = new Scene(sellWindow, 600, 400);
		sellStage.setScene(settingsScene);
		sellStage.showAndWait(); 
	}
	
	/**
	 * Updates the sell window's property list.
	 * 
	 * This method is called to update the sell window's property
	 * list with the proper properties and house/hotel counts. It 
	 * adds some information on the selling process and a list of
	 * the user's properties that they can sell. It is called when
	 * the player opens the window or when a player sells something.
	 * 
	 * @param properties is the players list of properties
	 * @param sellList is a GridPane holding the window's components
	 * Author: Jesse Catterton
	 */
	public void updatePropertyList(ArrayList<Property> properties, GridPane sellList) {
		sellList.getChildren().clear(); // resets window to rebuild
		
		Label title = new Label("Sell");
		title.setStyle("-fx-font-size: 20px;");
		sellList.add(title, 0, 0);
		
		Label information = new Label("Shown below are all properties you own with their house and hotel counts. "
				+ "Clicking sell on a propety with no houses will sell the property for half its buy value. "
				+ "If the property has houses on it, it will sell one house.");
		information.setWrapText(true);
		sellList.add(information, 0, 1);
		
		int row = 2;
		for (int i = 0; i < properties.size(); i++) { // goes through each property the player has
			Property property = properties.get(i);
			Label message = new Label(property.getName());
			if (property instanceof Street) { // adds house counts if property is a street
				message.setText(property.getName() + " with " + ((Street) property).getNumHouses() + " house(s) and " +
							((Street) property).getNumHotels() + " hotel(s)");
			}
			
			sellList.add(message, 0, row);
			Button sell = new Button("Sell");
			sell.setMinWidth(100);
			sell.setPrefWidth(100);
			sell.setMaxWidth(100);
			sell.setOnAction((event) -> {sellProperty(property, sellList, true);});
			sellList.add(sell, 1, row);
			row++;
		}
		
		Button closeWindow = new Button("Close");
		closeWindow.setOnAction((event) -> {sellStage.close();});
		sellList.add(closeWindow, 0, row + 1);
	}
	
	/**
	 * Sells a property or a house off a property.
	 * 
	 * This method completes the "sell" action of selling a 
	 * property by either removing a house and giving the player
	 * the build cost or by removing the property and giving the
	 * player half the buy value. It shows a success message and
	 * updates the sell window.
	 * 
	 * @param property is the property being sold
	 * @param sellList is a GridPane holding the window's components
	 * @param voluntarySale is a boolean indicating if the sale is a voluntary sale by the player or an involuntary computer sale
	 * Author: Jesse Catterton
	 */
	public void sellProperty(Property property, GridPane sellList, boolean voluntarySale) {
		Player currPlayer = players[controller.getCurPlayerIndex()];
		if (property instanceof Street && (((Street) property).getNumHouses() > 0 || ((Street) property).getNumHotels() > 0)) { // sees if we need to sell house instead of whole property
			ArrayList<Street> completeSet = currPlayer.checkForCompleteSet((Street) property, tiles, themeColors);
			currPlayer.adjustBalance(((Street) property).getBuildCost() * completeSet.size()); // gives player back the build cost
			for (int i = 0; i < 40; i++) { // loops to find property tiles
				if (completeSet.contains(tiles[i].getSpace())) { // sells a house on each property in set
					((Street) tiles[i].getSpace()).sell(); 

					((RectangleTile) tiles[i]).sell();
				}
			}
			updatePlayerBalances();
			if (voluntarySale) { // skips alert if this method was called for computer involunatrily
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Sell Message"); 
				msg.setHeaderText("Successful Sell");
				msg.setContentText("You sold the equivalent of one house for a value of " + (((Street) property).getBuildCost() * completeSet.size()));
				msg.showAndWait(); 
				updatePropertyList(currPlayer.getProperties(), sellList); // resets window to reflect sale
			}
			return;
		}
		currPlayer.adjustBalance(property.getMortgageValue()); // gives player back half the buy value
		currPlayer.removeProperty(property);
		property.setOwner(null);
		updatePlayerBalances();
		if (voluntarySale) { // skips alert if this method was called for computer involunatrily
			Alert msg = new Alert(Alert.AlertType.INFORMATION);
			msg.setTitle("Sell Message"); 
			msg.setHeaderText("Successful Sell");
			msg.setContentText("You sold " + property.getName() +  " for a value of " + property.getMortgageValue());
			msg.showAndWait(); 
			updatePropertyList(currPlayer.getProperties(), sellList);  // resets window to reflect sale
		}
	}
	
	/**
	 * Displays the save window. Gives the user the option to save their file
	 * into one of three slots, including being able to overwrite other save
	 * slots. 
	 * 
	 * Writes the current save data to a file, along with the date
	 * of the save for displaying in this and the load window.
	 * 
	 * Author: Tyler Ohme
	 */
	public void showSaveWindow() {
		// Set up window
		Stage saveStage = new Stage();
		saveStage.setTitle("Save Window");
		BorderPane saveWindow = new BorderPane();
		GridPane saveList = new GridPane();
		saveList.setPadding(new Insets(10));
		saveList.setHgap(10);
		saveList.setVgap(10);
		saveWindow.setCenter(saveList);
		
		saveList.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Label title = new Label("Save Game");
		title.setStyle("-fx-font-size: 20px;");
		saveList.add(title, 0, 0);
		
		Label information = new Label("Save Slots:");
		saveList.add(information,  0, 1);
		
		try {
			addSaveSlotView(saveList, saveStage, 1, 2);
			addSaveSlotView(saveList, saveStage, 2, 3);
			addSaveSlotView(saveList, saveStage, 3, 4);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR SAVE FILE SLOT NOT FOUND: " + e);
			e.printStackTrace();
		}
		
		Button close = new Button("Close");
		close.setOnAction((event) -> { saveStage.close(); });
		saveList.add(close, 0, 5);
		
		Scene saveScene = new Scene(saveWindow, 500, 350);
		saveStage.setScene(saveScene);
		saveStage.showAndWait();
	}
	
	/**
	 * Adds a single save slot row to the save window UI.
	 * 
	 * Each row displays the slot number, the date of the last save if one exists,
	 * and a button to save into that slot. If the slot is empty the date label
	 * reads "Empty".
	 * 
	 * @param saveList the list of elements in the full save window
	 * @param saveStage the stage of the main window, used for closing the window in lamdas
	 * @param slot the current save slot
	 * @param row the current UI row
	 * @throws FileNotFoundException
	 * Author: Tyler Ohme
	 */
	private void addSaveSlotView(GridPane saveList, Stage saveStage, int slot, int row) throws FileNotFoundException {
		// Open file
		String fileName = "src/save_files/game_save_" + slot + ".json";
		File saveFile = new File(fileName);
		
		// Define elements
		Label slotTitle = new Label("Slot " + slot + ":");
		Label slotDate = new Label();
		Button slotSave = new Button("Save");
		
		// Define save button functionality
		slotSave.setOnAction((event) -> {
			try {
				GameSave saveHandler = new GameSave(saveFile, controller);
				saveHandler.write();
				
				controller.setSaveSlot(slot);
				saveStage.close();
			} catch (IOException e) {
				System.out.println("ERROR SETTING SAVE SLOT: " + e);
				e.printStackTrace();
			}
		});
		slotSave.setDisable(false);
		
		// Check if slot has a file and handle each case
		if (saveFile.exists()) {
			slotDate.setText(getDate(saveFile));
		} else {
			slotDate.setText("Empty");
		}
		
		// Add elements
		saveList.add(slotTitle, 0, row);
		saveList.add(slotDate, 1, row);
		saveList.add(slotSave, 2, row);
	}
	
	/**
	 * Displays the load window. Allows the user to view, load, and delete their
	 * saves in three save slots.
	 * 
	 * @param menuStage the main menu stage used for closing the menu in lamdas
	 * Author: Tyler Ohme
	 */
	public void showLoadWindow(Stage menuStage) {
		// Setup stage
		Stage loadStage = new Stage();
		loadStage.setTitle("Load Window");
		BorderPane loadWindow = new BorderPane();
		GridPane loadList = new GridPane();
		loadList.setPadding(new Insets(10));
		loadList.setHgap(10);
		loadList.setVgap(10);
		loadWindow.setCenter(loadList);
		loadList.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		Label title = new Label("Load Game");
		title.setStyle("-fx-font-size: 20px;");
		loadList.add(title, 0, 0);
		
		Label information = new Label("Save Slots:");
		loadList.add(information, 0, 1);
		
		// Add save slot UI elements
		try {
			addLoadSlotView(loadList, loadStage, menuStage, 1, 2);
			addLoadSlotView(loadList, loadStage, menuStage, 2, 3);
			addLoadSlotView(loadList, loadStage, menuStage, 3, 4);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR LOAD FILE NOT FOUND: " + e);
			e.printStackTrace();
		}
		
		Button close = new Button("Close");
		close.setOnAction((event) -> {
			loadStage.close();
		});
		loadList.add(close, 0, 5);
		
		Scene loadScene = new Scene(loadWindow, 500, 350);
		loadStage.setScene(loadScene);
		loadStage.showAndWait();
	}
	
	/**
	 * Adds the labels and buttons that are used by a single save slot in the load window.
	 * 
	 * @param loadList the grid on the load window
	 * @param loadStage the load window stage
	 * @param menuStage the main menu stage
	 * @param slot the current save slot being created
	 * @param row the row this slot is being added to
	 * @throws FileNotFoundException
	 * Author: Tyler Ohme
	 */
	private void addLoadSlotView(GridPane loadList, Stage loadStage, Stage menuStage, int slot, int row) throws FileNotFoundException {
		// Open file
		String fileName = "src/save_files/game_save_" + slot + ".json";
		File saveFile = new File(fileName);
		
		// Define elements
		Label slotTitle = new Label("Slot " + slot + ":");
		Label slotDate = new Label();
		Button slotLoad = new Button("Load");
		Button slotDelete = new Button("Delete");
		
		// Define button functions
		slotLoad.setOnAction((event) -> {
			try {
				if (!saveFile.exists()) return; // failsafe in case button is enabled wrongly
				
				initializeControllerFromSave(fileName);
				loadStage.close();
				menuStage.close();
			} catch (IOException e) {
				System.out.println("ERROR CREATING CONTROLLER: " + e);
				e.printStackTrace();
			}
		});
		slotDelete.setOnAction((event) -> {
			saveFile.delete();
			slotDate.setText("Empty");
			slotLoad.setDisable(true);
			slotDelete.setDisable(true);
		});
		
		// Check if slot has a file and handle each case
		if (saveFile.exists()) {
			slotDate.setText(getDate(saveFile));
			slotLoad.setDisable(false);
			slotDelete.setDisable(false);
		} else {
			slotDate.setText("Empty");
			slotLoad.setDisable(true);
			slotDelete.setDisable(true);
		}
		
		// Add elements
		loadList.add(slotTitle, 0, row);
		loadList.add(slotDate, 1, row);
		loadList.add(slotLoad, 2, row);
		loadList.add(slotDelete, 3, row);
	}
	
	/**
	 * Reads and returns the save date stored in a save file.
	 * 
	 * Scans the file for the "date" key and returns its value as a formatted
	 * string. Used to display the last modified date for each slot in the save
	 * and load windows.
	 * 
	 * @param saveFile the file being read
	 * @return the string date being displayed
	 * @throws FileNotFoundException if the save file does not exist
	 * Author: Tyler Ohme
	 */
	private String getDate(File saveFile) throws FileNotFoundException {
		Scanner saveScanner = new Scanner(saveFile);
		
		while (saveScanner.hasNextLine()) {
			String line = saveScanner.nextLine();
			String tokens[] = line.strip().split(":", 2);
			if (tokens[0].equals("\"date\"")) {
				String result = tokens[1].replace(",", "").replace("\"", "").strip();
				saveScanner.close();
				return result;
			}
		}
		saveScanner.close();
		throw new FileNotFoundException("No save file found.\n");
	}
	
	/**
	 * Initializes the controller from an existing save file.
	 * 
	 * Creates a new MonopolyController from the given save file and updates
	 * the view's local copies of the theme and player counts to match. This
	 * is a private helper used by the load window.
	 * 
	 * @param fileName the name of the file being read into controller
	 * @throws IOException if the save file cannot be found or contains invalid data
	 * Author: Tyler Ohme
	 */
	private void initializeControllerFromSave(String fileName) throws IOException {
		controller = new MonopolyController(fileName);
		theme = controller.getTheme();
		humanPlayerCount = controller.getHumanPlayerCount();
		computerPlayerCount = controller.getComputerPlayerCount();
	}

	/**
	 * Used to update player balances on screen.
	 * 
	 * This method is used to update the balances that show next
	 * to each player's name on the screeen. After a purchase is 
	 * made, rent is paid, money is collected, etc., this method 
	 * is called at the end of a turn to reflect the current balances.
	 * 
	 * Author: Jesse Catterton
	 */
	public void updatePlayerBalances() {
		for (int i = 0; i < players.length; i++) { // loops to update each player's balance
			if (players[i].getBalance() < 0 && controller.canEscapeBankrupcy(players[i])) { // sets player status to bankrupt if they can escape
				players[i].setBankrupt(true);
				playerBalances[i].setText("$" + players[i].getBalance());
			} else if (players[i].getBalance() < 0 && !controller.canEscapeBankrupcy(players[i])) { // sets player status and officialy bankrupts them if they can't escape
				players[i].setBankrupt(true);
				playerBalances[i].setText("$0");
				players[i].playerIsBankrupt(tiles);
			} else { // sets balance like normal
				players[i].setBankrupt(false);
				playerBalances[i].setText("$" + players[i].getBalance());
			}
		}
	}
}
