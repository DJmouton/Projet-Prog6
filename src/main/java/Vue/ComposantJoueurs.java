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
		JLabel j1 = new JLabel(new ImageIcon(perso));
		JLabel j2 = new JLabel(new ImageIcon(perso));
		JLabel j3 = new JLabel(new ImageIcon(perso));
		JLabel j4 = new JLabel(new ImageIcon(perso));
		JButton joueur1 = new JButton("Joueur 1");
		add(joueur1);
		add(j1);
		JButton joueur2 = new JButton("Joueur 2");
		add(joueur2);
		add(j2);
		JButton joueur3 = new JButton("Joueur 3");
		add(joueur3);
		add(j3);
		JButton joueur4 = new JButton("Joueur 4");
		add(joueur4);
		add(j4);
	}
}
