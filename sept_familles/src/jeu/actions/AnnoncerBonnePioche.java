package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

/**
 * Action qui permet de dire que la carte piochée est celle demandée.
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class AnnoncerBonnePioche implements Action {
    private final Joueur joueurSource;
    private final Carte cartePiochee;
    private final Piocher piocher;

    /**
     * Construit une action d'annonce de bonne pioche.
     * @param joueurSource Le joueur qui a pioché
     * @param cartePiochee La carte piochée
     * @param piocher L'action précedente piocher
     */
    public AnnoncerBonnePioche(Joueur joueurSource, Carte cartePiochee, Piocher piocher) {
        this.joueurSource = joueurSource;
        this.cartePiochee = cartePiochee;
        this.piocher = piocher;
    }

    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.ANNONCER;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.POSER_FAMILLE;
    }

    @Override
    public SituationComplete executer(SituationComplete sc) {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());
        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    @Override
    public boolean verifier(Distribution d,Joueur joeurCourant) {
        return cartePiochee.equals(piocher.getDemander().getCarteDemandee()) &&
                d.getMains().get(joueurSource).contains(cartePiochee);
    }

    /**
     * {@inheritDoc}
     * Ici, il n'y a rien à faire, car annoncer une bonne pioche ne modifie ni la pioche ni les mains des joueurs.
     */
    @Override
    public void simuler(Distribution d) {}

    /**
     * {@inheritDoc}
     * Concrètement, l'annonce de la bonne pioche ne fait pas passer au joueur suivant.
     * C'est la pose effective ou non d'une famille qui fait passer au joueur suivant dans ce cas précis.
     * @return False
     */
    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return false;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueurSource;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueurSource + " annonce une bonne pioche.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnoncerBonnePioche abp = (AnnoncerBonnePioche) o;
        return joueurSource.equals(abp.joueurSource) && cartePiochee.equals(abp.cartePiochee) && piocher.equals(abp.piocher);
    }

    public Joueur getJoueurSource() {
        return joueurSource;
    }

    public Carte getCartePiochee() {
        return cartePiochee;
    }

    public Piocher getPiocher() {
        return piocher;
    }

}
