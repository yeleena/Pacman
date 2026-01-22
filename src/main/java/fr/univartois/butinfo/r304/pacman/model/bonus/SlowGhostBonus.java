package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SlowGhostBonus extends AbstractBonus {

    private static final long DURATION = 5000L;
    
    private static final double SLOW_SPEED = 25.0; 
    private static final double NORMAL_SPEED = 50.0; // Vitesse par défaut d'un fantôme

    public SlowGhostBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    @Override
    public void applyTo(IAnimated pacman) {
        for (IAnimated animated : game.getAnimatedObjects()) { 
            if (animated instanceof Fantomes ghost) {
                ghost.setBaseSpeed(SLOW_SPEED);
                
                Revertion(() -> {
                    if (!ghost.isDestroyed()) {
                        ghost.setBaseSpeed(NORMAL_SPEED);
                    }
                });
            }
        }
    }

    private void Revertion(Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.millis(DURATION));
        pause.setOnFinished(e -> action.run());
        pause.play();
    }

    @Override
    public long getDuration() {
        return DURATION;
    }

    @Override
    public String getBonusName() {
        return "Slow Ghosts";
    }
}