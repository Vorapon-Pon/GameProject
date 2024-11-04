package Entity;

import Main.GamePanel;

public abstract class Enemy extends Unit {

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        this.health = 100;
    }

    public void update() {
        // Common behavior for all enemies (e.g., collision detection, movement logic)
        super.update();
    }

    // Abstract methods to be implemented by specific enemy types
    public abstract void moveTowardsPlayer();

    public abstract void attack();
}
