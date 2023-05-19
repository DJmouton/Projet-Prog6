package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantTypeJoueur extends JLabel {

	Image robot, pingouin;

	ImageIcon r, p;

	ComposantTypeJoueur(boolean bot, int joueur) {
		setBotHumain(bot, joueur);
	}

	public void setBotHumain(boolean bot, int joueur) {
		try {
			robot = ImageIO.read(new File("resources/assets/robot.png"));

			if(joueur == 0)
				pingouin = ImageIO.read(new File("resources/assets/pingouinB.png"));
			else if(joueur == 1)
				pingouin = ImageIO.read(new File("resources/assets/pingouinV.png"));
			else if(joueur == 2)
				pingouin = ImageIO.read(new File("resources/assets/pingouinR.png"));
			else if(joueur == 3)
				pingouin = ImageIO.read(new File("resources/assets/pingouinJ.png"));

			r = new ImageIcon(robot);
			p = new ImageIcon(pingouin);

			if (bot)
				setIcon(r);
			else
				setIcon(p);

		} catch (IOException e) { System.out.println("pas d'images"); }
	}
}
