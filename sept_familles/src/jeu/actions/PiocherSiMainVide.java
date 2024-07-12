package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

/**
 * Actiion pour un joueur de piocher une carte si sa main est vide
 * Cette action se glisse avant une demande de carte si le joueur en a besoin
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class PiocherSiMainVide implements Action {

    private final Joueur joueur;

    public PiocherSiMainVide(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.DEMANDER;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.DEMANDER;
    }

    @Override
    public SituationComplete executer(SituationComplete sc)  {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());

        situationSuivante.getMain(joueur).add(situationSuivante.getPioche().remove(0));

        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }


    @Override
    public boolean verifier(Distribution d, Joueur joueurCourant) {
        return
                        d.getPioche().size() > 0 &&
                        d.getMains().get(joueur).isEmpty();
    }

    @Override
    public void simuler(Distribution d) {
        d.getMains().get(joueur).add(d.getPioche().remove(0));
    }

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return false;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueur;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueur + " n'a plus de cartes en main : il pioche.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiocherSiMainVide psmv = (PiocherSiMainVide) o;
        return joueur.equals(psmv.joueur);
    }

}
