package fr.univartois.butinfo.r304.pacman.model;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

// import fr.univartois.butinfo.r304.pacman.model.bonus.PacGommes; // Si besoin selon ton package
import fr.univartois.butinfo.r304.pacman.model.map.*;
import fr.univartois.butinfo.r304.pacman.model.state.GhostVulnerableState;
import fr.univartois.butinfo.r304.pacman.model.state.PacmanInvulnerable;
import fr.univartois.butinfo.r304.pacman.model.state.PacmanVulnerable;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import fr.univartois.butinfo.r304.pacman.model.animated.*;
import fr.univartois.butinfo.r304.pacman.model.factory.BonusFactory;
import fr.univartois.butinfo.r304.pacman.model.factory.GameElementFactory;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IPacmanController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public final class PacmanGame implements IGame {
    private final IntegerProperty levelProperty = new SimpleIntegerProperty(1); 
    public static final Random RANDOM = new Random();
    public static final int DEFAULT_SPEED = 150; 
    private static final double MEGA_GUM_PROBABILITY = 0.01;

    private final int width;
    private final int height;
    private final ISpriteStore spriteStore;
    private GameMap gameMap;
    private Pacman player;
    private int nbGhosts;
    private int nbGums;

    // Listes d'objets
    private final List<IAnimated> movingObjects = new CopyOnWriteArrayList<>();
    private final List<IAnimated> animatedObjects = new CopyOnWriteArrayList<>();
    private final AnimationTimer animation = new GameAnimation(movingObjects, animatedObjects);
    
    private IPacmanController controller;
    private ILabyrinthe mapGenerator;
    private final LevelManager levelManager;
    private GameElementFactory levelFactory;

    public PacmanGame(int gameWidth, int gameHeight, ISpriteStore spriteStore, int nbGhosts, LevelManager levelManager) {
        this.width = gameWidth;
        this.height = gameHeight;
        this.spriteStore = spriteStore;
        this.nbGhosts = nbGhosts;
        this.levelManager = levelManager;
        
        // Niveau 1 par défaut
        this.levelFactory = levelManager.getFactoryLevel(1);
    }

    public void setController(IPacmanController controller) {
        this.controller = controller;
    }

    public ISpriteStore getSpriteStore() { return spriteStore; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public GameMap getGameMap() { return gameMap; }
    public List<IAnimated> getAnimatedObjects() { return animatedObjects; }
    public Pacman getPlayer() { return player; }

    public void prepare() {
        gameMap = createMap();
        controller.prepare(gameMap);
    }

    private GameMap createMap() {
        int cellSize = spriteStore.getSpriteSize();
        int cellsWidth = width / cellSize;
        int cellsHeight = height / cellSize;

        // Utilisation de la Factory pour la carte
        this.mapGenerator = levelFactory.createLabyrinthGenerator();

        GameMap map = mapGenerator.generateMap(cellsHeight, cellsWidth);
        nbGums = map.getEmptyCells().size();
        return map;
    }

    public void start() {
        prepare();
        createAnimated();
        initStatistics();
        animation.start();
    }

    private void createAnimated() {
        clearAnimated();
        int cellSize = spriteStore.getSpriteSize();

        // 1. Pacman
        Sprite pacmanSprite = spriteStore.getSprite("pacman/half-open");
        pacmanSprite.setWidth((int) (cellSize * 0.6));
        pacmanSprite.setHeight((int) (cellSize * 0.6));
        player = new Pacman(this, 0, 0, pacmanSprite, 3);
        
        addAnimated(player);
        addMoving(player); // Pacman bouge
        spawnAnimated(player);

        // 2. Fantômes (via Factory)
        String[] colors = {"orange", "blue", "red", "pink"};
        for (int i = 0; i < nbGhosts; i++) {
            Sprite ghostSprite = spriteStore.getSprite("ghosts/" + colors[i % colors.length] + "/1");
            ghostSprite.setWidth((int) (cellSize * 0.8));
            ghostSprite.setHeight((int) (cellSize * 0.8));

            Fantomes ghost = new Fantomes(
                    this, 0, 0, ghostSprite,
                    Fantomes.Couleurs.values()[i % Fantomes.Couleurs.values().length]
            );

            // Stratégie et Vitesse via Factory
            ghost.setMoveStrategy(levelFactory.createGhostMoveStrategy());
            ghost.setBaseSpeed(levelFactory.getInitialGhostSpeed());

            addAnimated(ghost);
            addMoving(ghost); // Les fantômes bougent
            spawnAnimated(ghost);

            ghost.setSpawnPoint(ghost.getX(), ghost.getY());
        }

        // 3. Gommes
        createGums(cellSize);

        // 4. Bonus
        createRandomBonuses();
    }

    private void createGums(int cellSize) {
        Sprite gumSprite = spriteStore.getSprite("pacgum");
        Sprite megaGumSprite = spriteStore.getSprite("megagum"); 
        
        List<Cell> emptyCells = gameMap.getEmptyCells();
        nbGums = emptyCells.size();

        for (Cell cell : emptyCells) {
            double x = cell.getColumn() * cellSize;
            double y = cell.getRow() * cellSize;

            if (RANDOM.nextDouble() < MEGA_GUM_PROBABILITY) {
                MegaPacGum mega = new MegaPacGum(this, x, y, megaGumSprite);
                addAnimated(mega);
            } else {
                PacGommes gum = new PacGommes(this, x, y, gumSprite);
                addAnimated(gum);
            }
        }
    }

    private void createRandomBonuses() {
        for (int i = 0; i < 3; i++) {
            List<Cell> empty = gameMap.getEmptyCells();
            if (empty.isEmpty()) break;
            Cell cell = empty.get(RANDOM.nextInt(empty.size()));
            
            double x = cell.getColumn() * spriteStore.getSpriteSize();
            double y = cell.getRow() * spriteStore.getSpriteSize();
            
            IBonus bonus = BonusFactory.createRandomBonus(this, x, y);
            
            if (bonus instanceof IAnimated animatedBonus) {
                 addAnimated(animatedBonus);
            }
        }
    }

    private void initStatistics() {
        controller.bindLife(player.getLives());
        controller.bindScore(player.getScore());
    }


    // --- Mouvements ---

    public void moveUp() {
        // On arrête le mouvement horizontal pour éviter les diagonales
        player.setHorizontalSpeed(0);
        player.setVerticalSpeed(-DEFAULT_SPEED);
        player.setRotate(270);
    }

    public void moveRight() {
        player.setVerticalSpeed(0);
        player.setHorizontalSpeed(DEFAULT_SPEED);
        player.setRotate(0);
    }

    public void moveDown() {
        player.setHorizontalSpeed(0);
        player.setVerticalSpeed(DEFAULT_SPEED);
        player.setRotate(90);
    }

    public void moveLeft() {
        player.setVerticalSpeed(0);
        player.setHorizontalSpeed(-DEFAULT_SPEED);
        player.setRotate(180);
    }

    public void stopMoving() {
        player.setHorizontalSpeed(0);
        player.setVerticalSpeed(0);
    }

    // --- Gestion listes ---

    public void addMoving(IAnimated object) {
        movingObjects.add(object);
    }

    public void addAnimated(IAnimated object) {
        animatedObjects.add(object);
        controller.addAnimated(object);
    }

    public void removeAnimated(IAnimated object) {
        animatedObjects.remove(object);
        movingObjects.remove(object);
        object.onDespawn();
    }

    private void clearAnimated() {
        for (IAnimated animated : animatedObjects) {
            animated.onDespawn();
        }
        animatedObjects.clear();
        movingObjects.clear();
    }

    private void spawnAnimated(IAnimated animated) {
        List<Cell> spawnableCells = gameMap.getEmptyCells();
        if (!spawnableCells.isEmpty()) {
            Cell cell = spawnableCells.get(RANDOM.nextInt(spawnableCells.size()));
            int cellSize = spriteStore.getSpriteSize();
            
            animated.setX(cell.getColumn() * cellSize + (cellSize - animated.getWidth()) / 2.0);
            animated.setY(cell.getRow() * cellSize + (cellSize - animated.getHeight()) / 2.0);
        }
    }


    @Override
    public void pacGumEaten(IAnimated gum) {
        nbGums--;
        removeAnimated(gum);
        player.getScore().set(player.getScore().get() + 10);
        if (nbGums <= 0) {
            startNextLevel();
        }
    }

    private void startNextLevel() {
        animation.stop();
        controller.reset();
        this.levelFactory = levelManager.nextLevel();
        int newLevel = levelManager.getLevel();
        levelProperty.set(newLevel);

        controller.gameOver("LEVEL " + newLevel + "\nPREPARE TO PLAY!");
    }

    @Override
    public void megaPacGumEaten(IAnimated gum) {
        nbGums--;
        removeAnimated(gum);
        for (IAnimated animated : animatedObjects) {
            if (animated instanceof Fantomes ghost) {
                ghost.setState(new GhostVulnerableState());
            }
        }
    }

    @Override
    public void bonusEaten(Object bonus) {
        // C'est ici qu'on gère le type Object imposé par IGame
        if (bonus instanceof IAnimated animated) {
            removeAnimated(animated);
        }
    }

    @Override
    public void playerIsDead() {
        if (player.getLives().get() > 0) {
            resetRound();
        } else {
            gameOver("GAME OVER");
            controller.reset();
        }
    }
    
    @Override
    public void resetRound() {
        stopMoving();
        spawnAnimated(player);
        for (IAnimated a : animatedObjects) {
            if (a instanceof Fantomes) spawnAnimated(a);
        }
        
        player.setEtat(new PacmanInvulnerable());
        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        wait.setOnFinished(e -> player.setEtat(new PacmanVulnerable()));
        wait.play();
    }

    private void gameOver(String message) {
        animation.stop();
        controller.gameOver(message);
    }
    
    // Setters/Getters simples
    public void setMapGenerator(ILabyrinthe mapgenerator) { this.mapGenerator = mapgenerator; }
    public Cell getCellAt(int x, int y) {
        int cellSize = spriteStore.getSpriteSize();
        return gameMap.getAt(y / cellSize, x / cellSize);
    }
}