
package GUI;

import javax.swing.*;
import java.awt.*;

public class TexasHoldem_money extends JPanel {

    private final JLabel[][] stageLabels = new JLabel[4][2];
    private final JLabel[] totalLabels = new JLabel[2];

    private final String[] stageNames = {
        "PREFLOP", "FLOP", "TURN", "RIVER"
    };

    public TexasHoldem_money() {
        setBounds(705, 385, 270, 155);
        setLayout(null);
        setOpaque(false);

        JLabel title = new JLabel("BETTING MONEY", SwingConstants.CENTER);

        title.setBounds(5, 4, 260, 22);
        title.setFont(new Font("Serif", Font.BOLD, 19));
        title.setForeground(new Color(248, 239, 218));
        add(title);

        JLabel comTitle = new JLabel("COM", SwingConstants.CENTER);
        comTitle.setBounds(105, 29, 55, 18);
        comTitle.setForeground(new Color(248, 239, 218));
        add(comTitle);

        JLabel userTitle = new JLabel("USER", SwingConstants.CENTER);
        userTitle.setBounds(175, 29, 55, 18);
        userTitle.setForeground(new Color(248, 239, 218));
        add(userTitle);

        // 단계별 금액 표시
        for (int i = 0; i < 4; i++) {
            JLabel stage = new JLabel(stageNames[i]);
            stage.setBounds(10, 51 + i * 21, 85, 18);
            stage.setFont(new Font("Serif", Font.PLAIN, 12));
            stage.setForeground(new Color(248, 239, 218));
            add(stage);

            stageLabels[i][0] = createValueLabel(105, 51 + i * 21);
            stageLabels[i][1] = createValueLabel(175, 51 + i * 21);

            add(stageLabels[i][0]);
            add(stageLabels[i][1]);
        }

        // 총 베팅 금액
        totalLabels[0] = createValueLabel(105, 137);
        totalLabels[1] = createValueLabel(175, 137);

        add(totalLabels[0]);
        add(totalLabels[1]);

        JLabel totalTitle = new JLabel("TOTAL");
        totalTitle.setBounds(10, 137, 85, 18);
        totalTitle.setFont(new Font("Serif", Font.BOLD, 13));
        totalTitle.setForeground(new Color(248, 239, 218));
        add(totalTitle);
    }

    // 1. 금액 JLabel 생성
    private JLabel createValueLabel(int x, int y) {

        JLabel label = new JLabel("0", SwingConstants.CENTER);

        label.setBounds(x, y, 55, 18);
        label.setFont(new Font("Serif", Font.PLAIN, 13));
        label.setForeground(Color.WHITE);

        return label;
    }

    // 2. 특정 단계 베팅 금액 변경
    public void setStageMoney(
            int stage,
            int comAmount,
            int userAmount
    ) {

        if (stage < 0 || stage >= 4) {
            return;
        }

        stageLabels[stage][0].setText(
                String.valueOf(comAmount)
        );

        stageLabels[stage][1].setText(
                String.valueOf(userAmount)
        );

        updateTotals();
    }

    // 3. 전체 베팅 금액 계산
    public void updateTotals() {

        int comTotal = 0;
        int userTotal = 0;

        for (int i = 0; i < 4; i++) {

            comTotal += parse(stageLabels[i][0].getText());

            userTotal += parse(stageLabels[i][1].getText());
        }

        totalLabels[0].setText(String.valueOf(comTotal));
        totalLabels[1].setText(String.valueOf(userTotal));
    }

    // 4. 문자열 숫자 변환
    private int parse(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // 5. 새로운 판 시작 시 초기화
    public void resetAmounts() {

        for (int i = 0; i < 4; i++) {
            stageLabels[i][0].setText("0");
            stageLabels[i][1].setText("0");
        }

        updateTotals();
    }

    // 6. 패널 배경
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(0, 0, 0, 180));

        g2.fillRoundRect(
                0, 0, getWidth(), getHeight(), 25, 25
        );

        g2.dispose();
    }
}
