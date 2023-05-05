package Modele;
import Controleur.ControleurMediateur;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class RankTest {
    Jeu jeu;
    ControleurMediateur control;

    @org.junit.jupiter.api.Test
    void rankingTest0() {
        jeu=new Jeu();
        control=new ControleurMediateur(jeu);
        jeu.joueurs[0].score=10;
        jeu.joueurs[1].score=9;
        jeu.joueurs[0].ilots=6;
        jeu.joueurs[1].ilots=4;

        List<Joueur> j= jeu.Ranking();
        assertTrue(j.get(0).num==4);
        assertTrue(j.get(1).num==5);
        control.afficheRanking(j);




    }
    @org.junit.jupiter.api.Test
    void rankingTest1() {
        jeu=new Jeu();
        control=new ControleurMediateur(jeu);
        jeu.joueurs[0].score=10;
        jeu.joueurs[1].score=9;
        jeu.joueurs[0].ilots=4;
        jeu.joueurs[1].ilots=6;

        List<Joueur> j= jeu.Ranking();
        assertTrue(j.get(0).num==4);
        assertTrue(j.get(1).num==5);
        control.afficheRanking(j);




    }
    @org.junit.jupiter.api.Test
    void rankingTest2() {
        jeu=new Jeu();
        control=new ControleurMediateur(jeu);
        jeu.joueurs[0].score=10;
        jeu.joueurs[1].score=10;
        jeu.joueurs[0].ilots=31;
        jeu.joueurs[1].ilots=69;
        List<Joueur> j= jeu.Ranking();
        assertTrue(j.get(0).num==5);
        assertTrue(j.get(1).num==4);
        control.afficheRanking(j);




    }
    @org.junit.jupiter.api.Test
    void rankingTest3() {
        jeu=new Jeu();
        control=new ControleurMediateur(jeu);
        jeu.joueurs[0].score=10;
        jeu.joueurs[1].score=10;
        jeu.joueurs[1].ilots=6;
        jeu.joueurs[0].ilots=6;
        List<Joueur> j= jeu.Ranking();
        assertTrue(j.get(0).num==5); //5 est preimer car on fait reverse pour ranking meme si y a un egalite entre 1 et 2
        assertTrue(j.get(1).num==4);
        assertTrue(j.get(0).ilots==j.get(1).ilots);
        assertTrue(j.get(0).score==j.get(1).score);
        control.afficheRanking(j);



    }
}
