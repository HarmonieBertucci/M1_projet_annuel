package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

/**
 * Action de piocher une carte pour un joueur suite à une demande de carte ayant échoué
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class Piocher implements Action {

    private final Joueur joueur;
    private final DemanderCarte demander;


    public Piocher(Joueur joueur, DemanderCarte demander) {
        this.joueur = joueur;
        this.demander = demander;
    }

    @Override
    public EtatsJeu etatActuel() {
        return EtatsJeu.REPONDRE;
    }

    @Override
    public EtatsJeu etatSuivant() {
        return EtatsJeu.ANNONCER;
    }

    @Override
    public SituationComplete executer(SituationComplete sc)  {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());

        situationSuivante.getMain(joueur).add(situationSuivante.getPioche().remove(0));

        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    /**
     * Ici pour avoir une action Piocher, il faut que l'adversaire n'ait pas la carte demandée pendant l'action précédente
     * @param d la distribution sur laquelle on doit faire la vérification
     * @param joueurCourant Le joueur courant qui souhaite effectuer l'action
     * @return si l'action est possible à cet instant du jeu
     */
    @Override
    public boolean verifier(Distribution d, Joueur joueurCourant) {
        return
                d.getPioche().size() > 0 &&
                !d.getMains().get(joueur).contains(demander.getCarteDemandee());
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

    public Joueur getJoueur(){
        return joueur;
    }

    public DemanderCarte getDemander(){
        return demander;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueur + " pioche (son adversaire lui a dit qu'il n'avait pas la carte demandée).";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piocher piocher = (Piocher) o;
        return joueur.equals(piocher.joueur) && demander.equals(piocher.demander);
    }

}
