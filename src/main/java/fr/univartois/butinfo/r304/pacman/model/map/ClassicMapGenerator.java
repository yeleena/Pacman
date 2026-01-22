package fr.univartois.butinfo.r304.pacman.model.map;

import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

/**
 * Cette classe crée une carte "classique" pour le jeu Pac-Man.
 *
 * La carte contient :
 * - des murs tout autour (bordure)
 * - des murs à l'intérieur (horizontaux et verticaux)
 * - une zone vide au centre pour les fantômes
 *
 * Cette classe correspond à la question 11 du TP (variante de labyrinthe).
 *
 * @author Yeleen
 */

public class ClassicMapGenerator implements ILabyrinthe {

    private final ISpriteStore spriteStore;

    public ClassicMapGenerator(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public GameMap generateMap(int height, int width) {
        Sprite wall = spriteStore.getSprite("wall");
        Sprite floor = spriteStore.getSprite("path"); 

        // Dimensions fixes pour s'adapter à 800x500
        int mapHeight = 15;
        int mapWidth = 19;

        GameMap map = new GameMap(mapHeight, mapWidth);

        int centerRow = mapHeight / 2;
        int centerCol = mapWidth / 2;

        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                map.setAt(r, c, new Cell(floor));
            }
        }

        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                boolean isTunnel = (r == centerRow && (c == 0 || c == mapWidth - 1));
                if ((r == 0 || r == mapHeight - 1 || c == 0 || c == mapWidth - 1) && !isTunnel) {
                    map.setAt(r, c, new Cell(new Wall(wall)));
                }
            }
        }


        for (int c = 2; c < mapWidth - 2; c++) {
            if (c != centerCol) map.setAt(2, c, new Cell(new Wall(wall)));
        }

        // Ligne horizontale basse
        for (int c = 2; c < mapWidth - 2; c++) {
            if (c != centerCol) map.setAt(mapHeight - 3, c, new Cell(new Wall(wall)));
        }

        // Barres verticales internes
        for (int r = 4; r < mapHeight - 4; r++) {
            map.setAt(r, 3, new Cell(new Wall(wall)));
            map.setAt(r, mapWidth - 4, new Cell(new Wall(wall)));
        }

        // Zone centrale (fantômes)
        for (int r = centerRow - 1; r <= centerRow + 1; r++) {
            for (int c = centerCol - 2; c <= centerCol + 2; c++) {
                map.setAt(r, c, new Cell(floor));
            }
        }

        // Encadrement zone fantômes
        for (int c = centerCol - 3; c <= centerCol + 3; c++) {
            if (c != centerCol - 2 && c != centerCol + 2)
                map.setAt(centerRow - 2, c, new Cell(new Wall(wall)));
            map.setAt(centerRow + 2, c, new Cell(new Wall(wall)));
        }

        // Colonnes zone fantômes
        for (int r = centerRow - 1; r <= centerRow + 1; r++) {
            map.setAt(r, centerCol - 3, new Cell(new Wall(wall)));
            map.setAt(r, centerCol + 3, new Cell(new Wall(wall)));
        }

        return map;
    }
}