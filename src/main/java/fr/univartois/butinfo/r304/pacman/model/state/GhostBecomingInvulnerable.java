package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * La classe GhostBecomingInvulnerable
 * État de transition avant de redevenir dangereux.
 *
 * @author enzo.lebas
 * @version 0.1.0
 */
public class GhostBecomingInvulnerable extends AbstractGhostState {

    private static final long TRANSITION_DURATION = 2000; // 2 secondes

    @Override
    public void onEnter(Fantomes ghost) {
        String color = ghost.getCouleur().name().toLowerCase();
        ghost.setSprite(ghost.getGame().getSpriteStore().getSprite("ghosts/" + color + "/1"));
        
        ghost.setBaseSpeed(70.0);

        PauseTransition timer = new PauseTransition(Duration.millis(TRANSITION_DURATION));
        
        timer.setOnFinished(e -> {
            // Une fois le temps écoulé, on redevient officiellement Invulnérable
            ghost.setState(new GhostInvulnerableState());
        });
        
        timer.play();
    }

    @Override
    public void onCollideWithPacman(Fantomes ghost, Pacman pacman) {
        pacman.getScore().set(pacman.getScore().get() + 200);

        ghost.setDestroyed(true);
        ghost.getGame().removeAnimated(ghost);

        PauseTransition respawnTimer = new PauseTransition(Duration.seconds(3));
        respawnTimer.setOnFinished(e -> {
            ghost.respawn();
            ghost.getGame().addAnimated(ghost);
            ghost.setState(new GhostInvulnerableState());
        });
        respawnTimer.play();
    }

    @Override
    public void onLeave(Fantomes ghost) {
    
    }
}