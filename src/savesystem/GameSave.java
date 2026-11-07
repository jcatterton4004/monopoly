package savesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import csc335monopoly.Card;
import csc335monopoly.ComputerPlayer;
import csc335monopoly.MonopolyController;
import csc335monopoly.Player;
import javafx.scene.paint.Color;
import model.MonopolyModel;
import model.spaces.Property;
import model.spaces.Space;
import model.spaces.Street;

/**
 * Handles the reading and writing of Monopoly game save files.
 * 
 * Game state is stored in a structured JSON text format containing
 * three main blocks: settings, players, and buildings. The save file
 * records all data needed to fully restore a game session, including
 * player balances, board positions, owned properties, held cards, and
 * the number of houses/hotels on each Street.
 * 
 * @author Tyler Ohme
 */
public class GameSave {
	private final Color[] pieceColors = {
			Color.RED, Color.GOLD, Color.BLUE, Color.ORANGE, 
			Color.GREEN, Color.PURPLE, Color.PINK, Color.BROWN};
	
	private File saveFile;
	private MonopolyController controller;
	
	/**
	 * Creates a new GameSave instance for the given save file and controller.
	 * 
	 * @param saveFile the file to read from or write to
	 * @param controller the controller holding the current game state
	 * Author: Tyler Ohme
	 */
	public GameSave(File saveFile, MonopolyController controller) {
		this.saveFile = saveFile;
		this.controller = controller;
	}
	
	/**
	 * Writes the current data stored in controller and the model to a game
	 * save text file.
	 * 
	 * Side Note: There is definitely a better way of doing this, but I didn't
	 * want to spend the time working on that rather than other parts of the project.
	 * 
	 * @throws IOException if there is an error writing to the save file
	 * Author: Tyler Ohme
	 */
	public void write() throws IOException {
		MonopolyModel model = controller.getModel();
		
		// Delete old contents
		saveFile.delete();
		saveFile.createNewFile();
		
		FileWriter output = new FileWriter(saveFile);
		
		// Write file header
		output.write("{\n");
		output.write("\t\"file\": {\n");
		
		// Write the current time of modifying the file
		output.write("\t\t\"date\": \"" + getTime() + "\",\n");
		
		// Write settings block
		output.write("\t\t\"settings\": {\n");
	    output.write("\t\t\t\"humanPlayerCount\": " + controller.getHumanPlayerCount() + ",\n");
	    output.write("\t\t\t\"computerPlayerCount\": " + controller.getComputerPlayerCount() + ",\n");
	    output.write("\t\t\t\"goAmount\": " + controller.getGoAmount() + ",\n");
	    output.write("\t\t\t\"startingBalance\": " + controller.getStartingBalance() + ",\n");
	    output.write("\t\t\t\"propertyScale\": " + controller.getPropertyScale() + ",\n");
	    output.write("\t\t\t\"freeParking\": " + controller.getFreeParking() + ",\n");
	    output.write("\t\t\t\"skipPurchases\": " + controller.getSkipPurchases() + ",\n");
	    output.write("\t\t\t\"trading\": " + controller.getTrading() + ",\n");
	    output.write("\t\t\t\"theme\": \"" + controller.getTheme() + "\"\n");
	    output.write("\t\t},\n");
	    
	    // Store current player index
	    output.write("\t\t\"curPlayer\": " + controller.getCurPlayerIndex() + ",\n");
	    
	    // Write players block
	    output.write("\t\t\"players\": {\n");
	    Player players[] = controller.getPlayers();
	    for (int i = 0; i < players.length; i++) {
	    	Player player = players[i];
	    	
	    	// Write individual player block
	    	output.write("\t\t\t\"Player " + (player.getPlayerId() + 1) + "\": {\n");
	    	output.write("\t\t\t\t\"isHuman\": " + !(player instanceof ComputerPlayer) + ",\n");
	    	output.write("\t\t\t\t\"balance\": " + player.getBalance() + ",\n");
	        output.write("\t\t\t\t\"location\": " + player.getLocation() + ",\n");
	        output.write("\t\t\t\t\"houses\": " + player.getHouses() + ",\n");
	        output.write("\t\t\t\t\"hotels\": " + player.getHotels() + ",\n");
	        output.write("\t\t\t\t\"lastRoll\": " + player.getLastRoll() + ",\n");
	        output.write("\t\t\t\t\"jailRollCount\": " + player.getJailRollCount() + ",\n");
	        output.write("\t\t\t\t\"isBankrupt\": " + player.checkBankrupt() + ",\n");
	        output.write("\t\t\t\t\"isJailed\": " + player.checkJailed() + ",\n");
	        
	        // Write owned properties
	        output.write("\t\t\t\t\"ownedProperties\": [\n");
	        ArrayList<Property> properties = player.getProperties();
	        for (int j = 0; j < properties.size(); j++) {
	        	Property property = properties.get(j);
	        	
	        	int index = model.getPropertyIndex(property);
	        	output.write("\t\t\t\t\t" + index);
	        	
	        	if (j < properties.size() - 1) output.write(","); // Decide if there should be a comma
	        	output.write("\n");
	        }
	        output.write("\t\t\t\t],\n");
	        
	        // Write held cards
	        output.write("\t\t\t\t\"heldCards\": {\n");
	        ArrayList<Card> cards = player.getHeldCards();
	        for (int j = 0; j < cards.size(); j++) {
	        	Card card = cards.get(j);
	        	
	        	output.write("\t\t\t\t\t\"" + card.getMessage() + "\": " + card.getEffect());
	        	
	        	if (j < cards.size() - 1) output.write(","); // Decide if there should be a comma
	        	output.write("\n");
	        }
	        output.write("\t\t\t\t}\n");
	        
	        output.write("\t\t\t}");
	        if (i < players.length - 1) output.write(","); // Decide if there should be a comma
	        output.write("\n");
	    }
	    output.write("\t\t},\n");
	    
	    // Write buildings block
	    output.write("\t\t\"buildings\": [\n");
	    Space[] spaces = model.getBoard();
	    boolean firstBuilding = true; // track comma from back due to not knowing when last street is
	    for (int i = 0; i < 40; i++) {
	    	if (spaces[i] instanceof Street) {
	    		Street street = (Street) spaces[i];
	    		if (street.getNumHouses() > 0 || street.getNumHotels() > 0) {
	    			if (!firstBuilding) {
	    				output.write(",\n");
	    			}
	    			output.write("\t\t\t" + i + ", " + street.getNumHouses() + ", " + street.getNumHotels());
    				firstBuilding = false;
	    		}
	    	}
	    }
	    if (!firstBuilding) output.write("\n"); // only add extra newline if list not empty
	    output.write("\t\t]\n");
	    
	    // Write final brackets
	    output.write("\t}\n");
	    output.write("}\n");
	    
	    output.close();
	}
	
