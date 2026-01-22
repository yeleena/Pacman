package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.AbstractAnimated;
import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.PacGommes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

public abstract class AbstractBonus extends AbstractAnimated implements IBonus {

    public AbstractBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    @Override
    public void onCollisionWith(IAnimated other) {
        other.collideWithBonus(this);
    }

    /**
     * C'est cette méthode qui est appelée par Pacman.onCollisionWith(this).
     */
    @Override
    public void collideWithPacman(Pacman pacman) {
        if (!isDestroyed()) {
            // 1. Appliquer l'effet au joueur
            applyTo(pacman);
            
            // 2. Retirer le bonus du jeu (IMPORTANT)
            game.bonusEaten(this); // Cette méthode dans PacmanGame appelle removeAnimated
            
            // 3. Marquer comme détruit pour éviter les collisions multiples
            setDestroyed(true);
        }
    }

    @Override
    public void collideWithFantomes(Fantomes fantome) {}

    @Override
    public void collideWithPacGommes(PacGommes pacgomme) {}

    @Override
    public void collideWithBonus(Object bonus) {}
}