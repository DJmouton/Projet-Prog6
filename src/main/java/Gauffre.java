import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

public class Gauffre
{
	public static void main(String[] args) {
		Jeu j = new Jeu(3);
		CollecteurEvenements control = new ControleurMediateur(j);
		InterfaceGraphique.demarrer(j, control);
	}
}
