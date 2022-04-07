package MVC;

import Environnement.*;
import Unites.*;
import Batiments.*;

import java.awt.*;
import java.util.ArrayList;

public class Affichage extends Grille {
	private final int hauteur = 500;
	private final int largeur = 800;
	// Attributs : taille et tableau de cases
	private Case[][] plateau;
	private Etat etat = new Etat(this);

	private ArrayList<Unite> aiList = new ArrayList<>();

	public Affichage(int taille) {
		super(taille, taille);
		this.plateau = new Case[taille][taille];

		for (int x = 0; x < plateau.length; x++) {
			for (int y = 0; y < plateau[x].length; y++) {
				Point p = new Point(x, y);
				this.plateau[x][y] = new Case(etat, p);
				ajouteElement(this.plateau[x][y]);
			}
		}
		this.setAllRessources();
		this.setBackground(Color.orange);
		this.etat.threadUnit();
		this.etat.threadRessource();
		//this.etat.setCombattantePlateau(new Combattante(new Point(13, 1)));
		//this.etat.setCombattantePlateau(new Combattante(new Point(12, 2)));
		this.etat.setFourmilierePlateau(new Fourmiliere(new Point(14, 1)));
		this.etat.setCasernePlateau(new Caserne(new Point(13, 0)));

		this.etat.getAI().start();
		//this.etat.threadAttaqueJoueur();
		this.etat.threadAttaqueAI();
	}

	/**
	 * Methode pour initialise toute les ressources dans chaque case en fonction des coordonnees de chaque ressources.
	 */

	public void setAllRessources() {
		for (Ressource r : this.etat.getListRessource()) {
			//System.out.println("x = " + r.getPosition().x + " " + "y = " + r.getPosition().y);
			this.plateau[r.getPosition().x][r.getPosition().y].setRessource(r);
		}
	}


	/**
	 * Methode pour actualiser l'affichage graphique.
	 */
	public void refresh() {
		for (Case[] tabCase : this.plateau) {
			for (Case c : tabCase) {
				if (c.estOccupeeRessource())
					c.repaint();
				if(c.estOccupeUnit())
					c.repaint();
				if(c.estOccupeeCombattanteAI())
					c.repaint();
			}
		}
	}

	public Case[][] getPlateau() {
		return plateau;
	}
}