
package fr.univartois.butinfo.r304.pacman.model.interfaces;

/**
 * L'interface {@link IBonus} définit le contrat pour tous les bonus que Pac-Man peut 
 * récupérer dans le jeu.
 *
 * @author Léo Monsterleet
 * @version 0.1.0
 */
public interface IBonus {

    /**
     * Applique le bonus à Pac-Man.
     *
     * @param pacman Le Pac-Man qui reçoit le bonus.
     */
    void applyTo(IAnimated pacman);

    /**
     * Retourne la durée du bonus en millisecondes.
     * Si le bonus est permanent, retourne -1.
     *
     * @return La durée du bonus en ms, ou -1 si permanent.
     */
    long getDuration();

    /**
     * Retourne le nom/type du bonus pour l'affichage ou le logging.
     *
     * @return Le nom du bonus.
     */
    String getBonusName();
}
