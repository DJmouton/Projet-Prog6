package Vue;

/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

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

		// Nom de la fenetre
		JFrame frame = new JFrame("Hey, that's my fish !");

		// Jeu principal
		NiveauGraphique niv = new NiveauGraphique(j);
		niv.addMouseListener(new AdaptateurSouris(niv, control));
		frame.add(niv);

		// Change l'icone de la fenetre principale
		try {
			frame.setIconImage(ImageIO.read(new File("resources/assets/penguin.png")));
		}
		catch (IOException exc) {
			System.out.println("Erreur de chargement de l'icone");
		}

		ComposantMenuPartie menuLateral = new ComposantMenuPartie(BoxLayout.Y_AXIS, control);
		ComposantBarreHaute barreHaute = new ComposantBarreHaute(BoxLayout.X_AXIS, control, menuLateral);

		// Non utilisé pour l'instant
		// ComposantJoueurs EtatJoueur = new ComposantJoueurs(BoxLayout.Y_AXIS, control);

		frame.add(barreHaute, BorderLayout.PAGE_START);
		frame.add(menuLateral, BorderLayout.LINE_END);
		// frame.add(EtatJoueur, BorderLayout.LINE_START);
		frame.repaint();
		Timer chrono = new Timer(16, new AdaptateurTemps(control));
		chrono.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
