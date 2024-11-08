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
        gamePanel.obj[0].worldX = gamePanel.TileSize * 46; // item coordinate
        gamePanel.obj[0].worldY = gamePanel.TileSize * 13;

        gamePanel.obj[1] = new ItemArrow(gamePanel);
        gamePanel.obj[1].worldX = gamePanel.TileSize * 46;
        gamePanel.obj[1].worldY = gamePanel.TileSize * 12;

        gamePanel.obj[2] = new ItemArrow(gamePanel);
        gamePanel.obj[2].worldX = gamePanel.TileSize * 45;
        gamePanel.obj[2].worldY = gamePanel.TileSize * 12;

        gamePanel.obj[3] = new ItemArrow3(gamePanel);
        gamePanel.obj[3].worldX = gamePanel.TileSize * 45;
        gamePanel.obj[3].worldY = gamePanel.TileSize * 13;

        gamePanel.obj[4] = new Potion(gamePanel);
        gamePanel.obj[4].worldX = gamePanel.TileSize * 7;
        gamePanel.obj[4].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[5] = new Campfire(gamePanel);
        gamePanel.obj[5].worldX = gamePanel.TileSize * 7;
        gamePanel.obj[5].worldY = gamePanel.TileSize * 45;

        gamePanel.obj[6] = new wkey(gamePanel);             //MOVEMENT KEY
        gamePanel.obj[6].worldX = gamePanel.TileSize * 7;
        gamePanel.obj[6].worldY = gamePanel.TileSize * 43;

        gamePanel.obj[7] = new akey(gamePanel);             //MOVEMENT KEY
        gamePanel.obj[7].worldX = gamePanel.TileSize * 6;
        gamePanel.obj[7].worldY = gamePanel.TileSize * 44;

        gamePanel.obj[8] = new skey(gamePanel);             //MOVEMENT KEY
        gamePanel.obj[8].worldX = gamePanel.TileSize * 7;
        gamePanel.obj[8].worldY = gamePanel.TileSize * 44;

        gamePanel.obj[9] = new dkey(gamePanel);             //MOVEMENT KEY
        gamePanel.obj[9].worldX = gamePanel.TileSize * 8;
        gamePanel.obj[9].worldY = gamePanel.TileSize * 44;

        gamePanel.obj[10] = new Potion(gamePanel);
        gamePanel.obj[10].worldX = gamePanel.TileSize * 2;
        gamePanel.obj[10].worldY = gamePanel.TileSize * 3;

        gamePanel.obj[11] = new Potion(gamePanel);
        gamePanel.obj[11].worldX = gamePanel.TileSize * 19;
        gamePanel.obj[11].worldY = gamePanel.TileSize * 30;

        gamePanel.obj[12] = new Potion(gamePanel);
        gamePanel.obj[12].worldX = gamePanel.TileSize * 10;
        gamePanel.obj[12].worldY = gamePanel.TileSize * 21;

        gamePanel.obj[13] = new Potion(gamePanel);
        gamePanel.obj[13].worldX = gamePanel.TileSize * 4;
        gamePanel.obj[13].worldY = gamePanel.TileSize * 28;

        gamePanel.obj[14] = new Potion(gamePanel);
        gamePanel.obj[14].worldX = gamePanel.TileSize * 27;
        gamePanel.obj[14].worldY = gamePanel.TileSize * 3;

        gamePanel.obj[15] = new Potion(gamePanel);
        gamePanel.obj[15].worldX = gamePanel.TileSize * 2;
        gamePanel.obj[15].worldY = gamePanel.TileSize * 2;

        gamePanel.obj[16] = new Potion(gamePanel);
        gamePanel.obj[16].worldX = gamePanel.TileSize * 21;
        gamePanel.obj[16].worldY = gamePanel.TileSize * 7;

        gamePanel.obj[17] = new Potion(gamePanel);
        gamePanel.obj[17].worldX = gamePanel.TileSize * 10;
        gamePanel.obj[17].worldY = gamePanel.TileSize * 8;

        gamePanel.obj[18] = new ItemArrow(gamePanel);
        gamePanel.obj[18].worldX = gamePanel.TileSize * 45;
        gamePanel.obj[18].worldY = gamePanel.TileSize * 14;

        gamePanel.obj[19] = new ItemArrow(gamePanel);
        gamePanel.obj[19].worldX = gamePanel.TileSize * 46;
        gamePanel.obj[19].worldY = gamePanel.TileSize * 14;

        gamePanel.obj[20] = new ItemArrow3(gamePanel);
        gamePanel.obj[20].worldX = gamePanel.TileSize * 28;
        gamePanel.obj[20].worldY = gamePanel.TileSize * 24;

        gamePanel.obj[21] = new ItemArrow3(gamePanel);
        gamePanel.obj[21].worldX = gamePanel.TileSize * 29;
        gamePanel.obj[21].worldY = gamePanel.TileSize * 25;

        gamePanel.obj[22] = new Potion(gamePanel);
        gamePanel.obj[22].worldX = gamePanel.TileSize * 28;
        gamePanel.obj[22].worldY = gamePanel.TileSize * 28;

        gamePanel.obj[23] = new Potion(gamePanel);
        gamePanel.obj[23].worldX = gamePanel.TileSize * 29;
        gamePanel.obj[23].worldY = gamePanel.TileSize * 29;

        gamePanel.obj[24] = new Potion(gamePanel);
        gamePanel.obj[24].worldX = gamePanel.TileSize * 27;
        gamePanel.obj[24].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[25] = new ItemArrow(gamePanel);
        gamePanel.obj[25].worldX = gamePanel.TileSize * 28;
        gamePanel.obj[25].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[26] = new ItemArrow(gamePanel);
        gamePanel.obj[26].worldX = gamePanel.TileSize * 27;
        gamePanel.obj[26].worldY = gamePanel.TileSize * 38;

        gamePanel.obj[27] = new Potion(gamePanel);
        gamePanel.obj[27].worldX = gamePanel.TileSize * 23;
        gamePanel.obj[27].worldY = gamePanel.TileSize * 44;

        gamePanel.obj[28] = new Potion(gamePanel);
        gamePanel.obj[28].worldX = gamePanel.TileSize * 23;
        gamePanel.obj[28].worldY = gamePanel.TileSize * 45;

        gamePanel.obj[29] = new ItemArrow(gamePanel);
        gamePanel.obj[29].worldX = gamePanel.TileSize * 23;
        gamePanel.obj[29].worldY = gamePanel.TileSize * 43;

        gamePanel.obj[30] = new ItemArrow(gamePanel);
        gamePanel.obj[30].worldX = gamePanel.TileSize * 23;
        gamePanel.obj[30].worldY = gamePanel.TileSize * 46;

        gamePanel.obj[31] = new Potion(gamePanel);
        gamePanel.obj[31].worldX = gamePanel.TileSize * 36;
        gamePanel.obj[31].worldY = gamePanel.TileSize * 39;

        gamePanel.obj[32] = new ItemArrow3(gamePanel);
        gamePanel.obj[32].worldX = gamePanel.TileSize * 37;
        gamePanel.obj[32].worldY = gamePanel.TileSize * 39;

        gamePanel.obj[33] = new ItemArrow3(gamePanel);
        gamePanel.obj[33].worldX = gamePanel.TileSize * 36;
        gamePanel.obj[33].worldY = gamePanel.TileSize * 40;

        gamePanel.obj[34] = new ItemArrow3(gamePanel);
        gamePanel.obj[34].worldX = gamePanel.TileSize * 41;
        gamePanel.obj[34].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[35] = new ItemArrow3(gamePanel);
        gamePanel.obj[35].worldX = gamePanel.TileSize * 42;
        gamePanel.obj[35].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[36] = new ItemArrow3(gamePanel);
        gamePanel.obj[36].worldX = gamePanel.TileSize * 43;
        gamePanel.obj[36].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[37] = new ItemArrow3(gamePanel);
        gamePanel.obj[37].worldX = gamePanel.TileSize * 44;
        gamePanel.obj[37].worldY = gamePanel.TileSize * 37;

        gamePanel.obj[38] = new ItemArrow3(gamePanel);
        gamePanel.obj[38].worldX = gamePanel.TileSize * 47;
        gamePanel.obj[38].worldY = gamePanel.TileSize * 45;

        gamePanel.obj[39] = new ItemArrow3(gamePanel);
        gamePanel.obj[39].worldX = gamePanel.TileSize * 47;
        gamePanel.obj[39].worldY = gamePanel.TileSize * 46;

        gamePanel.obj[40] = new Potion(gamePanel);
        gamePanel.obj[40].worldX = gamePanel.TileSize * 41;
        gamePanel.obj[40].worldY = gamePanel.TileSize * 46;

        gamePanel.obj[41] = new Potion(gamePanel);
        gamePanel.obj[41].worldX = gamePanel.TileSize * 42;
        gamePanel.obj[41].worldY = gamePanel.TileSize * 46;
    }

    public void setEnemy(){

        gamePanel.enemy[0] = new EnemySword(gamePanel);
        gamePanel.enemy[0].worldX = gamePanel.TileSize * 15;
        gamePanel.enemy[0].worldY = gamePanel.TileSize * 39;

        gamePanel.enemy[1] = new EnemySword(gamePanel);
        gamePanel.enemy[1].worldX = gamePanel.TileSize * 4;
        gamePanel.enemy[1].worldY = gamePanel.TileSize * 3;

        gamePanel.enemy[2] = new EnemySword(gamePanel);
        gamePanel.enemy[2].worldX = gamePanel.TileSize * 26 ;
        gamePanel.enemy[2].worldY = gamePanel.TileSize * 4;

        gamePanel.enemy[3] = new StatueHitZone(gamePanel);
        gamePanel.enemy[3].worldX = gamePanel.TileSize * 45;
        gamePanel.enemy[3].worldY = gamePanel.TileSize * 41;

        gamePanel.enemy[4] = new Statue(gamePanel);
        gamePanel.enemy[4].worldX = gamePanel.TileSize * 45;
        gamePanel.enemy[4].worldY = gamePanel.TileSize * 37;

        gamePanel.enemy[5] = new EnemyArcher(gamePanel);
        gamePanel.enemy[5].worldX = gamePanel.TileSize * 45;
        gamePanel.enemy[5].worldY = gamePanel.TileSize * 11;

        gamePanel.enemy[6] = new EnemySword(gamePanel);
        gamePanel.enemy[6].worldX = gamePanel.TileSize * 19;
        gamePanel.enemy[6].worldY = gamePanel.TileSize * 7;

        gamePanel.enemy[7] = new EnemySword(gamePanel);
        gamePanel.enemy[7].worldX = gamePanel.TileSize * 23;
        gamePanel.enemy[7].worldY = gamePanel.TileSize * 7;

        gamePanel.enemy[11] = new EnemySword(gamePanel);
        gamePanel.enemy[11].worldX = gamePanel.TileSize * 17;
        gamePanel.enemy[11].worldY = gamePanel.TileSize * 30;

        gamePanel.enemy[12] = new EnemySword(gamePanel);
        gamePanel.enemy[12].worldX = gamePanel.TileSize * 7;
        gamePanel.enemy[12].worldY = gamePanel.TileSize * 23;

        gamePanel.enemy[13] = new EnemySword(gamePanel);
        gamePanel.enemy[13].worldX = gamePanel.TileSize * 12;
        gamePanel.enemy[13].worldY = gamePanel.TileSize * 23;

        gamePanel.enemy[14] = new EnemyArcher(gamePanel);
        gamePanel.enemy[14].worldX = gamePanel.TileSize * 45;
        gamePanel.enemy[14].worldY = gamePanel.TileSize * 9;

        gamePanel.enemy[15] = new EnemyArcher(gamePanel);
        gamePanel.enemy[15].worldX = gamePanel.TileSize * 32;
        gamePanel.enemy[15].worldY = gamePanel.TileSize * 23;

        gamePanel.enemy[16] = new EnemyArcher(gamePanel);
        gamePanel.enemy[16].worldX = gamePanel.TileSize * 32;
        gamePanel.enemy[16].worldY = gamePanel.TileSize * 22;

        gamePanel.enemy[17] = new EnemySword(gamePanel);
        gamePanel.enemy[17].worldX = gamePanel.TileSize * 33;
        gamePanel.enemy[17].worldY = gamePanel.TileSize * 22;

        gamePanel.enemy[18] = new EnemySword(gamePanel);
        gamePanel.enemy[18].worldX = gamePanel.TileSize * 26;
        gamePanel.enemy[18].worldY = gamePanel.TileSize * 27;

        gamePanel.enemy[19] = new EnemySword(gamePanel);
        gamePanel.enemy[19].worldX = gamePanel.TileSize * 34;
        gamePanel.enemy[19].worldY = gamePanel.TileSize * 44;

        gamePanel.enemy[20] = new EnemySword(gamePanel);
        gamePanel.enemy[20].worldX = gamePanel.TileSize * 34;
        gamePanel.enemy[20].worldY = gamePanel.TileSize * 46;

        for(int i = 21; i <= 26;i++) {
            gamePanel.enemy[i] = null;
        }
    }

    public void revive() {

        if(gamePanel.enemy[21] == null && gamePanel.enemy[22] == null && gamePanel.enemy[23] == null) {
            gamePanel.enemy[21] = new EnemySword(gamePanel);
            gamePanel.enemy[21].worldX = gamePanel.TileSize * 42; // enemy spawn
            gamePanel.enemy[21].worldY = gamePanel.TileSize * 39;

            gamePanel.enemy[22] = new EnemyArcher(gamePanel);
            gamePanel.enemy[22].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[22].worldY = gamePanel.TileSize * 41;

            gamePanel.enemy[23] = new EnemySword(gamePanel);
            gamePanel.enemy[23].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[23].worldY = gamePanel.TileSize * 43;
        }else {
            gamePanel.enemy[24] = new EnemyArcher(gamePanel);
            gamePanel.enemy[24].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[24].worldY = gamePanel.TileSize * 39;

            gamePanel.enemy[25] = new EnemyArcher(gamePanel);
            gamePanel.enemy[25].worldX = gamePanel.TileSize * 42 ; // enemy spawn
            gamePanel.enemy[25].worldY = gamePanel.TileSize * 41;

            gamePanel.enemy[26] = new EnemySword(gamePanel);
            gamePanel.enemy[26].worldX = gamePanel.TileSize * 42; // enemy spawn
            gamePanel.enemy[26].worldY = gamePanel.TileSize * 43;
        }
    }

}
