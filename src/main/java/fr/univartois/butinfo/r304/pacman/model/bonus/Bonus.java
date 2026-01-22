package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

public class Bonus extends AbstractBonus {

    public Bonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    @Override
    public void applyTo(IAnimated player) {
        if (player instanceof Pacman pacman) {
            // Ici, 'pacman' est typé comme Pacman
            pacman.getScore().set(pacman.getScore().get() + 100);
        }
    }

    @Override
    public long getDuration() {
        return 0; // Effet immédiat
    }

    @Override
    public String getBonusName() {
        return "Fruit";
    }
}