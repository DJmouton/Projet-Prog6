package Modele;

import Patterns.Commande;
import Structure.Arbre;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    private Random random;
    public int profondeur = 4;
    public static Double[] weight = new Double[]{5.0,4.0,1.1,1.2,3.0};


    public IA(int n, Jeu p) {
        super(n, p);
        this.random = new Random();
        long seed = random.nextLong();
        System.out.println("Seed IA("+this.num+"): "+seed);
        random.setSeed(seed);
        this.estIA = true;
    }

    public Placement placement() {

        Pair<Integer, Commande> result = MinMaxJoueur(jeu, profondeur,Integer.MAX_VALUE);
        result.getValue1().setJeu(jeu);
        return (Placement) result.getValue1();

    }

    public Coup jeu() {
        Pair<Integer, Commande> result = MinMaxJoueur(jeu, profondeur,Integer.MAX_VALUE);
        result.getValue1().setJeu(jeu);
        return (Coup) result.getValue1();
    }


    public ArrayList<Commande> getCoups(Jeu jeu, int num){
        ArrayList<Commande> coups = new ArrayList<>();
        if(jeu.getEtat().equals(Etats.Initialisation)){
            for (int l =0; l < jeu.plateau.length;l++){
                for (int c =0; c < jeu.plateau[l].length;c++){
                    if(jeu.plateau[l][c]==1){
                        coups.add(new Placement(jeu,l,c));
                    }
                }
            }
        }else {
            ArrayList<int[]> pingouins = jeu.getPingouins(num);
            ArrayList<ArrayList<int[]>> ilot = getNombre(jeu, jeu.getPingouins(jeu.joueurs[jeu.joueurCourant].num));
            for (int[] pingouin : pingouins) {
                if(pingouins.size() != ilot.size()) {
                    boolean cont = false;
                    for (ArrayList<int[]> liste : ilot) {
                        if (liste.get(0)[0] == pingouin[0] && liste.get(0)[1] == pingouin[1]) {
                            cont = true;
                            break;
                        }
                    }
                    if(cont){
                        continue;
                    }
                }

                for (int[] emplacement : jeu.hex_accessible(pingouin[0], pingouin[1])) {
                    coups.add(new Coup(jeu, pingouin[0], pingouin[1], emplacement[0], emplacement[1]));
                }
            }
        }
        return coups;
    }


    private int Evaluation(Jeu j){
        return MonteCarlo(j, 1);
    }

    private int customEvaluation(Jeu jeu){//ilot + nombre de carte
        int ia;
        int adv;
        if(jeu.joueurs[jeu.joueurCourant].num != this.num){
            jeu.prochainJoueur();
        }
        ia = calculScore(jeu);
        jeu.prochainJoueur();
        adv = calculScore(jeu);
        return ia - adv;
    }

    private int calculScore(Jeu jeu){
        ArrayList<ArrayList<int[]>> ilot = getNombre(jeu, jeu.getPingouins(jeu.joueurs[jeu.joueurCourant].num));
        int[] score = new int[5];
        int result = 0;
        score[0] = jeu.joueurs[jeu.joueurCourant].score;
        for(ArrayList<int[]> l : ilot){
            for(int i =1; i < l.size(); i++){
                int[] coord = l.get(i);
                int value = jeu.plateau[coord[0]][coord[1]];
                if(value < 4) {
                    score[1] += value;
                }
            }
        }
        ArrayList<Commande> getCoups = getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
        for (Commande coup : getCoups){
            if(coup instanceof Coup){
                score[1+jeu.plateau[((Coup) coup).destl][((Coup) coup).destc]]++;
            }
        }
        for(int i = 0 ; i < score.length;i++){
            result+=score[i]*weight[i];
        }
        return result;
    }




    private int MonteCarlo(Jeu jeu, int taille){
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            while(j.enCours()){

                ArrayList<Commande> coups = getCoups(j, j.joueurs[j.joueurCourant].num);
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


    public Pair<Integer, Commande> MinMaxJoueur(Jeu jeu, int profondeur, int valP){
        if(!jeu.enCours() || profondeur == 0){
            return Pair.with(Evaluation(jeu),null);
        }else{

            int valeur, v2;
            Commande coup = null;
            if(jeu.joueurs[jeu.joueurCourant].num==this.num) {
                valeur = Integer.MIN_VALUE;
            }else{
                valeur = Integer.MAX_VALUE;
            }
            ArrayList<Commande> coups;
            coups = getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
            for(Commande c : coups){
                Jeu j = (Jeu) jeu.clone();
                c.setJeu(j);
                c.execute();
                j.prochainJoueur();
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
/**
    public Arbre MCTS(Jeu jeu){
        Arbre arbre = new Arbre(null);
        long temps = System.currentTimeMillis();
        long before = temps;
        while (temps < before+5000){
            Arbre current = meilleurArbre(arbre);
            if(current)
            temps = System.currentTimeMillis();;
        }
        ArrayList<Commande> coups = getCoups(jeu, jeu.joueurs[jeu.joueurCourant].num);
        for(Commande c : coups){
            Jeu j = (Jeu) jeu.clone();
            c.setJeu(j);
            c.execute();
            j.prochainJoueur();
            arbre.addEnfant(new Arbre(arbre));
        }


    }

    private Arbre meilleurArbre(Arbre arbre){ // PERE
        Arbre current = arbre;

        while (!current.getEnfants().isEmpty()){
            double c = Math.sqrt(2);
            current = current.getEnfants().get((int) (current.getW()/current.getN()+c*Math.sqrt(Math.log(current.getPere().getN())/current.getN())));
        }
        return current;
    }
 */

//[[[pingouin1X, pingouin1Y],[case1X,case2Y]],[[pingouinX, pingouinY],[case1X,case2Y]]]
    public ArrayList<ArrayList<int[]>> getNombre(Jeu jeu, ArrayList<int[]> pingouins){
        ArrayList<ArrayList<int[]>> result = new ArrayList<>();
        if(pingouins.isEmpty()){
            return result;
        }
        ArrayList<int[]> ilot = ilot(jeu,pingouins);
        if(ilot.size()>0){
            ilot.set(0, pingouins.get(0));
            result.add(ilot);
        }
        pingouins.remove(0);
        result.addAll(getNombre(jeu, pingouins));
        return result;
    }

    public ArrayList<int[]> ilot(Jeu jeu, ArrayList<int[]> pingouins){
        int[] source = pingouins.get(0);
        int pingouin = jeu.plateau[source[0]][source[1]];
        ArrayList<int[]> accessible = new ArrayList<>();
        accessible.add(source);
        int i = 0;
        while (i<accessible.size()){
            for (int[] cote : jeu.getCotes(accessible.get(i)[0],accessible.get(i)[1])){
                int valeur = jeu.plateau[cote[0]][cote[1]];
                if(valeur > 0){
                    if(valeur > 3 && valeur != pingouin){//2 pingouins sur une ilot
                        removeCoordFromList(cote, pingouins);
                        return new ArrayList<>();
                    }else if(!jeu.contains(cote,accessible)){
                        if(valeur == pingouin){
                            removeCoordFromList(cote, pingouins);
                        }
                        accessible.add(cote);
                    }
                }
            }
            i++;
        }
        return accessible;
    }

    public void removeCoordFromList(int[] cote, ArrayList<int[]> pingouins){
        int j = 1;
        while (j < pingouins.size()){
            if(cote[0]==pingouins.get(j)[0] && cote[1]==pingouins.get(j)[1]){
                pingouins.remove(j);
                j=pingouins.size();
            }
            j++;
        }
    }




}
