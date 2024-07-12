package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

/**
 * Action permettant à un joueur source de donner une carte à un joueur cible suite à une demande précédente.
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class DonnerCarte implements Action {

    private final Joueur joueurSource;
    private final Joueur joueurCible;
    private final Carte carteADonner;

    private final DemanderCarte demande;

    public DonnerCarte(Joueur joueurSource, Joueur joueurCible, Carte carteADonner, DemanderCarte demande) {
        this.joueurSource = joueurSource;
        this.joueurCible = joueurCible;
        this.carteADonner = carteADonner;
        this.demande = demande;
    }

    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.REPONDRE;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.POSER_FAMILLE;
    }

    @Override
    public SituationComplete executer(SituationComplete sc) {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());

        situationSuivante.getMain(joueurSource).remove(carteADonner);
        situationSuivante.getMain(joueurCible).add(carteADonner);

        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    @Override
    public boolean verifier(Distribution d,Joueur joeurCourant) {
        return d.getMains().get(joueurSource).contains(carteADonner) &&
                joueurCible.equals(demande.getJoueurSource()) &&
                joueurSource.equals(demande.getJoueurCible()) &&
                demande.getCarteDemandee().equals(carteADonner);
    }

    /**
     * Ici simuler retire la carte a donner de la main du joueur source et la rajoute dans la main du joueur cible
     * @param pioche La pioche du jeu
     * @param mains Les mains des différents joueurs
     */
    @Override
    public void simuler(Distribution d) {
        d.getMains().get(joueurSource).remove(carteADonner);
        d.getMains().get(joueurCible).add(carteADonner);
    }

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return false;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueurCible;
    }

    public Joueur getJoueurSource() {
        return joueurSource;
    }

    public Joueur getJoueurCible() {
        return joueurCible;
    }

    public Carte getCarteADonner() {
        return carteADonner;
    }

    public DemanderCarte getDemande(){
        return demande;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueurSource + " donne la carte " + carteADonner + " au joueur " + joueurCible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonnerCarte dc = (DonnerCarte) o;
        return joueurSource.equals(dc.joueurSource) && joueurCible.equals(dc.joueurCible) && carteADonner.equals(dc.carteADonner) && demande.equals(dc.demande);
    }

}
