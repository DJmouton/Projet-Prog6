package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurSauver implements ActionListener {
	CollecteurEvenements control;
	JTextField fichier;

	AdaptateurSauver(CollecteurEvenements c, JTextField f) {
		control = c;
		fichier = f;
	}

	/*
	 * Une taille a été entrée. On la convertie en entier et on envoie un évènement.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String filename = fichier.getText();
		if (filename.isEmpty())
			filename = "save";
		control.sauver(filename);
	}
}