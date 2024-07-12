package sept_familles.ia;

import sept_familles.ia.minmax.Vecteur;
import sept_familles.jeu.*;
import sept_familles.jeu.actions.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe reproduisant l'information incomplète.
 * C'est-à-dire la situation de jeu perçue par un joueur donné,
 * limitée par des informations partielles (main d'origine et actions effectuées).
 * @author Harmonie Bertucci
 */
public class SituationSimulee implements Situation {

    /**
     * Historique des actions effectuées depuis le début de la partie
     */
    private final List<Action> historiqueCoups;
    /**
     * Main d'origine du joueur à l'issue de la distribution initiale des cartes
     */
    private final List<Carte> main;
    /**
     * Liste de toutes les distributions initiales possibles
     */
    private final List<Distribution> distributions;
    /**
     * Liste des distributions initiales possibles à l'instant actuel
     * Calculée à partir de la main d'origine suivant les distributions et des actions réalisées
     */
    private final List<Distribution> distributionsPossibles;
    /**
     * Joueur comme référence dans
     */
    private final Joueur joueur;

    /**
     * Constructeur de la classe SituationSimulee
     * @param historique historique des actions effectuées depuis le début de la partie
     * @param main main d'origine du joueur
     * @param distributions liste de toutes les distributions initiales possibles
     * @param joueur joueur concerné par la situation
     */
    public SituationSimulee(
            List<Action> historique,
            List<Carte> main,
            List<Distribution> distributions,
            Joueur joueur
    ) {
        this.historiqueCoups = historique;
        this.main = main;
        this.distributions = distributions;
        this.joueur = joueur;
        distributionsPossibles = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     * Renvoie les actions réalisables dans les situations complètes cohérentes avec la situation simulée.
     * @return toutes les actions possiblement réalisables
     * @see #getSituationsCompletes()
     */
    @Override
    public Set<Action> getActions() {
        Set<Action> actions = new HashSet<>();
        // Parcours des situations complètes cohérentes
        for (SituationComplete sc : getSituationsCompletes())
            actions.addAll(sc.getActions());
        return actions;
    }

    /**
     * {@inheritDoc}
     * La situation simulée suivante se calcule en mettant à jour l'historique des actions.
     */
    @Override
    public SituationSimulee getSuccesseur(Action ac) {
        List<Action> historique = new ArrayList<>(historiqueCoups);
        // Mise à jour du nouvel historique des coups
        historique.add(ac);
        return new SituationSimulee(historique, main, distributions, joueur);
    }

    /**
     * {@inheritDoc}
     * Une situation simulée est terminale lorsque toutes les situations complètes cohérentes associées sont également terminales.
     */
    @Override
    public boolean estTerminale() {
        for (SituationComplete sc : getSituationsCompletes())
            if (!sc.estTerminale())
                return false;
        return true;
    }

    /**
     * Renvoie l'ensemble des situations cohérentes pour chaque distribution initiale coéhrente avec l'historique des coups.
     * @return les situations complètes cohérentes
     */
    public List<SituationComplete> getSituationsCompletes() {
        List<SituationComplete> situations = new ArrayList<>();
        //on vide la liste des distributions initiales possibles pour les recalculer
        distributionsPossibles.clear();
        //on parcours toutes les distributions
        for (Distribution d : distributions) {
            //si l'historique des actions est cohérent avec la distribution initiale
            if (d.verifierSelonMain(joueur) && d.estCoherenteSelon(this.historiqueCoups)) {
                //on l'ajoutea la liste des distributions possibles
                distributionsPossibles.add(d);
                // on crée une situation complète en lui donnant la distribution
                SituationComplete sc = new SituationComplete(d);
                //on rejoue les actions sur la situation créée pour que les mains et la pioche soient a jour
                for (Action a : historiqueCoups)
                    sc = a.executer(sc);
                //on ajoute la situation complète a la liste a renvoyer
                situations.add(sc);
            }
        }
        return situations;
    }

    /**
     * Appelé pour récupérer le vecteur final une fois que toutes les situations complètes sont terminales
     * @return un vecteur de 1 ou 0 selon chaque distribution
     */
    public Vecteur getFinalVector(){
        //TODO
        return null;
    }

    public List<Distribution> getDistributionsPossibles() {
        return distributionsPossibles;
    }
}
