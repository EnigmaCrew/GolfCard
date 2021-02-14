package fr.enigmacrew.golfcard;

public class Main {

	/*
	 *  @authors
	 *   - Hugo Simony-Jungo
	 *   - Celian Raimbault
	 */

	public static void main(String[] args) {

		/*
		 * Start the golf game
		 */
        Game game = new Game(6, true);
		game.debug();
		game.step(new GameAction(GameAction.Kind.TURN, 1));
		game.debug();
		game.step(new GameAction(GameAction.Kind.DRAW, 2));
		game.debug();
		game.step(new GameAction(GameAction.Kind.DRAW, -1));
		game.debug();

		Integer[] scores = game.getScores();
		System.out.println("Score P1 : " + scores[0].toString());
		System.out.println("Score P2 : " + scores[1].toString());

		// Start the main frame
		// Golf golf = new Golf();
		// golf.setVisible(true);
	}
}
