package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Iterator;


public class GameMaster {

	private static GameMaster gameMaster;
	static final public int MAX_PLAYER = 8;	
	private Die[] dice;
	private GameBoard gameBoard;
	private MonopolyGUI gui;
	private int initAmountOfMoney;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int turn = 0;
	private int utilDiceRoll;
	private boolean testMode;

	/**
	 * Returns the singleton instance of the GameMaster class, creating it if necessary.
	 * @return the singleton GameMaster instance
	 */
	public static GameMaster instance() {
		if(gameMaster == null) {
			gameMaster = new GameMaster();
		}
		return gameMaster;
	}

	/**
	 * Constructs a new GameMaster with default initial money and two dice.
	 */
	public GameMaster() {
		initAmountOfMoney = 1500;
		dice = new Die[]{new Die(), new Die()};
	}

    /**
     * Handles the event when the 'Buy House' button is clicked by showing the buy house dialog for the current player.
     */
    public void btnBuyHouseClicked() {
        gui.showBuyHouseDialog(getCurrentPlayer());
    }

    /**
     * Handles the event when the 'Draw Card' button is clicked by drawing a Chance or Community Chest card and applying its action.
     * @return the drawn Card object
     */
    public Card btnDrawCardClicked() {
        gui.setDrawCardEnabled(false);
        CardCell cell = (CardCell)getCurrentPlayer().getPosition();
        Card card = null;
        if(cell.getType() == Card.TYPE_CC) {
            card = getGameBoard().drawCCCard();
            card.applyAction();
        } else {
            card = getGameBoard().drawChanceCard();
            card.applyAction();
        }
        gui.setEndTurnEnabled(true);
        return card;
    }

    /**
     * Handles the event when the 'End Turn' button is clicked by processing the current player's turn end and updating the GUI accordingly.
     */
    public void btnEndTurnClicked() {
		setAllButtonEnabled(false);
		getCurrentPlayer().getPosition().playAction();
		if(getCurrentPlayer().isBankrupt()) {
			gui.setBuyHouseEnabled(false);
			gui.setDrawCardEnabled(false);
			gui.setEndTurnEnabled(false);
			gui.setGetOutOfJailEnabled(false);
			gui.setPurchasePropertyEnabled(false);
			gui.setRollDiceEnabled(false);
			gui.setTradeEnabled(getCurrentPlayerIndex(),false);
			updateGUI();
		}
		else {
			switchTurn();
			updateGUI();
		}
    }

    /**
     * Handles the event when the 'Get Out Of Jail' button is clicked by attempting to get the current player out of jail and updating the GUI.
     */
    public void btnGetOutOfJailClicked() {
		getCurrentPlayer().getOutOfJail();
		if(getCurrentPlayer().isBankrupt()) {
			gui.setBuyHouseEnabled(false);
			gui.setDrawCardEnabled(false);
			gui.setEndTurnEnabled(false);
			gui.setGetOutOfJailEnabled(false);
			gui.setPurchasePropertyEnabled(false);
			gui.setRollDiceEnabled(false);
			gui.setTradeEnabled(getCurrentPlayerIndex(),false);
		}
		else {
			gui.setRollDiceEnabled(true);
			gui.setBuyHouseEnabled(getCurrentPlayer().canBuyHouse());
			gui.setGetOutOfJailEnabled(getCurrentPlayer().isInJail());
		}
    }

    /**
     * Handles the event when the 'Purchase Property' button is clicked by having the current player purchase the property and updating the GUI.
     */
    public void btnPurchasePropertyClicked() {
        Player player = getCurrentPlayer();
		player.purchase();
		gui.setPurchasePropertyEnabled(false);
		updateGUI();
    }
    
    /**
     * Handles the event when the 'Roll Dice' button is clicked by rolling dice, moving the current player, and updating the GUI.
     */
    public void btnRollDiceClicked() {
		int[] rolls = rollDice();
		if((rolls[0]+rolls[1]) > 0) {
			Player player = getCurrentPlayer();
			gui.setRollDiceEnabled(false);
			StringBuffer msg = new StringBuffer();
			msg.append(player.getName())
					.append(", you rolled ")
					.append(rolls[0])
					.append(" and ")
					.append(rolls[1]);
			gui.showMessage(msg.toString());
			movePlayer(player, rolls[0] + rolls[1]);
			gui.setBuyHouseEnabled(false);
		}
    }

    /**
     * Handles the event when the 'Trade' button is clicked by opening trade dialogs and completing the trade if accepted.
     */
    public void btnTradeClicked() {
        TradeDialog dialog = gui.openTradeDialog();
        TradeDeal deal = dialog.getTradeDeal();
        if(deal != null) {
            RespondDialog rDialog = gui.openRespondDialog(deal);
            if(rDialog.getResponse()) {
                completeTrade(deal);
                updateGUI();
            }
        }
    }

