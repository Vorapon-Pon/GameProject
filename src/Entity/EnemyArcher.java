package Entity;

import Main.Direction;
import Main.Equip;
import Main.GamePanel;
import Obj.EnemyArrow;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyArcher extends Unit {

    private final int attackRange = 5; // Archer's attack range
    private boolean isShooting = false;
    private boolean isStayingStill = false;
    private int stayStillCounter = 0; // Counter to control stay-still duration
    private final int stayStillDuration = 30; // Duration for staying still, e.g., 1 second at 60 FPS


    public EnemyArcher(GamePanel gamePanel) {
        super(gamePanel);
        type = 2;
        Direction facingDirection = Direction.LEFT;
        behavior = "idle";
        equipWeapon = Equip.BOW;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxHealth = 60;
        health = maxHealth;
        projectile = new EnemyArrow(gamePanel);

        loadSprites("/Enemy");
    }

    public void update() {
        super.update();
        // Decrement cooldowns if they are greater than zero
        if (shotCooldown > 0) {
            shotCooldown--;
        } else {
            isShooting = false; // Reset isShooting once cooldown completes
        }
    }

    private void facePlayer() {
        if (worldX < gamePanel.player.worldX) {
            facingDirection = Direction.RIGHT;
        } else {
            facingDirection = Direction.LEFT;
        }
    }

    public void stayStill() {
        if(isStayingStill) {
            behavior = "idle";
        }
        stayStillCounter = stayStillDuration; // Start counter for 1 second
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

    public void setAction() {

        if (isStayingStill) {
            stayStillCounter--;
            if (stayStillCounter <= 0) {
                isStayingStill = false; // End the stay-still period
            }
            return; // Skip further actions while staying still
        }

        int unitIndex = gamePanel.collisionCheck.checkUnit(this, gamePanel.enemy);
        collideUnit(unitIndex);

        int distanceX = worldX - gamePanel.player.worldX;
        int distanceY = worldY - gamePanel.player.worldY;
        int distanceToPlayer = (distanceX + distanceY) / gamePanel.TileSize;

        if (distanceToPlayer <= attackRange) {
            if (!isShooting) {
                onPath = false;
                facePlayer();
                isAttacking = true;
                attack();
                isShooting = true;
            }
        } else {
            isShooting = false; // Stop shooting if the player is out of attack range
        }

        actionCounter++;
        if (onPath) {
            int goalCol;
            int goalRow;
            if(gamePanel.player.worldX > this.worldX) {
                goalCol = (gamePanel.player.worldX + gamePanel.player.collideBox.x - (gamePanel.TileSize * 4)) / gamePanel.TileSize;
            }else {
                goalCol = (gamePanel.player.worldX + gamePanel.player.collideBox.x + (gamePanel.TileSize * 4)) / gamePanel.TileSize;
            }
            goalRow = (gamePanel.player.worldY + gamePanel.player.collideBox.y) / gamePanel.TileSize;

            searchPath(goalCol, goalRow);

            if(distanceToPlayer > 10) {
                onPath = false;
            }
        }else {
            actionCounter++;

            if (actionCounter == 60) {
                Random random = new Random();
                int i = random.nextInt(125) + 1; // random num

                if (i <= 25) {
                    behavior = "up";
                } else if (i <= 50) {
                    behavior = "down";
                } else if (i <= 75) {
                    behavior = "left";
                } else if (i <= 100) {
                    behavior = "right";
                } else {
                    behavior = "idle";
                }

                actionCounter = 0;
            }

            if (distanceToPlayer < 7) {
                int i = new Random().nextInt(100) + 1;
                if (i > 50) {
                    onPath = true;
                }
            }
        }
    }

    @Override
    public void attack() {
        onPath = false;
        if (shotCooldown <= 0 && !projectile.isAlive) { // Only attack if cooldown is ready
            projectile.set(worldX, worldY, String.valueOf(facingDirection), true, this);
            gamePanel.projectileList.add(projectile);
            spriteIndex = 0;
            isAttacking = true;
            isStayingStill = true;
            stayStill();
            shotCooldown = 240; // Reset cooldown
            isShooting = false; // Ready for the next shot when cooldown completes
        }
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[6];
        idleBowLeft = new BufferedImage[6];
        walkBowLeftSprites = new BufferedImage[10];
        walkBowRightSprites = new BufferedImage[10];
        AttackBowR = new BufferedImage[6];
        AttackBowL = new BufferedImage[6];

        // Load each frame
        for (int i = 0; i < 10; i++) {
            walkBowLeftSprites[i] = setUp(folderPath + "/EnemyBowRunLeft" + (i+1));
            walkBowRightSprites[i] = setUp(folderPath + "/EnemyBowRunRight" + (i+1));
        }

        for(int i = 0; i < 6; i++) {
            AttackBowR[i] = setUp(folderPath + "/EnemyBowAttackRight" + (i+1));
            AttackBowL[i] = setUp(folderPath + "/EnemyBowAttackLeft" + (i+1));
            idleBowRight[i] = setUp(folderPath + "/EnemyBowIdleR" + (i+1));
            idleBowLeft[i] = setUp(folderPath + "/EnemyBowIdleL" + (i+1));
        }
    }

    public void damageReaction() {
        actionCounter = 0;
        onPath = true;

    }
}
