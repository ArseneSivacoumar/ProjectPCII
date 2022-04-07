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

        @Override
        public Point getPos() {
            return position;
        }


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

        @Override
        public void setPosFinal(Point p) {
            posFinal = p;
        }

        @Override
        public void setVie(int v) {
            this.vie = v;
        }

        @Override
        public int getVie() {
            return this.vie;
        }

        public int getEnemy() {
            return idEnemy;
        }

        public Point getPosFinal() {
            return posFinal;
        }

        public int getAttack(){
        return this.ATK;
    }

        public void setEnemy(int i, Point p) {
            idEnemy = i;
            posEnemy = p;

            setPosFinal(posEnemy);
        }

    public void setID(int i) {
        idEnemy = i;
    }
}
