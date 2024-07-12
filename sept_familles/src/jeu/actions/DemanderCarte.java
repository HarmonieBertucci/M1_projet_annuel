package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

import java.util.*;

/**
 * Action qui permet a un joueur source de demander une carte a un joueur cible
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class DemanderCarte implements Action {

    private final Joueur joueurSource;
    private final Joueur joueurCible;
    private final Carte carteDemandee;

    /**
     * Construit une action de demande de carte.
     * @throws IllegalArgumentException si le joueur source et le joueur cible sont les mêmes
     */
    public DemanderCarte(Joueur joueurSource, Joueur joueurCible, Carte carteDemandee) {
        this.joueurSource = joueurSource;
        this.joueurCible = joueurCible;
        this.carteDemandee = carteDemandee;
    }

    @Override
    public String toString() {
        return "Le joueur " + joueurSource.getNom() + " demande à " + joueurCible.getNom() + " la carte " + carteDemandee;
    }

    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.DEMANDER;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.REPONDRE;
    }

    @Override
    public SituationComplete executer(SituationComplete sc){
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());
        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    @Override
    public boolean verifier(Distribution d,Joueur joeurCourant) {
        Set<String> familles = new HashSet<>();
        for (Carte c : d.getMains().get(joueurSource))
            familles.add(c.famille());
        return
                !joueurSource.equals(joueurCible) &&
                familles.contains(carteDemandee.famille()) &&
                d.getMains().containsKey(joueurCible) &&
                !d.getMains().get(joueurSource).contains(carteDemandee);
    }

    /**
     * {@inheritDoc}
     * Ici, la simulation d'une demande de carte n'a besoin d'aucune modification des données du jeu.
     */
    @Override
    public void simuler(Distribution d) {}

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return false;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueurSource;
    }

    public Joueur getJoueurSource() {
        return joueurSource;
    }

    public Joueur getJoueurCible() {
        return joueurCible;
    }

    public Carte getCarteDemandee() {
        return carteDemandee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemanderCarte dc = (DemanderCarte) o;
        return joueurSource.equals(dc.joueurSource) && joueurCible.equals(dc.joueurCible) && carteDemandee.equals(dc.carteDemandee);
    }

}
