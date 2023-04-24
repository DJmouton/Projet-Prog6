package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRefaire implements ActionListener {
    CollecteurEvenements control;
    JButton button;

    AdaptateurRefaire(CollecteurEvenements c, JButton b) {
        control = c;
        button = b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        control.refaire();
    }
}