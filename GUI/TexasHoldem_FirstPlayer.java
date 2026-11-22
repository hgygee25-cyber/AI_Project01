
package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import GUI_1.Cardandimageswitch;

public class TexasHoldem_FirstPlayer extends JPanel {
    public JLabel userCard;
    public JLabel comCard;
    public JLabel resultLabel;
    public JButton drawButton;
    public Random random = new Random();
    public boolean resolved = false;

    public TexasHoldem_FirstPlayer() {
        setBounds(350, 150, 370, 290);
        setLayout(null);
        setBackground(new Color(25, 25, 25));
        setBorder(BorderFactory.createLineBorder(new Color(190, 145, 65), 2));

        JLabel title = new JLabel("FIRST PLAYER DECISION", SwingConstants.CENTER);
        title.setBounds(20, 15, 330, 35);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(new Color(238, 215, 174));
        add(title);

        JLabel userLabel = new JLabel("USER", SwingConstants.CENTER);
        userLabel.setBounds(65, 65, 100, 25);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(userLabel);

        JLabel comLabel = new JLabel("COM", SwingConstants.CENTER);
        comLabel.setBounds(205, 65, 100, 25);
        comLabel.setForeground(Color.WHITE);
        comLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(comLabel);

        userCard = new JLabel("", SwingConstants.CENTER);
        userCard.setBounds(80, 95, 65, 110);
        add(userCard);

        comCard = new JLabel("", SwingConstants.CENTER);
        comCard.setBounds(220, 95, 65, 110);
        add(comCard);

        resultLabel = new JLabel("카드를 뽑아 선공권을 결정하세요.", SwingConstants.CENTER);
        resultLabel.setBounds(20, 210, 330, 25);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        resultLabel.setForeground(Color.WHITE);
        add(resultLabel);

        drawButton = new JButton("카드 뽑기");
        drawButton.setBounds(115, 245, 140, 32);
        drawButton.setBackground(new Color(190, 145, 65));
        drawButton.setForeground(Color.WHITE);
        drawButton.setFocusPainted(false);
        drawButton.setBorderPainted(false);
        add(drawButton);

        drawButton.addActionListener(e -> drawOrStart());
    }

    public void resetPanel() {
        resolved = false;
        userCard.setIcon(null);
        comCard.setIcon(null);

        resultLabel.setText("카드를 뽑아 선공권을 결정하세요.");
        drawButton.setText("카드 뽑기");
    }

    public void drawOrStart() {
        if (resolved) {
            setVisible(false);
            TexasHoldem.startActualGame();
            return;
        }

        int userSuit = random.nextInt(4);
        int userRank = random.nextInt(13) + 1;

        int comSuit = random.nextInt(4);
        int comRank = random.nextInt(13) + 1;

        ImageIcon userIcon = Cardandimageswitch.Card(userSuit, userRank);
        ImageIcon comIcon = Cardandimageswitch.Card(comSuit, comRank);

        if (userIcon.getIconWidth() > 0) {
            Image userImage = userIcon.getImage().getScaledInstance(65, 110, Image.SCALE_SMOOTH);
            userCard.setIcon(new ImageIcon(userImage));
        } else {
            userCard.setText("IMAGE ERROR");
        }

        if (comIcon.getIconWidth() > 0) {
            Image comImage = comIcon.getImage().getScaledInstance(65, 110, Image.SCALE_SMOOTH);
            comCard.setIcon(new ImageIcon(comImage));
        } else {
            comCard.setText("IMAGE ERROR");
        }

        if (userRank == comRank) {
            resultLabel.setText("무승부! 다시 카드를 뽑으세요.");
            drawButton.setText("다시 뽑기");
            return;
        }

        if (getRankValue(userRank) > getRankValue(comRank)) {
            TexasHoldem.userFirst = true;
            resultLabel.setText("사용자가 선공권을 획득했습니다!");
        } else {
            TexasHoldem.userFirst = false;
            resultLabel.setText("컴퓨터가 선공권을 획득했습니다!");
        }

        resolved = true;
        drawButton.setText("게임 진행");
    }

    public int getRankValue(int rank) {
        if (rank == 1) return 14;
        return rank;
    }
}
