package Vue;

import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterfaceGraphique implements Runnable {
	Jeu j;
	CollecteurEvenements control;
	int size;

	InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
		j = jeu;
		control = c;
		size = 3;
	}

	public static void demarrer(Jeu j, CollecteurEvenements control) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j, control));
	}

	/*
	 * Définition des composants graphiques et de leurs évènements associés
	 */
	@Override
	public void run() {
		// Création de la fenêtre
		JFrame frame = new JFrame("Hey, that's my fish !");
		try {
			frame.setIconImage(ImageIO.read(new File("resources/assets/penguin.png")));
		}
		catch (IOException exc) {
			System.out.println("Erreur de chargement de l'icone");
		}

		// Ajout des composants
		ComposantBarreHaute barreHaute = new ComposantBarreHaute(control, frame);
		frame.add(barreHaute, BorderLayout.PAGE_START);

		ComposantJoueurs joueurs = new ComposantJoueurs(control);
		j.ajouteObservateur(joueurs);
		frame.add(joueurs, BorderLayout.LINE_START);

		NiveauGraphique niv = new NiveauGraphique(j);
		niv.addMouseListener(new AdaptateurSouris(niv, control));
		frame.add(niv);

		// Lancement de la fenêtre
		Timer chrono = new Timer(2000, new AdaptateurTemps(control));
		chrono.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
