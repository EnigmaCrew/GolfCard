package fr.enigmacrew.golfcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    // TODO : Useful ?
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

    private int nCards;
    private boolean p1Turn;
    private Phase phase;

    public Game(int nCards, boolean p1Turn) {
        this.nCards = nCards;
        this.p1Turn = p1Turn;

        reset();
    }

    // TODO : Can't play with trash if empty
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

                if (action.targetCard == -1) {
                    // Trash
                    cardTrash.add(card);
                } else {
                    // Replace the target card (put the old card in the trash)
                    cardTrash.add(player.get(action.targetCard));
                    player.set(action.targetCard, card);
                }

                break;
            }
            case TRASH:
            {
                // Pop and replace the target card (put the old card in the trash)
                GameCard card = cardTrash.remove(cardStack.size() - 1);
                cardTrash.add(player.get(action.targetCard));
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

        if (end)
            phase = Phase.END;

        p1Turn = !p1Turn;

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
            ids.put(card.id, ids.get(card.id) + 1);
        }

        for (GameCard card : cards) {
            // There is only one card
            if (ids.get(card.id) == 1)
                score += card.value;
        }

        return score;
    }
}
