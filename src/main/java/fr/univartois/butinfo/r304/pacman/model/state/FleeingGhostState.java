package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * La classe FleeingGhostState
 * État intermédiaire (ex: fuite temporaire)
 *
 * @author enzo.lebas
 * @version 0.1.0
 */
public class FleeingGhostState extends AbstractGhostState {
    
    private static final long FLEE_DURATION = 3000; 

    @Override
    public void onEnter(Fantomes ghost) {
        ghost.setSprite(ghost.getGame().getSpriteStore().getSprite("ghosts/afraid/1"));
        ghost.setBaseSpeed(55.0);

        // Quand les 3 secondes sont finies, on force le changement d'état.
        PauseTransition timer = new PauseTransition(Duration.millis(FLEE_DURATION));
        
        timer.setOnFinished(e -> {
            // Ce setState qui va déclencher le onLeave() plus bas
            ghost.setState(new GhostInvulnerableState());
        });
        
        timer.play();
    }

    @Override
    public void onCollideWithPacman(Fantomes ghost, Pacman pacman) {
        // Pac-Man ne peut pas re-manger le fantôme dans cet état
    }

    @Override
    public void onLeave(Fantomes ghost) {
        ghost.setBaseSpeed(50.0); // Retour à la vitesse normale
    }
}