package Modele;
import java.util.*;

import Patterns.Observable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Jeu extends Observable {
	int[][] plateau;
	public Joueur[] joueurs;
	int largeur=8;
	int hauteur=8;
	Etats etat = Etats.Initialisation;
	Coup coup;
	public int joueurCourant;
	int nombreP=0;

	public void InitPingou(int l, int c){
		if (plateau[l][c] == 1) {
			joueurs[joueurCourant].addScore(1);
			nombreP++;
			if (nombreP == 8)
				etat = Etats.Selection;

			plateau[l][c] = joueurCourant + 4;

			prochainJoueur();
		}
	}

	public void SelectPingou(int l, int c){
		if (plateau[l][c] == joueurCourant + 4) {
			coup = new Coup(l, c, this);
			etat = Etats.Deplacement;
		}
	}

	public ArrayList<int[]> getCotes(int x, int y){
		ArrayList<int[]>res=new ArrayList<>();
		transformtableau(res,x,y-1);
		transformtableau(res,x,y+1);
		transformtableau(res,x+1,y);
		transformtableau(res,x-1,y);

		if (y%2==0){
			transformtableau(res,x-1,y-1);
			transformtableau(res,x+1,y-1);
		} else {
			transformtableau(res,x+1,y+1);
			transformtableau(res,x-1,y+1);
		}
		return res;
	}

	public void transformtableau(ArrayList<int[]> tab, int x, int y){
		if(x >= 0 && y >= 0 && x < hauteur && y < largeur){
			tab.add(new int[]{x,y});
		}
	}

	public void EnlevePingou(int l, int c){
		ArrayList<int[]> cotes = getCotes(l, c);
		for (int[] cote : cotes) {
			if (plateau[cote[0]][cote[1]] > 3 && hex_accessible(cote[0], cote[1]).isEmpty()){
				plateau[cote[0]][cote[1]] = 0;
				nombreP--;
			}
		}
/* IL FAUT VERIFIER SI LE PINGOUIN DEPLACE SE RETROUVE BLOQUE, SINON IL NE SERA JAMAIS ENLEVE
		if (plateau[l][c] > 3 && hex_accessible(l, c).isEmpty()){
			plateau[l][c] = 0;
			nombreP--;
		}*/
	}

	public void DeplacePingou(int l, int c){
		if (contains(new int[]{l, c}, hex_accessible(coup.sourcel, coup.sourcec))){
			coup.destl = l;
			coup.destc = c;
			coup.execute();
			EnlevePingou(l, c);
			prochainJoueur();
			etat = Etats.Selection;
		} else {
			SelectPingou(l,c);
		}
	}

	public void prochainJoueur() {
		if (nombreP == 0) {
			System.out.println("Partie terminée");
			return;
		}

		joueurCourant=(joueurCourant+1)%this.joueurs.length;
		while (etat != Etats.Initialisation && getPingouins(joueurs[joueurCourant].num).isEmpty())
			joueurCourant = (joueurCourant + 1) % this.joueurs.length;
		System.out.println("Au tour du joueur "+joueurCourant+" score = "+joueurs[joueurCourant].getScore()+" etat = "+getEtat());
		if (joueurs[joueurCourant].estIA){
			if (etat == Etats.Initialisation){
				joueurs[joueurCourant].placement();
			}else{
				joueurs[joueurCourant].jeu();
			}
		}
	}


	boolean contains(int[] valeur, ArrayList<int[]> list){
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


	public Jeu() {
		reset();
	}

	public Jeu( int[][] plateau, int largeur, int hauteur) {

		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateau = plateau.clone();
	}

	public Etats getEtat() {
		return etat;
	}

	public void reset() {
		initPlateau();
		metAJour();
	}

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
//					coup=new Coup(this,l,c);

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
	* Jouer un coup valide
	*/
	public void jouer(int l, int c) {
		Coup coup;
		//coup = new Coup(this, l, c);
	}

	/*
	* Annuler un coup
	*/
	public void annuler(){

	}

	public boolean enCours() {
		return nombreP!=0;
	}

	public int largeur() {
		return plateau[0].length;
	}

	public int hauteur() {
		return plateau.length;
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

	public ArrayList<int[]> hex_accessible(int x,int y){
		ArrayList<int[]>res=new ArrayList<>();
		res.addAll(acc_ligne_inf(x,y-1));
		res.addAll(acc_ligne_sup(x,y+1));

		if (y%2==0){
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

	public int nombreCaseLibre(){
		int r = 0;
		for (int i = 0 ; i < largeur(); i++)
			for (int j = 0; j < hauteur(); j++)
				if(libre(i, j))
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

	/*
	* Manger la gaufre à partir de la coordonnée
	*/


	private void initPlateau(){
		List<Integer> list =new ArrayList<>(Collections.nCopies(30, 1));
		list.addAll(Collections.nCopies(20, 2));
		list.addAll(Collections.nCopies(10, 3));
		Collections.shuffle(list);

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
		return new Jeu(this.plateau,this.largeur,this.hauteur);
	}
}
