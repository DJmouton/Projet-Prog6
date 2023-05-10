package Modele;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    private Random random;
    public static int profondeur = 2;

    public IA(int n, Jeu p, int typeJoueur) {
        super(n, p);
        this.random = new Random();
        this.typeJoueur = typeJoueur;
    }

    @Override
    public void placement() {
        //placer pingouin aléatoirement
        int l;
        int c =0;
        boolean tmp = false;
        for(l= 0; l < jeu.largeur; l++){
            for(c= 0; c < jeu.hauteur; c++){
                if(jeu.plateau[l][c]==1){
                    tmp = true;
                    break;
                }
            }
            if(tmp){
                break;
            }
        }
        jeu.InitPingou(l, c);


    }
  
    private int Evaluation(Jeu j){
        return MonteCarlo(j, 1);
    }

    private int MonteCarlo(Jeu jeu, int taille, int nums[], boolean A){
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            int num;
            if(A){
                num=nums[0];
            }else{
                num=nums[1];
            }
            while(j.enCours()){
                ArrayList<Commande> coups = jeu.getCoups(j, j.joueurs[j.joueurCourant].num);
                if(coups.size()>0)
                    coups.get(random.nextInt(coups.size())).execute();
                j.prochainJoueur();
            }

            int advscore = 0;
            for(Joueur joueur : this.jeu.joueurs){
                if(joueur.num != this.num){
                    advscore = joueur.score;
                }
            }
            somme+=this.score-advscore;
        }
        return somme / taille;

    }

    public int MinMaxJoueur(Jeu jeu, int profondeur, int[] nums, boolean A){
        if(!jeu.enCours() || profondeur == 0){
            return Evaluation(jeu, nums, A);
        }else{

            int valeur;
            if(A) {
                valeur = Integer.MIN_VALUE;
            }else{
                valeur = Integer.MAX_VALUE;
            }
            ArrayList<Coup> coups;
            coups = jeu.getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
            for(Commande c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                c.execute();
                if(A) {
                    valeur = Math.max(MinMaxJoueur(j, profondeur - 1, nums, false), valeur);
                }else{
                    valeur = Math.min(MinMaxJoueur(j, profondeur-1, nums, true), valeur);
                }
                break;
            }
            return valeur;
        }
    }


}
