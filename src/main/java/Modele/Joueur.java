package Modele;

// Classe commune à tous les joueurs : IA ou humain
// L'idée est que, en ayant la même interface, tous les joueurs sont traités de la même
// manière par le moteur de jeu. C'est plus simple et permet toutes les combinaisons.
//
// Tous les joueurs ont donc potentiellement la possibilité de :
// - provoquer une temporisation (utilisé dans une IA)
// - tenir compte d'une temporisation écoulée (utilisé dans une IA)
// - tenir compte d'un coup joué à la souris (utilisé par un joueur humain)
public class Joueur implements Comparable<Joueur> {
	Jeu jeu;
	int typeJoueur; // 0=absent, 1=humain, 2=IAfacile, 3=IAnormale, 4=IAdifficile
	int num;
	int score;
	int ilots;


	public Joueur(int n, Jeu p) {
		num = n;
		jeu = p;
	}

	public Joueur(Jeu jeu, int num, int score, int typeJoueur) {
		this.jeu = jeu;
		this.num = num;
		this.score = score;
		this.typeJoueur = typeJoueur;
	}

	public Joueur(int score, int num) {
		this.score = score;
		this.num = num;

	}

	public int getTypeJoueur() {
		return this.typeJoueur;
	}

	public int getScore() {
		return score;
	}

	public int getNum() {
		return num;
	}

	public int getIlots() {
		return ilots;
	}


	public void addScore(int val) {
		score += val;
	}

	public void addIlots() {
		ilots++;
	}

	@Override
	protected Object clone() {
		return new Joueur(jeu, num, score, typeJoueur);
	}

	@Override
	public int compareTo(Joueur j) {
		return this.score - j.score;
	}
}