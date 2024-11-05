package Entity;

import Main.Direction;
import Main.GamePanel;

public class Projectile extends Unit{

    Unit ent;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String facing, boolean alive, Unit ent) {
        this.worldX = worldX;
        this.worldY = worldY - 30; // -30 adjust the arrow to come out the right position
        this.facingDirection = Direction.valueOf(facing);
        this.isAlive = alive;
        this.ent = ent;
        this.health = this.maxHealth;
    }

    public void update() {

        gamePanel.collisionCheck.checkTile(this);

        if(ent == gamePanel.player) {
            int enmeyIndex = gamePanel.collisionCheck.checkUnit(this,gamePanel.enemy);
            if(enmeyIndex != 999) {
                gamePanel.player.damageUnit(enmeyIndex, 20);// player damage arrow
                isAlive = false;
            }
        }else {
            boolean contactPlayer = gamePanel.collisionCheck.checkPlayer(this);
            if(!gamePanel.player.isInvincible && contactPlayer){
                damagePlayer(10);// monster damage arrow
                isAlive = false;
            }
        }
        switch (facingDirection) {
            case RIGHT :
                worldX += speed;
                break;
            case LEFT :
                worldX -= speed;
                break;
        }

        if(collisionOn) {
            isAlive = false;
        }

        health--;
        if(health <= 0) {
            isAlive = false;
        }

        spriteCounter++;
        if (spriteCounter > 6) {
            spriteIndex++;
            if (spriteIndex >= animationSpeed) { // Loop through the frames
                spriteIndex = 0;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveArrow(Unit ent) {
        return false;
    }

    public void spendArrow(Unit ent) {
        ent.arrow--;
    }
}
