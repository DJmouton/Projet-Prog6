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
package Vue;

import Patterns.Observateur;

import java.util.ArrayList;

/*
 * Tous les évènements à gérer lors de l'éxecution du programme
 */
public interface CollecteurEvenements {

	void nouvellePartie(int j1, int j2, int j3, int j4);

	void recommencer();

	ArrayList<int[]> hexAccessible(int l, int c);

	ArrayList<int[]> getPinguins(int numeroJ);

	int coupSrcL();

	int coupSrcC();

	int coupDestL();

	int coupDestC();

	int hauteur();

	int largeur();

	void ajouteObservateur(Observateur o);

	int valeur(int l, int c);

	boolean estIA();

	boolean etatDep();

	boolean etatSel();

	boolean etatPla();

	void clicSouris(int l, int c);

	void tictac();

	void sauver(String fichier);

	void charger(String fichier);

	void annuler();

	void refaire();

	int joueurCourant();

	int nbJoueurs();

	int scoreJoueur(int joueur);

	boolean partieEnCours();

	int[] ranking();

	boolean dernierCoupEstDeplacement();

	int[] pingSel();

	int nombreJoueurs();

	boolean peutAnnuler();

	boolean peutRefaire();
}
