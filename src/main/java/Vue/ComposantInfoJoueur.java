package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantInfoJoueur extends JLabel {

	int joueur;
	Color color;

	Image poisson, robot, pingouin;

	ImageIcon p, r, pi;


	ComposantInfoJoueur(int j, Color c) {
		setScore(0);
		joueur = j;
		color = c;
		setCurrent(false);
	}

	public void setScore(int score) {

		try {
			poisson = ImageIO.read(new File("resources/assets/poisson.png"));

			p = new ImageIcon(poisson);

			setText(String.valueOf(score));
			setIcon(p);
		} catch (IOException e) {
			System.out.println("Pas d'image");
		}
	}

	public void setCurrent(boolean isCurrent) {
		Border matteBorder = BorderFactory.createMatteBorder(1, 1, 3, 1, color);
		Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "Joueur " + joueur,
				TitledBorder.LEFT, TitledBorder.TOP,
				new Font("Arial", Font.PLAIN, 13), Color.black);

		if (isCurrent) {
			Border raisedbevel = BorderFactory.createRaisedBevelBorder();
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			Border compound = BorderFactory.createCompoundBorder(
					raisedbevel, loweredbevel);
			compound = BorderFactory.createCompoundBorder(
					compound, titledBorder);
			setBorder(compound);
		} else
			setBorder(titledBorder);
	}
}
