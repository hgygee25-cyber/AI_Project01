package GUI;

import javax.swing.*;
import java.awt.*;

public class TexasHoldem_donon extends JPanel {
    public JButton bettingButton;
    public JButton foldButton;

    public TexasHoldem_donon() {
        setBounds(380, 180, 310, 205);
        setLayout(null);
        setBackground(new Color(25, 25, 25));
        setBorder(BorderFactory.createLineBorder(new Color(190, 145, 65), 2));

        JLabel message = new JLabel("<html><center>KEEP PLAYING THE GAME?<br><br>Betting Fee : 1,000</center></html>",SwingConstants.CENTER);
        message.setBounds(10, 10, 290, 105);
        message.setFont(new Font("Serif", Font.BOLD, 19));
        message.setForeground(Color.WHITE);
        add(message);

        bettingButton = new JButton("Betting");
        bettingButton.setBounds(55, 145, 85, 35);
        styleButton(bettingButton);
        add(bettingButton);

        foldButton = new JButton("Fold");
        foldButton.setBounds(170, 145, 85, 35);
        styleButton(foldButton);
        add(foldButton);

        bettingButton.addActionListener(e -> startBetting());
        foldButton.addActionListener(e -> foldBeforeFlop());
    }

    public void styleButton(JButton button) {
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setBackground(new Color(190, 145, 65));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    public void startBetting() {
        TexasHoldem_Betting.startAfterPreflop();
    }

    public void foldBeforeFlop() {
        TexasHoldem_Betting.foldBeforeFlop();
    }
}
