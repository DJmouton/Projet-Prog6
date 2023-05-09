package Vue;

import javax.swing.*;

public class ComposantReset extends Box {

	ComposantReset(int axis, CollecteurEvenements control) {
		super(axis);
		JButton reset = new JButton("Recommencer une partie");
		reset.addActionListener(new AdaptateurReset(control));
		add(reset);
	}
}
