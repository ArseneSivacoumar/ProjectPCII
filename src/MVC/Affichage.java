package MVC;

import Environnement.*;
import Batiments.*;

import java.awt.*;

public class Affichage extends Grille {
	private Case[][] plateau;
	private Etat etat = new Etat(this);

	public Affichage(int taille) {
		super(taille, taille);
		this.plateau = new Case[taille][taille];

		// Initialisation du plateau de jeu avec des cases cliquable (JPanel).
		for (int x = 0; x < plateau.length; x++) {
			for (int y = 0; y < plateau[x].length; y++) {
				Point p = new Point(x, y);
				this.plateau[x][y] = new Case(etat, p);
				ajouteElement(this.plateau[x][y]);
			}
		}
		this.setAllRessources();
		this.setBackground(Color.orange);
		this.etat.threadUnit(); // Lancement du thread contenu dans la methode threadUnit pour les deplacement des unites du joueurs et verification condition de win ou lose.
		this.etat.threadRessource(); // Lancement du thread contenu dans la methode threadRessource pour le spawn continuel de ressource sur le plateau.

		this.etat.setFourmilierePlateau(new Fourmiliere(new Point(14, 1))); // Initialisation d'une Fourmilliére sur le plateau.
		this.etat.setCasernePlateau(new Caserne(new Point(13, 0))); // Initialisation d'une Caserne sur le plateau.

		this.etat.getAI().start(); // Lancement du thread de l'IA.
		this.etat.threadAttaqueJoueur(); // Lancement du thread contenu dans la methode threadAttaqueJoueur pour gerer les attaques des combattantes du joueur.
		this.etat.threadAttaqueAI(); // Lancement du thread contenu dans la methode threadAttqueAI pour gérer les attaques des unites de l'IA.
	}

	/**
	 * Methode pour initialise toute les ressources dans chaque case en fonction des coordonnees de chaque ressources.
	 */
	public void setAllRessources() {
		for (Ressource r : this.etat.getListRessource()) {
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
				if(c.estOccupeeCombattante())
					c.repaint();
			}
		}
	}

	/**
	 * @return le plateur de jeu.
	 */
	public Case[][] getPlateau() {
		return plateau;
	}
}