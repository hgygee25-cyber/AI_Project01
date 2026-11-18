package GUI;

import javax.swing.*;
import java.awt.event.*;

public class Practice_Betting {
    //
    static int combetting = 0;
    static int userbetting = 0;
    static int usermoney = 50000 - Practice_money_User.update();
    static int commoney = 49000;
    static int comtotal = 0;
    static int usertotal = 0;
    //
    static boolean gameEnd = false;
    //
    public static String betting(String who, int stage){
        System.out.println("betting 들어옴!");
        if(stage<1||stage>3){
            return null;
        }
        if(who==null){
            return null;
        }
        combetting = 0;
        userbetting = 0;
        if(who.equals("call")){
            //computer 선베팅
            //combetting = computer전략클래스에서 가져와야함. 임의로 1000 지정
            combetting = 1000;
            commoney -= combetting;
            comtotal += combetting;
            //user 후베팅
            userbetting = combetting;
            usermoney -= userbetting;
            usertotal += userbetting;
            
            //단계별 금액 세팅해주기
            if(stage==1){
                Practice_money.flopmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comflopmoney.setText(String.valueOf(combetting));
            }else if(stage==2){
                Practice_money.turnmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comturnmoney.setText(String.valueOf(combetting));
            }else if(stage==3){
                Practice_money.rivermoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comrivermoney.setText(String.valueOf(combetting));
                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                //
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Practice_card_back.flipToBack(Practice.card_com1, first, second);
                        Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                //
                Timer timer2 = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }

            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();
        }else if(who.equals("check")){
            //computer 선베팅
            //combetting = computer전략클래스에서 가져와야함. 임의로 1000 지정
            combetting = 0;
            commoney -= combetting;
            comtotal += combetting;
            //user 후베팅
            userbetting = combetting;
            usermoney -= userbetting;
            usertotal += userbetting;
            
            //단계별 금액 세팅해주기
            if(stage==1){
                Practice_money.flopmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comflopmoney.setText(String.valueOf(combetting));
            }else if(stage==2){
                Practice_money.turnmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comturnmoney.setText(String.valueOf(combetting));
            }else if(stage==3){
                Practice_money.rivermoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comrivermoney.setText(String.valueOf(combetting));
                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                //
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Practice_card_back.flipToBack(Practice.card_com1, first, second);
                        Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                //
                Timer timer2 = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }

            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();
        }else if(who.equals("raise")){
            //computer 선베팅
            //combetting = computer전략클래스에서 가져와야함. 임의로 1000 지정
            combetting = 1000;
            commoney -= combetting;
            comtotal += combetting;
            
            //user 후베팅
            //userbetting = 패널넣어서 금액 얼마할건지 정하게 해야함!!!!!!!!!!!!!!!! 임의로 2000 지정
            int inputMoney = 0;
            while (true) {
                String input = JOptionPane.showInputDialog(null, 
                    "레이즈할 금액을 입력하세요.\n(최소 베팅액: " + combetting + " / 현재 보유액: " + usermoney + ")", 
                    "RAISE 금액 입력", JOptionPane.QUESTION_MESSAGE);

                // 취소 버튼이나 X 버튼을 누른 경우 처리
                if (input == null) {
                    return "CANCEL"; // 취소 시 베팅 진행 안 함
                }

                try {
                    inputMoney = Integer.parseInt(input.trim());
                    if (inputMoney <= combetting) {
                        JOptionPane.showMessageDialog(null, "컴퓨터 베팅액(" + combetting + ")보다 큰 금액을 입력해야 합니다.");
                    } else if (inputMoney > usermoney) {
                        JOptionPane.showMessageDialog(null, "보유한 금액(" + usermoney + ")보다 많이 베팅할 수 없습니다.");
                    } else {
                        break; // 정상 입력 시 반복문 탈출
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "숫자만 정확히 입력해주세요.");
                }
            }

            userbetting = inputMoney;
            usermoney -= userbetting;
            usertotal += userbetting;
            
            //단계별 금액 세팅해주기
            if(stage==1){
                Practice_money.flopmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comflopmoney.setText(String.valueOf(combetting));
            }else if(stage==2){
                Practice_money.turnmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comturnmoney.setText(String.valueOf(combetting));
            }else if(stage==3){
                Practice_money.rivermoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comrivermoney.setText(String.valueOf(combetting));
                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Practice_card_back.flipToBack(Practice.card_com1, first, second);
                        Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                //
                Timer timer2 = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }

            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();
        }else if(who.equals("fold")){
            //computer 선베팅
            //combetting = computer전략클래스에서 가져와야함. 임의로 1000 지정
            combetting = 1000;
            commoney -= combetting;
            comtotal += combetting;
            
            //user 후베팅 == 0
            gameEnd = true;
            
            //단계별 금액 세팅해주기
            if(stage==1){
                Practice_money.flopmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comflopmoney.setText(String.valueOf(combetting));
            }else if(stage==2){
                Practice_money.turnmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comturnmoney.setText(String.valueOf(combetting));
            }else if(stage==3){
                Practice_money.rivermoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comrivermoney.setText(String.valueOf(combetting));
                //
                Timer timer2 = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }

            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();
            
            return "USER FOLD";
        }else if(who.equals("allin")){
            //computer 선베팅
            //combetting = computer전략클래스에서 가져와야함. 임의로 1000 지정
            combetting = 1000;
            commoney -= combetting;
            comtotal += combetting;
            
            //user 후베팅 == 0
            userbetting = usermoney;
            usermoney = 0;
            usertotal += userbetting;
            gameEnd = true;

            //단계별 금액 세팅해주기
            if(stage==1){
                Practice_money.flopmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comflopmoney.setText(String.valueOf(combetting));
            }else if(stage==2){
                Practice_money.turnmoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comturnmoney.setText(String.valueOf(combetting));
            }else if(stage==3){
                Practice_money.rivermoney.setText(String.valueOf(userbetting));
                Practice.count++;
                Practice_money.totalcom.setText(String.valueOf(comtotal));
                Practice_money.comrivermoney.setText(String.valueOf(combetting));
                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Practice_card_back.flipToBack(Practice.card_com1, first, second);
                        Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                //
                Timer timer2 = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }
            Practice.button1.setEnabled(false);
            Practice.button2.setEnabled(false);
            Practice.button3.setEnabled(false);
            Practice.button4.setEnabled(false);
            Practice.button5.setEnabled(false);

            Practice.com_button1.setEnabled(false);
            Practice.com_button2.setEnabled(false);
            Practice.com_button3.setEnabled(false);
            Practice.com_button4.setEnabled(false);
            Practice.com_button5.setEnabled(false);
            //
            Practice_money.updateTotalUser();
            Practice_money_User.update_usermoney();
            
            return "ALL-IN";
        }else {
            return "존재하지 않는 버튼입니다.";
        }
        return "COM : " + combetting + " / USER : " + userbetting;
    }
}
