package GUI;

import javax.swing.*;
import GUI_1.HandEvaluator;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
//import java.util.Random;
import GUI_1.HandProbability;

public class TexasHoldem extends JPanel {   
    // 실제 게임 칩 관리 객체
    static TexasHoldemMoney money;
    // 로그인 사용자 이름
    static JLabel userNameLabel;
    //족보
    static JLabel win_com;
    static JLabel win_user;
    //
    static JButton com_button1;
    static JButton com_button2;
    static JButton com_button3;
    static JButton com_button4;
    static JButton com_button5;
    static JButton button1;
    static JButton button2;
    static JButton button3;
    static JButton button4;
    static JButton button5;
    //
    static JLabel card_com1;
    static JLabel card_com2;
    static JLabel card_community1;
    static JLabel card_community2;
    static JLabel card_community3;
    static JLabel card_user1;
    static JLabel card_user2;
    //
    static JLabel card_community4;
    static JLabel card_community5;
    //
    static ArrayList<String> suit = new ArrayList<>();
    static  ArrayList<String> rank = new ArrayList<>();
    static ArrayList<String> choosed_card = new ArrayList<String>();
    static ArrayList<String> firstPlayerCards = new ArrayList<String>();
    static boolean userFirst = true;
    //
    static int count = 1;
    static ArrayList<String> com_choose;
    static ArrayList<String> communitycards;
    static ArrayList<String> usercards;
    static ArrayList<String> comcards;
    //
    static ProbabilityGraph probabilityGraph;
    static JButton startButton;
    static JPanel overlayPanel;
    static JLayeredPane layeredPane;
    //
    public static void startActualGame() {
        // 선공권 결정 패널 숨기기
        GUI_00.g4_firstPlayer.setVisible(false);

        // 실제 게임 카드 배분
        TexasHoldem_Basic_card.First_time_card_com();
        TexasHoldem_Basic_card.First_time_card_user();

        // 프리플랍 승률 계산
        ArrayList<String> userCardList = new ArrayList<>();
        ArrayList<String> comCardList = new ArrayList<>();

        userCardList.add(choosed_card.get(2));
        userCardList.add(choosed_card.get(3));

        comCardList.add(choosed_card.get(1));
        comCardList.add(choosed_card.get(0));

        Practice_Preflop_Percent probability =
            Practice_Preflop_Percent.calculate(userCardList, comCardList);

        System.out.println(probability.getResult());

        double userPercent = probability.getUserProbability();
        double comPercent = probability.getComProbability();
        double drawPercent = probability.getDrawProbability();

        System.out.println("사용자 승률: " + userPercent);
        System.out.println("컴퓨터 승률: " + comPercent);
        System.out.println("무승부 확률: " + drawPercent);

        win_com.setText("...");
        win_user.setText("...");

        probabilityGraph.setProbability(userPercent, comPercent, drawPercent);

        // 기존 프리플랍 진행 여부 패널 표시
        GUI_00.g4_donon.setVisible(true);
        GUI_00.g4_money.setVisible(false);
        GUI_00.g4_money_user.setVisible(true);
        GUI_00.g4_money_com.setVisible(true);

        setUserActionsEnabled(false);

        GUI_00.contentpane.setComponentZOrder(GUI_00.g4_donon, 0);
        GUI_00.contentpane.repaint();
    }
    //
    public static void returnToStart() {
        // 이전 게임 데이터 초기화
        choosed_card.clear();
        count = 1;

        usercards = null;
        comcards = null;
        communitycards = null;
        com_choose = null;

        // 이전 카드 화면 초기화
        card_com1.setIcon(null);
        card_com2.setIcon(null);

        card_user1.setIcon(null);
        card_user2.setIcon(null);

        card_community1.setIcon(null);
        card_community2.setIcon(null);
        card_community3.setIcon(null);
        card_community4.setIcon(null);
        card_community5.setIcon(null);

        // 족보 표시 초기화
        win_com.setText("");
        win_user.setText("");

        // GAME START 화면 표시
        if (startButton != null) startButton.setEnabled(true);
        if (overlayPanel != null) overlayPanel.setVisible(true);
        if (layeredPane != null) layeredPane.setVisible(true);
        if (probabilityGraph != null) probabilityGraph.setVisible(false);

        setUserActionsEnabled(false);
    }
    //
    public static void percent() {
                ArrayList<String> user_choose = new ArrayList<>();
                com_choose = new ArrayList<>();
                user_choose.add(choosed_card.get(2));
                user_choose.add(choosed_card.get(3));

                // 컴퓨터 카드 2장
                com_choose.add(choosed_card.get(0));
                com_choose.add(choosed_card.get(1));

                // 커뮤니티 카드 전부 추가
                for (int i = 4; i < choosed_card.size(); i++) {
                    user_choose.add(choosed_card.get(i));
                    com_choose.add(choosed_card.get(i));
                }
                System.out.println("0.. practice()... "+user_choose+"... "+com_choose);

                Map<String, Object> user_result = HandEvaluator.evaluate(user_choose);
                String user_name = (String) user_result.get("name");
                int user_rank = (int) user_result.get("rank");
                System.out.println("1.. 사용자 ... 첫 승률계산 ... 결과      : " + user_result);
                System.out.println("1.. 사용자 ... 첫 승률계산 ... 족보 이름 : " + user_name);
                System.out.println("1.. 사용자 ... 첫 승률계산 ... 족보 등급 : " + user_rank);              

                Map<String, Object> com_result = HandEvaluator.evaluate(com_choose);
                String com_name = (String)com_result.get("name");
                int com_rank = (int) com_result.get("rank");
                System.out.println("1.. 컴퓨터 ... 첫 승률계산 ... 결과      : " + com_result);
                System.out.println("1.. 컴퓨터 ... 첫 승률계산 ... 족보 이름 : " + com_name);
                System.out.println("1.. 컴퓨터 ... 첫 승률계산 ... 족보 등급 : " + com_rank);
                //
                win_com.setText(""+com_name);
                win_user.setText(""+user_name);
                //
                usercards = new ArrayList<>();
                comcards = new ArrayList<>();
                communitycards = new ArrayList<>(choosed_card);
                usercards.add(user_choose.get(1));usercards.add(user_choose.get(0));
                comcards.add(com_choose.get(1));comcards.add(com_choose.get(0));
                communitycards.remove(0);communitycards.remove(0);communitycards.remove(0);communitycards.remove(0);

                System.out.println("전체 카드: " + choosed_card);
                System.out.println("사용자 카드: " + usercards);
                System.out.println("컴퓨터 카드: " + comcards);
                System.out.println("커뮤니티 카드: " + communitycards);

                HandProbability probability = HandProbability.calculate(usercards, comcards, communitycards);
                System.out.println(probability.getResult());

                double userPercent = Double.parseDouble( String.valueOf(probability.getUserProbability()) );
                double comPercent = Double.parseDouble( String.valueOf(probability.getComProbability()));
                double drawPercent = Double.parseDouble(String.valueOf(probability.getDrawProbability()));
                
                System.out.println("사용자 승률 : " + userPercent);
                System.out.println("컴퓨터 승률 : " + comPercent);
                System.out.println("무승부 확률 : " + drawPercent);

                probabilityGraph.setProbability(userPercent, comPercent, drawPercent);
            }
    //
    public void button() {
        // TexasHoldem 전용 컨트롤러에 연결한다. Practice 로직은 호출하지 않는다.
        button1.addActionListener(e -> TexasHoldem_Betting.call());
        button2.addActionListener(e -> TexasHoldem_Betting.raise());
        button3.addActionListener(e -> TexasHoldem_Betting.fold());
        button4.addActionListener(e -> TexasHoldem_Betting.allIn());
        button5.addActionListener(e -> TexasHoldem_Betting.check());
    }
    //
    // 실제 게임 사용자 버튼 활성화 제어
    public static void setUserActionsEnabled(boolean enabled) {
        if (button1 != null) button1.setEnabled(enabled);
        if (button2 != null) button2.setEnabled(enabled);
        if (button3 != null) button3.setEnabled(enabled);
        if (button4 != null) button4.setEnabled(enabled);
        if (button5 != null) button5.setEnabled(enabled);
    }

