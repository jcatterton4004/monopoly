package csc335monopoly;

import java.util.ArrayList;
import java.util.Random;
/**
 * Purpose: Creates the Community Chest class.
 * @author Sophia Harms
 * CSC 355 Team Project: Monopoly
 * This class stores all the Community Chest deck.
 * There are unique decks for each theme that have different
 * messages. The deck is made of Card objects.
 */
public class CommunityChest {
	private ArrayList<Card> deck;
	private String theme;
	
	/**
	 * Purpose: Default Constructor method for the class.
	 * The deck is set when an instance is created 
	 * so that the player can draw a card when they 
	 * land on a community chest space. The default map
	 * is Atlantic City.
	 * Author: Sophia Harms
	 */
	public CommunityChest() {
		deck = new ArrayList<Card>();
		setAtlanticDeck();
		theme = "Atlantic City";
	}
	
	/**
	 * Purpose: Constructor method for the class when a theme is passed.
	 * The deck is set when an instance is created 
	 * so that the player can draw a card when they 
	 * land on a community chest space. The deck is chosen based on the
	 * theme passed in.
	 * @param theme: the String of which theme the user chose.
	 * Author: Sophia Harms
	 */
	public CommunityChest(String theme) {
		deck = new ArrayList<Card>();
		this.theme = theme;
		if(theme == "Marvel Comics") {
			setMarvelDeck();
		} else if(theme == "DC Comics") {
			setDCDeck();
		} else {
			setAtlanticDeck();
		}
	}
	
