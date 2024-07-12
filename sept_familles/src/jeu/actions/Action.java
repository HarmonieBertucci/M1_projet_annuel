package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

import java.util.List;

/**
 * Interface des actions possibles dans le jeu, conçue pour être exploitable par l'IA MinMax.
 * @author Luc Le Gaillard
 */
public interface Action {

    /**
     * Retourne l'état du jeu dans lequel l'action est possible.
     * @return Un état du jeu
     */
    EtatsJeu etatActuel();

    /**
     * Retourne l'état du jeu à l'issue de l'action.
     * @return Un état du jeu
     */
    EtatsJeu etatSuivant();

    /**
     * Effectue l'action sur une situation complète donnée et renvoie la situation résultante.
     * @param sc La situation à laquelle l'action doit être appliquée
     * @return L'unique situation déterministe résultante de l'action
     * @see sept_familles.jeu.SituationComplete#getSuccesseur(Action) 
     */
    SituationComplete executer(SituationComplete sc);

    /**
     * Vérifie si l'action est possible pour un ensemble de cartes distribuées et selon un joueur courant
     * @param d la distribution sur laquelle on doit faire la vérification
     * @param joueurCourant le joueur qui souhaite effectuer l'action
     * @return true si l'action est possible dans la situation donnée, false sinon.
     * @see Distribution#estCoherenteSelon(List)
     */
    boolean verifier(Distribution d, Joueur joueurCourant);

    /**
     * Effectue l'action sur un ensemble de cartes et de joueurs, sans vérifier si l'action est possible.
     * Selon les implémentations, cette méthode peut être vide.
     * @param d la distribution devant être modifiée
     * @see Distribution#estCoherenteSelon(List)
     */
    void simuler(Distribution d);

    /**
     * Indique si à l'issue de l'action, on doit passer au joueur suivant.
     * @return true si l'action doit passer au joueur suivant, false sinon
     */
    boolean doitPasserAuJoueurSuivant();

    /**
     * Retourne le joueur courant qui souhaite effectuer l'action.
     * @return Le joueur courant
     */
    Joueur getJoueurCourant();
}
