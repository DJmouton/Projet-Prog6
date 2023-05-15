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
		add(new ComposantSauverCharger(control), c);
	}
}
