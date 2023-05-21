package Vue;

import javax.swing.*;
import java.awt.*;
public class ComposantTour extends JPanel {

	CollecteurEvenements control;

	 	ComposantTour(CollecteurEvenements c) {
		control = c;
		setBackground(new Color(51, 153, 255));
		ComposantJoueurActuel ja = new ComposantJoueurActuel(control);
		add(ja);
		control.ajouteObservateur(ja);
	}
}
