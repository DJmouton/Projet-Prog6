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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoueur implements ActionListener {
	CollecteurEvenements control;
	JToggleButton toggle;
	int num;

	AdaptateurJoueur(CollecteurEvenements c, JToggleButton b, int n) {
		control = c;
		toggle = b;
		num = n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (toggle.isSelected())
			control.changeJoueur(num, 1);
		else
			control.changeJoueur(num, 0);
	}
}
