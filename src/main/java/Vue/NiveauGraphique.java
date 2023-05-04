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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

		g.setFont(new Font("Arial", Font.BOLD, 30));
		/* Load Assets */
		BufferedImage[] assetsPlateau = new BufferedImage[19];
		BufferedImage waterBG = null;


		try {
			waterBG = ImageIO.read(new File("resources/assets/waterTile.png"));
			assetsPlateau[0] = ImageIO.read(new File("resources/assets/water.png"));
			assetsPlateau[1] = ImageIO.read(new File("resources/assets/poisson1.png"));
			assetsPlateau[2] = ImageIO.read(new File("resources/assets/poisson2.png"));
			assetsPlateau[3] = ImageIO.read(new File("resources/assets/poisson3.png"));
			assetsPlateau[4] = ImageIO.read(new File("resources/assets/penguinBleu.png"));
			assetsPlateau[5] = ImageIO.read(new File("resources/assets/penguinVert.png"));
			assetsPlateau[6] = ImageIO.read(new File("resources/assets/penguinRouge.png"));
			assetsPlateau[7] = ImageIO.read(new File("resources/assets/penguinJaune.png"));
			assetsPlateau[8] = ImageIO.read(new File("resources/assets/penguinBleuSel.png"));
			assetsPlateau[9] = ImageIO.read(new File("resources/assets/penguinVertSel.png"));
			assetsPlateau[10] = ImageIO.read(new File("resources/assets/penguinRougeSel.png"));
			assetsPlateau[11] = ImageIO.read(new File("resources/assets/penguinJauneSel.png"));
			assetsPlateau[12] = ImageIO.read(new File("resources/assets/poisson1Sel.png"));
			assetsPlateau[13] = ImageIO.read(new File("resources/assets/poisson2Sel.png"));
			assetsPlateau[14] = ImageIO.read(new File("resources/assets/poisson3Sel.png"));
			assetsPlateau[15] = ImageIO.read(new File("resources/assets/penguin.png"));
			assetsPlateau[16] = ImageIO.read(new File("resources/assets/penguin_vert.png"));
			assetsPlateau[17] = ImageIO.read(new File("resources/assets/penguin_rouge.png"));
			assetsPlateau[18] = ImageIO.read(new File("resources/assets/penguin_jaune.png"));

		} catch (IOException exc) {
			System.out.println("Erreur dans le chargement des images");
		}

		// Fin de la partie
		if (!jeu.enCours()) {
			g.drawString("La partie est terminée", largeur() / 3, hauteur() / 2);
		}

		int lignes = jeu.hauteur();
		int colonnes = jeu.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;

		// Rectangle d'océan (bleu) en fond
		g.drawImage(waterBG, 0, 0, largeur(), hauteur(), this);

		// DIMINUE LA TAILLE DES IMAGES IMPORTANT A PRENDRE EN COMPTE POUR LE CALCUL DES POSITIONS
		((Graphics2D) g).scale(0.9, 1.3);

				// Grille
		float height;
		// Formule pour calculer la distance entre 2 hexagons
		height = (float) 3 / 4 * (float) hauteurCase;
		int hauteur, largeur;
		for (int i = 0; i < (lignes); i++) {
			hauteur = (int) ((float) i * (height));
			for (int j = 0; j < (colonnes); j++) {
				if (jeu.valeur(i, j) == 0) continue;
				if (i % 2 == 1)
					largeur = j * largeurCase + largeurCase / 2;
				else
					largeur = j * largeurCase;
				g.drawImage(assetsPlateau[jeu.valeur(i, j)], largeur, hauteur,
							largeurCase, hauteurCase, this);
			}
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

	public ArrayList<int[]> hexAccessible(int x, int y) {
		return jeu.hex_accessible(x, y);
	}

	public boolean pingouinSel(int x, int y) {
		return jeu.plateau[x][y] == jeu.joueurCourant + 4;
	}

	@Override
	public void miseAJour() {
		repaint();
	}
}
