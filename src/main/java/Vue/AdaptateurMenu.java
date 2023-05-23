package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurMenu implements ActionListener {

	CollecteurEvenements control;

	AdaptateurMenu(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ComposantPanneauMenu menu = new ComposantPanneauMenu(control);
		Object[] inputs = {menu};
		Object[] options = {"Retour au jeu"};
		JOptionPane.showOptionDialog(
				null,
				inputs,
				"Menu",
				0,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				0
		);
	}
}
