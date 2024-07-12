package sept_familles.ia;

import sept_familles.jeu.Partie;
import sept_familles.jeu.Joueur;
import sept_familles.jeu.actions.Action;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Permet de représenter un joueur (ordinateur) jouant aléatoirement
 */
public class IAAleatoire extends Joueur {

    private final Random random;

    public IAAleatoire(String nom, Partie partie) {
        super(nom, "IA à choix aléatoires", partie);
        this.random = new Random();
    }

    @Override
    public Action choisirAction() {
        List<Action> actions = partie.getActions().stream().collect(Collectors.toList());
        if (actions.isEmpty())
            return null;

        return actions.get(random.nextInt(actions.size()));
    }
}
