package Vue;

import Patterns.Observateur;

import javax.swing.*;

public class ComposantHistorique extends Box implements Observateur {

	JButton annuler;
	JButton refaire;
	CollecteurEvenements control;

	ComposantHistorique(CollecteurEvenements c) {
		super(BoxLayout.X_AXIS);
		control = c;
		annuler = new JButton("Annuler");
		annuler.addActionListener(new AdaptateurAnnuler(control));
		add(annuler);
		refaire = new JButton("Refaire");
		refaire.addActionListener(new AdaptateurRefaire(control));
		add(refaire);

		// Impossible de cliquer les boutons avant qu'un move soit joué
		annuler.setEnabled(false);
		refaire.setEnabled(false);

	}

	@Override
	public void miseAJour() {
		annuler.setEnabled(control.peutAnnuler());
		refaire.setEnabled(control.peutRefaire());
	}
}
