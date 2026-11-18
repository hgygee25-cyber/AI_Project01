
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class first_frame_store2 extends JPanel {
    public static JLabel label1;
    public static JLabel label2;
    public static JLabel label3;
    JLabel label4;
    JLabel label5;
    JLabel label6;
    JLabel label7;
    static JLabel label0;
    static int rank;
    //
    public static void username() {
        int m = TextDatabase.getUserChips();
        String name = TextDatabase.getUserName();
        label2.setText(name + "님");
        label3.setText(String.format("%,d CHIP", m));

        userranking(name);
    }
    //
    public static void userranking(String name) {
        String path = "data/user_chips.txt";
        ArrayList<String> users = new ArrayList<>();
        ArrayList<Integer> chips = new ArrayList<>();
        rank = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t", -1);
                if (data.length >= 2) {
                    users.add(data[0]);
                    chips.add(Integer.parseInt(data[1]));
                }
            }

            int userIndex = users.indexOf(name);
            if (userIndex == -1) {
                return;
            }

            int userChips = chips.get(userIndex);
            rank = 1;

            for (int i = 0; i < chips.size(); i++) {
                if (chips.get(i) > userChips) {
                    rank++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            rank = 0;
        }

        label0.setText(rank+" /"+users.size());
    }
    //
    public first_frame_store2() {
        setBounds(0, 0, 1072, 598);
        setBackground(Color.black);
        setLayout(null);

        //
		label1 = new JLabel("MY ACCOUNT");
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label1.setForeground(new Color(235, 190, 90));
        label1.setBounds(50, 30, 250, 30);
        add(label1);

        label2 = new JLabel("님");
        label2.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        label2.setForeground(Color.WHITE);
        label2.setBounds(130, 120, 250, 23);
        add(label2);

        JLabel label00 = new JLabel("RANKING OF ALL");
        label00.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label00.setForeground(Color.WHITE);
        label00.setBounds(410, 120, 200, 15);
        add(label00);

        label0 = new JLabel(""+rank);
        label0.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        label0.setForeground(Color.WHITE);
        label0.setBounds(410, 145, 200, 23);
        add(label0);

        label3 = new JLabel("CHIP");
        label3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label3.setForeground(new Color(235, 190, 90));
        label3.setBounds(130, 157, 250, 15);
        add(label3);

        JLabel label8 = new JLabel("CHIP SHOP");
        label8.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label8.setForeground(new Color(233, 210, 166));
        label8.setBounds(110, 295, 250, 15);
        add(label8);

        JLabel label88 = new JLabel("칩 관리");
        label88.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        label88.setForeground(new Color(233, 210, 166));
        label88.setBounds(110, 320, 250, 14);
        add(label88);

        JLabel label9 = new JLabel("GAME RECORD");
        label9.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label9.setForeground(new Color(233, 210, 166));
        label9.setBounds(560, 295, 250, 15);
        add(label9);

        JLabel label99 = new JLabel("누적 게임 기록");
        label99.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        label99.setForeground(new Color(233, 210, 166));
        label99.setBounds(560, 320, 250, 14);
        add(label99);

        JLabel label10 = new JLabel("HAND ANALYSIS");
        label10.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label10.setForeground(new Color(233, 210, 166));
        label10.setBounds(110, 470, 250, 15);
        add(label10);

        JLabel label1010 = new JLabel("핸드별 통계");
        label1010.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        label1010.setForeground(new Color(233, 210, 166));
        label1010.setBounds(110, 495, 250, 15);
        add(label1010);
        
        JLabel label111 = new JLabel("기간별 기록");
        label111.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        label111.setForeground(new Color(233, 210, 166));
        label111.setBounds(560, 495, 250, 15);
        add(label111);

        JLabel label11 = new JLabel("PERIOD RECORD");
        label11.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label11.setForeground(new Color(233, 210, 166));
        label11.setBounds(560, 470, 250, 15);
        add(label11);

        //
        ImageIcon background = new ImageIcon("image/rank.png");
        Image image = background.getImage();
        Image scaledimage = image.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(scaledimage);
        JLabel label000 = new JLabel(scaledicon);
        label000.setBounds(330, 120, 55, 55);
        add(label000);

        ImageIcon background11 = new ImageIcon("image/user3.png");
        Image image11 = background11.getImage();
        Image scaledimage11 = image11.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledicon11 = new ImageIcon(scaledimage11);
        JLabel label44 = new JLabel(scaledicon11);
        label44.setBounds(38, 105, 80, 80);
        add(label44);

        ImageIcon background22 = new ImageIcon("image/user1.png");
        Image image22 = background22.getImage();
        Image scaledimage22 = image22.getScaledInstance(420, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledicon22 = new ImageIcon(scaledimage22);
        label4 = new JLabel(scaledicon22);
        label4.setBounds(90, 205, 420, 150);
        add(label4);

        ImageIcon background33 = new ImageIcon("image/user1.png");
        Image image33 = background33.getImage();
        Image scaledimage33 = image33.getScaledInstance(420, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledicon33 = new ImageIcon(scaledimage33);
        label5 = new JLabel(scaledicon33);
        label5.setBounds(540, 205, 420, 150);
        add(label5);

        ImageIcon background44 = new ImageIcon("image/user1.png");
        Image image44 = background44.getImage();
        Image scaledimage44 = image44.getScaledInstance(420, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledicon44 = new ImageIcon(scaledimage44);
        label6 = new JLabel(scaledicon44);
        label6.setBounds(90, 380, 420, 150);
        add(label6);
        
        ImageIcon background55 = new ImageIcon("image/user1.png");
        Image image55 = background55.getImage();
        Image scaledimage55 = image55.getScaledInstance(420, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledicon55 = new ImageIcon(scaledimage55); 
        label7 = new JLabel(scaledicon55);
        label7.setBounds(540, 380, 420, 150);
        add(label7);

        JLabel line1 = new JLabel();
        line1.setBounds(25, 100, 1022, 1);
        line1.setOpaque(true);
        line1.setBackground(new Color(184, 145, 91));
        add(line1);

        JLabel line2 = new JLabel();
        line2.setBounds(320, 115, 1, 60);
        line2.setOpaque(true);
        line2.setBackground(new Color(184, 145, 91));
        add(line2);

        //
        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.gg.setVisible(false);
                GUI_00.g0.setVisible(true);
                GUI_00.g.setVisible(true);
            }
        });
        //

        label4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("CHIP SHOP 클릭");
            }
        });

        label5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("GAME RECORD 클릭");
                GUI_00.gg.setVisible(false);
                GUI_00.gg2.setVisible(true);
                GUI_00.contentpane.setComponentZOrder(GUI_00.gg2, 0);
                GUI_00.contentpane.revalidate();
                GUI_00.contentpane.repaint();
            }
        });

        label6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.gg.setVisible(false);
                GUI_00.gg3.setVisible(true);
                GUI_00.contentpane.setComponentZOrder(GUI_00.gg3, 0);
                GUI_00.contentpane.revalidate();
                GUI_00.contentpane.repaint();
            }
        });

        label7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.gg.setVisible(false);
                GUI_00.gg4.setVisible(true);
                GUI_00.contentpane.setComponentZOrder(GUI_00.gg4, 0);
                GUI_00.contentpane.revalidate();
                GUI_00.contentpane.repaint();
            }
        });
    }
}
