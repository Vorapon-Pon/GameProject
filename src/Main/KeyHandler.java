package Main;

import Entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
    public boolean usePotion;
    public boolean switchWeapon1, switchWeapon2;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {
        //not using
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Check which key is pressed and set corresponding direction
        if (keyCode == KeyEvent.VK_W) {
            if(gamePanel.gameState == GameState.GAMEOVER ||
               gamePanel.gameState == GameState.PAUSED ||
               gamePanel.gameState == GameState.OPTION ||
               gamePanel.gameState == GameState.MENU ) {

                if(gamePanel.ui.command > 0) {
                    gamePanel.ui.command--;
                }
                gamePanel.playSE(7);
            }
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            if(gamePanel.gameState == GameState.GAMEOVER || gamePanel.gameState == GameState.PAUSED) {
                if(gamePanel.ui.command < 2) {
                    gamePanel.ui.command++;
                }
                gamePanel.playSE(7);
            }else if(gamePanel.gameState == GameState.OPTION) {
                if(gamePanel.ui.command < 3) {
                    gamePanel.ui.command++;
                }
                gamePanel.playSE(7);
            }else if(gamePanel.gameState == GameState.MENU) {
                if(gamePanel.ui.command < 1) {
                    gamePanel.ui.command++;
                }
                gamePanel.playSE(7);
            }
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_A) {
            if(gamePanel.gameState == GameState.OPTION) {
                if(gamePanel.ui.command == 0 && gamePanel.music.volumeScale > 0) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playSE(7);
                }else if(gamePanel.ui.command == 1 && gamePanel.soundsEff.volumeScale > 0) {
                    gamePanel.soundsEff.volumeScale--;
                    gamePanel.soundsEff.checkVolume();
                    gamePanel.playSE(7);
                }
            }
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            if(gamePanel.gameState == GameState.OPTION) {
                if(gamePanel.ui.command == 0 && gamePanel.music.volumeScale < 5) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playSE(7);
                }else if(gamePanel.ui.command == 1 && gamePanel.soundsEff.volumeScale <  5) {
                    gamePanel.soundsEff.volumeScale++;
                    gamePanel.soundsEff.checkVolume();
                    gamePanel.playSE(7);
                }
            }

            rightPressed = true;
        }
        if(keyCode == KeyEvent.VK_Q) {
            usePotion = true;
            System.out.println("use potion!!");
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if(gamePanel.gameState != GameState.MENU) {
                gamePanel.togglePause();
                gamePanel.playSE(8);
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            if(gamePanel.gameState == GameState.MENU) {
                gamePanel.togglePause();
                gamePanel.playSE(7);
                gamePanel.playMusic(0);
            }else {
                attackPressed = true;
            }
        }
        if(keyCode == KeyEvent.VK_ENTER) {
            gamePanel.playSE(7);
            if(gamePanel.gameState == GameState.GAMEOVER) {
                if(gamePanel.ui.command == 0) {
                    gamePanel.gameState = GameState.PLAYING;
                    gamePanel.retry();
                }else if(gamePanel.ui.command == 1) {
                    gamePanel.gameState = GameState.MENU;
                    gamePanel.reset();
                }
            }else if (gamePanel.gameState == GameState.PAUSED) {
                if(gamePanel.ui.command == 0) {
                    gamePanel.togglePause();
                }else if(gamePanel.ui.command == 1) {
                    gamePanel.gameState = GameState.OPTION;
                    gamePanel.ui.command = 0;
                }else if(gamePanel.ui.command == 2) {
                    gamePanel.gameState = GameState.MENU;
                    gamePanel.ui.command = 0;
                    gamePanel.reset();
                }
            }else if(gamePanel.gameState == GameState.OPTION) {
                if(gamePanel.ui.command == 2) {
                    gamePanel.ui.command = 3;
                    gamePanel.gameState = GameState.CONTROL;
                }else if(gamePanel.ui.command == 3) {
                    gamePanel.gameState = GameState.PAUSED;
                    gamePanel.ui.command -= 2;
                }
            }else if (gamePanel.gameState == GameState.CONTROL) {
                if(gamePanel.ui.command == 3) {
                    gamePanel.gameState = GameState.OPTION;
                    gamePanel.ui.command = 2;
                }
            }else if(gamePanel.gameState == GameState.MENU) {
                if(gamePanel.ui.command == 0) {
                    gamePanel.togglePause();
                    gamePanel.playSE(7);
                    gamePanel.playMusic(0);
                }else if(gamePanel.ui.command == 1) {
                    System.exit(0);
                }
            }
        }
        if (keyCode == KeyEvent.VK_1) {
            switchWeapon1 = true;
            switchWeapon2 = false;
            gamePanel.playSE(9);
        }
        if (keyCode == KeyEvent.VK_2) {
            switchWeapon1 = false;
            switchWeapon2 = true;
            gamePanel.playSE(9);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Check which key is released and stop movement in that direction
        if (keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_Q) {
            usePotion = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            attackPressed = false;
        }
    }
}
