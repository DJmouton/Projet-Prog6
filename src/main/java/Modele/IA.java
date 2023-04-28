package Modele;

import Structure.Tree;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    private Random random;

    public IA(int n, Jeu p) {
        super(n, p);
        this.random = new Random();
        this.estIA = true;
    }

    @Override
    public void placement() {
        //placer pingouin aléatoirement
    }

    @Override
    public void jeu() {
        int profondeur = 3;
        ArrayList<Coup> coups = getCoups(this.plateau, this.num);
        int value = Integer.MIN_VALUE;
        Coup coup = null;
        for(Coup c : coups){
            Jeu j = (Jeu) plateau.clone();
            c.setJeu(j);
            c.execute();
            int i = MinMaxJoueur(j, profondeur, new int[]{this.plateau.joueurs[0].num,this.plateau.joueurs[1].num},false);
            if(i > value){
                value = i;
                coup = c;
            }
        }
        if(coup != null) {
            coup.setJeu(plateau);
            coup.execute();
        }
    }

    public ArrayList<Coup> getCoups(Jeu jeu, int num){
        ArrayList<int[]> pingouins = jeu.getPingouins(num);
        ArrayList<Coup> coups = new ArrayList<>();
        for(int[] pingouin : pingouins){
            for(int[] emplacement : jeu.hex_accessible(pingouin[0],pingouin[1])){
                coups.add(new Coup(jeu, pingouin[0],pingouin[1],emplacement[0],emplacement[1]));
            }
        }
        return coups;
    }


    private int Evaluation(Jeu jeu, int nums[], boolean A){
        return MonteCarlo(jeu, 100, nums, A);
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
                ArrayList<Coup> coups = getCoups(j, num);
                coups.get(random.nextInt(coups.size())).execute();
                if(num == nums[0]){
                    num = nums[1];
                }else{
                    num = nums[0];
                }
            }

            int advscore = 0;
            for(Joueur joueur : this.plateau.joueurs){
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

            if(A){
                coups = getCoups(jeu, nums[0]);
            }else{
                coups = getCoups(jeu, nums[1]);
            }
            for(Coup c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                c.execute();
                if(A) {
                    valeur = Math.max(MinMaxJoueur(j, profondeur - 1, nums, false), valeur);
                }else{
                    valeur = Math.min(MinMaxJoueur(j, profondeur-1, nums, true), valeur);
                }
            }
            return valeur;
        }
    }


}