    /**
     * Completes a property trade deal between the specified seller and the current player.
     * @param deal the trade deal details including seller, property, and amount
     */
    public void completeTrade(TradeDeal deal) {
        Player seller = getPlayer(deal.getPlayerIndex());
        Cell property = gameBoard.queryCell(deal.getPropertyName());
        seller.sellProperty(property, deal.getAmount());
        getCurrentPlayer().buyProperty(property, deal.getAmount());
    }

    /**
     * Draws and returns a Community Chest card from the game board.
     * @return the drawn Community Chest card
     */
    public Card drawCCCard() {
        return gameBoard.drawCCCard();
    }

    /**
     * Draws and returns a Chance card from the game board.
     * @return the drawn Chance card
     */
    public Card drawChanceCard() {
        return gameBoard.drawChanceCard();
    }

	
	/**
	 * Returns the player whose turn it currently is.
	 * @return the current Player
	 */
	public Player getCurrentPlayer() {
		return getPlayer(turn);
	}
    
    /**
     * Returns the index of the current player whose turn it is.
     * @return the index of the current player
     */
    public int getCurrentPlayerIndex() {
        return turn;
    }

	/**
	 * Returns the game board used in the game.
	 * @return the GameBoard instance
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}

    /**
     * Returns the GUI interface managing the game display and controls.
     * @return the MonopolyGUI instance
     */
    public MonopolyGUI getGUI() {
        return gui;
    }

	/**
	 * Returns the initial amount of money assigned to each player at game start.
	 * @return the initial money amount
	 */
	public int getInitAmountOfMoney() {
		return initAmountOfMoney;
	}
	
	/**
	 * Returns the current number of players in the game.
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}

    /**
     * Returns the number of other players available for selling properties to the current player.
     * @return the number of potential sellers (players minus one)
     */
    public int getNumberOfSellers() {
        return players.size() - 1;
    }

	/**
	 * Returns the player at the specified index.
	 * @param index the index of the player to retrieve
	 * @return the Player at the given index
	 */
	public Player getPlayer(int index) {
		return (Player)players.get(index);
	}
	
	/**
	 * Returns the index of the specified player in the player list.
	 * @param player the Player whose index is to be found
	 * @return the index of the given player or -1 if not found
	 */
	public int getPlayerIndex(Player player) {
		return players.indexOf(player);
	}

