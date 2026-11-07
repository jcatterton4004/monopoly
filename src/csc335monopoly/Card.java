package csc335monopoly;
/**
 * Purpose: Creates the Card class.
 * @author Sophia Harms
 * CSC 355 Team Project: Monopoly
 * This class stores all info for a Card. Each Card will have
 * a message and an effect. The message is what will be displayed
 * to the user when the draw the Card from the Community Chest.
 * The effect is how it will affect the Player's balance.
 */
public class Card {
	private String message;
	private int effect;
	/**
	 * Purpose: Constructor for Card class.
	 * In the CommunityDeck class, when a new card is created,
	 * this class will be called and passed the message and effect
	 * the card should hold.
	 * @param message: String of what the card will display to the player.
	 * @param effect: int to add/subtract from balance
	 * Author: Sophia Harms
	 */
	public Card(String message, int effect) {
		this.message = message;
		this.effect = effect;
	}
	/**
	 * Purpose: Getter method for the Card's message.
	 * This is so the message can be displayed to the player later.
	 * @return message: String of what the card does.
	 * Author: Sophia Harms
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Purpose: Getter method for the Card's effect.
	 * This is so the player's balance can be updated when they draw the Card.
	 * @return effect: int to update the Player's balance with.
	 * Author: Sophia Harms
	 */
	public int getEffect() {
		return effect;
	}
}
