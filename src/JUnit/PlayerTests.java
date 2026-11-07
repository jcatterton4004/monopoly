package JUnit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import csc335monopoly.Card;
import csc335monopoly.ComputerPlayer;
import csc335monopoly.Player;
import javafx.scene.paint.Color;
import model.spaces.Property;
import model.spaces.Railroad;
import model.spaces.Space;
import model.spaces.Utility;

/**
 * Tests the classes for the Player class.
 * 
 * This class holds the test cases to test the classes that
 * represent a player in the game of Monopoly. It tests all of
 * Player's non-GUI methods along with the ComputerPlayer class.
 * 
 * @author Jesse Catterton
 * @author Sophia Harms
 * @author Heather Faber (no longer in group)
 */

/*
 * Run using these VM arguments:
 * -ea
 * --module-path /path/to/javafx/lib
 * --add-modules javafx.controls,javafx.fxml
 * --enable-native-access=javafx.graphics
 */

public class PlayerTests {
	
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public PlayerTests() {
		
	}
	
	/**
	 * Test method for adjusting the player's piece color. Tests
	 * every single color and ensures the proper one is set.
	 * Author: Heather Faber
	 */
	@Test
	void setPieceColorTest() {
		Player p = new Player();
		p.setPieceColor(Color.RED);
		assertEquals(Color.RED, p.getPieceColor());
		p.setPieceColor(Color.ORANGE);
		assertEquals(Color.ORANGE, p.getPieceColor());
		p.setPieceColor(Color.YELLOW);
		assertEquals(Color.YELLOW, p.getPieceColor());
		p.setPieceColor(Color.GREEN);
		assertEquals(Color.GREEN, p.getPieceColor());
		p.setPieceColor(Color.BLUE);
		assertEquals(Color.BLUE, p.getPieceColor());
		p.setPieceColor(Color.PURPLE);
		assertEquals(Color.PURPLE, p.getPieceColor());
		p.setPieceColor(Color.PINK);
		assertEquals(Color.PINK, p.getPieceColor());
		p.setPieceColor(Color.BROWN);
		assertEquals(Color.BROWN, p.getPieceColor());
	}
	
	/**
	 * Test method for adjusting the player's balance. Tests
	 * gaining money which returns false, and taking away money into bankruptcy
	 * returning true.
	 * Author: Heather Faber
	 */
	@Test
	void adjustBalanceTest() {
		Player p = new Player();
		assertEquals(p.getBalance(), 0);
		
		boolean newBalance;
		newBalance = p.adjustBalance(10);
		assertEquals(newBalance, false);
		newBalance = p.adjustBalance(-11);
		assertEquals(newBalance, true);
	}

	/**
	 * Test method for moving the player along the board. Tests
	 * regular moves and when the player makes a full loop.
	 * Author: Heather Faber
	 */
	@Test
	void moveTest() {
		Player p = new Player();
		
		p.move(39);
		assertEquals(p.getLocation(), 39);
		p.move(1);
		assertEquals(p.getLocation(), 0);
	}
	
	/**
	 * Test method for the held cards a player has.
	 * Author: Jesse Catterton
	 */
	@Test
	void testCards() {
		Player player = new Player();
		Card card = new Card("Test Card", 10);
		Card card2 = new Card("Fake Test Card", 10);
		player.addCard(card);
		ArrayList<Card> cardsList = new ArrayList<Card>();
		cardsList.add(card);
		assertEquals(player.getHeldCards(), cardsList);
		player.removeCard(card2);
		assertFalse(player.getHeldCards().isEmpty());
		player.removeCard(card);
		assertTrue(player.getHeldCards().isEmpty());	
	}
	

