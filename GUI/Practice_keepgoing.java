package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_keepgoing extends JPanel {
    public Practice_keepgoing() {
        System.out.println("Practice_keepgoing");
        setBounds(300, 270, 400, 100);
        setLayout(null);
        setOpaque(false); // 배경을 불투명하게 명시적으로 설정
        
        JLabel label = new JLabel("KEEP GOING?");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setForeground(Color.BLACK);
        label.setBounds(130, 10, 200, 25); 
        add(label);

        JButton button1 = new JButton("STOP");
        button1.setFont(new Font("Serif", Font.BOLD, 12));
        button1.setBounds(105, 50, 75, 35); 
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setFocusPainted(false);
        button1.setBorderPainted(false);
        button1.setOpaque(true);
        add(button1);

        JButton button2 = new JButton("GO");
        button2.setFont(new Font("Serif", Font.BOLD, 12));
        button2.setBounds(220, 50, 75, 35); 
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setFocusPainted(false);
        button2.setBorderPainted(false);
        button2.setOpaque(true);
        add(button2);

        button1.addActionListener(e -> {
    button1.setEnabled(false);
    button2.setEnabled(false);

    // 커뮤니티 카드 5장 공개 여부 확인
    if (Practice.choosed_card == null) {
        System.out.println("카드 데이터가 없습니다.");
        return;
    }

    int cardCount = Practice.choosed_card.size() - 4;

    if (cardCount < 3) {
        Practice_Basic_card.community_card();
    }

    if (Practice.choosed_card.size() < 8) {
        Practice_Basic_up_card.community_card();
    }

    if (Practice.choosed_card.size() < 9) {
        Practice_Basic_upup_card.community_card();
    }

    // 승패 판정
    Practice_End.checkWinner();

    // END 화면 전환
    GUI_00.g1_2_7.setVisible(false);
    GUI_00.g1_2_5.setVisible(false);
    GUI_00.g1_2_6.setVisible(false);
    GUI_00.g1_2_4.setVisible(true);
});
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "연습 홀덤 게임에서는 이어서 게임을 진행할 수 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        addMouseListener(new MouseAdapter() {});
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255, 130)); 
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
