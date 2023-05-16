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
	int x1, x2, y1, y2, hauteur1, largeur1, hauteur2, largeur2;

	// Formule pour calculer la distance entre 2 hexagons
	float height;

	Timer timer;

	float[] dashingPattern2 = {10f, 4f};
	Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER, 1.0f, dashingPattern2, 0.0f);

	// Liste des coordonnées d'hexagones à dessiner
	ArrayList<int[]> dessinplat;

	BufferedImage[] assetsPlateau = new BufferedImage[28];


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
				g.drawImage(assetsPlateau[control.valeur(x, y) + 7], largeur, hauteur,
						largeurCase, hauteurCase, this);
		}
	}

	public void dernierCoup(Graphics g) {
		if (control.etatSel()) {
			try {
				historique();
				g.setColor(Color.MAGENTA);
				g.drawLine(largeur1, hauteur1, largeur2, hauteur2);
				g.setColor(Color.BLACK);
				g.drawImage(assetsPlateau[27], largeur, hauteur,
						largeurCase, hauteurCase, this);
			} catch (NullPointerException e) {
				System.out.println("Pas de coup disponible");
			}

		}
	}

	private void historique() {
		x = control.coupSrcL();
		y = control.coupSrcC();

		x1 = control.coupSrcL();
		y1 = control.coupSrcC();
		x2 = control.coupDestL();
		y2 = control.coupDestC();
		hauteur = Math.round((float) x * height);
		if (x % 2 == 1)
			largeur = y * largeurCase + largeurCase / 2;
		else
			largeur = y * largeurCase;

		hauteur1 = Math.round((float) x1 * height);
		if (x1 % 2 == 1)
			largeur1 = y1 * largeurCase + largeurCase / 2;
		else
			largeur1 = y1 * largeurCase;

		hauteur2 = Math.round((float) x2 * height);
		if (x2   % 2 == 1)
			largeur2 = y2 * largeurCase + largeurCase / 2;
		else
			largeur2 = y2 * largeurCase;

		hauteur1 += hauteurCase / 2;
		hauteur2 += hauteurCase / 2;
		largeur1 -= largeurCase / 2;
		largeur2 -= largeurCase / 2;
		largeur1 += largeurCase;
		largeur2 += largeurCase;
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
			assetsPlateau[8] = ImageIO.read(new File("resources/assets/p1S.png"));
			assetsPlateau[9] = ImageIO.read(new File("resources/assets/p2S.png"));
			assetsPlateau[10] = ImageIO.read(new File("resources/assets/p3S.png"));
			assetsPlateau[11] = ImageIO.read(new File("resources/assets/bleuC.png"));
			assetsPlateau[12] = ImageIO.read(new File("resources/assets/vertC.png"));
			assetsPlateau[13] = ImageIO.read(new File("resources/assets/rougeC.png"));
			assetsPlateau[14] = ImageIO.read(new File("resources/assets/jauneC.png"));
			assetsPlateau[15] = ImageIO.read(new File("resources/assets/bleuS.png"));
			assetsPlateau[16] = ImageIO.read(new File("resources/assets/vertS.png"));
			assetsPlateau[17] = ImageIO.read(new File("resources/assets/rougeS.png"));
			assetsPlateau[18] = ImageIO.read(new File("resources/assets/jauneS.png"));
			assetsPlateau[19] = ImageIO.read(new File("resources/assets/bleuH.png"));
			assetsPlateau[20] = ImageIO.read(new File("resources/assets/vertH.png"));
			assetsPlateau[21] = ImageIO.read(new File("resources/assets/rougeH.png"));
			assetsPlateau[22] = ImageIO.read(new File("resources/assets/jauneH.png"));
			assetsPlateau[23] = ImageIO.read(new File("resources/assets/bleuP.png"));
			assetsPlateau[24] = ImageIO.read(new File("resources/assets/vertP.png"));
			assetsPlateau[25] = ImageIO.read(new File("resources/assets/rougeP.png"));
			assetsPlateau[26] = ImageIO.read(new File("resources/assets/jauneP.png"));
			assetsPlateau[27] = ImageIO.read(new File("resources/assets/hist.png"));


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
				dessinplat = new ArrayList<>(control.getPinguins(1));
				feedforward(g, assetsPlateau, dessinplat);
			} catch (NullPointerException e) {
				System.out.println("Erreur d'initialisation de liste");
			}

		// Pingouins selectionnable durant son tour
		if (!control.etatPla())
			try {
				dessinplat = new ArrayList<>(control.getPinguins(control.joueurCourant() + 4));
				feedforward(g, assetsPlateau, dessinplat);

				// Pingouins actuellement selectionné
				historique();
				if (!(control.valeur(x, y) == 0))
					g.drawImage(assetsPlateau[control.valeur(x, y) + 11], largeur, hauteur,
							largeurCase, hauteurCase, this);
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
		if (control.estIA())
			g.drawImage(assetsPlateau[0], 0, 0, largeurCase, hauteurCase, this);

		// TODO : Dessine l'animation de déplacement d'un pingouin
		dernierCoup(g);
		// ligneHist(g);
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
