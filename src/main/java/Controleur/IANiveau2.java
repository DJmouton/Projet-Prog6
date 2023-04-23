package Controleur;

import Modele.Jeu;

import java.util.Random;

public class IANiveau2 extends Joueur{
    Random r;
    IANiveau2(int n, Jeu p) {
        super(n, p);
        r = new Random();

    }

    @Override
    boolean tempsEcoule() {
        // Pour cette IA, on choisit une coup non perdant s'il exite
        // sinon, on choisit un coup gagnat s'il existe
        int[][] tableau = this.plateau.coups_possibles();
        // choix d'un coup non perdant
        for(int i=0;i< tableau.length;i++){
            if((tableau[i][0]!=0 && tableau[i][1]!=1) && (tableau[i][0]!=1 && tableau[i][1]!=0)){
                plateau.jouer(tableau[i][0],tableau[i][1]);
                return true;
            }
        }
        // choix d'un coup gagnant
        if(plateau.libre(tableau[0][1],tableau[1][0])){
            plateau.jouer(tableau[1][0],tableau[0][1]);
            return true;
        }
        // si aucun coup: non perdant ou gagnant on fait un choix aleatoire
        int c = r.nextInt(tableau.length);
        int k=0;
        while(k< tableau.length && c!=0){
            if(this.plateau.libre(tableau[k][0],tableau[k][1])){
                c--;
            }
            k++;
        }
        plateau.jouer(tableau[k][0],tableau[k][1]);
        return true;
    }
}
