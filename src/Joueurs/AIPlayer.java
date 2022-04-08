package Joueurs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import MVC.Etat;
import Unites.CombattanteAI;
import Unites.Unite;

/**
 *@author Thomas, Arsene, Charlies
 * Cette classe correspond a un Thread qui gere l'environnement de notre jeu. Le Thread s'occupe de creer de nouvelles CombattanteAI selon le nombre d'unites
 * du joueur et permet aussi de donner un ennemi a aller attaquer.
 */

public class AIPlayer extends Thread{
	ArrayList<CombattanteAI> list = new ArrayList<CombattanteAI>(); // Liste des unites de l'environnement qui va attaquer les unites du joueur
	private Etat etat;

	public AIPlayer(Etat e) {
		etat = e;
	}

	/**
	 * Tout les secondes la méthode run regarde si toutes les unites de l'environnement ont bien un ennemi a aller attaquer et permet de changer de cible quand
	 * un unite de l'environnement a tuer sa cible. Aussi il genere une combattanteAI tel que le nombre de combattanteAI soit equivalent a la moitie des unites
	 * du joueur.
	 */
	@Override
	public void  run() {
		while(true) {
			ArrayList<Unite> unitesJoueur = etat.getJoueur().getUnites();
			Random rand = new Random();
			int numUnitAChasser;

			for(CombattanteAI u : list) {
				if(u.getEnemy() >= unitesJoueur.size()) { // Si l'id de l'unite a attaquer (position de l'unite ennemi dans la liste des unites du joueur) est superieur ou egale a la
					u.setID(-1);                          // taille de la liste des unites du joueur alors il faut donner une nouvelle unite a attaquer a la combattanteAI
				}
				if(u.getEnemy() == -1) {
					if(unitesJoueur.size() > 0) {
						numUnitAChasser = rand.nextInt(unitesJoueur.size());
						u.setEnemy(numUnitAChasser, etat.getJoueur().getUnites().get(numUnitAChasser).getPos());
					}
				}
				else {
					if(u.getPosFinal().x == u.getPos().x && u.getPosFinal().y == u.getPos().y) {
						u.setPosFinal(etat.getJoueur().getUnites().get(u.getEnemy()).getPos());
					}
				}

			}

			// Je rajoute des combattanteAI si le nombre d'unite du joueur est trop grande
			if(unitesJoueur.size()/2 > list.size()) {
				CombattanteAI c = new CombattanteAI(new Point(0, 14));
				list.add(c);
				c.start();

			}


			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return la liste des unites (combattanteAI) de l'environnement
	 */
	public  ArrayList<CombattanteAI> getUnit() {
		return list;
	}
}
