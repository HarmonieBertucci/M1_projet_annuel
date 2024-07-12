package sept_familles.main;

import sept_familles.jeu.Partie;
import sept_familles.jeu.PartieAffichee;

public class Main {

	public static void main(String[] args) {

		int nbJoueurs = 2;
		// Familles du jeu de base
		String[] familles = {"Chat", "Chien", "Lapin", "Hérisson", "Serpent", "Lion", "Aigle"};
		String[] membres = {"Grand-père", "Grand-mère", "Père", "Mère", "Fils", "Fille"};

		// Familles pour tester et réduire les temps de calcul
		String[] testFamilles = {"1", "2", "3"};
		String[] testMembres = {"A", "B", "C"};

		//Partie partie = new Partie(nbJoueurs, testFamilles, testMembres);
		Partie partie = new PartieAffichee(nbJoueurs, testFamilles, testMembres);
		partie.lancer();
	}
}
