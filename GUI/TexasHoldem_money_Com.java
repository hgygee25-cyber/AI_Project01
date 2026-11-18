package GUI;

import javax.swing.*;
import java.awt.*;

public class TexasHoldem_money_Com extends JPanel {
    public final JLabel moneyLabel;
    public TexasHoldem_money_Com() {
        setBounds(190, 260, 245, 35);
        setLayout(null);
        setOpaque(false);

        moneyLabel = new JLabel("COM CHIPS    0");
        moneyLabel.setBounds(10, 8, 230, 20);
        moneyLabel.setFont(new Font("Serif", Font.BOLD, 14));
        moneyLabel.setForeground(new Color(235, 200, 140));

        add(moneyLabel);
    }

    // 컴퓨터 보유 칩 변경
    public void updateMoney(int money) {
        moneyLabel.setText("COM CHIPS    " + money);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g2.dispose();
    }
}
