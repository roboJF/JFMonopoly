package edu.towson.cis.cosc442.project1.monopoly;

public abstract class Cell {
	private boolean available = true;
	private String name;
	protected Player theOwner;

	/**
	 * Returns the name of this Cell.
	 * @return the name of the Cell
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the player who owns this Cell.
	 * @return the owner Player of the Cell
	 */
	public Player getTheOwner() {
		return theOwner;
	}
	
	/**
	 * Returns the price of this Cell, defaulting to 0.
	 * @return the price of the Cell as an integer
	 */
	public int getPrice() {
		return 0;
	}

	/**
	 * Indicates whether this Cell is currently available.
	 * @return true if the Cell is available; false otherwise
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * Performs the action associated with this Cell when played.
	 */
	public abstract void playAction();

	/**
	 * Sets the availability status of this Cell.
	 * @param available true to make the Cell available; false to make it unavailable
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	/**
	 * Sets the name of this Cell.
	 * @param name the new name to assign to the Cell
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Assigns an owner to this Cell.
	 * @param owner the Player to set as owner of the Cell
	 */
	public void setTheOwner(Player owner) {
		this.theOwner = owner;
	}
    
    /**
     * Returns a string representation of this Cell, which is its name.
     * @return the name of the Cell as a String
     */
    public String toString() {
        return name;
    }
}
