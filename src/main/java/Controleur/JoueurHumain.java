package Controleur;

import Modele.Jeu;
import Modele.Coup;
import Modele.Joueur;

public class JoueurHumain extends Joueur
{
	public JoueurHumain(int n, Jeu p) {
		super(n, p);
	}

	@Override
	public Coup jeu() {
		/*
		// A adapter selon le jeu,
		// Un coup peut être constitué de plusieurs passages par cette fonction, ex :
		// - selection d'un pièce + surlignage des coups possibles
		// - selection de la destination
		// Autrement dit une machine à état peut aussi être gérée par un objet de cette
		// classe. Dans le cas du morpion, un clic suffit.
		if (plateau.libre(i, j)) {
			plateau.jouer(i, j);
			return true;
		} else {
			return false;
		}
		*/
		return null;
	}
}