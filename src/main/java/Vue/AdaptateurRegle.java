package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRegle implements ActionListener {

    AdaptateurRegle() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComposantPanneauRegles regles = new ComposantPanneauRegles();
        Object[] inputs = {regles};
        Object[] options = {"J'ai compris!"};
        JOptionPane.showOptionDialog(
                null,
                inputs,
                "Règles",
                0,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                0
        );
    }
}
