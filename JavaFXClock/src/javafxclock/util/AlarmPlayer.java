package javafxclock.util;

import java.io.*;
import javax.sound.sampled.*;

/**
 *
 *
 */
public class AlarmPlayer {

    public static void playAlarmSound() {
        System.out.println("ALARM ALARM !!!!!!1!11");
        try {
            //http://www.garrens.com/xanacreations/czero/sound/misc/alarm3.wav
            File yourFile = new File("alarm.wav");
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

            System.out.println(e);

        }

    }
}
