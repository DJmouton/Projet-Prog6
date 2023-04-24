package Modele;

import Patterns.Commande;

public class Coup implements Commande
{
	int l, c;
	Jeu jeu;

	Coup(Jeu jeu, int ligne, int colonne)
	{
		l = ligne;
		c = colonne;
		this.jeu = jeu;
	}

	public void execute()
	{
		if (jeu.libre(l, c))
			jeu.manger(l, c);
	}

	public void desexecute()
	{
		jeu.annuler();
	}

	public String toString()
	{
		return "Coup en ("+l+";"+c+")";
	}
}
