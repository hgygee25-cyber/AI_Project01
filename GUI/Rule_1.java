package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rule_1 extends JPanel{
    public Rule_1() {
        setBounds(0, 0, 1072,598);
        setLayout(null);
        
        JLabel label01 = new JLabel("01. 텍사스 홀덤이란?");
        label01.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 20));
        label01.setForeground(new Color(235, 200, 140));
        label01.setBounds(50, 100, 300,50);
        add(label01);

        JLabel label01_1 = new JLabel("플레이어마다 개인 카드 2장을 받고, 테이블에 공개되는 커뮤니티 카드 5장을 조합하여 승패를 결정하는 카드 게임입니다.");
        label01_1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        label01_1.setForeground(Color.white);
        label01_1.setOpaque(true);
        label01_1.setBackground(new Color(0, 0, 0, 100));
        label01_1.setBounds(50, 135, 1000,35);
        add(label01_1);

        JLabel label02 = new JLabel("02. 게임의 목표");
        label02.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 20));
        label02.setForeground(new Color(235, 200, 140));
        label02.setBounds(50, 200, 300,50);
        add(label02);

        JLabel label02_1 = new JLabel("상대보다 높은 족보를 완성하거나 상대방이 폴드하도록 만들어 팟을 획득하는 것이 목표입니다.");
        label02_1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        label02_1.setForeground(Color.white);
        label02_1.setOpaque(true);
        label02_1.setBackground(new Color(0, 0, 0, 100));
        label02_1.setBounds(50, 235, 1000,35);
        add(label02_1);

        JLabel label03 = new JLabel("03. 카드 구성");
        label03.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 20));
        label03.setForeground(new Color(235, 200, 140));
        label03.setBounds(50, 300, 300,50);
        add(label03);

        JLabel label03_1 = new JLabel("개인 카드 2장과 공용 카드 5장, 총 7장 중 가장 강력한 5장을 선택합니다.");
        label03_1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        label03_1.setForeground(Color.white);
        label03_1.setOpaque(true);
        label03_1.setBackground(new Color(0, 0, 0, 100));
        label03_1.setBounds(50, 335, 1000,35);
        add(label03_1);

        JLabel label04 = new JLabel("04. 게임 환경");
        label04.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 20));
        label04.setForeground(new Color(235, 200, 140));
        label04.setBounds(50, 400, 300,50);
        add(label04);

        JLabel label04_1 = new JLabel("플레이어와 컴퓨터가 각각 50,000칩으로 시작하여 대결합니다.");
        label04_1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        label04_1.setForeground(Color.white);
        label04_1.setOpaque(true);
        label04_1.setBackground(new Color(0, 0, 0, 100));
        label04_1.setBounds(50, 435, 1000,35);
        add(label04_1);

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(520, 540, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);

        ImageIcon icon1 = new ImageIcon("image/rule_1_1.png");
        Image image1 = icon1.getImage();
        Image scaledimage1 = image1.getScaledInstance(1072,598, Image.SCALE_SMOOTH);
        ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
        JLabel label_image1 = new JLabel(scaledicon1);
        label_image1.setBounds(0,0,1072,598);
        add(label_image1);

        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_1.setVisible(false);
                GUI_00.g1_1.setVisible(true);
            }
        });
    }
}
