package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileSet {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTile; // x 48
    boolean drawPath = true;

    public TileSet(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[100];//type of tile
        mapTile = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTile();
        loadMap("/Map/map1.txt");
    }

    public void getTile() {
        setUp(0, "Tile1", false);
        setUp(1, "Tile2", false);
        setUp(2, "Tile3", true);
        setUp(3, "Floor", false);

        for(int i = 4; i < 8; i++) {
            setUp(i,"Borderleft"+(i - 3), false);
        }
        setUp(8,"Borderleft5", true);

        for(int i = 9; i < 12; i++) {
            setUp(i,"Bordertop"+(i - 8), false);
        }

        for(int i = 12; i < 17; i++) {
            setUp(i,"Borderright"+(i - 11), true);
        }
        for(int i = 17; i < 20; i++) {
            setUp(i,"Boderbottom"+(i - 16), true);
        }

        for(int i = 20; i < 23; i++) {
            setUp(i,"CampFloor"+(i - 19), false);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTile[worldCol][worldRow];
            int worldX = worldCol * gamePanel.TileSize;
            int worldY = worldRow * gamePanel.TileSize;
            int screenX =  worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            if (worldX + gamePanel.TileSize * 15 > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.TileSize * 15 < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.TileSize * 8 > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.TileSize * 8 < gamePanel.player.worldY + gamePanel.player.screenY) {

                g2.drawImage (tiles[tileNum].image, screenX, screenY, gamePanel.TileSize, gamePanel.TileSize, null);
            }
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        if(drawPath) {
            g2.setColor(new Color(255,0,0,70));
            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++) {
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.TileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.TileSize;
                int screenX =  worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                g2.fillRect(screenX,screenY,gamePanel.TileSize,gamePanel.TileSize );
            }
        }
    }

    public void setUp(int index, String imagePath, Boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/Tile/" + imagePath + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gamePanel.TileSize, gamePanel.TileSize);
            tiles[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath); // import map
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read content of txt file

            int col = 0;
            int row = 0;

            while (row < gamePanel.maxWorldRow && col < gamePanel.maxWorldCol) {
                String line = br.readLine(); // read inline txt file

                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTile[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
