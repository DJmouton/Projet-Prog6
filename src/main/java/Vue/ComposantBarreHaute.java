package Vue;

import javax.swing.*;

public class ComposantBarreHaute extends Box {
    ComposantBarreHaute(CollecteurEvenements control){
        super(BoxLayout.X_AXIS);
        JButton nouvelle_partie = new JButton("Nouvelle partie");
        nouvelle_partie.addActionListener(new AdaptateurNouvellePartie(control));
        add(nouvelle_partie);
        add(Box.createGlue());
        add(new ComposantHistorique(control));
        add(Box.createGlue());
        JButton menu = new JButton("Menu");
        menu.addActionListener(new AdaptateurMenu(control));
        add(menu);
    }
}
