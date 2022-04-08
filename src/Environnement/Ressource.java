package Environnement;

import java.awt.*;
import java.util.Random;

public class Ressource {
    private typeRessource tR;
    private final Point position = new Point();

    public Ressource() {
        initialiseRessources();
    }

    /**
     * Methode qui permet d'initialiser une ressource.
     */
    public void initialiseRessources() {
        int xtmp;
        int ytmp;
        Random rand = new Random();
        do {
            xtmp = rand.nextInt(15);
            ytmp = rand.nextInt(15);
        } while ((xtmp > 11 ) && (ytmp < 3) || (xtmp == 0 && ytmp == 14)); // les 9 cases en bas a gauche de la grille et la case (0,14) ne sont pas utiliser pour les ressources car elles sont utilisée pour les batiments et unités.
        this.position.setLocation(xtmp, ytmp);
        if (rand.nextInt(2) == 0)
            tR = typeRessource.bois;
        else
            tR = typeRessource.nourriture;
    }

    /**
     * @return la position de la ressource
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * @return le type de la ressource.
     */
    public typeRessource gettR() {
        return tR;
    }
}



