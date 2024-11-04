package Obj;

import Entity.Unit;
import Main.Direction;
import Main.Equip;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Campfire extends Unit {
    public Campfire(GamePanel gamePanel) {
        super(gamePanel);
        name = "fire";
        behavior = "idle";
        image = setUp("/Obj/Fire1");
        facingDirection = Direction.RIGHT;
        equipWeapon = Equip.BOW;
        collideBox = new Rectangle(0,0,60,40);
        collision = true;
        loadSprites("/Obj");
    }

    @Override
    public void loadSprites(String folderPath) {
        idleBowRight = new BufferedImage[8];
        for(int i = 0; i < 8; i++) {
            idleBowRight[i] = setUp(folderPath + "/Campfire" + (i+1));
        }
    }
}
