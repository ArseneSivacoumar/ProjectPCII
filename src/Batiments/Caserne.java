package Batiments;

import java.awt.*;


public class Caserne extends Batiment {
    private Point position;

    public Caserne(Point pos) {
        position = pos;
    }

    /**
     * @return la position de la caserne
     */
    public Point getPosition() {
        return position;
    }
}

