package Modele;

import Patterns.Commande;

public class Placement implements Commande {
	public int destl;
	public int destc;
	Jeu jeu;

	public Placement(Jeu jeu, int destl, int destc) {
		this.destl = destl;
		this.destc = destc;
		this.jeu = jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public void execute() {
		jeu.joueurs[jeu.joueurCourant].addIlots();
		jeu.joueurs[jeu.joueurCourant].addScore(1);
		jeu.nombreP++;
		if (jeu.nombreP == jeu.nombrePAvoir - jeu.e)
			jeu.etat = Etats.Selection;

		jeu.plateau[destl][destc] = jeu.joueurCourant + 4;
		jeu.e = jeu.EnlevePingou(destl, destc);
		jeu.prochainJoueur();
	}

	public void desexecute() {

	}

	@Override
	public String toString() {
		return "Placement" + "\n" +
				+destl +
				" " + destc
				//", jeu=" + jeu +
				;
	}
}
