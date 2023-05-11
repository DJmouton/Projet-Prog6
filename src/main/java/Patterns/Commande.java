package Patterns;

import Modele.Jeu;

public interface Commande {
	void execute();

	void desexecute();

	void setJeu(Jeu jeu);
}
