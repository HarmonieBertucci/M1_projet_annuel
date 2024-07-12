package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

import java.util.*;

/**
 * Action de ne pas poser de famille pour un joueur
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class NePasPoserFamille implements Action {

    private Action actionPrecedente;
    private Joueur joueur;

    public NePasPoserFamille(Action actionPrecedente, Joueur joueur) {
        this.actionPrecedente = actionPrecedente;
        this.joueur = joueur;
    }
    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.POSER_FAMILLE;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.DEMANDER;
    }

    @Override
    public SituationComplete executer(SituationComplete sc) {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());

        if (doitPasserAuJoueurSuivant())
            situationSuivante.joueurSuivant();

        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    /**
     * Regarde si le joueur courant ne peut pas poser de famille
     * @param d la distribution sur laquelle on doit faire la vérification
     * @param joueurCourant le joueur qui souhaite effectuer l'action
     * @return true si le joueur courant n'a pas de famille complète dans sa main, false sinon
     */
    @Override
    public boolean verifier(Distribution d, Joueur joueurCourant) {
        // On regarde toutes les familles dans la main du joueur courant
        Set<String> famillesJoueurs = new HashSet<>();
        for (Carte c : d.getMains().get(joueurCourant))
            famillesJoueurs.add(c.famille());
        // On récupère les familles des autres joueurs et de la pioche
        Set<String> famillesAutres = new HashSet<>();
        for (Carte c : d.getPioche())
            famillesAutres.add(c.famille());
        for (Joueur j : d.getMains().keySet()) {
            if (!j.equals(joueurCourant))
                for (Carte c : d.getMains().get(j))
                    famillesAutres.add(c.famille());
        }

        // Si la pioche ou les mains des autres joueurs contiennent
        // au moins une carte de chaque famille du joueur courant,
        // celui-ci ne peut pas poser de famille
        return famillesAutres.containsAll(famillesJoueurs);
    }

    @Override
    public void simuler(Distribution d) {}

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return actionPrecedente.doitPasserAuJoueurSuivant();
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueur;
    }

    public Action getActionPrecedente() {
        return actionPrecedente;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueur + " a choisit de ne pas poser de famille.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NePasPoserFamille nppf = (NePasPoserFamille) o;
        return actionPrecedente.equals(nppf.actionPrecedente) && joueur.equals(nppf.joueur);
    }

}
