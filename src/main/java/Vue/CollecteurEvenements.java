package Vue;

import Modele.Etats;
import Patterns.Observateur;

import java.util.ArrayList;

/*
 * Tous les évènements à gérer lors de l'éxecution du programme
 */
public interface CollecteurEvenements {

	ArrayList<int[]> hexAccessible(int l, int c);

	ArrayList<int[]> getPinguins(int numeroJ);

	int coupSrcL();

	int coupSrcC();

	int coupDestL();

	int coupDestC();

	int hauteur();

	int largeur();
	void nouvellePartie(int j1, int j2, int j3, int j4);

	void clicSouris(int l, int c);

	void tictac();

	void changeJoueur(int j, int t);

	void sauver(String fichier);

	void charger(String fichier);

	void annuler();

	void refaire();

	void reset();

	int joueurCourant();

	Etats etatCourant();

	void ajouteObservateur(Observateur o);

	int valeur(int l, int c);

	boolean partieEnCours();

	int scoreJoueur(int joueur);

	boolean estIA();
}
