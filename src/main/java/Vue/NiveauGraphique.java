package Vue;

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

	// Variables pour dessiner l'historique du dernier coup
	int x2, y2, hauteur1, largeur1, hauteur2, largeur2;

	// Formule pour calculer la distance entre 2 hexagons
	float height;

	float[] dashingPattern2 = {20f, 10f};
	Stroke stroke = new BasicStroke(2f, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 2.0f, dashingPattern2, 1.0f);
	Stroke stroke2 = new BasicStroke(4f, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 2.0f, dashingPattern2, 1.0f);

	// Liste des coordonnées d'hexagones à dessiner
	ArrayList<int[]> dessinplat;

	BufferedImage[] assetsPlateau = new BufferedImage[29];


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
			if (!(control.valeur(x, y) == 0))
				g.drawImage(assetsPlateau[control.valeur(x, y) + 8], largeur, hauteur,
						largeurCase, hauteurCase, this);
		}
	}

	public void dernierCoup(Graphics g, Graphics2D drawable) {
		if (control.etatSel() && control.dernierCoupEstDeplacement()) {
			try {
				historique();
				// Dessins d'un trait en pointillé
				drawable.setStroke(stroke2);
				g.drawLine(largeur1, hauteur1, largeur2, hauteur2);
				g.setColor(Color.RED);
				drawable.setStroke(stroke);
				g.drawLine(largeur1, hauteur1, largeur2, hauteur2);
				g.setColor(Color.BLACK);

				// Dessin du pingouins fantôme du dernier coup
				g.drawImage(assetsPlateau[23], largeur, hauteur,
						largeurCase, hauteurCase, this);
			} catch (NullPointerException e) {
				System.out.println("Pas de coup disponible");
			}

		}
	}

	private void historique() {

		x = control.coupSrcL();
		y = control.coupSrcC();

		x2 = control.coupDestL();
		y2 = control.coupDestC();

		hauteur = Math.round((float) x * height);

		if (x % 2 == 1)
			largeur = y * largeurCase + largeurCase / 2;
		else
			largeur = y * largeurCase;

		largeur1 = largeur;
		hauteur1 = hauteur;

		hauteur2 = Math.round((float) x2 * height);
		if (x2 % 2 == 1)
			largeur2 = y2 * largeurCase + largeurCase / 2;
		else
			largeur2 = y2 * largeurCase;

		hauteur1 += hauteurCase / 2;
		hauteur2 += hauteurCase / 2;
		largeur1 = (largeur1 - (largeurCase / 2)) + largeurCase;
		largeur2 = (largeur2 - (largeurCase / 2)) + largeurCase;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D drawable = (Graphics2D) g;

		drawable.setStroke(stroke);

		g.setFont(new Font("Arial", Font.BOLD, 30));

		/* Load Assets */
		try {
			assetsPlateau[0] = ImageIO.read(new File("resources/assets/sablier.png"));
			assetsPlateau[1] = ImageIO.read(new File("resources/assets/p1.png"));
			assetsPlateau[2] = ImageIO.read(new File("resources/assets/p2.png"));
			assetsPlateau[3] = ImageIO.read(new File("resources/assets/p3.png"));
			assetsPlateau[4] = ImageIO.read(new File("resources/assets/bleu.png"));
			assetsPlateau[5] = ImageIO.read(new File("resources/assets/vert.png"));
			assetsPlateau[6] = ImageIO.read(new File("resources/assets/rouge.png"));
			assetsPlateau[7] = ImageIO.read(new File("resources/assets/jaune.png"));
			assetsPlateau[8] = ImageIO.read(new File("resources/assets/dead.png"));
			assetsPlateau[9] = ImageIO.read(new File("resources/assets/p1S.png"));
			assetsPlateau[10] = ImageIO.read(new File("resources/assets/p2S.png"));
			assetsPlateau[11] = ImageIO.read(new File("resources/assets/p3S.png"));
			assetsPlateau[12] = ImageIO.read(new File("resources/assets/bleuC.png"));
			assetsPlateau[13] = ImageIO.read(new File("resources/assets/vertC.png"));
			assetsPlateau[14] = ImageIO.read(new File("resources/assets/rougeC.png"));
			assetsPlateau[15] = ImageIO.read(new File("resources/assets/jauneC.png"));
			assetsPlateau[16] = ImageIO.read(new File("resources/assets/bleuS.png"));
			assetsPlateau[17] = ImageIO.read(new File("resources/assets/vertS.png"));
			assetsPlateau[18] = ImageIO.read(new File("resources/assets/rougeS.png"));
			assetsPlateau[19] = ImageIO.read(new File("resources/assets/jauneS.png"));
			assetsPlateau[20] = ImageIO.read(new File("resources/assets/p1M.png"));
			assetsPlateau[21] = ImageIO.read(new File("resources/assets/p2M.png"));
			assetsPlateau[22] = ImageIO.read(new File("resources/assets/p3M.png"));
			assetsPlateau[23] = ImageIO.read(new File("resources/assets/hist.png"));

		} catch (IOException e) {
			System.out.println("Erreur dans le chargement des images");
			throw new RuntimeException(e);
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
		if (control.etatPla())
			try {
				dessinplat = new ArrayList<>(control.getCases(1));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Pingouins selectionnable durant son tour
		if (!control.etatPla())
			try {
				dessinplat = new ArrayList<>(control.getCases(control.joueurCourant() + 4));
				feedforward(g, assetsPlateau, dessinplat);

				// Pingouins actuellement selectionné
				historique();
				if (!(control.valeur(x, y) == 0) && !(control.pingSel()[0] == 0 && control.pingSel()[1] == 0)) {
					if (control.valeur(x, y) > 3)
						g.drawImage(assetsPlateau[control.valeur(x, y) + 12], largeur, hauteur,
								largeurCase, hauteurCase, this);
				}
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Case Selectionnable après sélection d'un pingouins
		if (control.etatDep())
			try {
				dessinplat = (control.hexAccessible(control.coupSrcL(), control.coupSrcC()));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Dessine un sablier si l'IA joue
		if (control.estIA() && !control.isConsultation())
			g.drawImage(assetsPlateau[0], 0, 0, largeurCase, hauteurCase, this);

		// Dessine l'historique du dernier coup joué
		dernierCoup(g, drawable);
	}

	int largeur() {
		return taille();
	}

	int hauteur() {
		return taille();
	}

	int taille() {
		return Math.min(getWidth(), getHeight());
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
