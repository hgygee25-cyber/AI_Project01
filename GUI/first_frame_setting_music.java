
package GUI;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class first_frame_setting_music {

    public static Clip clip;
    public static String currentMusic = "";
    public static int volume = 70;

    // 음악 재생
    public static void play(String path) {
        try {
            if (clip != null && currentMusic.equals(path) && clip.isRunning()) {
                setVolume(volume);
                return;
            }

            stop();

            File file = new File(path);

            if (!file.exists()) {
                System.out.println("음악 파일을 찾을 수 없습니다: " + path);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
            audio.close();

            currentMusic = path;
            setVolume(volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            System.out.println("음악 재생 오류");
            e.printStackTrace();
        }
    }

    // 음악 정지
    public static void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }

        currentMusic = "";
    }

    // 음량 조절
    public static void setVolume(int value) {
        volume = value;

        if (clip != null) {
            try {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float min = control.getMinimum();
                float max = control.getMaximum();
                float gain;

                if (value <= 0) {
                    gain = min;
                } else {
                    gain = (float) (20.0 * Math.log10(value / 100.0));

                    if (gain < min) {
                        gain = min;
                    }

                    if (gain > max) {
                        gain = max;
                    }
                }

                control.setValue(gain);

            } catch (Exception e) {
                System.out.println("음량 조절을 지원하지 않는 오디오 장치입니다.");
            }
        }
    }
}
