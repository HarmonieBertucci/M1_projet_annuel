package sept_familles.jeu;

import sept_familles.jeu.actions.Action;
import sept_familles.jeu.actions.DemanderCarte;

/**
 * Représente un joueur humain, par opposition à une intelligence artificielle.
 */
public class Humain extends Joueur{

	/**
	 * Constructeur un joueur humain
	 * @param nom le nom du joueur
	 * @param partie la partie à laquelle le joueur participe
	 */
	public Humain(String nom, Partie partie) {
		super(nom, "Humain", partie);
	}

	@Override
	public Action choisirAction() {
		// TODO
		return null;
	}
}
