package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class ComposantJoueurActuel extends JLabel implements Observateur {

	CollecteurEvenements control;

	ComposantJoueurActuel(CollecteurEvenements c) {
		control = c;
		setFont(new Font("Arial", Font.BOLD, 20));
		setText("<html>Joueur " + (control.joueurCourant()+1) + ", place un pingouin !</html>");
	}
	@Override
	public void miseAJour() {
		if(!control.estIA())
			if(control.etatPla())
				setText("<html>Joueur " + (control.joueurCourant()+1) + ", place un pingouin !</html>");
			else
				setText("<html>Joueur " + (control.joueurCourant()+1) + ", déplace un pingouin !</html>");
		else
			setText("<html>L'IA du joueur " + (control.joueurCourant()+1) + " réfléchit</html>");
	}
}
