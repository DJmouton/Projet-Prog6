package Modele;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Modele.Historique;
import Patterns.Observable;
import Patterns.Commande;

public class Jeu extends Observable {
	public int[][] plateau;
	int[][] copy_plateau ;
	public Joueur[] joueurs;
	int largeur;
	int hauteur;
	Etats etat ;
	Coup coup;
	public int joueurCourant;
	int nombreP;
	int nombrePAvoir;
	int e ;
	public Historique historique;

	public Jeu() {
		reset();
	}

	public Jeu(int[][] plateau, Joueur[] joueurs, int largeur, int hauteur, Etats etat, int joueurCourant, int nombreP,int nombrePAvoir,int e) {
		this.joueurs = new Joueur[joueurs.length];
		for (int i = 0; i < joueurs.length; i++) {
			this.joueurs[i] = (Joueur) joueurs[i].clone();
		}
		this.etat = etat;
		this.joueurCourant = joueurCourant;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateau = new int[largeur][hauteur];
		this.nombreP = nombreP;
		this.nombrePAvoir=nombrePAvoir;
		this.e=e;
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				this.plateau[i][j] = plateau[i][j];
			}
		}
	}

	public void reset() {
		largeur = 8;
		hauteur = 8;
		etat = Etats.Initialisation;
		joueurCourant = 0;
		nombreP = 0;
		e = 0;
		historique = new Historique();
		//initPlateau();
		//initJoueurs();
		metAJour();
	}

	public boolean enCours() {
		if (etat == Etats.Initialisation)
			return true;
		else
			return nombreP != 0;
	}
	public int getNombreP(){return nombreP;}
	public int getnombrePAvoir(){return nombrePAvoir;}
	public int getE(){return e;}

	public int largeur() {
		return largeur;
	}

	public int hauteur() {
		return hauteur;
	}

	public Etats etatCourant() {
		return etat;
	}

	public int valeur(int i, int j) {
		return plateau[i][j];
	}

	// renvoie si la case à ces coordonnées est un ilot non occupé
	public boolean libre(int i, int j) {
		return valeur(i, j) > 0 && valeur(i, j) < 4;
	}


	/***************************************************
	 * Enlève tous les nouveaux pingouins bloqués du jeu
	 ****************************************************/
	public int EnlevePingou(int l, int c) {
		ArrayList<int[]> cotes = getCotes(l, c);
		for (int[] cote : cotes) {
			if (plateau[cote[0]][cote[1]] > 3 && hex_accessible(cote[0], cote[1]).isEmpty()) {
				plateau[cote[0]][cote[1]] = 8;
				nombreP--;
				e++;
			}
		}

		if (plateau[l][c] > 3 && hex_accessible(l, c).isEmpty()) {
			plateau[l][c] = 8;
			nombreP--;
			e++;
		}
		return e;
	}

	/******************************************************************
	 * Renvoie la liste des cases accessibles à partir d'une coordonnée
	 *******************************************************************/
	public ArrayList<int[]> hex_accessible(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		res.addAll(acc_ligne_inf(x, y - 1));
		res.addAll(acc_ligne_sup(x, y + 1));

		if (x % 2 == 0) {
			res.addAll(acc_diagonal1_inf(x - 1, y - 1));
			res.addAll(acc_diagonal1_sup(x + 1, y));
			res.addAll(acc_diagonal2_inf(x - 1, y));
			res.addAll(acc_diagonal2_sup(x + 1, y - 1));
		} else {
			res.addAll(acc_diagonal1_inf(x - 1, y));
			res.addAll(acc_diagonal1_sup(x + 1, y + 1));
			res.addAll(acc_diagonal2_inf(x - 1, y + 1));
			res.addAll(acc_diagonal2_sup(x + 1, y));
		}
		return res;
	}

	/**************************************************
	 * Renvoie la liste des pingouins portant le numéro
	 ***************************************************/
	public ArrayList<int[]> getPingouins(int num) {
		ArrayList<int[]> result = new ArrayList<>();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (plateau[i][j] == num) {
					result.add(new int[]{i, j});
				}
			}
		}
		return result;
	}

	/*********************************************************************
	 * Renvoie les déplacements possibles des pingouins portant le numéro
	 *********************************************************************/
	public ArrayList<Commande> getCoups(Jeu jeu, int num) {
		ArrayList<Commande> coups = new ArrayList<>();
		if (jeu.getEtat().equals(Etats.Initialisation)) {
			for (int l = 0; l < jeu.plateau.length; l++) {
				for (int c = 0; c < jeu.plateau[l].length; c++) {
					if (jeu.plateau[l][c] == 1) {
						coups.add(new Placement(jeu, l, c));
					}
				}
			}
		} else {
			ArrayList<int[]> pingouins = jeu.getPingouins(num);
			for (int[] pingouin : pingouins) {
				for (int[] emplacement : jeu.hex_accessible(pingouin[0], pingouin[1])) {
					coups.add(new Coup(jeu, pingouin[0], pingouin[1], emplacement[0], emplacement[1]));
				}
			}
		}
		return coups;
	}

	/********************************************
	 * Renvoie si la liste contient la coordonnée
	 *********************************************/
	public boolean contains(int[] valeur, ArrayList<int[]> list) {
		boolean res = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).length == valeur.length) {
				res = true;
				for (int j = 0; j < valeur.length; j++) {
					if (list.get(i)[j] != valeur[j]) {
						res = false;
						break;
					}
				}

				if (res) return res;
			}
		}

		return res;
	}

	public void prochainJoueur() {
		joueurCourant = (joueurCourant + 1) % this.joueurs.length;
		while (etat != Etats.Initialisation && getPingouins(joueurs[joueurCourant].num).isEmpty() && enCours())
			joueurCourant = (joueurCourant + 1) % this.joueurs.length;
	}


	/********************************************************************
	 * Renvoie la liste de joueur tire par rapport a score/ilots et les affiche
	 ********************************************************************/
	public List<Joueur> Ranking() {
		List<Joueur> joueur = new ArrayList<Joueur>();
		for (int i = 0; i < joueurs.length; i++) {
			joueur.add(new Joueur(joueurs[i].score, joueurs[i].num));
		}
		Collections.sort(joueur);
		Collections.reverse(joueur);
		return joueur;
	}

	public void initPlateau() {
		System.out.println("init plateau");
		List<Integer> list = new ArrayList<>(Collections.nCopies(30, 1));
		list.addAll(Collections.nCopies(20, 2));
		list.addAll(Collections.nCopies(10, 3));
		Random random = new Random();
		long seed = random.nextLong();
		System.out.println("Seed Plateau: "+seed);
		random.setSeed(seed);
		Collections.shuffle(list, new Random());

		int x = 0;
		plateau = new int[hauteur][largeur];
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				if (i % 2 == 0 && j == 0) {
					plateau[i][0] = 0;
				} else {
					plateau[i][j] = list.get(x);
					x++;
				}
			}
		}
		copy_plateau = new int[hauteur()][largeur()];
		for (int i = 0; i < plateau.length; i++) {
			copy_plateau[i] = plateau[i].clone();
		}
	}

	public void initJoueurs(List<Integer> typesJoueurs) {
		int typeJoueur;
		for (int i = 0; i < typesJoueurs.size(); i++) {
			typeJoueur = typesJoueurs.get(i);
			if (typeJoueur == 0) {
				typesJoueurs.remove(i);
				i--;
			}
		}
		joueurs = new Joueur[typesJoueurs.size()];
		if(typesJoueurs.size()==3){nombrePAvoir=9;}
		else{nombrePAvoir=8;}
		for (int i = 0; i < typesJoueurs.size(); i++) {
			typeJoueur = typesJoueurs.get(i);
			if (typeJoueur == 1) {
				joueurs[i] = new Joueur(this, i + 4, 0, typeJoueur);
			} else if (typeJoueur >= 2) {
				joueurs[i] = new IA(i + 4, this, typeJoueur);
			}

		}
	}

	public void resetJoueur() {
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].score = 0;
			joueurs[i].ilots = 0;
		}
	}

	public boolean peutAnnuler() {
		return historique.peutAnnuler();
	}

	public boolean peutRefaire() {
		return historique.peutRefaire();
	}

	public void annuler() {
		for (int i = 0; i < copy_plateau.length; i++) {
			plateau[i] = copy_plateau[i].clone();
		}
		nombreP=0;
		e=0;
		joueurCourant=0;
		resetJoueur();
		historique.annuler();
		//prochainJoueur();
	}

	public void refaire() {
		historique.refaire();
	}

	public void faire(Commande cmd) {
		historique.faire(cmd);
	}

	public Etats getEtat() {
		return etat;
	}

	public void setEtat(Etats etat) {
		this.etat = etat;
	}

	public void sauver(String fichier) throws IOException {
		// ouvrir fichier sortie

		FileOutputStream out = new FileOutputStream(fichier);
		PrintStream sortie = new PrintStream(out);

		// ecrire dedans

		sortie.println(hauteur());
		sortie.println(largeur());

		sortie.println(joueurs.length);
		for (int i=0;i<joueurs.length;i++){
			sortie.println(joueurs[i].typeJoueur);
		}

		//ecrire le plateau init. dans le fichier (Serialization)
		for(int i=0;i<hauteur();i++){
			for(int j=0;j<largeur();j++){
				sortie.println(copy_plateau[i][j]);
			}

		}

		//ecrire historique
		for (int i=0;i<historique.passe.size();i++){
			sortie.println(historique.passe.get(i).toString());
		}
		sortie.println("fin");
		for (int i=0;i<historique.futur.size();i++){
			sortie.println(historique.futur.get(i).toString());
		}
	}

	public void charger (String fichier) throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream(fichier);
		Scanner scanner = new Scanner(in);
		int nbLignes = scanner.nextInt();
		int nbColonnes = scanner.nextInt();
		String s;
		Coup coup;
		Commande cmd;
		int sl, sc,dl,dc;
		 //init. joueurs
		int joueursLen=scanner.nextInt();
		List<Integer> listeJoueur=new ArrayList<>();

		reset();

		for(int i=0;i<joueursLen;i++) {
			listeJoueur.add(scanner.nextInt());
		}
		initJoueurs(listeJoueur);

		//init. plateau
		for(int i=0;i<nbLignes;i++){
			for(int j=0;j<nbColonnes;j++){
				plateau[i][j] = scanner.nextInt();
			}
		}

		copy_plateau = new int[hauteur()][largeur()];
		for (int i = 0; i < plateau.length; i++) {
			copy_plateau[i] = plateau[i].clone();
		}



		//exec. historique.past
		s=scanner.nextLine(); // Coup 2 4
		while (!s.equals("fin")){
			switch (s){
				case "Placement":
					dl=scanner.nextInt();
					dc=scanner.nextInt();
					historique.faire(new Placement(this,dl,dc));
					break;
				case "Coup":
					sl=scanner.nextInt();
					sc=scanner.nextInt();
					dl=scanner.nextInt();
					dc=scanner.nextInt();
					coup=new Coup(this,sl,sc,dl,dc);
					faire(coup);
					break;
			}
			s=scanner.nextLine();
		}
		while (scanner.hasNextLine()){
			switch (s){
				case "Placement":
					dl=scanner.nextInt();
					dc=scanner.nextInt();
					historique.futur.push(new Placement(this,dl,dc));
					break;
				case "Coup":
					sl=scanner.nextInt();
					sc=scanner.nextInt();
					dl=scanner.nextInt();
					dc=scanner.nextInt();
					coup=new Coup(this,sl,sc,dl,dc);
					historique.futur.push(coup);
					break;
			}
			s=scanner.nextLine();
		}
 	}


