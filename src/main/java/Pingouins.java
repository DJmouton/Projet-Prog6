import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

public class Pingouins
{
	public static void main(String[] args) {
		Jeu j = new Jeu();
		CollecteurEvenements control = new ControleurMediateur(j);
		InterfaceGraphique.demarrer(j, control);
	}
}
