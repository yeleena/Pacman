package fr.univartois.butinfo.r304.pacman.model.interfaces;

import java.util.List;

import fr.univartois.butinfo.r304.pacman.model.map.Cell;
import fr.univartois.butinfo.r304.pacman.model.map.GameMap;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore;

/**
 * Définit le contrat que le jeu doit respecter pour être manipulé par les objets animés.
 */
public interface IGame {

    ISpriteStore getSpriteStore();
    int getWidth();
    int getHeight();
    Cell getCellAt(int x, int y);
    GameMap getGameMap();
    List<IAnimated> getAnimatedObjects();

    IAnimated getPlayer();
    void addAnimated(IAnimated animated);
    void removeAnimated(IAnimated animated);


    void pacGumEaten(IAnimated gum);
    void megaPacGumEaten(IAnimated gum);
    void bonusEaten(Object bonus);
    
    void playerIsDead();
    void resetRound();
}