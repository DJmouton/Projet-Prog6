package Modele;
import java.util.*;

import Patterns.Observable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Jeu extends Observable {
	public int[][] plateau;
	public Joueur[] joueurs;
	int largeur=8;
	int hauteur=8;
	Etats etat = Etats.Initialisation;
	Coup coup;
	public int joueurCourant;
	int nombreP=0;
	int e=0;

	public Jeu() {
		reset();
	}

	public Jeu( int[][] plateau, Joueur[] joueurs, int largeur, int hauteur, Etats etat, int joueurCourant, int nombreP) {
		this.joueurs = new Joueur[joueurs.length];
		for(int i = 0; i < joueurs.length; i++){
			this.joueurs[i] = (Joueur) joueurs[i].clone();
		}
		this.etat = etat;
		this.joueurCourant= joueurCourant;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateau = new int[largeur][hauteur];
		this.nombreP = nombreP;
		for(int i =0; i< largeur; i++){
			for(int j = 0 ;j < hauteur; j++){
				this.plateau[i][j]=plateau[i][j];
			}
		}
	}

	public void reset() {
		largeur=8;
		hauteur=8;
		etat = Etats.Initialisation;
		joueurCourant=0;
		nombreP=0;
		e=0;
		initPlateau();
		metAJour();
	}

	public boolean enCours() {
		if (etat == Etats.Initialisation)
			return true;
		else
			return nombreP!=0;
	}

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
	public int EnlevePingou(int l, int c){
		ArrayList<int[]> cotes = getCotes(l, c);
		for (int[] cote : cotes) {
			if (plateau[cote[0]][cote[1]] > 3 && hex_accessible(cote[0], cote[1]).isEmpty()){
				plateau[cote[0]][cote[1]] = 0;
				nombreP--;
				e++;
			}
		}

		if (plateau[l][c] > 3 && hex_accessible(l, c).isEmpty()){
			plateau[l][c] = 0;
			nombreP--;
			e++;
		}
		return e;
	}

	/******************************************************************
	 * Renvoie la liste des cases accessibles à partir d'une coordonnée
	 *******************************************************************/
	public ArrayList<int[]> hex_accessible(int x,int y){
		ArrayList<int[]>res=new ArrayList<>();
		res.addAll(acc_ligne_inf(x,y-1));
		res.addAll(acc_ligne_sup(x,y+1));

		if (x%2==0){
			res.addAll(acc_diagonal1_inf(x-1,y-1));
			res.addAll(acc_diagonal1_sup(x+1,y));
			res.addAll(acc_diagonal2_inf(x-1,y));
			res.addAll(acc_diagonal2_sup(x+1,y-1));
		} else {
			res.addAll(acc_diagonal1_inf(x-1,y));
			res.addAll(acc_diagonal1_sup(x+1,y+1));
			res.addAll(acc_diagonal2_inf(x-1,y+1));
			res.addAll(acc_diagonal2_sup(x+1,y));
		}
		return res;
	}

	/**************************************************
	 * Renvoie la liste des pingouins portant le numéro
	 ***************************************************/
	public ArrayList<int[]> getPingouins(int num){
		ArrayList<int[]> result = new ArrayList<>();
		for(int i = 0; i < largeur; i++){
			for(int j = 0 ; j < hauteur; j++){
				if(plateau[i][j]==num){
					result.add(new int[]{i,j});
				}
			}
		}
		return result;
	}

	/********************************************
	 * Renvoie si la liste contient la coordonnée
	 *********************************************/
	public boolean contains(int[] valeur, ArrayList<int[]> list){
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
		joueurCourant=(joueurCourant+1)%this.joueurs.length;
		while (etat != Etats.Initialisation && getPingouins(joueurs[joueurCourant].num).isEmpty() && enCours())
			joueurCourant = (joueurCourant + 1) % this.joueurs.length;
	}

	public void changeModeJoueur(int num){
		if(joueurs[num].estIA){
			joueurs[num].estIA=false;
		} else{
			joueurs[num].estIA=true;
		}
	}

	/********************************************************************
	 * Renvoie la liste de joueur tire par rapport a score/ilots et les affiche
	 ********************************************************************/
	public List<Joueur> Ranking(){
		List<Joueur> joueur = new ArrayList<Joueur>();
		for (int i = 0; i < joueurs.length; i++) {
			joueur.add(new Joueur(joueurs[i].score,joueurs[i].num));
		}
		Collections.sort(joueur);
		Collections.reverse(joueur);
		return joueur;
	}

//
//-----------------------------------------------------------------------------------------
//

	private void initPlateau(){
		List<Integer> list =new ArrayList<>(Collections.nCopies(30, 1));
		list.addAll(Collections.nCopies(20, 2));
		list.addAll(Collections.nCopies(10, 3));
		Collections.shuffle(list, new Random());

		int x=0;
		plateau=new int[hauteur][largeur];
		for (int i=0;i<hauteur;i++){
			for (int j=0;j<largeur;j++){
				if(i%2==0 && j==0){
					plateau[i][0]=0;
				} else {
					plateau[i][j] = list.get(x);
					x++;
				}
			}
		}
	}

	private ArrayList<int[]> getCotes(int x, int y){
		ArrayList<int[]>res=new ArrayList<>();
		transformtableau(res,x,y-1);
		transformtableau(res,x,y+1);
		transformtableau(res,x+1,y);
		transformtableau(res,x-1,y);

		if (x%2==0){
			transformtableau(res,x-1,y-1);
			transformtableau(res,x+1,y-1);
		} else {
			transformtableau(res,x+1,y+1);
			transformtableau(res,x-1,y+1);
		}
		return res;
	}

	private void transformtableau(ArrayList<int[]> tab, int x, int y){
		if(x >= 0 && y >= 0 && x < hauteur && y < largeur){
			tab.add(new int[]{x,y});
		}
	}

	private ArrayList<int[]>acc_ligne_inf(int x,int y){
		ArrayList<int[]>res=new ArrayList<>();
		if(y<0||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		res.addAll(acc_ligne_inf(x,y-1));
		return res;
	}

	private ArrayList<int[]>acc_ligne_sup(int x,int y){
		ArrayList<int[]>res=new ArrayList<>();
		if(y>=largeur||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		res.addAll(acc_ligne_sup(x,y+1));
		return res;
	}

	private ArrayList<int[]> acc_diagonal1_inf(int x, int y){
		ArrayList<int[]> res=new ArrayList<>();
		if(x<0 || y<0 ||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		if (x%2==0){
			res.addAll(acc_diagonal1_inf(x-1,y-1));
		} else{
			res.addAll(acc_diagonal1_inf(x-1, y));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal1_sup(int x, int y){
		ArrayList<int[]> res=new ArrayList<>();
		if(x>=hauteur || y>=largeur ||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		if (x%2==0){
			res.addAll(acc_diagonal1_sup(x+1,y));
		} else{
			res.addAll(acc_diagonal1_sup(x+1, y+1));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal2_inf(int x, int y){
		ArrayList<int[]> res=new ArrayList<>();
		if(x<0 || y>=largeur ||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		if (x%2==0){
			res.addAll(acc_diagonal2_inf(x-1,y));
		} else{
			res.addAll(acc_diagonal2_inf(x-1, y+1));
		}
		return res;
	}

	private ArrayList<int[]> acc_diagonal2_sup(int x, int y){
		ArrayList<int[]> res=new ArrayList<>();
		if(x>=hauteur || y<0 ||plateau[x][y]==0||plateau[x][y]>3){
			return res;
		}
		res.add(new int[]{x,y});
		if (x%2==0){
			res.addAll(acc_diagonal2_sup(x+1,y-1));
		} else{
			res.addAll(acc_diagonal2_sup(x+1, y));
		}
		return res;
	}

	@Override
	protected Object clone(){
		Jeu j =  new Jeu(this.plateau,this.joueurs,this.largeur,this.hauteur,this.etat,this.joueurCourant, this.nombreP);
		for(Joueur joueur : j.joueurs){
			joueur.jeu = j;
		}
		return j;
	}

//////////////////////////////////////////////////////////////////////////
//
// FONCTIONS A SUPPRIMER POUR LA PREMIERE VERSION
//
//////////////////////////////////////////////////////////////////////////

	public void reset(String fichier) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(fichier);
		Scanner scanner = new Scanner(in);
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
					//coup=new Coup(this,l,c);

			}
			s=scanner.nextLine();
		}
		while (scanner.hasNextLine()){
			switch (s){
				case "Coup":
					l=scanner.nextInt();
					c=scanner.nextInt();
					//coup=new Coup(this,l,c);

			}
			s=scanner.nextLine();
		}
	}

	public void sauver(String fichier) throws FileNotFoundException {
		// ouvrir fichier sortie

		FileOutputStream out = new FileOutputStream(fichier);
		PrintStream sortie = new PrintStream(out);

		// ecrire dedans

		sortie.println(hauteur());
		sortie.println(largeur());
	}

	/*
	 * Annuler un coup
	 */
	public void annuler(){

	}

	public Etats getEtat() {
		return etat;
	}

	public void setEtat(Etats etat) {
		this.etat = etat;
	}
}