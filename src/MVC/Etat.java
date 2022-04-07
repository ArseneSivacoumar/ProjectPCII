package MVC;

import Batiments.*;
import Environnement.*;
import Joueurs.AIPlayer;
import Joueurs.Joueur;
import Unites.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Etat {
	private Joueur Joueur;
	private Affichage aff;
	private AIPlayer ordi;
	private ArrayList<Ressource> listRessource = new ArrayList<>();
	public Point posInitial = null;
	public Point posfinal = null;

	public Etat(Affichage a) {
		this.aff = a;
		this.Joueur = new Joueur();
		this.ordi = new AIPlayer(this);
		this.initRessources();
	}

	public Joueur getJoueurs() {
		return this.Joueur;
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
							this.aff.repaint();
						}
						if (c.estOccupeeCombattante()) {
							c.removeCombattante();
							this.aff.repaint();
						}
						if (c.estOccupeeCombattanteAI()) {
							c.removeCombattanteAI();
							this.aff.repaint();
						}
					}
				}
				for (Unite u : Joueur.getUnites()) {
					Case c = this.aff.getPlateau()[u.getPos().x][u.getPos().y];
					if (u instanceof Combattante) {
						c.setCombattante((Combattante) u);
					} else {
						c.setUnit(u);
						if (c.estOccupeeRessource()) { // Je regarde si la case contient une ressource si c'est le cas alors je l'enleve et augmente le score du joueur
							Ressource r = c.removeRessource();
							if (r.gettR() == typeRessource.bois) {
								System.out.println("nombre de bois : " + Joueur.getNbBois());
							} else {
								Joueur.setNbNourritures(1);
								System.out.println("nombre de nourriture : " + Joueur.getNbNourritures());
							}
						}
					}
				}
				for (CombattanteAI u : ordi.getUnit()) {
					Case c = this.aff.getPlateau()[u.getPos().x][u.getPos().y];
					c.setCombattanteAI(u);
				}
				//Verification de fin partie
				if (Joueur.getNbNourritures() >= 110) {
					win();
				} else if (Joueur.getNbNourritures() < 10 && (Joueur.getNbBois() < 20 && Joueur.getUnites().size() == 0)) {
					lose();
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
		this.Joueur.addUnite(c);
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

	public void threadAttaqueJoueur(){
		new Thread(() -> {
			while(true){
				ArrayList<Unite> listUniteJ = this.Joueur.getUnites();
				ArrayList<CombattanteAI> listUniteE = this.ordi.getUnit();
				ArrayList<Point> temp = new ArrayList<Point>();
				for(Unite uJ : listUniteJ){
					for(Unite uAI : listUniteE){
						if(uJ instanceof Combattante){
							Point p1 = uJ.getPos();
							Point p2 = uAI.getPos();
							int xValide = Math.abs(p1.x - p2.x);
							int yValide = Math.abs(p1.y - p2.y);
							if(xValide == 1 || yValide == 1){
								uAI.setVie(uAI.getVie()-((Combattante) uJ).getAttack());
								System.out.println(uAI.getVie());
								if(uAI.getVie() <= 0){
									temp.add(uAI.getPos());
								}
							}
						}
					}
				}
				for(Point p : temp){
					this.getAI().getUnit().remove(this.aff.getPlateau()[p.x][p.y].getCombattanteAI());
					this.aff.getPlateau()[p.x][p.y].removeCombattanteAI();
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void threadAttaqueAI(){
		new Thread(() -> {
			while(true){
				ArrayList<Unite> listUniteJ = this.Joueur.getUnites();
				ArrayList<CombattanteAI> listUniteE = this.ordi.getUnit();
				ArrayList<Point> temp = new ArrayList<Point>();

				for(Unite uAI : listUniteE){
					for(Unite uJ : listUniteJ){
						if(uAI instanceof CombattanteAI){
							Point p1 = uJ.getPos();
							Point p2 = uAI.getPos();
							int xValide = Math.abs(p1.x - p2.x);
							int yValide = Math.abs(p1.y - p2.y);
							if(xValide == 1 || yValide == 1){
								uJ.setVie(uJ.getVie() - ((CombattanteAI) uAI).getAttack());
								//System.out.println(uJ.getVie());
								if(uJ.getVie() <= 0) {
									temp.add(uJ.getPos());
								}
							}
						}
						else if(uAI instanceof Ouvrier){
							Point p1 = uJ.getPos();
							Point p2 = uAI.getPos();
							int xValide = Math.abs(p1.x - p2.x);
							int yValide = Math.abs(p1.y - p2.y);
							if(xValide == 1 || yValide == 1){
								uJ.setVie(uJ.getVie() - ((CombattanteAI) uAI).getAttack());
								System.out.println(uJ.getVie());
								if(uJ.getVie() <= 0) {
									temp.add(uJ.getPos());
								}
							}
						}
					}
				}
				for(Point p : temp){
					Case c = this.aff.getPlateau()[p.x][p.y];
					if(c.estOccupeUnit()){
						this.getJoueurs().getUnites().remove(this.aff.getPlateau()[p.x][p.y].getUnit());
						this.aff.getPlateau()[p.x][p.y].removeUnit();
					}
					else {
						this.getJoueurs().getUnites().remove(this.aff.getPlateau()[p.x][p.y].getCombattante());
						this.aff.getPlateau()[p.x][p.y].removeCombattante();
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

	public void setCombattanteAIPlateau(CombattanteAI c) {
		this.aff.getPlateau()[c.getPos().x][c.getPos().y].setCombattanteAI(c);
	}

	public void setFourmilierePlateau(Fourmiliere f){
		this.Joueur.addBat(f);
		this.aff.getPlateau()[f.getPosition().x][f.getPosition().y].setFourmiliere(f);
	}

	public void setCasernePlateau(Caserne c){
		this.Joueur.addBat(c);
		this.aff.getPlateau()[c.getPosition().x][c.getPosition().y].setCaserne(c);
	}


	public void win() {
		JOptionPane fin = new JOptionPane();
		String s = "Vous avez assez de rations pour l'hiver pour votre fourmiliere, BRAVO !";
		fin.showConfirmDialog(aff, s, "Victoire!", JOptionPane.DEFAULT_OPTION);
	}

	public void lose() {
		JOptionPane fin = new JOptionPane();
		String s = "Vous avez perdu !";
		fin.showConfirmDialog(aff, s, "Defaite !", JOptionPane.DEFAULT_OPTION);
	}

}
