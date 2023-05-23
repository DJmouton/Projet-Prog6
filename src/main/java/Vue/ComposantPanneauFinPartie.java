package Vue;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ComposantPanneauFinPartie extends JPanel {

    public ComposantPanneauFinPartie(CollecteurEvenements control){
        int[] ranks = control.ranking();
        ComposantInfoJoueur[] joueurs = {
                new ComposantInfoJoueur(1, Color.blue, control),
                new ComposantInfoJoueur(2, Color.green, control),
                new ComposantInfoJoueur(3, Color.red, control),
                new ComposantInfoJoueur(4, Color.yellow, control)
        };
        for (int i = 0; i < control.nbJoueurs(); i++) {
            joueurs[i].setScore(control.scoreJoueur(i));
        }
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < ranks.length; i++) {
            c.gridy = i;
            add(joueurs[ranks[i]], c);
            if(i == 0){
                Border matteBorder = BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black);
                Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "1er",
                        TitledBorder.CENTER,TitledBorder.TOP,
                        new Font("Arial", Font.BOLD , 17), Color.black);
                Border compoundBorder = BorderFactory.createCompoundBorder(titledBorder, joueurs[ranks[i]].getBorder());
                joueurs[ranks[i]].setBorder(compoundBorder);
            }
        }
    }
}