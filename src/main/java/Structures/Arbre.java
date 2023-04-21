package Structures;

import Modele.Jeu;

public class Arbre {

    private Jeu value;
    private Arbre[] fils;

    public Arbre(Jeu value, Arbre[] fils) {
        this.value = value;
        this.fils = fils;
    }

    public Jeu getValue() {
        return value;
    }

    public Arbre[] getFils() {
        return fils;
    }
}
