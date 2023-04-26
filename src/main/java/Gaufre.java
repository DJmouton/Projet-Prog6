import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

public class Gaufre
{
	public static void main(String[] args) {
		Jeu j = new Jeu(10, 10);
		CollecteurEvenements control = new ControleurMediateur(j);
		InterfaceGraphique.demarrer(j, control);
	}
}
