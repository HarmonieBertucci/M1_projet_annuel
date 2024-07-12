package sept_familles.ia;

import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.Partie;
import sept_familles.jeu.actions.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class IASimulation extends Joueur {
    private final List<Distribution> distributions;
    private final Random random;

    private SituationSimulee situationSimulee;

    public IASimulation(String nom, Partie partie) {
        super(nom, "Pseudo-MinMax", partie);

        distributions = new ArrayList<>();
        random = new Random();
    }

    @Override
    public Action choisirAction() {
        List<Action> actions = partie.getActions().stream().collect(Collectors.toList());
        if (actions.isEmpty())
            return null;

        return actions.get(random.nextInt(actions.size()));
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
     *
     * @param cartes Les cartes à permuter
     * @param k      Un cardinal indiquant l'index de départ de la sous séquence à permuter
     * @param perms  La liste des permutations deja calculées
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

    /**
     * Instancie toutes les distributions initiales de cartes
     */
    public void creerDistributions() {
        for (List<Carte> distribution : permutations(partie.getCartes()))
            if (Collections.indexOfSubList(distribution, partie.getMain(this)) == 0)
                distributions.add(new Distribution(distribution, partie.getJoueurs(), partie.getNbCartesADonner()));
    }

    /**
     * Instancie la situation simulée initiale
     */
    public void creerSituationSimuleeInitiale(){
        situationSimulee = new SituationSimulee(new ArrayList<>(),mainInitiale, distributions, this);
    }

    /**
     * met a jour la situation simulée en y ajoutant l'action a
     * @param a la dernière action effectuée
     */
    public void mettreAJourSituationSimulee(Action a){
        situationSimulee = situationSimulee.getSuccesseur(a);
    }

    public SituationSimulee getSituationSimulee() {
        return situationSimulee;
    }

}
