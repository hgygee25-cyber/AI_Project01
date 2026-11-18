package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_money extends JPanel{
    static JLabel rivermoney;
    static JLabel turnmoney;
    static JLabel flopmoney;

    static JLabel comrivermoney;
    static JLabel comturnmoney;
    static JLabel comflopmoney;
    
    static JLabel bettingmoney;
    static JLabel bettingmoneycom;
    
    static JLabel totalcom;
    static JLabel totaluser;
    
    static int sum_user;
    static int sum_com;
    //
    public static int updateTotalUser() {
        sum_user = parseMoney(bettingmoney.getText()) + parseMoney(flopmoney.getText()) + parseMoney(turnmoney.getText()) + parseMoney(rivermoney.getText());
        totaluser.setText(String.valueOf(sum_user));
        return sum_user;
    }
    public static void updateTotalcom() {
        sum_com = parseMoney(bettingmoneycom.getText()) + parseMoney(comflopmoney.getText()) + parseMoney(comturnmoney.getText()) + parseMoney(comrivermoney.getText());
        totalcom.setText(String.valueOf(sum_com));
    }
    public static int parseMoney(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(text.trim());
    }
    //
    public Practice_money() {
        setBounds(705, 385, 270, 140);
        setLayout(null);
        setOpaque(false);

        JLabel label = new JLabel("BETTING MONEY", SwingConstants.CENTER);
        label.setBounds(10, 5, 260, 20);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(new Color(248, 239, 218));
        add(label);

        JLabel label1 = new JLabel("COM", SwingConstants.CENTER);
        label1.setBounds(15, 30, 50, 15);
        label1.setFont(new Font("Serif", Font.PLAIN, 15));
        label1.setForeground(new Color(248, 239, 218));
        add(label1);

        JLabel vLine = new JLabel();
        vLine.setBounds(75, 35, 1, 90);
        vLine.setOpaque(true);
        vLine.setBackground(new Color(184, 145, 91));
        add(vLine);

        JLabel label2 = new JLabel("USER", SwingConstants.CENTER);
        label2.setBounds(90, 30, 50, 15);
        label2.setFont(new Font("Serif", Font.PLAIN, 15));
        label2.setForeground(new Color(248, 239, 218));
        add(label2);

        JLabel vLine2 = new JLabel();
        vLine2.setBounds(155, 35, 1, 90);
        vLine2.setOpaque(true);
        vLine2.setBackground(new Color(184, 145, 91));
        add(vLine2);

        JLabel label3 = new JLabel("TOTAL", SwingConstants.CENTER);
        label3.setBounds(170, 30, 60, 15);
        label3.setFont(new Font("Serif", Font.PLAIN, 15));
        label3.setForeground(new Color(248, 239, 218));
        add(label3);

        JLabel totalc = new JLabel("COM");
        totalc.setBounds(160, 60, 60, 14);
        totalc.setFont(new Font("Serif", Font.BOLD, 14));
        totalc.setForeground(new Color(248, 239, 218));
        add(totalc);
        
        JLabel totalu = new JLabel("USER");
        totalu.setBounds(160, 100, 60, 14);
        totalu.setFont(new Font("Serif", Font.BOLD, 14));
        totalu.setForeground(new Color(248, 239, 218));
        add(totalu);
        
        //bettingmoney  flopmoney  turnmoney  rivermoney
        bettingmoney = new JLabel("");
        bettingmoney.setBounds(100, 50, 50, 13);
        bettingmoney.setFont(new Font("Serif", Font.PLAIN, 13));
        bettingmoney.setForeground(Color.white);
        add(bettingmoney);

        flopmoney = new JLabel("");
        flopmoney.setBounds(100, 70, 50, 13);
        flopmoney.setFont(new Font("Serif", Font.PLAIN, 13));
        flopmoney.setForeground(Color.white);
        add(flopmoney);
        
        turnmoney = new JLabel("");
        turnmoney.setBounds(100, 90, 50, 13);
        turnmoney.setFont(new Font("Serif", Font.PLAIN, 13));
        turnmoney.setForeground(Color.white);
        add(turnmoney);
        
        rivermoney = new JLabel("");
        rivermoney.setBounds(100, 110, 50, 13);
        rivermoney.setFont(new Font("Serif", Font.PLAIN, 13));
        rivermoney.setForeground(Color.white);
        add(rivermoney);

        //
        bettingmoneycom = new JLabel("");
        bettingmoneycom.setBounds(20, 50, 50, 13);
        bettingmoneycom.setFont(new Font("Serif", Font.PLAIN, 13));
        bettingmoneycom.setForeground(Color.white);
        add(bettingmoneycom);

        comflopmoney = new JLabel("");
        comflopmoney.setBounds(20, 70, 50, 13);
        comflopmoney.setFont(new Font("Serif", Font.PLAIN, 13));
        comflopmoney.setForeground(Color.white);
        add(comflopmoney);
        
        comturnmoney = new JLabel("");
        comturnmoney.setBounds(20, 90, 50, 13);
        comturnmoney.setFont(new Font("Serif", Font.PLAIN, 13));
        comturnmoney.setForeground(Color.white);
        add(comturnmoney);
        
        comrivermoney = new JLabel("");
        comrivermoney.setBounds(20, 110, 50, 13);
        comrivermoney.setFont(new Font("Serif", Font.PLAIN, 13));
        comrivermoney.setForeground(Color.white);
        add(comrivermoney);

        totalcom = new JLabel("");
        totalcom.setBounds(205, 60, 50, 14);
        totalcom.setFont(new Font("Serif", Font.BOLD, 14));
        totalcom.setForeground(Color.white);
        add(totalcom);
        
        totaluser = new JLabel("");
        totaluser.setBounds(205, 100, 50, 14);
        totaluser.setFont(new Font("Serif", Font.BOLD, 14));
        totaluser.setForeground(Color.white);
        add(totaluser);
        updateTotalUser();
        updateTotalcom();
        addMouseListener(new MouseAdapter() {});
    }

    // 패널 배경을 반투명 검은색으로 칠하는 메서드
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 180));
        
        int arc = 30; 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);   
        g2.dispose();
    }
}
