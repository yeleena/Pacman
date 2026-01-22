package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;

public abstract class AbstractGhostState implements GhostState {

    @Override
    public void onEnter(Fantomes ghost) {
        // Par défaut
    }

    @Override
    public void onLeave(Fantomes ghost) {
        // Par défaut
    }

    @Override
    public abstract void onCollideWithPacman(Fantomes ghost, Pacman pacman);
}

