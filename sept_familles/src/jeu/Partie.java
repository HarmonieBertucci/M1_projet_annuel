package sept_familles.jeu;

import sept_familles.ia.IAAleatoire;
import sept_familles.jeu.actions.Action;

import java.util.*;

/**
 * Permet de représenter une partie
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class Partie {

	protected SituationComplete sc;
	protected int nbCartesADonner;

	protected List<Carte> ordre;

	protected Partie() {}

	public Partie(int nbJoueurs, String[] familles, String[] membres) {
		List<Carte> cartes = new ArrayList<>();
		List<Carte> pioche;
		List<Joueur> joueurs ;

		// Création des joueurs
		joueurs = creerJoueurs(nbJoueurs);

		// Création des cartes
		for (String famille : familles)
			for(String membre : membres)
				cartes.add(new Carte(famille, membre));

		pioche = new ArrayList<>(cartes);
		// Mélange de la pioche
		Collections.shuffle(pioche);

		ordre = new ArrayList<>();
		ordre.addAll(pioche);

		// Permet de toujours avoir une pioche avec un jeu réduit
		nbCartesADonner = (int) (familles.length * membres.length * 0.15);
		nbCartesADonner = nbCartesADonner <= 0 ? 1 : nbCartesADonner;

		// Distribution des cartes
		Map<Joueur, List<Carte>> mains = new HashMap<>();
		for (Joueur j : joueurs)
			mains.put(j, new ArrayList<>());
		for (int i = 0; i < nbCartesADonner; i++)
			for (Joueur j : joueurs)
				mains.get(j).add(pioche.remove(0));

		//set mains initiales aux joueurs

		for (Joueur j :joueurs){
			ArrayList<Carte> mainInitiale = new ArrayList<>(mains.get(j));
			j.setMainInitiale(mainInitiale);
		}

		sc = new SituationComplete(joueurs, mains, pioche, cartes);
	}

	/**
	 * Crée les joueurs
	 * @param nbJoueurs nombre de joueurs à créer
	 * @return la liste des joueurs créés
	 */
	public List<Joueur> creerJoueurs(int nbJoueurs){
		List<Joueur> joueurs = new ArrayList<>();
		for (int i = 0 ; i<nbJoueurs ; i++)
			joueurs.add(new IAAleatoire("ia_"+i,this));
		return joueurs;
	}

	public void lancer() {
		int tour = 1;
		while (!sc.estTerminale()) {
			Action action = sc.getJoueurCourant().choisirAction();

			if (action == null)
				break;

			sc = sc.getSuccesseur(action);

			if (sc.getEtatCourant().equals(EtatsJeu.POSER_FAMILLE))
				tour ++;
		}
		finDePartie(-- tour);
	}

	/**
	 * permet de simuler la fin de la partie
	 */
	public void finDePartie(int nbTour){
		System.out.println("\n\nLa partie s'est terminée en "+ nbTour +" tours.");

		Map<Joueur, List<String>> famillesParJoueur = new HashMap<>();
		for (Joueur j : sc.getJoueurs())
			famillesParJoueur.put(j, new ArrayList<>());
		// À chaque joueur, on associe la liste des familles qu'il a posées
		for (Map.Entry<String, Joueur> entry : sc.getFamillesPosees().entrySet())
			if (entry.getValue() != null)
				famillesParJoueur.get(entry.getValue()).add(entry.getKey());

		for (Joueur j : sc.getJoueurs()){
			switch (famillesParJoueur.get(j).size()){
				case 0:
					System.out.println(j.getNom() + " n'a pas posé de familles.");
					break;
				case 1:
					System.out.println(j.getNom() + " a posé la famille : " + famillesParJoueur.get(j).get(0));
					break;
				default:
					StringBuilder sb = new StringBuilder();
					for (String fam : famillesParJoueur.get(j))
						sb.append(fam).append(", ");

					System.out.println(j.getNom() + " a posé les familles : " + sb);
					break;
			}

		}

		// Calculs des gagnants
		List<Joueur> gagnants = new ArrayList<>();
		int max = 0;
		for (Joueur j : sc.getJoueurs()) {
			int score = famillesParJoueur.get(j).size();
			if (score > max) {
				max = score;
				gagnants = new ArrayList<>();
				gagnants.add(j);
			} else if (score == max)
				gagnants.add(j);
		}

		if (gagnants.size() != 1)
			System.out.println("Match nul");
		else
			System.out.println("Le gagnant est : " + gagnants.get(0));
	}

	public Set<Action> getActions() {
		return sc.getActions();
	}

	public List<Carte> getCartes() {
		return sc.getCartes();
	}

	public List<Joueur> getJoueurs() {
		return sc.getJoueurs();
	}

	public List<Carte> getMain(Joueur j) {
		return sc.getMain(j);
	}

	public int getNbCartesADonner(){
		return nbCartesADonner;
	}

}
