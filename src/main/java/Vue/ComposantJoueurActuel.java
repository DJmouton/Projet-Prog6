package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class ComposantJoueurActuel extends JLabel implements Observateur {

	CollecteurEvenements control;

	ComposantJoueurActuel(CollecteurEvenements c) {
		control = c;
		setFont(new Font("Arial", Font.BOLD, 20));
		setText("<html>Joueur " + (control.joueurCourant()+1) + " à ton tour !<br> Clique sur un case à 1 poisson</html>");
	}
	@Override
	public void miseAJour() {
		if(!control.estIA())
			if(control.etatPla())
				setText("<html>Joueur " + (control.joueurCourant()+1) + " à ton tour !<br> Clique sur un case à 1 poisson</html>");
			else
				setText("<html>Joueur " + (control.joueurCourant()+1) + " à ton tour !<br> Clique sur un pingouin, puis sur une case</html>");
	}
}
