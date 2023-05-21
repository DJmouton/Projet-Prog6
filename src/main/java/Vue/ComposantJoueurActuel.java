package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class ComposantJoueurActuel extends JLabel implements Observateur {

	CollecteurEvenements control;

	ComposantJoueurActuel(CollecteurEvenements c) {
		control = c;
		setFont(new Font("Arial", Font.BOLD, 30));
		setText("Joueur 1 à ton tour !");
	}
	@Override
	public void miseAJour() {
		setText("Joueur " + (control.joueurCourant()+1) + " à ton tour !");
	}
}
