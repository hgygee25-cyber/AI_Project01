package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rule_5 extends JPanel {
    public Rule_5() {
        setBounds(0, 0, 1072,598);
        setLayout(null);

        JLabel label0 = new JLabel("WINNING RULES");
        label0.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label0.setForeground(new Color(235, 200, 140));
        label0.setBounds(425, 20, 400, 30);
        add(label0);

        JLabel label1 = new JLabel("Texas Hold'em Guide.05");
        label1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label1.setForeground(Color.white);
        label1.setBounds(470, 60, 300, 15);
        add(label1);

        JLabel button1 = new JLabel("01. 족보 비교", JLabel.CENTER);
        button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button1.setBounds(100, 120, 270, 200);
        button1.setOpaque(true);
        button1.setBackground(new Color(230, 220, 195));
        button1.setForeground(Color.black);
        add(button1);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setText("<html><center>각 플레이어는 자신의 카드 2장과 공용 카드 5장 중 가장 좋은 5장을 선택하여 족보를 완성</center></html>");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setText("01. 족보 비교");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));

            }
        });


        JLabel button2 = new JLabel("02. 높은 족보 승리", JLabel.CENTER);
        button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button2.setBounds(425, 120, 270, 200);
        button2.setOpaque(true);
        button2.setBackground(new Color(230, 220, 195));
        button2.setForeground(Color.black);
        add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setText("<html><center>완성된 족보의 순위를 비교하여 더 높은 족보를 가진 플레이어가 승리</center></html>");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
                }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setText("02. 높은 족보 승리");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button3 = new JLabel("03. 동점 판정", JLabel.CENTER);
        button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button3.setBounds(750, 120, 270, 200);
        button3.setOpaque(true);
        button3.setBackground(new Color(230, 220, 195));
        button3.setForeground(Color.black);
        add(button3);

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setText("<html><center>족보가 같으면 구성 카드의 숫자를 차례로 비교하며, 필요하면 키커까지 비교</center></html>");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button3.setText("03. 동점 판정");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });


        JLabel button4 = new JLabel("04. 상대방의 포기", JLabel.CENTER);
        button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button4.setBounds(100, 350, 270, 200);
        button4.setOpaque(true);
        button4.setBackground(new Color(230, 220, 195));
        button4.setForeground(Color.black);
        add(button4);

        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setText("<html><center>한 명을 제외한 모든 플레이어가 폴드하면 남은 플레이어가 승리</center></html>");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button4.setText("04. 상대방의 포기");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
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
                GUI_00.g1_1_5.setVisible(false);
                GUI_00.g1_1.setVisible(true);
            }
        });
    }
}
