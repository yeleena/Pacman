package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IPacmanController;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PacmanVulnerable implements EtatPacman {

    private static final double DUREE_INVULNERABILITE = 2.0; 

    public PacmanVulnerable() {}

    @Override
    public void collisionAvecFantome(Pacman pacman, Fantomes ghost) {
        if (ghost.isVulnerable()) {
            pacman.getScore().set(pacman.getScore().get() + 200);
            ghost.setDestroyed(true);
            ghost.getGame().removeAnimated(ghost);
            
        } else {
            pacman.loseLife();

            if (pacman.getLives().get() > 0) {
                pacman.getGame().resetRound();

                ISpriteStore store = pacman.getGame().getSpriteStore();
                // Remet le sprite neutre
                pacman.setSprite(store.getSprite("pacman/half-open")); 
                
                // Passe en mode invincible temporaire
                pacman.setEtat(new PacmanInvulnerable());

                PauseTransition timer = new PauseTransition(Duration.seconds(DUREE_INVULNERABILITE));
                timer.setOnFinished(e -> {
                    pacman.setEtat(new PacmanVulnerable());
                });
                timer.play();

            } else {
                pacman.getGame().playerIsDead();
            }
        }
    }
}