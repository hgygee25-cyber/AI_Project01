package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Practice_Texas_Hold  extends JPanel{
    public Practice_Texas_Hold() {
        System.out.println("Practice_Texas_Hold'em");

        setBounds(0, 0, 1072,598);
		setLayout(null);

		JLabel label01 = new JLabel("Rule");
        label01.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label01.setForeground(Color.white);
        label01.setBounds(320, 260, 200,50);
        add(label01);

        JLabel label02 = new JLabel("Practice Texas Hold'em");
        label02.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label02.setForeground(Color.white);
        label02.setBounds(650, 260, 300,50);
        add(label02);

		ImageIcon icon10 = new ImageIcon("image/버튼배경.png");
        Image image10 = icon10.getImage();
        Image scaledimage10 = image10.getScaledInstance(300,300,Image.SCALE_SMOOTH);
        ImageIcon scaledicon10 = new ImageIcon(scaledimage10);
		JLabel label_image10 = new JLabel(scaledicon10);
		label_image10.setBounds(200, 150, 300,300);
        add(label_image10);

        ImageIcon icon11 = new ImageIcon("image/버튼배경.png");
        Image image11 = icon11.getImage();
        Image scaledimage11 = image11.getScaledInstance(300,300,Image.SCALE_SMOOTH);
        ImageIcon scaledicon11 = new ImageIcon(scaledimage11);
		JLabel label_image11 = new JLabel(scaledicon11);
		label_image11.setBounds(600, 150, 300,300);
        add(label_image11);

		JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(Color.white);
        label06.setBounds(520, 540, 200,50);
        add(label06);

        ImageIcon icon1 = new ImageIcon("image/배경01_1.png");
		Image image1 = icon1.getImage();
		Image scaledimage1 = image1.getScaledInstance(1060,620, Image.SCALE_SMOOTH);
		ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
		JLabel label_image1 = new JLabel(scaledicon1);
		label_image1.setBounds(0,0,1060,620);
		add(label_image1);

		//동작
		label_image10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//규칙 알려주기
                GUI_00.g1_1.setVisible(true);
				GUI_00.g1.setVisible(false);
            }
        });

		label_image11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//연습하기
                GUI_00.g1.setVisible(false);
                GUI_00.g1_2.setVisible(true);
            }
        });

		label06.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1.setVisible(false);
                GUI_00.g0.setVisible(true);
            }
        });
    }
}
