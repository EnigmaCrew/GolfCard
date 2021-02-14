package fr.enigmacrew.golfcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    public enum Phase {
        // Players have to turn cards
        START,
        // Main gameplay
        MAIN,
        // The game has ended
        END
    }

    public ArrayList<GameCard> cardStack = new ArrayList<>();
    public ArrayList<GameCard> cardTrash = new ArrayList<>();
    public ArrayList<GameCard> p1 = new ArrayList<>();
    public ArrayList<GameCard> p2 = new ArrayList<>();

    public boolean p1Turn;
    public int turnId;
    private int nCards;
    private Phase phase;

    public Game(int nCards, boolean p1Turn) {
        this.nCards = nCards;
        this.p1Turn = p1Turn;

        reset();
    }

    public boolean step(GameAction action) {
        /*
         * Play a game step (turn).
         * Return whether the game is ended.
         */
        ArrayList<GameCard> player = p1Turn ? p1 : p2;

        switch (action.kind) {
            case DRAW:
            {
                // Pop
                GameCard card = cardStack.remove(cardStack.size() - 1);
                card.visible = true;

                if (action.targetCard == -1) {
                    // Trash
                    cardTrash.add(card);
                } else {
                    GameCard toTrash = player.get(action.targetCard);
                    toTrash.visible = true;

                    // Replace the target card (put the old card in the trash)
                    cardTrash.add(toTrash);
                    player.set(action.targetCard, card);
                }

                break;
            }
            case TRASH:
            {
                // Pop and replace the target card (put the old card in the trash)
                GameCard card = cardTrash.remove(cardTrash.size() - 1);
                GameCard toTrash = player.get(action.targetCard);

                card.visible = true;
                toTrash.visible = true;

                cardTrash.add(toTrash);
                player.set(action.targetCard, card);

                break;
            }
            case TURN:
            {
                player.get(action.targetCard).visible = true;

                break;
            }
        }

        // Check end
        boolean end = true;
        for (GameCard card : player) {
            if (!card.visible) {
                end = false;
                break;
            }
        }

        // Check deck empty
        if (cardStack.size() == 0)
            refill();

        if (end)
            phase = Phase.END;

        if (phase == Phase.START) {
            // All initial cards turned
            if (turnId >= nCards / 3 * 2 - 1) {
                phase = Phase.MAIN;
            }

            // Switch player
            if (turnId % (nCards / 3) == nCards / 3 - 1) {
                p1Turn = !p1Turn;
            }
        } else
            p1Turn = !p1Turn;

        ++turnId;

        return phase == Phase.END;
    }

    public Integer[] getScores() {
        /*
         * Fetch players' scores
         */
        return new Integer[] {
            getScore(p1),
            getScore(p2),
        };
    }

    public void reset() {
        /*
         * Reset the game state.
         * Fill randomly cardStack, cardTrash, p1, p2.
         */

        turnId = 0;
        phase = Phase.START;

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

    public void refill() {
        /*
         * Fill the deck when there is no card in it anymore
         */

        ArrayList<GameCard> tmp = cardStack;
        cardStack = cardTrash;
        cardTrash = tmp;

        Collections.shuffle(cardStack);
        for (GameCard card : cardStack)
            card.visible = false;
    }

    // TODO : Remove
    public void debug() {
        System.out.println("-----");
        System.out.print("P1 :");
        for (GameCard card : p1) {
            System.out.print(" " + card.id);
        }
        System.out.print("\nP2 :");
        for (GameCard card : p2) {
            System.out.print(" " + card.id);
        }
        System.out.println();
        if (!cardStack.isEmpty())
            System.out.print("Stack : " + cardStack.get(cardStack.size() - 1).id);
        if (!cardTrash.isEmpty())
            System.out.print(" Trash : " + cardTrash.get(cardTrash.size() - 1).id);
        System.out.println("\n-----");
    }

    private int getScore(ArrayList<GameCard> cards) {
        int score = 0;
        HashMap<String, Integer> ids = new HashMap<>();

        for (GameCard card : cards) {
            if (ids.containsKey(card.id))
                ids.put(card.id, ids.get(card.id) + 1);
            else
                ids.put(card.id, 1);
        }

        for (GameCard card : cards) {
            // There is only one card
            if (ids.get(card.id) == 1)
                score += card.value;
        }

        return score;
    }
}
