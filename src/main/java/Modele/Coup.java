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
//		if (jeu.libre(l, c))
//			jeu.manger(l, c);
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
