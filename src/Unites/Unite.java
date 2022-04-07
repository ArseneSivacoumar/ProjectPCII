package Unites;

import java.awt.Point;

public abstract class Unite extends Thread{
	protected Point position;
	
	public abstract Point getPos();

	public abstract void run();

	public abstract void setPosFinal(Point p);

	public abstract void setVie(int v);

	public abstract int getVie();
}