package Unites;
import java.awt.Point;


public class CombattanteAI extends Unite {
        private Point position;
        private Point posFinal = null;
        private int vie = 100;
        protected final int ATK = 10;

        private int idEnemy = -1;
        private Point posEnemy = null;

        public CombattanteAI(Point pos) {
            position = pos;
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
                if(posFinal != null) {
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
                        position =  new Point(position.x, position.y - 1);
                    }
                }

                try {
                    Thread.sleep(1750);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
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
        *
        * @return la position de la combattanteAI.
        */
        @Override
        public Point getPos() {
        return position;
    }


    /**
        * Set la vie de la combattante.
        * @param v
        */
        @Override
        public void setVie(int v) {
            this.vie = v;
        }

        /**
        * @return la vie de la combattante.
        */
        @Override
        public int getVie() {
            return this.vie;
        }

        /**
        * @return l'ID de l'ennemi.
        */
        public int getEnemy() {
            return idEnemy;
        }

        /**
        * @return la position final(arriver)
        */
    public Point getPosFinal() {
            return posFinal;
        }

        /**
         * @return l'attaque de la combattante.
        */
        public int getAttack(){
        return this.ATK;
    }

        /**
         * Set un id d'un ennemi et la position de l'ennemi(comme position final).
        * @param i
        * @param p
        */
        public void setEnemy(int i, Point p) {
            idEnemy = i;
            posEnemy = p;

            setPosFinal(posEnemy);
        }

        /**
        * Set l'ID de ennemi.
        * @param i
        */
        public void setID(int i) {
            idEnemy = i;
        }
}
