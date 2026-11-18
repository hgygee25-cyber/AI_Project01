package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Practice_Basic_upup_card{
    static Random rand = new Random();
    //
    public static void animateCard(JLabel card, ImageIcon image, Runnable after) {
        // 기존 카드의 도착 위치
        int endX = card.getX();
        int endY = card.getY();

        // 카드가 속한 부모 패널
        Container parent = card.getParent();

        // 애니메이션 전용 JLabel 생성
        JLabel movingCard = new JLabel(image);

        movingCard.setBounds(100, 250, 65, 110);

        // 기존 카드 위에 애니메이션 카드 표시
        parent.add(movingCard, 0);
        parent.setComponentZOrder(movingCard, 0);

        // 기존 카드는 애니메이션이 끝날 때까지 숨김
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

                // 애니메이션 카드 제거
                parent.remove(movingCard);

                // 기존 카드에 이미지 표시
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
    //
    public static void community_card() {
        int random_suit1;
        int random_rank1; 
        String value;

        do {
            random_suit1 = rand.nextInt(Practice.suit.size());
            random_rank1 = rand.nextInt(Practice.rank.size()) + 1;
            value = random_suit1 + "," + random_rank1;
        } while (Practice.choosed_card.contains(value));
        Practice.choosed_card.add(value);
        
        System.out.println("0*0*.. 현재 arraylist choosed card: " + Practice.choosed_card);

        ImageIcon original1 = GUI_1.Cardandimageswitch.Card(random_suit1, random_rank1);
        Image scaledImage = original1.getImage().getScaledInstance(65, 110, Image.SCALE_SMOOTH);
        
        animateCard(Practice.card_community5, new ImageIcon(scaledImage), () -> {
        });
    }
}
