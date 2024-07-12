package sept_familles.ia;

import sept_familles.jeu.Carte;
import sept_familles.jeu.EtatsJeu;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.actions.*;

import java.util.*;

/**
 * Modélise une distribution initiale hypothétique de cartes
 * @author Luc Le Gaillard & Harmonie Bertucci
 */
public class Distribution {

    private List<Joueur> joueurs;
    private List<Carte> pioche;
    private Map<Joueur, List<Carte>> mains;
    private List<Carte> ordreInitial;

    /**
     * Constructeur privé utilisé pour le clonage
     */
    private Distribution() {
        joueurs = new ArrayList<>();
        pioche = new ArrayList<>();
        mains = new HashMap<>();
        ordreInitial = new ArrayList<>();
    }

    /**
     * Construit une distribution initiale à partir d'une liste ordonnée de cartes
     * @param distribution Une liste ordonnée de cartes utilisée comme pioche pour la distribution
     * @param joueurs Les joueurs de la partie
     * @param tailleMain La taille de la main de chaque joueur
     */
    public Distribution(List<Carte> distribution, List<Joueur> joueurs, int tailleMain) {
        this();

        this.joueurs = joueurs;
        ordreInitial.addAll(distribution);
        pioche = distribution;

        for (Joueur j : joueurs)
            mains.put(j, new ArrayList<>());

        // Distribution des cartes aux joueurs
        for (int i = 0; i < tailleMain; i++)
            for (Joueur joueur : this.joueurs)
                mains.get(joueur).add(pioche.remove(0));
    }

    @Override
    public Distribution clone() {
        Distribution d = new Distribution();
        d.joueurs = joueurs;
        d.pioche = clonerPioche();
        d.mains = clonerMain();
        d.ordreInitial = clonerCartes();
        return d;
    }

    /**
     * Vérifie si l'historique des actions donné est cohérent avec cette distribution initiale
     * @param historique l'historique des actions
     * @return true si les actions peuvent se produire dans l'ordre indiqué, false sinon
     */
    public boolean estCoherenteSelon(List<Action> historique) {
        // On clone la distribution courante pour la modifier grâce aux actions et vériier sa cohérence
        Distribution dSimulee = clone();

        // Définir le joueur qui commence
        int indexJoueur = 0;
        Joueur jCourant = this.joueurs.get(indexJoueur);

        // Définir l'état initial du jeu
        EtatsJeu etat = EtatsJeu.DEMANDER;

        // Rejouer la partie action par action
        for (Action a : historique) {
            // Si l'action actuelle est cohérente avec l'état du jeu
            if (
                    a.etatActuel() == etat && a.verifier(dSimulee, jCourant)
            ) {
                // Si l'action est possible, on la réalise
                a.simuler(dSimulee);

                etat = a.etatSuivant();
                // On passe au joueur suivant si l'action le requiert
                if (a.etatActuel() == EtatsJeu.POSER_FAMILLE && a.doitPasserAuJoueurSuivant())
                    jCourant = joueurSuivant(indexJoueur++);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Indique si la distribution initiale des cartes est cohérente avec la main initiale du joueur donné
     * @param j le joueur de référence
     * @return true si la distribution est cohérente, false sinon
     */
    public boolean verifierSelonMain(Joueur j) {
        return new HashSet<>(j.getMainInitiale()).containsAll(mains.get(j)) &&
                new HashSet<>(mains.get(j)).containsAll(j.getMainInitiale());
    }

    /**
     * Renvoie les mains des joueurs après les actions données dans l'historique.
     * La méthode renvoie des données utilisées pour l'affichage.
     * @param historique les actions effectuées
     * @return les mains des joueurs après les actions sur la distribution
     * @see sept_familles.jeu.PartieAffichee
     */
    public Map<Joueur, List<Carte>> mainsApresHistorique(List<Action> historique) {
        Distribution dSimulee = clone();

        for (Action a : historique)
            a.simuler(dSimulee);

        return dSimulee.getMains();
    }

    /**
     * Permet de récupérer le joueur suivant dans la liste des joueurs
     * @param index L'index du joueur courant
     * @return Le joueur suivant
     */
    private Joueur joueurSuivant(int index){
        return this.joueurs.get(index % this.joueurs.size());
    }

    /**
     * Renvoie une Map de mains en clonant celle de la distribution
     * @return la nouvelle map
     */
    public HashMap<Joueur, List<Carte>> clonerMain() {
        HashMap<Joueur, List<Carte>> mainsSimulees = new HashMap<>();
        for (Map.Entry<Joueur, List<Carte>> entry : mains.entrySet()) {
            List<Carte> list = new ArrayList<>(entry.getValue());
            mainsSimulees.put(entry.getKey(), list);
        }
        return mainsSimulees;
    }

    /**
     * Renvoie une liste de cartes en clonant la pioche de la distribution
     * @return la nouvelle liste
     */
    public List<Carte> clonerPioche(){
        return new ArrayList<>(pioche);
    }

    /**
     * Renvoie une liste de cartes en clonant l'ordre initial de la pioche de la distribution
     * @return la nouvelle liste
     */
    public List<Carte> clonerCartes(){
        return new ArrayList<>(ordreInitial);
    }

    public List<Carte> getPioche() {
        return pioche;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public List<Carte> getOrdreInitial() {
        return ordreInitial;
    }

    public Map<Joueur, List<Carte>> getMains() {
        return mains;
    }

    @Override
    public String toString() {
        return "Ordre initial : " + ordreInitial;
    }



}
