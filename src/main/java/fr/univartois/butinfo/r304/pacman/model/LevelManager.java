package fr.univartois.butinfo.r304.pacman.model;

import fr.univartois.butinfo.r304.pacman.model.factory.EasyLevelFactory;
import fr.univartois.butinfo.r304.pacman.model.factory.GameElementFactory;
import fr.univartois.butinfo.r304.pacman.model.factory.HardLevelFactory;
import fr.univartois.butinfo.r304.pacman.model.factory.MediumLevelFactory;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;

/**
 * Gère le niveau actuel du jeu et fournit la fabrique associée.
 * Permet aussi de passer au niveau supérieur.
 * * @author Lebas Enzo
 */
public class LevelManager {

    private int level = 1;

    private final ISpriteStore spriteStore;

    /**
     * Crée un nouveau gestionnaire de niveaux.
     * @param spriteStore Le store d'images à passer aux usines.
     */
    public LevelManager(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    /**
     * Retourne la fabrique correspondant au niveau donné.
     */
    public GameElementFactory getFactoryLevel(int level) {
        return switch (level) {
            case 1 -> new EasyLevelFactory(spriteStore);
            case 2 -> new MediumLevelFactory(spriteStore); 
            case 3 -> new HardLevelFactory(spriteStore); // À partir du niveau 3, c'est difficile 
            default -> new HardLevelFactory(spriteStore); // À partir du niveau 3, c'est difficile 
        };
    }

    /**
     * Passe au niveau suivant et renvoie sa fabrique.
     */
    public GameElementFactory nextLevel() {
        level++;
        return getFactoryLevel(level);
    }

    /**
     * @return le niveau actuel.
     */
    public int getLevel() {
        return level;
    }
}