package sept_familles.jeu.actions;

import sept_familles.jeu.Carte;
import sept_familles.jeu.Joueur;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet de créer des actions uniques (sans recréer plusieurs fois une mee action)
 *
 * Les méthodes de création d'action sont statiques :
 * À l'intérieur, on parcourt la liste d'actions déjà existantes
 * si on tombe sur l'action que l'on voulait créer (si les paramètres sont les mêmes) on renvoie l'action
 * sinon on crée l'action, on l'ajoute à la liste d'action correspondante et on la renvoie
 *
 * @author Harmonie Bertucci
 */
public class ActionFactory {

    static List<AnnoncerBonnePioche> annoncerBonnePioches = new ArrayList<>();
    static List<AnnoncerMauvaisePioche> annoncerMauvaisePioches = new ArrayList<>();
    static List<DemanderCarte> demanderCartes = new ArrayList<>();
    static List<DonnerCarte> donnerCartes = new ArrayList<>();
    static List<NePasPoserFamille> nePasPoserFamilles = new ArrayList<>();
    static List<Piocher> piochers = new ArrayList<>();
    static List<PiocherSiMainVide> piocherSiMainVides = new ArrayList<>();
    static List<PoserFamille> poserFamilles = new ArrayList<>();

    public static AnnoncerBonnePioche creerAnnoncerBonnePioche(Joueur joueurSource, Carte cartePiochee, Piocher piocher){
        for (AnnoncerBonnePioche abp : annoncerBonnePioches)
            if(abp.getJoueurSource().equals(joueurSource) && abp.getCartePiochee().equals(cartePiochee) && abp.getPiocher().equals(piocher))
                return abp;

        AnnoncerBonnePioche abp = new AnnoncerBonnePioche(joueurSource,cartePiochee,piocher);
        annoncerBonnePioches.add(abp);
        return abp;
    }

    public static AnnoncerMauvaisePioche creerAnnoncerMauvaisePioche(Joueur joueurSource, Carte cartePiochee, Piocher piocher){
        for (AnnoncerMauvaisePioche amp : annoncerMauvaisePioches)
            if(amp.getJoueurSource().equals(joueurSource) && amp.getCartePiochee().equals(cartePiochee) && amp.getPiocher().equals(piocher))
                return amp;

        AnnoncerMauvaisePioche amp = new AnnoncerMauvaisePioche(joueurSource,cartePiochee,piocher);
        annoncerMauvaisePioches.add(amp);
        return amp;
    }

    public static DemanderCarte creerDemanderCarte(Joueur joueurSource, Joueur joueurCible, Carte carteDemandee){
        for (DemanderCarte dc : demanderCartes)
            if (dc.getJoueurSource().equals(joueurSource) && dc.getJoueurCible().equals(joueurCible) && dc.getCarteDemandee().equals(carteDemandee))
                return dc;
        DemanderCarte dc = new DemanderCarte(joueurSource,joueurCible,carteDemandee);
        demanderCartes.add(dc);
        return dc;
    }

    public static DonnerCarte creerDonnerCarte(Joueur joueurSource, Joueur joueurCible, Carte carteADonner, DemanderCarte demande){
        for (DonnerCarte dc : donnerCartes)
            if (dc.getJoueurSource().equals(joueurSource) && dc.getJoueurCible().equals(joueurCible) && dc.getCarteADonner().equals(carteADonner) && dc.getDemande().equals(demande))
                return dc;
        DonnerCarte dc = new DonnerCarte(joueurSource,joueurCible,carteADonner,demande);
        donnerCartes.add(dc);
        return dc;
    }

    public static NePasPoserFamille creerNePasPoserFamille(Action actionPrecedente, Joueur joueur){
        for (NePasPoserFamille nePasPoserFamille : nePasPoserFamilles)
            if(nePasPoserFamille.getActionPrecedente().equals(actionPrecedente) && nePasPoserFamille.getJoueur().equals(joueur))
                return nePasPoserFamille;
        NePasPoserFamille nePasPoserFamille = new NePasPoserFamille(actionPrecedente,joueur);
        nePasPoserFamilles.add(nePasPoserFamille);
        return nePasPoserFamille;
    }

    public static Piocher creerPiocher(Joueur joueur, DemanderCarte demander){
        for(Piocher piocher : piochers)
            if (piocher.getJoueur().equals(joueur) && piocher.getDemander().equals(demander))
                return piocher;
        Piocher piocher = new Piocher(joueur, demander);
        piochers.add(piocher);
        return piocher;
    }

    public static PiocherSiMainVide creerPiocherSiMainVide(Joueur joueur){
        for (PiocherSiMainVide piocherSiMainVide : piocherSiMainVides)
            if (piocherSiMainVide.getJoueur().equals(joueur))
                return piocherSiMainVide;
        PiocherSiMainVide piocherSiMainVide = new PiocherSiMainVide(joueur);
        piocherSiMainVides.add(piocherSiMainVide);
        return piocherSiMainVide;
    }

    public static PoserFamille creerPoserFamille(Joueur joueur, String famille){
        for (PoserFamille poserFamille : poserFamilles)
            if(poserFamille.getJoueur().equals(joueur) && poserFamille.getFamille().equals(famille))
                return poserFamille;
        PoserFamille poserFamille = new PoserFamille(joueur, famille);
        poserFamilles.add(poserFamille);
        return poserFamille;
    }

}
