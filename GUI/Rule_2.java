package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rule_2 extends JPanel{
    public Rule_2() {
        setBounds(0, 0, 1072,598);
        setLayout(null);

        JLabel label0 = new JLabel("GAME FLOW");
        label0.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label0.setForeground(new Color(235, 200, 140));
        label0.setBounds(490, 20, 400, 30);
        add(label0);

        JLabel label1 = new JLabel("Texas Hold'em Guide.02");
        label1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label1.setForeground(Color.white);
        label1.setBounds(470, 60, 300, 15);
        add(label1);

        JLabel label2 = new JLabel("게임 진행 순서");
        label2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label2.setForeground(new Color(235, 200, 140));
        label2.setBounds(100, 80, 300, 20);
        add(label2);

        JLabel label3 = new JLabel("개인 카드 2장과 커뮤니티 카드 5장을 이용하여 승자를 결정하는 과정");
        label3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label3.setForeground(Color.white);
        label3.setBounds(100, 110, 500, 15);
        add(label3);

        JLabel label4 = new JLabel("** 각 단계마다 베팅 라운드가 진행되며, 모든 플레이어가 폴드하면 쇼다운 없이 종료될 수 있음");
        label4.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label4.setForeground(Color.white);
        label4.setBounds(100, 520, 700, 15);
        add(label4);

        JLabel button1 = new JLabel("01. PRE-FLOP(프리플랍)", JLabel.CENTER);
        button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button1.setBounds(100, 140, 250, 150);
        button1.setOpaque(true);
        button1.setBackground(new Color(230, 220, 195));
        button1.setForeground(Color.black);
        add(button1);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setText("<html><center>각 플레이어에게 개인 카드 2장 배분<br><br>스몰 블라인드와 빅 블라인드 지불<br><br>첫 번째 베팅 라운드 진행<br><br>베팅이 종료되면 플랍으로 이동</center></html>");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setText("01. PRE-FLOP(프리플랍)");
                button1.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));

            }
        });


        JLabel button2 = new JLabel("02. FLOP(플랍)", JLabel.CENTER);
        button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button2.setBounds(400, 140, 250, 150);
        button2.setOpaque(true);
        button2.setBackground(new Color(230, 220, 195));
        button2.setForeground(Color.black);
        add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setText("<html><center>커뮤니티 카드 3장 공개<br><br>개인 카드와 공개 카드를 조합하여 족보 확인<br><br>두 번째 베팅 라운드 진행</center></html>");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
                }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setText("02. FLOP(플랍)");
                button2.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button3 = new JLabel("03. TURN(턴)", JLabel.CENTER);
        button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button3.setBounds(700, 140, 250, 150);
        button3.setOpaque(true);
        button3.setBackground(new Color(230, 220, 195));
        button3.setForeground(Color.black);
        add(button3);

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setText("<html><center>네 번째 커뮤니티 카드 공개<br><br>현재 가능한 카드 조합 확인<br><br>세 번째 베팅 라운드 진행</center></html>");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button3.setText("03. TURN(턴)");
                button3.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });


        JLabel button4 = new JLabel("04. RIVER(리버)", JLabel.CENTER);
        button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button4.setBounds(200, 350, 300, 150);
        button4.setOpaque(true);
        button4.setBackground(new Color(230, 220, 195));
        button4.setForeground(Color.black);
        add(button4);

        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setText("<html><center>다섯 번째 커뮤니티 카드 공개<br><br>최종적으로 만들 수 있는 족보 확인<br><br>마지막 베팅 라운드 진행</center></html>");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button4.setText("04. RIVER(리버)");
                button4.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
            }
        });

        JLabel button5 = new JLabel("05. SHOWDOWN(쇼다운)", JLabel.CENTER);
        button5.setFont(new Font("메모먼트꾹꾹체", Font.BOLD, 15));
        button5.setBounds(600, 350, 300, 150);
        button5.setOpaque(true);
        button5.setBackground(new Color(230, 220, 195));
        button5.setForeground(Color.black);
        add(button5);

        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button5.setText("<html><center>남아 있는 플레이어의 개인 카드 공개<br><br>개인 카드 2장과 커뮤니티 카드 5장 중 가장 좋은 5장으로 족보 구성<br><br>족보 비교 후 승자 결정</center></html>");
                button5.setFont(new Font("메모먼트꾹꾹체", Font.PLAIN, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button5.setText("05. SHOWDOWN(쇼다운)");
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
                GUI_00.g1_1_2.setVisible(false);
                GUI_00.g1_1.setVisible(true);
            }
        });
    }
}
