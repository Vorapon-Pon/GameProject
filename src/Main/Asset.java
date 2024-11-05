package Main;

import Entity.EnemyArcher;
import Entity.EnemySword;
import Entity.StatueHitZone;
import Entity.Statue;
import Obj.*;
import java.util.Random;

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
        gamePanel.obj[1].worldX = gamePanel.TileSize * 10;
        gamePanel.obj[1].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[2] = new ItemArrow(gamePanel);
        gamePanel.obj[2].worldX = gamePanel.TileSize * 10;
        gamePanel.obj[2].worldY = gamePanel.TileSize * 20;

        gamePanel.obj[3] = new ItemArrow3(gamePanel);
        gamePanel.obj[3].worldX = gamePanel.TileSize * 10;
        gamePanel.obj[3].worldY = gamePanel.TileSize * 19;

        gamePanel.obj[4] = new Potion(gamePanel);
        gamePanel.obj[4].worldX = gamePanel.TileSize * 20;
        gamePanel.obj[4].worldY = gamePanel.TileSize * 19;

        gamePanel.obj[5] = new Campfire(gamePanel);
        gamePanel.obj[5].worldX = gamePanel.TileSize * 6;
        gamePanel.obj[5].worldY = gamePanel.TileSize * 22;

        gamePanel.obj[6] = new wkey(gamePanel);
        gamePanel.obj[6].worldX = gamePanel.TileSize * 6;
        gamePanel.obj[6].worldY = gamePanel.TileSize * 20;

        gamePanel.obj[7] = new akey(gamePanel);
        gamePanel.obj[7].worldX = gamePanel.TileSize * 5;
        gamePanel.obj[7].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[8] = new skey(gamePanel);
        gamePanel.obj[8].worldX = gamePanel.TileSize * 6;
        gamePanel.obj[8].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[9] = new dkey(gamePanel);
        gamePanel.obj[9].worldX = gamePanel.TileSize * 7;
        gamePanel.obj[9].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[10] = new Potion(gamePanel);
        gamePanel.obj[10].worldX = gamePanel.TileSize * 2;
        gamePanel.obj[10].worldY = gamePanel.TileSize * 3;
    }

    public void setEnemy(){

        gamePanel.enemy[0] = new EnemySword(gamePanel);
        gamePanel.enemy[0].worldX = gamePanel.TileSize * 21;
        gamePanel.enemy[0].worldY = gamePanel.TileSize * 8;

        gamePanel.enemy[1] = new EnemySword(gamePanel);
        gamePanel.enemy[1].worldX = gamePanel.TileSize * 4;
        gamePanel.enemy[1].worldY = gamePanel.TileSize * 3;


        gamePanel.enemy[2] = new EnemyArcher(gamePanel);
        gamePanel.enemy[2].worldX = gamePanel.TileSize * 38 ;
        gamePanel.enemy[2].worldY = gamePanel.TileSize * 3;

        gamePanel.enemy[3] = new StatueHitZone(gamePanel);
        gamePanel.enemy[3].worldX = gamePanel.TileSize * 40;
        gamePanel.enemy[3].worldY = gamePanel.TileSize * 18;


        gamePanel.enemy[4] = new Statue(gamePanel);
        gamePanel.enemy[4].worldX = gamePanel.TileSize * 40;
        gamePanel.enemy[4].worldY = gamePanel.TileSize * 14;

        for(int i = 5; i < 11;i++) {
            gamePanel.enemy[i] = null;
        }

    }

    public void revive() {
        Random random = new Random();
        int i = random.nextInt(125);

        if(gamePanel.enemy[5] == null && gamePanel.enemy[6] == null && gamePanel.enemy[7] == null) {
            gamePanel.enemy[5] = new EnemySword(gamePanel);
            gamePanel.enemy[5].worldX = gamePanel.TileSize * 42; // enemy spawn
            gamePanel.enemy[5].worldY = gamePanel.TileSize * 16;

            gamePanel.enemy[6] = new EnemyArcher(gamePanel);
            gamePanel.enemy[6].worldX = gamePanel.TileSize * 40 ; // enemy spawn
            gamePanel.enemy[6].worldY = gamePanel.TileSize * 20;

            gamePanel.enemy[7] = new EnemySword(gamePanel);
            gamePanel.enemy[7].worldX = gamePanel.TileSize * 43 ; // enemy spawn
            gamePanel.enemy[7].worldY = gamePanel.TileSize * 19;
        }else {
            gamePanel.enemy[8] = new EnemyArcher(gamePanel);
            gamePanel.enemy[8].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[8].worldY = gamePanel.TileSize * 18;

            gamePanel.enemy[9] = new EnemyArcher(gamePanel);
            gamePanel.enemy[9].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[9].worldY = gamePanel.TileSize * 20;

            gamePanel.enemy[10] = new EnemySword(gamePanel);
            gamePanel.enemy[10].worldX = gamePanel.TileSize * 42; // enemy spawn
            gamePanel.enemy[10].worldY = gamePanel.TileSize * 16;
        }

    }

}
