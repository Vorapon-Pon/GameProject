package Entity;

import Main.Direction;
import Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SwordMan extends Unit {
    private static final int ATTACK_RANGE = 50; // Range within which the SwordMan will attack
    private static final int MOVE_SPEED = 2; // Speed of the SwordMan
    private Player targetPlayer;


    public SwordMan(GamePanel gamePanel, Player player) throws IOException {
        super(gamePanel);
        this.targetPlayer = player; // Set the target player
        this.worldX = gamePanel.TileSize * 10; // Starting position
        this.worldY = gamePanel.TileSize * 20; // Starting position
        this.facingDirection = Direction.LEFT;
        this.speed = MOVE_SPEED; // Speed of the swordman
        this.maxHealth = 80; // Max health for SwordMan
        this.health = maxHealth;
        this.damage = 15; // Damage dealt by SwordMan

        loadSprites("/SwordMan"); // Load sprites from the same folder as
    }

    @Override
    public void update() {
        super.update(); // Call to parent update method

        double dx = targetPlayer.worldX - worldX;
        double dy = targetPlayer.worldY - worldY;
        double distanceToPlayer = Math.sqrt(dx * dx + dy * dy);

        if (isAlive) {
            if (distanceToPlayer < ATTACK_RANGE) {
                attack(); // Attack if within range
            } else {
                moveTowardsPlayer(dx, dy); // Move towards player if not in range
            }

        }
    }

    private void moveTowardsPlayer(double dx, double dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                worldX += speed; // Move right
                facingDirection = Direction.RIGHT;
                behavior = "right";
            } else {
                worldX -= speed; // Move left
                facingDirection = Direction.LEFT;
                behavior = "left";
            }
        } else {
            if (dy > 0) {
                worldY += speed; // Move down
                behavior = "down";
            } else {
                worldY -= speed; // Move up
                behavior = "up";
            }
        }
    }


    public void attack() {

    }

    @Override
    protected void onDeath() {
        System.out.println("Swordman has died.");
    }

    @Override
    public void loadSprites(String folderPath)  {
        idleRight = new BufferedImage[6];
        idleLeft = new BufferedImage[6];
        walkLeftSprites = new BufferedImage[10];
        walkRightSprites = new BufferedImage[10];
        AttackR = new BufferedImage[10];
        AttackL = new BufferedImage[10];

        try {
            // Load each frame for walking up (assuming files are named "walk_up_0.png", "walk_up_1.png", etc.)

            for (int i = 0; i < 10; i++) {
                walkLeftSprites[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManRunLeft" + (i+1) + ".png"));
                walkRightSprites[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManRunRight_" + (i+1) + ".png"));
                AttackR[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManAttackRight" + (i+1) + ".png"));
                AttackL[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManAttackLeft" + (i+1) + ".png"));
            }

            for(int i = 0; i < 6; i++) {
                idleRight[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManIdle_" + (i+1)+ ".png"));
                idleLeft[i] = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManEnemyIdleIdleLeft" + (i+1)+ ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
