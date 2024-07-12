package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

/**
 * Action qui permet de dire que la carte piochée n'est pas celle demandée.
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class AnnoncerMauvaisePioche implements Action {

    private final Joueur joueurSource;
    private final Carte cartePiochee;
    private final Piocher piocher;

    /**
     * Construit une action d'annonce de mauvaise pioche.
     * @param joueurSource Le joueur qui a pioché
     * @param cartePiochee La carte piochée
     * @param piocher L'action précedente piocher
     */
    public AnnoncerMauvaisePioche(Joueur joueurSource, Carte cartePiochee, Piocher piocher) {
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
    public SituationComplete executer(SituationComplete sc){
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());
        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    @Override
    public boolean verifier(Distribution d,Joueur joeurCourant) {
        return
                !cartePiochee.equals(piocher.getDemander().getCarteDemandee()) &&
                d.getMains().get(joueurSource).contains(cartePiochee);
    }

    /**
     * {@inheritDoc}
     * Ici, on ne fait rien car l'annonce de mauvaise pioche ne modifie pas la situation.
     */
    @Override
    public void simuler(Distribution d) {}

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return true;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueurSource;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueurSource + " annonce une mauvaise pioche. Il a pioché la carte : " + cartePiochee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnoncerMauvaisePioche amp = (AnnoncerMauvaisePioche) o;
        return joueurSource.equals(amp.joueurSource) && cartePiochee.equals(amp.cartePiochee) && piocher.equals(amp.piocher);
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
