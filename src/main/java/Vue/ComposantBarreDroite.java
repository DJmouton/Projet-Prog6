package Vue;

import javax.swing.*;

public class ComposantBarreDroite extends Box {

	CollecteurEvenements control;

	ComposantBarreDroite(CollecteurEvenements c) {
		super(BoxLayout.Y_AXIS);
		control = c;

		ComposantHistoriqueCoups panelHistorique = new ComposantHistoriqueCoups(control);
		add(panelHistorique);
	}
}
