package Structure;

import Modele.Coup;
import Modele.Jeu;
import Patterns.Commande;

import java.util.ArrayList;
import java.util.Collections;

public class Arbre {
    private boolean terminal = false;
    private int score;
    private int profondeur;
    private Arbre pere;
    private ArrayList<Arbre> enfants;
    private Commande coup;

    public Arbre(Arbre pere, Commande coup) {
        this.score = 0;
        this.pere = pere;
        this.coup = coup;
        this.enfants = new ArrayList<>();
    }

    public int getScore() {
        return score;
    }


    public Arbre getPere() {
        return pere;
    }

    public ArrayList<Arbre> getEnfants() {
        return enfants;
    }

    public Commande getCoup() {
        return coup;
    }
    public int getProfondeur(){return profondeur;}

    public void setProfondeur(int i){
        if(i > profondeur){
            profondeur = i;
            if(this.pere!=null){
                this.pere.setProfondeur(i+1);
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
        if(this.pere != null && this.pere.score < score){
            this.pere.setScore(score);
        }
    }

    public void setTerminal() {
        this.profondeur = Integer.MAX_VALUE;
        if(this.pere != null){
            boolean term = true;
            for (Arbre a : this.pere.enfants){
                if(term)
                    term = a.profondeur==this.profondeur;
            }
            if(term){
                this.pere.setTerminal();
            }
        }
    }

    public void addEnfant(Arbre arbre){
        this.enfants.add(arbre);
    }


    public boolean isLeaf(){
        return this.enfants.isEmpty();
    }



    public Arbre choisir(){
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < this.getEnfants().size(); i++) {
            Arbre a = this.getEnfants().get(i);
            if (a.getProfondeur() < min) {
                min = a.getProfondeur();
                index = i;
            }
        }
        return this.enfants.get(index);
    }

}