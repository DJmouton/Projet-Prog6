package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantTypeJoueur extends JLabel {

	int numeroJoueur;

	boolean ordi;

	Image robot, pingouin, joue;

	ImageIcon r, p, j;

	ComposantTypeJoueur(boolean bot, int joueur) {
		numeroJoueur = joueur;
		ordi = bot;
		setBotHumain(ordi, numeroJoueur);
	}

	public void setBotHumain(boolean ordi, int numeroJoueur) {
		try {

			if (numeroJoueur == 0) {
				pingouin = ImageIO.read(new File("resources/assets/pingouinB.png"));
				robot = ImageIO.read(new File("resources/assets/robotB.png"));
			}
			else if (numeroJoueur == 1) {
				pingouin = ImageIO.read(new File("resources/assets/pingouinV.png"));
				robot = ImageIO.read(new File("resources/assets/robotV.png"));
			}
			else if (numeroJoueur == 2) {
				pingouin = ImageIO.read(new File("resources/assets/pingouinR.png"));
				robot = ImageIO.read(new File("resources/assets/robotR.png"));
			}
			else if (numeroJoueur == 3) {
				pingouin = ImageIO.read(new File("resources/assets/pingouinJ.png"));
				robot = ImageIO.read(new File("resources/assets/robotJ.png"));
			}
			r = new ImageIcon(robot);
			p = new ImageIcon(pingouin);

			if (ordi)
				setIcon(r);
			else
				setIcon(p);

		} catch (IOException e) {
			System.out.println("pas d'images");
		}
	}

	public void setJoue(boolean estCourant, boolean estIA) {

		if (estCourant) {
			try {
				if (estIA) {
					if (numeroJoueur == 0)
						joue = ImageIO.read(new File("resources/assets/robotMB.png"));
					else if (numeroJoueur == 1)
						joue = ImageIO.read(new File("resources/assets/robotMV.png"));
					else if (numeroJoueur == 2)
						joue = ImageIO.read(new File("resources/assets/robotMR.png"));
					else if (numeroJoueur == 3)
						joue = ImageIO.read(new File("resources/assets/robotMJ.png"));
				}
				else
					if (numeroJoueur == 0)
						joue = ImageIO.read(new File("resources/assets/mangeB.png"));
					else if (numeroJoueur == 1)
						joue = ImageIO.read(new File("resources/assets/mangeV.png"));
					else if (numeroJoueur == 2)
						joue = ImageIO.read(new File("resources/assets/mangeR.png"));
					else if (numeroJoueur == 3)
						joue = ImageIO.read(new File("resources/assets/mangeJ.png"));
				j = new ImageIcon(joue);
				setIcon(j);
			} catch (IOException e) {
				System.out.println("Pas d'image");
			}

		}
	}
}