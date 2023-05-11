package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurMenu implements ActionListener {

    CollecteurEvenements control;
    JFrame parent;

    AdaptateurMenu(CollecteurEvenements c, JFrame p) {
        control = c;
        parent = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComposantPanneauMenu menu = new ComposantPanneauMenu();
        Object[] inputs = {menu};
        JOptionPane.showMessageDialog(
                parent,
                inputs,
                "Non implémenté",
                JOptionPane.ERROR_MESSAGE,
                null
        );
    }
}
