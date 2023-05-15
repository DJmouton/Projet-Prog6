package Modele;

import Patterns.Commande;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    private Random random;
    public static int profondeur = 1;

    public IA(int n, Jeu p, int typeJoueur) {
        super(n, p);
        this.random = new Random();
        this.typeJoueur = typeJoueur;
    }

    public Placement placement() {
        long start = System.currentTimeMillis();
        Pair<Integer, Commande> result = null;
        profondeur = 1;
        while (start+5000 > System.currentTimeMillis()){
            Pair<Integer, Commande> r2 = MinMaxJoueur(jeu, profondeur,Integer.MAX_VALUE, start+5000);
            if(r2!=null){
                result = r2;
                System.out.println(profondeur);
            }
            profondeur++;
        }
        result.getValue1().setJeu(jeu);
        return (Placement) result.getValue1();

    }

    public Coup jeu() {
        long start = System.currentTimeMillis();
        Pair<Integer, Commande> result = null;
        profondeur = 1;
        while (start+5000 > System.currentTimeMillis()){
            Pair<Integer, Commande> r2 = MinMaxJoueur(jeu, profondeur,Integer.MAX_VALUE, start+5000);
            if(r2!=null){
                result = r2;
                System.out.println(profondeur);
            }
            profondeur++;
        }
        result.getValue1().setJeu(jeu);
        return (Coup) result.getValue1();
    }

    private int Evaluation(Jeu j){
        return MonteCarlo(j, 1);
    }

    private int MonteCarlo(Jeu jeu, int taille){
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            while(j.enCours()){
                ArrayList<Commande> coups = jeu.getCoups(j, j.joueurs[j.joueurCourant].num);
                if(coups.size()>0)
                    coups.get(random.nextInt(coups.size())).execute();
                j.prochainJoueur();
            }
            if(j.joueurs[j.joueurCourant].num != this.num){
                j.prochainJoueur();
            }
            int scoreIA = j.joueurs[j.joueurCourant].score;
            j.prochainJoueur();
            int scoreADV = j.joueurs[j.joueurCourant].score;
            somme+=scoreIA-scoreADV;
        }
        return somme / taille;

    }


    public Pair<Integer, Commande> MinMaxJoueur(Jeu jeu, int profondeur, int valP, long time){
        if(time < System.currentTimeMillis()){
            return null;
        }
        if(!jeu.enCours() || profondeur == 0){
            return Pair.with(Evaluation(jeu),null);
        }else{

            int valeur;
            Pair<Integer, Commande> v2;
            Commande coup = null;
            if(jeu.joueurs[jeu.joueurCourant].num==this.num) {
                valeur = Integer.MIN_VALUE;
            }else{
                valeur = Integer.MAX_VALUE;
            }
            ArrayList<Commande> coups;

            coups = jeu.getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
            for(Commande c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                c.execute();
                j.prochainJoueur();
                v2 = MinMaxJoueur(j, profondeur - 1,valeur, time);
                if(v2==null){
                    return null;
                }
                if(jeu.joueurs[jeu.joueurCourant].num==this.num) {//max
                    if(v2.getValue0() > valeur){
                        valeur = v2.getValue0();
                        coup = c;
                    }
                    if(valeur >= valP){
                        break;
                    }
                }else{
                    if(v2.getValue0() < valeur){
                        valeur = v2.getValue0();
                        coup = c;
                    }
                    if(valeur <= valP){
                        break;
                    }

                }

            }
            return Pair.with(valeur,coup);
        }
    }


}
