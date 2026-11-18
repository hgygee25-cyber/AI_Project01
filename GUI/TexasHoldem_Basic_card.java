
package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TexasHoldem_Basic_card {
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

    // 1. 컴퓨터 카드 2장 배분
    public static void First_time_card_com() {
        int random_suit1;
        int random_rank1;
        String value;

        do {
            random_suit1 = rand.nextInt(TexasHoldem.suit.size());
            random_rank1 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value = random_suit1 + "," + random_rank1;
        } while (TexasHoldem.choosed_card.contains(value));

        TexasHoldem.choosed_card.add(value);

        ImageIcon original1 = GUI_1.Cardandimageswitch.Card(4, 0);
        Image scaledImage = original1.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );
        TexasHoldem.card_com1.setIcon(new ImageIcon(scaledImage));

        int random_suit2;
        int random_rank2;
        String value2;

        do {
            random_suit2 = rand.nextInt(TexasHoldem.suit.size());
            random_rank2 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value2 = random_suit2 + "," + random_rank2;
        } while (TexasHoldem.choosed_card.contains(value2));

        TexasHoldem.choosed_card.add(value2);

        ImageIcon original2 = GUI_1.Cardandimageswitch.Card(4, 0);
        Image scaledImage2 = original2.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );
        TexasHoldem.card_com2.setIcon(new ImageIcon(scaledImage2));
    }

    // 2. 사용자 카드 2장 배분
    public static void First_time_card_user() {
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
        Image scaledImage1 = original1.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );
        TexasHoldem.card_user1.setIcon(new ImageIcon(scaledImage1));

        int random_suit2;
        int random_rank2;
        String value2;

        do {
            random_suit2 = rand.nextInt(TexasHoldem.suit.size());
            random_rank2 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value2 = random_suit2 + "," + random_rank2;
        } while (TexasHoldem.choosed_card.contains(value2));

        TexasHoldem.choosed_card.add(value2);

        ImageIcon original2 = GUI_1.Cardandimageswitch.Card(
                random_suit2, random_rank2
        );
        Image scaledImage2 = original2.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );
        TexasHoldem.card_user2.setIcon(new ImageIcon(scaledImage2));
    }

    // 3. 플랍 카드 3장 배분
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
        Image scaledImage1 = original1.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );

        int random_suit2;
        int random_rank2;
        String value2;

        do {
            random_suit2 = rand.nextInt(TexasHoldem.suit.size());
            random_rank2 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value2 = random_suit2 + "," + random_rank2;
        } while (TexasHoldem.choosed_card.contains(value2));

        TexasHoldem.choosed_card.add(value2);

        ImageIcon original2 = GUI_1.Cardandimageswitch.Card(
                random_suit2, random_rank2
        );
        Image scaledImage2 = original2.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );

        int random_suit3;
        int random_rank3;
        String value3;

        do {
            random_suit3 = rand.nextInt(TexasHoldem.suit.size());
            random_rank3 = rand.nextInt(TexasHoldem.rank.size()) + 1;
            value3 = random_suit3 + "," + random_rank3;
        } while (TexasHoldem.choosed_card.contains(value3));

        TexasHoldem.choosed_card.add(value3);

        ImageIcon original3 = GUI_1.Cardandimageswitch.Card(
                random_suit3, random_rank3
        );
        Image scaledImage3 = original3.getImage().getScaledInstance(
                65, 110, Image.SCALE_SMOOTH
        );

        animateCard(TexasHoldem.card_community1,
                new ImageIcon(scaledImage1), () -> {
            animateCard(TexasHoldem.card_community2,
                    new ImageIcon(scaledImage2), () -> {
                animateCard(TexasHoldem.card_community3,
                        new ImageIcon(scaledImage3), null);
            });
        });
    }
}
