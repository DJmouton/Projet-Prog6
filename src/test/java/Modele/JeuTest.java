package Modele;

import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
    Jeu jeu;

    @org.junit.jupiter.api.Test
    void jouerInfDroit() {
        jeu=new Jeu(8, 8);
        int l=7, c=7;
        jeu.jouer(l, c);
        for (int i = 0; i < jeu.plateau.length; i++) {
            for (int j = 0; j < jeu.plateau[0].length; j++) {
                if (i == l && j == c)
                    assertEquals(1, jeu.plateau[i][j]);
                else
                    assertEquals(0, jeu.plateau[i][j]);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void jouerMilieu() {
        jeu=new Jeu(8, 8);
        int l=4, c=4;
        jeu.jouer(l, c);
        for (int i = 0; i < jeu.plateau.length; i++) {
            for (int j = 0; j < jeu.plateau[0].length; j++) {
                if (i >= l && j >= c)
                    assertEquals(1, jeu.plateau[i][j]);
                else
                    assertEquals(0, jeu.plateau[i][j]);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void jouerSupGauche() {
        jeu=new Jeu(8, 8);
        int l=0, c=0;
        jeu.jouer(l, c);
        for (int i = 0; i < jeu.plateau.length; i++)
            for (int j = 0; j < jeu.plateau[0].length; j++)
                assertEquals(1, jeu.plateau[i][j]);
    }

    @org.junit.jupiter.api.Test
    void jouerInvalide() {
        jeu=new Jeu(8, 8);
        int l=3, c=6;
        jeu.jouer(l, c);
        int l2=4, c2=7;
        jeu.jouer(l2, c2);
        for (int i = 0; i < jeu.plateau.length; i++) {
            for (int j = 0; j < jeu.plateau[0].length; j++) {
                if (i >= l && j >= c)
                    assertEquals(1, jeu.plateau[i][j]);
                else
                    assertEquals(0, jeu.plateau[i][j]);
            }
        }
    }
}