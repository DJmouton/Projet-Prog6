package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurAnnuler implements ActionListener {
    CollecteurEvenements control;
    JButton button;

    AdaptateurAnnuler(CollecteurEvenements c, JButton b) {
        control = c;
        button = b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        control.annuler();
    }
}