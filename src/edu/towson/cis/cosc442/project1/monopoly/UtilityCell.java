package edu.towson.cis.cosc442.project1.monopoly;

public class UtilityCell extends Cell {

	public static final String COLOR_GROUP = "UTILITY";
	private static int PRICE;

	/**
	 * Sets the static purchase price for all UtilityCell instances.
	 * @param price the price to set for the utility property
	 */
	public static void setPrice(int price) {
		UtilityCell.PRICE = price;
	}

	/**
	 * Returns the purchase price of the utility property.
	 * @return the purchase price of the utility
	 */
	public int getPrice() {
		return UtilityCell.PRICE;
	}

	/**
	 * Calculates the rent owed based on the dice roll and number of utilities owned by the owner.
	 * @param diceRoll the result of the dice roll used to determine rent
	 * @return the calculated rent amount
	 */
	public int getRent(int diceRoll) {
		if(theOwner.numberOfUtil() == 1) {
			return diceRoll * 4;
		} else if (theOwner.numberOfUtil() >= 2) {
			return diceRoll * 10;
		}
		return 0;
	}

	/**
	 * Executes the action that occurs when a player lands on this utility cell, including rent payment if applicable.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				GameMaster.instance().utilRollDice();
				int diceRoll = GameMaster.instance().getUtilDiceRoll();
				currentPlayer.payRentTo(theOwner, getRent(diceRoll));
			}
		}
	}
}
