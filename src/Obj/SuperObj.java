package Obj;

import Main.GamePanel;
import Main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObj {
    UtilityTool uTool = new UtilityTool();
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(10,10,60,60);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = (int) (worldX - gamePanel.player.worldX + gamePanel.player.screenX);
        int screenY = (int) (worldY - gamePanel.player.worldY + gamePanel.player.screenY);

        // Only draw tiles that are within the screen bounds
        if (screenX + gamePanel.TileSize > 0 && screenX < gamePanel.screenWidth / gamePanel.scaleFactor &&
                screenY + gamePanel.TileSize > 0 && screenY < gamePanel.screenHeight / gamePanel.scaleFactor) {
            g2.drawImage(image, screenX, screenY,
                    (int)(gamePanel.TileSize), (int)(gamePanel.TileSize), null);
        }
    }
}
