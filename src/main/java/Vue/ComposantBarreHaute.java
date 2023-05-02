package Vue;

import javax.swing.*;
import java.awt.*;

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
