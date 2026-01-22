package fr.univartois.butinfo.r304.pacman.model.movesStrategy;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;

/**
 * Stratégie : le fantôme "fuit" Pac-Man en s'éloignant de lui sur l'axe dominant.
 *
 *
 * @author Killian
 */
public class FleeingGhostMoveStrategy implements GhostMoveStrategy {

    @Override
    public boolean move(Fantomes ghost, long duree) {
        // Avance selon sa vitesse actuelle
        ghost.superStep(duree);

        // Récupère la référence au joueur via le jeu
        IAnimated player = ghost.getGame().getPlayer();
        if (player == null) return false;

        // Coordonnées du fantôme et du joueur
        double ghostX = ghost.getX();
        double ghostY = ghost.getY();
        double playerX = player.getX();
        double playerY = player.getY();

        // Calcule les différences
        double dx = playerX - ghostX;
        double dy = playerY - ghostY;

        double speed = 150.0;

        // Le fantôme fuit : il se déplace à l'opposé de Pac-Man
        if (Math.abs(dx) > Math.abs(dy)) {
            // S'éloigne horizontalement
            ghost.setHorizontalSpeed(dx > 0 ? -speed : speed);
            ghost.setVerticalSpeed(0);
        } else {
            // S'éloigne verticalement
            ghost.setVerticalSpeed(dy > 0 ? -speed : speed);
            ghost.setHorizontalSpeed(0);
        }

        return true;
    }
}
