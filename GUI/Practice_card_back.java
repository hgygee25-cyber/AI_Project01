package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_card_back {
    public static void flipToBack(JLabel cardLabel, String value1, String value2) {
        // 카드의 원래 위치와 크기 저장
        final int origX = cardLabel.getX();
        final int origY = cardLabel.getY();
        final int origW = cardLabel.getWidth();
        final int origH = cardLabel.getHeight();

        // 뒷면 이미지를 카드 크기에 맞게 미리 로드
        String value = value1+"."+value2;
        String backImagePath = "image_card/"+value+".png";
        ImageIcon rawIcon = new ImageIcon(backImagePath);
        Image scaledImg = rawIcon.getImage().getScaledInstance(origW, origH, Image.SCALE_SMOOTH);
        final ImageIcon backIcon = new ImageIcon(scaledImg);

        // 15ms마다 실행되는 애니메이션 타이머
        Timer timer = new Timer(15, new ActionListener() {
            int currentW = origW;
            boolean shrinking = true; // true: 줄어드는 중, false: 커지는 중
            int speed = Math.max(1, origW / 10); // 10프레임 만에 절반이 돌도록 속도 자동 계산

            @Override
            public void actionPerformed(ActionEvent e) {
                if (shrinking) {
                    currentW -= speed;
                    if (currentW <= 0) {
                        currentW = 0;
                        shrinking = false;
                        // 너비가 0이 되는 순간 뒷면 이미지로 교체
                        cardLabel.setIcon(backIcon);
                    }
                } else {
                    currentW += speed;
                    if (currentW >= origW) {
                        currentW = origW;
                        ((Timer) e.getSource()).stop(); // 애니메이션 종료
                    }
                }

                // 제자리(중앙)에서 회전하도록 X좌표와 너비 갱신
                int newX = origX + (origW - currentW) / 2;
                cardLabel.setBounds(newX, origY, currentW, origH);
            }
        });
        timer.start();
    }
}