	/**
	 * Purpose: Adds all the cards to the deck if the theme is Atlantic City.
	 * This will allow the game to keep track of what cards are in the game
	 * and reset the deck if it is empty. Once all the cards are added, the
	 * deck is shuffled to simulate randomness in the game play.
	 * Author: Sophia Harms
	 */
	private void setAtlanticDeck() {
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Bank error in your favor. Collect $200.", 200);
		Card doctorFee = new Card("Doctor's fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of stock, you get $50.", 50);
		Card outOfJail = new Card("Get out of Jail Free.", 0);
		Card goToJail = new Card("Go to jail. Do not pass Go, Do not collect $200.", 0);
		Card operaNight = new Card("Grand Opera Night. Collect $50 from every player for opening night seats.", 50);
		Card holidayFund = new Card("Holiday Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees. Pay $100.", -100);
		Card schoolFee = new Card("School fees. Pay $150.", -150);
		Card consultancy = new Card("Receive $25 consultancy fee.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card beautyWinner = new Card("You have won second prize in a beauty contest. Collect $10.", 10);
		Card inherit = new Card("You inherit $100.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(doctorFee);
		deck.add(saleOfStock);
		deck.add(outOfJail);
		deck.add(goToJail);
		deck.add(operaNight);
		deck.add(holidayFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(consultancy);
		deck.add(repairFee);
		deck.add(beautyWinner);
		deck.add(inherit);
		
		shuffleDeck();
	}
	
	/**
	 * Purpose: Adds all the cards to the deck if the theme is DC Comics.
	 * This will allow the game to keep track of what cards are in the game
	 * and reset the deck if it is empty. Once all the cards are added, the
	 * deck is shuffled to simulate randomness in the game play.
	 * Author: Sophia Harms
	 */
	private void setDCDeck() {
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Metropolis Bank error in your favor. Collect $200.", 200);
		Card jlInsurance = new Card("Justice League Insurance fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of Lex Corp stock, you get $50.", 50);
		Card outOfJail = new Card("Get out of Arkham Asylum Free. Courtesy of Joker :)", 0);
		Card goToJail = new Card("Batman caught you evading your taxes. Go to Arkham Asylum. Do not pass Go, Do not collect $200.", 0);
		Card operaNight = new Card("Gotham City Opera House Night. Collect $50 from every player for opening night seats. Make sure to wear your pearls!", 50);
		Card holidayFund = new Card("Booster Gold's Tour Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees from the lastest Rogues Gallery attack. Pay $100.", -100);
		Card schoolFee = new Card("Smallville School fees. Pay $150.", -150);
		Card consultancy = new Card("Receive $25 Justice League Damage Relief.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card contestWinner = new Card("You have won second prize in a Justice League Costume Contest. Collect $10.", 10);
		Card wayneFoundation = new Card("You receive $100 from The Wayne Foundation.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(jlInsurance);
		deck.add(saleOfStock);
		deck.add(outOfJail);
		deck.add(goToJail);
		deck.add(operaNight);
		deck.add(holidayFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(consultancy);
		deck.add(repairFee);
		deck.add(contestWinner);
		deck.add(wayneFoundation);
		
		shuffleDeck();
	}
	
	/**
	 * Purpose: Adds all the cards to the deck if the theme is Marvel Comics.
	 * This will allow the game to keep track of what cards are in the game
	 * and reset the deck if it is empty. Once all the cards are added, the
	 * deck is shuffled to simulate randomness in the game play.
	 * Author: Sophia Harms
	 */
	private void setMarvelDeck() {
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Midtown Bank error in your favor. Collect $200.", 200);
		Card avengerInsurance = new Card("Avengers Insurance fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of Oscorp stock, you get $50.", 50);
		Card outOfRaft = new Card("Get out of the Raft Free.", 0);
		Card goToRaft = new Card("Go to the Raft. Do not pass Go, Do not collect $200.", 0);
		Card RogersNight = new Card("Rogers: The Musical Night. Collect $50 from every player for opening night seats.", 50);
		Card sokoviaFund = new Card("Sokovia Trip Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees from alien invasion. Pay $100.", -100);
		Card schoolFee = new Card("Midtown High School fees. Pay $150.", -150);
		Card damageRelief = new Card("Receive $25 Super Hero Damage Relief.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card contestWinner = new Card("You have won second prize in an Avenger Costume Contest. Collect $10.", 10);
		Card scienceFair = new Card("You win $100 from first place in the Stark Science Fair.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(avengerInsurance);
		deck.add(saleOfStock);
		deck.add(outOfRaft);
		deck.add(goToRaft);
		deck.add(RogersNight);
		deck.add(sokoviaFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(damageRelief);
		deck.add(repairFee);
		deck.add(contestWinner);
		deck.add(scienceFair);
		
		shuffleDeck();
	}
	
	/**
	 * Purpose: Tester method to have a constant deck for Marvel Comics.
	 * This is the same as the setMarvelDeck except it does not
	 * shuffle the deck so that each card can be tested when
	 * calling the drawCard method.
	 * Author: Sophia Harms
	 */
	public void setTestMarvelDeck() {
		deck.removeAll(deck);
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Midtown Bank error in your favor. Collect $200.", 200);
		Card avengerInsurance = new Card("Avengers Insurance fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of Oscorp stock, you get $50.", 50);
		Card outOfRaft = new Card("Get out of the Raft Free.", 0);
		Card goToRaft = new Card("Go to the Raft. Do not pass Go, Do not collect $200.", 0);
		Card RogersNight = new Card("Rogers: The Musical Night. Collect $50 from every player for opening night seats.", 50);
		Card sokoviaFund = new Card("Sokovia Trip Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees from alien invasion. Pay $100.", -100);
		Card schoolFee = new Card("Midtown High School fees. Pay $150.", -150);
		Card damageRelief = new Card("Receive $25 Super Hero Damage Relief.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card contestWinner = new Card("You have won second prize in an Avenger Costume Contest. Collect $10.", 10);
		Card scienceFair = new Card("You win $100 from first place in the Stark Science Fair.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(avengerInsurance);
		deck.add(saleOfStock);
		deck.add(outOfRaft);
		deck.add(goToRaft);
		deck.add(RogersNight);
		deck.add(sokoviaFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(damageRelief);
		deck.add(repairFee);
		deck.add(contestWinner);
		deck.add(scienceFair);
	}
	
	/**
	 * Purpose: Tester method to have a constant deck for DC Comics.
	 * This is the same as the setDCDeck except it does not
	 * shuffle the deck so that each card can be tested when
	 * calling the drawCard method.
	 * Author: Sophia Harms
	 */
	public void setTestDCDeck() {
		deck.removeAll(deck);
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Metropolis Bank error in your favor. Collect $200.", 200);
		Card jlInsurance = new Card("Justice League Insurance fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of Lex Corp stock, you get $50.", 50);
		Card outOfJail = new Card("Get out of Arkham Asylum Free. Courtesy of Joker :)", 0);
		Card goToJail = new Card("Batman caught you evading your taxes. Go to Arkham Asylum. Do not pass Go, Do not collect $200.", 0);
		Card operaNight = new Card("Gotham City Opera House Night. Collect $50 from every player for opening night seats. Make sure to wear your pearls!", 50);
		Card holidayFund = new Card("Booster Gold's Tour Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees from the lastest Rogues Gallery attack. Pay $100.", -100);
		Card schoolFee = new Card("Smallville School fees. Pay $150.", -150);
		Card consultancy = new Card("Receive $25 Justice League Damage Relief.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card contestWinner = new Card("You have won second prize in a Justice League Costume Contest. Collect $10.", 10);
		Card wayneFoundation = new Card("You receive $100 from The Wayne Foundation.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(jlInsurance);
		deck.add(saleOfStock);
		deck.add(outOfJail);
		deck.add(goToJail);
		deck.add(operaNight);
		deck.add(holidayFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(consultancy);
		deck.add(repairFee);
		deck.add(contestWinner);
		deck.add(wayneFoundation);
	}
	
	/**
	 * Purpose: Tester method to have a constant deck for Atlantic City.
	 * This is the same as the setAtlanticDeck except it does not
	 * shuffle the deck so that each card can be tested when
	 * calling the drawCard method.
	 * Author: Sophia Harms
	 */
	public void setTestDeck() {
		deck.removeAll(deck);
		// create cards for the deck
		// 0 because landing on Go should add 200 to bank account
		Card advanceToGo = new Card("Advance to Go", 0);
		Card bankError = new Card("Bank error in your favor. Collect $200.", 200);
		Card doctorFee = new Card("Doctor's fee. Pay $50.", -50);
		Card saleOfStock = new Card("From sale of stock, you get $50,", 50);
		Card outOfJail = new Card("Get out of Jail Free.", 0);
		Card goToJail = new Card("Go to jail. Do not pass Go, Do not collect $200.", 0);
		Card operaNight = new Card("Grand Opera Night. Collect $50 from every player for opening night seats.", 50);
		Card holidayFund = new Card("Holiday Fund matures. Receive $100.", 100);
		Card taxRefund = new Card("Income tax refund. Collect $20.", 20);
		Card birthday = new Card("It's your birthday. Collect $10 from every player.", 10);
		Card lifeInsurance = new Card("Life insurance matures. Collect $100.", 100);
		Card hospitalFee = new Card("Hospital Fees. Pay $100.", -100);
		Card schoolFee = new Card("School fees. Pay $150.", -150);
		Card consultancy = new Card("Receive $25 consultancy fee.", 25);
		Card repairFee = new Card("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0);
		Card beautyWinner = new Card("You have won second prize in a beauty contest. Collect $10.", 10);
		Card inherit = new Card("You inherit $100.", 100);
		
		// add Cards to Array List
		deck.add(advanceToGo);
		deck.add(bankError);
		deck.add(doctorFee);
		deck.add(saleOfStock);
		deck.add(outOfJail);
		deck.add(goToJail);
		deck.add(operaNight);
		deck.add(holidayFund);
		deck.add(taxRefund);
		deck.add(birthday);
		deck.add(lifeInsurance);
		deck.add(hospitalFee);
		deck.add(schoolFee);
		deck.add(consultancy);
		deck.add(repairFee);
		deck.add(beautyWinner);
		deck.add(inherit);
	}
	
	/**
	 * Purpose: Returns the size of the deck.
	 * This is for testing to make sure that when the
	 * deck shuffles/resets, it has the right number of 
	 * cards.
	 * @return int: the number of cards in the deck.
	 * Author: Sophia Harms
	 */
	public int getSize() {
		return deck.size();
	}
	
	/**
	 * Purpose: Simulates drawing a card from the deck.
	 * The top card is drawn from the deck and the effect is applied to the player's balance.
	 * If the player needs access to a card later, it is added to their held cards.
	 * If it is special card that affects everyone's balance, it will have a special if
	 * statement. The card is then removed from the deck to simulate placing it in the discard
	 * pile.
	 * @param drawer: Player who is drawing a card.
	 * @param playerCount: int of the number of Players in the game.
	 * @param players: Array of all the Player objects in the game.
	 * @return the card that is drawn so the message can be shown to the player
	 * Author: Sophia Harms
	 */
	public Card drawCard(Player drawer, int playerCount, Player[] players) {
		// If the deck is empty, it is reset
		if(deck.isEmpty()) {
			if(theme == "Marvel Comics") {
				setMarvelDeck();
			} else if (theme == "DC Comics") {
				setDCDeck();
			} else {
				setAtlanticDeck();
			}
			
		}
		Card drew = deck.get(0);
		// Special case if repairFee Card is drawn
		if(drew.getMessage().equals("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.")) {
			int amount = drawer.getHouses() * 40 + drawer.getHotels()* 115;
			drawer.adjustBalance(-amount);
		// Special case if operaNight Card is drawn from Atlantic Deck
		} else if(drew.getMessage().equals("Grand Opera Night. Collect $50 from every player for opening night seats.")) {
			drawer.adjustBalance(50 * (playerCount));
			for(int i = 0; i < players.length; i++) {
				players[i].adjustBalance(-50);
			}
		// Special case if operaNight Card is drawn from DC Deck
		} else if(drew.getMessage().equals("Gotham City Opera House Night. Collect $50 from every player for opening night seats. Make sure to wear your pearls!")) {
			drawer.adjustBalance(50 * (playerCount));
			for(int i = 0; i < players.length; i++) {
				players[i].adjustBalance(-50);
			}
		// Special case if rogersNight Card is drawn from Marvel Deck
		} else if(drew.getMessage().equals("Rogers: The Musical Night. Collect $50 from every player for opening night seats.")) {
			drawer.adjustBalance(50 * (playerCount));
			for(int i = 0; i < players.length; i++) {
				players[i].adjustBalance(-50);
			}
		// Special case if birthday Card is drawn
		} else if(drew.getMessage().equals("It's your birthday. Collect $10 from every player.")) {
			drawer.adjustBalance(10 * (playerCount));
			for(int i = 0; i < players.length; i++) {
				players[i].adjustBalance(-10);
			}
		// Special case if outOfJail is drawn from Atlantic City
		} else if(drew.getMessage().equals("Get out of Jail Free.")) {
			drawer.addCard(drew);
		// Special case if outOfJail is drawn from DC Deck
		} else if(drew.getMessage().equals("Get out of Arkham Asylum Free. Courtesy of Joker :)")) {
			drawer.addCard(drew);
		// Special case if outOfJail is drawn from Marvel Deck
		} else if(drew.getMessage().equals("Get out of the Raft Free.")) {
			drawer.addCard(drew);
		}
		else {
			drawer.adjustBalance(drew.getEffect());
		}
		deck.remove(0);
		return drew;
	}
	
	/**
	 * Purpose: Shuffles Community Chest Deck.
	 * The method loops over the cards list and randomly picks 
	 * an index. The card at that index is moved to the bottom of the 
	 * deck. This will simulate shuffling the deck into a random order.
	 * 20 was chosen arbitrarily as the number of times to pick a random card.
	 * Author: Sophia Harms
	 */
	private void shuffleDeck() {
		Random random = new Random();
		for(int i = 0; i < 20; i++) { // loops to reset each card randomly
			int randCardIndex = random.nextInt(deck.size());
			Card card = deck.get(randCardIndex);
			deck.remove(randCardIndex);
			deck.add(card);
		}
	}
}