    class ProbabilityGraph extends JPanel {
        double userCurrent = 0;
        double comCurrent = 0;
        double drawCurrent = 0;
        double userTarget = 0;
        double comTarget = 0;
        double drawTarget = 0;
        Timer timer;

        public ProbabilityGraph() {
            setOpaque(false);
            timer = new Timer(16, e -> {
                // 목표값에 조금씩 가까워지기
                userCurrent += (userTarget - userCurrent) * 0.15;
                comCurrent += (comTarget - comCurrent) * 0.15;
                drawCurrent += (drawTarget - drawCurrent) * 0.15;

                // 목표값과 거의 같아지면 종료
                if (
                        Math.abs(userTarget - userCurrent) < 0.1 &&
                        Math.abs(comTarget - comCurrent) < 0.1 &&
                        Math.abs(drawTarget - drawCurrent) < 0.1
                ) {
                    userCurrent = userTarget;
                    comCurrent = comTarget;
                    drawCurrent = drawTarget;
                    timer.stop();
                }
                repaint();
            });
        }

        public void setProbability(double user, double com, double draw) {
            userTarget = Math.max(0, Math.min(100, user));
            comTarget = Math.max(0, Math.min(100, com));
            drawTarget = Math.max(0, Math.min(100, draw));

            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(24, 35, 31, 220));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(),20, 20);
            g2.setFont(new Font("Serif", Font.BOLD, 20));
            g2.setColor(new Color(238, 215, 174));
            g2.drawString("WIN PROBABILITY",18, 32);

