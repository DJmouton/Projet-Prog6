package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantPanneauMenu extends JPanel {


	ComposantPanneauMenu(CollecteurEvenements control) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton regles = new JButton("Règles");
		regles.addActionListener(new AdaptateurRegle());
		add(regles, c);

		c.gridy = 1;
		JButton nouvelle_partie = new JButton("Partie Custom");
		nouvelle_partie.addActionListener(new AdaptateurNouvellePartie(control));
		add(nouvelle_partie, c);

		c.gridy = 2;
		add(new ComposantSauverCharger(control), c);
	}
}
