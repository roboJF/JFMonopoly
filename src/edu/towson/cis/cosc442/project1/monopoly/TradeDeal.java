package edu.towson.cis.cosc442.project1.monopoly;

public class TradeDeal {
    private int amount;
    private int playerIndex;
    private String propertyName;

    /**
     * Returns the monetary amount involved in the trade deal.
     * @return the amount of money offered in the trade
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Returns the index of the player who owns the property being traded.
     * @return the player index of the property's owner
     */
    public int getPlayerIndex() {
        return playerIndex;
    }
    
    /**
     * Returns the name of the property involved in the trade deal.
     * @return the property name as a string
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * Creates and returns a formatted message describing the trade offer.
     * @return a string message outlining the trade proposal
     */
    public String makeMessage() {
        String message = GameMaster.instance().getCurrentPlayer() + 
        	" wishes to purchase " +
        	propertyName + " from " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	" for " + amount + ".  " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	", do you wish to trade your property?";
        return message;
    }
    
    /**
     * Sets the monetary amount involved in the trade deal.
     * @param amount the amount of money to set for the trade
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Sets the name of the property involved in the trade deal.
     * @param propertyName the name of the property to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * Sets the index of the player who is selling the property in the trade deal.
     * @param playerIndex the player index to set as the seller
     */
    public void setSellerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
