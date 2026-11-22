package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import GUI_1.IndianPokerManager;
import GUI_1.Card;
import GUI_1.Cardandimageswitch;
import GUI_4.ComputerAI;
import GUI_4.Action;
import GUI_4.DecisionResult;

public class Indian_poker extends JPanel {
    private Image backgroundImage;
    private Image label1;
    private Image label2;
    private Image label3;
    private Image label4;
    private Image label5;
    private Image label6;

    private Image label7;
    private Image label8;
    private Image label9;
    private Image label10;
    private Image label11;
    //
    private IndianPokerManager manager;
    private final ComputerAI computerAI = new ComputerAI();

    private JLabel titleLabel;
    private JLabel playerCardLabel;
    private JLabel computerCardLabel;

    private JLabel playerNameLabel;
    private JLabel computerNameLabel;

    private JLabel playerChipsLabel;
    private JLabel computerChipsLabel;
    private JLabel potLabel;
    private JLabel statusLabel;

    // 이미지로 만든 클릭 버튼
    private JLabel callLabel;
    private JLabel raiseLabel;
    private JLabel raiseAmountLabel;
    private JLabel foldLabel;
    private JLabel nextRoundLabel;

    // label9에서 선택한 레이즈 금액
    private int selectedRaiseAmount = 100;

    // 컴퓨터가 레이즈해서 사용자의 응답을 기다리는 상태인지 확인
    private boolean awaitingUserResponse = false;

    private final Color BACKGROUND = new Color(15, 17, 19);
    private final Color GOLD = new Color(212, 175, 95);
    private final Color WHITE = new Color(240, 240, 240);

    public Indian_poker() {
        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(BACKGROUND);
        setOpaque(true);

        // 배경 이미지 불러오기
        //ImageIcon background = new ImageIcon("image/back1.png");
        ImageIcon background = new ImageIcon("image/backback.png");
        backgroundImage = background.getImage();

        ImageIcon label111 = new ImageIcon("image/back11.png");
        label1 = label111.getImage();

        ImageIcon label12 = new ImageIcon("image/back22.png");
        label2 = label12.getImage();

        ImageIcon label13 = new ImageIcon("image/back33.png");
        label3 = label13.getImage();

        ImageIcon label14 = new ImageIcon("image/back44.png");
        label4 = label14.getImage();

        ImageIcon label15 = new ImageIcon("image/back441.png");
        label5 = label15.getImage();

        ImageIcon label16 = new ImageIcon("image/back55.png");
        label6 = label16.getImage();

        ImageIcon label17 = new ImageIcon("image/back661.png");
        label7 = label17.getImage();

        ImageIcon label18 = new ImageIcon("image/back662.png");
        label8 = label18.getImage();

        ImageIcon label19 = new ImageIcon("image/back663.png");
        label9 = label19.getImage();

        ImageIcon label20 = new ImageIcon("image/back664.png");
        label10 = label20.getImage();

        ImageIcon label21 = new ImageIcon("image/back665.png");
        label11 = label21.getImage();

        createUI();
    }

