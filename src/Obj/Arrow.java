package Obj;

import Entity.Projectile;
import Entity.Unit;
import Main.Equip;
import Main.GamePanel;
import java.awt.image.BufferedImage;

public class Arrow extends Projectile {

    GamePanel gamePanel;

    public Arrow(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "arrow";
        speed = 10;
        maxHealth = 70;
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
            walkBowRightSprites[i] = setUp(folderPath + "/ArrowR" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
            walkBowLeftSprites[i] = setUp(folderPath + "/ArrowL" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
        }

        for(int i = 0; i < 6; i++) {
            idleBowRight[i] = setUp(folderPath + "/ArrowR" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
            idleBowLeft[i] = setUp(folderPath + "/ArrowL" + (i % 3 + 1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }

    public boolean haveArrow(Unit ent) {

        boolean haveArr = false;
        if(ent.arrow >= useArrow) {
            haveArr = true;
        }

        return  haveArr;
    }

    public void spendArrow(Unit ent) {
        ent.arrow -= useArrow;
    }
}
