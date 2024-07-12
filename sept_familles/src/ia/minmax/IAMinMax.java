package sept_familles.ia.minmax;

import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.Partie;
import sept_familles.jeu.actions.DemanderCarte;

import java.util.*;

public class IAMinMax extends Joueur {

	private final List<List<Carte>> distributions = new ArrayList<>();

	public IAMinMax(String nom, Partie partie) {
		super(nom, "IA avec graphe de distributions et stratégie MinMax", partie);

		// Sur toutes les distributions possibles,
		// on garde les distributions correspondant à la main de l'IA
		for (List<Carte> distribution : permutations(partie.getCartes()))
			if (Collections.indexOfSubList(distribution, partie.getMain(this)) == 0)
				distributions.add(distribution);
	}

	@Override
	public DemanderCarte choisirAction() {
		// TODO : Remplacer le choix aleatoire par MinMax
		return null;
	}

	/**
	 * Calcule toutes les combinaisons possibles des cartes.
	 * @param cartes La liste des cartes dont on doit calculer les combinaisons
	 * @return La liste exhaustive des permutations existantes
	 */
	public List<List<Carte>> permutations(List<Carte> cartes) {
		List<List<Carte>> perms = new ArrayList<>();
		permuter(cartes, 0, perms);
		return perms;
	}

	/**
	 * Effectue des appels récursifs avec un échange de cartes dans la séquence pour
	 * chaque itération.
	 * @param cartes les cartes à permuter
	 * @param k un cardinal indiquant l'index de départ de la sous-séquence à permuter
	 * @param perms la liste des permutations déjà calculées
	 */
	private void permuter(List<Carte> cartes, int k, List<List<Carte>> perms) {
		if (k >= cartes.size() - 1) {
			perms.add(new ArrayList<>(cartes));
		} else {
			for (int i = k; i < cartes.size(); i++) {
				Collections.swap(cartes, i, k);
				permuter(cartes, k + 1, perms);
				Collections.swap(cartes, k, i);
			}
		}
	}
}
