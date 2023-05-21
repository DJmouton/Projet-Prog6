package Controleur;

import Modele.Jeu;
import Modele.Historique;
import Modele.IA;

public class IACoup extends Thread
{
	ControleurMediateur control;
	Jeu jeu;
	IA ia;

	public IACoup(ControleurMediateur control, Jeu jeu, IA ia)
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
		control.coup = ia.jeu();
		if (jeu.historique.egal(hist_cpy)) {
			jeu.faire(control.coup);
			System.out.println("Coup éxecuté");
			jeu.metAJour();
			control.tour();
		}
	}
}
