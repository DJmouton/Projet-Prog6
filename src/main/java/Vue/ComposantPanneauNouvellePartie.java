package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ComposantPanneauNouvellePartie extends JPanel {

    JComboBox<String> joueur1, joueur2, joueur3, joueur4;

    ComposantPanneauNouvellePartie(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Border matteBorder = BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black);
        Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "Options pour une partie custom:",
                TitledBorder.LEFT,TitledBorder.TOP,
                new Font("Arial", Font.PLAIN , 13), Color.black);
        setBorder(titledBorder);

        joueur1 = AjouteJoueur(1, Color.blue);
        joueur2 = AjouteJoueur(2, Color.green);
        joueur3 = AjouteJoueur(3, Color.red);
        joueur4 = AjouteJoueur(4, Color.yellow);
        joueur1.setSelectedIndex(2);
        joueur2.setSelectedIndex(1);
    }

    private JComboBox<String> AjouteJoueur(int i, Color couleur) {
        Box box = new Box(BoxLayout.Y_AXIS);
        String[] typeJoueur = {
                "Vide",
                "Humain",
                "Robot Facile",
                "Robot Normal",
                "Robot Difficile"
        };
        JComboBox<String> joueur = new JComboBox<>(typeJoueur);
        box.add(joueur);
        Border matteBorder = BorderFactory.createMatteBorder(1, 1, 3, 1, couleur);
        Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "Joueur " + i,
                TitledBorder.LEFT,TitledBorder.TOP,
                new Font("Arial", Font.PLAIN , 13), Color.black);
        box.setBorder(titledBorder);
        add(box);
        return(joueur);
    }

}
