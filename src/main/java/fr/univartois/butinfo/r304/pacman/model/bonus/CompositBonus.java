
package fr.univartois.butinfo.r304.pacman.model.bonus;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@link CompositBonus} représente un super bonus contenant
 * plusieurs bonus à appliquer simultanément.
 * 
 * Cette implémentation du pattern Composite permet de créer des bonus complexes
 * combinant plusieurs effets. Par exemple, un super bonus peut regrouper :
 * - Un bonus de vitesse pour Pac-Man
 * - Un bonus de ralentissement des fantômes
 * - Un bonus de multiplicateur de score
 *
 * @author Léo Monsterleet
 * @version 0.1.0
 */
public class CompositBonus extends AbstractBonus {

    /**
     * La liste des bonus contenus dans ce super bonus.
     */
    private final List<IBonus> bonuses = new ArrayList<>();

    /**
     * La durée du super bonus (la plus longue parmi les bonus contenus).
     */
    private long maxDuration = 0;

    /**
     * Crée un nouveau super bonus.
     *
     * @param game Le jeu dans lequel le bonus évolue.
     * @param xPosition La position en x du bonus.
     * @param yPosition La position en y du bonus.
     * @param sprite Le sprite représentant le bonus.
     */
    public CompositBonus(IGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
    }

    /**
     * Ajoute un bonus à ce super bonus.
     *
     * @param bonus Le bonus à ajouter.
     */
    public void addBonus(IBonus bonus) {
        if (bonus != null) {
            bonuses.add(bonus);
            // Met à jour la durée maximale
            if (bonus.getDuration() > maxDuration) {
                maxDuration = bonus.getDuration();
            }
        }
    }

    /**
     * Applique tous les bonus contenus à Pac-Man.
     *
     * @param pacman Le Pac-Man qui reçoit les bonus.
     */
    @Override
    public void applyTo(IAnimated animated) {
        // Applique chaque bonus contenu
        for (IBonus bonus : bonuses) {
            bonus.applyTo(animated);
        }
    }

    /**
     * Retourne la durée du super bonus (la plus longue).
     *
     * @return La durée maximale en millisecondes.
     */
    @Override
    public long getDuration() {
        return maxDuration;
    }

    /**
     * Retourne le nom du super bonus.
     *
     * @return Le nom du super bonus.
     */
    @Override
    public String getBonusName() {
        StringBuilder sb = new StringBuilder("Super Bonus (");
        for (int i = 0; i < bonuses.size(); i++) {
            if (i > 0) {
                sb.append(" + ");
            }
            sb.append(bonuses.get(i).getBonusName());
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Retourne le nombre de bonus contenus dans ce super bonus.
     *
     * @return Le nombre de bonus.
     */
    public int getNumberOfBonuses() {
        return bonuses.size();
    }

    /**
     * Retourne les bonus contenus dans ce super bonus.
     *
     * @return La liste des bonus contenus.
     */
    public List<IBonus> getBonuses() {
        return new ArrayList<>(bonuses);
    }
}
