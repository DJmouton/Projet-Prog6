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
package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	NiveauGraphique niv;
	CollecteurEvenements control;

	AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
		niv = n;
		control = c;
	}

	/*
	 * Clic reçu. On calcule la case du plateau cliqué et on envoie un évènement
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int l;
		int c;
		float y = (float) e.getY() / (niv.hauteurCase() * (float) 1.3);
		y *= (float) 1.3;
		float x;
		if ((int) y % 2 == 1) {
			x = ((e.getX() - (niv.largeurCase() * (float) 0.9 / 2)) / niv.largeurCase() * (float) 1.1);
		} else {
			x = (float) e.getX() / niv.largeurCase() * (float) 1.1;
		}
		if ((int) x > 7 || (int) y > 7) {
			return;
		} else {
			l = (int) x;
			c = (int) y;
		}
		control.clicSouris(c, l);
	}
}
