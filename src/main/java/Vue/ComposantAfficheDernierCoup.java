package Vue;

import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantAfficheDernierCoup extends JLabel implements Observateur {

	CollecteurEvenements control;
	int dstL, dstC, jc;

	Image poisson;

	ImageIcon p;

	ComposantAfficheDernierCoup(CollecteurEvenements contr) {
		control = contr;
		setFont(new Font("Arial", Font.BOLD, control.largeur() + 12));
		try {
			poisson = ImageIO.read(new File("resources/assets/poisson.png"));
			dstL = control.coupDestL();
			dstC = control.coupDestC();
		} catch (NullPointerException e) {
			dstL = -1;
			dstC = -1;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		p = new ImageIcon(poisson);
	}

	@Override
	public void miseAJour() {
		try {
			dstL = control.coupDestL();
			dstC = control.coupDestC();
		} catch (NullPointerException e) {
			dstL = -1;
			dstC = -1;
		}
		if (control.joueurCourant() != 0)
			jc = control.joueurCourant();
		else
			jc = control.nombreJoueurs();
		if (dstL >= 0 && dstC >= 0) {
			setIcon(p);
		}
		setBackground(new Color(51, 153, 255));
	}
}
