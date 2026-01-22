package fr.univartois.butinfo.r304.pacman.model.factory;

import fr.univartois.butinfo.r304.pacman.model.bonus.CompositBonus;
import fr.univartois.butinfo.r304.pacman.model.bonus.DoorBonus;
import fr.univartois.butinfo.r304.pacman.model.bonus.InvincibilityBonus;
import fr.univartois.butinfo.r304.pacman.model.bonus.ScoreMultiplierBonus;
import fr.univartois.butinfo.r304.pacman.model.bonus.SlowGhostBonus;
import fr.univartois.butinfo.r304.pacman.model.bonus.SpeedBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.ISpriteStore; // Utilisation de l'interface
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import java.util.Random;
/**
 * La classe BonusFactory crée les bonus et leur associe la bonne image via le SpriteStore.
 */
public class BonusFactory {
    private static final Random RANDOM = new Random();
    public enum BonusType {
        SPEED,
        SLOW_GHOST,
        SCORE_MULTIPLIER,
        INVINCIBILITY,
        DOOR
    }

    /**
     * Crée un bonus spécifique (méthode interne ou pour usage direct).
     */
    public static IBonus createBonus(BonusType type, IGame game, 
                                    double xPosition, double yPosition, Sprite sprite) {
        switch (type) {
            case SPEED:
                return new SpeedBonus(game, xPosition, yPosition, sprite);
            case SLOW_GHOST:
                return new SlowGhostBonus(game, xPosition, yPosition, sprite);
            case SCORE_MULTIPLIER:
                return new ScoreMultiplierBonus(game, xPosition, yPosition, sprite);
            case INVINCIBILITY:
                return new InvincibilityBonus(game, xPosition, yPosition, sprite);
            case DOOR:
                return new DoorBonus(game, xPosition, yPosition, sprite);
            default:
                throw new IllegalArgumentException("Type de bonus inconnu : " + type);
        }
    }

    /**
     * Crée un bonus aléatoire.
     * C'est ici qu'on demande au SpriteStore l'image correspondant au pouvoir tiré au sort.
     */
    public static IBonus createRandomBonus(IGame game, double xPosition, double yPosition) {
        // 1. On choisit un type au hasard en utilisant notre RANDOM local
        BonusType[] types = BonusType.values();
        
        // On utilise le RANDOM défini en haut de cette classe
        BonusType randomType = types[RANDOM.nextInt(types.length)];
        
        // 2. On récupère le SpriteStore via l'interface
        ISpriteStore store = game.getSpriteStore();
        
        // 3. On récupère la bonne image
        Sprite correctSprite = getSpriteForType(randomType, store);

        // 4. On crée le bonus
        return createBonus(randomType, game, xPosition, yPosition, correctSprite);
    }
    /**
     * Crée un Super Bonus (panier garni) représenté par l'Orange.
     */
    public static IBonus createSuperBonus(IGame game, double xPosition, double yPosition) {
        // On utilise l'Orange pour le visuel du Super Bonus
        Sprite sprite = game.getSpriteStore().getOrangeSprite(); 
        
        CompositBonus superBonus = new CompositBonus(game, xPosition, yPosition, sprite);
        
        // On ajoute quelques bonus à l'intérieur
        superBonus.addBonus(createRandomBonus(game, 0, 0));
        superBonus.addBonus(createRandomBonus(game, 0, 0));
        superBonus.addBonus(createRandomBonus(game, 0, 0));
        
        return superBonus;
    }

    /**
     * Associe chaque pouvoir à son image.
     */
    private static Sprite getSpriteForType(BonusType type, ISpriteStore store) {
        switch (type) {
            case SPEED:
                return store.getCherrySprite();   // Cerise
            case SLOW_GHOST:
                return store.getMelonSprite();    // Melon
            case SCORE_MULTIPLIER:
                return store.getAppleSprite();    // Pomme
            case INVINCIBILITY:
                return store.getGalaxianSprite(); // Galaxian
            case DOOR:
                return store.getKeySprite();      // Clé
            default:
                return store.getBellSprite();     // Cloche
        }
    }
}