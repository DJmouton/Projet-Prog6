package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurNouvellePartie implements ActionListener {

    CollecteurEvenements control;
    JFrame parent;

    AdaptateurNouvellePartie(CollecteurEvenements c, JFrame p) {
        control = c;
        parent = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComposantPanneauNouvellePartie nouvelle_partie = new ComposantPanneauNouvellePartie();
        Object[] inputs = {
                nouvelle_partie
        };
        Object[] options = {
                "Partie Rapide",
                "Partie Custom",
                "Annuler"
        };
        int option = JOptionPane.showOptionDialog(
                null,
                inputs,
                "Nouvelle Partie",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                2
        );
        switch (option){
            case 0:
                System.out.println("Création de nouvelle partie rapide");

                break;

            case 1:
                System.out.println("Création de nouvelle partie custom");

                break;

            default:
                System.out.println("Création de nouvelle partie annulée");
                break;
        }
    }
}
