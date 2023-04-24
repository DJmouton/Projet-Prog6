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
}