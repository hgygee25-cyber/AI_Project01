package GUI;
import java.awt.*;
import javax.swing.*;
import login.GUI_01;
import java.awt.event.*;

public class GUI_00 extends JFrame {
    public static Container contentpane;
    public static first_frame g0;
    public static Practice_Texas_Hold g1;
    public static Rule g1_1;
    public static Practice g1_2;
    static Indian_poker g2;
    static OneCard g3;
    static Rule_1 g1_1_1;
    static Rule_2 g1_1_2;
    static Rule_3 g1_1_3;
    static Rule_4 g1_1_4;
    static Rule_5 g1_1_5;
    static Practice_donon g1_2_3;
    static Practice_End g1_2_4;
    static Practice_money g1_2_5;
    static Practice_money_User g1_2_6;
    static Practice_keepgoing g1_2_7;
    public static TexasHoldem g4;
    public static TexasHoldem_End g4_end;
    public static TexasHoldem_donon g4_donon;
    public static TexasHoldem_money g4_money;
    public static TexasHoldem_money_User g4_money_user;
    public static TexasHoldem_money_Com g4_money_com;
    public static first_frame_store g;
    public static first_frame_store2 gg;
    public static first_frame_store3 ggg;
    public static first_frame_chip gg1;
    public static first_frame_record gg2;
    public static first_frame_hand gg3;
    public static first_frame_period gg4;
    public static first_frame_settings gg5;
    //
    public GUI_00() {
        setUndecorated(true);
        setBounds(300, 200, 1072,598);
		setSize(1072,598);
		contentpane = getContentPane();
		contentpane.setLayout(null);
        
        gg = new first_frame_store2();
        g = new first_frame_store();
        g1_1_5 = new Rule_5();
        g1_1_4 = new Rule_4();
        g1_1_3 = new Rule_3();
        g1_1_2 = new Rule_2();
        g1_1_1 = new Rule_1();
        g1 = new Practice_Texas_Hold();
        g1_2 = new Practice();
        g1_2_3 = new Practice_donon();
        g1_2_4 = new Practice_End();
        g1_2_5 = new Practice_money();
        g1_2_6 = new Practice_money_User();
        g1_2_7 = new Practice_keepgoing();
        g1_1 = new Rule();
        g0 = new first_frame();
        g2 = new Indian_poker();
        g3 = new OneCard();
        g4 = new TexasHoldem();
        g4_end = new TexasHoldem_End();
        g4_donon = new TexasHoldem_donon();
        g4_money = new TexasHoldem_money();
        g4_money_user = new TexasHoldem_money_User();
        g4_money_com = new TexasHoldem_money_Com(); 
        gg1 = new first_frame_chip();
        ggg = new first_frame_store3();

        contentpane.add(ggg);
        contentpane.add(gg1);
        contentpane.add(gg);
        contentpane.add(g);
        contentpane.add(g4_donon);
        contentpane.add(g4_money);
        contentpane.add(g4_money_user);
        contentpane.add(g4_money_com);
        contentpane.add(g4);
        contentpane.add(g4_end);
        contentpane.add(g1_2_4);
        contentpane.add(g1_1_5);
        contentpane.add(g1_1_4);
        contentpane.add(g1_1_3);
        contentpane.add(g1_1_2);
        contentpane.add(g1_1_1);
        contentpane.add(g2);
        contentpane.add(g3);
        contentpane.add(g1);
        contentpane.add(g1_2_3);
        contentpane.add(g1_2_5);
        contentpane.add(g1_2_6);
        contentpane.add(g1_2_7);
        contentpane.add(g1_2);
        contentpane.add(g1_1);
        contentpane.add(g0);

        gg1.setVisible(false);
        ggg.setVisible(false);
        gg.setVisible(false);
        g.setVisible(false);
        g1_2_7.setVisible(false);
        g1_2_6.setVisible(false);
        g1_2_5.setVisible(false);
        g1_2_4.setVisible(false);
        g1_2_3.setVisible(false);
        g1_1_5.setVisible(false);
        g1_1_4.setVisible(false);
        g1_1_3.setVisible(false);
        g1_1_2.setVisible(false);
        g1_1_1.setVisible(false);
        g0.setVisible(false);
        g1.setVisible(false);
        g1_1.setVisible(false);
        g1_2.setVisible(false);
        g2.setVisible(false);
        g3.setVisible(false);
        g4.setVisible(false);
        g4_end.setVisible(false);
        g4_donon.setVisible(false);
        g4_money.setVisible(false);
        g4_money_user.setVisible(false);
        g4_money_com.setVisible(false);

        ImageIcon icon1 = new ImageIcon("image/배경01_1.png");
		Image image1 = icon1.getImage();
		Image scaledimage1 = image1.getScaledInstance(1072,598, Image.SCALE_SMOOTH);
		ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
		JLabel label_image1 = new JLabel(scaledicon1);
		label_image1.setBounds(0,0,1072,598);
		contentpane.add(label_image1);

        label_image1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                g0.setVisible(true);
            }
        });

    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            GUI_00 frame = new GUI_00();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
