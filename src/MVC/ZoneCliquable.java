package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ZoneCliquable extends Controle {

    private boolean occupee = false;

    public ZoneCliquable(Etat e, int x, int y) {
        super(e, x, y);
    }


}