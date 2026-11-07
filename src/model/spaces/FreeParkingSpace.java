package model.spaces;

/**
 * Represent the Free Parking space.
 * 
 * This class represents the free parking space on the
 * board where a player might collect money if free parking
 * rewards are enabled. It has Space as its superclass.
 * 
 * @author Tyler Ohme
 */
public class FreeParkingSpace extends Space {
	
	private int rewardAmount;
	
	/**
	 * Builds a new FreeParkingSpace object.
	 * 
	 * @param name the name of this space
	 * Author: Tyler Ohme
	 */
	public FreeParkingSpace(String name) {
		super(name);
	}
	
	/**
	 * Adds fees paid to the free parking space.
	 * 
	 * If players select to enable free parking rewards,
	 * then any fees or taxes will be added to be free
	 * parking and can be collected if someone lands on
	 * free parking. This method adds an amount paid to
	 * the reward amount variable to be collects.
	 * 
	 * @param amount is the fee/tax being paid.
	 * Author: Tyler Ohme
	 */
	public void addRewards(int amount) {
		rewardAmount += amount;
	}
	
	/**
	 * Returns the amount to be collected.
	 * 
	 * If a player lands on free parking, then get to
	 * collect the money that has been put there. This
	 * method returns the amount that has been collected 
	 * there and resets the reward to 0.
	 * 
	 * @return the amount of rewards to be collected.
	 * Author: Tyler Ohme
	 */
	public int collectRewards() {
		int reward = rewardAmount;
		rewardAmount = 0;
		return reward;
	}
 }
