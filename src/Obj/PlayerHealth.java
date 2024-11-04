package Obj;

import Entity.Unit;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerHealth extends Unit {
    public BufferedImage[] image = new BufferedImage[11];
    public PlayerHealth(GamePanel gamePanel) {
        super((gamePanel));
        name = "PlayerHealth";
        image[0] = setUp("/Obj/HealthBar" + 1 , gamePanel.TileSize, gamePanel.TileSize);
        for(int i = 1; i < 11; i++) {
            image[i] = setUp("/Obj/HealthBar" + (i+1), gamePanel.TileSize, gamePanel.TileSize);
        }
    }

    @Override
    public void attack() {

    }

    @Override
    public void loadSprites(String folderPath) {

    }
}
