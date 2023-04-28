package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class ComposantJoueurs extends Box {

	ComposantJoueurs(int axis, CollecteurEvenements control) {
		super(axis);

		BufferedImage perso = null;
		try {
			perso = ImageIO.read(new File("resources/assets/personnage.png"));
		} catch (
				IOException exc) {
			System.out.println("Erreur de chargement de l'icone Joueurs");
		}
		ImageIcon img = new ImageIcon("resources/assets/personnage.png");
		Image image = img.getImage();
		Image newimg = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);
		JLabel j1 = new JLabel(img);
		JLabel j2 = new JLabel(img);
		// JLabel j3 = new JLabel(img);
		// JLabel j4 = new JLabel(img);

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

		/*
		JButton joueur3 = new JButton("Joueur 3");
		add(joueur3);
		add(j3);
		JButton joueur4 = new JButton("Joueur 4");
		add(joueur4);
		add(j4);
		*/
	}
}
