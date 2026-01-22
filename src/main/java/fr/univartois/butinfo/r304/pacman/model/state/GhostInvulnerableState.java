package fr.univartois.butinfo.r304.pacman.model.state;

import fr.univartois.butinfo.r304.pacman.model.animated.Fantomes;
import fr.univartois.butinfo.r304.pacman.model.animated.Pacman;

/**
 * Le type GhostInvulnerableState
 * Correspond à un fantôme NORMAL.
 */
public class GhostInvulnerableState extends AbstractGhostState {

    @Override
    public void onEnter(Fantomes ghost) {
        ghost.setBaseSpeed(50.0);
        String color = ghost.getSpriteColorName(); 
        ghost.setSprite(ghost.getGame().getSpriteStore().getSprite("ghosts/" + color + "/1"));
    }
    
    @Override
    public void onCollideWithPacman(Fantomes ghost, Pacman pacman) {
        
    }
}