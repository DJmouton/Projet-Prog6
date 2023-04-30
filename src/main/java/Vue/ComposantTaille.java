package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantTaille extends Box {

	ComposantTaille(int axis, CollecteurEvenements control) {
		super(axis);
		add(new JLabel("Custom (x, y):"));
		Box barreTailleCustom = Box.createHorizontalBox();
		JTextField tailleX = new JTextField();
		tailleX.setMaximumSize(new Dimension(
				tailleX.getMaximumSize().width, tailleX.getMinimumSize().height));
		barreTailleCustom.add(tailleX);
		JTextField tailleY = new JTextField();
		tailleY.setMaximumSize(new Dimension(
				tailleY.getMaximumSize().width, tailleY.getMinimumSize().height));
		AdaptateurTaille tailleCustom = new AdaptateurTaille(control, tailleX, tailleY);
		tailleX.addActionListener(tailleCustom);
		tailleY.addActionListener(tailleCustom);
		barreTailleCustom.add(tailleY);
		add(barreTailleCustom);
	}
}
