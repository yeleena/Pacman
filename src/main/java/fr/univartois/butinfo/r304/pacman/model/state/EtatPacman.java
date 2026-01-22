package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;

public interface EtatPacman {
    void collisionAvecFantome(Pacman pacman, Fantomes ghost);
}
