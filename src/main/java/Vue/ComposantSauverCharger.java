package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantSauverCharger extends Box {

	CollecteurEvenements control;

	ComposantSauverCharger(int axis, CollecteurEvenements c) {
		super(axis);
		control = c;
		// Save and load
		add(new JLabel("Nom fichier:"));
		JTextField fichier = new JTextField();
		fichier.setMaximumSize(new Dimension(
				fichier.getMaximumSize().width, fichier.getMinimumSize().height));
		add(fichier);
		JButton sauver = new JButton("Sauver");
		sauver.addActionListener(new AdaptateurSauver(control, fichier));
		add(sauver);
		JButton charger = new JButton("Charger");
		charger.addActionListener(new AdaptateurCharger(control, fichier));
		add(charger);
	}
}
