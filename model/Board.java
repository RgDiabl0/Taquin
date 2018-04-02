package model;

import utility.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board extends AbstractModel {
	private final int rows;
	private final int cols;

	private Case[][] cases;

	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;

		initBoard();
	}

	private void initBoard() {
		cases = new Case[rows][cols];

		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				cases[row][col] = new Case(this, new int[]{row, col});

		cases[rows - 1][cols - 1].setId("X");
	}

	public void shuffleBoard() {
		ArrayList<Case> caseArrayList = new ArrayList<>();

		for (Case[] row : cases)
			caseArrayList.addAll(Arrays.asList(row));

		Case emptyCase = caseArrayList.get(caseArrayList.size() - 1);
		caseArrayList.remove(emptyCase);

		Collections.shuffle(caseArrayList);

		caseArrayList.add(emptyCase);

		for (int row = 0; row < cases.length; row++)
			for (int col = 0; col < cases[0].length; col++){
				cases[row][col] = caseArrayList.get((rows * row) + col);
				caseArrayList.get((rows * row) + col).setCoord(new int[]{row, col});
			}

		notifyObservers();
	}

	public Case getCase(String id) {
		for (Case[] row : cases)
			for (Case aCase : row)
				if (aCase.getId().equals(id))
					return aCase;

		return null;
	}

	public Case[][] getCases() {
		return cases;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		for (Case[] row : cases) {
			for (Case aCase : row)
				res.append(aCase.getId());
			res.append("\n");
		}

		return String.valueOf(res);
	}
}
