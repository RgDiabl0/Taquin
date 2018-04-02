package view.gui;

import model.Case;

import javax.swing.*;
import java.awt.image.BufferedImage;

class CaseGUI extends JButton {
	private Case aCase;

	CaseGUI(Case aCase, BufferedImage icon) {
		this.aCase = aCase;

		setEnabled(false);
		setBorderPainted(false);
		setFocusPainted(false);

		if (aCase.getId().equals("X"))
			setContentAreaFilled(false);
		else {
			setIcon(new ImageIcon(icon));
			setDisabledIcon(getIcon());
		}
	}

	Case getaCase() {
		return aCase;
	}
}
