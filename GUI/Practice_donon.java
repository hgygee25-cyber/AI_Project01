
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import GUI_4.DecisionResult;

public class Practice_donon extends JPanel {

    static int result = -1;

    public Practice_donon() {

        System.out.println("Practice_donon");

        setBounds(380, 180, 280, 200);
        setLayout(null);
        setBackground(Color.black);

        JLabel label = new JLabel(
            "<html>KEEP PLAYING THE GAME?<br><br>Betting Fee : 100</html>"
        );

        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setForeground(Color.white);
        label.setBounds(10, 5, 270, 120);
        add(label);

        JButton button1 = new JButton("Betting");
        button1.setFont(new Font("Serif", Font.BOLD, 12));
        button1.setBounds(55, 140, 75, 35);
        button1.setBackground(new Color(239, 136, 190));
        button1.setForeground(new Color(153, 217, 234));
        button1.setFocusPainted(false);
        button1.setBorderPainted(false);
        button1.setOpaque(true);
        add(button1);

        JLabel label1 = new JLabel("or");
        label1.setFont(new Font("Serif", Font.PLAIN, 20));
        label1.setForeground(Color.white);
        label1.setBounds(140, 150, 50, 20);
        add(label1);

        JButton button2 = new JButton("Fold");
        button2.setFont(new Font("Serif", Font.BOLD, 12));
        button2.setBounds(160, 140, 75, 35);
        button2.setBackground(new Color(239, 136, 190));
        button2.setForeground(new Color(153, 217, 234));
        button2.setFocusPainted(false);
        button2.setBorderPainted(false);
        button2.setOpaque(true);
        add(button2);

        // 처음에는 사용자 버튼 비활성화
        disableUserButtons();

        // Betting 버튼
        button1.addActionListener(e -> {

            System.out.println("Betting click!");

            result = 1;

            // 프리플랍 양쪽 베팅 금액 설정
            Practice_money.bettingmoney.setText("1000");
            Practice_money.bettingmoneycom.setText("1000");

            // 잔액 동기화
            Practice_Betting.syncMoney();

            Practice_money.updateTotalcom();
            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();

            // 화면 전환
            GUI_00.g1_2_3.setVisible(false);
            GUI_00.g1_2_5.setVisible(true);
            GUI_00.g1_2_6.setVisible(true);

            // 플랍 카드 공개
            Practice_Basic_card.community_card();

            // 플랍 승률 계산
            Practice.percent();

            // AI 첫 판단 (중복 호출 금지)
            DecisionResult aiResult = PracticeAI.decide(1);

            if (aiResult == null || !aiResult.isSuccess()) {
                System.out.println("AI 판단 실패");

                disableUserButtons();
                return;
            }

            // AI 베팅 반영
            boolean applied =
                Practice_Betting.applyComputerDecision(1);

            if (!applied) {
                System.out.println("AI 베팅 적용 실패");

                disableUserButtons();
                return;
            }

            // AI 행동이 끝난 뒤 사용자 버튼 활성화
            enableUserButtons();

            System.out.println("플랍 AI 판단 완료");
        });

        // Fold 버튼
        button2.addActionListener(e -> {

            System.out.println("Fold click!");

            result = 0;

            GUI_00.g1_2_3.setVisible(false);

            disableUserButtons();

            System.out.println("게임 종료");

            GUI_00.g1_2_7.setVisible(true);
        });
    }

    // 사용자 버튼 활성화
    private void enableUserButtons() {

        Practice.button1.setEnabled(true);
        Practice.button2.setEnabled(true);
        Practice.button3.setEnabled(true);
        Practice.button4.setEnabled(true);
        Practice.button5.setEnabled(true);
    }

    // 사용자 버튼 비활성화
    private void disableUserButtons() {

        Practice.button1.setEnabled(false);
        Practice.button2.setEnabled(false);
        Practice.button3.setEnabled(false);
        Practice.button4.setEnabled(false);
        Practice.button5.setEnabled(false);
    }
}
