
package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class first_frame_setting_musicdata {

    public static final String FOLDER = "data/music_settings";

    // 사용자별 파일 경로
    public static String getFilePath(String username) {
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(username.getBytes(StandardCharsets.UTF_8));
        return FOLDER + "/" + encoded + ".txt";
    }

    // 사용자 음악 설정 불러오기
    public static String[] load(String username) {
        String[] setting = {"1", "70", "true"};
        File file = new File(getFilePath(username));

        if (!file.exists()) {
            return setting;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("music=")) {
                    setting[0] = line.substring(6);
                } else if (line.startsWith("volume=")) {
                    setting[1] = line.substring(7);
                } else if (line.startsWith("enabled=")) {
                    setting[2] = line.substring(8);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return setting;
    }

    // 사용자 음악 설정 저장
    public static void save(String username, int music, int volume, boolean enabled) {
        try {
            File folder = new File(FOLDER);
            folder.mkdirs();

            PrintWriter pw = new PrintWriter(new FileWriter(getFilePath(username)));
            pw.println("music=" + music);
            pw.println("volume=" + volume);
            pw.println("enabled=" + enabled);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
