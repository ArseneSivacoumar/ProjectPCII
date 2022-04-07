package Unites;

import java.awt.*;

public class Combattante extends Unite{
    protected int vie = 100;
    protected final int ATK = 10;
    private Point position ;
    private Point posFinal = null;

    public Combattante(Point pos){
        super();
        this.position = pos;
    }

    @Override
    public void run() {
        while(true) {
            if(posFinal.x > position.x && posFinal.y > position.y) {
                position = new Point(position.x + 1, position.y +1);
            }
            else if(posFinal.x > position.x && posFinal.y < position.y) {
                position =  new Point(position.x + 1, position.y - 1);
            }
            else if(posFinal.x < position.x && posFinal.y > position.y) {
                position =  new Point(position.x - 1, position.y  + 1);
            }
            else if(posFinal.x < position.x && posFinal.y < position.y) {
                position =  new Point(position.x - 1, position.y - 1);
            }
            else if(posFinal.x < position.x && posFinal.y == position.y) {
                position =  new Point(position.x - 1, position.y);
            }
            else if(posFinal.x > position.x && posFinal.y == position.y) {
                position =  new Point(position.x + 1, position.y);
            }
            else if(posFinal.x == position.x && posFinal.y > position.y) {
                position =  new Point(position.x, position.y + 1);
            }
            else if(posFinal.x == position.x && posFinal.y < position.y) {
                position = new Point(position.x, position.y - 1);
            }
            try {
                Thread.sleep(1500);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Point getPos() {
        return this.position;
    }

    @Override
    public void setPosFinal(Point p) {
        posFinal = p;
    }

    public int getAttack(){
        return this.ATK;
    }

    @Override
    public int getVie(){
        return this.vie;
    }

    @Override
    public void setVie(int v){
        this.vie = v;
    }
}
