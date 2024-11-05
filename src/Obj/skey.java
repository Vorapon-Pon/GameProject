package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class skey extends Unit {
    public skey(GamePanel gamePanel) {
        super(gamePanel);
        name = "skey";
        behavior = "idle";
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[10];
        for(int i = 0; i < 10; i++) {
            idleBowRight[i] = setUp(folderPath + "/Skeys" + (i+1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }
}
