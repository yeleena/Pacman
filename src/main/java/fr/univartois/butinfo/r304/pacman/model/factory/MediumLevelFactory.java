package fr.univartois.butinfo.r304.pacman.model.factory;

import fr.univartois.butinfo.r304.pacman.model.map.ILabyrinthe;
import fr.univartois.butinfo.r304.pacman.model.map.ClassicMapGenerator; 
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.GhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.RandomGhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus; 

public class MediumLevelFactory implements GameElementFactory {

    private final ISpriteStore spriteStore;

    public MediumLevelFactory(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public ILabyrinthe createLabyrinthGenerator() {
        return new ClassicMapGenerator(spriteStore);
    }

    @Override
    public GhostMoveStrategy createGhostMoveStrategy() {
        return new RandomGhostMoveStrategy(); 
    }

    @Override
    public IBonus createBonusStrategy() {
        return null; 
    }

    @Override
    public int getInitialGhostSpeed() {
        return 180; 
    }

    @Override
    public int getBonusFrequency() {
        return 500; 
    }
}