package sept_familles.ia.minmax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un vecteur de valeurs entières dénotant chacune une distribution de cartes.
 * Les opérations sur ensembles de vecteurs sont implémentées comme méthodes statiques.
 * @author Luc Le Gaillard
 */
public class Vecteur extends ArrayList<Integer> {

    /**
     * Ajoute un entier au vecteur
     * @param i L'entier à ajouter
     * @throws IllegalArgumentException si l'entier est différent de 0 et de 1.
     */
    @Override
    public boolean add(Integer i) throws IllegalArgumentException {
        if (i != 0 && i != 1)
            throw new IllegalArgumentException("Vecteur.add(Integer i) : i doit être 0 ou 1");
        return super.add(i);
    }

    /**
     * Renvoie une copie du vecteur
     * @return Une copie du vecteur
     */
    @Override
    public Vecteur clone() {
        Vecteur v = new Vecteur();
        v.addAll(this);
        return v;
    }

    /**
     * Détermine si le vecteur est strictement inférieur à un autre vecteur.
     * Dans ce cas, le vecteur est dit "dominé" par l'autre et est donc négligeable.
     * @param v Le vecteur à comparer
     * @throws IllegalArgumentException Si les deux vecteurs n'ont pas la même taille
     * @return true si le vecteur est strictement inférieur à l'autre, false sinon
     */
    public boolean domine(Vecteur v) throws IllegalArgumentException {
        if (this.size() != v.size())
            throw new IllegalArgumentException("Les vecteurs doivent avoir la même taille");
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) < v.get(i))
                return false;
        return true;
    }

    /**
     * Réalise l'union du vecteur courant avec le vecteur passé en paramètre.
     * Utilisé dans le cas 'max' de l'algorithme MinMax.
     * @param v Le vecteur à ajouter
     * @throws IllegalArgumentException Si les deux vecteurs n'ont pas la même taille
     */
    public void union(Vecteur v) throws IllegalArgumentException {
        if (v.size() != this.size())
            throw new IllegalArgumentException("Les vecteurs doivent être de même taille");
        for (int i = 0; i < this.size(); i++)
            this.set(i, this.get(i) + v.get(i) > 0 ? 1 : 0);
    }

    /**
     * Réalise l'intersection du vecteur courant avec le vecteur passé en paramètre.
     * Utilisé dans le calcul du produit cartésien de deux ensembles de vecteurs.
     * @param v Le vecteur avec lequel on effectue l'intersection
     * @throws IllegalArgumentException Si les deux vecteurs n'ont pas la même taille
     */
    public void intersection(Vecteur v) throws IllegalArgumentException {
        if (v.size() != this.size())
            throw new IllegalArgumentException("Les vecteurs doivent être de même taille");
        for (int i = 0; i < this.size(); i++)
            this.set(i, this.get(i) * v.get(i));
    }

    /**
     * Réalise le produit cartésien de deux ensembles de vecteurs.
     * Utilisé dans le cas 'min' de l'algorithme MinMax.
     * @param s1 Le premier ensemble de vecteurs
     * @param s2 Le second ensemble de vecteurs
     * @throws IllegalArgumentException Si deux vecteurs n'ont pas la même taille
     * @return L'ensemble des vecteurs résultant du produit cartésien
     */
    public static Set<Vecteur> produitCartesien(Set<Vecteur> s1, Set<Vecteur> s2) throws IllegalArgumentException {
        Set<Vecteur> res = new HashSet<>();
        for (Vecteur v1 : s1)
            for (Vecteur v2 : s2) {
                Vecteur v = v1.clone();
                v.intersection(v2);
                res.add(v);
            }
        return res;
    }

    /**
     * Réalise l'union de deux ensembles de vecteurs.
     * Utilisé dans le cas 'max' de l'algorithme MinMax.
     * @param s1 Le premier ensemble de vecteurs
     * @param s2 Le second ensemble de vecteurs
     * @throws IllegalArgumentException Si deux vecteurs n'ont pas la même taille
     * @return L'ensemble des vecteurs résultant de l'union
     */
    public static Set<Vecteur> union(Set<Vecteur> s1, Set<Vecteur> s2) {
        Set<Vecteur> res = new HashSet<>();
        res.addAll(s1);
        res.addAll(s2);
        return res;
    }

    /**
     * Réduit un ensemble de vecteurs en supprimant les vecteurs dominés
     * @param s L'ensemble de vecteurs
     * @throws IllegalArgumentException Si deux vecteurs n'ont pas la même taille
     * @return L'ensemble des vecteurs réduit
     */
    public static Set<Vecteur> reduire(Set<Vecteur> s) throws IllegalArgumentException {
        Set<Vecteur> trash = new HashSet<>(s);
        for (Vecteur v1 : s)
            for (Vecteur v2 : s)
                if (!v1.equals(v2))
                    if (v1.domine(v2))
                        trash.add(v2);
        Set<Vecteur> res = new HashSet<>(s);
        res.removeAll(trash);
        return res;
    }
}
