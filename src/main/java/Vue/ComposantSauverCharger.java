package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantSauverCharger extends JPanel {


	ComposantSauverCharger(CollecteurEvenements control) {
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel("Nom fichier:"), c);

		c.gridy = 1;
		JTextField fichier = new JTextField();
		add(fichier, c);

		c.gridy = 2;
		JButton sauver = new JButton("Sauver");
		sauver.addActionListener(new AdaptateurSauver(control, fichier));
		add(sauver, c);

		c.gridy = 3;
		JButton charger = new JButton("Charger");
		charger.addActionListener(new AdaptateurCharger(control, fichier));
		add(charger, c);
	}
}
