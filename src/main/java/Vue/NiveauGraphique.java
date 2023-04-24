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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	/* Load assets */
	BufferedImage gaufre = null;
	BufferedImage poison = null;
	BufferedImage quickGame = null;
	BufferedImage p1 = null;
	BufferedImage p2 = null;
	BufferedImage perso = null;
	BufferedImage menu = null;
	BufferedImage couteau = null;
	BufferedImage fourchette = null;
	BufferedImage reticule = null;
	BufferedImage retour = null;
	BufferedImage question = null;
	BufferedImage zoomIn = null;
	BufferedImage zoomOut = null;

	/*
	* Peindre le plateau de jeu
	*/
	@Override
	public void paintComponent(Graphics g) {
		try
		{
			gaufre = ImageIO.read(new File("src/main/resources/assets/gaufre.png"));
			couteau = ImageIO.read(new File("src/main/resources/assets/couteau.png"));
			fourchette = ImageIO.read(new File("src/main/resources/assets/fourchette.png"));
			menu = ImageIO.read(new File("src/main/resources/assets/menuList.png"));
			perso = ImageIO.read(new File("src/main/resources/assets/personnage.png"));
			p1 = ImageIO.read(new File("src/main/resources/assets/players_1.png"));
			p2 = ImageIO.read(new File("src/main/resources/assets/players_2.png"));
			poison = ImageIO.read(new File("src/main/resources/assets/poison.png"));
			question = ImageIO.read(new File("src/main/resources/assets/question.png"));
			quickGame = ImageIO.read(new File("src/main/resources/assets/quickgame.png"));
			reticule = ImageIO.read(new File("src/main/resources/assets/reticule.png"));
			retour = ImageIO.read(new File("src/main/resources/assets/return.png"));
			zoomIn = ImageIO.read(new File("src/main/resources/assets/zoomIn.png"));
			zoomOut = ImageIO.read(new File("src/main/resources/assets/zoomOut.png"));
		}
		catch(IOException exc)
		{
			System.out.println("Erreur d'affichage");
		}

		Graphics2D drawable = (Graphics2D) g;
        int lignes = jeu.hauteur();
        int colonnes = jeu.largeur();
        largeurCase = largeur() / colonnes;
        hauteurCase = hauteur() / lignes;

        g.clearRect(0, 0, largeur(), hauteur());
		// Fin de la partie
        if (!jeu.enCours())
            g.drawString("La partie est terminée", 20, hauteur()/2);

        // Grille
		g.drawImage(poison, 0, 0, largeurCase, hauteurCase, this);

        // Coups
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case 0:
                        //g.drawOval(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
						g.drawImage(gaufre, j*largeurCase, i*hauteurCase, largeurCase, hauteurCase, this);
                        break;
                    case 1:
						if(!(i == 0 && j == 0)) {
							g.setColor(Color.RED);
							g.drawLine(j * largeurCase, i * hauteurCase, (j + 1) * largeurCase, (i + 1) * hauteurCase);
							g.drawLine(j * largeurCase, (i + 1) * hauteurCase, (j + 1) * largeurCase, i * hauteurCase);
							g.setColor(Color.BLACK);
						}
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