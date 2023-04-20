package Controleur;

/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Modele.Jeu;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Joueur[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		joueurs = new Joueur[2][2];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(i, jeu);
			joueurs[i][1] = new JoueurIA(i, jeu);
			typeJoueur[i] = 0;
		}
	}

	@Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(l, c))
			changeJoueur();
	}

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

	public void tictac() {
		if (jeu.enCours()) {
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
			}
		}
	}

	@Override
	public void changeJoueur(int j, int t) {
		System.out.println("Nouveau type " + t + " pour le joueur " + j);
		typeJoueur[j] = t;
	}

	@Override
	public void changeTaille(int t) {
		System.out.println("Nouvelle taille " + t);
		jeu.reset(t);
	}
}
