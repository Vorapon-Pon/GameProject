package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileSet {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTile;
    boolean drawPath = true;
    ArrayList<String> mapFile = new ArrayList<>();
    ArrayList<String> mapData = new ArrayList<>();

    public TileSet(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        InputStream is = getClass().getResourceAsStream("/Map/mapdata001.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
           while ((line = br.readLine()) != null) {
               mapFile.add(line);
               mapData.add(br.readLine());
           }
           br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tiles = new Tile[mapFile.size()];//type of tile

        getTile();

        is = getClass().getResourceAsStream("/Map/map001.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            gamePanel.maxWorldCol = maxTile.length;
            gamePanel.maxWorldRow = maxTile.length;

            br.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        mapTile = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTile();

        loadMap("/Map/map001.txt");
    }

    public void getTile() {

        for(int i = 0; i < mapFile.size();i++) {
            String tile;
            boolean collision;

            tile =  mapFile.get(i);

            if(mapData.get(i).equals("true")) {
                collision = true;
            }else {
                collision = false;
            }

            setUp(i, tile, collision);
        }
       /*
        setUp(0, "Tile1", false);
        setUp(1, "Tile2", true);
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

        for(int i = 23; i < 25; i++) {
            setUp(i,"innerBorder"+(i - 22), false);
        }

        setUp(25,"innerBorder"+(25 - 22), true);
        setUp(26,"innerBorder"+(26 - 22), false);
        */
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
        /*
        if(drawPath) {
            g2.setColor(new Color(255,0,0,70));
            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++) {
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.TileSize / 2;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.TileSize / 2;
                int screenX =  worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                g2.fillRect(screenX,screenY,gamePanel.TileSize,gamePanel.TileSize );
            }
        }
        */
    }

    public void setUp(int index, String imagePath, Boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/Tile/" + imagePath));
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