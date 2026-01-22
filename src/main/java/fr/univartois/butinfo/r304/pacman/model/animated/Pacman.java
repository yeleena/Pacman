package fr.univartois.butinfo.r304.pacman.model.animated;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IBonus;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.view.Sprite;
import fr.univartois.butinfo.r304.pacman.model.state.EtatPacman;
import fr.univartois.butinfo.r304.pacman.model.state.PacmanVulnerable; 

public class Pacman extends AbstractAnimated {

    private final IntegerProperty lives;
    private final IntegerProperty score;
    private EtatPacman etatActuel;
    private long invincibleUntilMs = 0;

    public Pacman(IGame game, double xPosition, double yPosition, Sprite sprite, int initialLives) {
        super(game, xPosition, yPosition, sprite);
        this.lives = new SimpleIntegerProperty(initialLives);
        this.score = new SimpleIntegerProperty(0);
        
        this.setEtat(new PacmanVulnerable());
    }

    @Override
    public void onCollisionWith(IAnimated other) {
        other.collideWithPacman(this);
        
        if (other instanceof Fantomes ghost) {
            if (etatActuel != null) {
                etatActuel.collisionAvecFantome(this, ghost);
            }
        }
    }
    
    public IntegerProperty getLives() { return lives; }
    public IntegerProperty getScore() { return score; }

    public void loseLife() {
        if (lives.get() > 0) lives.set(lives.get() - 1);
    }

    public void setEtat(EtatPacman nouvelEtat) {
        this.etatActuel = nouvelEtat;
    }

    public EtatPacman getEtat() {
        return etatActuel;
    }
    
    @Override
    public void collideWithBonus(IBonus bonus) {}
    @Override
    public void collideWithFantomes(Fantomes ghost) {}
    
    public boolean isInvincible() {
        return System.currentTimeMillis() < invincibleUntilMs;
    }

    public void setInvincibleForMillis(long durationMs) {
        invincibleUntilMs = System.currentTimeMillis() + durationMs;
    }

    @Override
    public void collideWithPacGommes(PacGommes gum) {
        gum.collideWithPacman(this);
    }
}