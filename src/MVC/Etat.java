package MVC;

import Environnement.Carte;
import Environnement.Ressource;
import Environnement.typeRessource;
import Joueurs.AIPlayer;
import Joueurs.Joueur;
import Unites.Combattante;
import Unites.Unite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Etat {
	private Carte carte = new Carte();
	private Joueur joueur;
	private Affichage aff;
	private AIPlayer ordi /*= new Joueurs.AIPlayer(this) */;
	private ArrayList<Ressource> listRessource = new ArrayList<>();
	private Timer timer = new Timer();
	private int tempspassee = 0;
	public Point posInitial = null;
	public Point posfinal = null;

	public Etat(Affichage a) {
		this.aff = a;
		//joueurs = new ArrayList<Joueur>();
		this.joueur = new Joueur();
		//joueurs.add(j1);
		//initCarte();
		this.initRessources();
	}

	public Joueur getJoueurs() {
		return this.joueur;
	}

	/*public void initCarte() {
		for(Joueur j : joueurs) {
			carte.getListeUnite().add(j.getUnites());
		}
	}*/

	/*public Carte getCarte() {
		return carte;
	}*/
	
	public boolean verifBorne(Point p) {
	   return p.x <= carte.getLongueur()-1 && p.x > 0 &&
			   p.y <= carte.getLargeur()-1 && p.y > 0;

	}

	/**
	 * Methode pour initialiser les ressources.
	 */
	public void initRessources() {
		Random rand = new Random();
		int nbRessources = rand.nextInt(51) + 20;
		System.out.println("Nb ressources qu'on souhaite initialiser : "+nbRessources);
		while(nbRessources != 0) {
			boolean tempB = true;
			Ressource temp = new Ressource();
			if(!this.listRessource.isEmpty()) {
				for(Ressource res : this.listRessource) {
					//Verification si il n'y a pas de ressource deja presente dans la case où on souhaite ajouter une ressource.
					if(temp.getPosition().x == res.getPosition().x && temp.getPosition().y == res.getPosition().y) {
						tempB = false;
						break;
					}
				}
			}
			if(tempB) {
				this.listRessource.add(temp);
				nbRessources--;
			}
		}
		System.out.println("Nb ressources present dans la liste de ressources : "+this.listRessource.size());
	}

	/**
	 * Thread pour ajouter des ressources toutes les 2,5 secondes.
	 */
	public void threadRessource() {
		new Thread(() -> {
			while(true) {
				boolean tempB = true;
				Ressource r = new Ressource();
				if(this.listRessource.size() < 60) {
					for (Ressource res : this.listRessource) {
						if (r.getPosition().x == res.getPosition().x && r.getPosition().y == res.getPosition().y) {
							tempB = false;
							break;
						}
					}
					if(tempB) {
						this.listRessource.add(r); // ajoute de la nouvelle ressource a la liste de ressources.
						System.out.println(this.listRessource.size());
						System.out.println("Coordonnee de la ressource ajouter : "+r.getPosition());
						this.aff.getPlateau()[r.getPosition().x][r.getPosition().y].setRessource(r); // ajout de la nouvelles ressource au plateau de jeu.
						this.aff.refresh(); // appel pour actualiser l'affichage graphique.
					}
				}
				try {
					Thread.sleep(2500);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void threadUnit() {
		new Thread(() -> {
			while(true) {
				for (Case[] tabCase : this.aff.getPlateau()) {
					for (Case c : tabCase) {
						if (c.estOccupeUnit()) {
							c.removeUnit();
						}
						if (c.estOccupeeCombattante())
							c.removeCombattante();
					}
				}
				for (Unite u : this.joueur.getUnites()) {
					Case c = this.aff.getPlateau()[u.getPos().x][u.getPos().y];
					if (u instanceof Combattante) {
						c.setCombattante((Combattante) u);
					}
					else {
						c.setUnit(u);
						if (c.estOccupeeRessource()) { // Je regarde si la case contient une ressource si c'est le cas alors je l'enleve et augmente le score du joueur
							Ressource r = c.removeRessource();
							if (r.gettR() == typeRessource.bois) {
								joueur.setNbBois(1);
								System.out.println("nombre de bois : " + joueur.getNbBois());
							}
							else {
								joueur.setNbNourritures(1);
								System.out.println("nombre de nourriture : " + joueur.getNbNourritures());
							}
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	public ArrayList<Ressource> getListRessource() {
		return this.listRessource;
	}

	public void setCombattantePlateau(Combattante c){
		this.joueur.addUnite(c);
		this.aff.getPlateau()[c.getPos().x][c.getPos().y].setCombattante(c);
	}

	public AIPlayer getAI() {
		return ordi;
	}

	public void unitADeplacer() {
		Case c = this.getAff().getPlateau()[posInitial.x][posInitial.y];
		Unite u = null;
		if(c.estOccupeUnit()) {
			u = c.getUnit();
		}
		else if(c.estOccupeeCombattante())
			u = c.getCombattante();
		System.out.println("pos :"+u.getPos());
		u.setPosFinal(posfinal);
		if(!u.isAlive()) {
			u.start();
		}
		posInitial = posfinal;
	}

	private Affichage getAff() {
		return this.aff;
	}

	/*public void createCaserne(Joueur joueur, Point pos) {
		int tempsConstruc = 10;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tempspassee++;
			}
		}, 1000, 1000);
		if (tempspassee == tempsConstruc) {
			joueur.addBat(new Caserne(pos, carte, joueur));
			timer.cancel();
			tempspassee = 0;
		}
	}*/
}
