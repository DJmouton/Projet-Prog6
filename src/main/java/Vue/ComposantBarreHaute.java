package Vue;

import javax.swing.*;

public class ComposantBarreHaute extends Box {

	CollecteurEvenements control;

	ComposantBarreHaute(CollecteurEvenements c) {
		super(BoxLayout.X_AXIS);
		control = c;
		JButton nouvelle_partie = new JButton("Recommencer");
		nouvelle_partie.addActionListener(new AdaptateurRecommencer(control));
		add(nouvelle_partie);
		add(Box.createGlue());

		// Boutons annuler et refaire
		ComposantHistorique historique;
		historique = new ComposantHistorique(control);
		control.ajouteObservateur(historique);
		add(historique);

		add(Box.createGlue());
		JButton menu = new JButton("Menu");
		menu.addActionListener(new AdaptateurMenu(control));
		add(menu);
	}
}
