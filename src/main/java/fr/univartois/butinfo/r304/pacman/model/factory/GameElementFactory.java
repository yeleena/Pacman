package fr.univartois.butinfo.r304.pacman.model.factory;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.map.ILabyrinthe;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.GhostMoveStrategy;

/**
 * Patron de conception : Fabrique Abstraite.
 * Permet de créer les familles d'objets (Carte, Stratégie fantôme, Bonus) pour chaque niveau.
 */
public interface GameElementFactory {

    /**
     * Crée le générateur de carte pour le niveau courant.
     * @return L'interface ILabyrinthe, le contrat que tous les générateurs doivent respecter.
     */
    ILabyrinthe createLabyrinthGenerator(); 

    /**
     * Crée la stratégie de déplacement des fantômes pour le niveau courant.
     * @return La stratégie de mouvement.
     */
    GhostMoveStrategy createGhostMoveStrategy();

    /**
     * Crée la stratégie de gestion des bonus pour le niveau courant.
     * @return La stratégie de bonus.
     */
    IBonus createBonusStrategy();

    /**
     * Obtient la vitesse initiale des fantômes pour ce niveau.
     */
    int getInitialGhostSpeed();

    /**
     * Obtient la fréquence d'apparition des bonus pour ce niveau.
     */
    int getBonusFrequency();
}