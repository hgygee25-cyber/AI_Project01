package login;
import java.io.File;
import java.util.ArrayList;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import javax.swing.*;

public class GUI_04 {
	static ArrayList<String> array = new ArrayList<>();
	public GUI_04(JTextField textfield0, JTextField textfield1, JTextField textfield2, JTextField textfield_email1, String email) {
        String text1 = textfield1.getText();
        String text3 = textfield_email1.getText();
        String filePath = "data/login.txt";
        String text0 = textfield0.getText();
        String text2 = textfield2.getText();
        String hashedPassword = "";
        
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(text2.toCharArray(), salt, 600000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            hashedPassword = Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
            spec.clearPassword();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        
        String newUser = text1 + "/" + hashedPassword + "/" + text3 + "@" + email + "/" + text0;
        array.add(newUser);

        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdirs();
        }
      
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filePath, true))) {
            writer.write(newUser);
            writer.newLine();
            System.out.println("회원가입 정보가 파일에 저장되었습니다.");
        } catch (java.io.IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
	}
}