	/**
	 * Returns the current time as a formatted string. Used for the last modified
	 * display string for game saves.
	 * 
	 * Helper for the write() function to get the current time. Credit: This code 
	 * was adapted from the example on the w3schools tutorial at this link:
	 * https://www.w3schools.com/java/java_date.asp
	 * 
	 * @return the string representation of the current time
	 * Author: Tyler Ohme
	 */
	private String getTime() {
		LocalDateTime time = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
	    String date = time.format(formatter);
	    return date;
	}
	
	/**
	 * Reads a game data file. Assumes that the settings have already been parsed
	 * into controller using SettingsSave class.
	 * 
	 * @throws FileNotFoundException if the save file is not found/does not exist
	 * @throws InvalidSaveFileException if save the save file is of an invalid form
	 * Author: Tyler Ohme
	 */
	public void read() throws FileNotFoundException, InvalidSaveFileException {
		Scanner saveScanner = new Scanner(saveFile);
		MonopolyModel model = controller.getModel();
		
		// Read each line
		boolean inPlayers = false;
		boolean inBuildings = false;
		int playerIndex = 0;
		while (saveScanner.hasNextLine()) {
			String line = saveScanner.nextLine();
			String stripped = line.strip();
			String key = stripped.split(":")[0];
			
			// Get cur player index
			if (key.equals("\"curPlayer\"")) {
				String value = stripped.split(":")[1].replace(",", "").strip();
				controller.setCurPlayerIndex(Integer.parseInt(value));
			}
			
			// Detect beginning of players block
			if (key.equals("\"players\"")) {
				inPlayers = true;
				continue;
			}
			
			// Detect beginning of buildings block
			if (key.equals("\"buildings\"")) {
				inBuildings = true;
				continue;
			}
			
			// Handle player parsing
			if (inPlayers) {
				if (stripped.charAt(0) == '}') {
					inPlayers = false;
					continue;
				}
				
				// Gather current single player block
				ArrayList<String> playerData = new ArrayList<>();
				int depth = 1;
				while (depth > 0) {
					line = saveScanner.nextLine();
					
					// Track depth to end block at correct line
					for (int i = 0; i < line.length(); i++) {
						char cur = line.charAt(i);
						
						if (cur == '{') depth++;
						else if (cur == '}') depth--;
					}
					
					playerData.add(line);
				}
				
				// Add player to controller after adding other data
				Player parsedPlayer = readPlayer(playerData, model);
				parsedPlayer.setPieceColor(pieceColors[playerIndex]);
				parsedPlayer.setPlayerId(playerIndex);
				controller.addPlayer(parsedPlayer, playerIndex);
				playerIndex++;
				
				continue;
			}
						
			// Handle property parsing
			if (inBuildings) {
				if (stripped.charAt(0) == ']') {
					inBuildings = false;
					continue;
				}
								
				// Get property inputs from line
				String parts[] = stripped.split(",");
				if (parts.length != 3 && !(parts.length == 4 && parts[3].strip().isEmpty())) {
					// Check if incorrect number of parameters, accounting for 
					// case with extra field from trailing comma
					saveScanner.close();
					throw new InvalidSaveFileException("Incorrect number of line parameters in save file's 'buildings' block.");
				}

				for (int i = 0; i < 3; i++) parts[i] = parts[i].strip(); // remove whitespace
				
				// Convert property inputs to integers
				int data[] = new int[3];
				for (int i = 0; i < 3; i++) data[i] = Integer.parseInt(parts[i]);
				
				// Attempt to set number of houses/hotels in indexed Street in model
				Space fileSpace = model.getSpace(data[0]);
				if (fileSpace instanceof Street) {
					Street fileStreet = (Street) fileSpace;
					fileStreet.setNumHouses(data[1]);
					fileStreet.setNumHotels(data[2]);
				} else {
					saveScanner.close();
					throw new InvalidSaveFileException("Inputted street index resulted in a non-street space in 'buildings' block.");
				}
			}
		}
		
		saveScanner.close();
	}
	
