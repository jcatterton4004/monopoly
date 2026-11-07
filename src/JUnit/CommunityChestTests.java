package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import csc335monopoly.CommunityChest;
import csc335monopoly.Player;

/**
 * Tests the classes for the community chest cards.
 * 
 * This class holds the test cases to test the classes that
 * maintian the community chest cards. It has tests for each
 * of the different themed decks.
 * 
 * @author Sophia Harms
 */
public class CommunityChestTests {
	
	/**
	 * Purpose: Default constructor for JavaDocs
	 * Author: Sophia Harms
	 */
	public CommunityChestTests() {
		
	}
	
	/**
	 * Tests the default constructor adds 17 cards to the deck
	 * Author: Sophia Harms
	 */
	@Test
	public void buildDeck() {
		CommunityChest chest = new CommunityChest();
		assertEquals(chest.getSize(), 17);
	}
	
	/**
	 * Tests the Marvel Deck constructor for adds 17 cards to the deck
	 * Author: Sophia Harms
	 */
	@Test
	public void buildMarvelDeck() {
		CommunityChest chest = new CommunityChest("Marvel Comics");
		assertEquals(chest.getSize(), 17);
	}
	
	/**
	 * Tests the DC Deck constructor adds 17 cards to the deck
	 * Author: Sophia Harms
	 */
	@Test
	public void buildDCDeck() {
		CommunityChest chest = new CommunityChest("DC Comics");
		assertEquals(chest.getSize(), 17);
	}
	
	/**
	 * Tests the Atlantic City constructor adds 17 cards to the deck
	 * Author: Sophia Harms
	 */
	@Test
	public void buildAtlanticDeck() {
		CommunityChest chest = new CommunityChest("Atlantic City");
		assertEquals(chest.getSize(), 17);
	}
	
	/**
	 * Tests drawing all cards in the Marvel deck.
	 * A testDeck method was created in the CommunityChest
	 * class to prevent random shuffling.
	 * Author: Sophia Harms
	 */
	@Test
	public void drawMarvelCards() {
		// Initialize player objects
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player[] players = {p1,p2,p3,p4};
		
		p1.adjustBalance(100);
		p2.adjustBalance(100);
		p3.adjustBalance(100);
		p4.adjustBalance(100);
		
		// Initialize community chest
		CommunityChest chest = new CommunityChest("Marvel Comics");
		chest.setTestMarvelDeck();
		
		// advance to go
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 100);
		// bank error
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// doctor fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 250);
		// sale of stock
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// out of jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		assertEquals(p1.getHeldCards().get(0).getMessage(), "Get out of the Raft Free.");
		// go to jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// opera night
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		assertEquals(p2.getBalance(), 50);
		assertEquals(p3.getBalance(), 50);
		assertEquals(p4.getBalance(), 50);
		// holiday fund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 550);
		// tax refund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 570);
		// birthday
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		assertEquals(p2.getBalance(), 40);
		assertEquals(p3.getBalance(), 40);
		assertEquals(p4.getBalance(), 40);
		// life insurance 
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 700);
		// hospital fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		// school fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		// consultancy
		chest.drawCard(p2, 4, players);
		assertEquals(p2.getBalance(), 65);
		// repair fee
		p1.addHotel();
		p1.addHotel();
		p1.addHouse();
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 180);
		// beauty winner
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 190);
		// inherit
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 290);
		// reset deck
		chest.drawCard(p1, 4, players);
		assertEquals(chest.getSize(), 16);
	}
	
	/**
	 * Tests drawing all cards in the DC deck.
	 * A testDeck method was created in the CommunityChest
	 * class to prevent random shuffling.
	 * Author: Sophia Harms
	 */
	@Test
	public void drawDCCards() {
		// Initialize player objects
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player[] players = {p1,p2,p3,p4};
		
		p1.adjustBalance(100);
		p2.adjustBalance(100);
		p3.adjustBalance(100);
		p4.adjustBalance(100);
		
		// Initialize community chest
		CommunityChest chest = new CommunityChest("DC Comics");
		chest.setTestDCDeck();
		
		// advance to go
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 100);
		// bank error
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// doctor fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 250);
		// sale of stock
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// out of jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		assertEquals(p1.getHeldCards().get(0).getMessage(), "Get out of Arkham Asylum Free. Courtesy of Joker :)");
		// go to jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// opera night
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		assertEquals(p2.getBalance(), 50);
		assertEquals(p3.getBalance(), 50);
		assertEquals(p4.getBalance(), 50);
		// holiday fund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 550);
		// tax refund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 570);
		// birthday
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		assertEquals(p2.getBalance(), 40);
		assertEquals(p3.getBalance(), 40);
		assertEquals(p4.getBalance(), 40);
		// life insurance 
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 700);
		// hospital fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		// school fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		// consultancy
		chest.drawCard(p2, 4, players);
		assertEquals(p2.getBalance(), 65);
		// repair fee
		p1.addHotel();
		p1.addHotel();
		p1.addHouse();
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 180);
		// beauty winner
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 190);
		// inherit
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 290);
		// reset deck
		chest.drawCard(p1, 4, players);
		assertEquals(chest.getSize(), 16);
	}
	
	/**
	 * Tests drawing all cards in the Atlantic deck.
	 * A testDeck method was created in the CommunityChest
	 * class to prevent random shuffling.
	 * Author: Sophia Harms
	 */
	@Test
	public void testDraw() {
		// Initialize player objects
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player[] players = {p1,p2,p3,p4};
		
		p1.adjustBalance(100);
		p2.adjustBalance(100);
		p3.adjustBalance(100);
		p4.adjustBalance(100);
		
		// Initialize community chest
		CommunityChest chest = new CommunityChest();
		chest.setTestDeck();
		
		// advance to go
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 100);
		// bank error
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// doctor fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 250);
		// sale of stock
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// out of jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		assertEquals(p1.getHeldCards().get(0).getMessage(), "Get out of Jail Free.");
		// go to jail
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 300);
		// opera night
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		assertEquals(p2.getBalance(), 50);
		assertEquals(p3.getBalance(), 50);
		assertEquals(p4.getBalance(), 50);
		// holiday fund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 550);
		// tax refund
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 570);
		// birthday
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		assertEquals(p2.getBalance(), 40);
		assertEquals(p3.getBalance(), 40);
		assertEquals(p4.getBalance(), 40);
		// life insurance 
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 700);
		// hospital fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 600);
		// school fee
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 450);
		// consultancy
		chest.drawCard(p2, 4, players);
		assertEquals(p2.getBalance(), 65);
		// repair fee
		p1.addHotel();
		p1.addHotel();
		p1.addHouse();
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 180);
		// beauty winner
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 190);
		// inherit
		chest.drawCard(p1, 4, players);
		assertEquals(p1.getBalance(), 290);
		// reset deck
		chest.drawCard(p1, 4, players);
		assertEquals(chest.getSize(), 16);
	}
}