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
		add(j1, BorderLayout.NORTH);
		add(j2, BorderLayout.SOUTH);
		add(j3);
		add(j4);
	}
}
