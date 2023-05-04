package Controleur;

import Modele.IA;
import Modele.Jeu;
import Modele.Joueur;
import Modele.Coup;
import Modele.Placement;
import Vue.CollecteurEvenements;

import java.io.FileNotFoundException;

public class ControleurMediateur implements CollecteurEvenements, Runnable {
	Jeu jeu;
	boolean EoT; // end of turn
	Placement placement;
	Coup coup;
	int joueurCourant; // unused

	public ControleurMediateur(Jeu j)
	{
		jeu = j;
		EoT = false;
		/////////////////////////////////////////
		// a deplacer plus tard dans Jeu
		this.jeu.joueurs = new Joueur[2];
		this.jeu.joueurs[0] = new Joueur(4, jeu);
		this.jeu.joueurs[1] = new IA(5, jeu);
		/////////////////////////////////////////

	}

	/***************************************************************************
	* Traitement d'un clic sur le bouton reset, réinitialise et relance le jeu.
	****************************************************************************/
	public void reset()
	{
		jeu.reset();
		EoT = false;
		/////////////////////////////////////////
		// a deplacer plus tard dans Jeu
		this.jeu.joueurs = new Joueur[2];
		this.jeu.joueurs[0] = new Joueur(4, jeu);
		this.jeu.joueurs[1] = new IA(5, jeu);
		/////////////////////////////////////////
		partie();
	}

	/****************************************************************************************
	* Traitement d'un clic sur le bouton IA, bascule le joueur de mode.
	* L'IHM doit faire appel à ces fonctions peut importe le bouton IA cliqué.
	* Il donneront en paramètre le numéro du joueur car ils savent quel bouton a été cliqué.
	*****************************************************************************************/
	public void basculeIA(int num)
	{
		jeu.changeModeJoueur(num);
	}


	/**************************************************************************************************
	* Traitement d'un clic humain sur le plateau, ignoré si ce n'est pas au tour d'un humain de jouer.
	***************************************************************************************************/
	public void clicSouris(int l, int c)
	{
		if (jeu.joueurs[jeu.joueurCourant].estIA)
			return;

		boolean sel = false;

		switch (jeu.etatCourant())
		{
			case Initialisation:
				EoT = jeu.InitPingou(l, c); // le tour d'un humain peut s'arreter ici
				if (EoT)
					System.out.println("Pingouin placé en (" + l + "," + c + ")");
				break;

			case Selection:
				sel = jeu.SelectPingou(l, c);
				if (sel)
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
				break;

			case Deplacement:
				EoT = jeu.DeplacePingou(l, c); // le tour d'un humain peut s'arreter ici
				if (EoT)
					System.out.println("Pingouin déplacé en (" + l + "," + c + ")");
				break;
		}

		jeu.metAJour();
	}

	/*****************************************
	* Passe au prochain joueur qui peut jouer
	******************************************/
	public void joueurSuivant()
	{
		jeu.prochainJoueur();
	}

	/**************************
	* Déroulement d'un tour
	***************************/
	public void tour()
	{
		System.out.println("Au tour du joueur " + jeu.joueurCourant);
		System.out.println("Score : " + jeu.joueurs[joueurCourant].getScore());

		if (!(jeu.joueurs[joueurCourant].estIA))
		{
			while (!EoT) ; // on attend en boucle que l'humain termine son tour (essayer avec thread pour v2)
			EoT = false;
		}
		else
		{// ajouter temporisation ici ?
			switch (jeu.etatCourant())
			{
				case Initialisation:
					placement = ((IA) jeu.joueurs[joueurCourant]).placement();
					placement.execute();
					System.out.println("Pingouin placé en (" + placement.destl + "," + placement.destc + ")");
					break;

				case Selection:
					coup = ((IA) jeu.joueurs[joueurCourant]).jeu();
					coup.execute();
					System.out.println("Pingouin déplacé de (" + coup.sourcel + "," + coup.sourcec + ") à (" + coup.destl + "," + coup.destc + ")");
					break;

				case Deplacement:
					System.err.println("L'IA ne devrait pas commencer son tour à l'état déplacement");
					break;
			}
		}

		joueurSuivant();
	}

	/**************************
	* Déroulement d'une partie
	***************************/
	public void partie()
	{
		while (jeu.enCours())
			tour();

		// affichage des scores finals
		System.out.println("Partie terminée");
	}


//////////////////////////////////////////////////////////////////////////
//
// FONCTIONS A SUPPRIMER POUR LA PREMIERE VERSION
//
//////////////////////////////////////////////////////////////////////////

	public void annuler(){

	}

	public void refaire(){

	}

	public void tictac() {
		if (jeu.enCours()) {/*
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].num());
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}*/
		}
	}

	@Override
	public void changeJoueur(int j, int t) {
		/*
		System.out.println("Nouveau type " + t + " pour le joueur " + j);
		if(t == 0) {
			joueurs[j][0] = new JoueurHumain(j, jeu);
		}
		else if(t == 1) {
			if(j==0) {
				joueurs[j][1] = new IAAleatoire(j, jeu);
			}
			else {
				joueurs[j][1] = new IANiveau2(j, jeu);
			}
		}
		typeJoueur[j] = t;
		*/
	}

	@Override
	public void changeTaille(int x, int y) {
		System.out.println("Nouvelle taille: " + x + ", " + y);
		jeu.reset();
	}

	public void sauver(String fichier){
		try {
			jeu.sauver(fichier);
		} catch (FileNotFoundException e) {
			System.err.println("Impossible de sauvegarder dans " + fichier);
		}
		System.out.println("Partie sauvegardée");
	}

	public void charger(String fichier){
		try {
			jeu.reset(fichier);
		} catch (FileNotFoundException e) {
			System.err.println("Impossible de charger depuis " + fichier);
		}
		System.out.println("Partie chargée");
	}

	public int joueurCourant() {
		return this.joueurCourant;
	}

	@Override
	public void run()
	{
		partie();
	}
}
