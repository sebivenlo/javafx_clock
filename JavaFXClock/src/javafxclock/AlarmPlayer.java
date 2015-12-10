package javafxclock;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 * This class plays the sound for alarm.
 *
 * @author Maximilian Walter <m.walter@student.fonty.nl>
 */
public class AlarmPlayer
{

    private AlarmPlayer()
    {
    }

    /**
     * Plays the alarm sound
     */
    public static void playAlarmSound()
    {
        try
        {
            //http://www.garrens.com/xanacreations/czero/sound/misc/alarm3.wav
            File yourFile = new File("alarm.wav");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class,
                                     format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Logger.getLogger(AlarmPlayer.class.getName()).log(Level.SEVERE,
                                                              null,
                                                              ex);
        }

    }
}
