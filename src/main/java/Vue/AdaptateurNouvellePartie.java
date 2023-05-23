package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurNouvellePartie implements ActionListener {

	CollecteurEvenements control;

	AdaptateurNouvellePartie(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ComposantPanneauNouvellePartie nouvelle_partie = new ComposantPanneauNouvellePartie();
		Object[] inputs = {
				nouvelle_partie
		};
		Object[] options = {
				"Lancer la partie",
				"Annuler"
		};
		int option = JOptionPane.showOptionDialog(
				null,
				inputs,
				"Nouvelle Partie",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				1
		);
		if (option == 0) {
			int nbJoueurs = 0;
			if (nouvelle_partie.joueur1.getSelectedIndex() != 0) nbJoueurs++;
			if (nouvelle_partie.joueur2.getSelectedIndex() != 0) nbJoueurs++;
			if (nouvelle_partie.joueur3.getSelectedIndex() != 0) nbJoueurs++;
			if (nouvelle_partie.joueur4.getSelectedIndex() != 0) nbJoueurs++;
			if (nbJoueurs >= 2)
				control.nouvellePartie(
						nouvelle_partie.joueur1.getSelectedIndex(),
						nouvelle_partie.joueur2.getSelectedIndex(),
						nouvelle_partie.joueur3.getSelectedIndex(),
						nouvelle_partie.joueur4.getSelectedIndex()
				);
		}
	}
}
