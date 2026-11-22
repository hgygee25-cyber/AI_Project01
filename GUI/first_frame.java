package GUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class first_frame extends JPanel{
    public first_frame() {
		//타이틀바 삭제 
        setBounds(0, 0, 1072,598);
		setLayout(null);

        ImageIcon icon11 = new ImageIcon("image/b1.png");
        Image image11 = icon11.getImage();
        Image scaledimage11 = image11.getScaledInstance(150,50,Image.SCALE_SMOOTH);
        ImageIcon scaledicon11 = new ImageIcon(scaledimage11);
		JLabel label_image11 = new JLabel(scaledicon11);
		label_image11.setBounds(870, 70, 150,50);
        add(label_image11);

        ImageIcon icon12 = new ImageIcon("image/b2.png");
        Image image12 = icon12.getImage();
        Image scaledimage12 = image12.getScaledInstance(150,50,Image.SCALE_SMOOTH);
        ImageIcon scaledicon12 = new ImageIcon(scaledimage12);
		JLabel label_image12 = new JLabel(scaledicon12);
		label_image12.setBounds(870, 150, 150,50);
        add(label_image12);

        ImageIcon icon13 = new ImageIcon("image/b3.png");
        Image image13 = icon13.getImage();
        Image scaledimage13 = image13.getScaledInstance(230,50,Image.SCALE_SMOOTH);
        ImageIcon scaledicon13 = new ImageIcon(scaledimage13);
		JLabel label_image13 = new JLabel(scaledicon13);
		label_image13.setBounds(820, 230, 230, 50);
        add(label_image13);
        
        ImageIcon icon14 = new ImageIcon("image/g1.png");
        Image image14 = icon14.getImage();
        Image scaledimage14 = image14.getScaledInstance(200,300,Image.SCALE_SMOOTH);
        ImageIcon scaledicon14 = new ImageIcon(scaledimage14);
		JLabel label_image14 = new JLabel(scaledicon14);
		label_image14.setBounds(50, 150, 200,300);
        add(label_image14);

        ImageIcon icon15 = new ImageIcon("image/g2.png");
        Image image15 = icon15.getImage();
        Image scaledimage15 = image15.getScaledInstance(200,300,Image.SCALE_SMOOTH);
        ImageIcon scaledicon15 = new ImageIcon(scaledimage15);
		JLabel label_image15 = new JLabel(scaledicon15);
		label_image15.setBounds(320, 150, 200,300);
        add(label_image15);

        ImageIcon icon16 = new ImageIcon("image/g3.png");
        Image image16 = icon16.getImage();
        Image scaledimage16 = image16.getScaledInstance(200,300,Image.SCALE_SMOOTH);
        ImageIcon scaledicon16 = new ImageIcon(scaledimage16);
		JLabel label_image16 = new JLabel(scaledicon16);
		label_image16.setBounds(600, 150, 200,300);
        add(label_image16);

        ImageIcon icon1 = new ImageIcon("image/배경01_1.png");
		Image image1 = icon1.getImage();
		Image scaledimage1 = image1.getScaledInstance(1072,598, Image.SCALE_SMOOTH);
		ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
		JLabel label_image1 = new JLabel(scaledicon1);
		label_image1.setBounds(0,0,1072,598);
		add(label_image1);

        System.out.println("first_frame 생성: " + this);
        //동작
        label_image13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                //텍사스 홀덤 연습창으로 이동
                GUI_00.g.setVisible(false);
                GUI_00.g1.setVisible(true);
                GUI_00.g0.setVisible(false);
            }
        });

        label_image12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

                SwingUtilities.invokeLater(() -> {
                    new login.GUI_01();
                });
            }
        });

         label_image11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        label_image14.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // 인디언 포커 창으로 넘어가기
                if (first_frame_store.label00.getText() != "") {

                    GUI_00.g0.setVisible(false);
                    GUI_00.g2.setVisible(true);
                    GUI_00.g.setVisible(false);

                    // 실제 게임 시작
                    GUI_00.g2.startGame();

                } else {

                    int result = JOptionPane.showConfirmDialog(
                        first_frame.this,
                        "로그인은 필수입니다.\n로그인 화면으로 넘어갑니다.",
                        "로그인 안내",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        new login.GUI_01();
                    }
                }
            }
        });

        label_image15.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //원카드 창으로 넘어가기
                if(first_frame_store.label00.getText() != ""){

                    // 원카드 게임 초기화
                    GUI_00.g3.startNewGame();

                    GUI_00.g3.setVisible(true);
                    GUI_00.g0.setVisible(false);
                    GUI_00.g.setVisible(false);

                } else {
                    int result = JOptionPane.showConfirmDialog(
                        first_frame.this,
                        "로그인은 필수입니다.\n로그인 화면으로 넘어갑니다.",
                        "로그인 안내",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        new login.GUI_01();
                    }
                }
            }
        });

        label_image16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                //텍사스 홀덤 창으로 넘어가기
                if(first_frame_store.label00.getText() != ""){    
                    GUI_00.g4.setVisible(true);
                    GUI_00.g0.setVisible(false);
                    GUI_00.g.setVisible(false);
                }else{
                    int result = JOptionPane.showConfirmDialog(first_frame.this, "로그인은 필수입니다.\n로그인 화면으로 넘어갑니다.", "로그인 안내", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        new login.GUI_01();
                    }
                }
            }
        });
        setVisible(true);
    }
}
