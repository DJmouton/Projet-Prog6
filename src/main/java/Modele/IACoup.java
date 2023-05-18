package Modele;

public class IACoup implements Runnable
{
	IA ia;
	Coup res;

	public IACoup(IA ia)
	{
		this.ia = ia;
	}

	@Override
	public void run()
	{
		res = ia.jeu();
		ia.jeu.faire(res);
		System.out.println("Coup éxecuté");
	}
}
