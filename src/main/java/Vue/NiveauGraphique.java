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

		int score;
		String s;
		int lignes = jeu.hauteur();
		int colonnes = jeu.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;

		// Rectangle d'océan (bleu) en fond
		g.drawImage(waterBG, 0, 0, getWidth(), getHeight(), this);

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
		// Tour des joueurs
		if (jeu.joueurCourant == 0) {
			g.drawImage(assetsPlateau[15], 0, 0, largeurCase / 2, hauteurCase / 2, this);
		} else if (jeu.joueurCourant == 1) {
			g.drawImage(assetsPlateau[16], 0, 30, largeurCase / 2, hauteurCase / 2, this);
		} else if (jeu.joueurCourant == 2) {
			g.drawImage(assetsPlateau[17], 0, 60, largeurCase / 2, hauteurCase / 2, this);
		} else if (jeu.joueurCourant == 3) {
			g.drawImage(assetsPlateau[18], 0, 90, largeurCase / 2, hauteurCase / 2, this);
		} else {

		}


		g.setFont(new Font("Arial", Font.PLAIN,  largeurCase / 10));
		score = jeu.joueurs[jeu.joueurCourant].getScore();
		String s1 = " : " + score;
		s = "Score J" + (jeu.joueurCourant+1) + s1;
		g.drawString(s, 0, hauteurCase);
	}

	int largeur() {
		//return getWidth();
		return taille();
	}

	int hauteur() {
		//return getHeight();
		return taille();
	}

	int taille(){
		if(getWidth() < getHeight())
			return getWidth();
		else
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
