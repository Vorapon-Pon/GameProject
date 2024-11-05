package Main;

import Entity.Unit;
import Obj.Bow;
import Obj.PlayerHealth;
import Obj.Potion;
import Obj.SuperObj;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font Arial;
    BufferedImage BowImage;
    BufferedImage PlayerHealthEmpty;
    BufferedImage Potions;
    BufferedImage[] PlayerHealth = new BufferedImage[11];
    int command;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        Arial = new Font("Arial", Font.BOLD, 48);

        Bow bow = new Bow(gamePanel);
        BowImage = bow.image;

        PlayerHealth PHealth = new PlayerHealth(gamePanel);
        PlayerHealthEmpty = PHealth.image[0];
        for(int i = 1; i < 11; i++) {
            PlayerHealth[i] = PHealth.image[i];
        }

        Potion potion = new Potion(gamePanel);
        Potions = potion.image;

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(Arial);
        g2.setColor(Color.WHITE);

        switch (gamePanel.gameState) {
            case PLAYING:
                drawPlayerHealth();
                drawEnemyHealthBar();
                drawPlayScreen();
                break;
            case PAUSED:
                drawPlayerHealth();
                drawPausedScreen();
                break;
            case OPTION:
                drawOptionsScreen();
                break;
            case CONTROL:
                drawControl();
                break;
            case MENU:
                drawMenuScreen();
                break;
            case GAMEOVER:
                drawGameOverScreen();
                break;
            case VICTORY:
                drawVictoryScreen();
                break;
        }

    }

    public void drawPlayerHealth(){
        int x = gamePanel.player.worldX - gamePanel.getCameraX()- gamePanel.player.collideBox.width / 2;
        int y = gamePanel.player.worldY - gamePanel.getCameraY() - gamePanel.player.collideBox.height / 2;

        int i = 1;
        int currHealth = 0;
        while(currHealth < gamePanel.player.health && i <= 10) {
            if(currHealth >= 0) {
                g2.drawImage(PlayerHealth[i] , x, y + 90, gamePanel.TileSize, gamePanel.TileSize/4, null);
            }else {
                g2.drawImage(PlayerHealthEmpty, x ,y + 90, gamePanel.TileSize, gamePanel.TileSize/4, null);
            }
            i++;
            currHealth += 10;
        }

        if(gamePanel.player.health <= 0) {
            g2.drawImage(PlayerHealthEmpty, x ,y + 90, gamePanel.TileSize, gamePanel.TileSize/4, null);
        }
    }

    public void drawPlayScreen() {
        if(gamePanel.player.hasBow) {
            g2.setColor(Color.WHITE);
            g2.drawImage(BowImage, gamePanel.TileSize / 2, gamePanel.TileSize / 2, gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.arrow, 100, 100);
        }
        if(gamePanel.player.hasPotion) {
            g2.drawImage(Potions, gamePanel.TileSize * 2  , gamePanel.TileSize / 2, gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.potion, 220, 100);
        }
    }

    public void drawPausedScreen() {
        // Draw Tile and player with the current zoom level
        gamePanel.tileSet.draw(g2);
        gamePanel.player.draw(g2, gamePanel.getCameraX(), gamePanel.getCameraY());
        for(int i = 0; i < gamePanel.obj.length; i++) {
            if(gamePanel.obj[i] != null) {
                gamePanel.obj[i].draw(g2);
            }
        }
        for (int i = 0; i < gamePanel.enemy.length; i++) {
            if (gamePanel.enemy[i] != null) {
                gamePanel.enemy[i].draw(g2);
            }
        }
        if(gamePanel.player.hasBow) {
            g2.drawImage(BowImage, gamePanel.TileSize / 2, gamePanel.TileSize / 2,
                    gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.arrow, 100, 100);
        }
        if(gamePanel.player.hasPotion) {
            g2.drawImage(Potions, gamePanel.TileSize * 2  , gamePanel.TileSize / 2, gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.potion, 220, 100);
        }

        // Draw dimmed Rectangle
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, (int) (gamePanel.screenWidth / gamePanel.scaleFactor), (int) (gamePanel.screenHeight / gamePanel.scaleFactor));

        // Draw pause menu text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (48/gamePanel.scaleFactor)));
        String pauseText = "Game Paused";
        int textWidth = g2.getFontMetrics().stringWidth(pauseText);
        g2.drawString(pauseText, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2));

        //Resume
        String resume = "Resume";
        if(command == 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(resume);
            g2.drawString(resume, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) +(int)(80 / gamePanel.scaleFactor)) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(resume);
            g2.drawString(resume, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + (int)(80 / gamePanel.scaleFactor)) ;
        }
        //Option
        String option = "Option";
        if(command == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(option);
            g2.drawString(option, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + (int)(140 / gamePanel.scaleFactor)) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(option);
            g2.drawString(option, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + (int)(140 / gamePanel.scaleFactor)) ;
        }
        //Back to Menu
        String back = "Back to Menu";
        if(command == 2) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + (int)(200 / gamePanel.scaleFactor)) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + (int)(200 / gamePanel.scaleFactor)) ;
        }
    }

    public void drawMenuScreen() {
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/SwordMan/SwordManIdle_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Draw dimmed Rectangle
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Draw menu elements

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 72));
        String gameName = "Project:Stick";
        int textWidth2 = g2.getFontMetrics().stringWidth(gameName);
        g2.drawString(gameName, (int) ((gamePanel.screenWidth - textWidth2) / 1.4), (int) (gamePanel.screenHeight / 4));

        if(command == 0) {
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String startText = "Start";
            int textWidth1 = g2.getFontMetrics().stringWidth(startText);
            g2.drawString(startText, (int) ((gamePanel.screenWidth - textWidth1) / 1.5), (int) (gamePanel.screenHeight / 2.5));
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            String startText = "Start";
            int textWidth1 = g2.getFontMetrics().stringWidth(startText);
            g2.drawString(startText, (int) ((gamePanel.screenWidth - textWidth1) / 1.5), (int) (gamePanel.screenHeight / 2.5));
        }


        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(new Color(255, 255, 255, 100));
        String spaceText = "Press Space or Enter";
        int textWidth3 = g2.getFontMetrics().stringWidth(spaceText);
        g2.drawString(spaceText, (int) ((gamePanel.screenWidth - textWidth3) / 1.49), (int) (gamePanel.screenHeight / 2.4));
        g2.drawImage(image, (int) (gamePanel.screenWidth / 5) - 30, (int) (gamePanel.screenHeight / 3.4),350, 350, null);

        if(command == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String exit = "Exit game";
            int textWidth4 = g2.getFontMetrics().stringWidth(exit);
            g2.drawString(exit, (int) ((gamePanel.screenWidth - textWidth4) / 1.45), (int) (gamePanel.screenHeight / 1.8));
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            String exit = "Exit game";
            int textWidth4 = g2.getFontMetrics().stringWidth(exit);
            g2.drawString(exit, (int) ((gamePanel.screenWidth - textWidth4) / 1.48), (int) (gamePanel.screenHeight / 1.8));
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, (int) (gamePanel.screenWidth / gamePanel.scaleFactor), (int) (gamePanel.screenHeight / gamePanel.scaleFactor));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (72/gamePanel.scaleFactor)));
        String gameOver = "Game Over";
        int textWidth = g2.getFontMetrics().stringWidth(gameOver);
        g2.drawString(gameOver, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2));

        //Retry
        String retry = "Retry";
        if(command == 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(retry);
            g2.drawString(retry, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 80) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(retry);
            g2.drawString(retry, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 80) ;
        }
        //Back to Menu
        String back = "Back to Menu";
        if(command == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 140) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 140) ;
        }
    }

    public void drawOptionsScreen() {
        if(gamePanel.player.hasBow) {
            g2.drawImage(BowImage, gamePanel.TileSize / 2, gamePanel.TileSize / 2,
                    gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.arrow, 100, 100);
        }

        if(gamePanel.player.hasPotion) {
            g2.drawImage(Potions, gamePanel.TileSize * 2  , gamePanel.TileSize / 2, gamePanel.TileSize, gamePanel.TileSize, null);
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("x " + gamePanel.player.potion, 220, 100);
        }

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, (int) (gamePanel.screenWidth / gamePanel.scaleFactor), (int) (gamePanel.screenHeight / gamePanel.scaleFactor));

        g2.setColor((Color.WHITE));
        g2.setFont(new Font("Arial", Font.BOLD, (int) (72/gamePanel.scaleFactor)));

        double frameX = (gamePanel.TileSize * 4) / gamePanel.scaleFactor;
        double frameY = gamePanel.TileSize / gamePanel.scaleFactor;
        double frameWidth = (gamePanel.TileSize * 8) / gamePanel.scaleFactor;
        double frameHeight = (gamePanel.TileSize * 6) / gamePanel.scaleFactor;
        drawSubWindow (frameX, frameY, frameWidth, frameHeight);

        // Draw Options text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
        String pauseText = "Option";
        int textWidth = g2.getFontMetrics().stringWidth(pauseText);
        g2.drawString(pauseText, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 6));

        //Label Setting
        int textX = (int) ((frameX + gamePanel.TileSize / gamePanel.scaleFactor));
        int textY = (int) ((frameY + 30) /gamePanel.scaleFactor);
        // MUSIC
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        if(command == 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            g2.drawString("Music", textX, textY);
        }else {
            g2.setColor((Color.GRAY));
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("Music", textX, textY);
        }
        // SE
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        if(command == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            g2.drawString("Sound Effect", textX, textY);
        }else {
            g2.setColor((Color.GRAY));
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("Sound Effect", textX, textY);
        }
        // CONTROL
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        if(command == 2) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            g2.drawString("Control", textX, textY);
        }else {
            g2.setColor((Color.GRAY));
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("Control", textX, textY);
        }
        //BACK
        textY += (int) (2 * gamePanel.TileSize /gamePanel.scaleFactor);
        if(command == 3) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            g2.drawString("Back", textX, textY);
        }else {
            g2.setColor((Color.GRAY));
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
            g2.drawString("Back", textX, textY);
        }

        //Interact Setting
        //Music Bar
        g2.setColor(Color.WHITE);
        textX = (int) ((frameX + (5 * gamePanel.TileSize) / gamePanel.scaleFactor) );
        textY = (int) ((frameY + 80) /gamePanel.scaleFactor);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX,textY, (int) (200 / gamePanel.scaleFactor),30);
        int volumeWitdh = 40 * gamePanel.music.volumeScale;//200/5 = 40; divided scale volume into 5 scale
        g2.fillRect(textX,textY, (int) (volumeWitdh/gamePanel.scaleFactor),30);
        //Sound Effect Bar
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX,textY,(int) (200 / gamePanel.scaleFactor),30);
        volumeWitdh = 40 * gamePanel.soundsEff.volumeScale;//200/5 = 40; divided scale volume into 5 scale
        g2.fillRect(textX,textY, (int) (volumeWitdh /gamePanel.scaleFactor),30);
    }

    public void drawControl() {
        double x = (gamePanel.TileSize * 4) / gamePanel.scaleFactor;
        double y = gamePanel.TileSize / gamePanel.scaleFactor;
        double width = (gamePanel.TileSize * 8) / gamePanel.scaleFactor;
        double height = (gamePanel.TileSize * 6) / gamePanel.scaleFactor;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, (int) (gamePanel.screenWidth / gamePanel.scaleFactor), (int) (gamePanel.screenHeight / gamePanel.scaleFactor));

        g2.setColor(new Color(0,0,0, 150));
        g2.fillRoundRect((int) x, (int) y, (int) width, (int) height, 35, 35);

        g2.setColor(new Color(255,255,255));
        g2.setStroke (new BasicStroke(1));
        g2.drawRoundRect ((int) (x+5), (int)y+5, (int)width-10, (int)height-10, 25, 25);
        g2.setStroke (new BasicStroke(3));
        g2.drawRoundRect ((int) x, (int)y, (int)width, (int)height, 25, 25);

        // Draw Control text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
        String controls = "Controls";
        int textWidth = g2.getFontMetrics().stringWidth(controls);
        g2.drawString(controls, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 6));

        int textX = (int) ((x + gamePanel.TileSize / gamePanel.scaleFactor));
        int textY = (int) ((y + 30) /gamePanel.scaleFactor);

        //Label Controls
        // Movement Keys
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (24/gamePanel.scaleFactor)));
        g2.drawString("Movement Keys", textX, textY);
        // Switch Weapon
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("Switch Weapon", textX, textY);
        // Potion
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("Potion", textX, textY);
        // Attack
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("Attack", textX, textY);
        //BACK
        g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
        g2.drawString("Back", (int) ((x + gamePanel.TileSize / gamePanel.scaleFactor)), (int) (520 /gamePanel.scaleFactor));

        //Control
        //WASD
        g2.setColor(Color.WHITE);
        textX = (int) ((x + (5 * gamePanel.TileSize) / gamePanel.scaleFactor) );
        textY = (int) ((y + 110) /gamePanel.scaleFactor);
        g2.setFont(new Font("Arial", Font.PLAIN, (int) (24/gamePanel.scaleFactor)));
        g2.drawString("WASD", textX, textY);
        //1,2
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("1, 2", textX, textY);
        //Q
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("Q", textX, textY);
        //SPACE
        textY += (int) (gamePanel.TileSize /gamePanel.scaleFactor);
        g2.drawString("SPACE", textX, textY);
    }

    public void drawEnemyHealthBar() {
        for (int i=0; i < gamePanel.enemy.length; i++) {
            Unit enemy = gamePanel.enemy[i];
            if (enemy != null && enemy.inFrame(enemy.getScreenX(), enemy.getScreenY())) {
                if (enemy.healthBarOn && !enemy.statue) {
                    double oneScale = (double) (gamePanel.TileSize - 10) / enemy.maxHealth;
                    double hpBarValue = oneScale * enemy.health;
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(enemy.getScreenX() - 1, enemy.getScreenY() - 16, gamePanel.TileSize + 2, 12);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(enemy.getScreenX(), enemy.getScreenY() - 15, (int) hpBarValue, 10);
                    enemy.healthBarCounter++;
                    if (enemy.healthBarCounter > 600) {
                        enemy.healthBarCounter = 0;
                        enemy.healthBarOn = false;
                    }
                }else if(enemy.statue &&
                        enemy.getScreenX() + (gamePanel.TileSize)> 0 && enemy.getScreenX() < (gamePanel.screenWidth)/ gamePanel.scaleFactor &&
                        enemy.getScreenY() + (gamePanel.TileSize) > 0 && enemy.getScreenY() < (gamePanel.screenHeight) / gamePanel.scaleFactor) {
                    double oneScale = (double) (gamePanel.TileSize * 8) / enemy.maxHealth;
                    double hpBarValue = oneScale * enemy.health;

                    int x = gamePanel.screenWidth / 2 - gamePanel.TileSize * 4;
                    int y = gamePanel.TileSize ;

                    g2.setColor(new Color (35,35,35));
                    g2.fillRect(x-1, y-2, gamePanel.TileSize*8 + 2, 20);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(x+2, y+1, (int) hpBarValue - 2, 15);
                    g2.setFont(g2.getFont().deriveFont (Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString ("Weird", x + 4, y - 10);

                    if(enemy.health <= 0) {
                        gamePanel.gameState = GameState.VICTORY;
                        gamePanel.stopMusic();
                        gamePanel.playMusic(12);
                    }

                }
            }
        }
    }

    public void drawVictoryScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, (int) (gamePanel.screenWidth / gamePanel.scaleFactor), (int) (gamePanel.screenHeight / gamePanel.scaleFactor));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (72/gamePanel.scaleFactor)));
        String victory = "Victory";
        int textWidth = g2.getFontMetrics().stringWidth(victory);
        g2.drawString(victory, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2));

        //Retry
        String retry = "Retry";
        if(command == 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(retry);
            g2.drawString(retry, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 80) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth2 = g2.getFontMetrics().stringWidth(retry);
            g2.drawString(retry, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth2) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 80) ;
        }
        //Back to Menu
        String back = "Back to Menu";
        if(command == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (32/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 140) ;
        }else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, (int) (28/gamePanel.scaleFactor)));
            int textWidth3 = g2.getFontMetrics().stringWidth(back);
            g2.drawString(back, (int) (((gamePanel.screenWidth/ gamePanel.scaleFactor) - textWidth3) / 2), (int) ((gamePanel.screenHeight/ gamePanel.scaleFactor) / 2) + 140) ;
        }
    }

    public void drawSubWindow(double x, double y, double width, double height) {
        g2.setColor(new Color(0,0,0, 150));
        g2.fillRoundRect((int) x, (int) y, (int) width, (int) height, 35, 35);

        g2.setColor(new Color(255,255,255));
        g2.setStroke (new BasicStroke(1));
        g2.drawRoundRect ((int) (x+5), (int)y+5, (int)width-10, (int)height-10, 25, 25);
        g2.setStroke (new BasicStroke(3));
        g2.drawRoundRect ((int) x, (int)y, (int)width, (int)height, 25, 25);
    }
}