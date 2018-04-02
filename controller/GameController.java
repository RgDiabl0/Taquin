package controller;

import model.Board;
import model.Case;
import model.Direction;

public class GameController {
	private final Board board;

	public GameController(Board board) {
		this.board = board;
	}

	public boolean isMovable(Case aCase) {
		System.out.println(aCase);

		if (aCase.getId().equals("X"))
			return false;
		if (aCase.getCoord()[0] - 1 >= 0 && board.getCases()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getId().equals("X"))
			return true;
		else if (aCase.getCoord()[0] + 1 < board.getCases().length && board.getCases()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getId().equals("X"))
			return true;
		else if (aCase.getCoord()[1] - 1 >= 0 && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getId().equals("X"))
			return true;
		else
			return aCase.getCoord()[1] + 1 < board.getCases()[0].length && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getId().equals("X");

	}

	public void move(Case aCase) {
		if (aCase.getCoord()[0] - 1 >= 0 && board.getCases()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getId().equals("X"))
			aCase.move(Direction.UP);
		else if (aCase.getCoord()[0] + 1 < board.getCases().length && board.getCases()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getId().equals("X"))
			aCase.move(Direction.DOWN);
		else if (aCase.getCoord()[1] - 1 >= 0 && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getId().equals("X"))
			aCase.move(Direction.LEFT);
		else if (aCase.getCoord()[1] + 1 < board.getCases()[0].length && board.getCases()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getId().equals("X"))
			aCase.move(Direction.RIGHT);
	}

	public boolean isFinished() {
		boolean isFinished = false;

		for (int row = 0; row < board.getCases().length; row++)
			for (int col = 0; col < board.getCases()[row].length; col++)
				if (board.getCases()[row][col].getId().equals(String.valueOf((board.getCases().length * row) + col)))
					isFinished = true;
				else
					return false;

		return isFinished;
	}
}
