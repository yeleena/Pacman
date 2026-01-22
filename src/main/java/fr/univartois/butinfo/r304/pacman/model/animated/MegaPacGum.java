package fr.univartois.butinfo.r304.pacman.model.animated;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

/**
 * Cette classe représente une méga-gomme sur la carte du jeu.
 * Une méga-gomme est un objet spécial que Pac-Man peut manger
 * pour obtenir un avantage temporaire.
 *
 * @author Yeleen
 */
public class MegaPacGum extends PacGommes {

    /**
     * Crée une nouvelle méga-gomme aux coordonnées données avec le sprite fourni.
     *
     * @param game Le jeu.
     * @param xPosition La position X (en pixels).
     * @param yPosition La position Y (en pixels).
     * @param sprite Le sprite affiché pour cette méga-gomme.
     */
    public MegaPacGum(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    /**
     * Lorsque Pac-Man entre en collision avec une méga-gomme :
     * on la détruit et on notifie le jeu via {@link PacmanGame#megaPacGumEaten()}.
     *
     * @param pacman Le joueur.
     */
    @Override
    public void collideWithPacman(Pacman pacman) {
        if (isDestroyed()) {
            return;
        }

        setDestroyed(true);              // côté objet
        game.megaPacGumEaten(this);      // côté jeu
    }
}
