package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class first_frame_store extends JPanel{
    //
    static JLabel label00;
    static JLabel label01;
    //
    public static void username(String name) {
        int m = TextDatabase.getUserChips();
        label00.setText(name + "님");
        label01.setText(String.format("%,d CHIP", m));
    }

    public first_frame_store() {
        setBounds(10, 10, 280,80);
        setLayout(null);
        setOpaque(false);

        label00 = new JLabel("");
        label00.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label00.setForeground(Color.white);
        label00.setBounds(100, 15, 180, 15);
        add(label00);

        label01 = new JLabel("");
        label01.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        label01.setForeground(new Color(235, 195, 110));
        label01.setBounds(115, 42, 200, 12);
        add(label01);

        ImageIcon background = new ImageIcon("image/user (2).png");
        Image image15 = background.getImage();
        Image scaledimage15 = image15.getScaledInstance(280,80,Image.SCALE_SMOOTH);
        ImageIcon scaledicon15 = new ImageIcon(scaledimage15);
		JLabel label_image15 = new JLabel(scaledicon15);
		label_image15.setBounds(0, 0,280,80);
        add(label_image15);

        // 클릭 이벤트
        label_image15.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("사용자 정보 클릭");
                first_frame_store2.username();
                GUI_00.g.setVisible(false);
                GUI_00.g0.setVisible(false);
                GUI_00.gg.setVisible(true);
                
            }
        });
    }
}