            drawBar(g2, "USER", userCurrent, new Color(80, 190, 130),65);
            drawBar(g2, "COM",comCurrent, new Color(220, 100, 90), 125);
            drawBar(g2, "DRAW", drawCurrent, new Color(220, 175, 80), 185);

            g2.dispose();
        }

        public void drawBar(Graphics2D g2, String name, double value, Color color, int y) {
            g2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
            g2.setColor(new Color(248, 239, 218));
            g2.drawString(name,18, y);
            g2.setFont(new Font("Serif", Font.BOLD, 17));
            g2.setColor(color);
            g2.drawString(String.format("%.1f%%", value),205, y);
            g2.setColor(new Color(90, 90, 80));
            g2.fillRoundRect(18, y + 10, 235, 13, 13, 13);
            
            int barWidth = (int) (235 * value / 100.0);
            
            g2.setColor(color);
            if (barWidth > 0) {
                g2.fillRoundRect(18, y + 10, barWidth, 13, 13, 13);
            }
        }
    }
    //
    public TexasHoldem() {
        System.out.println("Practice_Texas_Hold'em ------> Practice Texas Hold'em");
        setBounds(0, 0, 1072,598);
        setLayout(null);
        setOpaque(false);
        
        suit.clear();
        rank.clear();

        suit.add("Spades");
        suit.add("Hearts");
        suit.add("Diamonds");
        suit.add("Clubs");

        rank.add("A"); rank.add("2"); rank.add("3");
        rank.add("4"); rank.add("5"); rank.add("6"); rank.add("7"); rank.add("8"); rank.add("9"); rank.add("10");
        rank.add("J"); rank.add("Q"); rank.add("K");
        
        //
        //첫 배분 기능 불러오는거
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1072, 598);
        layeredPane.setLayout(null);
        layeredPane.setOpaque(false);
 
        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        overlayPanel.setBounds(0, 0, 1072, 598);
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(null);

        startButton = new JButton("GAME START");
        startButton.setBounds(411, 250, 250, 65);
        startButton.setFont(new Font("Serif", Font.BOLD, 25));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(190, 145, 65));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(true);

        overlayPanel.add(startButton);

        card_com1 = new JLabel("");
        card_com1.setBounds(470, 140, 65, 110);
        add(card_com1);

            card_com2 = new JLabel("");
            card_com2.setBounds(540, 140, 65, 110);
            add(card_com2);
            
            card_community1 = new JLabel("");
            card_community1.setBounds(460, 265, 65, 110);
            add(card_community1);

            card_community2 = new JLabel("");
            card_community2.setBounds(540, 265, 65, 110);
            add(card_community2);

            card_community3 = new JLabel("");
            card_community3.setBounds(620, 265, 65, 110);
            add(card_community3);

            card_community4 = new JLabel(" ");
            card_community4.setBounds(380, 265, 65, 110);
            add(card_community4);

            card_community5 = new JLabel(" ");
            card_community5.setBounds(300, 265, 65, 110);
            add(card_community5);

            card_user1 = new JLabel("image_card/4.0.png");        
            card_user1.setBounds(470, 390, 65, 110);
            add(card_user1);

            card_user2 = new JLabel("image_card/4.0.png");   
            card_user2.setBounds(540, 390, 65, 110);
            add(card_user2);
            //여기서 정의, 안에서 바꾸기
            win_com = new JLabel("");
            win_com.setFont(new Font("그리운 체리 한스푼", Font.PLAIN, 20));
            win_com.setForeground(new Color(136, 0, 21));
            win_com.setBounds(290, 190, 140, 25);
            win_com.setHorizontalAlignment(JLabel.CENTER);
            add(win_com);

            win_user = new JLabel("");
            win_user.setFont(new Font("그리운 체리 한스푼", Font.PLAIN, 20));
            win_user.setForeground(new Color(136, 0, 21));
            win_user.setBounds(290, 445, 140, 25);
            win_user.setHorizontalAlignment(JLabel.CENTER);
            add(win_user);
            //.... 아래에서 분배 기능
            JLabel label01 = new JLabel("Texas Hold'em Practice");
            label01.setFont(new Font("Serif", Font.BOLD, 30));
            label01.setForeground(new Color(238, 215, 174));
            label01.setBounds(386, 18, 400, 40);

            JLabel label02 = new JLabel("COM");
            label02.setBounds(330, 150, 50, 25);
            label02.setFont(new Font("Serif", Font.BOLD, 18));
            label02.setForeground(new Color(248, 239, 218));
            label02.setHorizontalAlignment(JLabel.CENTER);

            userNameLabel = new JLabel("USER");
            userNameLabel.setBounds(330, 410, 150, 25);
            userNameLabel.setFont(new Font("Serif", Font.BOLD, 18));
            userNameLabel.setForeground(new Color(248, 239, 218));
            userNameLabel.setHorizontalAlignment(JLabel.CENTER);
            //
            ImageIcon  image1 = new ImageIcon("image/홀덤사용2.png");
            Image scaledImage1 = image1.getImage().getScaledInstance( 150, 90, Image.SCALE_SMOOTH);
            JLabel user = new JLabel(new ImageIcon(scaledImage1));
            user.setBounds(280, 400, 150, 90);

            ImageIcon  com_image1 = new ImageIcon("image/홀덤사용2.png");
            Image com_scaledImage1 = com_image1.getImage().getScaledInstance( 150, 90, Image.SCALE_SMOOTH);
            JLabel com_user = new JLabel(new ImageIcon(com_scaledImage1));
            com_user.setBounds(280, 140, 150, 90);
            
            //
            com_button1 = new JButton("CALL");
            com_button1.setFont(new Font("Serif", Font.BOLD, 12));
            com_button1.setBounds(100, 140, 75, 35);
            com_button1.setBackground(new Color(95, 134, 158));
            com_button1.setForeground(Color.WHITE);
            com_button1.setFocusPainted(false);
            com_button1.setBorderPainted(false);
            com_button1.setOpaque(true);

            com_button2 = new JButton("RAISE");
            com_button2.setFont(new Font("Serif", Font.BOLD, 12));
            com_button2.setBounds(100, 190, 75, 35);
            com_button2.setBackground(new Color(95, 134, 158));
            com_button2.setForeground(Color.WHITE);
            com_button2.setFocusPainted(false);
            com_button2.setBorderPainted(false);
            com_button2.setOpaque(true);

            com_button3 = new JButton("FOLD");
            com_button3.setFont(new Font("Serif", Font.BOLD, 12));
            com_button3.setBounds(190, 140, 75, 35);
            com_button3.setBackground(new Color(95, 134, 158));
            com_button3.setForeground(Color.WHITE);
            com_button3.setFocusPainted(false);
            com_button3.setBorderPainted(false);
            com_button3.setOpaque(true);
            
            com_button4 = new JButton("ALLIN");
            com_button4.setFont(new Font("Serif", Font.BOLD, 12));
            com_button4.setBounds(190, 190, 75, 35);
            com_button4.setBackground(new Color(95, 134, 158));
            com_button4.setForeground(Color.WHITE);
            com_button4.setFocusPainted(false);
            com_button4.setBorderPainted(false);
            com_button4.setOpaque(true);
            
            com_button5 = new JButton("CHECK");
            com_button5.setFont(new Font("Serif", Font.BOLD, 11));
            com_button5.setBounds(100, 240, 75, 35);
            com_button5.setBackground(new Color(95, 134, 158));
            com_button5.setForeground(Color.WHITE);
            com_button5.setFocusPainted(false);
            com_button5.setBorderPainted(false);
            com_button5.setOpaque(true);
            //
            button1 = new JButton("CALL");
            button1.setFont(new Font("Serif", Font.BOLD, 12));
            button1.setBounds(100, 400, 75, 35);
            button1.setBackground(new Color(190, 145, 65));
            button1.setForeground(Color.WHITE);
            button1.setFocusPainted(false);
            button1.setBorderPainted(false);
            button1.setOpaque(true);

            button2 = new JButton("RAISE");
            button2.setFont(new Font("Serif", Font.BOLD, 12));
            button2.setBounds(100, 450, 75, 35);
            button2.setBackground(new Color(190, 145, 65));
            button2.setForeground(Color.WHITE);
            button2.setFocusPainted(false);
            button2.setBorderPainted(false);
            button2.setOpaque(true);

            button3 = new JButton("FOLD");
            button3.setFont(new Font("Serif", Font.BOLD, 12));
            button3.setBounds(190, 400, 75, 35);
            button3.setBackground(new Color(190, 145, 65));
            button3.setForeground(Color.WHITE);
            button3.setFocusPainted(false);
            button3.setBorderPainted(false);
            button3.setOpaque(true);

            button4 = new JButton("ALLIN");
            button4.setFont(new Font("Serif", Font.BOLD, 12));
            button4.setBounds(190, 450, 75, 35);
            button4.setBackground(new Color(190, 145, 65));
            button4.setForeground(Color.WHITE);
            button4.setFocusPainted(false);
            button4.setBorderPainted(false);
            button4.setOpaque(true);

            button5 = new JButton("CHECK");
            button5.setFont(new Font("Serif", Font.BOLD, 12));
            button5.setBounds(100, 350, 80, 35);
            button5.setBackground(new Color(190, 145, 65));
            button5.setForeground(Color.WHITE);
            button5.setFocusPainted(false);
            button5.setBorderPainted(false);
            button5.setOpaque(true);
        // 실제 게임 버튼 이벤트 연결
        button();
        setUserActionsEnabled(false);
        //
        probabilityGraph = new ProbabilityGraph();
        probabilityGraph.setBounds(705, 130, 270, 245);
        add(probabilityGraph);
        // 4. 시작 버튼 클릭 이벤트
        // 4. 시작 버튼 클릭 이벤트
        startButton.addActionListener(e -> {

            // 1. 로그인 확인
            if (!TextDatabase.isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "로그인이 필요합니다.", "로그인 안내", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // 2. 이전 게임 데이터 초기화
            choosed_card.clear();

            count = 1;

            usercards = null;
            comcards = null;
            communitycards = null;
            com_choose = null;

            card_com1.setIcon(null);
            card_com2.setIcon(null);

            card_user1.setIcon(null);
            card_user2.setIcon(null);

            card_community1.setIcon(null);
            card_community2.setIcon(null);
            card_community3.setIcon(null);
            card_community4.setIcon(null);
            card_community5.setIcon(null);

            win_com.setText("");
            win_user.setText("");

            // 3. 로그인 사용자 정보 불러오기
            userNameLabel.setText(TextDatabase.getUserName());

            // 4. 실제 게임 칩 관리 객체 생성
            money = new TexasHoldemMoney();
            TexasHoldem_Betting.reset();
            GUI_00.g4_money.resetAmounts();
            GUI_00.g4_money_user.updateMoney(money.getUserMoney());
            GUI_00.g4_money_com.updateMoney(money.getComMoney());


            System.out.println("현재 사용자: " + TextDatabase.getUserName());
            System.out.println("사용자 보유 칩: " + money.getUserMoney());

            // 5. 시작 화면 숨기기
            startButton.setEnabled(false);
            overlayPanel.setVisible(false);
            layeredPane.setVisible(false);

            probabilityGraph.setVisible(true);
            probabilityGraph.setProbability(0, 0, 0);
            
            // 선공권 카드 초기화
            firstPlayerCards.clear();
            userFirst = true;

            // 선공권 결정 패널 초기화 및 표시
            GUI_00.g4_firstPlayer.resetPanel();
            GUI_00.g4_firstPlayer.setVisible(true);

            GUI_00.contentpane.setComponentZOrder(GUI_00.g4_firstPlayer, 0);

            GUI_00.g4_money.setVisible(false);
            GUI_00.g4_money_user.setVisible(true);
            GUI_00.g4_money_com.setVisible(true);

            setUserActionsEnabled(false);

            GUI_00.contentpane.repaint();
        });

        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
        probabilityGraph.setVisible(false);
        revalidate();
        repaint();
        //여기에 다른거 다 추가하기
        add(userNameLabel);
        add(label02);
        add(label01);
        add(user);
        add(com_user);
        add(com_button1); 
        add(com_button2);
        add(com_button3);
        add(com_button4);
        add(com_button5);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        setUserActionsEnabled(false);
        //
        JLabel line1 = new JLabel();
        line1.setBounds(220, 260, 632, 1);
        line1.setOpaque(true);
        line1.setBackground(new Color(184, 145, 91));
        add(line1);
 
        JLabel line2 = new JLabel();
        line2.setBounds(220, 382, 632, 1);
        line2.setOpaque(true);
        line2.setBackground(new Color(184, 145, 91));
        add(line2);
        //
        
        ImageIcon icon1 = new ImageIcon("image/홀덤배경.png");
        Image image11 = icon1.getImage();
        Image scaledimage1 = image11.getScaledInstance(1072,598, Image.SCALE_SMOOTH);
        ImageIcon scaledicon1 = new ImageIcon(scaledimage1);
        JLabel label_image1 = new JLabel(scaledicon1);
        label_image1.setBounds(0,0,1072,598);
        add(label_image1);
    }
}
