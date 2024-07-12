package sept_familles.jeu;

import sept_familles.jeu.actions.Action;

import java.util.Set;

/**
 * Une situation est un état du jeu,
 * qui contient des informations sur le jeu à un instant donné.
 * Suivant les deux implémentations, une situation peut être complète ou simulée.
 * Dans ce dernier cas, elle ne contient pas toutes les informations du jeu.
 * @see SituationComplete
 * @see sept_familles.ia.SituationSimulee
 * @author Harmonie Bertucci
 */
public interface Situation {

    /**
     * Renvoie l'ensemble des actions possibles dans le contexte de la situation actuelle.
     * @return l'ensemble des actions possibles
     */
    Set<Action> getActions();

    /**
     * Renvoie de manière déterministe la situation suivant
     * l'exécution de l'action donnée sur la situation actuelle.
     * @param ac l'action à exécuter
     * @return la situation suivante
     */
    Situation getSuccesseur(Action ac);

    /**
     * Indique si la situation actuelle est une situation terminale,
     * c'est-à-dire si le jeu est terminé.
     * @return true si la situation est terminale, false sinon
     */
    boolean estTerminale();
}
