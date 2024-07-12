package sept_familles.jeu;

import sept_familles.ia.Distribution;
import sept_familles.jeu.actions.*;

import java.util.*;

/**
 * Une situation de jeu complète modélise l'état complet du jeu.
 * La classe Partie garde en mémoire une situation complète à chaque instant.
 * L'IA MinMax utilise des situations simulées basées sur une situation complète pour calculer ses coups.
 * @see sept_familles.jeu.Partie
 * @see sept_familles.ia.SituationSimulee
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class SituationComplete implements Situation {

	private EtatsJeu etatCourant;
	private List<Joueur> joueurs;
	private Joueur joueurCourant;
	private List<Carte> pioche;
	private Map<Joueur, List<Carte>> mains;
	private List<Carte> cartes;
	private Action derniereAction;
	private Map<String, Joueur> famillesPosees;

	public SituationComplete() {
		etatCourant = EtatsJeu.DEMANDER;
		derniereAction = null;
		joueurs = new ArrayList<>();
		joueurCourant = null;
		pioche = new ArrayList<>();
		mains = new HashMap<>();
		cartes = new ArrayList<>();
		famillesPosees = new HashMap<>();
	}

	public SituationComplete(Distribution d) {
		this(d.getJoueurs(), d.clonerMain(), d.clonerPioche(), d.clonerCartes());
	}

	public SituationComplete(List<Joueur> joueurs, Map<Joueur, List<Carte>> mains, List<Carte> pioche,
			List<Carte> cartes) {
		this();
		if (joueurs.size() < 2)
			throw new IllegalArgumentException("Il faut au moins 2 joueurs pour jouer aux sept familles");
		this.joueurs = joueurs;
		joueurCourant = joueurs.get(0);
		this.mains = mains;
		this.pioche = pioche;
		this.cartes = cartes;
		for (Carte c : cartes)
			famillesPosees.put(c.famille(), null);
	}

	@Override
	public SituationComplete clone() {
		SituationComplete sc = new SituationComplete();
		sc.setEtatCourant(etatCourant);
		sc.setJoueurs(joueurs);
		sc.setJoueurCourant(joueurCourant);
		sc.setCartes(cartes);
		sc.setPioche(pioche);
		sc.setMains(mains);
		sc.setFamillesPosees(famillesPosees);
		return sc;
	}

	/**
	 * crée les actions relatives a l'état en cours
	 *
	 * @return la liste des actions possibles a ce moment de la partie
	 */
	@Override
	public Set<Action> getActions() {
		Set<Action> actions = new HashSet<>();

		switch (etatCourant) {
			case ANNONCER:
				// On regarde le résultat de la pioche
				Piocher piocher = (Piocher) derniereAction;
				Carte cartePiochee = mains.get(joueurCourant).get(mains.get(joueurCourant).size() - 1);
				// On annonce ce qu'on a pioché
				if (cartePiochee.equals(piocher.getDemander().getCarteDemandee()))
					// Si la pioche a été bonne
					actions.add(ActionFactory.creerAnnoncerBonnePioche(joueurCourant, cartePiochee, piocher));
				else
					// Si la pioche a été mauvaise
					actions.add(ActionFactory.creerAnnoncerMauvaisePioche(joueurCourant, cartePiochee, piocher));
				break;
			case DEMANDER:
				// Si au début du tour le jeu est terminé,
				// on ne peut plus rien faire
				if (estTerminale())
					return null;
				// Si la main est vide, on ne peut que piocher avant de commencer à demander
				if (mains.get(joueurCourant).isEmpty())
					actions.add(ActionFactory.creerPiocherSiMainVide(joueurCourant));
				else
					// On cherche à lister toutes les demandes possibles
					for (Carte carteADemander : cartesDemandables(joueurCourant))
						for (Joueur joueurCible : joueursDemandables(joueurCourant))
							actions.add(ActionFactory.creerDemanderCarte(joueurCourant, joueurCible, carteADemander));
				break;
			case REPONDRE:
				// On regarde le résultat de la demande précédente
				DemanderCarte demanderCarte = (DemanderCarte) derniereAction;
				if (mains.get(demanderCarte.getJoueurCible()).contains(demanderCarte.getCarteDemandee()))
					// Si la demande a été bonne, le joueur ciblé doit donner la carte demandée
					actions.add(ActionFactory.creerDonnerCarte(
							demanderCarte.getJoueurCible(),
							demanderCarte.getJoueurSource(),
							demanderCarte.getCarteDemandee(),
							demanderCarte
					));
				else
					// Si la demande a été mauvaise, le joueur source doit piocher
					actions.add(ActionFactory.creerPiocher(joueurCourant, (DemanderCarte) derniereAction));
				break;
			case POSER_FAMILLE:
				// On récupère les familles que le joueur possède
				Set<String> famillesJoueurs = new HashSet<>();
				for (Carte c : mains.get(joueurCourant))
					famillesJoueurs.add(c.famille());

				// On récupère les familles que les autres joueurs ont, pioche incluse
				Set<String> famillesAutres = new HashSet<>();
				for (Map.Entry<Joueur, List<Carte>> entry : mains.entrySet()) {
					if (!entry.getKey().equals(joueurCourant))
						for (Carte c : entry.getValue())
							famillesAutres.add(c.famille());
				}
				for (Carte c : pioche)
					famillesAutres.add(c.famille());

				// Si aucun des autres joeurs ne possède une famille que le joueur possède,
				// c'est qu'il possède toute la famille en question
				for (String famille : famillesJoueurs)
					if (!famillesAutres.contains(famille))
						actions.add(ActionFactory.creerPoserFamille(joueurCourant, famille));

				// S'il ne peut pas poser de famille, il faut indiquer ce cas de figure
				if (actions.isEmpty())
					actions.add(ActionFactory.creerNePasPoserFamille(derniereAction, joueurCourant));

				break;
		}
		return actions;
	}

	/**
	 * {@inheritDoc}
	 * La situation complète suivante est la situation complète courante modifiée par l'action en paramètre.
	 */
	@Override
	public SituationComplete getSuccesseur(Action ac) {
		return ac.executer(this);
	}

	@Override
	public boolean estTerminale() {
		// Si la pioche est vide au début du tour, la situation est de fait terminale
		if (pioche.isEmpty() && etatCourant == EtatsJeu.DEMANDER)
			return true;

		Map<Joueur, Integer> cptFamilles = new HashMap<>();
		for (Joueur j : joueurs)
			cptFamilles.put(j, 0);
		// On compte le nombre de familles que chaque joueur a posé
		for (Map.Entry<String, Joueur> famille : famillesPosees.entrySet())
			// Si la famille a été posée
			if (famille.getValue() != null) {
				int cpt = cptFamilles.get(famille.getValue()) + 1;
				// On regarde le nombre de familles posées par le joueur
				if (cpt >= Math.ceil(famillesPosees.size() / 2f))
					// Si le joueur a posé plus de la moitié des familles,
					// la situation est terminale
					return true;
				else
					// Sinon on met à jour le compteur
					cptFamilles.put(famille.getValue(), cpt);
			}
		// Pioche non vide, et aucun joueur n'a posé plus de la moitié des familles
		return false;
	}

	public void joueurSuivant() {
		// Récupère l'index du joueur suivant le joueur courant
		int index = this.joueurs.indexOf(this.joueurCourant);
		index = index + 1 >= this.joueurs.size() ? 0 : index + 1;

		this.joueurCourant = this.joueurs.get(index);
	}

	/**
	 * Permet de savoir quelles cartes le joueur donné peut demander (les cartes qu'il n'a pas déjà, des familles qu'il a)
	 * @param j le joueur
	 * @return La liste des cartes demandables
	 */
	public ArrayList<Carte> cartesDemandables(Joueur j) {
		ArrayList<Carte> coups = new ArrayList<>();
		Set<String> famillesDemandables = new HashSet<>();

		// Liste des familles que le joueur peut demander
		for (Carte carte : mains.get(j))
			famillesDemandables.add(carte.famille());

		// Liste des cartes que le joueur peut demander
		for (Carte carte : cartes)
			if (famillesDemandables.contains(carte.famille()) && !mains.get(j).contains(carte))
				coups.add(carte);
		return coups;
	}

	/**
	 * permet d'avoir la liste des autres joueurs
	 * @param j le joueur
	 * @return la liste des joueurs
	 */
	public List<Joueur> joueursDemandables(Joueur j) {
		List<Joueur> joueurs = new ArrayList<>(this.joueurs);
		joueurs.remove(j);
		return joueurs;
	}

	public List<Carte> getCartes() {
		return cartes;
	}

	public EtatsJeu getEtatCourant() {
		return etatCourant;
	}

	public Set<String> getFamilles() {
		return famillesPosees.keySet();
	}

	public Map<String, Joueur> getFamillesPosees() {
		return famillesPosees;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public List<Carte> getPioche() {
		return pioche;
	}

	public List<Carte> getMain(Joueur j) {
		return mains.get(j);
	}

	public void setEtatCourant(EtatsJeu etatCourant) {
		this.etatCourant = etatCourant;
	}

	private void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	private void setJoueurCourant(Joueur joueurCourant) {
		this.joueurCourant = joueurCourant;
	}

	private void setPioche(List<Carte> pioche) {
		this.pioche = pioche;
	}

	public void setDerniereAction(Action derniereAction) {
		this.derniereAction = derniereAction;
	}

	private void setMains(Map<Joueur, List<Carte>> mains) {
		this.mains = mains;
	}

	private void setFamillesPosees(Map<String, Joueur> famillesPosees) {
		this.famillesPosees = famillesPosees;
	}

	private void setCartes(List<Carte> cartes) {
		this.cartes = cartes;
	}

}