    //
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage,0, 0,getWidth(),getHeight(),this);
        }

        if (label1 != null) {
            g.drawImage(label1, 850, 50, 210, 280, this);
        }

        if (label2 != null) {
            g.drawImage(label2,50, 50, 180, 75, this);
        }

        if (label3 != null) {
            g.drawImage(label3, 50, 420, 180, 75, this);
        }

        if (label4 != null) {
            g.drawImage(label4, 290, 10, 480, 100, this);
        }

        if (label5 != null) {
            g.drawImage(label5, 470, 120, 140, 30, this);
        }

        if (label6  != null) {
            g.drawImage(label6, 470, 310, 140, 30, this);
        }

    }
    // =====================================
    // 게임 시작
    // =====================================

    public boolean startGame() {

        int userChips = TextDatabase.getUserChips();

        if (userChips < 100) {
            JOptionPane.showMessageDialog(
                this,
                "보유 칩이 부족합니다.\n현재 보유 칩: " + userChips,
                "게임 시작 불가",
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        manager = new IndianPokerManager(userChips);
        awaitingUserResponse = false;

        setActionButtonsEnabled(true);
        nextRoundLabel.setEnabled(false);

        setStatusText("당신의 행동을 선택하세요.");

        updateGameScreen();

        return true;
    }

    // =====================================
    // UI 구성
    // =====================================
    private void setStatusText(String message) {
        statusLabel.setText(
            "<html><div style='width:120px;'>" + message + "</div></html>"
        );
    }
    private void createUI() {
        computerCardLabel = new JLabel();
        computerCardLabel.setBounds(505, 155, 70, 105);
        computerCardLabel.setHorizontalAlignment(JLabel.CENTER);
        add(computerCardLabel);
        
        playerCardLabel = new JLabel();
        playerCardLabel.setBounds(505, 345, 70, 105);
        playerCardLabel.setHorizontalAlignment(JLabel.CENTER);
        add(playerCardLabel);

        computerChipsLabel = makeLabel("0", 12, 85, 250, 30, WHITE, 20);
        computerChipsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(computerChipsLabel);

        playerChipsLabel = makeLabel("0", 12, 455, 250, 30, WHITE, 20);
        playerChipsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(playerChipsLabel);

        potLabel = makeLabel("0", 438, 76, 272, 30, GOLD, 22);
        potLabel.setFont(new Font("Serif", Font.BOLD, 22));
        add(potLabel);

        // 게임 진행 상태
        statusLabel = makeLabel("당신의 행동을 선택하세요.", 810, 150, 300, 60, GOLD, 16);
        add(statusLabel);

        // 이미지 버튼 5개
        callLabel = makeImageButton(
            label7, "CALL", 150, 520, 150, 70,
            () -> playerCall()
        );

        raiseLabel = makeImageButton(
            label8, "RAISE", 310, 520, 150, 70,
            () -> playerRaise()
        );

        raiseAmountLabel = makeImageButton(
            label9, "RAISE AMOUNT", 470, 520, 150, 70,
            () -> chooseRaiseAmount()
        );

        foldLabel = makeImageButton(
            label10, "FOLD", 630, 520, 150, 70,
            () -> playerFold()
        );

        nextRoundLabel = makeImageButton(
            label11, "NEXT ROUND", 790, 520, 150, 70,
            () -> startNextRound()
        );

        // 게임 시작 전에는 조작 버튼 비활성화
        setActionButtonsEnabled(false);
        nextRoundLabel.setEnabled(false);

        JLabel label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_00.g2.setVisible(false);
                GUI_00.g0.setVisible(true);
                GUI_00.g.setVisible(true);
                GUI_00.g3.setVisible(false);
            }
        });
    }

    private JLabel makeLabel(
        String text,
        int x,
        int y,
        int width,
        int height,
        Color color,
        int fontSize
    ) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(color);
        label.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    // 이미지 JLabel을 클릭 가능한 버튼처럼 사용
    private JLabel makeImageButton(
        Image image,
        String fallbackText,
        int x,
        int y,
        int width,
        int height,
        Runnable action
    ) {
        JLabel button;

        if (image != null && new ImageIcon(image).getIconWidth() > 0) {
            Image scaledImage = image.getScaledInstance(
                width, height, Image.SCALE_SMOOTH
            );
            button = new JLabel(new ImageIcon(scaledImage));
        } else {
            // 이미지 파일을 찾지 못한 경우에도 버튼 기능을 확인할 수 있도록 대체 문구 표시
            button = new JLabel(fallbackText, JLabel.CENTER);
            button.setForeground(WHITE);
            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            button.setOpaque(true);
            button.setBackground(new Color(70, 55, 35));
        }

        button.setBounds(x, y, width, height);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(fallbackText);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (button.isEnabled()) {
                    action.run();
                }
            }
        });

        add(button);
        return button;
    }

    // label9를 누르면 레이즈 금액 선택 창 표시
    private void chooseRaiseAmount() {
        if (!isGameAvailable()) {
            return;
        }

        Integer[] amounts = {100, 500, 1000, 5000};

        Integer selected = (Integer) JOptionPane.showInputDialog(
            this,
            "레이즈 금액을 선택하세요.",
            "RAISE 금액 선택",
            JOptionPane.PLAIN_MESSAGE,
            null,
            amounts,
            selectedRaiseAmount
        );

        if (selected != null) {
            selectedRaiseAmount = selected;
            setStatusText("선택한 레이즈 금액: " + selectedRaiseAmount + "칩");
        }
    }

    // =====================================
    // 사용자 CALL
    // =====================================

    private void playerCall() {

        if (!isGameAvailable()) return;

        try {

            int callCost = Math.max(
                0,
                manager.getComputerBet() - manager.getPlayerBet()
            );

            int payment = Math.min(
                callCost,
                manager.getPlayer().getChips()
            );

            // 상대의 추가 베팅이 있을 때만 칩 지불
            if (payment > 0) {

                if (!manager.applyPlayerBet(payment)) {
                    showError("CALL 금액을 지불할 수 없습니다.");
                    return;
                }
            }

            // 컴퓨터가 레이즈한 뒤 사용자가 CALL한 경우
            if (awaitingUserResponse) {

                awaitingUserResponse = false;

                completeAndShowdown();
                return;
            }

            // 상대 베팅이 없으면 CHECK와 같은 의미
            setStatusText("컴퓨터가 행동을 결정하고 있습니다.");
            updateGameScreen();

            computerTurn();

        } catch (Exception ex) {
            showError("CALL 처리 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 사용자 RAISE
    // =====================================

    private void playerRaise() {

        if (!isGameAvailable()) return;

        try {

            int callCost = Math.max(
                0,
                manager.getComputerBet() - manager.getPlayerBet()
            );

            int raiseAmount = selectedRaiseAmount;

            // CALL 금액 + 추가 RAISE 금액
            int totalPayment = callCost + raiseAmount;

            if (totalPayment > manager.getPlayer().getChips()) {

                showError(
                    "보유 칩이 부족합니다.\n" +
                    "필요한 칩: " + totalPayment + "\n" +
                    "보유 칩: " + manager.getPlayer().getChips()
                );

                return;
            }

            if (!manager.applyPlayerBet(totalPayment)) {
                showError("RAISE를 진행할 수 없습니다.");
                return;
            }

            awaitingUserResponse = false;

            setStatusText("컴퓨터가 RAISE에 응답하고 있습니다.");

            updateGameScreen();

            computerTurn();

        } catch (Exception ex) {
            showError("RAISE 처리 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 사용자 FOLD
    // =====================================

    private void playerFold() {

        if (!isGameAvailable()) return;

        try {

            Map<String, Object> result =
                manager.finishByFold(manager.getPlayer());

            awaitingUserResponse = false;

            finishRound(formatResult(result));

        } catch (Exception ex) {
            showError("FOLD 처리 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 컴퓨터 AI 행동
    // =====================================

    private void computerTurn() {

        if (!isGameAvailable()) return;

        try {

            DecisionResult decision = computerAI.decide(manager);

            if (decision == null || !decision.isSuccess()) {

                String errorMessage = decision == null
                    ? "컴퓨터의 판단 결과가 없습니다."
                    : decision.getErrorMessage();

                showError(errorMessage);
                return;
            }

            Action action = decision.getAction();

            switch (action) {

                // 컴퓨터 FOLD
                case FOLD:

                    Map<String, Object> foldResult =
                        manager.finishByFold(manager.getComputer());

                    awaitingUserResponse = false;

                    finishRound(formatResult(foldResult));

                    break;

                // 컴퓨터 CHECK
                case CHECK:

                    awaitingUserResponse = false;

                    completeAndShowdown();

                    break;

                // 컴퓨터 CALL
                case CALL:

                    if (!manager.applyComputerBet(decision.getAmount())) {
                        showError("컴퓨터 CALL 처리에 실패했습니다.");
                        return;
                    }

                    awaitingUserResponse = false;

                    updateGameScreen();

                    completeAndShowdown();

                    break;

                // 컴퓨터 RAISE
                case RAISE:

                    if (!manager.applyComputerBet(decision.getAmount())) {
                        showError("컴퓨터 RAISE 처리에 실패했습니다.");
                        return;
                    }

                    // 사용자가 다시 응답해야 함
                    awaitingUserResponse = true;

                    setStatusText("컴퓨터가 RAISE했습니다. 응답을 선택하세요.");

                    updateGameScreen();

                    break;

                default:

                    showError("알 수 없는 컴퓨터 행동입니다.");
                    break;
            }

        } catch (Exception ex) {
            showError("컴퓨터 행동 처리 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 베팅 종료 및 승패 판정
    // =====================================
    private String formatResult(Map<String, Object> result) {

        String winner = String.valueOf(result.get("winner"));
        String reason = String.valueOf(result.get("end_reason"));

        int pot = ((Number) result.get("pot")).intValue();

        int playerChips =
            ((Number) result.get("player_chips")).intValue();

        int computerChips =
            ((Number) result.get("computer_chips")).intValue();

        String winnerText;

        if ("PLAYER_WIN".equals(winner)) {
            winnerText = "당신의 승리!";
        } else if ("COMPUTER_WIN".equals(winner)) {
            winnerText = "컴퓨터의 승리!";
        } else {
            winnerText = "무승부!";
        }

        String reasonText;

        if ("SHOWDOWN".equals(reason)) {
            reasonText = "카드 비교 결과";
        } else {
            reasonText = "기권으로 인한 승리";
        }

        return winnerText
            + "\n\n판정 방식: " + reasonText
            + "\n획득 판돈: " + pot + "칩"
            + "\n\n사용자 잔여 칩: " + playerChips
            + "\n컴퓨터 잔여 칩: " + computerChips;
    }
    private void completeAndShowdown() {

        try {

            manager.completeBetting();

            Map<String, Object> result = manager.showdown();

            awaitingUserResponse = false;

            finishRound(formatResult(result));

        } catch (Exception ex) {
            showError("승패 판정 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 라운드 종료
    // =====================================

    private void finishRound(String message) {

        updateGameScreen();

        setStatusText("라운드 종료");

        setActionButtonsEnabled(false);

        nextRoundLabel.setEnabled(true);

        JOptionPane.showMessageDialog(
            this,
            message,
            "라운드 결과",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================
    // 다음 라운드
    // =====================================

    private void startNextRound() {

        if (manager == null) return;

        try {

            boolean started = manager.nextRound();

            if (!started) {

                updateGameScreen();

                nextRoundLabel.setEnabled(false);

                JOptionPane.showMessageDialog(
                    this,
                    "보유 칩이 부족하여 다음 라운드를 시작할 수 없습니다.",
                    "게임 종료",
                    JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            awaitingUserResponse = false;

            setActionButtonsEnabled(true);

            nextRoundLabel.setEnabled(false);

            setStatusText("새로운 라운드가 시작되었습니다.");

            updateGameScreen();

        } catch (Exception ex) {
            showError("다음 라운드 시작 중 오류가 발생했습니다.\n" + ex.getMessage());
        }
    }

    // =====================================
    // 화면 갱신
    // =====================================

    private void updateGameScreen() {

        if (manager == null) return;

        Map<String, Object> view = manager.getPlayerView();

        // 컴퓨터 카드
        Card.CardData computerCard =
            (Card.CardData) view.get("opponent_card");

        if (computerCard != null) {

            ImageIcon computerImage = getCardImage(computerCard);

            computerCardLabel.setIcon(resizeCard(computerImage));
        }

        // 사용자 카드
        Card.CardData playerCard =
            (Card.CardData) view.get("own_card");

        ImageIcon playerImage;

        if (playerCard == null) {
            playerImage = getBackCardImage();
        } else {
            playerImage = getCardImage(playerCard);
        }

        playerCardLabel.setIcon(resizeCard(playerImage));

        // 칩 갱신
        int playerChips =
            ((Number) view.get("player_chips")).intValue();

        int computerChips =
            ((Number) view.get("computer_chips")).intValue();

        int pot =
            ((Number) view.get("pot")).intValue();

        playerChipsLabel.setText(
            ""+playerChips
        );

        computerChipsLabel.setText(
            "" + computerChips
        );

        potLabel.setText(
            "" + pot
        );

        // 사용자 칩 저장
        TextDatabase.saveUserChips(playerChips);

        repaint();
    }

    // =====================================
    // 버튼 활성화
    // =====================================

    private void setActionButtonsEnabled(boolean enabled) {

        callLabel.setEnabled(enabled);
        raiseLabel.setEnabled(enabled);
        raiseAmountLabel.setEnabled(enabled);
        foldLabel.setEnabled(enabled);
    }

    private boolean isGameAvailable() {

        if (manager == null || manager.isGameOver()) {
            return false;
        }

        if (manager.isBettingCompleted()) {
            return false;
        }

        return true;
    }

    // =====================================
    // 카드 이미지
    // =====================================

    private ImageIcon getCardImage(Card.CardData card) {

        int suit = Cardandimageswitch.suittonum(card.getSuit());

        int rank = Cardandimageswitch.ranktonum(card.getRank());

        return Cardandimageswitch.Card(suit, rank);
    }

    private ImageIcon getBackCardImage() {

        return Cardandimageswitch.BackCard();
    }

    private ImageIcon resizeCard(ImageIcon icon) {

        if (icon == null || icon.getIconWidth() <= 0) {
            return icon;
        }

        Image image = icon.getImage().getScaledInstance(70, 105,Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }

    // =====================================
    // 오류 메시지
    // =====================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "게임 오류",
            JOptionPane.ERROR_MESSAGE
        );
    }
}