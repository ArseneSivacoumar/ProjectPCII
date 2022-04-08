package Joueurs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import MVC.Etat;
import Unites.CombattanteAI;
import Unites.Unite;

public class AIPlayer extends Thread{
	ArrayList<CombattanteAI> list = new ArrayList<CombattanteAI>();
	private Etat etat;

	public AIPlayer(Etat e) {
		etat = e;
	}

	@Override
	public void  run() {
		while(true) {
			ArrayList<Unite> unitesJoueur = etat.getJoueur().getUnites();
			Random rand = new Random();
			int numUnitAChasser;

			for(CombattanteAI u : list) {
				if(u.getEnemy() >= unitesJoueur.size()) {
					u.setID(-1);
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

	public  ArrayList<CombattanteAI> getUnit() {
		return list;
	}

}
