package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import csc335monopoly.ComputerPlayer;
import csc335monopoly.MonopolyController;
import csc335monopoly.Player;
import javafx.scene.paint.Color;
import model.spaces.Space;
import model.spaces.Street;
import model.spaces.Property;
import model.MonopolyModel;
import model.spaces.GoSpace;

/**
 * Tests the methods in the controller.
 * 
 * This class holds the test cases to test the methods in the
 * controller. It tests all of the methods in the controller
 * to ensure that the game behavior is managed correctly.
 * 
 * @author Jesse Catterton
 * @author Sophia Harms
 */

/*
 * Run using these VM arguments:
 * -ea
 * --module-path /path/to/javafx/lib
 * --add-modules javafx.controls,javafx.fxml
 * --enable-native-access=javafx.graphics
 */

public class ControllerTests {
	
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public ControllerTests() {
		
	}

	/**
	 * Tests the controller's assignment of spaces.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testAssignSpaces() throws IOException {
		MonopolyController controller = new MonopolyController();
		Space[] spaces = controller.getSpaces();
		
		 assertTrue(spaces != null);
	     assertEquals(40, spaces.length);
	}
	
	/**
	 * Tests the controller's addPlayers() method.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testAddPlayers() throws IOException {
		MonopolyController controller = new MonopolyController();
		Player[] players = controller.addPlayers(2, 2);
		assertEquals(4, controller.getPlayerCount());
		assertTrue(players[0] instanceof Player);
		assertTrue(players[1] instanceof Player);
		assertTrue(players[2] instanceof Player);
		assertTrue(players[3] instanceof Player);
		assertTrue(players[2] instanceof ComputerPlayer);
		assertTrue(players[3] instanceof ComputerPlayer);
		
		assertEquals(players.length, 4);
		
		assertEquals(players[0].getPieceColor(), Color.RED);
		assertEquals(players[1].getPieceColor(), Color.GOLD);
		assertEquals(players[2].getPieceColor(), Color.BLUE);
		assertEquals(players[3].getPieceColor(), Color.ORANGE);
		
		assertEquals(players[0].getPlayerId(), 0);
		assertEquals(players[1].getPlayerId(), 1);
		assertEquals(players[2].getPlayerId(), 2);
		assertEquals(players[3].getPlayerId(), 3);
	}
	
	/**
	 * Tests the controller's setGoAmount() method.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testSetGo() throws IOException {
		MonopolyController controller = new MonopolyController();
		Space[] spaces = controller.getSpaces();
		controller.setGoAmount(200);
		controller.updateGoAmount();
		assertEquals(((GoSpace) spaces[0]).getCollectAmount(), 200);
		controller.setGoAmount(500);
		controller.updateGoAmount();
		assertEquals(((GoSpace) spaces[0]).getCollectAmount(), 500);
	}
	
	/**
	 * Tests the controller's adjustPropertyPrices() method.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testAdjustPrices() throws IOException {
		MonopolyController controller = new MonopolyController();
		Space[] spaces = controller.getSpaces();
		assertEquals(((Property) spaces[1]).getBuyValue(), 60);
		controller.setPropertyScale(200);
		controller.adjustPropertyPrices();
		assertEquals(((Property) spaces[1]).getBuyValue(), 120);
		controller.setPropertyScale(50);
		controller.adjustPropertyPrices();
		controller.adjustPropertyPrices();
		assertEquals(((Property) spaces[1]).getBuyValue(), 30);
	}
	
	/**
	 * Tests the controller's player methods.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testPlayerControls() throws IOException {
		MonopolyController controller = new MonopolyController();
		Player[] players = controller.addPlayers(2, 2);
		
		controller.setStartingBalance(1000);
		controller.setPlayerBalance(players);
		assertEquals(players[0].getBalance(), 1000);
		assertEquals(players[1].getBalance(), 1000);
		assertEquals(players[2].getBalance(), 1000);
		assertEquals(players[3].getBalance(), 1000);
	}
	
	/**
	 * Tests the controller's setting getter and setter methods.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testSettings() throws IOException {
		MonopolyController controller = new MonopolyController();
		controller.setHumanPlayerCount(2);
		assertEquals(controller.getHumanPlayerCount(), 2);
		controller.setComputerPlayerCount(2);
		assertEquals(controller.getComputerPlayerCount(), 2);
		controller.setGoAmount(250);
		assertEquals(controller.getGoAmount(), 250);
		controller.setPropertyScale(500);
		assertEquals(controller.getPropertyScale(), 500);
		controller.setFreeParking(true);
		assertEquals(controller.getFreeParking(), true);
		controller.setStartingBalance(2000);
		assertEquals(controller.getStartingBalance(), 2000);
		controller.setSkipPurchases(true);
		assertEquals(controller.getSkipPurchases(), true);
		controller.setTrading(true);
		assertEquals(controller.getTrading(), true);
	}
	
	/**
	 * Tests the checkGameOver method.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testGameOver() throws IOException {
		MonopolyController controller = new MonopolyController();
		for (Player player : controller.getPlayers()) {
			player.setBankrupt(false);
		}
		assertEquals(null, controller.checkGameOver());
		for (Player player : controller.getPlayers()) {
			player.setBankrupt(true);
		}
		Player winner = controller.getPlayers()[0];
		winner.setBankrupt(false);
		assertEquals(winner, controller.checkGameOver());
	}
	
	/**
	 * Test game state getters/setters.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testGameState() throws IOException {
		MonopolyController controller = new MonopolyController();
		controller.setTheme("Atlantic City");
		assertEquals("Atlantic City", controller.getTheme());
		assertTrue(controller.getModel() instanceof MonopolyModel);
		Player player = new Player();
		controller.addPlayer(player, 0);
		assertEquals(player, controller.getPlayers()[0]);
		assertFalse(controller.isLoadedFromSave());
		controller.setCurPlayerIndex(5);
		assertEquals(5, controller.getCurPlayerIndex());
		assertEquals(6, controller.nextPlayerIndex());
		controller.setSaveSlot(2);
		assertEquals(2, controller.getSaveSlot());
	}
	
	/**
	 * Test bankrupcy logic.
	 * Author: Jesse Catterton
	 * @throws IOException if there is an error reading the settings file
	 */
	@Test
	public void testBankrupcy() throws IOException {
		MonopolyController controller = new MonopolyController();
		Player[] players = controller.addPlayers(2, 2);
		Street boardwalk = (Street) controller.getModel().getSpace(39);
		players[0].addProperty(boardwalk);
		players[0].setBalance(-100);
		assertTrue(controller.canEscapeBankrupcy(players[0]));
		players[0].setBalance(-500);
		assertFalse(controller.canEscapeBankrupcy(players[0]));
	}
	
	
}
