package Structure;

import Modele.Coup;

import java.util.ArrayList;

public class Arbre {
    private int w;
    private int n;
    private Arbre pere;
    private ArrayList<Arbre> enfants;

    public Arbre(Arbre pere) {
        this.w = 0;
        this.n = 0;
        this.pere = pere;
        this.enfants = new ArrayList<>();
    }

    public int getW() {
        return w;
    }

    public int getN() {
        return n;
    }

    public Arbre getPere() {
        return pere;
    }

    public ArrayList<Arbre> getEnfants() {
        return enfants;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void addEnfant(Arbre arbre){
        this.enfants.add(arbre);
    }
}
