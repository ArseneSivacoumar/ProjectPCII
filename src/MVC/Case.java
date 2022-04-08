package MVC;

import Environnement.*;
import Unites.*;
import Batiments.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Case extends Controle {
    private Point posInGrid; // position de la case
    private Ressource ressource = null; // ressource present dans la case(initialiser a nul case vide au debut)
    private boolean occupeeRessource = false; // boolean pour voir si la case est occupée par une ressource

    private Unite u = null; // ouvrier present dans la case
    public boolean occupeUnite = false; // boolean pour voir si la case est occupée par un ouvier

    private Combattante c = null; // combattante present dans la case
    private boolean occupeCombattante = false; // boolean pour voir si la case est occupée par une combattante

    private CombattanteAI combattanteAI = null; // combattanteAI present dans la case
    private boolean occupeCombattanteAI = false; // boolean pour voir si la case est occupée par une combattanteAI

    private Fourmiliere f = null; // fourmilére present dans la case
    private boolean occupeFourmiliere = false; // boolean pour voir si la case est occupée par une fourmilére

    private Caserne caserne = null; // caserne present dans la case
    private boolean occupeCaserne = false; // boolean pour voir si la case est occupée par une caserne

    public Case(Etat e, Point p) {
        // Initialisation d'une case cliquable, de dimensions 40*40 pixels.
        super(e,40, 40);
        posInGrid = p;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Border blackline = BorderFactory.createLineBorder(Color.gray);
        this.setBorder(blackline);

        // affichage graphique des Ressources.
        if(this.occupeeRessource)
            drawRessource(g);

        // affichage graphique des Combattantes.
        if(this.occupeCombattante)
            drawCombattante(g);

        // affichage graphique des Ouvrier.
        if(this.occupeUnite) {
            drawUnit(g);
        }

        // affichage graphique des CombattanteAI.
        if(this.occupeCombattanteAI) {
            drawCombattanteAI(g);
        }

        // affichage graphique de la Fourmilliére.
        if(this.estOccupeFourmiliere()) {
            drawFourmiliere(g);
        }

        // affichage graphique de la Caserne.
        if(this.estOccupeCaserne()) {
            drawCaserne(g);
        }
    }

    /**
     * Permet de tester si une case est occupée par une ressource.
     * @return le boolean occupeeRessource
     */
    public boolean estOccupeeRessource() { return this.occupeeRessource; }

    /**
     * Permet de tester si une case est occupée par une combattante.
     * @return le boolean occupeCombattante
     */
    public boolean estOccupeeCombattante() { return this.occupeCombattante; }

    /**
     * Permet de tester si une case est occupée par une combattanteAI.
     * @return le boolean occupeCombattanteAI
     */
    public boolean estOccupeeCombattanteAI() { return this.occupeCombattanteAI; }

    /**
     * Permet de tester si une case est occupée par un ouvrier.
     * @return le boolean occupeUnite
     */
    public boolean estOccupeUnit() { return this.occupeUnite; }

    /**
     * Permet de tester si une case est occupée par une caserne.
     * @return le boolean occupeCaserne
     */
    public boolean estOccupeCaserne() {
        return this.occupeCaserne;
    }

    /**
     * Permet de tester si une case est occupée par une fourmiliére.
     * @return le boolean occupeFourmiliére.
     */
    public boolean estOccupeFourmiliere() {
        return this.occupeFourmiliere;
    }

    /**
     * Methode pour affiché graphiquement les différents ressoureces.
     * @param g
     */
    public void drawRessource(Graphics g){
        try {
            Image imageMiel = ImageIO.read(new File("Ressources/miel.jpg"));
            Image imageBois = ImageIO.read(new File("Ressources/Ressource.png"));

            if (this.ressource.gettR() == typeRessource.bois)
                g.drawImage(imageBois, 0 , 0, 474/11, 288/8, this);
            else
                g.drawImage(imageMiel, 0 , 0, 839/22, 847/22, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode pour affiché graphiquement les combattantes.
     * @param g
     */
    public void drawCombattante(Graphics g){
        try {
            Image imageCombattante = ImageIO.read(new File("Ressources/combattante.jpg"));
            g.drawImage(imageCombattante, 0, 0, 1353/35, 1076/20, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode pour affiché graphiquement les ouvriers.
     * @param g
     */
    public void drawUnit(Graphics g) {
        try {
            Image imageUnit = ImageIO.read(new File("Ressources/ouvrier.jpeg"));
            if(u instanceof Ouvrier) {
                g.drawImage(imageUnit, 0 , 0, 192/5, 165/4, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode pour affiché les combattante(AI) de l'IA.
     * @param g
     */
    public void drawCombattanteAI(Graphics g) {
        try {
            Image image = ImageIO.read(new File("Ressources/combattanteAI.jpg"));
            g.drawImage(image, 0, 0, 1353/35, 1076/20, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * J'affiche l'image de la fourmiliere qui genere les ouvieres
     * @param g
     */
    public void drawFourmiliere(Graphics g){
        try {
            Image image = ImageIO.read(new File("Ressources/fourmiliere.jpg"));
            g.drawImage(image, 0, 0, 612/10, 467/10, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * J'affiche l'image de la caserne qui genere les combattantes
     * @param g
     */
    public void drawCaserne(Graphics g){
        try {
            Image image = ImageIO.read(new File("Ressources/caserne.jpg"));
            g.drawImage(image, 0, 0, 800/15, 601/15, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set une ressource dans la case et met le boolean occupeeRessource a true.
     * @param r
     */
    public void setRessource(Ressource r) {
        this.ressource = r;
        this.occupeeRessource = true;
    }

    /**
     * Remove la réssource presente dans la cese et met le boolean occupeeRessource a false.
     * @return
     */
    public Ressource removeRessource() {
        Ressource r = this.ressource;
        this.ressource = null;
        this.occupeeRessource = false;
        return r;
    }

    /**
     * Set une combattante dans la case et met le boolean occupeeCombattante a true.
     * @param c
     */
    public void setCombattante(Combattante c){
        this.c = c;
        this.occupeCombattante = true;
    }

    /**
     * Remove la combattante présente dans la case et met le boolean occupeCombattante a false.
     */
    public void removeCombattante(){
        this.c = null;
        this.occupeCombattante = false;
    }

    /**
     * Set une combattante de l'IA dans la case et met le boolean occupeCombattanteAI a true.
     * @param c
     */
    public void setCombattanteAI(CombattanteAI c){
        this.combattanteAI = c;
        this.occupeCombattanteAI = true;
    }

    /**
     * Remove la combattante de l'IA de la case et met le boolean occupeCombattanteAI a false.
     */
    public void removeCombattanteAI(){
        this.combattanteAI = null;
        this.occupeCombattanteAI = false;
    }

    /**
     * Remove l'ouvrier present dans la casee et met le boolean occupeUnite a false.
     */
    public void removeUnit() {
        u = null;
        this.occupeUnite = false;
    }

    /**
     * Set un ouvrier dans la case et met le boolean occupeUnite a true.
     * @param u
     */
    public void setUnit(Unite u) {
        this.u = u;
        this.occupeUnite = true;
    }

    /**
     * Set une fourmiliére dans la case et met le boolean occupeFourmiliére a true.
     * @param fo
     */
    public void setFourmiliere(Fourmiliere fo) {
        this.f = fo;
        this.occupeFourmiliere = true;
    }

    /**
     * Set une caserne dans la case et met le boolean occupeCaserne a true.
     * @param c
     */
    public void setCaserne(Caserne c) {
        this.caserne = c;
        this.occupeCaserne = true;
    }

    /**
     * @return la combattante de l'IA présente dans la case.
     */
    public CombattanteAI getCombattanteAI(){
        return this.combattanteAI;
    }

    /**
     * @return la combattante présente dans la case.
     */
    public Combattante getCombattante(){
        return this.c;
    }

    /**
     * @return l'ouvrier present dans la case.
     */
    public Unite getUnit() { return u; }

    /**
     * Methode qui va en fonction du type de clic de la souris va effectuer different tache :
     * -ajouter une combattante au joueur
     * -ajouter un ouvrier au joueur
     * -deplace des unites.
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {// si clic droit effectuer
            clicDroit(e);
        }
        else {
            if(estOccupeFourmiliere()) { // si clique gauche sur une case contenant une fourmiliére
                if(!(super.getEtat().getJoueur().getNbNourritures() < 10)) { // si nombre de nourritures du joueur > 10
                    super.getEtat().getJoueur().addUnite(new Ouvrier(new Point(14, 2))); // on ajoute 1 ouvrier avec comme coordonnée (14, 2) au joueur.
                    super.getEtat().getJoueur().setNbNourritures(-10); // on enléve 10(coût pour 1 ouvier) de nourritures au joueur
                    System.out.println("Vous generez un ouvrier : - 10 de nourriture ! Votre nombre de nourriture : " + super.getEtat().getJoueur().getNbNourritures());
                }
                else {
                    System.out.println("Vous n'avez pas assez de nourritures !");
                }

            }
            else if(estOccupeCaserne()) { // si clique gauche sur une case contenant une caserne
                if(!(super.getEtat().getJoueur().getNbBois() < 20)) { // si nombre de bois du joueur > 20
                    super.getEtat().getJoueur().addUnite(new Combattante(new Point(13, 1))); // on ajoute 1 combattante avec comme coordonnée (13, 1) au joueur.
                    super.getEtat().getJoueur().setNbBois(-20);// on enléve 20(coût pour 1 combattante) de nourritures au joueur
                    System.out.println("Vous generez une ouvriere : - 20 de bois ! Votre nombre de bois : " + super.getEtat().getJoueur().getNbBois());
                }
                else {
                    System.out.println("Vous n'avez pas assez de bois !");
                }
            }
            else { // si clic gauche affectuer
                clicGauche(e);
            }
        }
    }

    /**
     * Methode qui va permet de stocker la position de la case de ou on souhaite ce deplacer.
     * @param e
     */
    public void clicDroit(MouseEvent e) {
        super.getEtat().posInitial = posInGrid;

    }

    /**
     * Methode qui va permet de stocker la position de la case où notre unite va ce deplacer.
     * @param e
     */
    public void clicGauche(MouseEvent e) {
        super.getEtat().posfinal = posInGrid;
        super.getEtat().unitADeplacer();
    }
}
