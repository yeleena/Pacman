package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.FleeingGhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GhostVulnerableState extends AbstractGhostState {

    private static final long VULNERABLE_DURATION = 7000; // 7 secondes
    private PauseTransition timer; // On garde une référence au timer

    @Override
    public void onEnter(Fantomes ghost) {
        ghost.setVulnerable(true);
        ghost.setMoveStrategy(new FleeingGhostMoveStrategy()); // Il fuit

        // Changement de Sprite (Bleu)
        Sprite sprite = ghost.getGame().getSpriteStore().getSprite("ghosts/afraid/1");
        sprite.setWidth(ghost.getSprite().getWidth());
        sprite.setHeight(ghost.getSprite().getHeight());
        ghost.setSprite(sprite);

        // --- LE TIMER ---
        timer = new PauseTransition(Duration.millis(VULNERABLE_DURATION));
        timer.setOnFinished(e -> {
            // Si le fantôme est toujours vivant à la fin du chrono
            if (!ghost.isDestroyed()) {
                // On repasse directement en mode Normal pour éviter les bugs
                ghost.setState(new GhostInvulnerableState()); 
            }
        });
        timer.play();
    }

    @Override
    public void onCollideWithPacman(Fantomes ghost, Pacman pacman) {
        if (timer != null) timer.stop();

        // Score
        pacman.getScore().set(pacman.getScore().get() + 200);

        // Mort du fantôme
        ghost.setDestroyed(true); // Il est mort
        ghost.getGame().removeAnimated(ghost); // On l'enlève de l'écran

        PauseTransition respawnTimer = new PauseTransition(Duration.seconds(2));
        respawnTimer.setOnFinished(e -> {
            ghost.respawn(); 
            
            // On le remet dans le jeu
            ghost.getGame().addAnimated(ghost);
            
            ghost.setState(new GhostInvulnerableState());
        });
        respawnTimer.play();
    }

    @Override
    public void onLeave(Fantomes ghost) {
        if (timer != null) timer.stop();
        ghost.setVulnerable(false); 
    }
}