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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import java.util.LinkedList;



public class Jeu extends Observable {
	boolean enCours;
	int[][] plateau;
	LinkedList<int[][]> liste_plateaux;
	Historique hist;

	public Jeu(int largeur, int hauteur) {
		reset(largeur, hauteur);
	}

	public void reset(int largeur, int hauteur) {
		plateau = new int[hauteur][largeur];
		liste_plateaux = new LinkedList<>();
		hist = new Historique();
		plateau[0][0] = 1;
		enCours = true;
		metAJour();
	}

	public void reset(String fichier) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(fichier);
		Scanner scanner = new Scanner(in);
		hist = new Historique();

		int nbLignes = scanner.nextInt();
		int nbColonnes = scanner.nextInt();
		String s;
		Coup coup;
		int l,c;
		plateau = new int[nbLignes][nbColonnes];
		plateau[0][0] = 1;
		s=scanner.nextLine(); // Coup 2 4
		while (!s.equals("fin")){
			switch (s){
				case "Coup":
					l=scanner.nextInt();
					c=scanner.nextInt();
					coup=new Coup(this,l,c);
					hist.faire(coup);
			}
			s=scanner.nextLine();
		}
		while (scanner.hasNextLine()){
			switch (s){
				case "Coup":
					l=scanner.nextInt();
					c=scanner.nextInt();
					coup=new Coup(this,l,c);
					hist.futur.push(coup);
			}
			s=scanner.nextLine();
		}
	}

	public void sauver(String fichier) throws FileNotFoundException
	{
		// ouvrir fichier sortie

		FileOutputStream out = new FileOutputStream(fichier);
		PrintStream sortie = new PrintStream(out);

		// ecrire dedans

		sortie.println(hauteur());
		sortie.println(largeur());
		for (int i=0;i<hist.passe.size();i++){
			sortie.println(hist.passe.get(i).toString());
		}
		sortie.println("fin");
		for (int i=0;i<hist.futur.size();i++){
			sortie.println(hist.futur.get(i).toString());
		}
	}

	/*
	* Jouer un coup valide
	*/
	public void jouer(int l, int c) {
		Coup coup;
		coup = new Coup(this, l, c);
		hist.faire(coup);
	}

	/*
	* Manger la gaufre à partir de la coordonnée
	*/
	public void manger(int l, int c) {
		if (enCours) {
			// sauvegarder le plateau
			int[][] nouv_plateau = new int[plateau.length][];
			for (int i = 0; i < plateau.length; i++) {
				nouv_plateau[i] = plateau[i].clone();
			}

			liste_plateaux.addLast(nouv_plateau);
			// actualiser le plateau
			for (int i=l; i<hauteur(); i++){
				for(int j=c; j<largeur(); j++){
					plateau[i][j]=1;
				}
			}
			// partie n'est pas finie s'il reste de la gaufre
			boolean flag=false;
			for (int i=0;i<hauteur();i++){
				for (int j=0;j<largeur()&& !flag;j++){
					if (plateau[i][j]==0){
						flag=true;
					}
				}
			}
			enCours=flag;
			// diffuser le changement d'état aux observateurs
			metAJour();
		}
	}

	/*
	* Annuler un coup
	*/
	public void annuler() {
		if (enCours) {
			// actualiser le plateau
			plateau = liste_plateaux.getLast();
			liste_plateaux.removeLast();
			// diffuser le changement d'état aux observateurs
			metAJour();
		}
	}

	/*
	* Verifier si un coup est valide
	*/
	public boolean libre(int i, int j) {
		return valeur(i, j) == 0;
	}

	public int valeur(int i, int j) {
		return plateau[i][j];
	}

	public int nombreCaseLibre(){
		int r = 0;
		for (int i = 0 ; i < largeur(); i++)
			for (int j = 0; j < hauteur(); j++)
				if(libre(i,j))
					r++;
		return r;
	}
	// tableau de coups possibles à partir de l'état courant
	public int[][] coups_possibles(){
		int[][] tableau = new int[nombreCaseLibre()][2];
		int k=0;
		for(int i=0; i<largeur();i++){
			for(int j=0; j<hauteur();j++){
				if(libre(i,j)){
					tableau[k][0]=i;
					tableau[k][1]=j;
					k++;
				}
			}
		}
		return tableau;
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

	public boolean peutAnnuler() {
		return hist.peutAnnuler();
	}

	public boolean peutRefaire() {
		return hist.peutRefaire();
	}

	public void refais() {
		hist.refaire();
	}

	public void annule() {
		hist.annuler();
	}
}
