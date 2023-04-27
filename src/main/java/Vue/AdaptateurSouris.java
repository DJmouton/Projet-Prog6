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

	private float [] axial_to_cube(float [] hex) {
		float q = hex[0];
		float r = hex[1];
		float s = -q-r;
		float [] cube = new float[3];
		cube[0] = q;
		cube[1] = r;
		cube[2] = s;
		return cube;
	}

	private float [] cube_to_axial(float [] cube) {
		float q = cube[0];
		float r = cube[1];
		float[] hex = new float[2];
		hex[0] = q;
		hex[1] = r;
		return hex;
	}

	private float [] cube_round(float [] frac) {
		float qf = frac[0];
		float rf = frac[1];
		float sf = frac[2];

		float [] cube = new float[3];
		float q = Math.round(qf);
		float r = Math.round(rf);
		float s = Math.round(sf);

		float q_diff = Math.abs(q - qf);
		float r_diff = Math.abs(r - rf);
		float s_diff = Math.abs(s - sf);

		if (q_diff > r_diff && q_diff > s_diff)
			q = -r-s;
		else if (r_diff > s_diff)
			r = -q-s;
        else
			s = -q-r;

		cube[0] = q;
		cube[1] = r;
		cube[2] = s;
		return cube;
	}

	private float [] axial_round(float [] hex) {
		return cube_to_axial(cube_round(axial_to_cube(hex)));
	}
	/*
	* Clic reçu. On calcule la case du plateau cliqué et on envoie un évènement
	*/
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int size = niv.hauteurCase / 2;

		float q = (float)((Math.sqrt(3)/3 * x) - ((1.0)/3.0 * y) / size);
		float r = (float)((2.0/3.0 * y) / size);
		float[] hex = new float[2];
		hex[0] = q;
		hex[1] = r;
		float[] res;
		res = axial_round(hex);
		int c = (int)res[0];
		int l = (int)res[1];
		control.clicSouris(c, l);
	}
}
