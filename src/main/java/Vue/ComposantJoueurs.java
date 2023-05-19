package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class ComposantJoueurs extends Box implements Observateur {

	int nombreJoueurs;
	ComposantInfoJoueur[] joueurs;

	ComposantTypeJoueur[] typeJoueurs;
	CollecteurEvenements control;

	ComposantJoueurs(CollecteurEvenements c) {
		super(BoxLayout.Y_AXIS);
		control = c;
		nombreJoueurs = control.nombreJoueurs();
		joueurs = new ComposantInfoJoueur[4];
		typeJoueurs = new ComposantTypeJoueur[4];
		joueurs[0] = new ComposantInfoJoueur(1, Color.blue);
		joueurs[1] = new ComposantInfoJoueur(2, Color.green);
		joueurs[2] = new ComposantInfoJoueur(3, Color.red);
		joueurs[3] = new ComposantInfoJoueur(4, Color.yellow);
		for (int i = 0; i < 4; i++)
			typeJoueurs[i] = new ComposantTypeJoueur(false);

		for (int i = 0; i < nombreJoueurs; i++) {
			typeJoueurs[i].setBotHumain(control.estIA(i));
			add(typeJoueurs[i]);
			add(joueurs[i]);
		}
		add(Box.createGlue());
		joueurs[0].setCurrent(true);
	}

	@Override
	public void miseAJour() {
		for (int i = 0; i < control.nbJoueurs(); i++) {
			joueurs[i].setScore(control.scoreJoueur(i));
			joueurs[i].setCurrent(control.joueurCourant() == i);
			typeJoueurs[i].setBotHumain(control.estIA(i));
			typeJoueurs[i].setCourant(control.joueurCourant() == i);
		}
		if (nombreJoueurs != control.nombreJoueurs()) {
			nombreJoueurs = control.nombreJoueurs();
			removeAll();
			for (int i = 0; i < nombreJoueurs; i++) {
				add(typeJoueurs[i]);
				add(joueurs[i]);
			}
			add(Box.createGlue());

		}
	}
}
