package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.model.map.Cell;
import fr.univartois.butinfo.r304.pacman.model.map.GameMap;
import fr.univartois.butinfo.r304.pacman.model.map.Wall;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe {@link DoorBonus} ouvre une porte temporaire sans modifier la classe Cell.
 */
public class DoorBonus extends AbstractBonus {

    private static final long DURATION = 3000L;
    private static final Random RANDOM = new Random();

    public DoorBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    @Override
    public void applyTo(IAnimated pacman) {
        GameMap gameMap = game.getGameMap();
        if (gameMap != null) {
            createTemporaryDoor(gameMap);
        }
    }

    private void createTemporaryDoor(GameMap gameMap) {
        // Lister tous les murs
        List<Cell> wallCells = new ArrayList<>();
        for (int row = 0; row < gameMap.getHeight(); row++) {
            for (int col = 0; col < gameMap.getWidth(); col++) {
                Cell cell = gameMap.getAt(row, col);
                if (cell.getWall() != null) {
                    wallCells.add(cell);
                }
            }
        }

        if (wallCells.isEmpty()) return;

        // Choisir un mur au hasard
        Cell targetCell = wallCells.get(RANDOM.nextInt(wallCells.size()));
        Wall originalWall = targetCell.getWall();
        targetCell.wallProperty().set(null);

        targetCell.imageProperty().unbind(); // coupe le lien avec le sprite de base
        targetCell.imageProperty().set(null); 

        // PROGRAMMER LA FERMETURE 
        PauseTransition wait = new PauseTransition(Duration.millis(DURATION));
        wait.setOnFinished(e -> {
           
            targetCell.replaceBy(new Cell(originalWall)); 
        });
        wait.play();
    }

    @Override
    public long getDuration() {
        return DURATION;
    }

    @Override
    public String getBonusName() {
        return "Door Opening";
    }
}