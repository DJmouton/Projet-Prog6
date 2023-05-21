package Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantBarreBasse extends Box {

	CollecteurEvenements control;

	ComposantBarreBasse(CollecteurEvenements c) {
		super(BoxLayout.X_AXIS);
		control = c;

		ComposantTour tour = new ComposantTour(control);
		add(tour);
	}
}