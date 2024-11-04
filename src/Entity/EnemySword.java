package Entity;

import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemySword extends Unit {

    public EnemySword(GamePanel gamePanel) {
        super(gamePanel);
        type = 2;
        Direction facingDirection = Direction.LEFT;
        behavior = "idle";
        equipWeapon = Equip.SWORD; // Set initial weapon to SWORD
        defaultSpeed = 2;
        damage = 15;
        speed = defaultSpeed;
        maxHealth = 80;
        health = maxHealth;

        loadSprites("/Enemy");
    }

    public void collideUnit(int index) {
        if (index != 999) {
            Unit otherEnemy = gamePanel.enemy[index]; // Get the colliding enemy unit

            // Calculate vector direction away from the other enemy
            int deltaX = this.worldX - otherEnemy.worldX;
            int deltaY = this.worldY - otherEnemy.worldY;

            // Calculate the vector direction from this enemy to the player
            int playerDeltaX = this.worldX - gamePanel.player.worldX;
            int playerDeltaY = this.worldY - gamePanel.player.worldY;

            // Normalize to get unit vectors
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            double playerDistance = Math.sqrt(playerDeltaX * playerDeltaX + playerDeltaY * playerDeltaY);

            if (distance > 0) {
                // Calculate the nudge distance
                double nudgeDistance = 2;

                // Only apply nudge if it doesn't push the enemy closer to the player
                double newEnemyX = this.worldX + (nudgeDistance * (deltaX / distance));
                double newEnemyY = this.worldY + (nudgeDistance * (deltaY / distance));

                // Check if the new position brings this enemy closer to the player
                double newPlayerDistance = Math.sqrt((newEnemyX - gamePanel.player.worldX) * (newEnemyX - gamePanel.player.worldX) +
                        (newEnemyY - gamePanel.player.worldY) * (newEnemyY - gamePanel.player.worldY));

                if (newPlayerDistance > playerDistance) {
                    // Only apply nudge if it won't push the enemy closer to the player
                    this.worldX = (int) newEnemyX;
                    this.worldY = (int) newEnemyY;
                }
            }
        }
    }

    public void update() {
        super.update();
    }

    public void setAction() {

        int unitIndex = gamePanel.collisionCheck.checkUnit(this, gamePanel.enemy);
        collideUnit(unitIndex);

        int distanceX = Math.abs(worldX - gamePanel.player.worldX);
        int distanceY = Math.abs(worldY - gamePanel.player.worldY);
        int distanceToPlayer = (distanceX + distanceY) / gamePanel.TileSize;

        if (isPlayerInRangeAndAligned()) {
            spriteIndex = 0;
            isAttacking = true;
            attack();
        }

        if (onPath) {
            int goalCol = (gamePanel.player.worldX + gamePanel.player.collideBox.x) / gamePanel.TileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.collideBox.y) / gamePanel.TileSize;

            searchPath(goalCol, goalRow);

            if (distanceToPlayer > 10) {
                onPath = false;
            }
        }else {
            actionCounter++;

            if(actionCounter == 60) {
                Random random = new Random();
                int i = random.nextInt(125) + 1; // random num

                if(i <= 25) {
                    behavior = "up";
                }else if(i <= 50) {
                    behavior = "down";
                }else if(i <= 75) {
                    behavior = "right";
                }else if(i <= 100) {
                    behavior = "left";
                }else  {
                    behavior = "idle";
                }

                actionCounter = 0;
            }

            if (distanceToPlayer < 5) {
                int i = new Random().nextInt(100) + 1;
                if (i > 50) {
                    onPath = true;
                }
            }
        }
    }

    @Override
    public void attack() {

    }

    private boolean isPlayerInRangeAndAligned() {
       if (worldY <= gamePanel.player.collideBox.y + gamePanel.player.collideBox.height &&
           worldY >= gamePanel.player.collideBox.y - gamePanel.player.collideBox.height) {

           return gamePanel.player.collideBox.width <= this.collideBox.x + this.collideBox.width;
       }
        return false;
    }

    @Override
    public void loadSprites(String folderPath) {
        idleRight = new BufferedImage[6];
        idleLeft = new BufferedImage[6];
        walkLeftSprites = new BufferedImage[10];
        walkRightSprites = new BufferedImage[10];
        AttackR = new BufferedImage[6];
        AttackL = new BufferedImage[6];

        // Load each frame
        for (int i = 0; i < 10; i++) {
            walkLeftSprites[i] = setUp(folderPath + "/EnemySwordRunLeft" + (i+1));
            walkRightSprites[i] = setUp(folderPath + "/EnemySwordRunRight" + (i+1));

        }

        for(int i = 0; i < 6; i++) {
            AttackR[i] = setUp(folderPath + "/EnemySwordAttackRight" + (i+1));
            AttackL[i] = setUp(folderPath + "/EnemySwordAttackLeft" + (i+1));
            idleRight[i] = setUp(folderPath + "/SwordEnemyIdleIdleRight" + (i+1));
            idleLeft[i] = setUp(folderPath + "/SwordIdleIdleLeft" + (i+1));
        }
    }

    public void damageReaction() {
        actionCounter = 0;
        onPath = true;
    }
}