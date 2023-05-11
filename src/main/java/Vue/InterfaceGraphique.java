package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterfaceGraphique implements Runnable {
	CollecteurEvenements control;
	int size;

	InterfaceGraphique(CollecteurEvenements c) {
		control = c;
		size = 3;
	}

	public static void demarrer(CollecteurEvenements control) {
		SwingUtilities.invokeLater(new InterfaceGraphique(control));
	}

	/*
	 * Définition des composants graphiques et de leurs évènements associés
	 */
	@Override
	public void run() {

		// Nom de la fenetre
		JFrame frame = new JFrame("Hey, that's my fish !");

		// Change l'icone de la fenetre principale
		try {
			frame.setIconImage(ImageIO.read(new File("resources/assets/penguin.png")));
		} catch (IOException exc) {
			System.out.println("Erreur de chargement de l'icone");
		}

		// Ajout des composants
		ComposantBarreHaute barreHaute = new ComposantBarreHaute(control, frame);
		frame.add(barreHaute, BorderLayout.PAGE_START);

		ComposantJoueurs joueurs = new ComposantJoueurs(control);
		control.ajouteObservateur(joueurs);
		frame.add(joueurs, BorderLayout.LINE_START);

		// Jeu principal
		NiveauGraphique niv = new NiveauGraphique(control);
		niv.addMouseListener(new AdaptateurSouris(niv, control));
		frame.add(niv);

		Timer chrono = new Timer(2000, new AdaptateurTemps(control));
		chrono.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setVisible(true);
	}
}
