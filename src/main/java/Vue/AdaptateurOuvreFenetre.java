package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurOuvreFenetre implements ActionListener {

	JToggleButton toggle;
	Box box;

	AdaptateurOuvreFenetre(JToggleButton t, Box b) {
		toggle = t;
		box = b;
		box.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		box.setVisible(toggle.isSelected());
	}
}
