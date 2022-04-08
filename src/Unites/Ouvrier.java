package Unites;

import java.awt.Point;

public class Ouvrier extends Unite {
	private Point position;
	private Point posFinal = null;

	private int vie = 100;

	public Ouvrier(Point pos) {
		position = pos;
	}

	@Override
	public Point getPos() {
		return position;
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
				position =  new Point(position.x, position.y - 1);
			}
			try {
				Thread.sleep(1500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setPosFinal(Point p) {
		posFinal = p;
	}

	/**
	 *
	 * @param v
	 */
	@Override
	public void setVie(int v) {
		this.vie = v;
	}

	/**
	 * @return la vie de l'ouvrier
	 */
	@Override
	public int getVie() {
		return this.vie;
	}
}