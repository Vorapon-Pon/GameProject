package Entity;

import Main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public abstract class Unit  {
    //position
    protected KeyHandler keyHandler;
    public int worldX, worldY;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int speed;
    public int defaultSpeed;
    protected GamePanel gamePanel;
    public int screenX, screenY;

    //unit
    protected Equip equipWeapon;
    public int maxHealth;
    public int health;
    public int arrow;
    public int maxArrow;
    public Boolean hasPotion = false;
    public int potion;
    protected int damage;
    public boolean isAlive = true;
    public boolean dying = false;
    protected boolean isAttacking;
    public int useArrow;
    public boolean isInvincible = false;
    public int type; // check unit type player enemy etc
    protected boolean healthBarOn = false;
    protected int healthBarCounter = 0;
    public Projectile projectile;
    public boolean onPath = false;
    public boolean knockback = false;


    //collision
    public Rectangle collideBox;
    public boolean collisionOn = false;
    public Rectangle AttackhitsBox;
    public int actionCounter = 0;

    //sprites
    protected Direction facingDirection = Direction.LEFT;
    protected BufferedImage[] idleRight;
    protected BufferedImage[] idleLeft;
    protected BufferedImage[] idleBowRight;
    protected BufferedImage[] idleBowLeft;
    protected BufferedImage[] walkBowLeftSprites;
    protected BufferedImage[] walkBowRightSprites;
    protected BufferedImage[] walkLeftSprites;
    protected BufferedImage[] walkRightSprites;
    protected BufferedImage[] AttackR;
    protected BufferedImage[] AttackL;
    protected BufferedImage[] AttackBowR;
    protected BufferedImage[] AttackBowL;

    //obj
    public BufferedImage image;
    public String name;
    public boolean collision = false;

    //animation
    public String behavior = "idle";
    public int spriteCounter = 0;
    public int spriteIndex = 0; // Current frame in animation
    public int animationSpeed = 10; // Controls how fast the animation cycles
    public int invincibleCounter = 0;
    public int dyingCoutner = 0;
    public int shotCooldown = 0;
    public int shotDelay = 0;
    public int knockbackCount = 0;

    public Unit(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.isAlive = true;
        collideBox = new Rectangle(-20,-20,40,40);

        this.screenX = gamePanel.screenWidth / 2 - (gamePanel.TileSize / 2);
        this.screenY = gamePanel.screenHeight / 2 - (gamePanel.TileSize / 2);
    }

    public void attack() {
        if(equipWeapon == Equip.SWORD) {
            gamePanel.playSE(2);
            // Store current position and box sizes
            int currWorldX = worldX;
            int currWorldY = worldY;
            int CollideBoxWidth = collideBox.width;
            int CollideBoxHeight = collideBox.height;

            // Adjust hitbox position based on facing direction
            switch (behavior) {
                case "left":
                    worldX -= AttackhitsBox.width;
                    break;
                case "right":
                    worldX += AttackhitsBox.width;
                    break;
                case "up","down", "idle":
                    worldX = facingDirection == Direction.RIGHT ? worldX + AttackhitsBox.width : worldX - AttackhitsBox.width;
                    break;
            }
            // Update collideBox to simulate attack range
            collideBox.width = AttackhitsBox.width;
            collideBox.height = AttackhitsBox.height;

            if(type == 2) {
                if(gamePanel.collisionCheck.checkPlayer(this)) {
                    damagePlayer(damage);
                }
            }else {
                // Check collision with enemy units
                int unitIndex = gamePanel.collisionCheck.checkUnit(this, gamePanel.enemy);
                gamePanel.player.damageUnit(unitIndex, damage);
            }

            // Revert position and box size after attack
            worldX = currWorldX;
            worldY = currWorldY;
            collideBox.width = CollideBoxWidth;
            collideBox.height = CollideBoxHeight;

        }else if(equipWeapon == Equip.BOW && shotCooldown <= 0) {
            gamePanel.playSE(5);
            gamePanel.playSE(2);
            shotDelay = 24;
        }

    }

    public void knockback(Unit ent) {
        ent.speed = 0;
        ent.knockback = true;
    }


    public void damageReaction() {

    }

    protected void onDeath() {

    }

    public void loadSprites(String folderPath){}

    public BufferedImage setUp(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            InputStream input = getClass().getResourceAsStream(imagePath + ".png");
            if (input == null) {
                System.out.println("Failed to load image from path: " + imagePath + ".png");
                throw new IllegalArgumentException("Resource not found: " + imagePath + ".png");
            }
            scaledImage = ImageIO.read(input);
            scaledImage = uTool.scaleImage(scaledImage, gamePanel.TileSize, gamePanel.TileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loading image from path: " + imagePath + ".png");
        return scaledImage;
    }

    public void setAction() {

    }

    public void checkCollision() {
        collisionOn = false;
        gamePanel.collisionCheck.checkTile(this);
        gamePanel.collisionCheck.checkObj(this,false);
        boolean contactPlayer = gamePanel.collisionCheck.checkPlayer(this);
        if(this.type == 2 && contactPlayer) {
            damagePlayer(10);
        }
    }

    public void update() {

        if(knockback) {
            checkCollision();
            if(collisionOn) {
                knockbackCount = 0;
                knockback = false;
                speed = defaultSpeed;
            }else if(!collisionOn) {
                switch (gamePanel.player.behavior) {
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "up" :
                        worldY -= speed;
                        break;
                    case "down" :
                        worldY += speed;
                        break;
                }
            }

            knockbackCount++;
            if(knockbackCount == 2) {
                knockbackCount= 0;
                knockback = false;
                speed = defaultSpeed;
            }
        }else {
            setAction();
            checkCollision();

            if(!collisionOn) {
                switch (behavior) {
                    case "left":
                        facingDirection = Direction.LEFT;
                        worldX -= speed;
                        break;
                    case "right":
                        facingDirection = Direction.RIGHT;
                        worldX += speed;
                        break;
                    case "up" :
                        worldY -= speed;
                        break;
                    case "down" :
                        worldY += speed;
                        break;
                }
            }else {
                behavior = "idle";
            }
        }

        if (shotCooldown > 0) {
            shotCooldown--;
        }

        if (isAttacking) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteIndex++;
                spriteCounter = 0;
            }

            if (spriteIndex >= 6) {
                isAttacking = false;
                spriteIndex = 0;
                behavior = "idle";
            }
        }

        spriteCounter++;
        if (spriteCounter > 6) {
            spriteIndex++;
            if (spriteIndex >= animationSpeed) { // Loop through the frames
                spriteIndex = 0;
            }
            spriteCounter = 0;
        }

        if (isInvincible) {
            invincibleCounter++;
            if (invincibleCounter >= 40) {
                isInvincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void damagePlayer(int damage) {
        if(!gamePanel.player.isInvincible){
            gamePanel.player.health -= damage;
            gamePanel.player.isInvincible = true;
            gamePanel.playSE(4);
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Only draw tiles that are within the screen bounds
        BufferedImage image = null;
        if(equipWeapon == Equip.SWORD) {
            switch (behavior) {
                case "up", "down":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex % 6] : AttackL[spriteIndex % 6];
                    }else{
                        image = facingDirection == Direction.RIGHT ? walkRightSprites[spriteIndex] : walkLeftSprites[spriteIndex];
                    }
                    break;
                case "left":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex % 6] : AttackL[spriteIndex % 6];
                    }else{
                        image = walkLeftSprites[spriteIndex];
                    }
                    break;
                case "right":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex % 6] : AttackL[spriteIndex % 6];
                    }else{
                        image = walkRightSprites[spriteIndex];
                    }
                    break;
                case "idle":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex % 6] : AttackL[spriteIndex % 6];
                    }else{
                         image = facingDirection == Direction.RIGHT ? idleRight[spriteIndex % 6] : idleLeft[spriteIndex % 6];
                    }
                    break;
            }
        }else if(equipWeapon == Equip.BOW) {
            switch (behavior) {
                case "up", "down":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex % 6] : AttackBowL[spriteIndex % 6];
                    }else {
                        image = facingDirection == Direction.RIGHT ? walkBowRightSprites[spriteIndex] : walkBowLeftSprites[spriteIndex];
                    }
                    break;
                case "left":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex % 6] : AttackBowL[spriteIndex % 6];
                    }else{
                        image = walkBowLeftSprites[spriteIndex];
                    }
                    break;
                case "right":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex % 6] : AttackBowL[spriteIndex % 6];
                    }else{
                        image = walkBowRightSprites[spriteIndex];
                    }
                    break;
                case "idle":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex % 6] : AttackBowL[spriteIndex % 6];
                    }else{
                        image = facingDirection == Direction.RIGHT ? idleBowRight[spriteIndex % 6] : idleBowLeft[spriteIndex % 6];
                    }
                    break;
            }
        }

        if(type == 2 && healthBarOn) {
            double oneScale = (double)gamePanel.TileSize/maxHealth;
            double hpBarValue = oneScale*health;

            g2.setColor(new Color(50,50,50));
            g2.fillRect(screenX-2,screenY - 22, gamePanel.TileSize / 2 + 5,9);

            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX,screenY - 20, (int) hpBarValue / 2,5);

            healthBarCounter++;
            if(healthBarCounter > 600) {
                healthBarOn = false;
                healthBarCounter = 0;
            }
        }

        if(isInvincible) {
            healthBarOn = true;
            healthBarCounter = 0;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        if(dying) {
            dyingAnimation(g2);
        }

        if (screenX + gamePanel.TileSize > 0 && screenX < gamePanel.screenWidth / gamePanel.scaleFactor &&
                screenY + gamePanel.TileSize > 0 && screenY < gamePanel.screenHeight / gamePanel.scaleFactor) {
            g2.drawImage(image, screenX, screenY, null);
        }

        g2.drawRect(screenX , screenY , collideBox.width, collideBox.height);// draw collideBOx

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCoutner++;
        if(dyingCoutner > 40) {
            dying = false;
            isAlive = false;
        }else {
            switch(dyingCoutner % 10) {
                case 0,1,2,3,4 :
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
                    break;
                case  5,6,7,8,9 :
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    break;
            }
        }

    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + collideBox.x)/gamePanel.TileSize;
        int startRow = (worldY + collideBox.y)/gamePanel.TileSize;

        gamePanel.pathFinder.setNode(startCol, startRow, goalCol, goalRow);

        if(gamePanel.pathFinder.search()){
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.TileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.TileSize;

            // Entity's solidArea position
            int enLeftX = worldX + collideBox.x;
            int enRightX = worldX + collideBox.x + collideBox.width;
            int enTopY =  worldY + collideBox.y;
            int enBottomY = worldY + collideBox.y + collideBox.height;
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.TileSize) {
                behavior = "up";

            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.TileSize) {
                behavior = "down";

            }else if (enTopY >= nextY && enBottomY < nextY + gamePanel.TileSize) {
                // left or right
                if (enLeftX > nextX) {
                    behavior = "left";
                }
                if(enLeftX < nextX) {
                    behavior = "right";
                }
            }else if(enTopY > nextY && enLeftX > nextX) {
                // up or left
                behavior = "up";
                checkCollision();
                if(collision) {
                    behavior = "left";
                }
            }else if(enTopY > nextY && enLeftX < nextX) {
                // up or left
                behavior = "up";
                checkCollision();
                if(collision) {
                    behavior = "right";
                }
            }else if(enTopY < nextY && enLeftX > nextX) {
                // up or left
                behavior = "down";
                checkCollision();
                if(collision) {
                    behavior = "left";
                }
            }else if(enTopY < nextY && enLeftX < nextX) {
                // up or left
                behavior = "down";
                checkCollision();
                if(collision) {
                    behavior = "right";
                }
            }
        }
    }

    public int getXdistance(Unit ent) {
        return Math.abs(worldX - ent.worldX);
    }

    public int getYdistance(Unit ent) {
        return Math.abs(worldY - ent.worldY);
    }

    public int getDistance(Unit ent) {// tile distance
        return (getXdistance(ent) + getYdistance(ent)) / gamePanel.TileSize;
    }
}
