package Controleur;

import Modele.*;
import Patterns.Observateur;
import Vue.CollecteurEvenements;
import Vue.ComposantPanneauFinPartie;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControleurMediateur implements CollecteurEvenements {
	public int[] PingSel;
	Jeu jeu;
	Coup coup;
	Thread thread;
	int poissons;

	int[] ranks;

	boolean consultation;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		nouvellePartie(1, 2, 0, 0);
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
		jeu.metAJour();
		tour();
		jeu.metAJour();
	}

	public void recommencer() {
		int[] typesJoueurs = new int[4];
		for (int i = 0; i < 4; i++) {
			if (i < jeu.joueurs.length)
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
		jeu.metAJour();
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
				} else if (jeu.getNombreP() == jeu.getnombrePAvoir() - jeu.getE()) {
					jeu.setEtat(Etats.Selection);
				} else {
					System.out.println("Un pingouin doit être placé sur un ilot à 1 poisson");
				}
				break;

			case Selection:
				if (jeu.plateau[l][c] == jeu.joueurCourant + 4) {
					coup = new Coup(l, c, this.jeu);
					PingSel = new int[]{l, c};
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
					PingSel = new int[]{l, c};
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
				thread = new Thread(new IAPlacement(this, jeu, ((IA) jeu.joueurs[jeu.joueurCourant])));
				thread.start();
				break;

			case Selection:
				thread = new Thread(new IACoup(this, jeu, ((IA) jeu.joueurs[jeu.joueurCourant])));
				thread.start();
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

		if (jeu.joueurs[joueurCourant()].getTypeJoueur() > 1 && !consultation) {
			System.out.println("L'IA réfléchit...");
			tictac();
		} else
			System.out.println();
	}

	/***************************
	 * Gestion de fin de partie
	 ***************************/
	public void finPartie() {
		System.out.println("--------------------------------------------------");
		System.out.println("Partie terminée, voici le classement final :");
		afficheRanking(jeu.Ranking());
		ComposantPanneauFinPartie fin_partie = new ComposantPanneauFinPartie(this);
		Object[] inputs = {fin_partie};
		Object[] options = {"Bien joué!"};
		JOptionPane.showOptionDialog(
				null,
				inputs,
				"Fin de Partie",
				0,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				0
		);
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
				if (joueur.get(i).getIlots() < joueur.get(i + 1).getIlots()) {
					Joueur temp = joueur.get(i);
					joueur.remove(i);
					joueur.add(i + 1, temp);
				} else if (joueur.get(i).getIlots() == joueur.get(i + 1).getIlots()) {
					System.out.println("EGALITE");
				}
			}
			System.out.println("Joueur " + (joueur.get(i).getNum() - 4) + " avec " + joueur.get(i).getScore() + " poissons et " + jeu.joueurs[joueur.get(i).getNum() - 4].getIlots() + " ilots");
			ranks[i] = joueur.get(i).getNum() - 4;

		}
	}

	/**************************
	 * Annuler le dernier coup
	 **************************/
	public void annuler() {
		if (jeu.peutAnnuler()) {
			do {
				jeu.setEtat(Etats.Initialisation);
				jeu.annuler();
			} while (jeu.peutAnnuler() && jeu.joueurs[joueurCourant()].getTypeJoueur() > 1);
			if (jeu.getNombreP() == jeu.getnombrePAvoir() - jeu.getE()) {
				jeu.setEtat(Etats.Selection);
			}
			// le source du coup doit être celui du sommet de la pile du passe
			SourceCoup();
			consultation = true;
			tour();
		}
	}

	/********************************
	 * Refaire le dernier coup Annule
	 ********************************/
	public void refaire() {
		if (jeu.peutRefaire()) {
			do {
				jeu.refaire();
			} while (jeu.peutRefaire() && jeu.joueurs[joueurCourant()].getTypeJoueur() > 1);

			if (!jeu.peutRefaire())
				consultation = false;
			// le source du coup doit être celui du sommet de la pile passe
			SourceCoup();
			tour();
		}
	}

	/*************************************************************************************************
	 * Remet l'état du plateau au bon endroit pour l'affichage après une annulation ou un coup refait
	 *************************************************************************************************/
	private void SourceCoup() {
		if (jeu.historique.passe.size() > 0 && jeu.historique.passe.get(jeu.historique.passe.size() - 1) instanceof Coup) {
			coup = ((Coup) jeu.historique.passe.get(jeu.historique.passe.size() - 1));
		}
		if (jeu.getNombreP() == jeu.getnombrePAvoir() - jeu.getE()) {
			jeu.setEtat(Etats.Selection);
		}
		PingSel = new int[]{0, 0};
		jeu.metAJour();
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
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if (jeu.getNombreP() == jeu.getnombrePAvoir() - jeu.getE()) {
			jeu.setEtat(Etats.Selection);
		}
		jeu.metAJour();
		System.out.println("Partie chargée");
		tour();

	}

	public int joueurCourant() {
		return jeu.joueurCourant;
	}

	public int nbJoueurs() {
		return jeu.joueurs.length;
	}

	public int scoreJoueur(int joueur) {
		return jeu.joueurs[joueur].getScore();
	}

	public boolean estIA() {
		return jeu.joueurs[joueurCourant()].getTypeJoueur() > 1;
	}

	public boolean estIA(int j) {
		return jeu.joueurs[j].getTypeJoueur() > 1;
	}

	public boolean isConsultation() {
		return consultation;
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

	public ArrayList<int[]> getCases(int numeroJ) {
		return jeu.getCases(numeroJ);
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

	public boolean dernierCoupEstDeplacement() {
		return jeu.historique.passe.get(jeu.historique.passe.size() - 1) instanceof Coup;
	}

	public int[] pingSel() {
		return PingSel;
	}

	public int nombreJoueurs() {
		return jeu.joueurs.length;
	}

	public boolean peutAnnuler() {
		return jeu.historique.peutAnnuler();
	}

	public boolean peutRefaire() {
		return jeu.historique.peutRefaire();
	}
}
