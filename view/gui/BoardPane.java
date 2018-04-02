package view.gui;

import controller.GameController;
import model.Board;
import model.Case;
import utility.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

class BoardPane extends JPanel implements Observer {
	private final int rows = 3;
	private final int cols = 3;

	private Board board;
	private ArrayList<CaseGUI> cases;
	private GameController controller;

	private BufferedImage bufferedImage;
	private int imageHeight;
	private int imageWidth;

	private boolean isPlaying;

	BoardPane() {
		board = new Board(rows, cols);
		cases = new ArrayList<>();
		controller = new GameController(board);
		isPlaying = false;

		setLayout(new GridLayout(rows, cols));
		setSize(new Dimension(360, 360));

		initPane();
	}

	private void initPane() {
		Image image = null;

		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/images/New-Super-Mario-Bros-Art-21-400x400.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image scaledImage = Objects.requireNonNull(image).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

		bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(scaledImage, 0, 0, null);
		graphics.dispose();

		imageHeight = bufferedImage.getHeight();
		imageWidth = bufferedImage.getWidth();

		update();
	}

	void resetBoard() {
		Case.COUNT = new AtomicInteger(0);
		board = new Board(rows, cols);
		controller = new GameController(board);

		cases.clear();

		update();
	}

	void shuffle() {
		board.shuffleBoard();

		update();
	}

	private void moveButton(CaseGUI caseGUI) {
		controller.move(caseGUI.getaCase());

		update();
	}

	void setPlaying(boolean playing) {
		isPlaying = playing;
	}

	@Override
	public void update() {
		removeAll();

		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++) {
				if (cases.size() < rows * cols) {
					if (cases.size() == (rows * cols) - 1)
						cases.add(new CaseGUI(board.getCases()[row][col], null));
					else {
						cases.add(new CaseGUI(board.getCases()[row][col], bufferedImage.getSubimage((imageWidth / cols) * col, (imageHeight / rows) * row, imageWidth / cols, imageHeight / rows)));
						cases.get(cases.size() - 1).addActionListener(e -> moveButton((CaseGUI) e.getSource()));
					}
				} else if (cases.get((row * rows) + col).getaCase() != board.getCases()[row][col])
					for (CaseGUI caseGUI : cases)
						if (caseGUI.getaCase() == board.getCases()[row][col])
							Collections.swap(cases, (row * rows) + col, cases.indexOf(caseGUI));
			}

		cases.forEach(caseGUI -> {
			if (isPlaying && controller.isMovable(caseGUI.getaCase())) {
				caseGUI.setEnabled(true);
				caseGUI.setBorderPainted(true);
				caseGUI.setFocusPainted(true);

				caseGUI.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						caseGUI.setBorder(new LineBorder(Color.GREEN, 5));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						caseGUI.setBorder(UIManager.getBorder("Button.border"));
					}
				});
			} else {
				caseGUI.setEnabled(false);
				caseGUI.setBorderPainted(false);
				caseGUI.setFocusPainted(false);
			}

			add(caseGUI);
		});

		revalidate();
		repaint();

		if (controller.isFinished())
			MainGUI.isFinished();
	}
}
