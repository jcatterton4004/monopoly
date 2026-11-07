package savesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import csc335monopoly.MonopolyController;

import java.io.FileWriter;

/**
 * Handles reading and writing of game settings within a Monopoly save file.
 * 
 * Operates on the "settings" block of a structured JSON save file, supporting
 * two read/write methods: existing settings from a MonopolyController object,
 * or reading/writing settings as a HashMap for cases where a controller is not
 * available.
 * 
 * @author Tyler Ohme
 */
public class SettingsSave {
	private File settingsFile;

	/**
	 * Creates a new SettingsSave object for the given file.
	 * 
	 * @param inputFile the save file to read from or write data to
	 * @throws IOException if the file doesn't exist
	 */
	public SettingsSave(File inputFile) throws IOException {
		this.settingsFile = inputFile;
		
		if (!inputFile.exists()) {
			throw new IOException("Save file not found: " + inputFile.getAbsolutePath());
		}
	}
	
	/**
	 * Copies the test from one file to this object's settingsFile.
	 * 
	 * This function overwrites the class instance variable 'settingsFile' with
	 * the contents of the file inputted to this function. Will destroy the data
	 * previously held in settingsFile.
	 * 
	 * @param inputFile the file being copied to the output file
	 * @throws IOException
	 * Author: Tyler Ohme
	 */
	public void copyContents(File inputFile) throws IOException {
		// Delete old file contents
		settingsFile.delete();
		settingsFile.createNewFile();
		
		// Define file scanner and writer
		FileWriter output = new FileWriter(settingsFile);
		Scanner input = new Scanner(inputFile);
		
		// Copy each line over to output file
		while (input.hasNextLine()) {
			String line = input.nextLine();
			output.write(line + '\n');
		}
		
		output.close();
		input.close();
	}
	
	/**
	 * Writes the current data in the controller to a text file.
	 * 
	 * Reads each field from the inputted controller into a settings file.
	 * Goes line by line and adds each new line to an ArrayList, before
	 * fully overwriting the old content.
	 * 
	 * @param controller the reference to the controller
	 * @throws IOException
	 * Author: Tyler Ohme
	 */
	public void write(MonopolyController controller) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		Scanner input = new Scanner(settingsFile);
		boolean inSettings = false;
		
		// Read each line in file into arrayList, updating as necessary
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String stripped = line.strip();
			
			// Detect beginning of settings block
			if (stripped.split(":")[0].strip().equals("\"settings\"")) {
				inSettings = true;
				lines.add(line);
				continue;
			}
			
			// Detect end of settings block
			if (inSettings && stripped.charAt(0) == '}') {
				// Remove comma from last element in settings
				String lastLine = lines.get(lines.size() - 1);
				lastLine = lastLine.substring(0, lastLine.length() - 1);
				lines.remove(lines.size() - 1);
				lines.add(lastLine);
				
				inSettings = false;
				lines.add(line);
				continue;
			}
			
