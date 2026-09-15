package edu.towson.cis.cosc442.project1.monopoly;

public class GameBoardFull extends GameBoard {

    private static final String PURPLE = "purple";
    private static final String AQUA = "aqua";
    private static final String FUCHSIA = "fuchsia";
    private static final String MAROON = "maroon";
    private static final String RED = "red";
    private static final String YELLOW = "yellow";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    /**
     * Constructs a full game board with all standard Monopoly cells and cards initialized.
     */
    public GameBoardFull() {
        super();

        CardCell cc1 = new CardCell(Card.TYPE_CC, "Community Chest 1");
        CardCell cc2 = new CardCell(Card.TYPE_CC, "Community Chest 2");
        CardCell cc3 = new CardCell(Card.TYPE_CC, "Community Chest 3");
        CardCell c1 = new CardCell(Card.TYPE_CHANCE, "Chance 1");
        CardCell c2 = new CardCell(Card.TYPE_CHANCE, "Chance 2");
        CardCell c3 = new CardCell(Card.TYPE_CHANCE, "Chance 3");
        FreeParkingCell fp = new FreeParkingCell();
        GoToJailCell goToJail = new GoToJailCell();
        JailCell jail = new JailCell();
        RailRoadCell rr1 = new RailRoadCell();
        RailRoadCell rr2 = new RailRoadCell();
        RailRoadCell rr3 = new RailRoadCell();
        RailRoadCell rr4 = new RailRoadCell();
        UtilityCell u1 = new UtilityCell();
        UtilityCell u2 = new UtilityCell();


        PropertyCell dp1 = createPropertyCell(60, PURPLE, 50, "Mediterranean Avenue", 2);
        PropertyCell dp2 = createPropertyCell(60, PURPLE, 50, "Baltic Avenue", 4);
        PropertyCell dp3 = createPropertyCell(60, PURPLE, 50, "Sarah Avenue", 4);

        PropertyCell lb1 = createPropertyCell(100, AQUA, 50, "Oriental Avenue", 6);
        PropertyCell lb2 = createPropertyCell(100, AQUA, 50, "Vermont Avenue", 6);
        PropertyCell lb3 = createPropertyCell(120, AQUA, 50, "Connecticut Avenue", 8);

        PropertyCell p1 = createPropertyCell(140, FUCHSIA, 100, "St. Charles Place", 10);
        PropertyCell p2 = createPropertyCell(140, FUCHSIA, 100, "States Avenue", 10);
        PropertyCell p3 = createPropertyCell(160, FUCHSIA, 100, "Virginia Avenue", 12);

        PropertyCell o1 = createPropertyCell( 180, MAROON, 100, "St. James Avenue", 14);
        PropertyCell o2 = createPropertyCell(180, MAROON, 100, "Tennessee Avenue", 14);
        PropertyCell o3 = createPropertyCell(200, MAROON, 100, "New York Avenue", 16);

        PropertyCell r1 = createPropertyCell(220, RED, 150, "Kentucky Avenue", 18);
        PropertyCell r2 = createPropertyCell(220, RED, 150, "Indiana Avenue", 18);
        PropertyCell r3 = createPropertyCell(240, RED, 150, "Illinois Avenue", 20);

        PropertyCell y1 = createPropertyCell(260, YELLOW, 150, "Atlantic Avenue", 22);
        PropertyCell y2 = createPropertyCell(260, YELLOW, 150, "Ventnor Avenue", 22);
        PropertyCell y3 = createPropertyCell(280, YELLOW, 150, "Marvin Gardens", 24);

        PropertyCell g1 = createPropertyCell(300, GREEN, 200, "Pacific Avenue", 26);
        PropertyCell g2 = createPropertyCell(300, GREEN, 200, "North Carolina Avenue", 26);
        PropertyCell g3 = createPropertyCell(320, GREEN, 200, "Pennsylvania Avenue", 28);

        PropertyCell db1 = createPropertyCell(350, BLUE, 200, "Park Place", 35);
        PropertyCell db2 = createPropertyCell(350, BLUE, 200, "Dright Place", 35);
        PropertyCell db3 = createPropertyCell(400, BLUE, 200, "Boardwalk", 50);


        RailRoadCell.setBaseRent(50);
        RailRoadCell.setPrice(200);

        rr1.setName("Reading Railroad");
        rr2.setName("Pennsylvania Railroad");
        rr3.setName("B. & O. RailRoad");
        rr4.setName("Short Line");

        UtilityCell.setPrice(150);

        u1.setName("Electric Company");
        u2.setName("Water Works");

        addCell(dp1);
        addCell(cc1);
        addCell(dp2);
        addCell(dp3);
        addCell(rr1);
        addCell(lb1);
        addCell(c1);
        addCell(lb2);
        addCell(lb3);
        addCell(jail);
        addCell(p1);
        addCell(u1);
        addCell(p2);
        addCell(p3);
        addCell(rr2);
        addCell(o1);
        addCell(cc2);
        addCell(o2);
        addCell(o3);
        addCell(fp);
        addCell(r1);
        addCell(c2);
        addCell(r2);
        addCell(r3);
        addCell(rr3);
        addCell(y1);
        addCell(y2);
        addCell(u2);
        addCell(y3);
        addCell(goToJail);
        addCell(g1);
        addCell(g2);
        addCell(cc3);
        addCell(g3);
        addCell(rr4);
        addCell(c3);
        addCell(db1);
        addCell(db2);
        addCell(db3);

        addCard(new MoneyCard("Win $50", 50, Card.TYPE_CC));
        addCard(new MoneyCard("Win $20", 20, Card.TYPE_CC));
        addCard(new MoneyCard("Win $10", 10, Card.TYPE_CC));
        addCard(new MoneyCard("Lose $100", -100, Card.TYPE_CC));
        addCard(new MoneyCard("Lose $50", -50, Card.TYPE_CC));
        addCard(new JailCard(Card.TYPE_CC));
        addCard(new MovePlayerCard("St. Charles Place", Card.TYPE_CC));
        addCard(new MovePlayerCard("Boardwalk", Card.TYPE_CC));

        addCard(new MoneyCard("Win $50", 50, Card.TYPE_CHANCE));
        addCard(new MoneyCard("Win $20", 20, Card.TYPE_CHANCE));
        addCard(new MoneyCard("Win $10", 10, Card.TYPE_CHANCE));
        addCard(new MoneyCard("Lose $100", -100, Card.TYPE_CHANCE));
        addCard(new MoneyCard("Lose $50", -50, Card.TYPE_CHANCE));
        addCard(new JailCard(Card.TYPE_CHANCE));
        addCard(new MovePlayerCard("Illinois Avenue", Card.TYPE_CHANCE));
    }

    /**
     * Creates and initializes a property cell with specified price, color group, house price, name, and rent.
     * @param price the purchase price of the property
     * @param colorGroup the color group the property belongs to
     * @param housePrice the cost to build a house on the property
     * @param name the name of the property
     * @param rent the rent charged for landing on the property
     * @return the initialized PropertyCell object
     */
    private PropertyCell createPropertyCell(int price, String colorGroup, int housePrice, String name, int rent) {
        PropertyCell property = new PropertyCell();

        property.setPrice(price);
        property.setColorGroup(colorGroup);
        property.setHousePrice(housePrice);
        property.setName(name);
        property.setRent(rent);

        return property;
    }
}
