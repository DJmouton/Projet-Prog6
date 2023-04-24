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

	/*
	* Peindre le plateau de jeu
	*/
	@Override
	public void paintComponent(Graphics g) {
		try
		{
			gaufre = ImageIO.read(new File("src/main/resources/assets/gaufre.png"));
			poison = ImageIO.read(new File("src/main/resources/assets/poison.png"));
		}

		catch(IOException exc)
		{
			System.out.println("Erreur de chargement des assets");
		}

		Graphics2D drawable = (Graphics2D) g;

        int lignes = jeu.hauteur();
        int colonnes = jeu.largeur();
        largeurCase = largeur() / colonnes;
        hauteurCase = hauteur() / lignes;

		// Rectangle clair en fond
        g.clearRect(0, 0, largeur(), hauteur());

		// Fin de la partie
        if (!jeu.enCours()) {
			g.setColor(Color.WHITE);
	        g.drawRect(0, 0, largeur(), hauteur());
			g.clearRect(0, 0, largeur(), hauteur());
	        g.setColor(Color.BLACK);
	        g.drawString("La partie est terminée", largeur() / 3, hauteur() - 5);
        }

        // Grille
		g.drawImage(poison, 0, 0, largeurCase, hauteurCase, this);

        // Coups
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case 0:
                        // Case de gaufre
						g.drawImage(gaufre, j*largeurCase, i*hauteurCase, largeurCase, hauteurCase, this);
                        break;
                    case 1:
						if(!(i == 0 && j == 0)) {
							// Croix rouge sur les cases mangées.
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