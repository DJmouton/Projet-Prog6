package Vue;

import javax.swing.*;

public class ComposantPanneauRegles extends JPanel {

	ComposantPanneauRegles() {
		add(new JLabel("<html>2 à 4 joueurs<br>" +
				"2 joueurs = 4 pingouins chacun<br>" +
				"3 joueurs = 3 pingouins chacun<br>" +
				"4 joueurs = 2 pingouins chacun<br>" +
				"<br>" +
				"- 1ère phase : Placement<br>" +
				"Chaque joueur pose à tour de rôle un pingouin sur un iceberg à 1 poisson<br>" +
				"jusqu'à ce que tous les pingouins soient posés.<br>" +
				"<br>" +
				"- 2ème phase : Déplacement<br>" +
				"Chaque joueur déplace à tour de rôle un de ses pingouins sur un iceberg.<br>" +
				"Cliquer sur un pingouin entouré pour le sélectionner.<br>" +
				"Ensuite cliquer sur un iceberg entouré pour le déplacer à cet endroit.<br>" +
				"Le joueur récupère alors les poissons se trouvant sur cet iceberg<br>" +
				"ainsi que l'iceberg où se trouvait le pingouin avant son déplacement.<br>" +
				"<br>" +
				"- Fin de partie<br>" +
				"Un pingouin est retiré du jeu si il ne peut plus se déplacer.<br>" +
				"Un joueur est éliminé si il a perdu tous ses pingouins.<br>" +
				"La partie se termine lorsqu'il n'y a plus aucun pingouin en jeu.<br>" +
				"<br>" +
				"- Objectif : être le joueur ayant mangé le plus de poissons !<br>" +
				"En cas d'égalité, on départage selon le nombre d'icebergs récupérés.<br>" +
				"Si plusieurs joueurs ont le même nombre de poissons et d'icebergs, il y a égalité parfaite.</html>"));
	}
}
