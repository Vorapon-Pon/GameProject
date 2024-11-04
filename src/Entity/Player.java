package Entity;

import Main.*;
import Obj.Arrow;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Unit {
    private Direction facingDirection;
    private boolean isAttacking = false;
    public boolean hasBow = false;
    private Equip equipWeapon = Equip.SWORD;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;
        this.facingDirection = Direction.RIGHT;
        this.worldX = gamePanel.TileSize * 9;
        this.worldY = gamePanel.TileSize * 22;
        solidAreaDefaultX = collideBox.x;
        solidAreaDefaultY = collideBox.y;
        collideBox = new Rectangle(20,20,40,40);
        AttackhitsBox = new Rectangle(20,20, 40,60);
        damage = 30;
        maxHealth = 100;
        health = maxHealth;
        maxArrow = 32;
        arrow = 3;
        potion = 0;
        this.defaultSpeed = 10;
        this.speed = defaultSpeed;
        projectile = new Arrow(gamePanel);

        loadSprites("/SwordMan");
    }

    public void setDefault() {
        this.worldX = gamePanel.TileSize * 9;
        this.worldY = gamePanel.TileSize * 22;
        isInvincible = false;
        maxHealth = 100;
        health = maxHealth;
        hasBow = false;
        arrow = 3;
        hasPotion = false;
        potion = 0;

    }

    @Override
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

            // Check collision with enemy units
            int unitIndex = gamePanel.collisionCheck.checkUnit(this, gamePanel.enemy);
            damageUnit(unitIndex, damage);

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

    @Override
    public void loadSprites(String folderPath) {
        idleRight = new BufferedImage[6];
        idleLeft = new BufferedImage[6];
        idleBowRight = new BufferedImage[6];
        idleBowLeft = new BufferedImage[6];
        walkLeftSprites = new BufferedImage[10];
        walkRightSprites = new BufferedImage[10];
        walkBowLeftSprites = new BufferedImage[10];
        walkBowRightSprites = new BufferedImage[10];
        AttackR = new BufferedImage[6];
        AttackL = new BufferedImage[6];
        AttackBowR = new BufferedImage[6];
        AttackBowL = new BufferedImage[6];

        // Load each frame
        for (int i = 0; i < 10; i++) {
            walkLeftSprites[i] = setUp(folderPath + "/SwordManRunLeft" + (i+1));
            walkRightSprites[i] = setUp(folderPath + "/SwordManRunRight_" + (i+1));
            walkBowLeftSprites[i] = setUp(folderPath + "/SwordManBowRunLeft" + (i+1));
            walkBowRightSprites[i] = setUp(folderPath + "/SwordManBowRunRight" + (i+1));
        }

        for(int i = 0; i < 6; i++) {
            AttackR[i] = setUp(folderPath + "/SwordManAttackRight" + (i+1));
            AttackL[i] = setUp(folderPath + "/SwordManAttackLeft" + (i+1));
            AttackBowR[i] = setUp(folderPath + "/SwordManAttackBowRight" + (i+1));
            AttackBowL[i] = setUp(folderPath + "/SwordManAttackBowLeft" + (i+1));
            idleRight[i] = setUp(folderPath + "/SwordManIdle_" + (i+1));
            idleLeft[i] = setUp(folderPath + "/SwordManIdleIdleLeft" + (i+1));
            idleBowRight[i] = setUp(folderPath + "/SwordManIdleBowR" + (i+1));
            idleBowLeft[i] = setUp(folderPath + "/SwordManIdleBowL" + (i+1));
        }
    }

    @Override
    public void update() {

        if (shotCooldown > 0) {
            shotCooldown--;
        }

        //attack
        if (keyHandler.attackPressed && !isAttacking) {
            attack();
            spriteIndex = 0; // Reset the animation index to the beginning
            isAttacking = true;  // Set the attacking flag to true
        }

        //healing
        if(keyHandler.usePotion) {
            if(health != maxHealth) {
                useHealing();
            }
        }

        //swap weapon
        if(keyHandler.switchWeapon1 || keyHandler.switchWeapon2) {
            switchWeapon();
        }

        if (isAttacking) {
            spriteCounter++;
            if (spriteCounter > 6) {
                spriteIndex++;
                spriteCounter = 0;
            }

            if (spriteIndex >= 6) {
                isAttacking = false;
                spriteIndex = 0;
                behavior = "idle";
            }

            if (shotDelay > 0) {
                shotDelay--;

                // Create the arrow when the delay is over
                if (shotDelay == 0 && !projectile.isAlive && projectile.haveArrow(this)) {
                    projectile.set(worldX, worldY, String.valueOf(facingDirection), true, this);
                    gamePanel.projectileList.add(projectile);
                    projectile.spendArrow(this);
                    shotCooldown = 30;
                }
            }

            //movement
        } else {

            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

                if (keyHandler.leftPressed) {
                    behavior = "left";
                    facingDirection = Direction.LEFT;
                } else if (keyHandler.rightPressed) {
                    behavior = "right";
                    facingDirection = Direction.RIGHT;
                }else if (keyHandler.upPressed) {
                    behavior = "up";
                }else if (keyHandler.downPressed) {
                    behavior = "down";
                }

                collisionOn = false;
                gamePanel.collisionCheck.checkTile(this); // check Tiel collision
                int objIndex = gamePanel.collisionCheck.checkObj(this,true);
                pickUpObj(objIndex);
                int unitIndex = gamePanel.collisionCheck.checkUnit(this, gamePanel.enemy);
                collideUnit(unitIndex);

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
                }
            }else {
                behavior = "idle";
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
                if (invincibleCounter >= 60) {
                    isInvincible = false;
                    invincibleCounter = 0;
                }
            }

            if(health <= 0) {
                gamePanel.gameState = GameState.GAMEOVER;
                gamePanel.stopMusic();
            }

            updateScreenPosition();
        }
    }

    public void pickUpObj(int index) {
        if(index != 999) {
            String objName = gamePanel.obj[index].name;
            switch (objName) {
                case "Bow" :
                    gamePanel.playSE(1);
                    hasBow = true;
                    gamePanel.obj[index] = null;
                    break;
                case "ItemArrow1" :
                    gamePanel.playSE(1);
                    arrow += 1;
                    gamePanel.obj[index] = null;
                    break;
                case "ItemArrow3" :
                    gamePanel.playSE(1);
                    arrow += 3;
                    gamePanel.obj[index] = null;
                    break;
                case "potion" :
                    gamePanel.playSE(1);
                    potion += 1;
                    hasPotion = true;
                    gamePanel.obj[index] = null;
                    break;
            }
        }
    }

    public void collideUnit(int index) {
        if(index != 999) {
            if(!isInvincible) {
                health -= 10;
                gamePanel.enemy[index].spriteIndex = 0;
                gamePanel.enemy[index].isAttacking = true;
                isInvincible = true;
                gamePanel.playSE(4);
            }
        }
    }

    public void knockback(Unit ent) {
        ent.speed = 0;
        ent.knockback = true;
    }

    public void takeDamage(int damage) {
        health -= damage; // Reduce player’s health
        if (health <= 0) {
            health = 0;

        }
    }

    public void damageUnit(int index, int dmg) {
        if(index != 999) {
            if(!gamePanel.enemy[index].isInvincible)  {
                gamePanel.playSE(3);
                gamePanel.enemy[index].health -= dmg;
                gamePanel.enemy[index].isInvincible = true;
                gamePanel.enemy[index].damageReaction();
                knockback(gamePanel.enemy[index]);

                if(gamePanel.enemy[index].health <= 0) {
                    gamePanel.enemy[index].dying = true;
                }
            }
            gamePanel.enemy[index].onPath = true;
        }else {
            System.out.println("miss");
        }
    }

    public void switchWeapon() {
        if(keyHandler.switchWeapon1) {
            equipWeapon = Equip.SWORD;
        }
        if (keyHandler.switchWeapon2 && hasBow) {
            equipWeapon = Equip.BOW;
        }
    }

    public void useHealing() {
        if (potion >= 1) {
            gamePanel.playSE(10);
            health += 20;
            if (health > maxHealth) {
                health = maxHealth;  // Prevent overhealing
            }
            potion--;
        }
    }

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        // Update screen position based on camera
        int screenX = worldX - cameraX - collideBox.width / 2;
        int screenY = worldY - cameraY - collideBox.height / 2;

        BufferedImage image = null;
        if(equipWeapon == Equip.SWORD) {
            switch (behavior) {
                case "up", "down":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex] : AttackL[spriteIndex];
                    }else{
                        image = facingDirection == Direction.RIGHT ? walkRightSprites[spriteIndex] : walkLeftSprites[spriteIndex];
                    }
                    break;
                case "left":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex] : AttackL[spriteIndex];
                    }else{
                        image = walkLeftSprites[spriteIndex];
                    }
                    break;
                case "right":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex] : AttackL[spriteIndex];
                    }else{
                        image = walkRightSprites[spriteIndex];
                    }
                    break;
                case "idle":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackR[spriteIndex] : AttackL[spriteIndex];
                    }else{
                        image = facingDirection == Direction.RIGHT ? idleRight[spriteIndex % 6] : idleLeft[spriteIndex % 6];
                    }
                    break;
            }
        }else if(equipWeapon == Equip.BOW) {
            switch (behavior) {
                case "up", "down":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex] : AttackBowL[spriteIndex];
                    }else {
                        image = facingDirection == Direction.RIGHT ? walkBowRightSprites[spriteIndex] : walkBowLeftSprites[spriteIndex];
                    }
                    break;
                case "left":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex] : AttackBowL[spriteIndex];
                    }else{
                        image = walkBowLeftSprites[spriteIndex];
                    }
                    break;
                case "right":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex] : AttackL[spriteIndex];
                    }else{
                        image = walkBowRightSprites[spriteIndex];
                    }
                    break;
                case "idle":
                    if(isAttacking) {
                        image = facingDirection == Direction.RIGHT ? AttackBowR[spriteIndex] : AttackBowL[spriteIndex];
                    }else{
                        image = facingDirection == Direction.RIGHT ? idleBowRight[spriteIndex % 6] : idleBowLeft[spriteIndex % 6];
                    }
                    break;
            }
        }

        if(isInvincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        if (isAttacking) {
            g2.setColor(Color.YELLOW);
            g2.drawRect(screenX + collideBox.width , screenY + collideBox.y, AttackhitsBox.width, AttackhitsBox.height);
        }

        g2.drawImage(image, screenX, screenY, null);
        g2.drawRect(screenX  , screenY , collideBox.width, collideBox.height);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void updateScreenPosition() {
        // Calculate the screenX and screenY based on worldX, worldY, and the camera position
        int cameraX = gamePanel.getCameraX();
        int cameraY = gamePanel.getCameraY();

        screenX = worldX - cameraX;
        screenY = worldY - cameraY;
    }
}
