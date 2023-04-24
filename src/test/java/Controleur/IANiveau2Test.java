package Controleur;

import Modele.Jeu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IANiveau2Test {
    Jeu j;
    @Test
    void TestIA2() {
        j=new Jeu(6, 6);
        // Test pour un cas où il y a un seul coup possible
        j.jouer(0,1);
        j.jouer(2,0);
        ControleurMediateur c1 = new ControleurMediateur(j);
        assertTrue(c1.joueurs[0][1].tempsEcoule());
        j=new Jeu(6,6);
        j.jouer(1,0);
        j.jouer(0,2);
        ControleurMediateur c2 = new ControleurMediateur(j);
        assertTrue(c2.joueurs[0][1].tempsEcoule());
        // Test pour un cas où il y a un coup non perdant possible
        j=new Jeu(6,6);
        j.jouer(0,2);
        j.jouer(2,0);
        ControleurMediateur c3 = new ControleurMediateur(j);
        assertTrue(c3.joueurs[0][1].tempsEcoule());
        // Test pour un cas où il y a un coup gagnant possible
        j=new Jeu(6,6);
        j.jouer(0,1);
        ControleurMediateur c4 = new ControleurMediateur(j);
        assertTrue(c4.joueurs[0][1].tempsEcoule());
        // Test pour un cas où il n'y a aucun coup non perdant ou gagnant possible
        j=new Jeu(6,6);
        ControleurMediateur c5 = new ControleurMediateur(j);
        assertTrue(c5.joueurs[0][1].tempsEcoule());
        j=new Jeu(6,6);
        j.jouer(0,2);
        j.jouer(2,0);
        ControleurMediateur c6 = new ControleurMediateur(j);
        assertTrue(c6.joueurs[0][1].tempsEcoule());
    }
}