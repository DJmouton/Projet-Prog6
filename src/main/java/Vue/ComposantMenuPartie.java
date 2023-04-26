package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantMenuPartie extends Box {


    ComposantMenuPartie(int axis, CollecteurEvenements control){
        super(axis);
        add(Box.createGlue());
        add(new ComposantTaille(axis, control));
        add(Box.createGlue());
        for (int i=0; i<2; i++) {
            add(new JLabel("Joueur " + (i+1)));
            JToggleButton but = new JToggleButton("IA");
            but.addActionListener(new AdaptateurJoueur(control, but, i));
            add(but);
        }
        add(Box.createGlue());
        add(new ComposantSauverCharger(axis, control));
        add(Box.createGlue());

    }
}
