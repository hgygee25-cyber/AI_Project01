package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rule_4 extends JPanel {
    public Rule_4() {
        setBounds(0, 0, 1072,598);
        setLayout(null);

        JLabel label0 = new JLabel("BETTING GUIDE");
        label0.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label0.setForeground(new Color(235, 200, 140));
        label0.setBounds(425, 20, 400, 30);
        add(label0);

        JLabel label1 = new JLabel("Texas Hold'em Guide.04");
        label1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label1.setForeground(Color.white);
        label1.setBounds(470, 60, 300, 15);
        add(label1);

        JLabel button1 = new JLabel("01. CHECK", JLabel.CENTER);
        button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button1.setBounds(100, 120, 270, 200);
        button1.setOpaque(true);
        button1.setBackground(new Color(230, 220, 195));
        button1.setForeground(Color.black);
        add(button1);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setText("<html><center>추가로 칩을 내지 않고 다음 차례로 넘어가는 행동<br>단, 현재 베팅 금액이 0일 때 가능</center></html>");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setText("01. CHECK");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));

            }
        });


        JLabel button2 = new JLabel("02. CALL", JLabel.CENTER);
        button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button2.setBounds(425, 120, 270, 200);
        button2.setOpaque(true);
        button2.setBackground(new Color(230, 220, 195));
        button2.setForeground(Color.black);
        add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setText("<html><center>앞사람이 베팅한 금액만큼 칩을 내는 행동</center></html>");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
                }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setText("02. CALL");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button3 = new JLabel("03. RAISE", JLabel.CENTER);
        button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button3.setBounds(750, 120, 270, 200);
        button3.setOpaque(true);
        button3.setBackground(new Color(230, 220, 195));
        button3.setForeground(Color.black);
        add(button3);

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setText("<html><center>현재 베팅 금액보다 더 많은 금액을 베팅하는 행동</center></html>");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button3.setText("03. RAISE");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });


        JLabel button4 = new JLabel("04. FOLD", JLabel.CENTER);
        button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button4.setBounds(100, 350, 270, 200);
        button4.setOpaque(true);
        button4.setBackground(new Color(230, 220, 195));
        button4.setForeground(Color.black);
        add(button4);

        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setText("<html><center>현재 판을 포기하고 게임에서 빠지는 행동</center></html>");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button4.setText("04. FOLD");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button5 = new JLabel("05. ALL-IN", JLabel.CENTER);
        button5.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button5.setBounds(425, 350, 270,200);
        button5.setOpaque(true);
        button5.setBackground(new Color(230, 220, 195));
        button5.setForeground(Color.black);
        add(button5);

        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button5.setText("<html><center>자신이 가진 남은 칩을 전부 베팅하는 행동</center></html>");
                button5.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button5.setText("05. ALL-IN");
                button5.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

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
                GUI_00.g1_1_4.setVisible(false);
                GUI_00.g1_1.setVisible(true);
            }
        });
    }
}
