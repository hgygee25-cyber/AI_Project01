package GUI;

import javax.swing.*;
import login.GUI_01;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.event.*;

public class first_frame_record extends JPanel {
    private static final String RECORD_FILE = "data/texas_records.txt";
    private String username;
    private int totalGames = 0;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;
    private long totalChipChange = 0;
    private long totalPot = 0;
    private double winRate = 0.0;
    private ArrayList<GameData> gameList = new ArrayList<>();
    private JLabel totalLabel;
    private JLabel winLabel;
    private JLabel loseLabel;
    private JLabel drawLabel;
    private JLabel rateLabel;
    private JLabel chipLabel;
    private JLabel potLabel;
    private JLabel recentMessage;
    private static class GameData {
        String time;
        String result;
        String hand;

        int startChip;
        int endChip;
        int finalPot;

        GameData(String time, String result, String hand, int startChip, int endChip,int finalPot) {
            this.time = time;
            this.result = result;
            this.hand = hand;
            this.startChip = startChip;
            this.endChip = endChip;
            this.finalPot = finalPot;
        }
    }

    public first_frame_record(String username) {
        System.out.println("로그인 사용자 이름22: " + GUI_01.result);
        this.username = username;
        setBackground(Color.black);
        setBounds(0, 0, 1072, 598);
        setLayout(null);

        loadRecords();
        calculateStatistics();
        createUI();

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.gg2.setVisible(false);
                GUI_00.gg.setVisible(true);
            }
        });
    }

    private JLabel makeLabel(String text, int x, int y, int width, int height, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        add(label);
        return label;
    }

    private void loadRecords() {
        gameList.clear();
        Path path = Paths.get(RECORD_FILE);

        if (!Files.exists(path)) {
            System.out.println("게임 기록 파일이 없습니다.");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t", -1);

                // 데이터가 8개가 아니면 제외
                if (data.length != 8) {
                    continue;
                }
                if (!data[0].equals(username)) {
                    continue;
                }

                try {
                    String time = data[1];
                    String result = data[2];
                    String hand = data[3];

                    int startChip = Integer.parseInt(data[5]);
                    int endChip = Integer.parseInt(data[6]);
                    int finalPot = Integer.parseInt(data[7]);

                    GameData game = new GameData(time, result, hand, startChip, endChip, finalPot);
                    gameList.add(game);
                } catch (NumberFormatException e) {
                    System.out.println("잘못된 게임 기록: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void calculateStatistics() {
        totalGames = gameList.size();
        wins = 0;
        losses = 0;
        draws = 0;
        totalChipChange = 0;
        totalPot = 0;

        for (GameData game : gameList) {
            if (game.result.equals("PLAYER_WIN")) {
                wins++;
            }else if (game.result.equals("COMPUTER_WIN")) {
                losses++;
            }else if (game.result.equals("DRAW")) {
                draws++;
            }

            totalChipChange += game.endChip - game.startChip;
            totalPot += game.finalPot;
        }

        int decidedGames = wins + losses;
        if (decidedGames > 0) {
            winRate = (double) wins / decidedGames * 100;
        } else {
            winRate = 0.0;
        }
    }
    private String formatNumber(long number) {
        return String.format("%,d", number);
    }

    private void createUI() {
        JLabel label2 = new JLabel("GAME RECORD");
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label2.setForeground(new Color(235, 190, 90));
        label2.setBounds(50, 30, 250, 30);
        add(label2);

        JLabel line1 = new JLabel();
        line1.setBounds(25, 90, 1022, 1);
        line1.setOpaque(true);
        line1.setBackground(new Color(184, 145, 91));
        add(line1);
 
        makeLabel("TOTAL GAMES", 120, 130, 160, 11, 11, new Color(190, 165, 125));
        totalLabel = makeLabel(formatNumber(totalGames) + " 회", 120, 145, 160, 20, 20, Color.WHITE);

        makeLabel("VICTORIES", 580, 130, 160, 11, 11, new Color(190, 165, 125));
        winLabel = makeLabel(formatNumber(wins) + " 회", 580, 145, 160, 20, 20, new Color(235, 195, 110));

        makeLabel("DEFEATS", 120, 210, 160, 11, 11, new Color(190, 165, 125));
        loseLabel = makeLabel(formatNumber(losses) + " 회", 120, 225, 160, 20, 20, new Color(210, 130, 110));

        makeLabel("DRAWS", 580, 210, 160, 11, 11, new Color(190, 165, 125));
        drawLabel = makeLabel(formatNumber(draws) + " 회", 580, 225, 160, 20, 20, Color.WHITE);

        makeLabel("WIN RATE", 120, 300, 200, 11, 11, new Color(190, 165, 125));
        rateLabel = makeLabel(new DecimalFormat("0.00").format(winRate) + "%", 120, 315, 250, 21, 21, new Color(235, 195, 110));
        
        makeLabel("NET CHIP", 120, 370, 160, 11, 11, new Color(190, 165, 125));
        String chipText = (totalChipChange > 0 ? "+" : "") + formatNumber(totalChipChange);
        chipLabel = makeLabel(chipText, 120, 385, 250, 20,20, totalChipChange >= 0 ? new Color(235, 195, 110): new Color(210, 130, 110));
        
        makeLabel("AVERAGE POT", 580, 370, 160, 11, 11, new Color(190, 165, 125));
        long averagePot = totalGames > 0 ? totalPot / totalGames : 0;
        potLabel = makeLabel(formatNumber(averagePot), 580, 385, 250, 20, 20, Color.WHITE);
        
        ImageIcon background3 = new ImageIcon("image/user1.png");
        Image image3 = background3.getImage();
        Image scaledimage3 = image3.getScaledInstance(430, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledicon3 = new ImageIcon(scaledimage3);
        JLabel label3 = new JLabel(scaledicon3);
        label3.setBounds(100, 110, 430, 70);
        add(label3);

        ImageIcon background4 = new ImageIcon("image/user1.png");
        Image image4 = background4.getImage();
        Image scaledimage4 = image4.getScaledInstance(430, 70, Image.SCALE_SMOOTH);
        ImageIcon scaedicon4 = new ImageIcon(scaledimage4);
        JLabel label4 = new JLabel(scaedicon4);
        label4.setBounds(560, 110, 430, 70);
        add(label4);

        ImageIcon background5 = new ImageIcon("image/user1.png");
        Image image5 = background5.getImage();
        Image scaledimage5 = image5.getScaledInstance(430, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledicon5 = new ImageIcon(scaledimage5);
        JLabel label5 = new JLabel(scaledicon5);
        label5.setBounds(100, 195, 430, 70);
        add(label5);

        ImageIcon background6 = new ImageIcon("image/user1.png");
        Image image6 = background6.getImage();
        Image scaledimage6 = image6.getScaledInstance(430, 70, Image.SCALE_SMOOTH);
        ImageIcon scaedicon6 = new ImageIcon(scaledimage6);
        JLabel label6 = new JLabel(scaedicon6);
        label6.setBounds(560, 195, 430, 70);
        add(label6);

        ImageIcon background7 = new ImageIcon("image/user1.png");
        Image image7 = background7.getImage();
        Image scaledimage7 = image7.getScaledInstance(890, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledicon7 = new ImageIcon(scaledimage7);
        JLabel label7 = new JLabel(scaledicon7);
        label7.setBounds(100, 280, 890, 80);
        add(label7);
    }
}
