package fr.univartois.butinfo.r304.pacman.model.map;

import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import java.util.Random;

public class MapGenerator implements ILabyrinthe {

    private final ISpriteStore spriteStore;

    public MapGenerator(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public GameMap generateMap(int height, int width) {
        GameMap map = new GameMap(height, width);
        Sprite wallSprite = spriteStore.getSprite("wall");
        Sprite pathSprite = spriteStore.getSprite("path");
                
        Random random = new Random();

        //Bordures et Remplissage initial
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    map.setAt(row, col, new Cell(new Wall(wallSprite)));
                } else {
                    map.setAt(row, col, new Cell(pathSprite));
                }
            }
        }

        for (int row = 1; row < height - 1; row++) {
            for (int col = 1; col < width - 1; col++) {
                // On vérifie qu'on est sur une case vide (pas déjà un mur)
                if (map.getAt(row, col).isEmpty() && random.nextDouble() < 0.1) {
                    if (SafeToPlaceWall(map, row, col)) {
                        map.setAt(row, col, new Cell(new Wall(wallSprite)));
                    }
                }
            }
        }
        return map;
    }

    private boolean SafeToPlaceWall(GameMap map, int row, int col) {
        int freeNeighbors = 0;
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            
            // Vérification des limites pour ne pas lire hors tableau
            if (r >= 0 && c >= 0 && r < map.getHeight() && c < map.getWidth()) {
                if (map.getAt(r, c).isEmpty()) {
                    freeNeighbors++;
                }
            }
        }
        // On autorise le mur seulement s'il reste au moins 2 chemins autour
        return freeNeighbors >= 2;
    }
}