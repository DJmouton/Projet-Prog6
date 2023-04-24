package Modele;

import Patterns.Commande;
import java.util.Stack;

public class Historique
{
	Stack<Commande> passe, futur;

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
		cmd.desexecute();
		futur.push(cmd);
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
}
