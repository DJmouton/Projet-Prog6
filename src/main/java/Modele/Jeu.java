package Modele;

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

import Patterns.Observable;

public class Jeu extends Observable {
	boolean enCours;
	int[][] plateau;
	int libre;
	int joueurCourant;

	public Jeu(int n) {
		reset(n);
	}

	public void reset(int n) {
		plateau = new int[n][n];
		libre = n*n;
		enCours = true;
		for (int i = 0; i < plateau.length; i++)
			for (int j = 0; j < plateau[0].length; j++)
				plateau[i][j] = -1;
		joueurCourant = 0;
		metAJour();
	}

	public void jouer(int l, int c) {
		if (enCours && (plateau[l][c] == -1)) {
			plateau[l][c] = joueurCourant;
			libre--;
			boolean vertical = true, horizontal = true, slash = true, antiSlash = true;
			for (int p = 0; p < plateau.length; p++) {
				horizontal = horizontal && (plateau[l][p] == joueurCourant);
				vertical = vertical && (plateau[p][c] == joueurCourant);
				slash = slash && (plateau[p][p] == joueurCourant);
				antiSlash = antiSlash && (plateau[p][plateau.length - p - 1] == joueurCourant);
			}
			enCours = !(horizontal || vertical || slash || antiSlash) && (libre > 0);
			joueurCourant = 1 - joueurCourant;
			metAJour();
		}
	}

	public boolean libre(int i, int j) {
		return valeur(i, j) == -1;
	}

	public int valeur(int i, int j) {
		return plateau[i][j];
	}

	public boolean enCours() {
		return enCours;
	}

	public int largeur() {
		return plateau[0].length;
	}

	public int hauteur() {
		return plateau.length;
	}
}
