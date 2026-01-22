package fr.univartois.butinfo.r304.pacman.model.animated;
import java.util.concurrent.ThreadLocalRandom;

import fr.univartois.butinfo.r304.pacman.model.interfaces.IAnimated;
import fr.univartois.butinfo.r304.pacman.model.interfaces.IGame;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.GhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.model.movesStrategy.RandomGhostMoveStrategy;
import fr.univartois.butinfo.r304.pacman.model.state.GhostState;
import fr.univartois.butinfo.r304.pacman.view.Sprite;

public class Fantomes extends AbstractAnimated {
    private  double spawnX;
    private  double spawnY;
	public enum Couleurs{
		ROUGE,ROSE,BLEU,ORANGE;
	}
	
	private Couleurs couleur;
	private long changerDelai = 2000L;
	private long dernierChangement = 0L;
	private double vitesseBase = 50.0;
    private GhostMoveStrategy moveStrategy = new RandomGhostMoveStrategy();
	private boolean vulnerable = false;
	
	public Fantomes(IGame game, double xPosition, double yPosition, Sprite sprite, Couleurs couleur) {
		super(game, xPosition, yPosition, sprite);
		this.couleur = couleur;
	    this.spawnX = xPosition;
	    this.spawnY = yPosition;
		chooseRandomDirection();
	}
	public void setSpawnPoint(double x, double y) {
    	this.spawnX = x;
    	this.spawnY = y;
	}

	public Couleurs getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleurs couleur) {
		this.couleur = couleur;
	}

	public void setChangeDelay(long changerDelai) {
        if (changerDelai < 0) {
            throw new IllegalArgumentException("changeDelay must be >= 0");
        }
        this.changerDelai = changerDelai;
    }

	public void setBaseSpeed(double vitesseBase) {
        this.vitesseBase = vitesseBase;
    }

    public void setMoveStrategy(GhostMoveStrategy strategy) {
        this.moveStrategy = strategy;
    }

    public GhostMoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    // Methods for strategy access to internal state
    public void setHorizontalSpeed(double speed) { this.horizontalSpeed = speed; }
    public void setVerticalSpeed(double speed) { this.verticalSpeed = speed; }
    public void incrementDernierChangement(long d) { this.dernierChangement += d; }
    public long getDernierChangement() { return this.dernierChangement; }
    public long getChangerDelai() { return this.changerDelai; }
    public void resetDernierChangement() { this.dernierChangement = 0L; }
    public boolean superStep(long duree) { return super.onStep(duree); }

	private void chooseRandomDirection() {
		ThreadLocalRandom rnd = ThreadLocalRandom.current();

		// Réinitialise les vitesses à 0
		horizontalSpeed = 0;
		verticalSpeed = 0;

		// Tire au sort : 0 -> horizontal, 1 -> vertical
		int axis = rnd.nextInt(2);

		// Tire au sort : 1 → positif, -1 → négatif
		int sign = rnd.nextBoolean() ? 1 : -1;

		// Applique la direction sur l'axe choisi
		double speed = vitesseBase * sign;

		switch (axis) {
			case 0 -> horizontalSpeed = speed;
			case 1 -> verticalSpeed = speed;
        }
    }

	@Override
	public void onCollisionWith(IAnimated other) {
		// Laisse Pacman gérer la logique
	}



	@Override
	public boolean onStep(long duree) {
        return moveStrategy.move(this, duree);
    }

	private GhostState state; // État courant du fantôme

	public void setState(GhostState newState) {
		if (state != null) {
			state.onLeave(this);
		}
		state = newState;
		state.onEnter(this);
	}

	public GhostState getState() {
		return state;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public String getSpriteColorName() {
		return switch (couleur) {
			case ROUGE -> "red";
			case ROSE  -> "pink";
			case BLEU  -> "blue";
			case ORANGE -> "orange";
		};
	}
	public void respawn() {
		this.setDestroyed(false);
	    // Retourne au point de spawn
	    setX(spawnX);
	    setY(spawnY);

	    // Reprend son état normal
	    setVulnerable(false);

	    Sprite normalSprite = game.getSpriteStore()
	            .getSprite("ghosts/" + getSpriteColorName() + "/1");
	    setSprite(normalSprite);

	    setBaseSpeed(50.0);
	    setMoveStrategy(new RandomGhostMoveStrategy());

	    // Redonne une direction aléatoire
	    chooseRandomDirection();
	}
	@Override
    public void collideWithPacman(Pacman pacman) {
        if (state != null) {
            state.onCollideWithPacman(this, pacman);
        }
    }

}