			// Handle adding lines to file list
			if (inSettings) {
				// Get key by splitting on colon and removing quotes
				String key = stripped.split(":")[0];
				String normalizedKey = key.substring(1, key.length() - 1);
				
				lines.add(getUpdatedLine(normalizedKey, controller));
			} else {
				lines.add(line);
			}
		}
		input.close();
		
		// Overwrite old content with modified content
		settingsFile.delete();
		settingsFile.createNewFile();
		FileWriter output = new FileWriter(settingsFile);
		for (String line : lines) {
			output.write(line + "\n");
		}
		output.close();
	}
	
	/**
	 * Generates a single file output line based on an input key and the corresponding
	 * value from the controller data.
	 * 
	 * This function is solely a helper for the write function.
	 * 
	 * @param key the settings key being queried in controller
	 * @param controller the controller containing the settings data
	 * @return the fully formatted string with the key and updated value
	 * Author: Tyler Ohme
	 */
	private String getUpdatedLine(String key, MonopolyController controller) {
		String value = "";
		
		// Convert string key into a value query
		switch (key) {
			case "humanPlayerCount":
				value = String.valueOf(controller.getHumanPlayerCount());
				break;
				
			case "computerPlayerCount":
				value = String.valueOf(controller.getComputerPlayerCount());
				break;
			
			case "goAmount":
				value = String.valueOf(controller.getGoAmount());
				break;
				
			case "startingBalance":
				value = String.valueOf(controller.getStartingBalance());
				break;
			
			case "propertyScale":
				value = String.valueOf(controller.getPropertyScale());
				break;
				
			case "freeParking":
				value = String.valueOf(controller.getFreeParking());
				break;
			
			case "skipPurchases":
				value = String.valueOf(controller.getSkipPurchases());
				break;
			
			case "trading":
				value = String.valueOf(controller.getTrading());
				break;
			
			case "theme":
				value = String.valueOf(controller.getTheme());
				break;
		}
		
		// Parse key and value pair into result
		String result = ("\t\t\t\"" + key + "\": " + value + ",");
		return result;
	}
	
	/**
	 * Reads data from a settings file into the fields in a controller object.
	 * 
	 * Reads the file line by line and updates the settings in the controller
	 * when each one is found.
	 * 
	 * @param controller the reference to the controller
	 * @throws FileNotFoundException
	 * Author: Tyler Ohme
	 */
	public void read(MonopolyController controller) throws FileNotFoundException {
		Scanner input = new Scanner(settingsFile);
		boolean inSettings = false;
		
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String stripped = line.strip();
			
			// Detect beginning of settings block
			if (stripped.split(":")[0].equals("\"settings\"")) {
				inSettings = true;
				continue;
			}
			
			// Detect end of settings block
			if (inSettings && stripped.charAt(0) == '}') {
				inSettings = false;
				continue;
			}
			
			// Read data from file into controller
			if (inSettings) {
				String split[] = stripped.split(":");
				
				// Get key by splitting on colon and removing quotes
				String key = split[0].substring(1, split[0].length() - 1);
				String value = split[1].replaceAll(",", "").strip();
				
				readValue(key, value, controller);
			}
		}
		
		input.close();
	}
	
	/**
	 * Takes a single read value from an input file and parses it into the
	 * corresponding setter function in the controller to modify the value.
	 * 
	 * This function is solely a helper function for the read function.
	 * 
	 * @param key the parsed key value
	 * @param value the new value the variable in controller is being set to
	 * @param controller the reference to the controller
	 * Author: Tyler Ohme
	 */
	private void readValue(String key, String value, MonopolyController controller) {
		// Convert string key into a value query
		switch (key) {
			case "humanPlayerCount":
				controller.setHumanPlayerCount(Integer.valueOf(value));
				break;
				
			case "computerPlayerCount":
				controller.setComputerPlayerCount(Integer.valueOf(value));
				break;
			
			case "goAmount":
				controller.setGoAmount(Integer.valueOf(value));
				break;
				
			case "startingBalance":
				controller.setStartingBalance(Integer.valueOf(value));
				break;
			
			case "propertyScale":
				controller.setPropertyScale(Integer.valueOf(value));
				break;
				
			case "freeParking":
				controller.setFreeParking(Boolean.parseBoolean(value));
				break;
			
			case "skipPurchases":
				controller.setSkipPurchases(Boolean.parseBoolean(value));
				break;
			
			case "trading":
				controller.setTrading(Boolean.parseBoolean(value));
				break;
			
			case "theme":
				controller.setTheme(value);
				break;
		}
	}
	
	/**
	 * Writes the current data in the controller to a text file.
	 * 
	 * Reads each field from the inputted controller into a settings file.
	 * Goes line by line and adds each new line to an ArrayList, before
	 * fully overwriting the old content.
	 * 
	 * @param settings is a HashMap containing the settings data
	 * @throws IOException
	 * Author: Tyler Ohme
	 */
	public void write(HashMap<String, String> settings) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		Scanner input = new Scanner(settingsFile);
		boolean inSettings = false;
		
		// Read each line in file into arrayList, updating as necessary
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String stripped = line.strip();
			
			// Detect beginning of settings block
			if (stripped.split(":")[0].equals("\"settings\"")) {
				inSettings = true;
				lines.add(line);
				continue;
			}
			
			// Detect end of settings block
			if (inSettings && stripped.charAt(0) == '}') {
				// Remove comma from last element in settings
				String lastLine = lines.get(lines.size() - 1);
				lastLine = lastLine.substring(0, lastLine.length() - 1);
				lines.remove(lines.size() - 1);
				lines.add(lastLine);
				
				inSettings = false;
				lines.add(line);
				continue;
			}
			
			// Handle adding lines to file list
			if (inSettings) {
				// Get key by splitting on colon and removing quotes
				String key = stripped.split(":")[0];
				String normalizedKey = key.substring(1, key.length() - 1);
				
				String value = settings.get(normalizedKey);
				
				// Get formatted line
				lines.add("\t\t\t\"" + normalizedKey + "\": " + value + ",");
			} else {
				lines.add(line);
			}
		}
		input.close();
		
		// Overwrite old content with modified content
		settingsFile.delete();
		settingsFile.createNewFile();
		FileWriter output = new FileWriter(settingsFile);
		for (String line : lines) {
			output.write(line + "\n");
		}
		output.close();
	}
	
	/**
	 * Reads the settings data from a file into a HashMap of strings.
	 * 
	 * Useful when settings need to be transferred without just putting values
	 * into a controller, such as when populating the settings UI with existing
	 * values.
	 * 
	 * @return a map of settings keys to their string values as read from the file
	 * @throws FileNotFoundException if the save file does not exist
	 */
	public HashMap<String, String> fileToMap() throws FileNotFoundException {
		HashMap<String, String> result = new HashMap<>();
		
		Scanner input = new Scanner(settingsFile);
		boolean inSettings = false;
		
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String stripped = line.strip();
			
			// Detect beginning of settings block
			if (stripped.split(":")[0].equals("\"settings\"")) {
				inSettings = true;
				continue;
			}
			
			// Detect end of settings block
			if (inSettings && stripped.charAt(0) == '}') {
				inSettings = false;
				continue;
			}
			
			// Read data from file into controller
			if (inSettings) {
				String split[] = stripped.split(":");
				
				// Get key by splitting on colon and removing quotes
				String key = split[0].substring(1, split[0].length() - 1);
				String value = split[1].replaceAll(",", "").strip();
				
				result.put(key, value);
			}
		}
		
		input.close();
		return result;
	}
}
