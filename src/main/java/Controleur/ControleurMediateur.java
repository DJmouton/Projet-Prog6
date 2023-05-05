package Controleur;

import Modele.*;
import Vue.CollecteurEvenements;

import java.io.FileNotFoundException;
import java.util.List;

public class ControleurMediateur implements CollecteurEvenements, Runnable {
	Jeu jeu;
	boolean EoT; // end of turn
	Coup coup;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		setEoT(false);
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
	public void reset() {
		jeu.reset();
		setEoT(false);
		/////////////////////////////////////////
		// a deplacer plus tard dans Jeu
		this.jeu.joueurs = new Joueur[2];
		this.jeu.joueurs[0] = new Joueur(4, jeu);
		this.jeu.joueurs[1] = new Joueur(5, jeu);
		/////////////////////////////////////////
		partie();
	}

	/****************************************************************************************
	 * Traitement d'un clic sur le bouton IA, bascule le joueur de mode.
	 * L'IHM doit faire appel à ces fonctions peut importe le bouton IA cliqué.
	 * Il donneront en paramètre le numéro du joueur car ils savent quel bouton a été cliqué.
	 *****************************************************************************************/
	public void basculeIA(int num) {
		jeu.changeModeJoueur(num);
	}


	/**************************************************************************************************
	 * Traitement d'un clic humain sur le plateau, ignoré si ce n'est pas au tour d'un humain de jouer.
	 ***************************************************************************************************/
	public void clicSouris(int l, int c) {
		if (jeu.joueurs[jeu.joueurCourant].estIA)
			return;


		switch (jeu.etatCourant()) {
			case Initialisation:
				if (this.jeu.plateau[l][c] == 1) {
					new Placement(jeu, l, c).execute();
					setEoT(true); // le tour d'un humain peut s'arreter ici
					System.out.println("Pingouin placé en (" + l + "," + c + ")");

				}
				break;

			case Selection:
				if (jeu.plateau[l][c] == jeu.joueurCourant + 4) {
					coup = new Coup(l, c, this.jeu);
					jeu.setEtat(Etats.Deplacement);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
				}else{
					System.out.println("Coup impossible");
				}
				break;

			case Deplacement:
				if (jeu.contains(new int[]{l, c}, jeu.hex_accessible(coup.sourcel, coup.sourcec))){
					coup.destl = l;
					coup.destc = c;
					coup.execute();
					jeu.setEtat(Etats.Selection);
					System.out.println("Pingouin déplacé en (" + l + "," + c + ")");
					setEoT(true); // le tour d'un humain peut s'arreter ici
				}else if(jeu.plateau[l][c] == jeu.joueurCourant + 4){
					coup = new Coup(l, c, this.jeu);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
				}else {
					System.out.println("Coup impossible");
				}
				break;
		}

		jeu.metAJour();
	}

	/*****************************************
	 * Passe au prochain joueur qui peut jouer
	 ******************************************/
	public void joueurSuivant() {
		jeu.prochainJoueur();
	}

	/**************************
	 * Déroulement d'un tour
	 ***************************/
	public void tour() {
		System.out.println("--------------------------------------------------");
		System.out.println("Au tour du joueur " + jeu.joueurCourant);
		System.out.println("Score : " + jeu.joueurs[jeu.joueurCourant].getScore());

		if (!(jeu.joueurs[jeu.joueurCourant].estIA)) {
			while (!isEoT());// on attend en boucle que l'humain termine son tour (essayer avec thread pour v2)
			setEoT(false);

		} else {// ajouter temporisation ici ?
			try { // TEMPORAIRE
				Thread.sleep(250);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			switch (jeu.etatCourant()) {
				case Initialisation:
					Placement placement = ((IA) jeu.joueurs[jeu.joueurCourant]).placement();
					placement.execute();
					System.out.println("Pingouin placé en (" + placement.destl + "," + placement.destc + ")");
					break;

				case Selection:
					coup = ((IA) jeu.joueurs[jeu.joueurCourant]).jeu();
					coup.execute();
					System.out.println("Pingouin déplacé de (" + coup.sourcel + "," + coup.sourcec + ") à (" + coup.destl + "," + coup.destc + ")");
					break;

				case Deplacement:
					System.err.println("L'IA ne devrait pas commencer son tour à l'état déplacement");
					break;
			}
		}
		joueurSuivant();
		jeu.metAJour();
	}

	/**************************
	 * Déroulement d'une partie
	 ***************************/
	public void partie() {
		while (jeu.enCours())
			tour();

		// affichage des scores finals
		System.out.println("--------------------------------------------------");
		System.out.println("Partie terminée");
		afficheRanking(jeu.Ranking());
	}
	public void afficheRanking(List<Joueur> joueur){
		for (int i=0;i<joueur.size();i++){
			if(i+1<joueur.size() && joueur.get(i).getScore()==joueur.get(i+1).getScore()){
				if (jeu.joueurs[joueur.get(i).getNum()-4].getIlots()<jeu.joueurs[joueur.get(i+1).getNum()-4].getIlots()){
					Joueur temp=joueur.get(i);
					joueur.add(i,joueur.get(i+1));
					joueur.add(i+1,temp);
				}
				else if(jeu.joueurs[joueur.get(i).getNum()-4].getIlots()==jeu.joueurs[joueur.get(i+1).getNum()-4].getIlots()){
					System.out.println("EGALITE");
				}
			}
			System.out.println("Joeur numero " +(joueur.get(i).getNum()-4) + " avec le score " + joueur.get(i).getScore()+ " et nombre d'ilots "+ jeu.joueurs[joueur.get(i).getNum()-4].getIlots());
		}
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
		return jeu.joueurCourant;
	}

	public synchronized boolean isEoT() {
		return EoT;
	}

	public synchronized void setEoT(boolean eoT) {
		EoT = eoT;
	}

	@Override
	public void run() {
		partie();
	}
}
