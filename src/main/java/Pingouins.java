import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

public class Pingouins
{
	public static void main(String[] args) {
		Jeu j = new Jeu();
		CollecteurEvenements control = new ControleurMediateur(j);
		Thread t = new Thread((Runnable) control);
		InterfaceGraphique.demarrer(j, control);
		t.start();
	}
}
