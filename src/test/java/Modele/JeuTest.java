package Modele;

import org.junit.Before;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
    Jeu jeu;

    @org.junit.jupiter.api.Test
    void accessibleCoinSupGauche()
    {
        jeu = new Jeu();
        ArrayList<int[]> res = jeu.hex_accessible(0, 1);

        assertFalse(res.isEmpty());
        assertEquals(0, res.get(0)[0]);
        assertTrue(equalstab(new int[]{0, 1}, res));
    }


    boolean equalstab(int[] valeur, ArrayList<int[]> list){
        boolean res = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length == valeur.length) {
                res = true;
                for (int j = 0; j < valeur.length; j++) {
                    if (list.get(i)[j] != valeur[j]) {
                            res = false;
                            break;
                    }
                }

                if (res) return res;
            }
        }

        return res;
    }
}