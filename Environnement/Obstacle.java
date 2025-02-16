package Environnement;

public class Obstacle {
    private Case position;

    public Obstacle(Case position) {
        this.position = position;
    }

    public Case getPosition() {
        return position;
    }

	public void setPosition(Case position) {
		this.position = position;
	}
}
