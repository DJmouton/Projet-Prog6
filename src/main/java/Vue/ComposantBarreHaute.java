package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ComposantBarreHaute extends Box {

    ComposantBarreHaute(int axis, CollecteurEvenements control, Box menu) {
        super(axis);
        add(Box.createGlue());
        add(new ComposantJouerCoup(axis, control));
        add(Box.createGlue());
        add(new ComposantAnnulerRefaire(axis, control));
        add(Box.createGlue());
        JToggleButton ouvreMenu = new JToggleButton("Menu");
        add(ouvreMenu, BorderLayout.LINE_END);
        ouvreMenu.addActionListener(new AdaptateurOuvreMenu(ouvreMenu, menu));
    }
}
