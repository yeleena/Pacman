package fr.univartois.butinfo.r304.pacman.view;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 * SpriteStore en Singleton.
 * Charge les images depuis les ressources (dossier 'sprites' ou 'sprites/bonus').
 */
public final class SpriteStore implements ISpriteStore {

    private final Map<String, Sprite> spriteCache = new ConcurrentHashMap<>();
    private final int spriteSize;

    private SpriteStore() {
        this.spriteSize = 32; // Taille par défaut des sprites
    }

    private static final class Holder {
        private static final SpriteStore INSTANCE = new SpriteStore();
    }

    public static SpriteStore getInstance() {
        return Holder.INSTANCE;
    }

    /* --------------------- Implémentation ISpriteStore --------------------- */

    @Override
    public Sprite getSprite(String identifier) {
        Sprite cached = spriteCache.get(identifier);
        if (cached != null) {
            return cached;
        }
        return spriteCache.computeIfAbsent(identifier, id -> {
            Image image = loadImage(id);
            Sprite sprite = StaticSprite.newInstance(image);
            sprite.setWidth(spriteSize);
            sprite.setHeight(spriteSize);
            return sprite;
        });
    }

    @Override
    public Sprite getSprite(int frameRate, List<String> identifiers) {
        List<Image> images = identifiers.stream().map(this::loadImage).toList();
        return AnimatedSprite.newInstance(FXCollections.observableList(images), frameRate);
    }

    /* --------------------- ACCESSEURS POUR LES BONUS --------------------- */
    
    public Sprite getCherrySprite() {
        return getSprite("cherries");
    }

    public Sprite getAppleSprite() {
        return getSprite("apple");
    }

    public Sprite getMelonSprite() {
        return getSprite("melon");
    }

    public Sprite getBellSprite() {
        return getSprite("bell");
    }

    public Sprite getKeySprite() {
        return getSprite("key");
    }

    public Sprite getGalaxianSprite() {
        return getSprite("galaxian");
    }
    
    public Sprite getOrangeSprite() {
        return getSprite("orange");
    }
    
    public Sprite getStrawberrySprite() { // J'ai vu que tu avais aussi strawberry.png
        return getSprite("strawberry");
    }

    /* --------------------------------------------------------------------- */

    /**
     * Charge une image. Cherche d'abord dans "sprites/", puis dans "sprites/bonus/".
     */
    private Image loadImage(String name) {
        try {
            // 1. Essai chemin standard : resources/.../view/sprites/
            String pathStandard = "/fr/univartois/butinfo/r304/pacman/view/sprites/" + name + ".png";
            URL urlImage = getClass().getResource(pathStandard);

            // 2. Si pas trouvé, essai dans le sous-dossier : resources/.../view/sprites/bonus/
            if (urlImage == null) {
                String pathBonus = "/fr/univartois/butinfo/r304/pacman/view/sprites/bonus/" + name + ".png";
                urlImage = getClass().getResource(pathBonus);
            }

            // 3. Vérification finale
            if (urlImage == null) {
                throw new NoSuchElementException("Impossible de trouver l'image : " + name + " (ni dans sprites/, ni dans sprites/bonus/)");
            }

            return new Image(urlImage.toExternalForm(), getSpriteSize(), getSpriteSize(), true, true);

        } catch (Exception e) {
            throw new NoSuchElementException("Erreur au chargement de l'image " + name, e);
        }
    }

    @Override
    public int getSpriteSize() {
        return spriteSize;
    }
}