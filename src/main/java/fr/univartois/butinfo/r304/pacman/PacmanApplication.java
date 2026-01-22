package fr.univartois.butinfo.r304.pacman;

import java.io.IOException;

import fr.univartois.butinfo.r304.pacman.controller.PacmanController;
import fr.univartois.butinfo.r304.pacman.model.LevelManager;
import fr.univartois.butinfo.r304.pacman.model.PacmanGame;
import fr.univartois.butinfo.r304.pacman.view.SpriteStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Lance l'application Pac-Man JavaFX.
 */
public final class PacmanApplication extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 500;
    private static final int NB_GHOSTS = 4;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/pacman.fxml"));
        Parent viewContent = fxmlLoader.load();
        PacmanController controller = fxmlLoader.getController();
        controller.setStage(stage);

        LevelManager levelManager = new LevelManager(SpriteStore.getInstance());

        PacmanGame game = new PacmanGame(GAME_WIDTH, GAME_HEIGHT, SpriteStore.getInstance(), NB_GHOSTS, levelManager);
        
        // 4. Liaisons MVC
        controller.setGame(game);
        game.setController(controller);

        game.prepare();

        // 6. Affichage
        Scene scene = new Scene(viewContent, GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("PacmanFX");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}