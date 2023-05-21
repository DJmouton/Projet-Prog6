package Vue;

import javax.swing.*;

public class ComposantBarreGauche extends Box {

	CollecteurEvenements control;

	ComposantBarreGauche(CollecteurEvenements c) {
		super(BoxLayout.Y_AXIS);
		control = c;
		ComposantJoueurs j = new ComposantJoueurs(control);
		add(j);
		control.ajouteObservateur(j);
	}
}
