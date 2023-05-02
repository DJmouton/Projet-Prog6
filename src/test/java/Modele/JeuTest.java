package Modele;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
    Jeu jeu;

    //
    // IDEES DE TESTS :
    //
    // - tester toutes les fonctions public séparément (même si pas grand chose à tester, faire un petit test)
    // - simuler une partie casual en vérifiant tout l'état du jeu pour éviter d'en faire une à la main
    //

    @org.junit.jupiter.api.Test
    void hex_accessible_test1() {
        jeu = new Jeu();

        for (int i=0;i<jeu.hauteur;i++){
            for (int j=0;j<jeu.largeur;j++){
                jeu.plateau[i][j]=0;
            }
        }
        ArrayList<int[]> actual = jeu.hex_accessible( 0,1);
        assertTrue(actual.isEmpty());
        jeu.plateau[3][4]=1;
        actual = jeu.hex_accessible( 3,4);
        assertTrue(actual.isEmpty());
        jeu.plateau[4][5]=2;
        actual = jeu.hex_accessible( 3,4);
        assertTrue(jeu.contains(new int[]{4,5},actual));
        jeu.plateau[3][4]=4;
        actual = jeu.hex_accessible( 3,4);
        assertTrue(jeu.contains(new int[]{4,5},actual));
        actual = jeu.hex_accessible( 4,5);
        assertTrue(actual.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void hex_accessible_test2() {
        jeu = new Jeu();

        jeu.plateau[4][4]=4;
        ArrayList<int[]> actual = jeu.hex_accessible( 2,3);
        assertTrue(jeu.contains(new int[]{1,2},actual));
        assertTrue(jeu.contains(new int[]{0,2},actual));
        assertFalse(jeu.contains(new int[]{2,3},actual));
        assertTrue(jeu.contains(new int[]{3,3},actual));
        assertFalse(jeu.contains(new int[]{4,4},actual));
        assertFalse(jeu.contains(new int[]{5,4},actual));
        assertFalse(jeu.contains(new int[]{6,5},actual));
        assertFalse(jeu.contains(new int[]{7,5},actual));
        actual = jeu.hex_accessible( 1,5);
        assertTrue(jeu.contains(new int[]{0,6},actual));
        assertFalse(jeu.contains(new int[]{1,5},actual));
        assertTrue(jeu.contains(new int[]{2,5},actual));
        assertTrue(jeu.contains(new int[]{3,4},actual));
        assertFalse(jeu.contains(new int[]{4,4},actual));
        assertFalse(jeu.contains(new int[]{5,3},actual));
        assertFalse(jeu.contains(new int[]{6,3},actual));
        assertFalse(jeu.contains(new int[]{7,2},actual));

        for (int i=0;i<jeu.hauteur;i++){
            for (int j=0;j<jeu.largeur;j++){
                if (i==0||i==2)
                    jeu.plateau[i][j]=1;
                else{jeu.plateau[i][j]=0;}
            }
        }
        jeu.plateau[2][1]=4;
        actual = jeu.hex_accessible( 2,1);
        assertFalse(jeu.contains(new int[]{0,3},actual));
        for (int i=2;i<jeu.largeur;i++) {
            assertTrue(jeu.contains(new int[]{2,i},actual));
        }
        jeu.plateau[2][4]=0;
        actual = jeu.hex_accessible( 2,1);
        for (int i=4;i<jeu.largeur;i++){
            assertFalse(jeu.contains(new int[]{2,i},actual));
        }
    }
}