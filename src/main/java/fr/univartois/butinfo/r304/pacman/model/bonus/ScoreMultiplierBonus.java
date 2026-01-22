
package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

/**
 * La classe {@link ScoreMultiplierBonus} représente un bonus permettant
 * de multiplier (double) temporairement les points gagnés en mangeant des gommes.
 *
 * @author Léo Monsterleet
 * @version 0.1.0
 */
public class ScoreMultiplierBonus extends AbstractBonus {

    /**
     * La durée du bonus en millisecondes (4 secondes).
     */
    private static final long DURATION = 4000L;

    /**
     * Le multiplicateur de score appliqué.
     */
    private static final double SCORE_MULTIPLIER = 2.0;

    /**
     * Crée un nouveau bonus de multiplicateur de score.
     *
     * @param game Le jeu dans lequel le bonus évolue.
     * @param xPosition La position en x du bonus.
     * @param yPosition La position en y du bonus.
     * @param sprite Le sprite représentant le bonus.
     */
    public ScoreMultiplierBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    /**
     * Applique le bonus de multiplicateur de score à Pac-Man.
     *
     * @param pacman Le Pac-Man qui reçoit le bonus.
     */
    @Override
    public void applyTo(IAnimated player) {
        if (player instanceof Pacman pacman) {
            pacman.getScore().set((int) (pacman.getScore().get() * SCORE_MULTIPLIER));
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
        return "Score Multiplier x2";
    }
}
