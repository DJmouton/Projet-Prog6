package Vue;

import javax.swing.*;

public class ComposantPanneauRegles extends JPanel {

    ComposantPanneauRegles(){
        add(new JLabel("<html>Règles :<br>" +
                "Ce joue de 2 à 4 joueurs<br>" +
                "- Chaque joueurs possèdent : <br>" +
                "4 pinougins chacun pour 2 joueurs<br>" +
                "3 pingouins chacun pour 3 joueurs<br>" +
                "2 pingouins chacun pour 4 joueurs<br>" +
                "Le plateau est composé : <br>" +
                "60 iceberg, chacun contenant un certain nombre de poisson :<br>" +
                "- 30 icebergs de 1 poisson<br>" +
                "- 20 icebergs de 2 poissons<br>" +
                "- 10 icebergs de 3 poissons<br>" +
                "<br>" +
                "- Objectif de la partie :<br>" +
                "Pour gagner il faut être le joueur avec le plus de poissons à la fin de la partie.<br>" +
                "Si des joueurs ont le même nombre de poissons alors le joueur avec le plus d'icebergs retiré du jeu gagne.<br>" +
                "En cas d'égalité de poisson et d'iceberg il y a alors égalité entre ces joueurs.<br>" +
                "<br>" +
                "- Fin de partie : <br>" +
                "Lorsqu'un pingouin n'a plus aucun mouvement disponible il est retiré du jeu.<br>" +
                "La partie se termine lorsque tous les pingouins ont été retiré du jeu.<br>" +
                "Si un joueur n'a plus de pingouin il passe son tour.<br>" +
                "<br>" +
                "- Début de partie :<br>" +
                "A tour de rôle chaque joueur pose un pingouin sur un iceberg<br>" +
                "jusqu'à ce que tous leurs pingouins soient posé.<br>" +
                "Un pingouin ne peut être posé uniquement sur un iceberg contenant 1 poisson.<br>" +
                "<br>" +
                "- Déroulement d'un tour :<br>" +
                "A chaque tour un joueur peut choisir un pingouin et peut le déplacer sur une case en ligne depuis sa position,<br>" +
                "or, un pingouin ne peut pas traverser un vide ou un iceberg où un autre pingouin est déjà posé.<br>" +
                "L'iceberg que vient de quitter le pingouin est alors retirée du jeu et<br>" +
                "récupérer par le joueur qui vient de bouger son pingouin.<br>" +
                "Lorsqu'un pingouin arrive sur un iceberg il mange les poissons qui s'y trouve," +
                "<br>ces poissons s'ajoutent au score total du joueur à qui appartient ce pingouin.</html>"));
    }
}
