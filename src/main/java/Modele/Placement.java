package Modele;

import Patterns.Commande;

public class Placement implements Commande
{
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
		if (jeu.nombreP == 4-jeu.e)
			jeu.etat = Etats.Selection;

		jeu.plateau[destl][destc] = jeu.joueurCourant + 4;
		jeu.e=jeu.EnlevePingou(destl,destc);
	}

	public void desexecute() {
		jeu.annuler();
	}

	@Override
	public String toString() {
		return "Coup{" +
				"destl=" + destl +
				", destc=" + destc +
				", jeu=" + jeu +
				'}';
	}
}
