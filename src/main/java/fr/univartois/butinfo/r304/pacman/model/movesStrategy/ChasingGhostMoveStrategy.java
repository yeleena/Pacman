package fr.univartois.butinfo.r304.pacman.model.movesStrategy;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;

/**
 * Stratégie : le fantôme "chasse" Pac-Man en suivant sa direction.
 *
 *
 * @author Killian
 */
public class ChasingGhostMoveStrategy implements GhostMoveStrategy {

    @Override
    public boolean move(Fantomes ghost, long duree) {
        ghost.superStep(duree);

        // Récupère le joueur
        IAnimated player = ghost.getGame().getPlayer();
        if (player == null) return false;

        double ghostX = ghost.getX();
        double ghostY = ghost.getY();
        double playerX = player.getX();
        double playerY = player.getY();

        double dx = playerX - ghostX;
        double dy = playerY - ghostY;

        double speed = 150.0;

        // Déplace le fantôme vers Pac-Man
        if (Math.abs(dx) > Math.abs(dy)) {
            ghost.setHorizontalSpeed(dx > 0 ? speed : -speed);
            ghost.setVerticalSpeed(0);
        } else {
            ghost.setVerticalSpeed(dy > 0 ? speed : -speed);
            ghost.setHorizontalSpeed(0);
        }

        return true;
    }
}
