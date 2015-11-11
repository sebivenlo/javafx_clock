package javafxclock.util;

import java.io.*;
import javax.sound.sampled.*;

/**
 *
 *
 */
public class AlarmPlayer {

    public static void playAlarmSound() {
        //TODO impl
        System.out.println("ALARM ALARM !!!!!!1!11");
        try {
            File yourFile = new File("");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            //whatevers
        }
    }
}
