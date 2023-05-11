package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class ComposantJoueurs extends Box implements Observateur {

	ComposantInfoJoueur[] joueurs;
	CollecteurEvenements control;

	ComposantJoueurs(CollecteurEvenements c) {
		super(BoxLayout.Y_AXIS);
		control = c;
		joueurs = new ComposantInfoJoueur[4];
		joueurs[0] = new ComposantInfoJoueur(1, Color.blue);
		joueurs[1] = new ComposantInfoJoueur(2, Color.green);
		joueurs[2] = new ComposantInfoJoueur(3, Color.red);
		joueurs[3] = new ComposantInfoJoueur(4, Color.yellow);
		for (ComposantInfoJoueur joueur : joueurs) {
			add(Box.createGlue());
			add(joueur);
		}
		add(Box.createGlue());
		joueurs[0].setCurrent(true);
	}

	@Override
	public void miseAJour() {
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].setScore(control.scoreJoueur(i));
			joueurs[i].setCurrent(control.joueurCourant() == i);
		}
	}
}
