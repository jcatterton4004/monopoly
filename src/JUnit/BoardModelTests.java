package JUnit;
import model.*;
import model.spaces.FreeParkingSpace;
import model.spaces.GoSpace;
import model.spaces.Railroad;
import model.spaces.Space;
import model.spaces.Street;
import model.spaces.TaxSpace;
import model.spaces.Utility;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import csc335monopoly.Player;

/**
 * Tests the board's model.
 * 
 * This class holds the test cases to test the classes that
 * build and maintain the board's model. This includes the
 * Monopoly model class, the Space class, and all subclasses
 * of the Space class.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 * @author Sophia Harms
 */

/*
 * Run using these VM arguments:
 * -ea
 * --module-path /path/to/javafx/lib
 * --add-modules javafx.controls,javafx.fxml
 * --enable-native-access=javafx.graphics
 */

public class BoardModelTests {
	private static final String DEFAULT_INPUT_FILE = "src/input_files/default_board.csv";
	
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public BoardModelTests() {
		
	}

	/**
	 * Tests if all of the board slots were assigned to a value that isn't null.
	 * Author: Tyler Ohme
	 */
	@Test
	void testNotNull() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		
		for (int i = 0; i < 40; i++) {
			assertNotEquals(model.getSpace(i), null);
		}
	}
	
	/**
	 * Tests the names of the board model spaces against hard coded names taken from
	 * the file to see if they are being assigned properly. Note: Only tests names,
	 * should probably write tests to check other fields later.
	 * Author: Tyler Ohme
	 */
	@Test
	void testNames() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		String answers[] = { "Go",
			"Mediterranean Avenue",
			"Community Chest",
			"Baltic Avenue",
			"Income Tax",
			"Reading Railroad",
			"Oriental Avenue",
			"Community Chest",
			"Vermont Avenue",
			"Connecticut Avenue",
			"Jail",
			"St. Charles Place",
			"Electric Company",
			"States Avenue",
			"Virginia Avenue",
			"Pennsylvania Railroad",
			"St. James Place",
			"Community Chest",
			"Tennessee Avenue",
			"New York Avenue",
			"Free Parking",
			"Kentucky Avenue",
			"Community Chest",
			"Indiana Avenue",
			"Illinois Avenue",
			"B. & O. Railroad",
			"Atlantic Avenue",
			"Ventnor Avenue",
			"Water Works",
			"Marvin Gardens",
			"Go To Jail",
			"Pacific Avenue",
			"North Carolina Avenue",
			"Community Chest",
			"Pennsylvania Avenue",
			"Short Line",
			"Community Chest",
			"Park Place",
			"Luxury Tax",
			"Boardwalk"};
		
		Space[] board = model.getBoard();
		// Check each value against its hard coded value
		for (int i = 0; i < 40; i++) {
			assertEquals(board[i].getName().toLowerCase(), answers[i].toLowerCase());
		}
		assertEquals(null, model.getSpace(-10));
		assertEquals(null, model.getSpace(100));
	}
	
	/**
	 * Tests the getPropertyGroup() method.
	 * Author: Jesse Catterton
	 */
	@Test
	void testGetPropertyGroup()   {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		ArrayList<Street> group1 = new ArrayList<Street>();
		group1.add((Street) model.getSpace(1)); // mediterranean avenue
		group1.add((Street) model.getSpace(3));
		assertEquals(group1, model.getPropertyGroup((Street) model.getSpace(1)));
		assertEquals(group1, model.getPropertyGroup((Street) model.getSpace(3)));
	}
	
	/**
	 * Tests the methods in the Street class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testStreetClass() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		Street balticAve = (Street) model.getSpace(3);
		
		assertEquals(60, balticAve.getBuyValue());
		assertEquals(30, balticAve.getMortgageValue());
		balticAve.setBuyValue(100);
		assertEquals(100, balticAve.getBuyValue());
		assertEquals(50, balticAve.getMortgageValue());
		
		Player player = new Player();
		assertEquals(null, balticAve.getOwner());
		balticAve.setOwner(player);
		assertEquals(player, balticAve.getOwner());

		assertEquals(4, balticAve.getRent(0));
		assertEquals(50, balticAve.getBuildCost());
		
		balticAve.build();
		balticAve.build();
		balticAve.build();
		balticAve.build();
		balticAve.build();
		assertEquals(450, balticAve.getRent(0));
		assertEquals(1, balticAve.getNumHotels());
		assertFalse(balticAve.build());
		balticAve.sell();
		balticAve.sell();		
		balticAve.sell();
		assertEquals(0, balticAve.getNumHotels());
		assertEquals(2, balticAve.getNumHouses());
		assertEquals(60, balticAve.getRent(0));
		balticAve.sell();
		balticAve.sell();
		assertFalse(balticAve.sell());	
		balticAve.setNumHouses(3);
		assertEquals(3, balticAve.getNumHouses());
		balticAve.setNumHotels(1);
		assertEquals(1, balticAve.getNumHotels());
	}
	
	/**
	 * Tests the methods in the Free Parking class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testFreeParking() {
		FreeParkingSpace fp = new FreeParkingSpace("fp");
		assertEquals(0, fp.collectRewards());
		fp.addRewards(100);
		assertEquals(100, fp.collectRewards());
		assertEquals(0, fp.collectRewards());
	}
	
	/**
	 * Tests the methods in the GoSpace class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testGoSpace() {
		GoSpace go = new GoSpace("GO", 200);
		assertEquals(200, go.getCollectAmount());
		go.setCollectAmount(100);
		assertEquals(100, go.getCollectAmount());
	}
	
	/**
	 * Tests the methods in the Railroad class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testRailRoadClass() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		
		Player player = new Player();
		player.adjustBalance(600);
		Railroad r1 = (Railroad) model.getSpace(5);
		r1.setOwner(player);
		player.buyProperty(r1);
		assertEquals(25, r1.getRent(0));
		Railroad r2 = (Railroad) model.getSpace(15);
		Railroad r3 = (Railroad) model.getSpace(25);
		r2.setOwner(player);
		player.buyProperty(r2);
		r3.setOwner(player);
		player.buyProperty(r3);
		assertEquals(100, r1.getRent(0));
	}
	
	/**
	 * Tests the methods in the TaxSpace class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testTaxSpace() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		TaxSpace incomeTax = (TaxSpace) model.getSpace(4);
		assertEquals(200, incomeTax.getTaxAmount());
		TaxSpace luxuryTax = (TaxSpace) model.getSpace(4);
		assertEquals(200, luxuryTax.getTaxAmount());
	}
	
	/**
	 * Tests the methods in the Utility class.
	 * Author: Jesse Catterton
	 */
	@Test
	void testUtilityClass() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		
		Utility electricCompany = (Utility) model.getSpace(12);
		Utility waterWorks = (Utility) model.getSpace(28);
		assertEquals(0, electricCompany.getRent(3));
		
		Player player = new Player();
		electricCompany.setOwner(player);
		player.addProperty(electricCompany);
		assertEquals(12, electricCompany.getRent(3));
		assertEquals(36, electricCompany.getRent(9));

		waterWorks.setOwner(player);
		player.addProperty(waterWorks);
		assertEquals(30, electricCompany.getRent(3));
		assertEquals(120, waterWorks.getRent(12));
	}
	
	@Test
	void testGetPropertyIndex() {
		MonopolyModel model = new MonopolyModel(DEFAULT_INPUT_FILE);
		Utility electricCompany = (Utility) model.getSpace(12);
		Utility waterWorks = (Utility) model.getSpace(28);
		assertEquals(12, model.getPropertyIndex(electricCompany));
		assertEquals(28, model.getPropertyIndex(waterWorks));
	}
}