	/**
	 * Parses a single Player's data into a player object.
	 * 
	 * Private helper to the wider scope read function. This function focuses
	 * on a single Player object rather than covering large amounts of the save.
	 * 
	 * @param playerLines the lines containing only this player's data
	 * @param model a reference to the model
	 * @return the populated player object
	 * @throws InvalidSaveFileException 
	 * Author: Tyler Ohme
	 */
	private Player readPlayer(ArrayList<String> playerLines, MonopolyModel model) throws InvalidSaveFileException {
		// Get player type
		Player result;
		if (isHumanPlayer(playerLines)) {
			result = new Player();
		} else {
			result = new ComputerPlayer();
		}
		
		// Read simple variable data
		boolean inProperty = false;
		boolean inCards = false;
		for (String line : playerLines) {
			// Handle property list
			if (inProperty) {
				// Check if block is ended
				if (line.strip().charAt(0) == ']') {
					inProperty = false;
					continue;
				}
				
				// Parse property index into object
				int index = Integer.valueOf(line.replace(",", "").strip());
				Space curProperty = model.getSpace(index);
				if (curProperty instanceof Property) {
					result.addProperty((Property) curProperty);
				} else {
					throw new InvalidSaveFileException("Inputted property index resulted in a non-property space in 'Player->ownedProperties' block.");
				}
				
				continue;
			}
			
			// Handle cards list ending (check before tokenizing to avoid indexing issues)
			if (inCards && line.strip().charAt(0) == '}') {
				inCards = false;
				continue;
			}
			
			// Split line into processed fields
			String lineSplit[] = line.strip().split(":");
			if (lineSplit.length != 2) continue; // handle out of index errors
			String key = lineSplit[0].replace("\"", "").strip();
			String value = lineSplit[1].replace(",", "").strip();
			
			// Read card data
			if (inCards) {
				Card newCard = new Card(key, Integer.parseInt(value));
				result.addCard(newCard);
				continue;
			}
			
			// Parse key/value into usable data
			switch (key) {
			case "balance":
				result.setBalance(Integer.valueOf(value));
				break;
				
			case "location":
				result.setLocation(Integer.valueOf(value));
				break;
				
			case "houses":
				result.setHouses(Integer.valueOf(value));
				break;
			
			case "hotels":
				result.setHotels(Integer.valueOf(value));
				break;
				
			case "lastRoll":
				result.setLastRoll(Integer.valueOf(value));
				break;
			
			case "jailRollCount":
				result.setJailRollCount(Integer.valueOf(value));
				break;
				
			case "isBankrupt":
				result.setBankrupt(Boolean.parseBoolean(value));
				break;
				
			case "isJailed":
				result.setJailed(Boolean.parseBoolean(value));
				break;
				
			case "ownedProperties":
				inProperty = true;
				break;
			
			case "heldCards":
				inCards = true;
				break;
			
			case "isHuman":
				continue;
			}
		}
		
		return result;
	}
	
	/**
	 * Determines whether a player's save data represents a human player.
	 * 
	 * Scans the lines for the "isHuman" key and returns the value as a boolean.
	 * Used as a helper for readPlayer() to determine which Player class/subclass
	 * to instantiate.
	 * 
	 * @param playerLines the lines of the save file representing a single player object
	 * @return true if the player is human, false if they are a computer player
	 * @throws InvalidSaveFileException if no "isHuman" key is found
	 * Author: Tyler Ohme
	 */
	private boolean isHumanPlayer(ArrayList<String> playerLines) throws InvalidSaveFileException {
		for (String line : playerLines) {
			// Split line into processed fields
			String lineSplit[] = line.strip().split(":");
			if (lineSplit.length != 2) continue;
			
			String key = lineSplit[0].replace("\"", "").strip();
			String value = lineSplit[1].replace(",", "").strip();
			
			if (key.equals("isHuman")) {
				return Boolean.parseBoolean(value);
			}
		}
		throw new InvalidSaveFileException("Save file player data does not contain an isHuman boolean flag.");
	}
}
