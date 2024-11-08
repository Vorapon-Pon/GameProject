package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class Bow extends Unit {
    public Bow(GamePanel gamePanel) {
        super(gamePanel);
        name = "Bow";
        behavior = "idle";
        image = setUp("/Obj/Bow", gamePanel.TileSize, gamePanel.TileSize);
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW; //idk other way to do rendering order
        collision = true;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[6];
        for(int i = 0; i < 6; i++) {
            idleBowRight[i] = setUp(folderPath + "/Bow", gamePanel.TileSize, gamePanel.TileSize);
        }
    }
}
