package fr.enigmacrew.golfcard;

public class GameAction {
    public enum Kind {
        TURN,
        DRAW,
        TRASH
    }

    public Kind kind;
    public int targetCard;

    public GameAction(Kind kind, int targetCard) {
        /*
         * Set targetCard to -1 for trash
         */
        this.kind = kind;
        this.targetCard = targetCard;
    }
}
