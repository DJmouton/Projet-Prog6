package Vue;

import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
public class ComposantJoueurs extends Box implements Observateur {

	ComposantJoueurs(int axis, CollecteurEvenements control) {
		super(axis);
		paintComponent(control);
	}

	public void paintComponent(CollecteurEvenements control) {
		ImageIcon pb = new ImageIcon("resources/assets/penguin.png");
		ImageIcon pv = new ImageIcon("resources/assets/penguin_vert.png");
		ImageIcon pr = new ImageIcon("resources/assets/penguin_rouge.png");
		ImageIcon pj = new ImageIcon("resources/assets/penguin_jaune.png");

		Image image = pb.getImage();
		Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		pb = new ImageIcon(newimg);

		Image image2 = pv.getImage();
		Image newimg2 = image2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		pv = new ImageIcon(newimg2);

		Image image3 = pr.getImage();
		Image newimg3 = image3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		pr = new ImageIcon(newimg3);

		Image image4 = pj.getImage();
		Image newimg4 = image4.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		pj = new ImageIcon(newimg4);

		JLabel j1 = new JLabel(pb);
		JLabel j2 = new JLabel(pv);
		JLabel j3 = new JLabel(pr);
		JLabel j4 = new JLabel(pj);

		if (control.joueurCourant() == 0) {
			JButton joueur1 = new JButton("Au tour du Joueur 1");
			add(joueur1);
			add(j1);
		}
		else if (control.joueurCourant() == 1) {
			JButton joueur2 = new JButton("Au tour du Joueur 2");
			add(joueur2);
			add(j2);
		}
		else if (control.joueurCourant() == 2) {
			JButton joueur3 = new JButton("Au tour du Joueur 3");
			add(joueur3);
			add(j3);
		}
		else if (control.joueurCourant() == 3) {
			JButton joueur4 = new JButton("Au tour du Joueur 4");
			add(joueur4);
			add(j4);
		}
		else {

		}
	}

	@Override
	public void miseAJour() {
		repaint();
	}
}
