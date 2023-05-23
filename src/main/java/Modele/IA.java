package Modele;

import Patterns.Commande;
import Structure.Arbre;
import com.kitfox.svg.A;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur{
    public Random random;
    private int profondeur;
    private Etats etats;
    private double[] weight = new double[]{2.366799251648115,0.6793297871850733,1.5828892078266514,1.9597206869528327,0.98823659118579,0.8488076063459944};


    public IA(int n, Jeu p, int typeJoueur) {
        super(n, p);
        this.random = new Random(0);
        long seed = random.nextLong();
        System.out.println("Seed IA("+this.num+"): "+seed);
        random.setSeed(seed);
        this.typeJoueur = typeJoueur;
    }

    public Placement placement() {
        etats = Etats.Initialisation;
        int i = jeu.nombrePAvoir-jeu.nombreP;
        Pair<Integer, Commande> result = MinMaxJoueur(jeu, Math.min(i,4), Integer.MAX_VALUE);
            result.getValue1().setJeu(jeu);
        return (Placement) result.getValue1();


    }


    public Coup jeu() {
        etats = Etats.Deplacement;
        ArrayList<int[]> pingouins = jeu.getCases(num);
        ArrayList<ArrayList<int[]>> ilots = getNombre(jeu, jeu.getCases(jeu.joueurs[jeu.joueurCourant].num));
        if(this.typeJoueur==2){
            profondeur = 4;
        }else if(this.typeJoueur==3){//4
            profondeur = 3;
        }else{ //4
            profondeur = 2;
        }
        if(pingouins.size() == ilots.size()){
            Coup coup = parcours_profondeur(jeu);
            coup.setJeu(jeu);
            return coup;
        }else {
            Pair<Integer, Commande> result = MinMaxJoueur(jeu, profondeur, Integer.MAX_VALUE);
            result.getValue1().setJeu(jeu);
            return (Coup) result.getValue1();
        }

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
            ArrayList<int[]> pingouins = jeu.getCases(num);
            for (int[] pingouin : pingouins) {
                for (int[] emplacement : jeu.hex_accessible(pingouin[0], pingouin[1])) {
                    coups.add(new Coup(jeu, pingouin[0], pingouin[1], emplacement[0], emplacement[1]));
                }
            }
        }
        return coups;
    }


    private int Evaluation(Jeu jeu){
        if(this.typeJoueur==2 || this.etats.equals(Etats.Initialisation)){
            if(jeu.joueurs[jeu.joueurCourant].num != this.num){
                jeu.prochainJoueur();
            }
            int scoreIA = evaluationInit(jeu);
            jeu.prochainJoueur();
            int scoreADV = evaluationInit(jeu);
            return scoreIA - scoreADV;
        }else {
            if(this.typeJoueur==3){
                return MonteCarlo(jeu, 5);
            }else {//4
                return MonteCarlo(jeu,250);
            }
        }

    }

    private int evaluationInit(Jeu jeu){
        int result = 0;
        int[] score = new int[6];
        for(int[] pingouin : jeu.getCases(jeu.joueurs[jeu.joueurCourant].num)) {
            ArrayList<int[]> accessible = jeu.hex_accessible(pingouin[0], pingouin[1]);
            for (int[] ilot : accessible) {
                score[jeu.plateau[ilot[0]][ilot[1]]]++;
            }
            for(int i = Math.max(pingouin[0]-1,0);i <= Math.min(pingouin[0]+1,jeu.plateau.length-1);i++){
                for(int j = Math.max(pingouin[1]-1,0);j <= Math.min(pingouin[1]+1,jeu.plateau[i].length-1);j++){
                    int value = jeu.plateau[i][j];
                    if(value > 3){
                        if(value == jeu.joueurs[jeu.joueurCourant].num){
                            score[4]++;
                        }else {
                            score[5]++;
                        }
                        value = 0;
                    }
                    score[0]+=value;
                }
            }
        }
        int k = (9*jeu.getCases(jeu.joueurs[jeu.joueurCourant].num).size());
        if(k>0) {
            score[0] /=k;
        }
        for(int i = 0; i<score.length;i++){
            result+=score[i]*weight[i];
        }
        return result;
    }


    public int MonteCarlo(Jeu jeu, int taille){
        int somme = 0;
        for(int i = 0; i< taille; i++){
            Jeu j = (Jeu) jeu.clone();
            while(j.enCours()){
                ArrayList<Commande> coups = getCoups(j, j.joueurs[j.joueurCourant].num);
                if(coups.size()>0){
                    coups.get(random.nextInt(coups.size())).execute();
                }else {
                    j.prochainJoueur();
                }
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
        if(!jeu.enCours() || profondeur == 0 ){
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

    public Coup parcours_profondeur(Jeu jeu){
        Arbre arbre = new Arbre(null, null);
        ArrayList<Long> hashlist = new ArrayList<>();
        expansion(arbre, jeu,hashlist);
        Arbre current = arbre;
        long temps = System.currentTimeMillis();
        while (System.currentTimeMillis() < temps+5000 && arbre.getProfondeur() != Integer.MAX_VALUE){
            Jeu j = (Jeu) jeu.clone();
            while (!current.isLeaf()) {
                current = current.choisir();
                current.getCoup().setJeu(j);
                current.getCoup().execute();
                j.joueurCourant = this.num-4;
            }
            expansion(current, j,hashlist);
            current = arbre;
        }
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < current.getEnfants().size(); i++) {
            Arbre a = current.getEnfants().get(i);
            if (a.getScore() > max) {
                max = a.getScore();
                index = i;
            }
        }

        return (Coup) arbre.getEnfants().get(index).getCoup();

    }

    private void expansion(Arbre pere, Jeu jeu, ArrayList<Long> hashList){
        ArrayList<int[]> pingouins = new ArrayList<>();
        pingouins.add(jeu.getCases(this.num).get(0));
        ArrayList<int[]> ilot = ilot(jeu, pingouins);
        pingouins.remove(0);
        for(int[] coord : ilot){
            if(jeu.plateau[coord[0]][coord[1]]==this.num){
                pingouins.add(coord);
            }
        }
        pere.setScore(0);
        pere.setProfondeur(1);
        for(int[] pingouin : pingouins){
            for(int[] coord : jeu.hex_accessible(pingouin[0],pingouin[1])){
                Jeu j = (Jeu) jeu.clone();
                Arbre a = new Arbre(pere, new Coup(j,pingouin[0], pingouin[1], coord[0], coord[1]));
                a.getCoup().execute();
                j.joueurCourant = this.num-4;
                ArrayList<int[]> p = new ArrayList<>();
                p.add(coord);
                ilot = ilot(j, p);
                Long l = hash(ilot, j.joueurs[j.joueurCourant].score);
                if(hashList.contains(l)){
                    a.setTerminal();
                    continue;
                }
                hashList.add(l);
                pere.addEnfant(a);
                if(j.plateau[coord[0]][coord[1]]==8){
                    a.setTerminal();
                }
                a.setScore(j.joueurs[j.joueurCourant].score+calculPoint(j, ilot));
            }
        }
    }

    private long hash(ArrayList<int[]> ilot,int score){
        long result = score;
        for(int[] coord : ilot){
            result = 31L*result + coord[0]* 31L +coord[1];
        }
        return result;

    }

    public int calculPoint(Jeu jeu,  ArrayList<int[]> ilot){
        int sum = 0;
        for(int[] coord : ilot){
            if(jeu.plateau[coord[0]][coord[1]]<4){
                sum+=jeu.plateau[coord[0]][coord[1]];
            }
        }
        return sum;
    }





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
        if(pingouins.size()==0){
            return new ArrayList<>();
        }
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
                        removeCoordFromList(cote, pingouins, 1);
                        return new ArrayList<>();
                    }else if(!jeu.contains(cote,accessible)){
                        if(valeur == pingouin){
                            removeCoordFromList(cote, pingouins, 1);
                        }
                        accessible.add(cote);
                    }
                }
            }
            i++;
        }
        return accessible;
    }

    public void removeCoordFromList(int[] cote, ArrayList<int[]> pingouins, int index){
        int j = index;
        while (j < pingouins.size()){
            if(cote[0]==pingouins.get(j)[0] && cote[1]==pingouins.get(j)[1]){
                pingouins.remove(j);
                j=pingouins.size();
            }
            j++;
        }
    }




}