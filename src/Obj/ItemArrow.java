package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class ItemArrow extends Unit {

    GamePanel gamePanel;

    public ItemArrow(GamePanel gamePanel) {
        super(gamePanel);
        name = "ItemArrow1";
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
            idleBowRight[i] = setUp(folderPath + "/ItemArrow1_" + (i+1));
        }
    }
}
