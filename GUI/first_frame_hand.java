
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;

public class first_frame_hand extends JPanel {
    static final String RECORD_FILE = "data/texas_records.txt";
    static final String[] HAND_NAMES = {
        "High Card",
        "One Pair",
        "Two Pair",
        "Three of a Kind",
        "Straight",
        "Flush",
        "Full House",
        "Four of a Kind",
        "Straight Flush",
        "Royal Flush"
    };
    String username;
    public DecimalFormat numberFormat = new DecimalFormat("#,##0");
    public DecimalFormat percentFormat = new DecimalFormat("0.0");
    public Map<String, HandStats> handStats = new LinkedHashMap<>();
    public static class HandStats {
        int played = 0;
        int wins = 0;
        int losses = 0;
        int draws = 0;
        long totalPot = 0;

        double getWinRate() {
            int completedGames = wins + losses;

            if (completedGames == 0) {
                return 0.0;
            }
            return (double) wins / completedGames * 100.0;
        }

        long getAveragePot() {
            if (played == 0) {
                return 0;
            }
            return totalPot / played;
        }
    }

    public first_frame_hand(String username) {
        this.username = username;

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(new Color(15, 17, 19));
        setOpaque(true);

        initializeStats();
        loadRecords();
        createUI();
    }

    public void initializeStats() {
        for (String hand : HAND_NAMES) {
            handStats.put(hand, new HandStats());
        }
    }
    public void loadRecords() {
        handStats.clear();
        initializeStats();
        Path path = Paths.get(RECORD_FILE);

        if (!Files.exists(path)) {
            System.out.println("게임 기록 파일이 없습니다.");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t", -1);

                if (data.length < 8) {
                    continue;
                }

                if (!data[0].equals(username)) {
                    continue;
                }

                String result = data[2];
                String handName = data[3];

                if (handName.equalsIgnoreCase("FOLD")) {
                    continue;
                }

                HandStats stats = handStats.get(handName);
                if (stats == null) {
                    continue;
                }
                long pot;

                try {
                    pot = Long.parseLong(data[7]);
                } catch (NumberFormatException e) {
                    continue;
                }

                stats.played++;
                stats.totalPot += pot;

                if (result.equals("PLAYER_WIN")) {
                    stats.wins++;
                }else if (result.equals("COMPUTER_WIN")) {
                    stats.losses++;
                }else if (result.equals("DRAW")) {
                    stats.draws++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshRecords() {
        loadRecords();
        removeAll();
        createUI();
        revalidate();
        repaint();
    }
    public void createUI() {
        JPanel background = new JPanel();
        setBounds(0, 0, 1072, 598);
        setBackground(new Color(15, 17, 19));
        setLayout(null);
        add(background);

        JLabel title = makeLabel("HAND ANALYSIS", 55, 30, 500, 45, 30, new Color(235, 200, 140), Font.BOLD);
        add(title);
        JPanel line = new JPanel();
        line.setBounds(55, 115, 960, 1);
        line.setBackground(new Color(130, 105, 70));
        add(line);

        JLabel label = new JLabel("*HAND ANALYSIS는 FOLD한 게임을 제외하기에 실제 게임 횟수와는 다를 수 있습니다.");
        label.setBounds(55, 100, 500, 14);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        label.setBackground(Color.BLACK);
        add(label);

        int headerY = 140;
        String[] headers = {"HAND", "PLAYED", "WINS", "LOSSES", "DRAWS", "WIN RATE", "AVERAGE POT"};
        int[] xPositions = {65, 390, 490, 585, 680, 775, 900};
        int[] widths = {300, 90, 90, 90, 90, 110, 120};

        for (int i = 0; i < headers.length; i++) {
            JLabel header = makeLabel( headers[i], xPositions[i], headerY, widths[i], 30, 13, new Color(235, 200, 140), Font.BOLD);
            add(header);
        }

        JPanel headerLine = new JPanel();
        headerLine.setBounds(55, 175, 960, 1);
        headerLine.setBackground(new Color(100, 85, 65));
        add(headerLine);

        int startY = 185;
        int rowHeight = 32;
        int totalPlayed = 0;
        int totalWins = 0;
        int totalLosses = 0;
        int totalDraws = 0;

        for (int i = 0; i < HAND_NAMES.length; i++) {
            String handName = HAND_NAMES[i];
            HandStats stats = handStats.get(handName);
            int y = startY + i * rowHeight;
            Color textColor = stats.played > 0 ? new Color(235, 235, 235) : new Color(105, 105, 105);

            add(makeLabel(handName, xPositions[0],  y,  widths[0], rowHeight,14, textColor, Font.PLAIN));
            add(makeLabel(String.valueOf(stats.played), xPositions[1], y,  widths[1], rowHeight, 14, textColor, Font.PLAIN));
            add(makeLabel(String.valueOf(stats.wins), xPositions[2], y, widths[2], rowHeight, 14, new Color(120, 205, 145), Font.PLAIN));
            add(makeLabel(String.valueOf(stats.losses), xPositions[3], y, widths[3], rowHeight, 14, new Color(220, 125, 115), Font.PLAIN));
            add(makeLabel(String.valueOf(stats.draws),xPositions[4], y, widths[4], rowHeight,14, textColor, Font.PLAIN));
            add(makeLabel(percentFormat.format(stats.getWinRate()) + "%", xPositions[5], y, widths[5], rowHeight, 14, new Color(235, 200, 140), Font.BOLD));
            add(makeLabel(numberFormat.format(stats.getAveragePot()), xPositions[6], y, widths[6], rowHeight, 14, textColor, Font.PLAIN));

            JPanel rowLine = new JPanel();
            rowLine.setBounds(55, y + rowHeight - 1, 960, 1);
            rowLine.setBackground(new Color(45, 48, 50));
            add(rowLine);

            totalPlayed += stats.played;
            totalWins += stats.wins;
            totalLosses += stats.losses;
            totalDraws += stats.draws;
        }

        int summaryY = 520;
        JLabel totalLabel = makeLabel("TOTAL", 65, summaryY, 300, 30, 15, new Color(235, 200, 140), Font.BOLD);
        add(totalLabel);
        add(makeLabel(numberFormat.format(totalPlayed) + " Games", 390, summaryY, 180, 30, 14, Color.WHITE, Font.BOLD));
        add(makeLabel("W : " + totalWins, 585, summaryY, 100, 30,14, new Color(120, 205, 145), Font.BOLD));
        add(makeLabel("L : " + totalLosses, 680, summaryY, 100, 30, 14, new Color(220, 125, 115),Font.BOLD));
        add(makeLabel("D : " + totalDraws, 775, summaryY, 100, 30, 14, Color.WHITE, Font.BOLD));

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.gg.setVisible(true);
                GUI_00.g0.setVisible(false);
                GUI_00.g.setVisible(false);
                setVisible(false);
            }
        });
        setComponentZOrder(title, 0);
    }

    public JLabel makeLabel(String text, int x, int y, int width, int height, int fontSize, Color color, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("맑은 고딕", fontStyle, fontSize));
        label.setForeground(color);
        return label;
    }
}
