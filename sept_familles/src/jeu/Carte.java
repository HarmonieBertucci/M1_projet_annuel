package sept_familles.jeu;

/**
 * Représente une carte du jeu
 *
 * @author Harmonie Bertucci
 */
public class Carte {
	private final String famille;
	private final String membre;

	public Carte(String famille, String membre) {
		this.famille = famille;
		this.membre = membre;
	}

	public String famille() {
		return this.famille;
	}

	@Override
	public String toString() {
		return this.membre + this.famille;
	}

	public boolean equals(Carte carte) {
		return carte.toString().equals(toString());
	}

}
