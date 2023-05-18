package Controleur;

import Modele.Jeu;
import Modele.IA;
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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		res = ia.placement();
		jeu.faire(res);
		System.out.println("Placement éxecuté");
		jeu.metAJour();
		control.tour();
	}
}
