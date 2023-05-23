package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantHistoriqueCoups extends JPanel {

	CollecteurEvenements control;

	ComposantHistoriqueCoups(CollecteurEvenements c) {
		control = c;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ComposantAfficheDernierCoup dc = new ComposantAfficheDernierCoup(control);
		add(dc);
		control.ajouteObservateur(dc);
		setBackground(new Color(51, 153, 255));
	}
}
