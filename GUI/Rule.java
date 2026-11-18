package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Rule extends JPanel {
    public Rule() {
        System.out.println("Practice_Texas_Hold'em ------> Rule");

        setBounds(0, 0, 1072,598);
		setLayout(null);

        JLabel label01 = new JLabel("게임 기본 규칙");
        label01.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label01.setForeground(Color.black);
        label01.setBounds(460, 150, 200,50);
        add(label01);

        JLabel label02 = new JLabel("게임 진행 순서");
        label02.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label02.setForeground(Color.black);
        label02.setBounds(460, 225, 300,50);
        add(label02);

        JLabel label03 = new JLabel("카드 족보");
        label03.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label03.setForeground(Color.black);
        label03.setBounds(500, 300, 200,50);
        add(label03);

        JLabel label04 = new JLabel("베팅 방법");
        label04.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label04.setForeground(Color.black);
        label04.setBounds(495, 375, 300,50);
        add(label04);

        JLabel label05 = new JLabel("승패 판정");
        label05.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label05.setForeground(Color.black);
        label05.setBounds(500, 450, 200,50);
        add(label05);

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(Color.white);
        label06.setBounds(520, 540, 200,50);
        add(label06);

        JLabel label07 = new JLabel("GAME RULE");
        label07.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        label07.setForeground(Color.white);
        label07.setBounds(425, 20, 300, 50);
        add(label07);

        JLabel label08 = new JLabel("Texas Hold'em Guide");
        label08.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label08.setForeground(Color.white);
        label08.setBounds(470, 110, 300, 15);
        add(label08);

        ImageIcon icon00 = new ImageIcon("image/rule_01.png");
		Image image00 = icon00.getImage();
		Image scaledimage00 = image00.getScaledInstance(1040, 60, Image.SCALE_SMOOTH);
		ImageIcon scaledicon00 = new ImageIcon(scaledimage00);
		JLabel label_image00 = new JLabel(scaledicon00);
		label_image00.setBounds(10, 150, 1040,60);
		add(label_image00);

        ImageIcon icon01 = new ImageIcon("image/rule_01.png");
		Image image01 = icon01.getImage();
		Image scaledimage01 = image01.getScaledInstance(1040, 60, Image.SCALE_SMOOTH);
		ImageIcon scaledicon01 = new ImageIcon(scaledimage01);
		JLabel label_image01 = new JLabel(scaledicon01);
		label_image01.setBounds(10, 225, 1040, 60);
		add(label_image01);

        ImageIcon icon02 = new ImageIcon("image/rule_01.png");
		Image image02 = icon02.getImage();
		Image scaledimage02 = image02.getScaledInstance(1040, 60, Image.SCALE_SMOOTH);
		ImageIcon scaledicon02 = new ImageIcon(scaledimage02);
		JLabel label_image02 = new JLabel(scaledicon02);
		label_image02.setBounds(10, 300, 1040,60);
		add(label_image02);

        ImageIcon icon03 = new ImageIcon("image/rule_01.png");
		Image image03 = icon03.getImage();
		Image scaledimage03 = image03.getScaledInstance(1040, 60, Image.SCALE_SMOOTH);
		ImageIcon scaledicon03 = new ImageIcon(scaledimage03);
		JLabel label_image03 = new JLabel(scaledicon03);
		label_image03.setBounds(10,375,1040,60);
		add(label_image03);

        ImageIcon icon04 = new ImageIcon("image/rule_01.png");
		Image image04 = icon04.getImage();
		Image scaledimage04 = image04.getScaledInstance(1040,60, Image.SCALE_SMOOTH);
		ImageIcon scaledicon04 = new ImageIcon(scaledimage04);
		JLabel label_image04 = new JLabel(scaledicon04);
		label_image04.setBounds(10,450,1040,60);
		add(label_image04);

        ImageIcon icon1 = new ImageIcon("image/rule00.png");
		Image image1 = icon1.getImage();
		Image scaledimage1 = image1.getScaledInstance(1072,620, Image.SCALE_SMOOTH);
		ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
		JLabel label_image1 = new JLabel(scaledicon1);
		label_image1.setBounds(0,0,1072,620);
		add(label_image1);

        label06.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1.setVisible(false);
                GUI_00.g1.setVisible(true);
            }
        });

        label_image00.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_1.setVisible(true);
                GUI_00.g1_1.setVisible(false);
            }
        });

        label_image01.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_2.setVisible(true);
                GUI_00.g1_1.setVisible(false);
            }
        });
        
        label_image02.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_3.setVisible(true);
                GUI_00.g1_1.setVisible(false);
            }
        });

        label_image03.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_4.setVisible(true);
                GUI_00.g1_1.setVisible(false);
            }
        });

        label_image04.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                GUI_00.g1_1_5.setVisible(true);
                GUI_00.g1_1.setVisible(false);
            }
        });
    }
}
