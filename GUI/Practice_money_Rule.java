package GUI;

import javax.swing.Timer;
import java.awt.event.*;

public class Practice_money_Rule {
    static int stage;
    public Practice_money_Rule(String who) {
        if(who==null){
            return;
        }

        stage = Practice.count;
        
        if (stage < 1 || stage > 3) {
            return;
        }
        
        System.out.println("5.. 현재 버튼... " + who);
        System.out.println("5.. 현재 단계... " + stage);
        
        //베팅
        String result = Practice_Betting.betting(who, stage);
        System.out.println("5.. Practice_betting... result... "+result);
        if(who=="allin"){
            if (stage == 1) {
                Practice_Basic_up_card.community_card();
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Practice_Basic_upup_card.community_card();
                    }
                });
                timer.setRepeats(false);
                timer.start(); 
                GUI_00.g1_2_7.setVisible(true);

                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                Practice_card_back.flipToBack(Practice.card_com1, first, second);
                Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
            }else if (stage == 2) {
                Practice_Basic_upup_card.community_card();
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUI_00.g1_2_7.setVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start(); 
                GUI_00.g1_2_7.setVisible(true);

                //
                String data = String.valueOf(Practice.com_choose.get(0));
                String[] parts = data.split(",");

                String first = parts[0].trim();
                String second = parts[1].trim();

                String data2 = String.valueOf(Practice.com_choose.get(1));
                String[] parts2 = data2.split(",");

                String first2 = parts2[0].trim();
                String second2 = parts2[1].trim();

                Practice_card_back.flipToBack(Practice.card_com1, first, second);
                Practice_card_back.flipToBack(Practice.card_com2, first2, second2);
            }
            Practice.percent();

            return;
        }
        if(who=="fold"){
            GUI_00.g1_2_7.setVisible(true);
            return;
        }
        //
        if (stage == 1) {
            // FLOP 베팅 종료
            System.out.println("FLOP 베팅 종료");
            Practice.count = 2;

            // TURN 카드 공개
            Practice_Basic_up_card.community_card();
            Practice.percent();
        }else if (stage == 2) {
            // TURN 베팅 종료
            System.out.println("TURN 베팅 종료");
            Practice.count = 3;

            // RIVER 카드 공개
            Practice_Basic_upup_card.community_card();
            Practice.percent();
        }else if (stage == 3) {
            // RIVER 베팅 종료
            System.out.println("RIVER 베팅 종료");
            GUI_00.g1_2_7.setVisible(true);
        }
        System.out.println("현재 다음 단계 : " + Practice.count);
    }

    public static void endGame() {
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

        GUI_00.g1_2_4.setVisible(true);
    }
}
