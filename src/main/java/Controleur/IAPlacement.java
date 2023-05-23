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
		long tstart, tend, twait;
		Historique hist_cpy = jeu.historique.copier();
		tstart = System.currentTimeMillis();
		res = ia.placement();
		tend = System.currentTimeMillis();
		twait = 2000 - (tend-tstart);
		if (twait > 0) {
			try {
				Thread.sleep(twait);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		if (jeu.historique.egal(hist_cpy)) {
			jeu.faire(res);
			System.out.println("Placement éxecuté");
			jeu.metAJour();
			control.tour();
		}
	}
}
