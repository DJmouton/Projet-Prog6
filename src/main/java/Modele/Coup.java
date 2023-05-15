package Modele;

import Patterns.Commande;

public class Coup implements Commande
{
	public int sourcel, sourcec;
	public int destl, destc;
	Jeu jeu;

	public Coup(Jeu jeu, int sourcel, int sourcec, int destl, int destc) {
		this.sourcel = sourcel;
		this.sourcec = sourcec;
		this.destl = destl;
		this.destc = destc;
		this.jeu = jeu;
	}

	public Coup(int sourcel, int sourcec, Jeu jeu) {
		this.sourcel = sourcel;
		this.sourcec = sourcec;
		this.destl = -1;
		this.destc = -1;
		this.jeu = jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public void execute() {
		jeu.joueurs[jeu.joueurCourant].addIlots();
		jeu.joueurs[jeu.joueurCourant].addScore(jeu.plateau[destl][destc]);
		jeu.plateau[destl][destc] = jeu.joueurCourant + 4;
		jeu.plateau[sourcel][sourcec] = 0;
		jeu.EnlevePingou(destl, destc);
		jeu.prochainJoueur();
	}

	public void desexecute() {
		jeu.annuler();
	}

	@Override
	public String toString() {
		return "Coup" +"\n"
				+ sourcel +
				" " + sourcec +
				" " + destl +
				" " + destc
				//+ ", jeu=" + jeu +
				;
	}
}
