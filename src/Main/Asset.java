package Main;

import Entity.EnemyArcher;
import Entity.EnemySword;
import Obj.Bow;
import Obj.ItemArrow;
import Obj.ItemArrow3;
import Obj.Potion;

public class Asset {
    GamePanel gamePanel;

    public Asset(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObj() {
        gamePanel.obj[0] = new Bow(gamePanel);
        gamePanel.obj[0].worldX = gamePanel.TileSize * 10; // item coordinate
        gamePanel.obj[0].worldY = gamePanel.TileSize * 22;

        gamePanel.obj[1] = new ItemArrow(gamePanel);
        gamePanel.obj[1].worldX = gamePanel.TileSize * 10; // item coordinate
        gamePanel.obj[1].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[2] = new ItemArrow(gamePanel);
        gamePanel.obj[2].worldX = gamePanel.TileSize * 10; // item coordinate
        gamePanel.obj[2].worldY = gamePanel.TileSize * 20;

        gamePanel.obj[3] = new ItemArrow3(gamePanel);
        gamePanel.obj[3].worldX = gamePanel.TileSize * 10; // item coordinate
        gamePanel.obj[3].worldY = gamePanel.TileSize * 19;

        gamePanel.obj[4] = new Potion(gamePanel);
        gamePanel.obj[4].worldX = gamePanel.TileSize * 9; // item coordinate
        gamePanel.obj[4].worldY = gamePanel.TileSize * 19;
    }

    public void setEnemy(){
        /*
        gamePanel.enemy[0] = new EnemySword(gamePanel);
        gamePanel.enemy[0].worldX = gamePanel.TileSize * 15;
        gamePanel.enemy[0].worldY = gamePanel.TileSize * 23;

        gamePanel.enemy[1] = new EnemySword(gamePanel);
        gamePanel.enemy[1].worldX = gamePanel.TileSize * 15;
        gamePanel.enemy[1].worldY = gamePanel.TileSize * 23;
        */
        gamePanel.enemy[2] = new EnemyArcher(gamePanel);
        gamePanel.enemy[2].worldX = gamePanel.TileSize * 18 ;
        gamePanel.enemy[2].worldY = gamePanel.TileSize * 23;
    }
}
