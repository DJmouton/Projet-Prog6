package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantMenuPartie extends Box {


    ComposantMenuPartie(int axis, CollecteurEvenements control){
        super(axis);
        add(Box.createGlue());
        for (int i=0; i<2; i++) {
            JToggleButton but = new JToggleButton("AI Joueur " + (i+1));
            but.addActionListener(new AdaptateurJoueur(control, but, i));
            add(but);
        }
        add(Box.createGlue());
        add(new ComposantSauverCharger(axis, control));
        add(Box.createGlue());
    }
}
