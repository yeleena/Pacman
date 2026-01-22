package fr.univartois.butinfo.r304.pacman.model.factory;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.map.ILabyrinthe;
import fr.univartois.butinfo.r304.pacman.model.map.MapGenerator;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.GhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.ChasingGhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;

public class HardLevelFactory implements GameElementFactory {

    //On a besoin du store pour le passer au MapGenerator
    private final ISpriteStore spriteStore;

    public HardLevelFactory(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public ILabyrinthe createLabyrinthGenerator() {
        return new MapGenerator(spriteStore);
    }

    @Override
    public GhostMoveStrategy createGhostMoveStrategy() {
        return new ChasingGhostMoveStrategy(); 
    }

    @Override
    public IBonus createBonusStrategy() {
        return null;
    }

    @Override
    public int getInitialGhostSpeed() {
        return 300; 
    }

    @Override
    public int getBonusFrequency() {
        return 2000; 
    }
}