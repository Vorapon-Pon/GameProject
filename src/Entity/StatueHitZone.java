package Entity;

import Main.Asset;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StatueHitZone extends Unit {
    private  boolean isAttacked = false;
    private int attackRange = 9;
    private boolean isSpawn = false;
    private int spawnCooldown = 1200;
    private int spawnCounter = 0;

    public StatueHitZone(GamePanel gamePanel) {
        super(gamePanel);
        type = 2;
        Direction facingDirection = Direction.LEFT;
        behavior = "idle";
        equipWeapon = Equip.SWORD; // Set initial weapon to SWORD
        collideBox = new Rectangle(0,0,110,80);
        defaultSpeed = 0;
        damage = 15;
        speed = defaultSpeed;
        maxHealth = 1000;
        health = maxHealth;
        statue = true;

        loadSprites("/Enemy");
    }

    public void collideUnit(int index) {

    }

    public void update() {
        super.update();
    }

    public void setAction() {

        if(spawnCounter > 0) {
            spawnCounter--;
        }else {
            isSpawn = false;
        }

        if (isPlayerInRange() && !isSpawn && isAttacked) {
            spriteIndex = 0;
            isSpawn = true;
            attack();
        }

        if(health < maxHealth) {
            isAttacked = true;
        }

    }

    @Override
    public void attack() {
        if (isSpawn && isAlive) {
            gamePanel.playSE(11);
            spawnCounter = spawnCooldown;
            new Thread(() -> gamePanel.asset.revive()).start();
        }
    }

    private boolean isPlayerInRange() {
        // Calculate distance between statue and player
        int distanceX = Math.abs(this.worldX - gamePanel.player.worldX);
        int distanceY = Math.abs(this.worldY - gamePanel.player.worldY);

        // Convert distance to tiles (assuming TileSize is in pixels)
        int distanceToPlayer = (distanceX + distanceY) / gamePanel.TileSize;

        // Check if the player is within the defined range
        return distanceToPlayer <= attackRange;
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
            walkLeftSprites[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);
            walkRightSprites[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);

        }

        for(int i = 0; i < 6; i++) {
            AttackR[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);
            AttackL[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);
            idleRight[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);
            idleLeft[i] = setUp(folderPath + "/StatueHitZone" , gamePanel.TileSize * 2 , gamePanel.TileSize*2);
        }
    }

    public void damageReaction() {
    }
}
