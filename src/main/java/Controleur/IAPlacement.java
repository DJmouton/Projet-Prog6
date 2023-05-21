package Controleur;

import Modele.Jeu;
import Modele.IA;
import Modele.Historique;
import Modele.Placement;

public class IAPlacement extends Thread
{
	ControleurMediateur control;
	Jeu jeu;
	IA ia;
	Placement res;

	public IAPlacement(ControleurMediateur control, Jeu jeu, IA ia)
	{
		this.control = control;
		this.jeu = jeu;
		this.ia = ia;
	}

	@Override
	public void run()
	{
		Historique hist_cpy = jeu.historique.copier();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		res = ia.placement();
		if (jeu.historique.egal(hist_cpy)) {
			jeu.faire(res);
			System.out.println("Placement éxecuté");
			jeu.metAJour();
			control.tour();
		}
	}
}
