
package GUI;

import GUI_1.WinnerJudge;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TexasHoldem_End extends JPanel {

    // 1. 결과 표시
    static JLabel label1;
    static JLabel label2;
    static JLabel label3;

    // 2. 승패 판정
    public static void checkWinner() {

        if (TexasHoldem.usercards == null
                || TexasHoldem.comcards == null
                || TexasHoldem.communitycards == null) {
            return;
        }

        if (TexasHoldem.usercards.size() != 2
                || TexasHoldem.comcards.size() != 2
                || TexasHoldem.communitycards.size() != 5) {
            return;
        }

        try {

            Map<String, Object> result = WinnerJudge.compare(
                    TexasHoldem.usercards,
                    TexasHoldem.comcards,
                    TexasHoldem.communitycards
            );

            String winner = (String) result.get("winner");

            Map<String, Object> playerHand =
                    (Map<String, Object>) result.get("player_hand");

            Map<String, Object> computerHand =
                    (Map<String, Object>) result.get("computer_hand");

            String playerName = (String) playerHand.get("name");
            String computerName = (String) computerHand.get("name");

            System.out.println("승패 결과: " + winner);
            System.out.println("사용자 족보: " + playerName);
            System.out.println("컴퓨터 족보: " + computerName);

            label1.setText(winner);
            label2.setText("USER : " + playerName);
            label3.setText("COM : " + computerName);

            // 실제 게임 칩 정산은 베팅 시스템 연결 후 호출

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "카드 판정 오류",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void showResult(String winner, String playerName, String computerName) {
        label1.setText(winner);
        label2.setText("USER : " + playerName);
        label3.setText("COM : " + computerName);
        GUI_00.g4.setVisible(false);
        GUI_00.g4_donon.setVisible(false);
        GUI_00.g4_money.setVisible(false);
        GUI_00.g4_money_user.setVisible(false);
        GUI_00.g4_end.setVisible(true);
        GUI_00.g4_end.repaint();
    }

    // 3. 생성자
    public TexasHoldem_End() {

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setOpaque(false);

        JLabel endLabel = new JLabel(
                "GAME RESULT",
                SwingConstants.CENTER
        );

        endLabel.setBounds(0, 170, 1072, 80);
        endLabel.setFont(new Font("맑은 고딕", Font.BOLD, 45));
        endLabel.setForeground(Color.WHITE);
        add(endLabel);

        label1 = new JLabel("", SwingConstants.CENTER);
        label1.setBounds(320, 270, 432, 55);
        label1.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        label1.setForeground(new Color(235, 200, 140));
        label1.setOpaque(true);
        label1.setBackground(new Color(45, 35, 30));
        label1.setBorder(
                BorderFactory.createLineBorder(
                        new Color(235, 200, 140), 2
                )
        );
        add(label1);

        label2 = new JLabel("", SwingConstants.CENTER);
        label2.setBounds(320, 345, 432, 35);
        label2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label2.setForeground(Color.WHITE);
        add(label2);

        label3 = new JLabel("", SwingConstants.CENTER);
        label3.setBounds(320, 390, 432, 35);
        label3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label3.setForeground(Color.WHITE);
        add(label3);

        // 4. 홈으로 돌아가기
        JButton backButton = new JButton("BACK TO HOME");
        backButton.setBounds(411, 490, 250, 50);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(190, 145, 65));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        add(backButton);

        backButton.addActionListener(e -> {
            GUI_00.g4_end.setVisible(false);
            GUI_00.g4.setVisible(false);
            TexasHoldem.returnToStart();
            GUI_00.g0.setVisible(true);
            first_frame_store.username(TextDatabase.getUserName());
        });
    }

    // 5. 배경
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
