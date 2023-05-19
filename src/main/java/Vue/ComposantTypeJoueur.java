package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantTypeJoueur extends JLabel {

	Image robot, pingouin, joue;

	ImageIcon r, p, j;

	ComposantTypeJoueur(boolean bot) {
		setBotHumain(bot);
	}

	public void setBotHumain(boolean bot) {
		try {
			robot = ImageIO.read(new File("resources/assets/robot.png"));
			pingouin = ImageIO.read(new File("resources/assets/pingouin.png"));

			r = new ImageIcon(robot);
			p = new ImageIcon(pingouin);

			if (bot)
				setIcon(r);
			else
				setIcon(p);
		} catch (IOException e) {
			System.out.println("pas d'images");
		}
	}

	public void setCourant(boolean courant) {

		if(courant)
			try {
				joue = ImageIO.read(new File("resoureces/assets/pE.png"));

				j = new ImageIcon(joue);

				setIcon(j);

			}
			catch (IOException e) {
				System.out.println("Pas d'image");
			}
	}
}
