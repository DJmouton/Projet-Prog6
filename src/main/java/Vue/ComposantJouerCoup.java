package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantJouerCoup extends Box{

    ComposantJouerCoup(int axis, CollecteurEvenements control){
        super(axis);
        Box coupX = Box.createHorizontalBox();
        coupX.add(new JLabel("x:"));
        JTextField x = new JTextField();
        x.setMaximumSize(new Dimension(
                x.getMaximumSize().width, x.getMinimumSize().height));
        coupX.add(x);
        add(coupX);

        coupX.add(new JLabel("y:"));
        Box coupY = Box.createHorizontalBox();
        JTextField y = new JTextField();
        y.setMaximumSize(new Dimension(
                y.getMaximumSize().width, y.getMinimumSize().height));
        coupY.add(y);
        add(coupY);
        AdaptateurJouerCoup jouerCoup = new AdaptateurJouerCoup(control, x, y);
        x.addActionListener(jouerCoup);
        y.addActionListener(jouerCoup);
    }
}
