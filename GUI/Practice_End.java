package GUI;

import GUI_1.WinnerJudge;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_End extends JPanel {
    //
    static JLabel label1;
    //
    public static void checkWinner() {
        if (Practice.usercards == null || Practice.comcards == null || Practice.communitycards == null) {
            return;
        }

        if (Practice.usercards.size() != 2 || Practice.comcards.size() != 2 || Practice.communitycards.size() != 5) {
            return;
        }

        Map<String, Object> result = WinnerJudge.compare(Practice.usercards, Practice.comcards, Practice.communitycards);
        String winner = (String) result.get("winner");
        System.out.println("승패 결과: " + winner);

        label1.setText(winner);
    }
    //
    public Practice_End() {
        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setOpaque(false);

        // 안내 문구 JLabel 생성
        JLabel endLabel = new JLabel("게임 종료", SwingConstants.CENTER);
        endLabel.setBounds(0, 230, 1072, 80);
        endLabel.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        endLabel.setForeground(Color.WHITE);
        add(endLabel);

        label1 = new JLabel("", SwingConstants.CENTER);
        label1.setBounds(320, 380, 500, 50);
        label1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        label1.setForeground(new Color(235, 200, 140));
        label1.setOpaque(true);
        label1.setBackground(new Color(45, 35, 30));
        label1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(235, 200, 140), 2), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(label1);

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        //
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.g1_2.setVisible(false);
                GUI_00.g1.setVisible(true);
                GUI_00.g1_2_5.setVisible(false);
                GUI_00.g1_2_4.setVisible(false);
                GUI_00.g1_2_6.setVisible(false);
                GUI_00.g1_2_3.setVisible(false);
                GUI_00.g1_2_7.setVisible(false);
                GUI_00.g.setVisible(true);
                Practice.returnToStart();
            }
        });

        addMouseListener(new MouseAdapter() {});
    }

    // 패널 배경을 반투명 검은색으로 칠하는 메서드
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
