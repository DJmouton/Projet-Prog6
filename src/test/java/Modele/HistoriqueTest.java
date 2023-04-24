package Modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoriqueTest
{
	Jeu jeu;

	@Test
	public void historiqueTest()
	{
		jeu = new Jeu(12, 12);
		Historique hist = new Historique();
		Coup coup;
		int i;

		// jouer 10 coups

		for (i = 10; i > 0; i--)
		{
			coup = new Coup(jeu, i, i);
			hist.faire(coup);
		}

		assertTrue(hist.peutAnnuler());
		assertEquals(10, hist.passe.size());
		assertFalse(hist.peutRefaire());

		// annuler les 10 coups

		hist.annuler();

		assertTrue(hist.peutAnnuler());
		assertEquals(9, hist.passe.size());
		assertTrue(hist.peutRefaire());
		assertEquals(1, hist.futur.size());

		for (i = 0; i < 9; i++)
			hist.annuler();

		assertFalse(hist.peutAnnuler());
		assertTrue(hist.peutRefaire());
		assertEquals(10, hist.futur.size());

		// refaire les 10 coups

		hist.refaire();

		assertTrue(hist.peutAnnuler());
		assertEquals(1, hist.passe.size());
		assertTrue(hist.peutRefaire());
		assertEquals(9, hist.futur.size());

		for (i = 0; i < 9; i++)
			hist.refaire();

		assertTrue(hist.peutAnnuler());
		assertEquals(10, hist.passe.size());
		assertFalse(hist.peutRefaire());
	}

	public void afficherPlateauCourant()
	{
		for (int i = 0; i < jeu.hauteur(); i++)
		{
			for (int j = 0; j < jeu.largeur(); j++)
			{
				System.out.print(jeu.plateau[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void afficherListePlateaux()
	{
		for (int i = 0; i<jeu.liste_plateaux.size(); i++)
		{
			for (int j = 0; j < jeu.liste_plateaux.get(i).length; j++)
			{
				for (int k = 0; k< jeu.liste_plateaux.get(i)[0].length; k++)
				{
					System.out.print(jeu.liste_plateaux.get(i)[j][k]);
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	@Test
	public void bugHistorique()
	{
		jeu = new Jeu(6, 6);
		Historique hist = new Historique();
		Coup coup;

		//jeu.liste_plateaux.addLast(jeu.plateau);
		//afficherListePlateaux();

		coup = new Coup(jeu, 4, 2);
		hist.faire(coup);
		System.out.println("coup joué");

		afficherPlateauCourant();
		//afficherListePlateaux();

		assertTrue(hist.peutAnnuler());
		assertFalse(hist.peutRefaire());

		hist.annuler();
		System.out.println("coup annulé");

		for (int i=0; i<jeu.hauteur(); i++){
			for(int j=0; j<jeu.largeur(); j++){
				System.out.print(jeu.plateau[i][j]);
			}
			System.out.println();
		}
		System.out.println();

		assertFalse(hist.peutAnnuler());
		assertTrue(hist.peutRefaire());

		hist.refaire();
		System.out.println("coup refait");

		for (int i=0; i<jeu.hauteur(); i++){
			for(int j=0; j<jeu.largeur(); j++){
				System.out.print(jeu.plateau[i][j]);
			}
			System.out.println();
		}
		System.out.println();

		assertTrue(hist.peutAnnuler());
		assertFalse(hist.peutRefaire());

		hist.annuler();
		System.out.println("coup annulé");

		for (int i=0; i<jeu.hauteur(); i++){
			for(int j=0; j<jeu.largeur(); j++){
				System.out.print(jeu.plateau[i][j]);
			}
			System.out.println();
		}
		System.out.println();

		assertFalse(hist.peutAnnuler());
		assertTrue(hist.peutRefaire());
	}
	// jouer un coup
	// annuler
	// refaire
	// annuler
}