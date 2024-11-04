package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sounds {

    Clip clip;
    URL[] soundURL = new URL[20];
    FloatControl floatControl;
    int volumeScale = 3; // start volume at 3
    float volume;

    public Sounds() {
        soundURL[0] = getClass().getResource("/Sound/Ruins.wav");
        soundURL[1] = getClass().getResource("/Sound/Pickup.wav");
        soundURL[2] = getClass().getResource("/Sound/slashSword.wav");
        soundURL[3] = getClass().getResource("/Sound/TakeDamage.wav");
        soundURL[4] = getClass().getResource("/Sound/PlayerTakeDamage.wav");
        soundURL[5] = getClass().getResource("/Sound/bow-loading.wav");
        soundURL[6] = getClass().getResource("/Sound/bow-release.wav");
        soundURL[7] = getClass().getResource("/Sound/tink.wav");
        soundURL[8] = getClass().getResource("/Sound/esc.wav");
        soundURL[9] = getClass().getResource("/Sound/SwapWeapon.wav");
        soundURL[10] = getClass().getResource("/Sound/HealingSound.wav");
        soundURL[11] = getClass().getResource("/Sound/eerieHorn.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream AIS = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(AIS);
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0: volume = -80f;
                break;
            case 1: volume = -20f;
                break;
            case 2: volume = -12f;
                break;
            case 3: volume = -5f;
                break;
            case 4: volume = 1f;
                break;
            case 5: volume = 6f;
                break;
        }
        floatControl.setValue(volume);
    }
}
