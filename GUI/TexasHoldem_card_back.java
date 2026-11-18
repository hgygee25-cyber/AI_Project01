
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TexasHoldem_card_back {

    public static void flipToBack(
            JLabel cardLabel,
            String value1,
            String value2
    ) {

        // 1. 카드의 원래 위치와 크기 저장
        final int origX = cardLabel.getX();
        final int origY = cardLabel.getY();
        final int origW = cardLabel.getWidth();
        final int origH = cardLabel.getHeight();

        // 2. 카드 뒷면 이미지 준비
        String value = value1 + "." + value2;
        String backImagePath = "image_card/" + value + ".png";

        ImageIcon rawIcon = new ImageIcon(backImagePath);

        Image scaledImg = rawIcon.getImage().getScaledInstance(
                origW,
                origH,
                Image.SCALE_SMOOTH
        );

        final ImageIcon backIcon = new ImageIcon(scaledImg);

        // 3. 카드 뒤집기 애니메이션
        Timer timer = new Timer(15, new ActionListener() {

            int currentW = origW;
            boolean shrinking = true;
            int speed = Math.max(1, origW / 10);

            @Override
            public void actionPerformed(ActionEvent e) {

                if (shrinking) {

                    currentW -= speed;

                    if (currentW <= 0) {
                        currentW = 0;
                        shrinking = false;

                        // 너비가 0이 되면 카드 이미지 교체
                        cardLabel.setIcon(backIcon);
                    }

                } else {

                    currentW += speed;

                    if (currentW >= origW) {
                        currentW = origW;
                        ((Timer) e.getSource()).stop();
                    }
                }

                // 4. 중앙을 기준으로 너비 변경
                int newX = origX + (origW - currentW) / 2;

                cardLabel.setBounds(
                        newX,
                        origY,
                        currentW,
                        origH
                );
            }
        });

        timer.start();
    }
}
