package fr.univartois.butinfo.r304.pacman.model.movesStrategy;

import java.util.Random;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;

/**
 * Mouvement aléatoire des fantomes.
 */
public class RandomGhostMoveStrategy implements GhostMoveStrategy {
    
    private static final double DEFAULT_SPEED = 50.0;
    private final double speed;
    
    public RandomGhostMoveStrategy() {
        this(DEFAULT_SPEED);
    }
    
    public RandomGhostMoveStrategy(double speed) {
        this.speed = speed;
    }
    
    @Override
    public boolean move(Fantomes ghost, long duree) {
        boolean moved = ghost.superStep(duree);
        ghost.incrementDernierChangement(duree);
        
        if (ghost.getDernierChangement() >= ghost.getChangerDelai()) {
            Random random = new Random();
            int sign = random.nextBoolean() ? 1 : -1;
            
            if (random.nextBoolean()) {
                ghost.setHorizontalSpeed(speed * sign);
                ghost.setVerticalSpeed(0);
            } else {
                ghost.setVerticalSpeed(speed * sign);
                ghost.setHorizontalSpeed(0);
            }
            ghost.resetDernierChangement();
        }
        return moved;
    }
}