package MVC;

import Environnement.Ressource;
import Environnement.typeRessource;
import Unites.Unite;
import Unites.Ouvrier;
import Unites.Combattante;

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
    private Combattante c = null;
    private boolean occupeCombattante = false;
    private Unite u = null;
    private boolean occupeUnite = false;

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
    }

    // Permet de tester si une case est occupée par une ressource.
    public boolean estOccupeeRessource() { return this.occupeeRessource; }

    public boolean estOccupeeCombattante() { return this.occupeCombattante; }

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

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            clicDroit(e);
        }
        else {
            clicGauche(e);
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
