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
import java.util.Random;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	/*
	* Peindre le plateau de jeu
	*/
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

		/* Load Assets */
		BufferedImage water = null;
		BufferedImage waterBG = null;
		BufferedImage penguinB = null;
		BufferedImage penguinV = null;
		BufferedImage penguinR = null;
		BufferedImage penguinJ = null;
		BufferedImage poisson1 = null;
		BufferedImage poisson2 = null;
		BufferedImage poisson3 = null;


		try {
			water = ImageIO.read(new File("resources/assets/water.png"));
			waterBG = ImageIO.read(new File("resources/assets/waterTile.png"));
			penguinB = ImageIO.read(new File("resources/assets/penguinBleu.png"));
			penguinV = ImageIO.read(new File("resources/assets/penguinVert.png"));
			penguinR = ImageIO.read(new File("resources/assets/penguinRouge.png"));
			penguinJ = ImageIO.read(new File("resources/assets/penguinJaune.png"));
			poisson1 = ImageIO.read(new File("resources/assets/poisson1.png"));
			poisson2 = ImageIO.read(new File("resources/assets/poisson2.png"));
			poisson3 = ImageIO.read(new File("resources/assets/poisson3.png"));

		} catch (IOException exc) {
			System.out.println("Erreur dans le chargement des images");
		}

		int lignes = jeu.hauteur();
		int colonnes = jeu.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;

		// Rectangle d'océan (bleu) en fond
		g.drawImage(waterBG, 0, 0, largeur(), hauteur(), this);

		// Fin de la partie
		if (!jeu.enCours()) {
			g.drawString("La partie est terminée", largeur() / 3, hauteur() - 5);
		}

		// Grille
		float height;
		height = (float)3/4 * (float)hauteurCase;
		int hauteur;
		for (double i = 0; i < (lignes); i++) {
			hauteur = (int) ((float) i * (height));
			if (i % 2 == 1) {
				for (double j = 0; j < (colonnes - 3); j++) {
					switch (jeu.plateau[i][j]) {
						case 0:
							g.drawImage(water, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 1:
							g.drawImage(poisson1, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 2:
							g.drawImage(poisson2, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 3:
							g.drawImage(poisson3, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 4:
							g.drawImage(penguinV, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 5:
							g.drawImage(penguinR, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 6:
							g.drawImage(penguinB, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 7:
							g.drawImage(penguinJ, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
									largeurCase, hauteurCase, this);
							break;
					}
					g.drawImage(poisson3, (int) (j + 1) * largeurCase + largeurCase / 2, hauteur,
							largeurCase, hauteurCase, this);
				}
			} else {
				for (double j = 0; j < (colonnes - 2); j++) {
					g.drawImage(poisson3, (int) (j + 1) * largeurCase, hauteur,
							largeurCase, hauteurCase, this);
					switch (jeu.plateau[i][j]) {
						case 0:
							g.drawImage(water, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 1:
							g.drawImage(poisson1, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 2:
							g.drawImage(poisson2, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;
						case 3:
							g.drawImage(poisson3, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 4:
							g.drawImage(penguinV, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 5:
							g.drawImage(penguinR, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 6:
							g.drawImage(penguinB, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;

						case 7:
							g.drawImage(penguinJ, (int) (j + 1) * largeurCase, hauteur,
									largeurCase, hauteurCase, this);
							break;
					}
				}
			}
		}

		// Coups / Placement des Pingouins
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case 0:
						//g.drawImage(iceTile, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase, this);
                        break;

	                case 1:

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
