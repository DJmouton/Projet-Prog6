package Vue;

import Patterns.Observateur;

import javax.swing.*;

public class ComposantHistorique extends Box implements Observateur {

	ComposantHistorique(CollecteurEvenements control) {
		super(BoxLayout.X_AXIS);
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new AdaptateurAnnuler(control));
		add(annuler);
		JButton refaire = new JButton("Refaire");
		refaire.addActionListener(new AdaptateurRefaire(control));
		add(refaire);

		// Impossible de cliquer les boutons avant qu'un move soit joué
		if(control.etatPla())
			annuler.setEnabled(true);
		else annuler.setEnabled(false);
		if(control.etatDep())
			refaire.setEnabled(true);
		else annuler.setEnabled(false);

	}

	@Override
	public void miseAJour() {
		revalidate();
		repaint();
	}
}
