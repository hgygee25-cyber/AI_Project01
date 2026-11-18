package login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextField;

public class GUI_08 {
    static int aa = 0;
    static String al = null;

    public GUI_08(JTextField textfield1, String email) {
        aa = 0;
        al = null;
        String emailCor = textfield1.getText() + "@" + email;
        String requestedId = GUI_03_2.textfield_id.getText();

        // 최신 파일 내용을 기준으로 검색
        GUI_01.readtxt();
        try (BufferedReader br = new BufferedReader(new FileReader("data/login.txt"))) {
            for (String line; (line = br.readLine()) != null;) {
                GUI_10 account = GUI_10.parse(line);
                if (account == null) continue;
                if (account.id.equals(requestedId)) {
                    if (account.email.equals(emailCor)) {
                        aa = 1;
                        al = line;
                        break;
                    }
                    aa = -1;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            aa = 0;
        }
    }
}
