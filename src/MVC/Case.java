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

public class Case extends ZoneCliquable {
    private Point posInGrid;
    private Ressource ressource = null;
    private boolean occupeeRessource = false;

    private Unite u = null;
    public boolean occupeUnite = false;

    private Combattante c = null;
    private boolean occupeCombattante = false;

    private CombattanteAI combattanteAI = null;
    private boolean occupeCombattanteAI = false;

    private Fourmiliere f = null;
    private boolean occupeFourmiliere = false;

    private Caserne caserne = null;
    private boolean occupeCaserne = false;

    // Constructeur
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

        //affichage graphique des ressources
        if(this.occupeeRessource)
            drawRessource(g);

        //affichage graphique des combattantes
        if(this.occupeCombattante)
            drawCombattante(g);

        if(this.occupeUnite) {
            drawUnit(g);
        }
        if(this.occupeCombattanteAI) {
            drawCombattanteAI(g);
        }
        if(this.estOccupeFourmiliere()) {
            drawFourmiliere(g);
        }
        if(this.estOccupeCaserne()) {
            drawCaserne(g);
        }
    }

    // Permet de tester si une case est occupée par une ressource.
    public boolean estOccupeeRessource() { return this.occupeeRessource; }

    public boolean estOccupeeCombattante() { return this.occupeCombattante; }

    public boolean estOccupeeCombattanteAI() { return this.occupeCombattanteAI; }

    /**
     * Methode pour effectuer l'affichage graphique des ressources.
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

    public void drawCombattante(Graphics g){
        try {
            Image imageCombattante = ImageIO.read(new File("Ressources/combattante.jpg"));
            g.drawImage(imageCombattante, 0, 0, 1353/35, 1076/20, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void drawCombattanteAI(Graphics g) {
        try {
            Image image = ImageIO.read(new File("Ressources/combattanteAI.jpg"));
            g.drawImage(image, 0, 0, 1353/35, 1076/20, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param g
     *
     * J'affiche l'image de la fourmiliere qui genere les ouvieres
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
     *
     * @param g
     *
     * J'affiche l'image de la caserne qui genere les combattantes
     */

    public void drawCaserne(Graphics g){
        try {
            Image image = ImageIO.read(new File("Ressources/caserne.jpg"));
            g.drawImage(image, 0, 0, 800/15, 601/15, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRessource(Ressource r) {
        this.ressource = r;
        this.occupeeRessource = true;
    }

    public Ressource removeRessource() {
        Ressource r = this.ressource;
        this.ressource = null;
        this.occupeeRessource = false;
        return r;
    }

    public void setCombattante(Combattante c){
        this.c = c;
        this.occupeCombattante = true;
    }

    public void removeCombattante(){
        this.c = null;
        this.occupeCombattante = false;
    }

    public Combattante getCombattante(){
        return this.c;
    }

    public void setCombattanteAI(CombattanteAI c){
        this.combattanteAI = c;
        this.occupeCombattanteAI = true;
    }

    public void removeCombattanteAI(){
        this.combattanteAI = null;
        this.occupeCombattanteAI = false;
    }

    public CombattanteAI getCombattanteAI(){
        return this.combattanteAI;
    }

    public boolean estOccupeFourmiliere() {
        return this.occupeFourmiliere;
    }

    public void setFourmiliere(Fourmiliere fo) {
        this.f = fo;
        this.occupeFourmiliere = true;
    }

    public boolean estOccupeCaserne() {
        return this.occupeCaserne;
    }

    public void setCaserne(Caserne c) {
        this.caserne = c;
        this.occupeCaserne = true;
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            clicDroit(e);
        }
        else {
            if(estOccupeFourmiliere()) {
                if(!(super.getEtat().getJoueurs().getNbNourritures() < 10)) {
                    super.getEtat().getJoueurs().addUnite(new Ouvrier(new Point(14, 2)));
                    super.getEtat().getJoueurs().setNbNourritures(-10);
                    System.out.println("Vous generez une ouvriere : - 10 de nourriture ! Votre nombre de nourriture : " + super.getEtat().getJoueurs().getNbNourritures());
                }
                else {
                    System.out.println("Vous n'avez pas assez de nourritures !");
                }

            }
            else if(estOccupeCaserne()) {
                if(!(super.getEtat().getJoueurs().getNbBois() < 20)) {
                    super.getEtat().getJoueurs().addUnite(new Combattante(new Point(13, 1)));
                    super.getEtat().getJoueurs().setNbBois(-20);
                    System.out.println("Vous generez une ouvriere : - 20 de bois ! Votre nombre de bois : " + super.getEtat().getJoueurs().getNbBois());
                }
                else {
                    System.out.println("Vous n'avez pas assez de bois !");
                }
            }
            else {
                clicGauche(e);
            }
        }
    }

    public void clicDroit(MouseEvent e) {
        super.getEtat().posInitial = posInGrid;

    }

    public void clicGauche(MouseEvent e) {
        super.getEtat().posfinal = posInGrid;
        super.getEtat().unitADeplacer();
    }

    public Unite getUnit() {
        return u;
    }

    public boolean estOccupeUnit() {
        return this.occupeUnite;
    }

    public void removeUnit() {
        u = null;
        this.occupeUnite = false;
    }

    public void setUnit(Unite u) {
        this.u = u;
        this.occupeUnite = true;
    }
}
