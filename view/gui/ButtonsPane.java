package view.gui;

import javax.swing.*;

class ButtonsPane extends JPanel {
	private final JButton startButton;
	private final JButton resetButton;

	ButtonsPane(BoardPane boardPane) {
		startButton = new JButton("Start");
		resetButton = new JButton("Reset");
		resetButton.setEnabled(false);

		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			resetButton.setEnabled(true);

			boardPane.setPlaying(true);
			boardPane.setFocusable(true);
			boardPane.requestFocus();
			boardPane.shuffle();
		});

		resetButton.addActionListener(e -> {
			startButton.setEnabled(true);
			resetButton.setEnabled(false);

			boardPane.setPlaying(false);
			boardPane.setFocusable(false);
			boardPane.resetBoard();
		});

		add(startButton);
		add(resetButton);
	}

	JButton getStartButton() {
		return startButton;
	}

	JButton getResetButton() {
		return resetButton;
	}
}
