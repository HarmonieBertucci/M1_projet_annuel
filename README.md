# M1_projet_annuel
(2022-2023) Résolution d'un jeu à information incomplète

Université de Caen-Normandie, projet à 2 (L. Le Gaillard - H. Bertucci)

# Le projet :

Un jeu multi-joueurs à information incomplète est un jeu opposant plusieurs joueurs, mais dans lequel l'état, qui détermine les effets des différents coups, n'est que partiellement connu des deux joueurs (possiblement, pas connu de la même manière par les différents joueurs). Des exemples typiques sont les jeux de cartes (belote, bridge, etc.) et des jeux de société tels que le Cluedo, le jeu des 7 familles, etc. En effet, dans de tels jeux, l'état est la distribution complète des cartes (et éventuellement l'ordre de la pioche), or chacun ne connaît au départ que sa propre main, et obtient des indices sur les mains des autres joueurs au fur et à mesure de la partie.

On s'intéresse dans ce projet à la résolution de tels jeux, dans un cadre adversarial. Dans sa thèse, Junkang Li, en étendant des travaux de la littérature scientifique, propose un algorithme basé sur l'algorithme min-max, en incluant des coupes alpha-beta et une gestion de cache dédiée à ce cadre général de l'information incomplète. Le premier objectif de ce projet est d'implémenter cet algorithme sur un jeu au choix.

Un second objectif serait de mener des expériences sur la résolution de tels jeux, dans un cadre où plusieurs joueurs jouent chacun pour soi (comme dans l'exemple du jeu des 7 familles). Il s'agirait en particulier d'étudier l'effet de deux hypothèses : jouer en considérant que chaque adversaire cherche à maximiser sa propre récompense, ou jouer en considérant que tous les autres joueurs sont contre soi.