    /**
     * Returns a list of all players except the current player, who can act as property sellers.
     * @return a list of Player objects excluding the current player
     */
    public ArrayList<Player> getSellerList() {
        ArrayList<Player> sellers = new ArrayList<Player>();
        for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
            Player player = (Player) iter.next();
            if(player != getCurrentPlayer()) sellers.add(player);
        }
        return sellers;
    }

	/**
	 * Returns the current turn index representing whose turn it is.
	 * @return the current turn index
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Returns the utility dice roll value used for specific game mechanics.
	 * @return the utility dice roll value
	 */
	public int getUtilDiceRoll() {
		return this.utilDiceRoll;
	}

	/**
	 * Moves the player at the specified index by the given dice value, updating the game state accordingly.
	 * @param playerIndex the index of the player to move
	 * @param diceValue the dice roll total to move the player by
	 */
	public void movePlayer(int playerIndex, int diceValue) {
		Player player = (Player)players.get(playerIndex);
		movePlayer(player, diceValue);
	}
	
	/**
	 * Moves the specified player by the given dice value, updating position, money for passing 'Go', and GUI accordingly.
	 * @param player the Player to move
	 * @param diceValue the dice roll total to move the player by
	 */
	public void movePlayer(Player player, int diceValue) {
		Cell currentPosition = player.getPosition();
		int positionIndex = gameBoard.queryCellIndex(currentPosition.getName());
		int newIndex = (positionIndex+diceValue)%gameBoard.getCellNumber();
		if(newIndex <= positionIndex || diceValue > gameBoard.getCellNumber()) {
			player.setMoney(player.getMoney() + 200);
		}
		player.setPosition(gameBoard.getCell(newIndex));
		gui.movePlayer(getPlayerIndex(player), positionIndex, newIndex);
		playerMoved(player);
		updateGUI();
	}

	/**
	 * Processes actions after a player has moved, enabling GUI controls as appropriate based on the new position.
	 * @param player the Player who has completed movement
	 */
	public void playerMoved(Player player) {
		Cell cell = player.getPosition();
		int playerIndex = getPlayerIndex(player);
		if(cell instanceof CardCell) {
		    gui.setDrawCardEnabled(true);
		} else{
			enablePurchaseIfAvailable(player, cell, playerIndex);	
			gui.enableEndTurnBtn(playerIndex);
		}
        gui.setTradeEnabled(turn, false);
	}

	/**
	 * Enables the purchase button if the player can afford and the cell is available for purchase.
	 * @param player the Player who may purchase
	 * @param cell the Cell that may be purchased
	 * @param playerIndex the index of the player
	 */
	private void enablePurchaseIfAvailable(Player player, Cell cell, int playerIndex) {
		if(cell.isAvailable()) {
			int price = cell.getPrice();
			if(price <= player.getMoney() && price > 0) {
				gui.enablePurchaseBtn(playerIndex);
			}
		}
	}

	/**
	 * Resets the game state, repositioning all players to start and clearing game board cards.
	 */
	public void reset() {
		for(int i = 0; i < getNumberOfPlayers(); i++){
			Player player = (Player)players.get(i);
			player.setPosition(gameBoard.getCell(0));
		}
		if(gameBoard != null) gameBoard.removeCards();
		turn = 0;
	}
	
	/**
	 * Rolls the dice using either test mode values or random rolls depending on the current mode.
	 * @return an array containing two dice roll values
	 */
	public int[] rollDice() {
		if(testMode) {
			return gui.getDiceRoll();
		}
		else {
			return new int[]{
					dice[0].getRoll(),
					dice[1].getRoll()
			};
		}
	}
	
	/**
	 * Sends the specified player to jail, updating their position and GUI accordingly.
	 * @param player the Player to send to jail
	 */
	public void sendToJail(Player player) {
	    int oldPosition = gameBoard.queryCellIndex(getCurrentPlayer().getPosition().getName());
		player.setPosition(gameBoard.queryCell("Jail"));
		player.setInJail(true);
		int jailIndex = gameBoard.queryCellIndex("Jail");
		gui.movePlayer(
		        getPlayerIndex(player),
		        oldPosition,
		        jailIndex);
	}
    
	/**
	 * Enables or disables all main GUI buttons based on the specified flag.
	 * @param enabled true to enable buttons; false to disable
	 */
	private void setAllButtonEnabled(boolean enabled) {
		gui.setRollDiceEnabled(enabled);
		gui.setPurchasePropertyEnabled(enabled);
		gui.setEndTurnEnabled(enabled);
        gui.setTradeEnabled(turn, enabled);
        gui.setBuyHouseEnabled(enabled);
        gui.setDrawCardEnabled(enabled);
        gui.setGetOutOfJailEnabled(enabled);
	}

	/**
	 * Sets the game board to be used during the game.
	 * @param board the GameBoard instance to set
	 */
	public void setGameBoard(GameBoard board) {
		this.gameBoard = board;
	}
	
	/**
	 * Sets the GUI interface for managing game display and controls.
	 * @param gui the MonopolyGUI instance to set
	 */
	public void setGUI(MonopolyGUI gui) {
		this.gui = gui;
	}

	/**
	 * Sets the initial amount of money each player receives at game start.
	 * @param money the initial money amount to assign
	 */
	public void setInitAmountOfMoney(int money) {
		this.initAmountOfMoney = money;
	}

	/**
	 * Sets the number of players in the game and initializes their starting money and status.
	 * @param number the number of players to create
	 */
	public void setNumberOfPlayers(int number) {
		players.clear();
		for(int i =0;i<number;i++) {
			Player player = new Player();
			player.setMoney(initAmountOfMoney);
			players.add(player);
		}
	}

	/**
	 * Sets the utility dice roll value used for specific game functions.
	 * @param diceRoll the dice roll value to store
	 */
	public void setUtilDiceRoll(int diceRoll) {
		this.utilDiceRoll = diceRoll;
	}
	
	/**
	 * Starts the game by initializing GUI and enabling the first player's turn with trading enabled.
	 */
	public void startGame() {
		gui.startGame();
		gui.enablePlayerTurn(0);
        gui.setTradeEnabled(0, true);
	}

	/**
	 * Advances the turn to the next player, enabling GUI controls appropriately depending on jail status.
	 */
	public void switchTurn() {
		turn = (turn + 1) % getNumberOfPlayers();
		if(!getCurrentPlayer().isInJail()) {
			gui.enablePlayerTurn(turn);
			gui.setBuyHouseEnabled(getCurrentPlayer().canBuyHouse());
            gui.setTradeEnabled(turn, true);
		}
		else {
			gui.setGetOutOfJailEnabled(true);
		}
	}
	
	/**
	 * Updates the GUI to reflect the current game state.
	 */
	public void updateGUI() {
		gui.update();
	}

	/**
	 * Triggers a dialog to perform a utility dice roll and stores the result.
	 */
	public void utilRollDice() {
		this.utilDiceRoll = gui.showUtilDiceRoll();
	}

	/**
	 * Enables or disables test mode, affecting how dice rolls are generated.
	 * @param b true to enable test mode; false to disable
	 */
	public void setTestMode(boolean b) {
		testMode = b;
	}
}
