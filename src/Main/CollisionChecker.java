package Main;

import Entity.Player;
import Entity.Unit;
import Tile.TileSet;

public class CollisionChecker {
    GamePanel gamePanel;
    TileSet tileSet;

    public CollisionChecker(GamePanel gamePanel, TileSet tileSet) {
        this.gamePanel = gamePanel;
        this.tileSet = tileSet;
    }

    public void checkTile(Unit ent) {
        int entityLeftWorldX = ent.worldX + ent.collideBox.x;
        int entityRightWorldX = ent.worldX + ent.collideBox.x + ent.collideBox.width;
        int entityTopWorldY = ent.worldY + ent.collideBox.y;
        int entityBottomWorldY = ent.worldY + ent.collideBox.y + ent.collideBox.height;

        int entityLeft = entityLeftWorldX / gamePanel.TileSize;
        int entityRight = entityRightWorldX / gamePanel.TileSize;
        int entityTop = entityTopWorldY / gamePanel.TileSize;
        int entityBottom = entityBottomWorldY / gamePanel.TileSize;

        int tilenum1, tilenum2;
        switch (ent.behavior) {
            case "up" :
                entityTop = (entityTopWorldY - ent.speed) / gamePanel.TileSize;
                tilenum1 = gamePanel.tileSet.mapTile[entityLeft][entityTop];
                tilenum2 = gamePanel.tileSet.mapTile[entityRight][entityTop];
                if(gamePanel.tileSet.tiles[tilenum1].collision || gamePanel.tileSet.tiles[tilenum2].collision) {
                    ent.collisionOn = true;
                }
                break;
            case "down" :
                entityBottom = (entityBottomWorldY + ent.speed) / gamePanel.TileSize;
                tilenum1 = gamePanel.tileSet.mapTile[entityLeft][entityBottom];
                tilenum2 = gamePanel.tileSet.mapTile[entityRight][entityBottom];
                if(gamePanel.tileSet.tiles[tilenum1].collision || gamePanel.tileSet.tiles[tilenum2].collision) {
                    ent.collisionOn = true;
                }
                break;
            case "left" :
                entityLeft = (entityLeftWorldX - ent.speed) / gamePanel.TileSize;
                tilenum1 = gamePanel.tileSet.mapTile[entityLeft][entityTop];
                tilenum2 = gamePanel.tileSet.mapTile[entityLeft][entityBottom];
                if(gamePanel.tileSet.tiles[tilenum1].collision || gamePanel.tileSet.tiles[tilenum2].collision) {
                    ent.collisionOn = true;
                }
                break;
            case "right" :
                entityRight = (entityRightWorldX + ent.speed) / gamePanel.TileSize;
                tilenum1 = gamePanel.tileSet.mapTile[entityRight][entityTop];
                tilenum2 = gamePanel.tileSet.mapTile[entityRight][entityBottom];
                if(gamePanel.tileSet.tiles[tilenum1].collision || gamePanel.tileSet.tiles[tilenum2].collision) {
                    ent.collisionOn = true;
                }
                break;
        }
    }

    public int checkObj(Unit ent, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {

                ent.collideBox.x = ent.worldX + ent.collideBox.x;
                ent.collideBox.y = ent.worldY + ent.collideBox.y;

                gamePanel.obj[i].collideBox.x =  gamePanel.obj[i].worldX + gamePanel.obj[i].collideBox.x;
                gamePanel.obj[i].collideBox.y =  gamePanel.obj[i].worldY + gamePanel.obj[i].collideBox.y;

                switch (ent.behavior) {
                    case "up":
                        ent.collideBox.y -= ent.speed;
                        break;
                    case "down":
                        ent.collideBox.y += ent.speed;
                        break;
                    case "left":
                        ent.collideBox.x -= ent.speed;
                        break;
                    case "right":
                        ent.collideBox.x += ent.speed;
                        break;
                }

                if(ent.collideBox.intersects(gamePanel.obj[i].collideBox)) {
                    if(player) {
                        index = i;
                        if(gamePanel.obj[i].collision) {
                            ent.collisionOn = true;
                        }
                    }else {
                        ent.collisionOn = false; // prevent unit stuck add the item obj
                    }
                }

                ent.collideBox.x = ent.solidAreaDefaultX;
                ent.collideBox.y = ent.solidAreaDefaultY;
                gamePanel.obj[i].collideBox.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].collideBox.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public int checkUnit(Unit ent, Unit[] target) {
        int index = 999;

        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {
                ent.collideBox.x = ent.worldX + ent.collideBox.x;
                ent.collideBox.y = ent.worldY + ent.collideBox.y;

                target[i].collideBox.x  = target[i].worldX + target[i].collideBox.x;
                target[i].collideBox.y = target[i].worldY + target[i].collideBox.y;

                switch (ent.behavior) {
                    case "up" :
                        ent.collideBox.y -=  ent.speed;
                        break;
                    case "down" :
                        ent.collideBox.x += ent.speed;
                        break;
                    case "left" :
                        ent.collideBox.x -= ent.speed;
                        break;
                    case "right" :
                        ent.collideBox.x += ent.speed;
                        break;
                }
                if(ent.collideBox.intersects(target[i].collideBox)) {
                    ent.collisionOn = true;
                    index = i;
                }
                ent.collideBox.x = ent.solidAreaDefaultX;
                ent.collideBox.y = ent.solidAreaDefaultY;
                target[i].collideBox.x = target[i].solidAreaDefaultX;
                target[i].collideBox.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }


    public boolean checkPlayer(Unit ent) {
        boolean contactPlayer = false;
        ent.collideBox.x = ent.worldX + ent.collideBox.x;
        ent.collideBox.y = ent.worldY + ent.collideBox.y;

        gamePanel.player.collideBox.x  = gamePanel.player.worldX + gamePanel.player.collideBox.x;
        gamePanel.player.collideBox.y = gamePanel.player.worldY + gamePanel.player.collideBox.y;

        switch (ent.behavior) {
            case "up" :
                ent.collideBox.y -=  ent.speed;
                break;
            case "down" :
                ent.collideBox.y += ent.speed;
                break;
            case "left" :
                ent.collideBox.x -= ent.speed;
                break;
            case "right" :
                ent.collideBox.x += ent.speed;
                break;
        }
        if(ent.collideBox.intersects(gamePanel.player.collideBox)) {
            ent.collisionOn = true;
            contactPlayer = true;
        }
        ent.collideBox.x = ent.solidAreaDefaultX;
        ent.collideBox.y = ent.solidAreaDefaultY;
        gamePanel.player.collideBox.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.collideBox.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }


}
