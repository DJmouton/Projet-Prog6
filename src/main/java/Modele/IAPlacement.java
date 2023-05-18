package Modele;

public class IAPlacement implements Runnable
{
	IA ia;
	Placement res;

	public IAPlacement(IA ia)
	{
		this.ia = ia;
	}

	@Override
	public void run()
	{
		res = ia.placement();
		ia.jeu.faire(res);
		System.out.println("Placement éxecuté");
	}
}
