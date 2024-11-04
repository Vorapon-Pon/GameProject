package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class ItemArrow3 extends Unit {

    GamePanel gamePanel;

    public ItemArrow3(GamePanel gamePanel) {
        super(gamePanel);
        name = "ItemArrow3";
        behavior = "idle";
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW;
        collision = true;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[6];
        for(int i = 0; i < 6; i++) {
            idleBowRight[i] = setUp(folderPath + "/ItemArrow3_" + (i+1));
        }
    }
}
