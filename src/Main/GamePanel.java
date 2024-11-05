package Main;

import AI.PathFinder;
import Entity.Player;
import Entity.Unit;
import Tile.TileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable {
    final int SpriteSize = 16;
    int scale = 5;

    public final int TileSize = SpriteSize * scale;// calculate each tile in map (80)
    public double scaleFactor = 1.0;

    public int MaxRow = 9;
    public int MaxCol = 16;
    public int screenWidth = TileSize * MaxCol;  // 1280 Pixel
    public int screenHeight = TileSize * MaxRow; // 720 Pixel

    public int maxWorldRow = 50;
    public int maxWorldCol = 50;
    public final int maxWorldWidth = TileSize * maxWorldCol;
    public final int maxWorldHeight = TileSize * maxWorldRow;

    Thread gameThread; // automatic call run method
    KeyHandler keyHandler = new KeyHandler(this);
    public TileSet tileSet =  new TileSet(this);
    public CollisionChecker collisionCheck = new CollisionChecker(this, tileSet);
    public GameState gameState = GameState.MENU;
    public UI ui = new UI(this);
    Sounds music = new Sounds();
    Sounds soundsEff = new Sounds();
    public Player player = new Player(this,keyHandler);
    public Asset asset = new Asset(this);
    public PathFinder pathFinder = new PathFinder(this);
    public Unit[] obj = new Unit[20];
    public Unit[] enemy = new Unit[30];
    public ArrayList<Unit> RandomEnemy = new ArrayList<>();
    public ArrayList<Unit> projectileList = new ArrayList<Unit>();
    ArrayList<Unit> renderList = new ArrayList<Unit>();

    int FPS = 60;
    private int cameraX = 0;
    private int cameraY = 0;

    JButton spawnButton;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // GamePanel "focused" to receive key input
        this.addMouseWheelListener(this::zoom); // ScrollMouse zoom in N out
    }

    public void setUpGame() {
        asset.setObj();
        asset.setEnemy();
    }

    public void retry() {
        player.setDefault();
        asset.setObj();
        asset.setEnemy();
        playMusic(0);
    }

    public void reset() {
        player.setDefault();
        asset.setObj();
        asset.setEnemy();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // draw the screen every (1 / 60) 0.0166667sec of 60 time per sec
        double delta = 0;
        long lastTime = System.nanoTime();
        long currTime;
        // System.nanoTime() return current value  of JVM's high-res time source in Nanosecond;
        // 1,000,000,000 nanosec = 1 sec
        while(gameThread != null) {
            /*
            run the game by updating data in-game
            updating such as position of the character

             <-  <-  <-  <-  <-  <-
            |                      |
            -> update -> repaint ->
            */
            currTime = System.nanoTime();
            delta +=  (currTime - lastTime) / drawInterval;
            lastTime = currTime;

            if(delta >= 1) { // delta method
                if (gameState == GameState.PLAYING) {
                    update();
                }
                repaint();
                delta--;
            }

        }
    }

    public void update() {
        if (gameState == GameState.PLAYING) {
            player.update();
            for (int i = 0; i < enemy.length; i++) {
                if(enemy[i] != null ) {
                    if (enemy[i].isAlive && !enemy[i].dying) {
                        enemy[i].update();
                    }
                    if(!enemy[i].isAlive && i != 3) {
                        enemy[i] = null;
                    }
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null ) {
                    obj[i].update();
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if (projectileList.get(i).isAlive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).isAlive ) {
                        projectileList.remove(i);
                    }
                }
            }
        }
    }

    public void togglePause() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
            stopMusic();
        } else if(gameState == GameState.MENU){
            gameState = GameState.PLAYING;
        }else if(gameState == GameState.PAUSED){
            gameState = GameState.PLAYING;
            music.play();
        }else if(gameState == GameState.OPTION) {
            gameState = GameState.PAUSED;
        }else if(gameState == GameState.CONTROL) {
            gameState = GameState.OPTION;
        }
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraPosition(int x, int y) {
        cameraX = x;
        cameraY = y;
    }

    public void zoom(MouseWheelEvent e) {
        if(gameState == GameState.PLAYING) {
            int notches = e.getWheelRotation(); // wheel down 1, wheel up -1

            // Adjust scaleFactor based on mouse wheel movement
            scaleFactor -= notches * 0.1; // Adjust the 0.1 to change zoom speed

            // Clamp scaleFactor to prevent excessive zooming
            if (scaleFactor < 1) scaleFactor = 1; // Minimum zoom (100%)
            if (scaleFactor > 1.5) scaleFactor = 1.5; // Maximum zoom (150%)

            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(gameState == GameState.MENU) {
            ui.draw(g2);
        }else {
            g2.scale(scaleFactor, scaleFactor);

            renderList.add(player);
            for (int i = 0; i < enemy.length; i++){
                if(enemy[i] != null && i != 4) {
                    renderList.add(enemy[i]);
                }
            }

            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    renderList.add(obj[i]);
                }
            }

            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    renderList.add(projectileList.get(i));
                }
            }

            Collections.sort(renderList, new Comparator<Unit>(){
                @Override
                public int compare(Unit u1, Unit u2) {
                    int result = Integer.compare((int) u1.worldY, (int) u2.worldY);
                    return  result;
                }
            });

            int playerCenterX = player.worldX - (int)(screenWidth / (2 * scaleFactor));
            int playerCenterY = player.worldY - (int)(screenHeight / (2 * scaleFactor));

            // Clamp camera position to keep player within the world bounds
            int cameraX = Math.max(0, Math.min(playerCenterX, maxWorldWidth - (int)(screenWidth / scaleFactor)));
            int cameraY = Math.max(0, Math.min(playerCenterY, maxWorldHeight - (int)(screenHeight / scaleFactor)));
            setCameraPosition(cameraX,cameraY);
            // Draw game elements (tiles, player, enemies) with camera adjustments
            tileSet.draw(g2);
            for(int i = 0; i < renderList.size(); i++) {
                if(renderList.get(i) == player) {
                    player.draw(g2, getCameraX(), getCameraY());
                }else {
                    renderList.get(i).draw(g2);
                }
            }

            enemy[4].draw(g2);

            renderList.clear();

            // Delegate the drawing of UI elements to the UI class
            ui.draw(g2);
            long drawStart = 0;
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
            g2.drawString("Worldy" + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col" + (player.worldX + player.collideBox.x)/TileSize, x, y); y += lineHeight;
            g2.drawString("Row" + (player.worldY + player.collideBox.y)/TileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time:" + passed, x, y);
            
        }


        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        soundsEff.setFile(i);
        soundsEff.play();
    }
}
