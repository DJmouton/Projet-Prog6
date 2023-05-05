package Modele;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    private Random random;
    public static int profondeur = 1;

    public IA(int n, Jeu p) {
        super(n, p);
        this.random = new Random();
        this.estIA = true;
    }

    public Placement placement() {
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
        return new Placement(jeu, l, c);
    }

    public Coup jeu() {
        Pair<Integer, Coup> result = MinMaxJoueur(jeu, profondeur,Integer.MAX_VALUE);
        result.getValue1().setJeu(jeu);
        return result.getValue1();
    }

    private void jouerCoup(Coup coup){
        coup.execute();
        coup.jeu.EnlevePingou(coup.destl, coup.destc);
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


    private int Evaluation(Jeu j){
        return MonteCarlo(j, 10);
    }

    private void prochainJoueur(Jeu jeu){
        jeu.joueurCourant = (jeu.joueurCourant + 1) % jeu.joueurs.length;
        while (jeu.etat != Etats.Initialisation && jeu.getPingouins(jeu.joueurs[jeu.joueurCourant].num).isEmpty() && jeu.enCours())
            jeu.joueurCourant = (jeu.joueurCourant + 1) % jeu.joueurs.length;
    }

    private int MonteCarlo(Jeu jeu, int taille){
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            while(j.enCours()){
                ArrayList<Coup> coups = getCoups(j, j.joueurs[j.joueurCourant].num);
                if(coups.size()>0)
                    jouerCoup(coups.get(random.nextInt(coups.size())));
                prochainJoueur(j);
            }
            if(j.joueurs[j.joueurCourant].num != this.num){
                prochainJoueur(j);
            }
            int scoreIA = j.joueurs[j.joueurCourant].score;
            prochainJoueur(j);
            int scoreADV = j.joueurs[j.joueurCourant].score;
            somme+=scoreIA-scoreADV;
        }
        return somme / taille;

    }


    public Pair<Integer, Coup> MinMaxJoueur(Jeu jeu, int profondeur, int valP){
        if(!jeu.enCours() || profondeur == 0){
            return Pair.with(Evaluation(jeu),null);
        }else{

            int valeur, v2;
            Coup coup = null;
            if(jeu.joueurs[jeu.joueurCourant].num==this.num) {
                valeur = Integer.MIN_VALUE;
            }else{
                valeur = Integer.MAX_VALUE;
            }
            ArrayList<Coup> coups;

            coups = getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
            for(Coup c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                jouerCoup(c);
                prochainJoueur(j);
                v2 = MinMaxJoueur(j, profondeur - 1,valeur).getValue0();
                if(jeu.joueurs[jeu.joueurCourant].num==this.num) {//max
                    if(v2 > valeur){
                        valeur = v2;
                        coup = c;
                    }
                    if(valeur >= valP){
                        break;
                    }
                }else{
                    if(v2 < valeur){
                        valeur = v2;
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
