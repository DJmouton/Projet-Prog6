package Modele;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoriqueTest
{
	@Test
	void historiqueTest() {
		Jeu jeu = new Jeu();
		jeu.plateau = new int[][]{{0, 1, 1, 3, 3, 1, 2, 3},
								  {1, 1, 1, 1, 1, 2, 1, 1},
								  {0, 2, 1, 3, 2, 1, 2, 1},
								  {2, 1, 1, 1, 1, 1, 1, 2},
								  {0, 1, 2, 1, 2, 3, 1, 2},
								  {3, 3, 1, 3, 1, 1, 2, 1},
								  {0, 2, 3, 2, 1, 1, 1, 1},
								  {1, 2, 1, 1, 2, 3, 1, 2}};

		jeu.copy_plateau = new int[][]{{0, 1, 1, 3, 3, 1, 2, 3},
									   {1, 1, 1, 1, 1, 2, 1, 1},
									   {0, 2, 1, 3, 2, 1, 2, 1},
									   {2, 1, 1, 1, 1, 1, 1, 2},
									   {0, 1, 2, 1, 2, 3, 1, 2},
									   {3, 3, 1, 3, 1, 1, 2, 1},
									   {0, 2, 3, 2, 1, 1, 1, 1},
									   {1, 2, 1, 1, 2, 3, 1, 2}};
		ArrayList<Integer> typesJoueurs = new ArrayList<>();
		typesJoueurs.add(1);
		typesJoueurs.add(1);
		typesJoueurs.add(0);
		typesJoueurs.add(0);
		jeu.initJoueurs(typesJoueurs);

		assertEquals(0, jeu.historique.passe.size());
		assertEquals(0, jeu.historique.futur.size());

		// DEBUT PLACEMENTS

		jeu.faire(new Placement(jeu, 0, 1));

		assertEquals(1, jeu.historique.passe.size());
		assertEquals(0, jeu.historique.futur.size());
		assertEquals(1, jeu.joueurCourant);
		assertEquals(1, jeu.nombreP);

		jeu.faire(new Placement(jeu, 0, 2));

		assertEquals(2, jeu.historique.passe.size());
		assertEquals(0, jeu.historique.futur.size());
		assertEquals(0, jeu.joueurCourant);

		jeu.faire(new Placement(jeu, 1, 0));
		/* ETAT DU JEU
		{0, 4, 5, 3, 3, 1, 2, 3}
		{4, 1, 1, 1, 1, 2, 1, 1}
		{0, 2, 1, 3, 2, 1, 2, 1}
		{2, 1, 1, 1, 1, 1, 1, 2}
		{0, 1, 2, 1, 2, 3, 1, 2}
		{3, 3, 1, 3, 1, 1, 2, 1}
		{0, 2, 3, 2, 1, 1, 1, 1}
		{1, 2, 1, 1, 2, 3, 1, 2}
		Joueur 0 : 2 poissons, 2 ilots
		Joueur 1 : 1 poisson, 1 ilot
		*/
		jeu.faire(new Placement(jeu, 1, 1));
		jeu.annuler();

		assertEquals(3, jeu.historique.passe.size());
		assertEquals(1, jeu.historique.futur.size());
		assertEquals(4, jeu.plateau[0][1]);
		assertEquals(5, jeu.plateau[0][2]);
		assertEquals(4, jeu.plateau[1][0]);
		assertEquals(1, jeu.plateau[1][1]);
		assertEquals(1, jeu.joueurCourant);
		assertEquals(3, jeu.nombreP);
		assertEquals(0, jeu.e);
		assertEquals(2, jeu.joueurs[0].score);
		assertEquals(2, jeu.joueurs[0].ilots);
		assertEquals(1, jeu.joueurs[1].score);
		assertEquals(1, jeu.joueurs[1].ilots);

		jeu.refaire();

		assertEquals(4, jeu.historique.passe.size());
		assertEquals(0, jeu.historique.futur.size());
		assertEquals(8, jeu.plateau[0][1]);
		assertEquals(5, jeu.plateau[0][2]);
		assertEquals(4, jeu.plateau[1][0]);
		assertEquals(5, jeu.plateau[1][1]);
		assertEquals(0, jeu.joueurCourant);
		assertEquals(3, jeu.nombreP);
		assertEquals(1, jeu.e);
		assertEquals(2, jeu.joueurs[0].score);
		assertEquals(2, jeu.joueurs[0].ilots);
		assertEquals(2, jeu.joueurs[1].score);
		assertEquals(2, jeu.joueurs[1].ilots);

		jeu.faire(new Placement(jeu, 4, 3));
		jeu.faire(new Placement(jeu, 6, 6));
		jeu.faire(new Placement(jeu, 7, 0));
		jeu.faire(new Placement(jeu, 2, 7));

		/* ETAT DU JEU
		{0, 0, 5, 3, 3, 1, 2, 3}
		{4, 5, 1, 1, 1, 2, 1, 1}
		{0, 2, 1, 3, 2, 1, 2, 5}
		{2, 1, 1, 1, 1, 1, 1, 2}
		{0, 1, 2, 4, 2, 3, 1, 2}
		{3, 3, 1, 3, 1, 1, 2, 1}
		{0, 2, 3, 2, 1, 1, 5, 1}
		{4, 2, 1, 1, 2, 3, 1, 2}
		Joueur 0 : 4 poissons, 4 ilots
		Joueur 1 : 4 poissons, 4 ilots
		JoueurCourant = 0
		*/

		// DEBUT DEPLACEMENTS

		jeu.faire(new Coup(jeu, 4, 3, 4, 5));
		jeu.faire(new Coup(jeu, 1, 1, 2, 1));
		/* ETAT DU JEU
		{0, 0, 5, 3, 3, 1, 2, 3}
		{0, 0, 1, 1, 1, 2, 1, 1}
		{0, 5, 1, 3, 2, 1, 2, 5}
		{2, 1, 1, 1, 1, 1, 1, 2}
		{0, 1, 2, 0, 2, 4, 1, 2}
		{3, 3, 1, 3, 1, 1, 2, 1}
		{0, 2, 3, 2, 1, 1, 5, 1}
		{4, 2, 1, 1, 2, 3, 1, 2}
		Joueur 0 : 7 poissons, 5 ilots
		Joueur 1 : 6 poissons, 5 ilots
		JoueurCourant = 0
		*/
		jeu.setEtat(Etats.Initialisation);
		jeu.annuler();
		assertEquals(1, jeu.joueurCourant);
		jeu.setEtat(Etats.Initialisation);
		jeu.refaire();
		jeu.setEtat(Etats.Initialisation);

		assertEquals(10, jeu.historique.passe.size());
		assertEquals(0, jeu.historique.futur.size());
		assertEquals(8, jeu.plateau[0][1]);
		assertEquals(5, jeu.plateau[0][2]);
		assertEquals(8, jeu.plateau[1][0]);
		assertEquals(0, jeu.plateau[1][1]);
		assertEquals(5, jeu.plateau[2][1]);
		assertEquals(5, jeu.plateau[2][7]);
		assertEquals(4, jeu.plateau[4][5]);
		assertEquals(5, jeu.plateau[6][6]);
		assertEquals(4, jeu.plateau[7][0]);
		assertEquals(0, jeu.joueurCourant);
		assertEquals(6, jeu.nombreP);
		assertEquals(2, jeu.e);
		assertEquals(7, jeu.joueurs[0].score);
		assertEquals(5, jeu.joueurs[0].ilots);
		assertEquals(6, jeu.joueurs[1].score);
		assertEquals(5, jeu.joueurs[1].ilots);

		jeu.faire(new Coup(jeu, 4, 5, 0, 3));
		jeu.faire(new Coup(jeu, 6, 6, 6, 1));
		jeu.faire(new Coup(jeu, 0, 3, 1, 2));
		jeu.faire(new Coup(jeu, 6, 1, 7, 1));
		jeu.setEtat(Etats.Initialisation);
		jeu.annuler();
		jeu.setEtat(Etats.Initialisation);
		jeu.annuler();
		jeu.setEtat(Etats.Initialisation);
		jeu.annuler();
		jeu.setEtat(Etats.Initialisation);
		jeu.annuler();

		assertEquals(10, jeu.historique.passe.size());
		assertEquals(4, jeu.historique.futur.size());
		assertEquals(8, jeu.plateau[0][1]);
		assertEquals(5, jeu.plateau[0][2]);
		assertEquals(8, jeu.plateau[1][0]);
		assertEquals(0, jeu.plateau[1][1]);
		assertEquals(5, jeu.plateau[2][1]);
		assertEquals(5, jeu.plateau[2][7]);
		assertEquals(4, jeu.plateau[4][5]);
		assertEquals(5, jeu.plateau[6][6]);
		assertEquals(4, jeu.plateau[7][0]);
		assertEquals(0, jeu.joueurCourant);
		assertEquals(6, jeu.nombreP);
		assertEquals(2, jeu.e);
		assertEquals(7, jeu.joueurs[0].score);
		assertEquals(5, jeu.joueurs[0].ilots);
		assertEquals(6, jeu.joueurs[1].score);
		assertEquals(5, jeu.joueurs[1].ilots);
	}
}