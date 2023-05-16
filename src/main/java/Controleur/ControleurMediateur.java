package Controleur;

import Modele.*;
import Patterns.Observateur;
import Vue.CollecteurEvenements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Coup coup;
	int poissons;

	int[] ranks;

	boolean consultation;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		nouvellePartie(1, 1, 0, 0);
	}

	public void nouvellePartie(int j1, int j2, int j3, int j4) {
		System.out.println("Création d'une partie avec (" + j1 + ", " + j2 + ", " + j3 + ", " + j4 + ")");
		List<Integer> typesJoueurs = new ArrayList<Integer>();
		typesJoueurs.add(j1);
		typesJoueurs.add(j2);
		typesJoueurs.add(j3);
		typesJoueurs.add(j4);
		jeu.reset();
		jeu.initJoueurs(typesJoueurs);
		jeu.initPlateau();
		consultation = false;
		tour();
	}

	public void recommencer(){
		int[] typesJoueurs = new int[4];
		for (int i = 0; i < 4; i++) {
			if(i < jeu.joueurs.length)
				typesJoueurs[i] = jeu.joueurs[i].getTypeJoueur();
			else
				typesJoueurs[i] = 0;
		}
		nouvellePartie(
				typesJoueurs[0],
				typesJoueurs[1],
				typesJoueurs[2],
				typesJoueurs[3]
		);
	}

	/**************************************************************************************************
	 * Traitement d'un clic humain sur le plateau, ignoré si ce n'est pas au tour d'un humain de jouer.
	 ***************************************************************************************************/
	public void clicSouris(int l, int c) {
		if (!jeu.enCours() || jeu.joueurs[jeu.joueurCourant].getTypeJoueur() > 1)
			return;

		switch (jeu.etatCourant()) {
			case Initialisation:
				if (this.jeu.plateau[l][c] == 1) {
					jeu.faire(new Placement(jeu, l, c));
					System.out.println("Pingouin placé en (" + l + "," + c + "), tu as gagné 1 poisson !");
					jeu.metAJour();
					consultation = false;
					tour();
				} else if (jeu.getNombreP() == 8 - jeu.getE()) {
					jeu.setEtat(Etats.Selection);
				} else {
					System.out.println("Un pingouin doit être placé sur un ilot à 1 poisson");
				}
				break;

			case Selection:
				if (jeu.plateau[l][c] == jeu.joueurCourant + 4) {
					coup = new Coup(l, c, this.jeu);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
					jeu.metAJour();
					jeu.setEtat(Etats.Deplacement);

				} else {
					System.out.println("Sélectionne un de tes pingouins");
				}
				break;

			case Deplacement:
				if (jeu.contains(new int[]{l, c}, jeu.hex_accessible(coup.sourcel, coup.sourcec))) {
					poissons = jeu.plateau[l][c];
					coup.destl = l;
					coup.destc = c;
					jeu.faire(coup);
					jeu.setEtat(Etats.Selection);
					System.out.println("Pingouin déplacé en (" + l + "," + c + "), tu as gagné " + poissons + " poissons !");
					jeu.metAJour();
					consultation = false;
					tour();
				} else if (jeu.plateau[l][c] == jeu.joueurCourant + 4) {
					coup = new Coup(l, c, this.jeu);
					System.out.println("Pingouin (" + l + "," + c + ") selectionné");
					jeu.metAJour();
				} else {
					System.out.println("Coup impossible");
				}
				break;
		}
	}

	/**********************************************************************************
	 * Traitement d'un tic du timer, ignoré si ce n'est pas au tour d'une IA de jouer.
	 **********************************************************************************/
	public void tictac() {
		if (!jeu.enCours() || jeu.joueurs[jeu.joueurCourant].getTypeJoueur() < 2 || consultation)
			return;

		switch (jeu.etatCourant()) {
			case Initialisation:
				Placement placement = ((IA) jeu.joueurs[jeu.joueurCourant]).placement();
				jeu.faire(placement);
				System.out.println("Pingouin placé en (" + placement.destl + "," + placement.destc + "), tu as gagné 1 poisson !");
				jeu.metAJour();
				tour();
				break;

			case Selection:
				coup = ((IA) jeu.joueurs[jeu.joueurCourant]).jeu();
				poissons = jeu.plateau[coup.destl][coup.destc];
				jeu.faire(coup);
				System.out.println("Pingouin déplacé de (" + coup.sourcel + "," + coup.sourcec + ") à (" + coup.destl + "," + coup.destc + "), tu as gagné " + poissons + " poissons !");
				jeu.metAJour();
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
		System.out.println("Au tour du joueur " + jeu.joueurCourant + " !");
		System.out.println("Score : " + jeu.joueurs[jeu.joueurCourant].getScore());

		switch (jeu.etatCourant()) {
			case Initialisation:
				System.out.println("Place le prochain pingouin");
				break;

			case Selection:
				System.out.println("Sélectionne un pingouin");
				break;
		}

		if (jeu.joueurs[joueurCourant()].getTypeJoueur() > 1 && !consultation)
			System.out.println("L'IA réfléchit...");
		else
			System.out.println();
	}

	/***************************
	 * Gestion de fin de partie
	 ***************************/
	public void finPartie() {
		System.out.println("--------------------------------------------------");
		System.out.println("Partie terminée, voici le classement final :");
		afficheRanking(jeu.Ranking());
	}

	public int[] ranking() {
		return ranks;
	}

	/********************************
	 * Affichage du classement final
	 ********************************/
	public void afficheRanking(List<Joueur> joueur) {
		ranks = new int[joueur.size()];
		for (int i = 0; i < joueur.size(); i++) {
			if (i + 1 < joueur.size() && joueur.get(i).getScore() == joueur.get(i + 1).getScore()) {
				if (jeu.joueurs[joueur.get(i).getNum() - 4].getIlots() < jeu.joueurs[joueur.get(i + 1).getNum() - 4].getIlots()) {
					Joueur temp = joueur.get(i);
					joueur.add(i, joueur.get(i + 1));
					joueur.add(i + 1, temp);
				} else if (jeu.joueurs[joueur.get(i).getNum() - 4].getIlots() == jeu.joueurs[joueur.get(i + 1).getNum() - 4].getIlots()) {
					System.out.println("EGALITE");
				}
			}
			System.out.println("Joueur " + (joueur.get(i).getNum() - 3) + " avec " + joueur.get(i).getScore() + " poissons et " + jeu.joueurs[joueur.get(i).getNum() - 4].getIlots() + " ilots");
			ranks[i] = joueur.get(i).getNum() - 4;
		}
	}

	public void annuler() {
		if (jeu.peutAnnuler()) {
			do {
				jeu.setEtat(Etats.Initialisation);
				jeu.annuler();
			} while (jeu.peutAnnuler() && jeu.joueurs[joueurCourant()].getTypeJoueur() > 1);
			if (jeu.getNombreP() == jeu.getnombrePAvoir()) {
				jeu.setEtat(Etats.Selection);
			}
			// le source du coup doit être celui du sommet de la pile passe
			if (jeu.historique.passe.size() > 0 && jeu.historique.passe.get(jeu.historique.passe.size()-1) instanceof Coup) {
				coup = ((Coup) jeu.historique.passe.get(jeu.historique.passe.size()-1));
			}
			if (jeu.getNombreP() == jeu.getnombrePAvoir()-jeu.getE()) {
				jeu.setEtat(Etats.Selection);
			}
			jeu.metAJour();
			consultation = true;
			tour();
		}
	}

	public void refaire() {
		if (jeu.peutRefaire()) {
			do {
				jeu.refaire();
			} while (jeu.peutRefaire() && jeu.joueurs[joueurCourant()].getTypeJoueur() > 1);

			if (!jeu.peutRefaire())
				consultation = false;
			// le source du coup doit être celui du sommet de la pile passe
			if (jeu.historique.passe.size() > 0 && jeu.historique.passe.get(jeu.historique.passe.size()-1) instanceof Coup) {
				coup = ((Coup) jeu.historique.passe.get(jeu.historique.passe.size()-1));
			}
			if (jeu.getNombreP() == jeu.getnombrePAvoir()-jeu.getE()) {
				jeu.setEtat(Etats.Selection);
			}
			jeu.metAJour();
			tour();
		}
	}


//////////////////////////////////////////////////////////////////////////
//
// FONCTIONS A SUPPRIMER POUR LA PREMIERE VERSION
//
//////////////////////////////////////////////////////////////////////////

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


	public void sauver(String fichier) {
		try {
			jeu.sauver(fichier);
		} catch (FileNotFoundException e) {
			System.err.println("Impossible de sauvegarder dans " + fichier);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Partie sauvegardée");
	}

	public void charger(String fichier) {
		jeu.setEtat(Etats.Initialisation);

		try {
			jeu.charger(fichier);
		} catch (FileNotFoundException e) {
			System.err.println("Impossible de charger depuis " + fichier);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if (jeu.getNombreP() == 8) {
			jeu.setEtat(Etats.Selection);
		}
		jeu.metAJour();
		System.out.println("Partie chargée");
		tour();

	}

	public int joueurCourant() {
		int jc = 0;
		try {
			jc = jeu.joueurCourant;
		} catch (Exception e) {
		}
		return jc;
	}

	public int scoreJoueur(int joueur) {
		int score = 0;
		try {
			score = jeu.joueurs[joueur].getScore();
		} catch (Exception e) {
		}
		return score;
	}

	public boolean estIA() {
		return jeu.joueurs[joueurCourant()].getTypeJoueur() > 1;
	}

	public ArrayList<int[]> hexAccessible(int l, int c) {
		return jeu.hex_accessible(l, c);
	}

	public int coupSrcL() {
		return coup.sourcel;
	}

	public int coupSrcC() {
		return coup.sourcec;
	}

	public int coupDestL() {
		return coup.destl;
	}

	public int coupDestC() {
		return coup.destc;
	}

	public int largeur() {
		return jeu.largeur();
	}

	public int hauteur() {
		return jeu.hauteur();
	}

	public ArrayList<int[]> getPinguins(int numeroJ) {
		return jeu.getPingouins(numeroJ);
	}

	public void ajouteObservateur(Observateur o) {
		jeu.ajouteObservateur(o);
	}

	public int valeur(int l, int c) {
		return jeu.valeur(l, c);
	}

	public boolean partieEnCours() {
		return jeu.enCours();
	}

	public boolean etatDep() {
		return Etats.Deplacement == jeu.etatCourant();
	}

	public boolean etatSel() {
		return Etats.Selection == jeu.etatCourant();
	}

	public boolean etatPla() {
		return Etats.Initialisation == jeu.etatCourant();
	}
}
