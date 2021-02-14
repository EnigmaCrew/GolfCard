package fr.enigmacrew.golfcard.game;

public class GameCard {
    public enum Color {
        SPADE,
        HEART,
        DIAMOND,
        CLUB
    }

    public int value;
    // 1 to 10, JQK
    public String id;
    public Color color;
    public boolean visible;

    public GameCard(String id, Color color, boolean visible) {
        this.id = id;
        this.color = color;
        this.visible = visible;

        switch (id) {
            case "J":
                value = 10;
                break;
            case "Q":
                value = 10;
                break;
            case "K":
                value = 0;
                break;
            default:
                value = Integer.parseInt(id);
                break;
        }
    }
}
