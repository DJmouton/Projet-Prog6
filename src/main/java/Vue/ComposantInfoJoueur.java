package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantInfoJoueur extends JLabel {


	CollecteurEvenements control;
	int joueur;
	int nombreJoueur;
	Color color;

	Image poisson;

	ImageIcon p;

	ComposantTypeJoueur[] typeJoueurs;


	ComposantInfoJoueur(int j, Color c, CollecteurEvenements co) {
		control = co;
		setScore(0);
		joueur = j;
		color = c;
		setCurrent(false);
		setFont(new Font("Arial", Font.BOLD, 20));
	}

	public void setScore(int score) {

		try {
			poisson = ImageIO.read(new File("resources/assets/poisson.png"));

			typeJoueurs = new ComposantTypeJoueur[4];
			for (int i = 0; i < 4; i++)
				typeJoueurs[i] = new ComposantTypeJoueur(false, i);

			for (int i = 0; i < nombreJoueur; i++) {
				typeJoueurs[i].setBotHumain(control.estIA(i), i);
				add(typeJoueurs[i]);
			}

			p = new ImageIcon(poisson);

			setText(String.valueOf(score));
			setIcon(p);

		} catch (IOException e) { System.out.println("Pas d'image"); }
	}

	public void setCurrent(boolean isCurrent) {
		Border matteBorder = BorderFactory.createMatteBorder(1, 1, 3, 1, color);
		Border titledBorder = BorderFactory.createTitledBorder(matteBorder, "J" + joueur,
				TitledBorder.LEFT, TitledBorder.TOP,
				new Font("Arial", Font.PLAIN, 17), Color.black);

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
