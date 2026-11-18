
package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TexasHoldem_Basic_up_card {

    static Random rand = new Random();

    public static void animateCard(JLabel card, ImageIcon image, Runnable after) {
        int endX = card.getX();
        int endY = card.getY();

        Container parent = card.getParent();

        JLabel movingCard = new JLabel(image);
        movingCard.setBounds(100, 250, 65, 110);

        parent.add(movingCard, 0);
        parent.setComponentZOrder(movingCard, 0);

        card.setVisible(false);

        final int[] step = {0};
        int totalStep = 25;

        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {
            step[0]++;

            double t = (double) step[0] / totalStep;
            double speed = 1 - Math.pow(1 - t, 2);

            int x = 100 + (int)((endX - 100) * speed);
            int y = 250 + (int)((endY - 250) * speed);

            movingCard.setLocation(x, y);

            if (step[0] >= totalStep) {
                timer.stop();

                parent.remove(movingCard);

                card.setIcon(image);
                card.setVisible(true);

                parent.repaint();

                if (after != null) {
                    after.run();
                }
            }
        });

        timer.start();
    }

    // 4. 턴 카드 배분
    public static void community_card() {
        int random_suit1;
        int random_rank1;
        String value;

        do {
            random_suit1 = rand.nextInt(TexasHoldem.suit.size());
            random_rank1 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value = random_suit1 + "," + random_rank1;
        } while (TexasHoldem.choosed_card.contains(value));

        TexasHoldem.choosed_card.add(value);

        ImageIcon original1 = GUI_1.Cardandimageswitch.Card(
                random_suit1, random_rank1
        );
        Image scaledImage = original1.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );

        animateCard(TexasHoldem.card_community4,
                new ImageIcon(scaledImage), null);
    }
}
