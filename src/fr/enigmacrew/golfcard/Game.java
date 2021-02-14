package fr.enigmacrew.golfcard;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    public ArrayList<GameCard> cardStack = new ArrayList<>();
    public ArrayList<GameCard> cardTrash = new ArrayList<>();
    public ArrayList<GameCard> p1 = new ArrayList<>();
    public ArrayList<GameCard> p2 = new ArrayList<>();

    private int nCards;
    private boolean p1Turn;

    public Game(int nCards, boolean p1Turn) {
        this.nCards = nCards;
        this.p1Turn = p1Turn;

        generateCards();
    }

    private void generateCards() {
        /*
         * Fill randomly cardStack, cardTrash, p1, p2
         */

        String[] ids = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "J",
            "Q",
            "K",
        };

        GameCard.Color[] colors = {
            GameCard.Color.CLUB,
            GameCard.Color.SPADE,
            GameCard.Color.HEART,
            GameCard.Color.DIAMOND
        };

        // Generate all cards
        for (String id : ids)
            for (GameCard.Color color : colors)
                cardStack.add(new GameCard(id, color, false));

        // Shuffle and distribute
        Collections.shuffle(cardStack);
        ArrayList<GameCard> players = new ArrayList<GameCard>(
                cardStack.subList(cardStack.size() - nCards * 2, cardStack.size()));

        p1 = new ArrayList<GameCard>(players.subList(0, nCards));
        p2 = new ArrayList<GameCard>(players.subList(nCards, 2 * nCards));
        cardTrash.clear();

        cardStack = new ArrayList<GameCard>(
                cardStack.subList(0, cardStack.size() - nCards * 2));
    }
}
