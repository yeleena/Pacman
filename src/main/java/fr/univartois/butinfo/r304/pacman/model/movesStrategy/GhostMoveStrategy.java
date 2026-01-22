package fr.univartois.butinfo.r304.pacman.model.movesStrategy;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;

public interface GhostMoveStrategy {
    boolean move(Fantomes ghost, long duree);
}
