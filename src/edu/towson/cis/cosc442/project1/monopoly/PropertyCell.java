package edu.towson.cis.cosc442.project1.monopoly;

public class PropertyCell extends Cell {
	private String colorGroup;
	private int housePrice;
	private int numHouses;
	private int rent;
	private int sellPrice;

	/**
	 * Gets the color group of the property for categorization.
	 * @return the color group of the property
	 */
	public String getColorGroup() {
		return colorGroup;
	}

	/**
	 * Retrieves the cost of purchasing a house on this property.
	 * @return the price of a house on this property
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Returns the number of houses currently built on the property.
	 * @return the number of houses on the property
	 */
	public int getNumHouses() {
		return numHouses;
	}
    
    /**
     * Gets the current selling price of the property.
     * @return the selling price of the property
     */
    public int getPrice() {
		return sellPrice;
	}

	/**
	 * Calculates and returns the rent amount owed for landing on the property.
	 * @return the rent to charge for this property
	 */
	public int getRent() {
		int rentToCharge = rent;
		String [] monopolies = theOwner.getMonopolies();
		rentToCharge = calculateMonopoliesRent(rentToCharge, monopolies);
		if(numHouses > 0) {
			rentToCharge = rent * (numHouses + 1);
		}
		return rentToCharge;
	}

	/**
	 * Calculates rent based on monopoly ownership and base rent value.
	 * @param rentToCharge the current rent value to potentially adjust
	 * @param monopolies an array of color groups the owner holds a monopoly in
	 * @return the adjusted rent amount considering monopolies
	 */
	private int calculateMonopoliesRent(int rentToCharge, String[] monopolies) {
		for(int i = 0; i < monopolies.length; i++) {
			if(monopolies[i].equals(colorGroup)) {
				rentToCharge = rent * 2;
			}
		}
		return rentToCharge;
	}

	/**
	 * Executes the action when a player lands on this property, including rent payment if owned.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				currentPlayer.payRentTo(theOwner, getRent());
			}
		}
	}

	/**
	 * Assigns the color group category to this property.
	 * @param colorGroup the color group to set for this property
	 */
	public void setColorGroup(String colorGroup) {
		this.colorGroup = colorGroup;
	}

	/**
	 * Sets the cost for purchasing a house on this property.
	 * @param housePrice the price to set for a house
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Sets the number of houses currently built on the property.
	 * @param numHouses the number of houses to assign
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * Sets the selling price of the property.
	 * @param sellPrice the selling price to assign to the property
	 */
	public void setPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * Defines the base rent charged for landing on this property without considering houses or monopolies.
	 * @param rent the base rent to set
	 */
	public void setRent(int rent) {
		this.rent = rent;
	}
}
