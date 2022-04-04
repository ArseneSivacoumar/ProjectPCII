package Unites;

import java.awt.Point;

import javax.swing.JComponent;

import MVC.PanneauDeControle;

public abstract class Unite extends Thread{
	protected Point position;
	private PanneauDeControle panneau;
	
	public abstract Point getPos();

	public abstract void run();

	public abstract void setPosFinal(Point p);
}