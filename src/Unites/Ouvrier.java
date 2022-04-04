package Unites;

import java.awt.Point;

public class Ouvrier extends Unite {
	private Point position;
	private Point posFinal = null;

	private int vie = 100;

	public Ouvrier() {
	}

	public Ouvrier(Point pos) {
		position = pos;
	}

	@Override
	public Point getPos() {
		return position;
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
				position =  new Point(position.x, position.y - 1);
			}
			try {
				Thread.sleep(2000);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setPosFinal(Point p) {
		posFinal = p;
	}
}