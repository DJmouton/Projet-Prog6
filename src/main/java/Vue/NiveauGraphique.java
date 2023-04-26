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
import Patterns.Observateur;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.*;
import javax.swing.*;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	/* Load assets */

	/*
	* Peindre le plateau de jeu
	*/
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

		/* Load Assets */
		BufferedImage iceTile = null;
		BufferedImage waterTile = null;
		BufferedImage penguin = null;

		try {
			iceTile = ImageIO.read(new File("resources/assets/tileSnow_full.png"));
			waterTile = ImageIO.read(new File("resources/assets/waterTile.png"));
			penguin = ImageIO.read(new File("resources/assets/penguin.png"));
		} catch (IOException exc) {
			System.out.println("Erreur dans le chargement des images");
		}

		int lignes = jeu.hauteur();
		int colonnes = jeu.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;

		int[] xpoint = new int[6];
		int[] ypoint = new int[6];
		Arrays.fill(xpoint, 6);
		Arrays.fill(ypoint, 6);

		// Rectangle d'océan (bleu) en fond
		g.drawImage(waterTile, 0, 0, largeur(), hauteur(), this);

		// Fin de la partie
		if (!jeu.enCours()) {
			g.drawString("La partie est terminée", largeur() / 3, hauteur() - 5);
		}

		float height;
		height = (float)3/4 * (float)hauteurCase;
		int hauteur;
		int largeur = largeurCase;
		int test = (int)1.5;
		// Grille
		for (double i = 2; i < (lignes); i++) {
			hauteur = (int)((float)i * (height));
			if (i % 2 == 1) {
					for (double j = 1.5; j < (colonnes - 2); j++) {
							g.drawImage(iceTile, (int)(j) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
						}
					} else {
				for (double j = 0.5; j < (colonnes - 2); j++) {
						g.drawImage(iceTile, (int)(j + 1) * largeurCase, hauteur,
								largeurCase, hauteurCase, this);
					}
				}
			}

		// Coups
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case 0:
						//g.drawImage(iceTile, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase, this);
                        break;

                    case 1:
						g.drawImage(waterTile, j * largeurCase, i * hauteurCase,
								(j + 1) * largeurCase, (i + 1) * hauteurCase, this);
						g.drawImage(waterTile, j * largeurCase, (i + 1) * hauteurCase,
								(j + 1) * largeurCase, i * hauteurCase, this);
						break;
                }
	}

	int largeur() {
		return getWidth();
	}

	int hauteur() {
		return getHeight();
	}

	public int largeurCase() {
		return largeurCase;
	}

	public int hauteurCase() {
		return hauteurCase;
	}

	@Override
	public void miseAJour() {
		repaint();
	}
}
