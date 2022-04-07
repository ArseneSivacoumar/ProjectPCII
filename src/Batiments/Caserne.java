package Batiments;

import Joueurs.*;
import Unites.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Caserne extends Batiment {
    private Point position;

    public Caserne(Point pos) {
        position = pos;
    }

    public Point getPosition() {
        return position;
    }
}

