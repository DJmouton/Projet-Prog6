package Vue;/*
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

import javax.swing.*;
import java.awt.*;

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
		JFrame frame = new JFrame("Ma fenetre a moi");
		NiveauGraphique niv = new NiveauGraphique(j);
		niv.addMouseListener(new AdaptateurSouris(niv, control));
		frame.add(niv);
		Box barre = Box.createVerticalBox();
		barre.add(Box.createGlue());

		// Taille custom:
		Box barreTaille = Box.createHorizontalBox();
		barre.add(new JLabel("Custom (x, y):"));
		JTextField tailleX = new JTextField();
		tailleX.setMaximumSize(new Dimension(
				tailleX.getMaximumSize().width, tailleX.getMinimumSize().height));
		barreTaille.add(tailleX);
		JTextField tailleY = new JTextField();
		tailleY.setMaximumSize(new Dimension(
				tailleY.getMaximumSize().width, tailleY.getMinimumSize().height));
		AdaptateurTaille tailleCustom = new AdaptateurTaille(control, tailleX, tailleY);
		tailleX.addActionListener(tailleCustom);
		tailleY.addActionListener(tailleCustom);
		barreTaille.add(tailleY);
		barre.add(barreTaille);
		barre.add(Box.createGlue());

		for (int i=0; i<2; i++) {
			barre.add(new JLabel("Joueur " + (i+1)));
			JToggleButton but = new JToggleButton("IA");
			but.addActionListener(new AdaptateurJoueur(control, but, i));
			barre.add(but);
		}
		barre.add(Box.createGlue());

		// Boutons annuler et refaire
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new AdaptateurAnnuler(control, annuler));
		barre.add(annuler);
		JButton refaire = new JButton("refaire");
		refaire.addActionListener(new AdaptateurRefaire(control, refaire));
		barre.add(refaire);
		barre.add(Box.createGlue());
		frame.add(barre, BorderLayout.LINE_END);

		Timer chrono = new Timer( 16, new AdaptateurTemps(control));
		chrono.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}