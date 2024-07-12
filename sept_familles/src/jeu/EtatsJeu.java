package sept_familles.jeu;

/**
 * Représente les différents états possibles au cour d'un tour de jeu de Sept Familles
 * @see sept_familles.jeu.actions.Action
 * @author Luc Le Gaillard
 */
public enum EtatsJeu {

    /**
     * État initial au début d'un tour de jeu
     * @see sept_familles.jeu.actions.DemanderCarte
     * @see sept_familles.jeu.actions.PiocherSiMainVide
     */
    DEMANDER,
    /**
     * État de jeu après avoir demandé une carte à un joueur
     * @see sept_familles.jeu.actions.DonnerCarte
     * @see sept_familles.jeu.actions.Piocher
     */
    REPONDRE,
    /**
     * État de jeu suivant une action de pioche
     * @see sept_familles.jeu.actions.AnnoncerBonnePioche
     * @see sept_familles.jeu.actions.AnnoncerMauvaisePioche
     */
    ANNONCER,
    /**
     * Dernier état de jeu d'un tour, permet de poser ou non une famille
     * @see sept_familles.jeu.actions.PoserFamille
     * @see sept_familles.jeu.actions.NePasPoserFamille
     */
    POSER_FAMILLE
}
