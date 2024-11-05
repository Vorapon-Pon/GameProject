package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class akey extends Unit {
    public akey(GamePanel gamePanel) {
        super(gamePanel);
        name = "akey";
        behavior = "idle";
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[10];
        for(int i = 0; i < 10; i++) {
            idleBowRight[i] = setUp(folderPath + "/Akeys" + (i+1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }
}
