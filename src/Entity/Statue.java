package Entity;

import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Statue extends Unit {

    public Statue(GamePanel gamePanel) {
        super(gamePanel);
        type = 2;
        Direction facingDirection = Direction.LEFT;
        behavior = "idle";
        equipWeapon = Equip.SWORD; // Set initial weapon to SWORD
        collideBox = new Rectangle(0,0,0,0);
        defaultSpeed = 0;
        damage = 15;
        speed = defaultSpeed;
        maxHealth = 500;
        health = maxHealth;

        loadSprites("/Enemy");
    }

    public void update() {
        super.update();
    }

    public void setAction() {

    }

    @Override
    public void attack() {

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
            walkLeftSprites[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);
            walkRightSprites[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);

        }

        for(int i = 0; i < 6; i++) {
            AttackR[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);
            AttackL[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);
            idleRight[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);
            idleLeft[i] = setUp(folderPath + "/Statue" , gamePanel.TileSize * 2 , gamePanel.TileSize*6);
        }
    }


}
