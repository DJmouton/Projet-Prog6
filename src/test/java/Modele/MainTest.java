package Modele;
import Controleur.ControleurMediateur;

import static org.junit.jupiter.api.Assertions.*;
public class MainTest {
    Jeu jeu;
    ControleurMediateur control;


    @org.junit.jupiter.api.Test
    void testReset(){
        jeu = new Jeu();

        control=new ControleurMediateur(jeu);


        for (int i=0;i<jeu.hauteur;i++){
            for (int j=0;j<jeu.largeur;j++){
                jeu.plateau[i][j]=0;
            }
        }
        jeu.plateau[0][1]=1;
        jeu.plateau[0][2]=1;
        jeu.plateau[0][3]=1;
        jeu.plateau[1][0]=1;
        jeu.plateau[1][1]=3;
        jeu.plateau[1][2]=1;
        jeu.plateau[1][3]=1;

        jeu.nombreP=2;
        jeu.plateau[0][1]=4;

        jeu.plateau[0][3]=5;
        jeu.SelectPingou(0,1);
        jeu.DeplacePingou(1,1);
        jeu.SelectPingou(0,3);
        jeu.DeplacePingou(1,3);
        jeu.SelectPingou(1,1);
        jeu.DeplacePingou(0,2);

        control.reset();
        assertTrue(jeu.etat==Etats.Initialisation);
        assertTrue(jeu.nombreP==0);
        assertTrue(jeu.joueurCourant==0);

        for (int i=0;i<jeu.hauteur;i++){
            for (int j=0;j<jeu.largeur;j++){
                if(i%2==0 && j==0){
                    assertTrue(jeu.plateau[i][0]==0);
                } else {
                    assertTrue(jeu.plateau[i][j]<4&&jeu.plateau[i][j]>0);
                }
            }
        }

    }
}

