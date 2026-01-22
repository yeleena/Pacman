package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IPacmanController;

public class PacmanInvulnerable implements EtatPacman {

    public PacmanInvulnerable() {}

    @Override
    public void collisionAvecFantome(Pacman pacman, Fantomes ghost) {
        if (ghost.isVulnerable()) {
            pacman.getScore().set(pacman.getScore().get() + 200);
            ghost.setDestroyed(true);
            ghost.getGame().removeAnimated(ghost);
        }
        
    }
}