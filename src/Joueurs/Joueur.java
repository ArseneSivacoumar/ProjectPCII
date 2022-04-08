package Joueurs;

import Batiments.Batiment;
import Unites.*;

import java.util.ArrayList;


public class Joueur {
	private ArrayList<Unite> listUnit = new ArrayList<>(); // liste des unites du joueur
	private ArrayList<Batiment> listBat = new ArrayList<>(); // liste des bâtiments du joueur
	private int nbBois; // entier pour le nombre de bois
	private int nbNourritures; // entier pour le nombre de nourritures

	public Joueur() {
		nbBois = 100;
		nbNourritures = 100;
	}

	/**
	 * @return la liste des unites.
	 */
	public ArrayList<Unite> getUnites() {
		return listUnit;
	}

	/**
	 * Methode qui ajoute un batiment a la liste des bâtiments du joueur.
	 * @param bat
	 */
	public void addBat(Batiment bat) {
		listBat.add(bat);
	}

	/**
	 * Methode qui ajoute une unite a la liste des unites du joueur.
	 * @param unit
	 */
	public void addUnite(Unite unit) {
		listUnit.add(unit);
	}

	/**
	 * @return le nombre de bois du joueur.
	 */
	public int getNbBois() {
		return nbBois;
	}

	/**
	 * @return le nombre de nourritures du joueur.
	 */
	public int getNbNourritures() {
		return nbNourritures;
	}

	/**
	 * Set un nombre de bois pour le joueur.
	 * @param bois
	 */
	public void setNbBois(int bois) {
		nbBois += bois;
	}

	/**
	 * Set un nombre de nourriture pour le joueur.
	 * @param nourriture
	 */
	public void setNbNourritures(int nourriture) {
		nbNourritures += nourriture;
	}
}