package GUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class first_frame_store3 extends JPanel {
    static JTable table;
    static DefaultTableModel model;

    public first_frame_store3() {
        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(Color.black);

        JLabel label2 = new JLabel("RANKING OF ALL");
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        label2.setForeground(new Color(235, 190, 90));
        label2.setBounds(50, 30, 300, 30);
        add(label2);

        JLabel line1 = new JLabel();
        line1.setBounds(25, 80, 1022, 1);
        line1.setOpaque(true);
        line1.setBackground(new Color(184, 145, 91));
        add(line1);

        String[] title = {"RANK", "USER", "CHIP", "TOTAL GAME", "WIN", "LOSE", "DRAW", "WIN RATE"};

        model = new DefaultTableModel(title, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        table.setRowHeight(42);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);

        ImageIcon medal1 = new ImageIcon(new ImageIcon("image/메달1.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon medal2 = new ImageIcon(new ImageIcon("image/메달2.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon medal3 = new ImageIcon(new ImageIcon("image/메달3.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBackground(Color.BLACK);
                label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(45, 40, 35)));

                label.setIcon(null);
                label.setText(value != null ? value.toString() : "");

                if (column == 0) {
                    int rank = Integer.parseInt(value.toString());
                    
                    if (rank == 1) {
                        label.setIcon(medal1);
                        label.setText("");
                    } else if (rank == 2) {
                        label.setIcon(medal2);
                        label.setText("");
                    } else if (rank == 3) {
                        label.setIcon(medal3);
                        label.setText("");
                    }
                }

                if (column == 0 || column == 2 || column == 7) {
                    label.setForeground(new Color(235, 195, 110));
                } else if (column == 5) {
                    label.setForeground(new Color(210, 130, 110));
                } else {
                    label.setForeground(Color.WHITE);
                }
                return label;
            }
        };


        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 35));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
                label.setForeground(new Color(190, 165, 125)); 
                label.setBackground(Color.BLACK);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(184, 145, 91)));
                return label;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(40, 115, 992, 420);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setViewportBorder(null);
        scroll.setBackground(Color.BLACK);
        scroll.getViewport().setBackground(Color.BLACK);

        JPanel corner = new JPanel();
        corner.setBackground(Color.BLACK);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);
        add(scroll);

        ranking();

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.ggg.setVisible(false);
                GUI_00.gg.setVisible(true);
            }
        });
    }

    public static void ranking() {
        model.setRowCount(0);

        HashMap<String, Integer> chips = new HashMap<>();
        HashMap<String, Integer> win = new HashMap<>();
        HashMap<String, Integer> lose = new HashMap<>();
        HashMap<String, Integer> draw = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/user_chips.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");

                if (data.length >= 2) {
                    String name = data[0];
                    int chip = Integer.parseInt(data[1]);

                    chips.put(name, chip);
                    win.put(name, 0);
                    lose.put(name, 0);
                    draw.put(name, 0);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(
                new FileReader("data/texas_records.txt")
            );

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length >= 3) {
                    String name = data[0];
                    String result = data[2];

                    if (win.containsKey(name)) {
                        if (result.equals("PLAYER_WIN")) {
                            win.put(name, win.get(name) + 1);
                        } else if (result.equals("COMPUTER_WIN")) {
                            lose.put(name, lose.get(name) + 1);
                        } else if (result.equals("DRAW")) {
                            draw.put(name, draw.get(name) + 1);
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> users = new ArrayList<>(chips.keySet());
        users.sort((a, b) -> Integer.compare(chips.get(b), chips.get(a)));

        // 표에 출력
        int rank = 1;

        for (int i = 0; i < users.size(); i++) {
            String name = users.get(i);
            int chip = chips.get(name);
            int w = win.get(name);
            int l = lose.get(name);
            int d = draw.get(name);
            int total = w + l + d;
            double rate = 0;
        
            if (i > 0 && chip < chips.get(users.get(i - 1))) {
                rank = i + 1;
            } 
            if (total > 0) {
                rate = (double) w / total * 100;
            }

            model.addRow(new Object[] {
                rank, name, String.format("%,d", chip), total, w, l, d, String.format("%.1f%%", rate)
            });
        }
    }
}