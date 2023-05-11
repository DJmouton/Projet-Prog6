package Vue;

import Modele.Etats;

import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NiveauGraphique extends JComponent implements Observateur {

	CollecteurEvenements control;

	// Variables de positionnement pour le dessin
	int x, y, hauteur, largeur, lignes, colonnes, largeurCase, hauteurCase;

	// Formule pour calculer la distance entre 2 hexagons
	float height;

	Timer timer;

	// Liste des coordonnées d'hexagones à dessiner
	ArrayList<int[]> dessinplat;

	public NiveauGraphique(CollecteurEvenements c) {
		control = c;
		control.ajouteObservateur(this);
	}

	private void grille(Graphics g, BufferedImage[] assetsPlateau) {
		for (int i = 0; i < (lignes); i++) {
			hauteur = Math.round((float) i * height);
			for (int j = 0; j < (colonnes); j++) {
				if (control.valeur(i, j) == 0) continue;
				if (i % 2 == 1)
					largeur = j * largeurCase + largeurCase / 2;
				else
					largeur = j * largeurCase;
				g.drawImage(assetsPlateau[control.valeur(i, j)], largeur, hauteur,
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
			if (control.valeur(x, y) == 0) ;
			else
				g.drawImage(assetsPlateau[control.valeur(x, y) + 7], largeur, hauteur,
						largeurCase, hauteurCase, this);
		}
	}
  
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

		g.setFont(new Font("Arial", Font.BOLD, 30));
		/* Load Assets */
		BufferedImage[] assetsPlateau = new BufferedImage[23];

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
			assetsPlateau[19] = ImageIO.read(new File("resources/assets/bleuH.png"));
			assetsPlateau[20] = ImageIO.read(new File("resources/assets/vertH.png"));
			assetsPlateau[21] = ImageIO.read(new File("resources/assets/rougeH.png"));
			assetsPlateau[22] = ImageIO.read(new File("resources/assets/jauneH.png"));

		} catch (IOException exc) {
			System.out.println("Erreur dans le chargement des images");
		}

		// Fin de la partie
		if (!control.partieEnCours()) {
			g.drawString("La partie est terminée", largeur() / 3, hauteur() / 2);
		}

		// Rectangle d'océan (bleu) en fond
		g.setColor(new Color(51, 153, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		// DIMINUE LA TAILLE DES IMAGES IMPORTANT A PRENDRE EN COMPTE POUR LE CALCUL DES POSITIONS
		((Graphics2D) g).scale(1, 1);

		// Variable du plateau
		lignes = control.hauteur();
		colonnes = control.largeur();
		largeurCase = largeur() / colonnes;
		hauteurCase = hauteur() / lignes;
		height = (3f / 4f) * (float) hauteurCase;

		// Grille
		grille(drawable, assetsPlateau);

		// Case selectionnable durant la partie de placement des pingouins
		if (control.etatCourant() == Etats.Initialisation)
			try {
				dessinplat = new ArrayList<>(control.getPinguins(1));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Pingouins selectionnable durant son tour
		if (control.etatCourant() != Etats.Initialisation)
			try {
				dessinplat = new ArrayList<>(control.getPinguins(control.joueurCourant() + 4));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Case Selectionnable après sélection d'un pingouins
		if (control.etatCourant() == Etats.Deplacement)
			try {
				dessinplat = (control.hexAccessible(control.coupSrcL(), control.coupSrcC()));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Montre le pingouins selectionné par le joueur courant
		// TODO : Trouver le pingouins cliqué par le joueur courant

		// Dessine un sablier si l'IA joue
		if (control.estIA())
			g.drawImage(assetsPlateau[0], 0, 0, largeurCase, hauteurCase, this);

		// TODO : Dessine l'animation de déplacement d'un pingouins
		if (control.etatCourant() != Etats.Initialisation)
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
