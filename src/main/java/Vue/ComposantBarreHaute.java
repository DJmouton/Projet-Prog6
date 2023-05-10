package Vue;

import javax.swing.*;

public class ComposantBarreHaute extends Box {
    ComposantBarreHaute(CollecteurEvenements control){
        super(BoxLayout.X_AXIS);
        JButton nouvelle_partie = new JButton("Nouvelle partie");
        add(nouvelle_partie);
        add(Box.createGlue());
        add(new ComposantHistorique(control));
        add(Box.createGlue());
        JButton menu = new JButton("menu");
        add(menu);
    }
}
