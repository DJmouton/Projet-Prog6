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
        int taille = this.plateau.nombreCaseLibre();
        // choix d'un coup non perdant
        for(int i=0; i<plateau.largeur();i++){
            for(int j=0;j<plateau.hauteur();j++){
                if((i!=0 && j!=1) && (i!=1 && j!=0)){
                    plateau.jouer(i,j);
                    return true;
                }
            }
        }
        // choix d'un coup gagnant
        if(!plateau.libre(0,1) && plateau.libre(1,0)){
            plateau.jouer(1, 0);
            return true;
        } else if (!plateau.libre(1,0) && plateau.libre(0,1)) {
            plateau.jouer(1,0);
            return true;
        }
        System.out.println("uiyhjkl");
        // si aucun coup non perdant ou gagnant on fait un choix aleatoire
        int c = r.nextInt(taille);
        int i;
        int j=0;
        if(c==0){
            int[][] table = this.plateau.coups_possibles();
            this.plateau.jouer(table[0][0], table[0][1]);
            return true;
        }
        for (i = 0; i < this.plateau.largeur(); i++){
            for (j = 0; j < this.plateau.hauteur(); j++){
                if(this.plateau.libre(i,j)){
                    c--;
                }
                if(c == 0){
                    break;
                }
            }
            if(c == 0){
                break;
            }
        }
        plateau.jouer(i,j);
        return true;
    }
}