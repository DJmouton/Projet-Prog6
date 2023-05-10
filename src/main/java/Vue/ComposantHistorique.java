package Vue;

import javax.swing.*;

public class ComposantHistorique extends Box {

	ComposantHistorique(CollecteurEvenements control) {
		super(BoxLayout.X_AXIS);
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new AdaptateurAnnuler(control));
		add(annuler);
		JButton refaire = new JButton("Refaire");
		refaire.addActionListener(new AdaptateurRefaire(control));
		add(refaire);
	}
}
