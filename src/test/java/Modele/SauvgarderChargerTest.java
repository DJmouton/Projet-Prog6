package Modele;

import Controleur.ControleurMediateur;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SauvgarderChargerTest {
    Jeu jeu;
    ControleurMediateur control;
    @org.junit.jupiter.api.Test
    void Test1(){
        jeu = new Jeu();
        control=new ControleurMediateur(jeu);
        Coup coup = null;

        control.nouvellePartie(1,1,0,0);
        jeu.plateau[1][0]=1;
        jeu.plateau[1][1]=1;

        jeu.faire(new Placement(jeu, 1, 0));
        jeu.faire(new Placement(jeu, 1, 1));

        int[][] copy_plateau = new int[jeu.hauteur()][jeu.largeur()];
        for (int i = 0; i < jeu.plateau.length; i++) {
            copy_plateau[i] = jeu.plateau[i].clone();
        }
        control.sauver("save2");
        for(int i=0;i<jeu.hauteur();i++){
            for(int j=0;j<jeu.largeur();j++){
                assertTrue(jeu.plateau[i][j]==copy_plateau[i][j]);
            }
        }
        control.reset();
        control.charger("save2");
        for(int i=0;i<jeu.hauteur();i++){
            for(int j=0;j<jeu.largeur();j++){
                assertEquals(jeu.plateau[i][j], copy_plateau[i][j]);
            }
        }
    }

}
