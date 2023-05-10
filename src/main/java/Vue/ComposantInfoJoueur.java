package Vue;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ComposantInfoJoueur extends JLabel {

    int joueur;
    Color color;

    ComposantInfoJoueur(int j, Color c){
        setScore(0);
        joueur = j;
        color = c;
        setCurrent(false);
    }

    public void setScore(int score){
        if(score < 2)
            setText(score + " poisson");
        else
            setText(score + " poissons");
    }

    public void setCurrent(boolean isCurrent){
        Border matteBorder = BorderFactory.createMatteBorder(1, 1, 3, 1, color);
        Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "Joueur " + joueur,
                TitledBorder.LEFT,TitledBorder.TOP,
                new Font("Arial", Font.PLAIN , 13), Color.black);

        if(isCurrent){
            Border raisedbevel = BorderFactory.createRaisedBevelBorder();
            Border loweredbevel = BorderFactory.createLoweredBevelBorder();
            Border compound = BorderFactory.createCompoundBorder(
                    raisedbevel, loweredbevel);
            compound = BorderFactory.createCompoundBorder(
                    compound, titledBorder);
            setBorder(compound);
        }
        else
            setBorder(titledBorder);
    }
}
