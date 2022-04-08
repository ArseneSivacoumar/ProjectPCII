import MVC.*;
public class Main {
	public static void main(String[] args) {
       Fenetre fenetre = new Fenetre("Main");
		Affichage affichage = new Affichage(15);
		fenetre.ajouteElement(affichage);
		fenetre.dessineFenetre();
	}
}