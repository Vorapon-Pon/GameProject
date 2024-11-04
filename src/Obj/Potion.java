package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class Potion extends Unit {
    public Potion(GamePanel gamePanel) {
        super(gamePanel);
        name = "potion";
        behavior = "idle";
        image = setUp("/Obj/Potion1", gamePanel.TileSize, gamePanel.TileSize);
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW;
        collision = true;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[6];
        for(int i = 0; i < 6; i++) {
            idleBowRight[i] = setUp(folderPath + "/Potion" + (i+1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }
}