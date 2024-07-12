package sept_familles.jeu;

import sept_familles.jeu.actions.Action;

import java.util.*;

/**
 * Représente un joueur IA ou humain
 * @author Harmonie Bertucci
 */
public abstract class Joueur {

	/**
	 * Le nom du joueur, permettant de le différencier des autres
	 */
	protected String nom;
	/**
	 * Le type du joueur, afin de notamment différencier les différentes IA
	 */
	protected String type;

	/**
	 * La partie à laquelle le joueur participe
	 */
	protected Partie partie;

	protected ArrayList<Carte> mainInitiale;

	public Joueur(String nom, String type, Partie partie) {
		this.nom = nom;
		this.type = type;
		this.partie = partie;
		mainInitiale = new ArrayList<>();
	}


	/**
	 * Retourne le choix de l'action à effectuer par le joueur pour le tour actuel
	 * @return l'action à effectuer, null si le joueur ne peut rien faire
	 */
	public abstract Action choisirAction();

	public String getNom() {
		return this.nom;
	}

	public Partie getPartie() {
		return partie;
	}

	public List<Carte> getMainInitiale() {
		return mainInitiale;
	}

	public void setMainInitiale(ArrayList<Carte> mainInitiale) {
		this.mainInitiale = mainInitiale;
	}

	@Override
	public String toString() {
		return nom ;
	}
}
