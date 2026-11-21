package login;
import javax.swing.*;
import GUI.first_frame_record;
import GUI.first_frame_settings;
import GUI.first_frame_settings_1;
import GUI.first_frame_store2;
import GUI.first_frame_settings_1;
import GUI.GUI_00;
import GUI.TexasHoldem;
import GUI.TextDatabase;
import GUI.first_frame_hand;
import GUI.first_frame_period;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class GUI_01 extends JFrame{
	public static Runnable loginSuccess;
	public static String result = null;
	public static String loginId = null;
	static String[] array2;
	public GUI_01() {
		readtxt();
        setUndecorated(true);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(550, 400, 500, 300);
		setSize(500,300);
		
		Container contentpane = getContentPane();
		contentpane.setLayout(null);
		
		//기본틀
		String a = "ID";
		JLabel label1 = new JLabel(a);
		label1.setFont(new Font("맑은고딕", Font.PLAIN, 30));
		label1.setBounds(50, 50, 50, 30);
		contentpane.add(label1);
		
		String b = "password";
		JLabel label2 = new JLabel(b);
		label2.setFont(new Font("맑은고딕", Font.PLAIN, 30));
		label2.setBounds(50, 105, 150, 30);
		contentpane.add(label2);
		
		JTextField textfield1 = new JTextField();
		textfield1.setBounds(200, 50, 150, 40);
		contentpane.add(textfield1);
		
		JPasswordField textfield2 = new JPasswordField();
		textfield2.setBounds(200, 105, 150, 40);
		contentpane.add(textfield2);
		
		JButton button = new JButton("로그인");
		button.setBounds(390, 75, 70, 50);
		button.setBackground(Color.white);
		contentpane.add(button);
	  // 라벨로 변경하기 
		JLabel button2 = new JLabel("회원가입");
		button2.setFont(new Font("맑은고딕", Font.PLAIN, 10));
		button2.setBounds(240, 230, 75, 30);
		contentpane.add(button2);
		
		JLabel button3 = new JLabel("ID찾기");
		button3.setFont(new Font("맑은고딕", Font.PLAIN, 10));
		button3.setBounds(320, 230, 75, 30);
		contentpane.add(button3);
		
		JLabel button4 = new JLabel("PW변경");
		button4.setFont(new Font("맑은고딕", Font.PLAIN, 10));
		button4.setBounds(400, 230, 75, 30);
		contentpane.add(button4);
	
		//회원가입 버튼 눌렀다면
		button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
		    	new GUI_02();
            }
        });
		// ID찾기 버튼 눌렀다면
		button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
		    	new GUI_03();
            }
        });
		// pw찾기 버튼 눌렀다면
		button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
		    	new GUI_03_2();
            }
        });
	
		
		//로그인 버튼 눌렀다면
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메모장에 있는지 확인&비교하기
				String filePath = "data/login.txt";
            	int a = 0;
            	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            		int count = 0;
            		while(count < GUI_04.array.size()) {
            			Object A = GUI_04.array.get(count);
                        GUI_10 account = GUI_10.parse((String) A);
                        count++;
                        if (account != null && account.id.equals(textfield1.getText())
                                && GUI_11.verify(textfield2.getPassword(), account.passwordData)) {
                            a = 1;
                            result = account.userName;
                            loginId = account.id;
                            break;
            			}
            		}
            	} catch (FileNotFoundException e1) {
            	    System.out.println("파일을 찾을 수 없습니다: " + filePath);
            	} catch (IOException e1) {
            	    e1.printStackTrace();
            	}
            	
              	if(a==1) {
              		JOptionPane.showMessageDialog(null, result+"님, "+"로그인이 완료되었습니다!");
              		dispose();
					GUI.first_frame_store.username(result);
					GUI_00.g.setVisible(true);
					//
					System.out.println("로그인 사용자 이름: " + result);
					GUI_00.gg2 = new first_frame_record(result);
					GUI_00.contentpane.add(GUI_00.gg2);
					GUI_00.gg2.setVisible(false);
					String profilePath = first_frame_settings_1.loadImage(result);
					
					GUI_00.gg3 = new first_frame_hand(result);
					GUI_00.contentpane.add(GUI_00.gg3);
					GUI_00.gg3.setVisible(false);
				
					GUI_00.gg4 = new first_frame_period(result);
					GUI_00.contentpane.add(GUI_00.gg4);
					GUI_00.gg4.setVisible(false);
				
					GUI_00.gg5 = new first_frame_settings(result);
					GUI_00.contentpane.add(GUI_00.gg5);
					GUI_00.gg5.setVisible(false);
				
					ImageIcon profileIcon = new ImageIcon(profilePath);
					Image scaled = profileIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
					first_frame_store2.label44.setIcon(new ImageIcon(scaled));

					
					if (loginSuccess != null) {
              			loginSuccess.run();
              		}
              	}else  {
              		JOptionPane.showMessageDialog(null, "로그인에 실패하셨습니다.");
              	}
			}
		});
		setVisible(true);
	}
	
	public static void readtxt() {
	    GUI_04.array.clear();
	    String filepath = "data/login.txt";
	    try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
       		String line;		
			while ((line = br.readLine()) != null) {
            	if (!line.isBlank()) {
                	GUI_04.array.add(line);
            	}
        	}
        System.out.println("회원 정보 불러오기 완료");
        System.out.println("등록된 회원 수: " + GUI_04.array.size());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
	public static void main(String[] args) {
		//readtxt();
		new GUI_01();
	}
}

