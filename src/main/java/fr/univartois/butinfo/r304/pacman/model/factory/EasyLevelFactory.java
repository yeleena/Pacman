package fr.univartois.butinfo.r304.pacman.model.factory;

import fr.univartois.butinfo.r304.pacman.model.map.ILabyrinthe;
import fr.univartois.butinfo.r304.pacman.model.map.SimpleBorderMapGenerator; // On utilise ta map "vide" (facile)
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.GhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.RandomGhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;

import fr.univartois.butinfo.r304.pacman.model.animated.*;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus; 

public class EasyLevelFactory implements GameElementFactory {

    private final ISpriteStore spriteStore;

    public EasyLevelFactory(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public ILabyrinthe createLabyrinthGenerator() {
        return new SimpleBorderMapGenerator(spriteStore);
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
        return 150; 
    }

    @Override
    public int getBonusFrequency() {
        return 500; 
    }
}