package fr.univartois.butinfo.r304.pacman.view;

import java.util.List;

/**
 * L'interface ISpriteStore définit un contrat pour charger les images.
 */
public interface ISpriteStore {

    int DEFAULT_SPRITE_SIZE = 30;
    int DEFAULT_FRAME_RATE = 5;

    Sprite getSprite(String identifier);

    default Sprite getSprite(String... identifiers) {
        return getSprite(DEFAULT_FRAME_RATE, identifiers);
    }

    default Sprite getSprite(int frameRate, String... identifiers) {
        return getSprite(frameRate, List.of(identifiers));
    }

    default Sprite getSprite(List<String> identifiers) {
        return getSprite(DEFAULT_FRAME_RATE, identifiers);
    }

    Sprite getSprite(int frameRate, List<String> identifiers);

    default int getSpriteSize() {
        return DEFAULT_SPRITE_SIZE;
    }

    
    Sprite getCherrySprite();
    Sprite getAppleSprite();
    Sprite getMelonSprite();
    Sprite getBellSprite();
    Sprite getKeySprite();
    Sprite getGalaxianSprite();
    Sprite getOrangeSprite();
    Sprite getStrawberrySprite();
}