package Batiments;
import java.awt.Point;

public class Fourmiliere extends Batiment {
    private Point position;

    public Fourmiliere(Point pos) {
        position = pos;
    }

    /**
     * @return la position de la fourmiliére
     */
    public Point getPosition() {
        return position;
    }

}