//
//-----------------------------------------------------------------------------------------
//

    /*private void initJoueurs(){
		joueurs = new Joueur[2];
		joueurs[0] = new IA(4, this);
		joueurs[1] = new Joueur(5, this);
	}*/

	public ArrayList<int[]> getCotes(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		transformtableau(res, x, y - 1);
		transformtableau(res, x, y + 1);
		transformtableau(res, x + 1, y);
		transformtableau(res, x - 1, y);

		if (x % 2 == 0) {
			transformtableau(res, x - 1, y - 1);
			transformtableau(res, x + 1, y - 1);
		} else {
			transformtableau(res, x + 1, y + 1);
			transformtableau(res, x - 1, y + 1);
		}
		return res;
	}

	private void transformtableau(ArrayList<int[]> tab, int x, int y) {
		if (x >= 0 && y >= 0 && x < hauteur && y < largeur) {
			tab.add(new int[]{x, y});
		}
	}

	private ArrayList<int[]> acc_ligne_inf(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (y < 0 || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		res.addAll(acc_ligne_inf(x, y - 1));
		return res;
	}

	private ArrayList<int[]> acc_ligne_sup(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (y >= largeur || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		res.addAll(acc_ligne_sup(x, y + 1));
		return res;
	}

	private ArrayList<int[]> acc_diagonal1_inf(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (x < 0 || y < 0 || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		if (x % 2 == 0) {
			res.addAll(acc_diagonal1_inf(x - 1, y - 1));
		} else {
			res.addAll(acc_diagonal1_inf(x - 1, y));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal1_sup(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (x >= hauteur || y >= largeur || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		if (x % 2 == 0) {
			res.addAll(acc_diagonal1_sup(x + 1, y));
		} else {
			res.addAll(acc_diagonal1_sup(x + 1, y + 1));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal2_inf(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (x < 0 || y >= largeur || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		if (x % 2 == 0) {
			res.addAll(acc_diagonal2_inf(x - 1, y));
		} else {
			res.addAll(acc_diagonal2_inf(x - 1, y + 1));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal2_sup(int x, int y) {
		ArrayList<int[]> res = new ArrayList<>();
		if (x >= hauteur || y < 0 || plateau[x][y] == 0 || plateau[x][y] > 3) {
			return res;
		}
		res.add(new int[]{x, y});
		if (x % 2 == 0) {
			res.addAll(acc_diagonal2_sup(x + 1, y - 1));
		} else {
			res.addAll(acc_diagonal2_sup(x + 1, y));
		}
		return res;
	}

	@Override
	protected Object clone() {
		Jeu j = new Jeu(this.plateau, this.joueurs, this.largeur, this.hauteur, this.etat, this.joueurCourant, this.nombreP,this.nombrePAvoir,this.e);
		for (Joueur joueur : j.joueurs) {
			joueur.jeu = j;
		}
		return j;
	}

//////////////////////////////////////////////////////////////////////////
//
// FONCTIONS A SUPPRIMER POUR LA PREMIERE VERSION
//
//////////////////////////////////////////////////////////////////////////




	/*
	 * Annuler un coup
	 */
}