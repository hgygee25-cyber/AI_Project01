package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rule_3 extends JPanel {
    public Rule_3() {
        setBounds(0, 0, 1072,598);
        setLayout(null);

        JLabel label0 = new JLabel("HAND RANKINGS");
        label0.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label0.setForeground(new Color(235, 200, 140));
        label0.setBounds(425, 20, 400, 30);
        add(label0);

        JLabel label1 = new JLabel("Texas Hold'em Guide.03");
        label1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label1.setForeground(Color.white);
        label1.setBounds(470, 60, 300, 15);
        add(label1);

        JLabel label2 = new JLabel("카드 족보");
        label2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label2.setForeground(new Color(235, 200, 140));
        label2.setBounds(100, 80, 300, 20);
        add(label2);

        JLabel label3 = new JLabel("아래 족보는 높은 점수부터 순서대로 정렬되어 있습니다.");
        label3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label3.setForeground(Color.white);
        label3.setBounds(100, 110, 500, 15);
        add(label3);

        JLabel button1 = new JLabel("01. 로열 플러시", JLabel.CENTER);
        button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button1.setBounds(100, 140, 150, 150);
        button1.setOpaque(true);
        button1.setBackground(new Color(230, 220, 195));
        button1.setForeground(Color.black);
        add(button1);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setText("<html><center>같은 무늬의 A, K, Q, J, 10</center></html>");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setText("01. 로열 플러시");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));

            }
        });


        JLabel button2 = new JLabel("02. 스트레이트 플러시", JLabel.CENTER);
        button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button2.setBounds(300, 140, 150, 150);
        button2.setOpaque(true);
        button2.setBackground(new Color(230, 220, 195));
        button2.setForeground(Color.black);
        add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setText("<html><center>같은 무늬의 연속된 숫자 5장</center></html>");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
                }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setText("02. 스트레이트 플러시");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button3 = new JLabel("03. 포카드", JLabel.CENTER);
        button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button3.setBounds(500, 140, 150, 150);
        button3.setOpaque(true);
        button3.setBackground(new Color(230, 220, 195));
        button3.setForeground(Color.black);
        add(button3);

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setText("<html><center>같은 숫자 카드 4장</center></html>");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button3.setText("03. 포카드");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });


        JLabel button4 = new JLabel("04. 풀하우스", JLabel.CENTER);
        button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button4.setBounds(700, 140, 150, 150);
        button4.setOpaque(true);
        button4.setBackground(new Color(230, 220, 195));
        button4.setForeground(Color.black);
        add(button4);

        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setText("<html><center>같은 숫자 3장과 같은 숫자 2장</center></html>");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button4.setText("04. 풀하우스");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button5 = new JLabel("05. 플러시", JLabel.CENTER);
        button5.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button5.setBounds(900, 140, 150, 150);
        button5.setOpaque(true);
        button5.setBackground(new Color(230, 220, 195));
        button5.setForeground(Color.black);
        add(button5);

        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button5.setText("<html><center>숫자가 일정하지 않은 같은 무늬 카드 5장</center></html>");
                button5.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button5.setText("05. 플러시");
                button5.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button6 = new JLabel("06. 스트레이트", JLabel.CENTER);
        button6.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button6.setBounds(100, 340, 150, 150);
        button6.setOpaque(true);
        button6.setBackground(new Color(230, 220, 195));
        button6.setForeground(Color.black);
        add(button6);

        button6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button6.setText("<html><center>연속된 숫자 카드 5장</center></html>");
                button6.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button6.setText("06. 스트레이트");
                button6.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));

            }
        });


        JLabel button7 = new JLabel("07. 트리플", JLabel.CENTER);
        button7.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button7.setBounds(300, 340, 150, 150);
        button7.setOpaque(true);
        button7.setBackground(new Color(230, 220, 195));
        button7.setForeground(Color.black);
        add(button7);

        button7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button7.setText("<html><center>같은 숫자 카드 3장</center></html>");
                button7.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
                }

            @Override
            public void mouseExited(MouseEvent e) {
                button7.setText("07. 트리플");
                button7.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button8 = new JLabel("08. 투 페어", JLabel.CENTER);
        button8.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button8.setBounds(500, 340, 150, 150);
        button8.setOpaque(true);
        button8.setBackground(new Color(230, 220, 195));
        button8.setForeground(Color.black);
        add(button8);

        button8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button8.setText("<html><center>같은 숫자 카드 2장씩 두 쌍</center></html>");
                button8.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button8.setText("08. 투 페어");
                button8.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });


        JLabel button9 = new JLabel("09. 원 페어", JLabel.CENTER);
        button9.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button9.setBounds(700, 340, 150, 150);
        button9.setOpaque(true);
        button9.setBackground(new Color(230, 220, 195));
        button9.setForeground(Color.black);
        add(button9);

        button9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button9.setText("<html><center>같은 숫자 카드 2장</center></html>");
                button9.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button9.setText("09. 원 페어");
                button9.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button10 = new JLabel("10. 하이 카드", JLabel.CENTER);
        button10.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button10.setBounds(900, 340, 150, 150);
        button10.setOpaque(true);
        button10.setBackground(new Color(230, 220, 195));
        button10.setForeground(Color.black);
        add(button10);

        button10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button10.setText("<html><center>다른 족보에 해당되지 않는 경우</center></html>");
                button10.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button10.setText("10. 하이 카드");
                button10.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
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
                GUI_00.g1_1_3.setVisible(false);
                GUI_00.g1_1.setVisible(true);
            }
        });
    }
}
