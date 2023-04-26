package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantAnnulerRefaire extends Box{

    ComposantAnnulerRefaire(int axis, CollecteurEvenements control){
        super(axis);
        JButton annuler = new JButton("Annuler");
        annuler.addActionListener(new AdaptateurAnnuler(control));
        add(annuler);
        JButton refaire = new JButton("Refaire");
        refaire.addActionListener(new AdaptateurRefaire(control));
        add(refaire);
    }
}
