package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Practice_donon extends JPanel{
    static int result = -1;
    public Practice_donon() {
		System.out.println("Practice_donon");
        setBounds(380, 180, 280, 200);
        setLayout(null);
        setBackground(Color.black);
        
        JLabel label = new JLabel("<html>KEEP PLAYING THE GAME?<br><br>Betting Fee : 100</html>");
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

        Practice.button1.setEnabled(false);
        Practice.button2.setEnabled(false);
        Practice.button3.setEnabled(false);
        Practice.button4.setEnabled(false);
        Practice.button5.setEnabled(false);

        button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                System.out.println("Bettin click!");
                result = 1;
                Practice_money.bettingmoney.setText("1000");
                Practice_money.bettingmoneycom.setText("1000");
                Practice_money.updateTotalcom();
                Practice_money.updateTotalUser();
                Practice_money_User.update_usermoney();
                GUI_00.g1_2_3.setVisible(false);
                GUI_00.g1_2_5.setVisible(true);
                GUI_00.g1_2_6.setVisible(true);
                Practice.button1.setEnabled(true);
                Practice.button2.setEnabled(true);
                Practice.button3.setEnabled(true);
                Practice.button4.setEnabled(true);
                Practice.button5.setEnabled(true);
                //2.. 플랍
                Practice_Basic_card.community_card();
                //2.. 플랍 승률 계산
                Practice.percent();
            }
        });

        button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                System.out.println("Fold click!");
                result = 0;
                GUI_00.g1_2_3.setVisible(false);
                System.out.println("게임 종료");
                //여기에 메소드 띄워야함
                GUI_00.g1_2_7.setVisible(true);              
            }
        });
        addMouseListener(new MouseAdapter() {});
    }
}
