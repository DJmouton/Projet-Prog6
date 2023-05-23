package Controleur;

import Modele.Coup;
import Modele.Historique;
import Modele.IA;
import Modele.Jeu;

public class IACoup extends Thread {
	ControleurMediateur control;
	Jeu jeu;
	IA ia;

	public IACoup(ControleurMediateur control, Jeu jeu, IA ia) {
		this.control = control;
		this.jeu = jeu;
		this.ia = ia;
	}

	@Override
	public void run() {
		long tstart, tend, twait;
		Historique hist_cpy = jeu.historique.copier();
		tstart = System.currentTimeMillis();
		Coup coup = ia.jeu();
		tend = System.currentTimeMillis();
		twait = 2000 - (tend - tstart);
		if (twait > 0) {
			try {
				Thread.sleep(twait);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		if (jeu.historique.egal(hist_cpy)) {
			control.coup = coup;
			jeu.faire(control.coup);
			System.out.println("Coup éxecuté");
			jeu.metAJour();
			control.tour();
		}
	}
}
