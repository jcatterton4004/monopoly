package JUnit;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.paint.Color;
import model.spaces.Street;
import savesystem.GameSave;
import savesystem.InvalidSaveFileException;
import savesystem.SettingsSave;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import csc335monopoly.Card;
import csc335monopoly.MonopolyController;

/**
 * Tests the save system.
 * 
 * This class holds the test cases to test the game save and
 * settings save classes work properly to save game data and
 * reload it.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 */

/*
 * Run using these VM arguments:
 * -ea
 * --module-path /path/to/javafx/lib
 * --add-modules javafx.controls,javafx.fxml
 * --enable-native-access=javafx.graphics
 */

class SaveSystemTests {
	private final File TESTFILE = new File("src/save_files/test_file.json");
	private final File DEFAULT = new File("src/save_files/default_settings.json");
	
	/**
	 * Test the ability to copy a file's contents
	 * @throws IOException if there is an error reading file
	 * Author: Tyler Ohme
	 */
	@Test
	void testCopyFileContents() throws IOException {
		TESTFILE.delete(); // remove old file contents
		TESTFILE.createNewFile(); // creates new file
		
		// Copy contents from default test file to test file
		SettingsSave saver = new SettingsSave(TESTFILE);
		saver.copyContents(DEFAULT);
		
		// Scan each line and compare them
		Scanner defaultScanner = new Scanner(DEFAULT);
		Scanner testScanner = new Scanner(TESTFILE);
		while (defaultScanner.hasNextLine() && testScanner.hasNextLine()) {
			String testLine = testScanner.nextLine();
			String defaultLine = defaultScanner.nextLine();
			
			assertTrue(testLine.equals(defaultLine));
		}
		
		defaultScanner.close();
		testScanner.close();
	}
	
	/**
	 * Tests that the settings file can be written to from both a file and
	 * from a string to string hashmap and that the file can be read.
	 * @throws IOException if there is an error reading/writing file
	 * Author: Jesse Catterton
	 */
	@Test
	void testWriteReadSettings() throws IOException {
		TESTFILE.delete(); // remove old file contents
		TESTFILE.createNewFile(); // creates new file
		
		// Copy contents from default test file to test file
		SettingsSave saver = new SettingsSave(TESTFILE);
		saver.copyContents(DEFAULT);
		
		MonopolyController controller = new MonopolyController();
		controller.setHumanPlayerCount(6);
		controller.setComputerPlayerCount(2);
	    controller.setGoAmount(250);
	    controller.setStartingBalance(2000);
	    controller.setPropertyScale(50);
	    controller.setFreeParking(false);
	    controller.setSkipPurchases(true);
	    controller.setTrading(true);
	    controller.setTheme("Marvel Comics");
	    saver.write(controller);
	    HashMap<String, String> data = saver.fileToMap();
	    assertEquals("6", data.get("humanPlayerCount"));
	    assertEquals("2", data.get("computerPlayerCount"));
	    assertEquals("250", data.get("goAmount"));
	    assertEquals("2000", data.get("startingBalance"));
	    assertEquals("50", data.get("propertyScale"));
	    assertEquals("false", data.get("freeParking"));
	    assertEquals("true", data.get("skipPurchases"));
	    assertEquals("true", data.get("trading"));
	    assertEquals("Marvel Comics", data.get("theme"));
	    saver.write(data);
	    assertEquals("6", data.get("humanPlayerCount"));
	    assertEquals("2", data.get("computerPlayerCount"));
	    assertEquals("250", data.get("goAmount"));
	    assertEquals("2000", data.get("startingBalance"));
	    assertEquals("50", data.get("propertyScale"));
	    assertEquals("false", data.get("freeParking"));
	    assertEquals("true", data.get("skipPurchases"));
	    assertEquals("true", data.get("trading"));
	    assertEquals("Marvel Comics", data.get("theme"));
	}
	
	/**
	 * Tests the ability to read/write the game state from/to a file.
	 * @throws IOException if there is an error reading/writing file
	 * Author: Jesse Catterton
	 */
	@Test
	void testGameSave() throws IOException {
		TESTFILE.delete(); // remove old file contents
		TESTFILE.createNewFile(); // creates new file
		MonopolyController controller = new MonopolyController();
		controller.setHumanPlayerCount(2);
		controller.setComputerPlayerCount(1);
		controller.setCurPlayerIndex(1);
		
		Street balticAve = (Street) controller.getModel().getSpace(3);
		balticAve.build();
		controller.getPlayers()[0].addProperty(balticAve);
		
		Street boardwalk = (Street) controller.getModel().getSpace(39);
		controller.getPlayers()[1].addProperty(boardwalk);
		Card card = new Card("Test card", 10);
		controller.getPlayers()[1].addCard(card);
		
		GameSave saver = new GameSave(TESTFILE, controller);
		saver.write();
		
		// change data to see if the read method correctly loads all values back
		balticAve.setOwner(null);
		balticAve.build();
		balticAve.build();
		boardwalk.setOwner(controller.getPlayers()[1]);
		controller.getPlayers()[1].removeCard(card);
		
		saver.read();
		assertEquals(2, controller.getHumanPlayerCount());
		assertEquals(1, controller.getComputerPlayerCount());
		assertEquals(1, controller.getCurPlayerIndex());
		assertEquals(1, balticAve.getNumHouses());
		assertEquals(controller.getPlayers()[0], balticAve.getOwner());
		assertEquals(0, boardwalk.getNumHouses());
		assertEquals(controller.getPlayers()[1], boardwalk.getOwner());
		assertEquals("Test card", controller.getPlayers()[1].getHeldCards().get(0).getMessage());
	}
	
	/**
	 * Tests the InvalidSaveFileException class constructor.
	 * Author: Jesse Catterton
	 */
	@Test
	void testException() {
		InvalidSaveFileException e = new InvalidSaveFileException("Invalid save file");

	    assertEquals("Invalid save file", e.getMessage());
	    assertTrue(e instanceof IOException);
	}
}
