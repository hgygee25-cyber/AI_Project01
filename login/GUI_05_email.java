package login;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GUI_05_email {
	int a;
	public GUI_05_email(String toEmail) {
		String host = "smtp.gmail.com";
        String username = "hgygee25@gmail.com";
        String password = "hxhc swzu avtw vjnv\r\n";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 사용
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // TLS 포트

        // 3. 세션 생성 및 인증
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 4. 메시지 객체 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("메일 인증번호 발송");
            Random rand = new Random();
            
            this.a = rand.nextInt(10000);  // 0~9999
            message.setText(String.format("%04d", this.a));
            System.out.println("인증번호: "+String.format("%04d", this.a));
            
            // 5. 이메일 전송
            Transport.send(message);
            System.out.println("이메일이 성공적으로 전송되었습니다.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일 전송 실패: " + e.getMessage());
        }
	}
	public int getA() {
        return this.a;
    }
}