	/**
	 * Test method for houses/hotels getters and setters.
	 * Author: Jesse Catterton
	 */
	@Test
	void testHousesHotels() {
		Player player = new Player();
		assertEquals(player.getHouses(), 0);
		assertEquals(player.getHotels(), 0);
		player.addHouse();
		player.addHouse();
		player.addHouse();
		player.addHotel();
		assertEquals(player.getHouses(), 3);
		assertEquals(player.getHotels(), 1);
		player.sellHouse();
		player.sellHotel();
		assertEquals(player.getHouses(), 2);
		assertEquals(player.getHotels(), 0);
	}
	
	/**
	 * Test method for player id getter/setter.
	 * Author: Jesse Catterton
	 */
	@Test
	void testPlayerId() {
		Player player = new Player();
		player.setPlayerId(0);
		assertEquals(player.getPlayerId(), 0);
		player.setPlayerId(1);
		assertEquals(player.getPlayerId(), 1);
	}
	
	/**
	 * Test method for dice roll method.
	 * Author: Jesse Catterton
	 */
	@Test
	void testDiceRoll() {
		Player player = new Player();
		player.setLocation(10);
		int oldLocation = player.getLocation();
		int[] dice = player.diceRoll();
		assertEquals(dice[0] == dice[1], player.getRollWasDoubles());
		int roll = dice[0] + dice[1];
		assertTrue(roll >= 2 && roll <= 12);
		assertTrue(oldLocation + roll == player.getLocation());
		assertEquals(roll, player.getLastRoll());
	}
	
	/**
	 * Test method for player statuses.
	 * Author: Jesse Catterton
	 */
	@Test
	void testPlayerStatuses() {
		Player player = new Player();
		assertEquals(player.checkBankrupt(), false);
		assertEquals(player.checkJailed(), false);
		player.setBankrupt(true);
		player.setJailed(true);
		assertEquals(player.checkBankrupt(), true);
		assertEquals(player.checkJailed(), true);
	}
	
	/**
	 * Test method for property methods.
	 * Author: Jesse Catterton
	 */
	@Test
	void testProperties() {
		Player player = new Player();
		Railroad testRailroad1 = new Railroad(null, 0);
		Railroad testRailroad2 = new Railroad(null, 0);
		Utility testUtility = new Utility(null, 0);
		player.buyProperty(testRailroad1);
		player.buyProperty(testRailroad2);
		player.addProperty(testUtility);
		assertEquals(player.getRailroadsOwned(), 2);
		assertEquals(player.getUtilitiesOwned(), 1);
		player.removeProperty(testRailroad1);
		player.removeProperty(testUtility);
		assertEquals(player.getRailroadsOwned(), 1);
		assertEquals(player.getUtilitiesOwned(), 0);
	}
	
	/**
	 * Test method for getProperty()
	 * Author: Sophia Harms
	 */
	@Test
	void testgetProperty() {
		Player player = new Player();
		assertTrue(player.getProperties().isEmpty());
	}
	
	/**
	 * Tests jail roll count methods.
	 * Author: Jesse Catterton
	 */
	@Test
	void testJailRoll() {
		Player player = new Player();
		player.setJailRollCount(0);
		assertEquals(0, player.getJailRollCount());
		player.setJailRollCount(1);
		assertEquals(1, player.getJailRollCount());
	}
	
	/**
	 * Tests the computer player's ability to make decisions about purchasing a property.
	 * Author: Jesse Catterton
	 */
	@Test
	public void testPropertyDecision() {
		ComputerPlayer player = new ComputerPlayer();
		player.adjustBalance(100);
		assertTrue(player.makePropertyDecision(50));
		assertTrue(player.makePropertyDecision(100));
		assertFalse(player.makePropertyDecision(200));
	}
	
	/**
	 * Tests the setters/getters for the input from the files.
	 * Author: Jesse Catterton
	 */
	@Test
	public void testInputMethods() {
		Player player = new Player();
		player.setBalance(100);
		assertEquals(100, player.getBalance());
		player.setHouses(6);
		assertEquals(6, player.getHouses());
		player.setHotels(2);
		assertEquals(2, player.getHotels());
		player.setLastRoll(12);
		assertEquals(12, player.getLastRoll());
	}
}
