package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRecommencer implements ActionListener {

	CollecteurEvenements control;

	AdaptateurRecommencer(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.recommencer();
	}
}
