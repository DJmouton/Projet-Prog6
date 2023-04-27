package Modele;

import Patterns.Commande;

public class Coup implements Commande
{
	int sourcel, sourcec;
	int destl, destc;
	Jeu jeu;

	public Coup(Jeu jeu, int sourcel, int sourcec, int destl, int destc) {
		this.sourcel = sourcel;
		this.sourcec = sourcec;
		this.destl = destl;
		this.destc = destc;
		this.jeu = jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public void execute()
	{
		int numJ=this.jeu.plateau[sourcel][sourcec];
		this.jeu.plateau[sourcel][sourcec]=0;
		this.jeu.joueurs[numJ-4].score=this.jeu.plateau[destl][destc];
		this.jeu.plateau[destl][destc]=numJ;
		if (jeu.hex_accessible(destl,destc).isEmpty()){
			this.jeu.plateau[destl][destc]=0;
			this.jeu.nombreP--;
		}

	}

	public void desexecute()
	{
		jeu.annuler();
	}

	@Override
	public String toString() {
		return "Coup{" +
				"sourcel=" + sourcel +
				", sourcec=" + sourcec +
				", destl=" + destl +
				", destc=" + destc +
				", jeu=" + jeu +
				'}';
	}
}
