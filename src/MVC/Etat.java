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


/**
 *
 * @author Thomas, Arsene, Charlies
 *
 * La classe Etat est la classe de modele du MVC. Il contient les informations qui caracterisent
 * l'etat de l'affichage et un changement de ces informations entraine un changement au niveau
 * de l'interface graphique.
 */
public class Etat {
	private Joueur joueur;
	private Affichage aff;
	private AIPlayer ordi;
	private ArrayList<Ressource> listRessource = new ArrayList<>();

	public Point posInitial = null; // Cet attribut permet de faire passer la position intiale d'une unite pour effectuer une action
	public Point posfinal = null; // Cet attribut permet de definir dans une methode la destination finale d'une unite apres un clic gauche

	public Etat(Affichage a) {
		aff = a;  // On recupere l'affichage

		Joueur j1 = new Joueur();
		joueur = j1; // On initialise et declare le joueur


		ordi = new AIPlayer(this); // On initialise l'AI qui est plus precisement un environnement contre lequel le joueur se bat

		initRessources();         // On initialise un certain nombre de ressource des le depart
	}

	/**
	 * @return le joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}


	/**
	 * On initialise les ressources aleatoirement sur le terrain, il y a en tout 60 bois ou nourritures places
	 */
	public void initRessources() {
		Random rand = new Random();
		int nbRessources = rand.nextInt(41) + 20;
		while(nbRessources != 0) {
			boolean tempB = true;
			Ressource temp = new Ressource();
			if(!this.listRessource.isEmpty()) {
				for(Ressource res : this.listRessource) {
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
	}

	/**
	 * Ce Thread est tres important car il permet de mettre a jour le nombre de ressource sur le plateau pour rester autour de
	 * 60 ressources placees aleatoirement sur le plateau
	 */
	public void threadRessource()
	{
		new Thread(() ->
		{
			while(true)
			{
				boolean tempB = true;
				Ressource r = new Ressource();
				if(this.listRessource.size() < 60)
				{
					for (Ressource res : this.listRessource)
					{
						if (r.getPosition().x == res.getPosition().x && r.getPosition().y == res.getPosition().y)
						{
							tempB = false;
							break;
						}
					}
					if(tempB)
					{
						this.listRessource.add(r); // ajoute de la nouvelle ressource a la liste de ressources.
						this.aff.getPlateau()[r.getPosition().x][r.getPosition().y].setRessource(r); // ajout de la nouvelles ressource au plateau de jeu.
						this.aff.refresh(); // appel pour actualiser l'affichage graphique.
					}
				}
				try
				{
					Thread.sleep(1750);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Ce Thread permet de gerer l'emplacement des unites et des differentes actualisations faites :
	 * - suppression des unites sur les cases ou elles sont pour ensuite regarder leur emplacement (qui a peut-etre changer)
	 * et les re-afficher
	 * - Si une unite de classe Ouvrier est sur une case avec une ressource alors elle la ramasse et met a jour les scores
	 * du joueur
	 * - Verification des conditions de fin de partie (win & lose)
	 */
	public void threadUnit() {
		new Thread(() -> {
			while(true) {
				for(Case[] tabCase : this.aff.getPlateau()) {
					for(Case c : tabCase) {
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

				for(Unite u : joueur.getUnites()) {
					Case c = this.aff.getPlateau()[u.getPos().x][u.getPos().y];
					if (u instanceof Combattante) {
						c.setCombattante((Combattante) u);
					}
					else {
						c.setUnit(u);

						if(c.estOccupeeRessource()) { // Je regarde si la case contient une ressource si c'est le cas alors je l'enleve et augmente le score du joueur
							Ressource r = c.removeRessource();
							this.listRessource.remove(r);
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

				// Mise sur le plateau des unites de l'environnement
				for(CombattanteAI u : ordi.getUnit()) {
					Case c = this.aff.getPlateau()[u.getPos().x][u.getPos().y];
					c.setCombattanteAI(u);
				}


				//Verification de fin partie
				if(joueur.getNbNourritures() >= 110) {
					win();
				}
				else if(joueur.getNbNourritures() < 10 && (joueur.getNbBois() < 20 && joueur.getUnites().size() == 0)) {
					lose();
				}

				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	/**
	 * Ce thread permet de gerer l'attaque des troupes du joueur (classe Combattante) envers les unites de l'environnement seulement
	 * si les deux unites sont à au plus une case de difference
	 */
	public void threadAttaqueJoueur(){
		new Thread(() -> {
			while(true){
				ArrayList<Unite> listUniteJ = this.joueur.getUnites();
				ArrayList<CombattanteAI> listUniteE = this.ordi.getUnit();
				ArrayList<Point> temp = new ArrayList<>();

				for(Unite uJ : listUniteJ){
					for(Unite uAI : listUniteE){
						if(uJ instanceof Combattante){
							Point p1 = uJ.getPos();
							Point p2 = uAI.getPos();
							int xValide = Math.abs(p1.x - p2.x);
							int yValide = Math.abs(p1.y - p2.y);
							if(xValide <= 1 && yValide <= 1){ // On regarde si les deux unites sont a au plus une case de distance
								uAI.setVie(uAI.getVie()-((Combattante) uJ).getAttack()); // On met a jour la vie de l'unite de l'environnement
								System.out.println(uAI.getVie());
								if(uAI.getVie() <= 0){
									temp.add(uAI.getPos());
								}
							}
						}
					}
				}

				// On supprime les unites dont la vie est tombe a 0
				for(Point p : temp){
					this.getAI().getUnit().remove(this.aff.getPlateau()[p.x][p.y].getCombattanteAI());
					this.aff.getPlateau()[p.x][p.y].removeCombattanteAI();
				}
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}



	/**
	 * Ce Thread permet de gerer l'attaque des unites de l'environnement sur les unites du joueur
	 */
	public void threadAttaqueAI(){
		new Thread(() -> {
			while(true){
				ArrayList<Unite> listUniteJ = this.joueur.getUnites();
				ArrayList<CombattanteAI> listUniteE = this.ordi.getUnit();
				ArrayList<Point> temp = new ArrayList<>();

				for(Unite uAI : listUniteE){
					for(Unite uJ : listUniteJ){
						if(uJ instanceof Combattante || uJ instanceof Ouvrier){
							Point p1 = uJ.getPos();
							Point p2 = uAI.getPos();
							int xValide = Math.abs(p1.x - p2.x);
							int yValide = Math.abs(p1.y - p2.y);
							if(xValide <= 1 && yValide <= 1){  // On regarde si les deux unites sont a au plus une case de distance
								uJ.setVie(uJ.getVie() - ((CombattanteAI) uAI).getAttack()); // On met a jour la vie de l'unite du joueur
								System.out.println(uJ.getVie());
								if(uJ.getVie() <= 0) {
									temp.add(uJ.getPos());
								}
							}
						}
					}
				}

				// On supprime du plateau les unites dont la vie est tombe a 0
				for(Point p : temp){
					Case c = this.aff.getPlateau()[p.x][p.y];
					if(c.estOccupeUnit()){ // On regarde juste si l'unite est une combattante ou une Ouvriere avec cette condition
						this.joueur.getUnites().remove(this.aff.getPlateau()[p.x][p.y].getUnit());
						this.aff.getPlateau()[p.x][p.y].removeUnit(); // Unit fait reference seulement a l'ouvriere ici
					}
					else {
						this.joueur.getUnites().remove(this.aff.getPlateau()[p.x][p.y].getCombattante());
						this.aff.getPlateau()[p.x][p.y].removeCombattante();
					}
				}
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	/**
	 * @return la liste des ressources sur le plateau de jeu
	 */
	public ArrayList<Ressource> getListRessource()
	{
		return this.listRessource;
	}

	/**
	 * @return l'AI qui gere les unites de l'environnement
	 */
	public AIPlayer getAI() {
		return ordi;
	}

	/**
	 * @return l'affichage
	 */
	public Affichage getAff() {
		return aff;
	}

	/**
	 * Permet de deplacer les unites avec les positions initiale et finale definis grace a des clic dans la classe Case
	 */
	public void unitADeplacer() {
		Case c = this.getAff().getPlateau()[posInitial.x][posInitial.y];
		Unite u = null;
		if(c.estOccupeUnit()) {
			u = c.getUnit();
		}
		else if(c.estOccupeeCombattante())
			u = c.getCombattante();
		u.setPosFinal(posfinal);
		if(!u.isAlive()) {
			u.start();
		}
		posInitial = posfinal;
	}

	/**
	 * @param c
	 * Permet d'ajouter une combattante sur le plateau et dans la liste du joueur
	 */
	public void setCombattantePlateau(Combattante c){
		this.joueur.addUnite(c);
		this.aff.getPlateau()[c.getPos().x][c.getPos().y].setCombattante(c);
	}

	/**
	 * @param c
	 * Permet de placer une unite de l'environnement sur le plateau
	 */
	public void setCombattanteAIPlateau(CombattanteAI c) {
		this.aff.getPlateau()[c.getPos().x][c.getPos().y].setCombattanteAI(c);
	}

	/**
	 * @param f
	 * Permet d'ajouter le batiment qui genere des Ouvrieres sur le plateau
	 */
	public void setFourmilierePlateau(Fourmiliere f){
		this.joueur.addBat(f);
		this.aff.getPlateau()[f.getPosition().x][f.getPosition().y].setFourmiliere(f);
	}

	/**
	 * @param c
	 * Permet d'ajouter le batiment qui genere des Combattantes sur le plateau
	 */
	public void setCasernePlateau(Caserne c){
		this.joueur.addBat(c);
		this.aff.getPlateau()[c.getPosition().x][c.getPosition().y].setCaserne(c);
	}

	/**
	 * Permet d'afficher une fenetre pour annoncer la victoire du joueur
	 */
	public void win() {
		JOptionPane fin = new JOptionPane();
		String s = "Vous avez assez de rations pour l'hiver pour votre fourmiliere, BRAVO !";
		fin.showConfirmDialog(aff, s, "Victoire!", JOptionPane.DEFAULT_OPTION);
	}

	/**
	 * Permet d'afficher une fenetre pour annoncer la defaite du joueur
	 */
	public void lose() {
		JOptionPane fin = new JOptionPane();
		String s = "Vous avez perdu !";
		fin.showConfirmDialog(aff, s, "Defaite !", JOptionPane.DEFAULT_OPTION);
	}
}