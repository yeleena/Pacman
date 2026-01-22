package fr.univartois.butinfo.r304.pacman.model.map;

import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
/**
 * Générateur simple de labyrinthe qui place des murs uniquement sur les
 * bordures de la carte. Toutes les autres cellules restent vides.
 * 
 * Implémente l'interface {@link ILabyrinthe} pour pouvoir être utilisé
 * dans la classe {@link PacmanGame}.
 * 
 * 
 * @author Enzo
 * @version 0.1.0
 */
public class SimpleBorderMapGenerator implements ILabyrinthe {

    private final ISpriteStore spriteStore;

    public SimpleBorderMapGenerator(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }
    /**
     * Génère une carte du jeu avec des murs uniquement sur les bordures.
     * Les cellules à l'intérieur restent vides.
     * 
     * @param height Nombre de lignes de la carte
     * @param width Nombre de colonnes de la carte
     * @return La carte du jeu générée
     */
    @Override
    public GameMap generateMap(int height, int width) {
        GameMap map = new GameMap(height, width);
        Sprite wallSprite = spriteStore.getSprite("wall");
        Sprite pathSprite = spriteStore.getSprite("path"); // <- ajout du sol

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    // Murs sur les bords
                    Cell wallCell = new Cell(new Wall(wallSprite));
                    map.setAt(row, col, wallCell);
                } else {
                    // Sol à l'intérieur
                    Cell pathCell = new Cell(pathSprite);
                    map.setAt(row, col, pathCell);
                }
            }
        }

        return map;
    }
}
