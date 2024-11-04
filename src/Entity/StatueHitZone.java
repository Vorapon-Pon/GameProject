package Entity;

import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StatueHitZone extends Unit {

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
        maxHealth = 500;
        health = maxHealth;

        loadSprites("/Enemy");
    }

    public void collideUnit(int index) {

    }

    public void update() {
        super.update();
    }

    public void setAction() {
        if (isPlayerInRangeAndAligned()) {
            spriteIndex = 0;
            isAttacking = true;
            attack();
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
