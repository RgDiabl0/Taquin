package view.console;

import controller.GameController;
import model.Board;
import model.Case;
import utility.Observer;

import java.util.Scanner;

public class Main implements Observer {
	private Board board;

	private Main(Board board) {
		this.board = board;
		board.addObserver(this);
	}

	public static void main(String[] args) {
		Board board = new Board(3, 3);
		Main main = new Main(board);
		GameController controller = new GameController(main.board);

		board.shuffleBoard();

		System.out.println(board);

		Scanner scanner = new Scanner(System.in);

		while (!controller.isFinished()) {
			System.out.println("Select case :");
			Case selectedCase;

			do {
				selectedCase = board.getCase(scanner.next());
			} while (!controller.isMovable(selectedCase));

			controller.move(selectedCase);
		}

		System.out.println("\n\n\nBoard completed successfully ! Well done !");
	}

	@Override
	public void update() {
		System.out.println("\n" + board);
	}
}
