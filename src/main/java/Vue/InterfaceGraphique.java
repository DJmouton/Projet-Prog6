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
		// Création de la fenêtre
		JFrame frame = new JFrame("Hey, that's my fish !");
		try {
			frame.setIconImage(ImageIO.read(new File("resources/assets/penguin.png")));
		}
		catch (IOException exc) {
			System.out.println("Erreur de chargement de l'icone");
		}

		// Ajout des composants
		ComposantBarreHaute barreHaute = new ComposantBarreHaute(control);
		frame.add(barreHaute, BorderLayout.PAGE_START);

		ComposantBarreBasse barreBasse = new ComposantBarreBasse(control);
		frame.add(barreBasse, BorderLayout.PAGE_END);

		ComposantBarreGauche barreGauche = new ComposantBarreGauche(control);
		frame.add(barreGauche, BorderLayout.LINE_START);

		/*
		ComposantBarreDroite barreDroite = new ComposantBarreDroite(control);
		frame.add(barreDroite, BorderLayout.LINE_END);
		*/

		NiveauGraphique niv = new NiveauGraphique(control);
		niv.addMouseListener(new AdaptateurSouris(niv, control));
		frame.add(niv);

		frame.setBackground(new Color(51, 153, 255));
		// Lancement de la fenêtre
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
}
