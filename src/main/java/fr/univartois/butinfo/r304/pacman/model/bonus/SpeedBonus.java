package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * La classe {@link SpeedBonus} accélère Pac-Man.
 */
public class SpeedBonus extends AbstractBonus {

    private static final long DURATION = 5000L;
    private static final double SPEED_MULTIPLIER = 1.5;

    public SpeedBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    @Override
    public void applyTo(IAnimated animated) {
        if (animated instanceof Pacman pacman) {
            double originalSpeed = pacman.getSpeed(); 
            
            pacman.setSpeed(originalSpeed * SPEED_MULTIPLIER);
            
            Revertion(() -> {
                pacman.setSpeed(originalSpeed);
            });
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
        return "Speed Boost";
    }
}