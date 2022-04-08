package Unites;

import java.awt.*;

public class Combattante extends Unite{
    protected int vie = 100;
    protected final int ATK = 10;
    private Point position; // Position actuel de la combattante
    private Point posFinal = null; // Position ou la combattante doit aller lors d'un deplacement.

    public Combattante(Point pos){
        super();
        this.position = pos;
    }

    /**
     * Methode de la classe Thread qui permet le deplacement :
     * -en diagonale si l'abscisse et l'ordonné de la postion d'arrivé sont differents de l'abscisse et l'ordonné de la postion initial.
     * -en ordonné si ordonné de la position d'arrivé est differente de celle de la position initial
     * -en abscisse si abscisse de la position d'arrivé est différente de celle de la position intial.
     */
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

    /**
     * @return la position de la combattante.
     */
    @Override
    public Point getPos() {
        return this.position;
    }

    /**
     * Set la position final(position de destination pour effectuer le mouvement) de la combattante.
     * @param p
     */
    @Override
    public void setPosFinal(Point p) {
        posFinal = p;
    }

    /**
     * @return l'attaque de la combattante.
     */
    public int getAttack(){
        return this.ATK;
    }

    /**
     * @return la vie de la combattante.
     */
    @Override
    public int getVie(){
        return this.vie;
    }

    /**
     * Set la vie de la combattante.
     * @param v
     */
    @Override
    public void setVie(int v){
        this.vie = v;
    }
}
