package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class first_frame_period extends JPanel {
    public static  String RECORD_FILE = "data/texas_records.txt";
    public static  DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public  DecimalFormat numberFormat = new DecimalFormat("#,##0");
    public String username;
    public JComboBox<String> periodCombo;
    public JPanel recordListPanel;
    public JLabel gameCountLabel;
    public JLabel winLabel;
    public JLabel lossLabel;
    public JLabel drawLabel;
    public JLabel winRateLabel;
    public JLabel chipLabel;
    public JLabel potLabel;
    public  ArrayList<GameRecord> allRecords = new ArrayList<>();

    public static class GameRecord {
        String date;
        String result;
        String hand;
        long beforeChips;
        long afterChips;
        long pot;
        LocalDateTime dateTime;

        GameRecord(String[] data) {
            date = data[1];
            result = data[2];
            hand = data[3];
            beforeChips = Long.parseLong(data[5]);
            afterChips = Long.parseLong(data[6]);
            pot = Long.parseLong(data[7]);
            dateTime = LocalDateTime.parse(date, DATE_FORMAT);
        }

        long getChipChange() {
            return afterChips - beforeChips;
        }
    }

    public first_frame_period(String username) {
        this.username = username;

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(new Color(15, 17, 19));
        setOpaque(true);

        loadRecords();
        createUI();
        updatePeriod();
    }

    public void loadRecords() {
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
                try {
                    GameRecord record = new GameRecord(data);
                    allRecords.add(record);

                } catch (Exception e) {
                    System.out.println("기록 형식 오류: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        allRecords.sort(
                (a, b) -> b.dateTime.compareTo(a.dateTime)
        );
    }

    public void createUI() {
        setBounds(0, 0, 1072, 598);
        setBackground(new Color(15, 17, 19));
        setLayout(null);
        setOpaque(true);

        JLabel title = makeLabel("PERIOD RECORD", 55, 30, 500, 45,30, new Color(235, 200, 140), Font.BOLD);
        add(title);

        // BACK 버튼
        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);

        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);

                if (GUI_00.gg != null) {
                    GUI_00.gg.setVisible(true);
                    GUI_00.contentpane.setComponentZOrder(GUI_00.gg, 0);
                    GUI_00.contentpane.revalidate();
                    GUI_00.contentpane.repaint();
                }
            }
        });

        // 상단 구분선
        JLabel line1 = new JLabel();
        line1.setBounds(55, 115, 960, 1);
        line1.setOpaque(true);
        line1.setBackground(new Color(184, 145, 91));
        add(line1);

        // GAME HISTORY
        JLabel listTitle = makeLabel(
            "GAME HISTORY", 60, 140, 300, 30,
            20, new Color(235, 200, 140), Font.BOLD
        );
        add(listTitle);
        //
        String[] periods = {"전체", "일별", "주별", "월별"};
        periodCombo = new JComboBox<>(periods);
        periodCombo.setBounds(850, 140, 150, 30);
        periodCombo.setBackground(new Color(15, 17, 19));
        periodCombo.setForeground(Color.WHITE);
        periodCombo.setBorder(BorderFactory.createLineBorder(new Color(184, 145, 91), 1));
        periodCombo.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        add(periodCombo);

        periodCombo.addActionListener(e -> {
            filterRecords();
        });
        // 테이블 헤더
        String[] headers = {
            "DATE",
            "RESULT",
            "HAND",
            "CHIP CHANGE",
            "POT"
        };

        int[] headerX = {65, 300, 465, 710, 895};
        int[] headerW = {220, 150, 200, 160, 100};

        for (int i = 0; i < headers.length; i++) {
            JLabel header = makeLabel(
                headers[i],
                headerX[i],
                190,
                headerW[i],
                25,
                12,
                new Color(180, 170, 150),
                Font.BOLD
            );

            add(header);
        }

        // 헤더 구분선
        JPanel headerLine = new JPanel();
        headerLine.setBounds(60, 220, 950, 1);
        headerLine.setBackground(new Color(100, 85, 65));
        add(headerLine);

        recordListPanel = new JPanel();
        recordListPanel.setLayout(null);
        recordListPanel.setBackground(new Color(20, 22, 24));

        JScrollPane scrollPane = new JScrollPane(recordListPanel);
        scrollPane.setBounds(60, 222, 950, 325);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(20, 22, 24));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setBackground(new Color(184, 156, 109)); // 스크롤바 배경
        scrollBar.setForeground(new Color(235, 200, 140)); // 스크롤 색상
        scrollBar.setUnitIncrement(16);

        add(scrollPane);
        setComponentZOrder(title, 0);
    }
    public void refreshRecords() {
        allRecords.clear();
        loadRecords();
        updatePeriod();
    }
    public void updatePeriod() {
        updateRecordList(allRecords);
    }
    public void updateRecordList(ArrayList<GameRecord> records) {
        recordListPanel.removeAll();
        int rowHeight = 35;

        for (int i = 0; i < records.size(); i++) {
            GameRecord record = records.get(i);

            int y = i * rowHeight;
            Color resultColor;

            if (record.result.equals("PLAYER_WIN")) {
                resultColor = new Color(120, 205, 145);
            }
            else if (record.result.equals("COMPUTER_WIN")) {
                resultColor = new Color(220, 125, 115);
            }
            else {
                resultColor = new Color(210, 190, 130);
            }

            recordListPanel.add(makeLabel(record.date, 5, y, 220, rowHeight, 12, Color.WHITE,Font.PLAIN));

            String resultText = record.result;
            recordListPanel.add(makeLabel(resultText, 240, y, 150, rowHeight, 12, resultColor, Font.BOLD));
            recordListPanel.add(makeLabel(record.hand, 405, y, 200, rowHeight, 12, Color.WHITE, Font.PLAIN));

            long change = record.getChipChange();
            String changeText = (change > 0 ? "+" : "") + numberFormat.format(change);
            Color chipColor = change > 0 ? new Color(120, 205, 145) : change < 0 ? new Color(220, 125, 115) : Color.WHITE;

            recordListPanel.add(makeLabel(changeText, 650, y, 160, rowHeight, 12,chipColor,Font.PLAIN));
            recordListPanel.add(makeLabel(numberFormat.format(record.pot), 835, y, 100, rowHeight, 12, Color.WHITE, Font.PLAIN));

            JPanel line = new JPanel();
            line.setBounds(0, y + rowHeight - 1, 940, 1);
            line.setBackground(new Color(45, 48, 50));
            recordListPanel.add(line);
        }

        if (records.isEmpty()) {
            JLabel emptyLabel = makeLabel("해당 기간에 게임 기록이 없습니다.",0, 65, 940, 30, 14, new Color(140, 140, 140), Font.PLAIN);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            recordListPanel.add(emptyLabel);
        }

        recordListPanel.setPreferredSize(new Dimension(930,Math.max(205, records.size() * rowHeight)));
        recordListPanel.revalidate();
        recordListPanel.repaint();
    }

    public JLabel makeLabel(String text, int x, int y, int width, int height, int fontSize, Color color, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("맑은 고딕", fontStyle, fontSize));
        label.setForeground(color);
        return label;
    }

    public void filterRecords() {
        String selected = (String) periodCombo.getSelectedItem();
        ArrayList<GameRecord> filteredRecords = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (GameRecord record : allRecords) {
            LocalDate gameDate = record.dateTime.toLocalDate();

            if (selected.equals("전체")) {
                filteredRecords.add(record);
            }else if (selected.equals("일별")) {
                if (gameDate.equals(today)) {
                    filteredRecords.add(record);
                }
            }else if (selected.equals("주별")) {
                LocalDate startOfWeek = today.with(
                    java.time.temporal.TemporalAdjusters.previousOrSame(
                        DayOfWeek.MONDAY
                    )
                );
                LocalDate endOfWeek = startOfWeek.plusDays(6);
                if (!gameDate.isBefore(startOfWeek) && !gameDate.isAfter(endOfWeek)) {
                    filteredRecords.add(record);
                }
            }else if (selected.equals("월별")) {
                if (gameDate.getYear() == today.getYear() && gameDate.getMonth() == today.getMonth()) {
                    filteredRecords.add(record);
                }
            }
        }
        updateRecordList(filteredRecords);
    }
}
