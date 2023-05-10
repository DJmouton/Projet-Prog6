package Vue;

import Modele.Etats;
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

	// Variables de positionnement pour le dessin
	int x, y, hauteur, largeur, lignes, colonnes, largeurCase, hauteurCase;

	// Formule pour calculer la distance entre 2 hexagons
	float height;

	Timer timer;

	// Liste des coordonnées d'hexagones à dessiner
	ArrayList<int[]> dessinplat;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	private void grille(Graphics g, BufferedImage[] assetsPlateau) {
		for (int i = 0; i < (lignes); i++) {
			hauteur = Math.round((float) i * height);
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

	private void feedforward(Graphics g, BufferedImage[] assetsPlateau,
	                         ArrayList<int[]> dessinplat) {
		for (int[] plat : dessinplat) {
			x = plat[0];
			y = plat[1];
			hauteur = Math.round((float) x * height);
			if (x % 2 == 1) {
				largeur = y * largeurCase + largeurCase / 2;
			} else {
				largeur = y * largeurCase;
			}
			if (jeu.valeur(x, y) == 0);
			else
				g.drawImage(assetsPlateau[jeu.valeur(x, y) + 7], largeur, hauteur,
						largeurCase, hauteurCase, this);
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

		g.setFont(new Font("Arial", Font.BOLD, 30));
		/* Load Assets */
		BufferedImage[] assetsPlateau = new BufferedImage[19];

		try {
			assetsPlateau[0] = ImageIO.read(new File("resources/assets/sablier.png"));
			assetsPlateau[1] = ImageIO.read(new File("resources/assets/poisson1.png"));
			assetsPlateau[2] = ImageIO.read(new File("resources/assets/poisson2.png"));
			assetsPlateau[3] = ImageIO.read(new File("resources/assets/poisson3.png"));
			assetsPlateau[4] = ImageIO.read(new File("resources/assets/bleu.png"));
			assetsPlateau[5] = ImageIO.read(new File("resources/assets/vert.png"));
			assetsPlateau[6] = ImageIO.read(new File("resources/assets/rouge.png"));
			assetsPlateau[7] = ImageIO.read(new File("resources/assets/jaune.png"));
			assetsPlateau[8] = ImageIO.read(new File("resources/assets/poisson1Sel.png"));
			assetsPlateau[9] = ImageIO.read(new File("resources/assets/poisson2Sel.png"));
			assetsPlateau[10] = ImageIO.read(new File("resources/assets/poisson3Sel.png"));
			assetsPlateau[11] = ImageIO.read(new File("resources/assets/bleuC.png"));
			assetsPlateau[12] = ImageIO.read(new File("resources/assets/vertC.png"));
			assetsPlateau[13] = ImageIO.read(new File("resources/assets/rougeC.png"));
			assetsPlateau[14] = ImageIO.read(new File("resources/assets/jauneC.png"));
			assetsPlateau[15] = ImageIO.read(new File("resources/assets/bleuSel.png"));
			assetsPlateau[16] = ImageIO.read(new File("resources/assets/vertSel.png"));
			assetsPlateau[17] = ImageIO.read(new File("resources/assets/rougeSel.png"));
			assetsPlateau[18] = ImageIO.read(new File("resources/assets/jauneSel.png"));


		} catch (IOException exc) {
			System.out.println("Erreur dans le chargement des images");
		}

		// Fin de la partie
		if (!jeu.enCours()) {
			g.drawString("La partie est terminée", largeur() / 3, hauteur() / 2);
		}

		// Rectangle d'océan (bleu) en fond
		g.setColor(new Color(51, 153, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		// DIMINUE LA TAILLE DES IMAGES IMPORTANT A PRENDRE EN COMPTE POUR LE CALCUL DES POSITIONS
		((Graphics2D) g).scale(1, 1);

		// Variable du plateau
		lignes = jeu.hauteur();
		colonnes = jeu.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;
		height = (3f / 4f) * (float) hauteurCase;

		// Grille
		grille(drawable, assetsPlateau);

		// Case selectionnable durant la partie de placement des pingouins
		if (jeu.etatCourant() == Etats.Initialisation)
			try {
				dessinplat = new ArrayList<>(jeu.getPingouins(1));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Pingouins selectionnable durant son tour
		if (jeu.etatCourant() != Etats.Initialisation)
			try {
				dessinplat = new ArrayList<>(jeu.getPingouins(jeu.joueurCourant + 4));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Case Selectionnable après sélection d'un pingouins
		if (jeu.etatCourant() == Etats.Deplacement)
			try {
				dessinplat = new ArrayList<>(jeu.hexAccess);
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Montre le pingouins selectionné par le joueur courant
		/*
		if (jeu.etatCourant() == Etats.Deplacement)
			x = clicSouris.l
			y = clicSouris.c
			hauteur = Math.round((float) x * height);
			if (x % 2 == 1) {
				largeur = y * largeurCase + largeurCase / 2;
			} else {
				largeur = y * largeurCase;
			}
			if (jeu.valeur(x, y) == 0);
			else
				g.drawImage(assetsPlateau[jeu.valeur(x, y) + 11], largeur, hauteur,
						largeurCase, hauteurCase, this);
		 */

		// Dessine un sablier si l'IA joue
		/* if (jeu.joueurs[jeu.joueurCourant].estIA)
			g.drawImage(sablier, 0, 0, largeurCase, hauteurCase, this); */

		// TODO : Dessine l'animation de déplacement d'un pingouins
		if (jeu.etatCourant() != Etats.Initialisation)
			;
			// Need : sources et destination
		
	}

	int largeur() {
		//return getWidth();
		return taille();
	}

	int hauteur() {
		//return getHeight();
		return taille();
	}

	int taille() {
		if (getWidth() < getHeight())
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
