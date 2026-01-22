
package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.model.state.PacmanInvulnerable;
import fr.univartois.butinfo.r304.pacman.model.state.PacmanVulnerable;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * La classe {@link InvincibilityBonus} représente un bonus permettant
 * à Pac-Man d'être temporairement invincible face aux fantômes.
 *
 * @author Léo Monsterleet
 * @version 0.1.0
 */
public class InvincibilityBonus extends AbstractBonus {

    /**
     * La durée du bonus en millisecondes (6 secondes).
     */
    private static final long DURATION = 6000L;

    /**
     * Crée un nouveau bonus d'invincibilité.
     *
     * @param game Le jeu dans lequel le bonus évolue.
     * @param xPosition La position en x du bonus.
     * @param yPosition La position en y du bonus.
     * @param sprite Le sprite représentant le bonus.
     */
    public InvincibilityBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    /**
     * Applique le bonus d'invincibilité à Pac-Man.
     * Pac-Man devient temporairement invincible.
     *
     * @param pacman Le Pac-Man qui reçoit le bonus.
     */
    @Override
    public void applyTo(IAnimated animated) {
        // Change l'état de Pac-Man pour le rendre invincible
        if (animated instanceof Pacman pacman) {
            // Change l'état de Pac-Man pour le rendre invincible
            pacman.setEtat(new PacmanInvulnerable());
            
            // Reprogramme un retour à l'état vulnérable
            PauseTransition pause = new PauseTransition(Duration.millis(DURATION));
            pause.setOnFinished(e -> pacman.setEtat(new PacmanVulnerable()));
            pause.play();
        }
    
    }

    /**
     * Retourne la durée du bonus.
     *
     * @return La durée du bonus en millisecondes.
     */
    @Override
    public long getDuration() {
        return DURATION;
    }

    /**
     * Retourne le nom du bonus.
     *
     * @return Le nom du bonus.
     */
    @Override
    public String getBonusName() {
        return "Invincibility Shield";
    }
}
