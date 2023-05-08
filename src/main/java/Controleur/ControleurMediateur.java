package Controleur;

import Modele.*;
import Vue.CollecteurEvenements;

import java.io.FileNotFoundException;
import java.util.List;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Coup coup;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		tour();
	}

	/*****************************************************************
	 * Traitement d'un clic sur le bouton reset, réinitialise le jeu.
	 *****************************************************************/
	public void reset() {
		jeu.reset();
		tour();
	}

	/**************************************************************************************************
	 * Traitement d'un clic humain sur le plateau, ignoré si ce n'est pas au tour d'un humain de jouer.
	 ***************************************************************************************************/
	public void clicSouris(int l, int c) {
		if (!jeu.enCours() || jeu.joueurs[jeu.joueurCourant].estIA)
			return;

		switch (jeu.etatCourant()) {
			case Initialisation:
				if (this.jeu.plateau[l][c] == 1) {
					new Placement(jeu, l, c).execute();
					System.out.println("Pingouin placé en (" + l + "," + c + ")");
					jeu.metAJour();
					jeu.prochainJoueur();
					tour();
				}
				break;

			case Selection:
				if (jeu.plateau[l][c] == jeu.joueurCourant + 4) {
					coup = new Coup(l, c, this.jeu);
					jeu.setEtat(Etats.Deplacement);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
				} else {
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
					jeu.metAJour();
					jeu.prochainJoueur();
					tour();
				}else if(jeu.plateau[l][c] == jeu.joueurCourant + 4){
					coup = new Coup(l, c, this.jeu);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
				}else {
					System.out.println("Coup impossible");
				}
				break;
		}
	}

	/**********************************************************************************
	 * Traitement d'un tic du timer, ignoré si ce n'est pas au tour d'une IA de jouer.
	 **********************************************************************************/
	public void tictac() {
		if (!jeu.enCours() || !jeu.joueurs[jeu.joueurCourant].estIA)
			return;

		switch (jeu.etatCourant()) {
			case Initialisation:
				Placement placement = ((IA) jeu.joueurs[jeu.joueurCourant]).placement();
				placement.execute();
				System.out.println("Pingouin placé en (" + placement.destl + "," + placement.destc + ")");
				jeu.metAJour();
				jeu.prochainJoueur();
				tour();
				break;

			case Selection:
				coup = ((IA) jeu.joueurs[jeu.joueurCourant]).jeu();
				coup.execute();
				System.out.println("Pingouin déplacé de (" + coup.sourcel + "," + coup.sourcec + ") à (" + coup.destl + "," + coup.destc + ")");
				jeu.metAJour();
				jeu.prochainJoueur();
				tour();
				break;

			case Deplacement:
				System.err.println("L'IA ne devrait pas commencer son tour à l'état déplacement");
				break;
		}
	}

	/***************************
	 * Gestion du début du tour
	 ***************************/
	public void tour() {
		if (!jeu.enCours()) {
			finPartie();
			return;
		}
		System.out.println("--------------------------------------------------");
		System.out.println("Au tour du joueur " + jeu.joueurCourant);
		System.out.println("Score : " + jeu.joueurs[jeu.joueurCourant].getScore());
	}

	/***************************
	 * Gestion de fin de partie
	 ***************************/
	public void finPartie() {
		System.out.println("--------------------------------------------------");
		System.out.println("Partie terminée");
		afficheRanking(jeu.Ranking());
	}

	/********************************
	 * Affichage du classement final
	 ********************************/
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
}
