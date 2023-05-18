package Controleur;

import Modele.Jeu;
import Modele.Coup;
import Modele.IA;

public class IACoup extends Thread
{
	ControleurMediateur control;
	Jeu jeu;
	IA ia;
	Coup res;

	public IACoup(ControleurMediateur control, Jeu jeu, IA ia)
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
		control.coup = ia.jeu();
		jeu.faire(control.coup);
		System.out.println("Coup éxecuté");
		jeu.metAJour();
		control.tour();
	}
}
