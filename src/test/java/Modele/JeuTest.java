package Modele;

import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
     Jeu jeu;
    //@Before public void init(){


    @org.junit.jupiter.api.Test

    void jouer() {
        jeu=new Jeu(16);
        jeu.jouer(jeu.plateau.length -1,jeu.plateau[0].length -1 );
        assertEquals(1,jeu.plateau[jeu.plateau.length -1][jeu.plateau[0].length -1 ]);
        jeu.jouer((jeu.plateau.length -1)/2,(jeu.plateau[0].length -1)/2 );
        for (int i=(jeu.plateau.length -1)/2; i<jeu.plateau.length -1; i++) {
            for(int j=(jeu.plateau[0].length -1)/2; j<jeu.plateau[0].length-1; j++ ){
                assertEquals(1, jeu.plateau[i][j]);
            }
        }
        jeu.jouer(0,0);
        for (int i=0; i<jeu.plateau.length; i++){
            for (int j=0; j<jeu.plateau[0].length; j++){
                assertEquals(1, jeu.plateau[i][j]);
            }
        }
    }
}