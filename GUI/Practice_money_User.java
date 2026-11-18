package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Practice_money_User extends JPanel{
    static JLabel label;
    static JLabel label06;
    //
    public static int update() {
        int usermoney = Practice_money.updateTotalUser();
        return usermoney;
    }
    public static void update_usermoney() {
        int a = 50000 - update();
        label06.setText("<html>USER_CHEAP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='white'>" + a + "</font></html>");
    }
    //
    public Practice_money_User() {
        setBounds(190, 350, 245, 35);
        setLayout(null);
        setOpaque(false);
        
        int a = 50000 - update();

        label06 = new JLabel("<html>USER_CHEAP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='white'>" + a + "</font></html>");
        label06.setFont(new Font("Serif", Font.BOLD, 14));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(10, 8, 240, 16);
        add(label06);
        
        addMouseListener(new MouseAdapter() {});
    }

    // 패널 배경
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 180));
        
        int arc = 0; 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);   
        g2.dispose();
    }
}