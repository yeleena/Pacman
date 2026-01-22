package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;

public interface GhostState {
    void onEnter(Fantomes ghost);
    void onLeave(Fantomes ghost);
    void onCollideWithPacman(Fantomes ghost, Pacman pacman);
}
