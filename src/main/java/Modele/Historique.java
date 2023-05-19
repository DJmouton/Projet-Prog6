package Modele;

import Patterns.Commande;
import java.util.Stack;

public class Historique
{
	public Stack<Commande> passe, futur;

	Historique()
	{
		passe = new Stack<>();
		futur = new Stack<>();
	}

	public void faire(Commande cmd)
	{
		cmd.execute();
		passe.push(cmd);
		futur.clear();
	}

	public void annuler()
	{
		Commande cmd;
		cmd = passe.pop();
		futur.push(cmd);
		for(int i=0;i<passe.size();i++)
			passe.get(i).execute();
	}

	public void refaire()
	{
		Commande cmd;
		cmd = futur.pop();
		cmd.execute();
		passe.push(cmd);
	}



	public boolean peutAnnuler()
	{
		return !passe.isEmpty();
	}

	public boolean peutRefaire()
	{
		return !futur.isEmpty();
	}

	public Historique copier() {
		Historique cpy = new Historique();

		cpy.passe.addAll(passe);
		cpy.passe.addAll(futur);

		return cpy;
	}

	public boolean egal(Historique autre) {
		if (passe.size() != autre.passe.size() || futur.size() != autre.futur.size())
			return false;

		for (int i = 0; i < passe.size(); i++) {
			if (!passe.get(i).equals(autre.passe.get(i)))
				return false;
		}

		for (int i = 0; i < futur.size(); i++) {
			if (!futur.get(i).equals(autre.futur.get(i)))
				return false;
		}

		return true;
	}
}
