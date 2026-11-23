
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import GUI_1.Card;
import GUI_1.OneCardManager;
import GUI_1.OneCardJudge;
import GUI_1.Cardandimageswitch;

public class OneCard extends JPanel {

    private Image backgroundImage;

    // 게임 로직 담당 객체
    private OneCardManager manager;

    // 화면 구성 요소
    private JLabel turnLabel;
    private JLabel centerCardLabel;
    private JLabel computerLabel;
    private JLabel deckLabel;

    // 새로 추가한 이미지 영역
    private JLabel computerCardBackLabel;
    private JLabel deckImageLabel;

    private JPanel handPanel;
    private JScrollPane handScroll;

    private JButton drawButton;
    private JButton passButton;

    private boolean resultShown = false;

    public OneCard() {

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(new Color(25, 45, 38));

        // =====================================
        // 배경 이미지
        // =====================================

        ImageIcon bgIcon = new ImageIcon("image/backbackback.png");

        if (bgIcon.getIconWidth() > 0) {
            backgroundImage = bgIcon.getImage();
        } else {
            backgroundImage = null;
            System.out.println("배경 이미지 로딩 실패!");
        }

        // =====================================
        // 턴 표시
        // =====================================

        turnLabel = new JLabel("게임 준비 중");

        turnLabel.setBounds(800, 180, 300, 35);
        turnLabel.setFont(new Font("Serif", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(turnLabel);

        // =====================================
        // 컴퓨터 카드 장수 표시
        // =====================================

        computerLabel = new JLabel("카드 4장");

        computerLabel.setBounds(320, 240, 120, 30);
        computerLabel.setForeground(Color.WHITE);
        computerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        computerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(computerLabel);
        // =====================================

        ImageIcon image13 = new ImageIcon("image/button00.png");
        Image image3 = image13.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
        JButton image333 = new JButton(new ImageIcon(image3));

        image333.setBounds(850, 50, 200, 280);
        image333.setContentAreaFilled(false);
        image333.setBorderPainted(false);
        image333.setFocusPainted(false);
        image333.setOpaque(false);

        add(image333);
        // =====================================
        // 컴퓨터 카드 뒷면
        // =====================================

        computerCardBackLabel = new JLabel("?", SwingConstants.CENTER);

        computerCardBackLabel.setBounds(340, 100, 80, 115);
        computerCardBackLabel.setOpaque(true);
        computerCardBackLabel.setBackground(Color.BLACK);
        computerCardBackLabel.setForeground(Color.WHITE);
        computerCardBackLabel.setFont(new Font("Serif", Font.BOLD, 45));

        computerCardBackLabel.setBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2)
        );

        add(computerCardBackLabel);

        // =====================================
        // 중앙 카드
        // =====================================

        centerCardLabel = new JLabel();

        centerCardLabel.setBounds(490, 205, 75, 110);

        add(centerCardLabel);

        // =====================================
        // 카드 더미 이미지
        // =====================================

        String deckPath =
            "C:\\Users\\user\\Web2026\\AI_Project01\\image_card\\4.0.png";

        ImageIcon deckIcon = new ImageIcon(deckPath);

        System.out.println("카드 더미 이미지 경로: "
            + new java.io.File(deckPath).getAbsolutePath());

        System.out.println("카드 더미 이미지 크기: "
            + deckIcon.getIconWidth() + " x " + deckIcon.getIconHeight());

        if (deckIcon.getIconWidth() > 0) {

            Image deckImage = deckIcon.getImage().getScaledInstance(
                80, 115, Image.SCALE_SMOOTH
            );

            deckImageLabel = new JLabel(new ImageIcon(deckImage));

        } else {

            deckImageLabel = new JLabel("이미지 로딩 실패");
            deckImageLabel.setForeground(Color.WHITE);
        }

        // 위치 및 크기 지정320, 180, 120, 30
        deckImageLabel.setBounds(640, 200, 80, 115);

        add(deckImageLabel);

        // =====================================
        // 남은 카드 수
        // =====================================

        deckLabel = new JLabel("41장");

        deckLabel.setBounds(630, 300, 100, 30);
        deckLabel.setForeground(Color.white);
        deckLabel.setFont(new Font("Serif", Font.BOLD, 18));
        deckLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(deckLabel);

        // =====================================
        // 사용자 카드 영역
        // =====================================

        handPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 8, 5)
        );

        handPanel.setOpaque(false);

        handScroll = new JScrollPane(handPanel);

        handScroll.setBounds(30, 370, 1010, 140);
        handScroll.setOpaque(false);
        handScroll.getViewport().setOpaque(false);

        handScroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        handScroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_NEVER
        );

        add(handScroll);

        // =====================================
        // 상단 이미지
        // =====================================

        ImageIcon image00 = new ImageIcon("image/button0.png");

        Image scaledImage = image00.getImage().getScaledInstance(
            480, 100, Image.SCALE_SMOOTH
        );

        JLabel label00 = new JLabel(new ImageIcon(scaledImage));

        label00.setBounds(290, 10, 480, 100);

        add(label00);

        // =====================================
        // 카드 뽑기 버튼
        // =====================================

        ImageIcon image11 = new ImageIcon("image/button1.png");

        Image image1 = image11.getImage().getScaledInstance(
            150, 40, Image.SCALE_SMOOTH
        );

        drawButton = new JButton(new ImageIcon(image1));

        drawButton.setBounds(350, 525, 150, 40);
        drawButton.setContentAreaFilled(false);
        drawButton.setBorderPainted(false);
        drawButton.setFocusPainted(false);
        drawButton.setOpaque(false);

        add(drawButton);

        // =====================================
        // 턴 넘기기 버튼
        // =====================================

        ImageIcon image22 = new ImageIcon("image/button2.png");

        Image image2 = image22.getImage().getScaledInstance(
            150, 40, Image.SCALE_SMOOTH
        );

        passButton = new JButton(new ImageIcon(image2));

        passButton.setBounds(530, 525, 150, 40);
        passButton.setContentAreaFilled(false);
        passButton.setBorderPainted(false);
        passButton.setFocusPainted(false);
        passButton.setOpaque(false);

        add(passButton);

        // =====================================
        // BACK 버튼
        // =====================================

        JLabel label06 = new JLabel("BACK");

        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200, 50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(label06);

        label06.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                setVisible(false);

                GUI_00.g0.setVisible(true);
                GUI_00.g2.setVisible(false);
                GUI_00.g.setVisible(true);
                GUI_00.g1.setVisible(false);
                GUI_00.g4.setVisible(false);
            }
        });

        // =====================================
        // 버튼 이벤트
        // =====================================

        drawButton.addActionListener(e -> drawUserCard());

        passButton.addActionListener(e -> passUserTurn());

        // 처음에는 게임 시작 전 상태
        drawButton.setEnabled(false);
        passButton.setEnabled(false);
    }

    // =====================================
    // 배경 그리기
    // =====================================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (backgroundImage != null) {

            g.drawImage(
                backgroundImage,
                0, 0,
                getWidth(), getHeight(),
                this
            );
        }
    }

    // =====================================
    // 1. 게임 시작
    // =====================================

    public void startNewGame() {

        // Player 1이 선공
        manager = new OneCardManager(1);

        resultShown = false;

        refreshScreen();
    }

    // =====================================
    // 2. 화면 갱신
    // =====================================

    private void refreshScreen() {

        if (manager == null) {
            return;
        }

        Map<String, Object> state = manager.getGameState();

        String currentPlayer =
            (String) state.get("current_player");

        boolean isUserTurn =
            currentPlayer.equals("Player 1");

        boolean hasDrawn =
            (Boolean) state.get("has_drawn_this_turn");

        // 턴 표시
        turnLabel.setText(
            isUserTurn ? "YOUR TURN" : "COMPUTER TURN"
        );

        // 남은 카드 수 갱신
        deckLabel.setText(
            ""+state.get("deck_remaining") + "장"
        );

        // 컴퓨터 카드 장수 갱신
        computerLabel.setText(
            "카드 " + manager.getPlayer2().getCards().size() + "장"
        );

        // =====================================
        // 중앙 카드 이미지
        // =====================================

        Card.CardData centerCard = manager.getTopCenterCard();

        int suit = Cardandimageswitch.suittonum(
            centerCard.getSuit()
        );

        int rank = Cardandimageswitch.ranktonum(
            centerCard.getRank()
        );

        ImageIcon centerIcon =
            Cardandimageswitch.Card(suit, rank);

        if (centerIcon.getIconWidth() > 0) {

            Image scaled = centerIcon.getImage().getScaledInstance(
                75, 110, Image.SCALE_SMOOTH
            );

            centerCardLabel.setIcon(new ImageIcon(scaled));
            centerCardLabel.setText("");

        } else {

            centerCardLabel.setIcon(null);

            centerCardLabel.setText(
                centerCard.getSuit() + " " + centerCard.getRank()
            );

            centerCardLabel.setForeground(Color.WHITE);
        }

        // =====================================
        // 사용자 카드 목록 갱신
        // =====================================

        handPanel.removeAll();

        List<Card.CardData> userCards =
            manager.getPlayer1().getCards();

        for (int i = 0; i < userCards.size(); i++) {

            final int cardIndex = i;

            Card.CardData card = userCards.get(i);

            int cardSuit =
                Cardandimageswitch.suittonum(card.getSuit());

            int cardRank =
                Cardandimageswitch.ranktonum(card.getRank());

            ImageIcon icon =
                Cardandimageswitch.Card(cardSuit, cardRank);

            JButton cardButton = new JButton();

            if (icon.getIconWidth() > 0) {

                Image scaled = icon.getImage().getScaledInstance(
                    70, 105, Image.SCALE_SMOOTH
                );

                cardButton.setIcon(new ImageIcon(scaled));

            } else {

                cardButton.setText(
                    card.getSuit() + " " + card.getRank()
                );
            }

            cardButton.setPreferredSize(
                new Dimension(75, 110)
            );

            cardButton.setEnabled(isUserTurn);

            cardButton.addActionListener(e ->
                playUserCard(cardIndex)
            );

            handPanel.add(cardButton);
        }

        handPanel.revalidate();
        handPanel.repaint();

        // =====================================
        // 버튼 활성화
        // =====================================

        drawButton.setEnabled(
            isUserTurn && !hasDrawn
        );

        passButton.setEnabled(
            isUserTurn && hasDrawn
            && !hasPlayableCard(userCards)
        );

        // =====================================
        // 게임 종료 확인
        // =====================================

        if (manager.isGameOver()) {
            showGameResult();
        }
    }

    // =====================================
    // 3. 사용자가 카드를 제출하는 기능
    // =====================================

    private void playUserCard(int cardIndex) {

        if (manager == null || manager.isGameOver()) {
            return;
        }

        if (!manager.getCurrentPlayer().getName().equals("Player 1")) {
            return;
        }

        Map<String, Object> result =
            manager.playCard(cardIndex);

        if (!(Boolean) result.get("success")) {

            JOptionPane.showMessageDialog(
                this,
                result.get("message")
            );

            return;
        }

        refreshScreen();

        if (!manager.isGameOver()
            && manager.getCurrentPlayer().getName().equals("Player 2")) {

            computerTurn();
        }
    }

    // =====================================
    // 4. 카드 뽑기
    // =====================================

    private void drawUserCard() {

        if (manager == null || manager.isGameOver()) {
            return;
        }

        if (hasPlayableCard(manager.getPlayer1().getCards())) {

            JOptionPane.showMessageDialog(
                this,
                "제출할 수 있는 카드가 있습니다."
            );

            return;
        }

        Map<String, Object> result = manager.drawCard();

        if (!(Boolean) result.get("success")) {

            JOptionPane.showMessageDialog(
                this,
                result.get("message")
            );

            refreshScreen();
            return;
        }

        refreshScreen();
    }

    // =====================================
    // 5. 턴 넘기기
    // =====================================

    private void passUserTurn() {

        if (manager == null || manager.isGameOver()) {
            return;
        }

        if (hasPlayableCard(manager.getPlayer1().getCards())) {

            JOptionPane.showMessageDialog(
                this,
                "제출할 수 있는 카드가 있습니다."
            );

            return;
        }

        if (manager.passTurn()) {

            refreshScreen();

            computerTurn();
        }
    }

    // =====================================
    // 6. 제출 가능한 카드 확인
    // =====================================

    private boolean hasPlayableCard(List<Card.CardData> cards) {

        Card.CardData centerCard =
            manager.getTopCenterCard();

        for (Card.CardData card : cards) {

            if (OneCardJudge.canPlayCard(card, centerCard)) {
                return true;
            }
        }

        return false;
    }

    // =====================================
    // 7. 컴퓨터 턴
    // =====================================

    private void computerTurn() {

        // J, K 등으로 컴퓨터 턴이 유지될 수 있으므로 반복 처리
        int count = 0;

        while (!manager.isGameOver()
            && manager.getCurrentPlayer().getName().equals("Player 2")
            && count < 20) {

            count++;

            List<Card.CardData> computerCards =
                manager.getPlayer2().getCards();

            int playableIndex = -1;

            // 제출할 수 있는 카드 검색
            for (int i = 0; i < computerCards.size(); i++) {

                if (OneCardJudge.canPlayCard(
                    computerCards.get(i),
                    manager.getTopCenterCard()
                )) {

                    playableIndex = i;
                    break;
                }
            }

            // 제출할 카드가 있는 경우
            if (playableIndex != -1) {

                manager.playCard(playableIndex);

            } else {

                // 제출할 카드가 없으면 한 장 뽑기
                Map<String, Object> drawResult =
                    manager.drawCard();

                if (!(Boolean) drawResult.get("success")) {
                    break;
                }

                boolean canPlay =
                    (Boolean) drawResult.get("can_play_drawn");

                if (canPlay) {

                    // 방금 뽑은 카드의 위치는 마지막 인덱스
                    int lastIndex =
                        manager.getPlayer2().getCards().size() - 1;

                    manager.playCard(lastIndex);

                } else {

                    manager.passTurn();
                }
            }
        }

        refreshScreen();
    }

    // =====================================
    // 8. 게임 결과
    // =====================================

    private void showGameResult() {

        if (resultShown) {
            return;
        }

        resultShown = true;

        String message;

        if (manager.isDraw()) {

            message = "무승부입니다.";

        } else {

            message =
                manager.getWinner().getName() + " 승리!";
        }

        JOptionPane.showMessageDialog(
            this,
            message,
            "게임 종료",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
