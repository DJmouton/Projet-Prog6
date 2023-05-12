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
                control.nouvellePartie(2, 1, 0, 0);
                break;
            case 1:
                control.nouvellePartie(
                        nouvelle_partie.joueur1.getSelectedIndex(),
                        nouvelle_partie.joueur2.getSelectedIndex(),
                        nouvelle_partie.joueur3.getSelectedIndex(),
                        nouvelle_partie.joueur4.getSelectedIndex()
                );
                break;
            default:
                break;
        }
    }
}
