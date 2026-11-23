
package GUI;

import GUI_1.WinnerJudge;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_End extends JPanel {

    static JLabel label1;
    static JLabel label2;
    static JLabel label3;

    public static void checkWinner() {

        if (Practice.usercards == null ||
            Practice.comcards == null ||
            Practice.communitycards == null) {

            System.out.println("승패 판정 실패: 카드 데이터가 null입니다.");
            label1.setText("카드 데이터 오류");
            label2.setText("");
            label3.setText("");
            return;
        }

        if (Practice.usercards.size() != 2 ||
            Practice.comcards.size() != 2 ||
            Practice.communitycards.size() != 5) {

            System.out.println("승패 판정 실패");
            System.out.println("USER 카드: " + Practice.usercards.size());
            System.out.println("COM 카드: " + Practice.comcards.size());
            System.out.println("커뮤니티 카드: " + Practice.communitycards.size());

            label1.setText("카드 공개가 완료되지 않았습니다.");
            label2.setText("");
            label3.setText("");
            return;
        }

        try {
            Map<String, Object> result =
                WinnerJudge.compare(
                    Practice.usercards,
                    Practice.comcards,
                    Practice.communitycards
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

        } catch (Exception e) {
            e.printStackTrace();

            label1.setText("승패 판정 오류");
            label2.setText("");
            label3.setText("");
        }
    }

    public Practice_End() {

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setOpaque(false);

        JLabel endLabel = new JLabel("GAME RESULT", SwingConstants.CENTER);
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

        JLabel backLabel = new JLabel("BACK");
        backLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        backLabel.setForeground(new Color(235, 200, 140));
        backLabel.setBounds(20, 550, 200, 50);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backLabel);

        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                GUI_00.g1_2.setVisible(false);
                GUI_00.g1.setVisible(true);
                GUI_00.g1_2_5.setVisible(false);
                GUI_00.g1_2_4.setVisible(false);
                GUI_00.g1_2_6.setVisible(false);
                GUI_00.g1_2_3.setVisible(false);
                GUI_00.g1_2_7.setVisible(false);
                GUI_00.g.setVisible(false);

                Practice.returnToStart();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
