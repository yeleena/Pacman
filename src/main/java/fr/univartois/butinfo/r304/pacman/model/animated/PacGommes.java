package fr.univartois.butinfo.r304.pacman.model.animated;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

/**
 * La classe {@link PacGommes} représente une pac-gomme dans le jeu Pac-Man.
 * Une pac-gomme peut être mangée par Pac-Man, auquel cas elle disparaît et
 * signale au jeu qu’elle a été consommée.
 *
 * @author Lebas Enzo
 */
public class PacGommes extends AbstractAnimated {
    public PacGommes (IGame game, double xPosition, double yPosition, Sprite sprite){
        super(game,xPosition,yPosition,sprite);
    }
    /**
    * Lorsqu'un objet entre en collision avec cette pac-gomme
    *
    * @author Lebas Enzo
    */
   @Override
    public void onCollisionWith(IAnimated other){
        other.collideWithPacGommes(this);
    }
    /**
    * Lorsque Pacman entre en collision avec une pac-gomme
    *
    * @author Lebas Enzo
    */
   public void collideWithPacman(Pacman pacman){
        if(!isDestroyed()){
            setDestroyed(true); //Le pac-gomme disparait
            game.pacGumEaten(this); //methode dans la classe PacmanGame, signale au jeu qu'elle a été mangée 
        }
    }
    /**
    * Lorsqu'un Fantome entre en collision avec une pac-gomme
    *
    * @author Lebas Enzo
    */
   @Override
   public void collideWithFantomes(Fantomes fantome){
        //aucun effet
   }
   /**
    * Lorsqu'une pac-gomme entre en collision avec une pac-gomme
    *
    * @author Lebas Enzo
    */
   @Override
    public void collideWithPacGommes(PacGommes pacgomme){
        //aucun effet
   }
}