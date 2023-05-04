package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurReset implements ActionListener {

	CollecteurEvenements control;

	AdaptateurReset(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.reset();
	}
}
