package sept_familles.jeu;

import sept_familles.ia.Distribution;
import sept_familles.ia.IASimulation;
import sept_familles.jeu.actions.Action;

import java.util.*;

/**
 * Permet de lancer une partie avec affichage
 */
public class PartieAffichee extends Partie {

	public PartieAffichee(int nbJoueurs, String[] familles, String[] membres) {
		super(nbJoueurs,familles,membres);

		for(Joueur j : sc.getJoueurs())
			if (j.getClass().equals(IASimulation.class)){
				IASimulation joueur = (IASimulation) j;
				joueur.creerDistributions();
				joueur.creerSituationSimuleeInitiale();
			}
	}

	/**
	 * Ici, on crée des joueurs de simulation (qui "raisonnent")
	 * @param nbJoueurs nombre de joueurs à créer
	 * @return la liste des joueurs
	 */
	@Override
	public List<Joueur> creerJoueurs(int nbJoueurs) {
		List<Joueur> joueurs = new ArrayList<>();
		for (int i = 0 ; i<nbJoueurs ; i++)
			joueurs.add(new IASimulation("ia_"+i,this));
		return joueurs;
	}

	@Override
	public void lancer() {

		IASimulation joueur = (IASimulation) sc.getJoueurCourant();
		List<Action> historique = new ArrayList<>();

		int tour = 1;

		System.out.println(joueur + " est le joueur qui raisonne\n\n*************** \nTour 1 :");

		while (!sc.estTerminale()) {
			System.out.println("  Joueur Courant : "+sc.getJoueurCourant()+"\n\n  Mains actuelle des joueurs :");
			for (Joueur j : getJoueurs())
				System.out.println("    " + j + " : " + getMain(j));
			System.out.println("   Contenu de la pioche : "+sc.getPioche());


			if (tour <= 10){
				System.out.println("\n  Actions possibles :");
				for (Action a : sc.getActions())
					System.out.println("    -> " + a );


				System.out.println("\n  Actions possibles selon la situation simulée du joueur " + joueur+":");
				Set<Action> actions = joueur.getSituationSimulee().getActions();
				for (Action a : actions)
					System.out.println("    -> " + a );
				if(actions.isEmpty())
					System.out.println("    -> aucune action\n");

				System.out.println("\n  Mains possibles des adversaires selon :"+joueur);
				List<Distribution> distri = joueur.getSituationSimulee().getDistributionsPossibles();
				for (int i = 0; i <=(Math.min(distri.size()-1, 3)) ; i++){
					System.out.println("     Si la distribution initiale était : "+distri.get(i).getOrdreInitial());
					for (Map.Entry<Joueur,List<Carte>> entry : distri.get(i).mainsApresHistorique(historique).entrySet())
						if(!entry.getKey().equals(joueur))
							System.out.println("        "+entry.getKey()+" possèderait "+entry.getValue());
				}
				System.out.println("\n  Nombre de distributions cohérentes : "+distri.size());
				}




			Action action = sc.getJoueurCourant().choisirAction();

			System.out.println("\n-------- \nTransition \n  Action effectuée par "+sc.getJoueurCourant()+" : " + action );

			//mettre à jour la situation simulée des joueurs IASimulation
			for (Joueur j : getJoueurs())
				if (j.getClass().equals(IASimulation.class))
					((IASimulation) j).mettreAJourSituationSimulee(action);

			historique.add(action);

			String affichage ;

			if (sc.getEtatCourant().equals(EtatsJeu.POSER_FAMILLE)){
				tour ++;
				affichage ="\n*************** \nTour "+tour+" :";
			}
			else affichage = "\n+++++++++ \nActions suivante :";

			sc = sc.getSuccesseur(action);
			if(!sc.estTerminale()) System.out.println(affichage);

		}
		finDePartie(-- tour);
	}

}
