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
        int profondeur = 3;
        ArrayList<Coup> coups = getCoups(this.jeu, this.num);
        int value = Integer.MIN_VALUE;
        Coup coup = null;
        for(Coup c : coups){
            Jeu j = (Jeu) jeu.clone();
            c.setJeu(j);
            c.execute();
            int i = MinMaxJoueur(j, profondeur);
            if(i > value){
                value = i;
                coup = c;
            }
        }
        if(coup != null) {
            System.out.println("Jouer Coup");
            coup.setJeu(jeu);
            jouerCoup(coup);
            jeu.prochainJoueur();
        }
        System.out.println("TAILLE: "+jeu.getPingouins(this.num).size());
        System.out.println("fin jeu");
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


    private int Evaluation(Jeu jeu){
        return MonteCarlo(jeu, 10);
    }

    private void prochainJoueur(Jeu jeu){
        jeu.joueurCourant = (jeu.joueurCourant + 1) % jeu.joueurs.length;
        while (jeu.etat != Etats.Initialisation && jeu.getPingouins(jeu.joueurs[jeu.joueurCourant].num).isEmpty() && jeu.enCours())
            jeu.joueurCourant = (jeu.joueurCourant + 1) % jeu.joueurs.length;
    }

    private int MonteCarlo(Jeu jeu, int taille){
        System.out.println("Monte Carlo");
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            while(j.enCours()){
                ArrayList<Coup> coups = getCoups(j, j.joueurs[j.joueurCourant].num);
                if(coups.size()>0)
                    jouerCoup(coups.get(random.nextInt(coups.size())));
                prochainJoueur(j);
            }
            int scoreIA = j.joueurs[j.joueurCourant].score;
            prochainJoueur(j);
            int scoreADV = j.joueurs[j.joueurCourant].score;
            somme+=scoreIA-scoreADV;
        }
        return somme / taille;

    }

    public int MinMaxJoueur(Jeu jeu, int profondeur){
        if(!jeu.enCours() || profondeur == 0){
            return Evaluation(jeu);
        }else{

            int valeur;
            if(jeu.joueurs[jeu.joueurCourant].num==this.num) {
                valeur = Integer.MIN_VALUE;
            }else{
                valeur = Integer.MAX_VALUE;
            }
            ArrayList<Coup> coups;

            coups = getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
            System.out.println("test");
            for(Coup c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                jouerCoup(c);
                j.prochainJoueur();
                if(jeu.joueurs[jeu.joueurCourant].num==this.num) {
                    valeur = Math.max(MinMaxJoueur(j, profondeur-1), valeur);
                }else{
                    valeur = Math.min(MinMaxJoueur(j, profondeur - 1), valeur);

                }
            }
            return valeur;
        }
    }


}
