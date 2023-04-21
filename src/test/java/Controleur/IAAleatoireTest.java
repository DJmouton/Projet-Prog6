package Controleur;

import Modele.Jeu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IAAleatoireTest {

    private Jeu jeu;

    @BeforeEach
    void setUp() {
        this.jeu = new Jeu(20,20);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testTempsEcoule() {
    }
}