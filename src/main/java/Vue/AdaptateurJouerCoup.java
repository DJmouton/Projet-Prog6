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

import Modele.IA;
import Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdaptateurJouerCoup implements ActionListener {
	CollecteurEvenements control;
	JTextField coupX;
	JTextField coupY;

	AdaptateurJouerCoup(CollecteurEvenements c, JTextField x, JTextField y) {
		control = c;
		coupX = x;
		coupY = y;
	}

	/*
	 * Une taille a été entrée. On la convertie en entier et on envoie un évènement.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String x = coupX.getText();
		String y = coupY.getText();
		try {
			int valX = Integer.parseInt(x);
			int valY = Integer.parseInt(y);
//			control.clicSouris(valX, valY);
//			System.out.println("ILOT: "+IA.getIlot((Jeu) control.getJeu(), ((Jeu) control.getJeu()).getPingouins()));
			/*Jeu jeu = (Jeu) control.getJeu();
			ArrayList<ArrayList<int[]>> result = IA.getNombre(jeu,jeu.getPingouins());
			System.out.println("ILOT: ");
			for(ArrayList<int[]> l : result){
				System.out.print("Pingouin: "+jeu.plateau[l.get(0)[0]][l.get(0)[1]]);
				int somme = 0;
				for(int i =1; i < l.size(); i++){
					int[] coord = l.get(i);
					int value = jeu.plateau[coord[0]][coord[1]];
					if(value < 4) {
						somme += value;
					}
				}
				System.out.println(" "+somme);
			}*/
		} catch (Exception ex) {
			// On ne fait rien si la valeur est invalide
		}
	}
}
