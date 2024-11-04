package Obj;

import Entity.Projectile;
import Main.Equip;
import Main.GamePanel;
import java.awt.image.BufferedImage;

public class EnemyArrow extends Projectile {

    GamePanel gamePanel;

    public EnemyArrow(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "arrow";
        speed = 8;
        maxHealth = 100;
        health = maxHealth;
        equipWeapon = Equip.BOW;
        damage = 20;
        isAlive = false;
        useArrow = 1;
        loadSprites("/Obj");
    }

    public void loadSprites(String folderPath) {
        walkBowRightSprites = new BufferedImage[10];
        walkBowLeftSprites = new BufferedImage[10];
        idleBowRight = new BufferedImage[6];
        idleBowLeft = new BufferedImage[6];

        for(int i = 0; i < 10; i++) {
            walkBowRightSprites[i] = setUp(folderPath + "/EnemyArrowR" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
            walkBowLeftSprites[i] = setUp(folderPath + "/EnemyArrowL" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
        }

        for(int i = 0; i < 6; i++) {
            idleBowRight[i] = setUp(folderPath + "/EnemyArrowR" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
            idleBowLeft[i] = setUp(folderPath + "/EnemyArrowL" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }
}
