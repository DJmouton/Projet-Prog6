package Modele;

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

    @Override
    public void jeu() {
        System.out.println("jeu");
        int profondeur = 1;
        ArrayList<Coup> coups = getCoups(this.jeu, this.num);
        int value = Integer.MIN_VALUE;
        Coup coup = null;
        for(Coup c : coups){
            Jeu j = jeu;//(Jeu) jeu.clone();
            c.setJeu(j);
            c.execute();
            int i = MinMaxJoueur(j, profondeur, new int[]{this.jeu.joueurs[0].num,this.jeu.joueurs[1].num},false);
            if(i > value){
                value = i;
                coup = c;
            }
            break;
        }
        if(coup != null) {
            coup.execute();
            jeu.SelectPingou(coup.sourcel,coup.sourcec);
            jeu.DeplacePingou(coup.destl,coup.destc);
        }
        System.out.println("fin jeu");
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

            if(A){
                coups = getCoups(jeu, nums[0]);
            }else{
                coups = getCoups(jeu, nums[1]);
            }
            for(Coup c : coups){
                Jeu j = jeu;//(Jeu) jeu.clone();
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
