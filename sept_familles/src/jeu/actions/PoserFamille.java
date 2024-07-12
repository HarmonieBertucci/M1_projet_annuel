package sept_familles.jeu.actions;

import sept_familles.ia.Distribution;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.SituationComplete;

import java.util.*;

/**
 * Action de poser une famille pour un joueur
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class PoserFamille implements Action {

    private final Joueur joueur;
    private final String famille;

    public PoserFamille(Joueur joueur, String famille) {
        this.joueur = joueur;
        this.famille = famille;
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
    public SituationComplete executer(SituationComplete sc)  {
        SituationComplete situationSuivante = sc.clone();
        situationSuivante.setEtatCourant(etatSuivant());

        situationSuivante.getFamillesPosees().put(famille, joueur);
        List<Carte> trashbin = new ArrayList<>();
        for (Carte c: situationSuivante.getMain(joueur))
            if (c.famille().equals(famille))
                trashbin.add(c);
        situationSuivante.getMain(joueur).removeAll(trashbin);

        situationSuivante.joueurSuivant();
        situationSuivante.setDerniereAction(this);
        return situationSuivante;
    }

    @Override
    public boolean verifier(Distribution d,Joueur joueurCourant) { // it just works (somehow)
        /*if (!joueurCourant.equals(joueur)){
            return false;
        }*/
        for (Carte c : d.getPioche())
            if (c.famille().equals(famille))
                return false;
        for (Map.Entry<Joueur, List<Carte>> entry : d.getMains().entrySet())
            if (!entry.getKey().equals(joueur))
                for (Carte c : entry.getValue())
                    if (c.famille().equals(famille))
                        return false;
        return true;
    }

    @Override
    public void simuler(Distribution d) {
        List<Carte> trashbin = new ArrayList<>();
        for (Carte c: d.getMains().get(joueur))
            if (c.famille().equals(famille))
                trashbin.add(c);
        d.getMains().get(joueur).removeAll(trashbin);
    }

    @Override
    public boolean doitPasserAuJoueurSuivant() {
        return true;
    }

    @Override
    public Joueur getJoueurCourant() {
        return joueur;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public String getFamille() {
        return famille;
    }

    @Override
    public String toString(){
        return "Le joueur " + joueur + " a choisit de poser la famille " + famille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoserFamille pf = (PoserFamille) o;
        return joueur.equals(pf.joueur) && famille.equals(pf.famille);
    }

}
