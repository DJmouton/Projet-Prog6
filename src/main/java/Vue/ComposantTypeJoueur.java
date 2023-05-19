package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ComposantTypeJoueur extends JLabel {

	Image robot, pingouin;

	ImageIcon r, p;

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
}
