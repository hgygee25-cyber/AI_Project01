package GUI;

import javax.swing.*;
import java.util.Map;
import GUI_1.WinnerJudge;
import GUI_1.Card;
import GUI_1.GameState;
import GUI_1.Player;
import GUI_4.ComputerAI;
import java.util.List;
import GUI_4.Action;
import GUI_4.DecisionResult;

public  class TexasHoldem_Betting {
    public static  int[] stageUser = new int[4];
    public static  int[] stageCom = new int[4];
    public static boolean roundFinished;
    //
    // 컴퓨터 AI 객체
    private static final ComputerAI computerAI = new ComputerAI();

    // AI에 전달할 게임 정보
    private static class AIContext {
        GameState gameState;
        Player computer;
        Player user;
    }
    //
    // GUI 카드 번호를 AI 카드 데이터로 변환
    private static Card.CardData convertCard(String cardText) {

        String[] parts = cardText.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 카드 정보: " + cardText);
        }

        int suitNumber = Integer.parseInt(parts[0].trim());
        int rankNumber = Integer.parseInt(parts[1].trim());

        String[] suits = {
            "Spades", "Hearts", "Diamonds", "Clubs"
        };

        String[] ranks = {
            "A", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K"
        };

        if (suitNumber < 0 || suitNumber >= suits.length
                || rankNumber < 1 || rankNumber > ranks.length) {
            throw new IllegalArgumentException("카드 번호가 올바르지 않습니다.");
        }

        return new Card.CardData(
            suits[suitNumber],
            ranks[rankNumber - 1]
        );
    }
    //
    private static AIContext createAIContext(TexasHoldemMoney money) {
        if (money == null || TexasHoldem.choosed_card == null
                || TexasHoldem.choosed_card.size() < 7) {
            return null;
        }

        AIContext context = new AIContext();

        // 1. 컴퓨터 플레이어 생성
        context.computer = new Player(
            "COM",
            money.getComMoney() + money.getComBet()
        );

        if (money.getComBet() > 0) {
            context.computer.bet(money.getComBet());
        }

        // 2. 사용자 플레이어 생성

        context.user = new Player(
            "USER",
            money.getUserMoney() + money.getUserBet()
        );

        if (money.getUserBet() > 0) {
            context.user.bet(money.getUserBet());
        }

        // 3. 컴퓨터 카드 2장
        context.computer.receiveCard(
            convertCard(TexasHoldem.choosed_card.get(0))
        );
        context.computer.receiveCard(
            convertCard(TexasHoldem.choosed_card.get(1))
        );

        // 4. 사용자 카드 2장
        context.user.receiveCard(
            convertCard(TexasHoldem.choosed_card.get(2))
        );
        context.user.receiveCard(
            convertCard(TexasHoldem.choosed_card.get(3))
        );

        // 5. 게임 상태 생성
        context.gameState = new GameState();

        if (TexasHoldem.count == 1) {
            context.gameState.setStage(GameState.Stage.FLOP);
        } else if (TexasHoldem.count == 2) {
            context.gameState.setStage(GameState.Stage.TURN);
        } else {
            context.gameState.setStage(GameState.Stage.RIVER);
        }

        // 6. 커뮤니티 카드 추가
        for (int i = 4; i < TexasHoldem.choosed_card.size(); i++) {

            context.gameState.addCommunityCard(
                convertCard(TexasHoldem.choosed_card.get(i))
            );
        }

        // 7. 현재 팟 금액 전달
        if (money.getPot() > 0) {
            context.gameState.addPot(money.getPot());
        }

        return context;
    }
    //
    public TexasHoldem_Betting() {}

    public static void reset() {
        for (int i = 0; i < 4; i++) {
            stageUser[i] = 0;
            stageCom[i] = 0;
        }
        roundFinished = false;
        if (GUI_00.g4_money != null) GUI_00.g4_money.resetAmounts();
    }

    public static void startAfterPreflop() {
        TexasHoldemMoney money = TexasHoldem.money;
        if (money == null || money.isGameEnded()) return;
        if (money.getUserMoney() < 1000 || money.getComMoney() < 1000) {
            JOptionPane.showMessageDialog(GUI_00.g4_donon, "양쪽 모두 최소 1,000칩이 필요합니다.");
            return;
        }
        if (!money.betCom(1000) || !money.betUser(1000)) return;
        stageCom[0] = 1000;
        stageUser[0] = 1000;
        updateDisplays();
        money.resetStreetBets();
        TexasHoldem.count = 1;
        GUI_00.g4_donon.setVisible(false);
        GUI_00.g4_money.setVisible(true);
        TexasHoldem_Basic_card.community_card();
        TexasHoldem.percent();
        
        if (!TexasHoldem.userFirst) {
            computerDecision();
        }

        enableActions(true);
    }

    public static void foldBeforeFlop() {
        TexasHoldemMoney money = TexasHoldem.money;
       
        if (money == null || money.isGameEnded()) return;
       
        GUI_00.g4_donon.setVisible(false);
        money.settle("COMPUTER_WIN", "FOLD", 0.0);
        TexasHoldem_End.showResult("COMPUTER_WIN", "FOLD", "- ");
        finishHand(null);
    }

    public static void call() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        int due = money.getComBet() - money.getUserBet();
        if (due <= 0) {
            JOptionPane.showMessageDialog(GUI_00.g4, "콜할 금액이 없습니다. CHECK 또는 RAISE를 선택하세요.");
            return;
        }
        if (due > money.getUserMoney()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "보유 칩이 부족합니다. 현재 버전에서는 부족한 금액의 부분 콜을 지원하지 않습니다.");
            return;
        }
        if (!money.callUser()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "콜 처리에 실패했습니다.");
            return;
        }
        addCurrentStage(0, due);
        completeStreet();
    }

    public static void check() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;

        if (money.getUserBet() != money.getComBet()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "상대 베팅 금액이 있습니다. CALL 또는 FOLD를 선택하세요.");
            return;
        }

        if (TexasHoldem.userFirst) {
            int beforeBet = money.getComBet();
            computerDecision();
        if (money.getComBet() > beforeBet) {
                enableActions(true);
                return;
            }
        }

        completeStreet();
    }

    public static void raise() {

        TexasHoldemMoney money = currentMoney();
        if (money == null) return;

        String input = JOptionPane.showInputDialog(
            GUI_00.g4,
            "추가로 베팅할 금액을 입력하세요.",
            "1,000"
        );

        if (input == null) return;

        int amount;

        try {
            amount = Integer.parseInt(input.replace(",", "").trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(GUI_00.g4, "숫자로 입력해 주세요.");
            return;
        }

        if (amount < 1000 || amount > money.getUserMoney()) {
            JOptionPane.showMessageDialog(
                GUI_00.g4,
                "최소 1,000칩부터 보유 칩 이하로 입력해 주세요."
            );
            return;
        }

        int callDue = Math.max(
            0,
            money.getComBet() - money.getUserBet()
        );

        if (callDue + amount > money.getUserMoney()) {
            JOptionPane.showMessageDialog(
                GUI_00.g4,
                "콜 금액과 추가 베팅 금액의 합이 보유 칩을 초과합니다."
            );
            return;
        }

        // 사용자 콜 금액 먼저 처리
        if (callDue > 0 && !money.callUser()) return;

        // 사용자 레이즈 실행
        if (!money.betUser(amount)) return;

        addCurrentStage(1, callDue + amount);

        // 컴퓨터 AI가 사용자 레이즈에 응답
        computerDecision();

        if (money.isGameEnded()) return;

        // 컴퓨터가 콜했다면 다음 단계로 진행
        if (money.isBetMatched()) {
            completeStreet();
        } else {
            // 컴퓨터가 레이즈했다면 사용자에게 다시 선택권 제공
            enableActions(true);
        }
    }

    public static void fold() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        int answer = JOptionPane.showConfirmDialog(GUI_00.g4, "이번 판을 포기하시겠습니까? 현재 팟은 컴퓨터가 가져갑니다.","FOLD", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        money.settle("COMPUTER_WIN", "FOLD", TexasHoldem.probabilityGraph.userTarget);
        TexasHoldem_End.showResult("COMPUTER_WIN", "FOLD", "- ");
        finishHand(null);
    }

    public static void allIn() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;

        int totalToMatch = money.getUserBet() + money.getUserMoney();

        if (totalToMatch < money.getComBet()
                || totalToMatch > money.getComBet() + money.getComMoney()) {

            JOptionPane.showMessageDialog(
                GUI_00.g4,
                "현재 버전은 사이드 팟을 지원하지 않습니다. "
                + "컴퓨터가 전액 콜할 수 있는 금액으로 올인해 주세요."
            );

            return;
        }

        int amount = money.getUserMoney();

        if (amount <= 0 || !money.allInUser()) return;

        addCurrentStage(3, amount);

        // 컴퓨터 AI가 올인에 대응
        computerDecision();

        if (money.isGameEnded()) return;

        // 양쪽 베팅이 일치하면 다음 단계 진행
        if (money.isBetMatched()) {
            runOutBoard();
        } else {
            // AI가 레이즈한 경우 사용자에게 다시 선택권 제공
            enableActions(true);
        }
    }

    public static TexasHoldemMoney currentMoney() {
        TexasHoldemMoney money = TexasHoldem.money;
        if (money == null || money.isGameEnded() || roundFinished) return null;
        return money;
    }

    // kind: 0=콜, 1=사용자 레이즈, 2=컴퓨터 콜, 3=사용자 올인, 4=컴퓨터 콜
    public static void addCurrentStage(int kind, int amount) {
        int index = Math.min(3, Math.max(1, TexasHoldem.count));
        if (kind == 0 || kind == 1 || kind == 3) stageUser[index] += amount;
        else stageCom[index] += amount;
        updateDisplays();
    }

    public static void completeStreet() {
        TexasHoldemMoney money = TexasHoldem.money;
        if (money == null || money.isGameEnded()) return;

        if (!money.isBetMatched()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "양쪽 베팅 금액이 일치하지 않습니다.");
            return;
        }

        // 현재 라운드 베팅 초기화
        money.resetStreetBets();

        // 플랍 → 턴
        if (TexasHoldem.count == 1) {
            TexasHoldem.count = 2;
            TexasHoldem_Basic_up_card.community_card();
            TexasHoldem.percent();
        }

        // 턴 → 리버
        else if (TexasHoldem.count == 2) {
            TexasHoldem.count = 3;
            TexasHoldem_Basic_upup_card.community_card();
            TexasHoldem.percent();
        }

        // 리버 → 쇼다운
        else if (TexasHoldem.count == 3) {
            showdown();
            return;
        }

        enableActions(true);
    }

public static void computerDecision() {

    TexasHoldemMoney money = TexasHoldem.money;

    if (money == null || money.isGameEnded()) return;

    try {

        AIContext context = createAIContext(money);

        if (context == null) {
            System.out.println("AI 게임 정보를 생성할 수 없습니다.");
            return;
        }

        DecisionResult result = computerAI.decide(
            context.gameState,
            context.computer,
            context.user
        );

        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(
                GUI_00.g4,
                "AI 판단 오류: " + result.getErrorMessage()
            );
            return;
        }

        Action action = result.getAction();
        int amount = result.getAmount();

        System.out.println("===== COMPUTER AI =====");
        System.out.println("선택 행동: " + action);
        System.out.println("지불 금액: " + amount);
        System.out.println("올인 여부: " + result.isAllIn());

        switch (action) {

            case FOLD:

                money.settle(
                    "PLAYER_WIN",
                    "FOLD",
                    TexasHoldem.probabilityGraph.userTarget
                );

                TexasHoldem_End.showResult(
                    "PLAYER_WIN",
                    "FOLD",
                    "-"
                );

                finishHand(null);
                return;

            case CHECK:

                if (money.getUserBet() != money.getComBet()) {
                    JOptionPane.showMessageDialog(
                        GUI_00.g4,
                        "AI CHECK 오류: 베팅 금액이 일치하지 않습니다."
                    );
                    return;
                }

                System.out.println("컴퓨터 CHECK");
                break;

            case CALL:

                if (amount <= 0 || amount > money.getComMoney()) {
                    JOptionPane.showMessageDialog(
                        GUI_00.g4,
                        "AI CALL 금액이 올바르지 않습니다."
                    );
                    return;
                }

                if (!money.betCom(amount)) return;

                addCurrentStage(2, amount);

                System.out.println("컴퓨터 CALL");
                break;

            case RAISE:

                if (amount <= 0 || amount > money.getComMoney()) {
                    JOptionPane.showMessageDialog(
                        GUI_00.g4,
                        "AI RAISE 금액이 올바르지 않습니다."
                    );
                    return;
                }

                if (!money.betCom(amount)) return;

                addCurrentStage(2, amount);

                System.out.println("컴퓨터 RAISE");

                // 사용자가 다시 대응해야 함
                enableActions(true);
                return;
        }

        // 중요:
        // 여기서 completeStreet()를 호출하지 않는다.
        // 다음 단계 진행은 call(), raise(), check()에서 처리한다.

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
            GUI_00.g4,
            "컴퓨터 AI 실행 중 오류가 발생했습니다: "
                + ex.getMessage()
        );

        ex.printStackTrace();
    }
}
    public static void runOutBoard() {
        TexasHoldemMoney money = TexasHoldem.money;
        if (money == null) return;
        money.resetStreetBets();
        if (TexasHoldem.count == 1) {
            TexasHoldem.count = 2;
            TexasHoldem_Basic_up_card.community_card();
            TexasHoldem.percent();
        }
        if (TexasHoldem.count == 2) {
            TexasHoldem.count = 3;
            TexasHoldem_Basic_upup_card.community_card();
            TexasHoldem.percent();
        }
        // 마지막 카드 애니메이션이 끝난 뒤 최종 결과 화면으로 전환한다.
        Timer finishTimer = new Timer(850, e -> showdown());
        finishTimer.setRepeats(false);
        finishTimer.start();
    }

public static void showdown() {

    TexasHoldemMoney money = TexasHoldem.money;

    if (money == null || money.isGameEnded()) {
        return;
    }

    try {

        System.out.println("===== SHOWDOWN 시작 =====");

        System.out.println("USER 카드: " + TexasHoldem.usercards);
        System.out.println("COM 카드: " + TexasHoldem.comcards);
        System.out.println("커뮤니티 카드: " + TexasHoldem.communitycards);

        if (TexasHoldem.usercards == null ||
            TexasHoldem.comcards == null ||
            TexasHoldem.communitycards == null ||
            TexasHoldem.usercards.size() != 2 ||
            TexasHoldem.comcards.size() != 2 ||
            TexasHoldem.communitycards.size() != 5) {

            throw new IllegalStateException("최종 카드 9장이 정상적으로 준비되지 않았습니다.");
        }

        Map<String, Object> result = WinnerJudge.compare(
            TexasHoldem.usercards,
            TexasHoldem.comcards,
            TexasHoldem.communitycards
        );

        String winner = (String) result.get("winner");

        Map<?, ?> playerHand = (Map<?, ?>) result.get("player_hand");
        Map<?, ?> computerHand = (Map<?, ?>) result.get("computer_hand");

        String playerName = String.valueOf(playerHand.get("name"));
        String computerName = String.valueOf(computerHand.get("name"));

        System.out.println("승자: " + winner);
        System.out.println("USER 족보: " + playerName);
        System.out.println("COM 족보: " + computerName);

        money.settle(
            winner,
            playerName,
            TexasHoldem.probabilityGraph.userTarget
        );

        if (!money.isGameEnded()) {
            throw new IllegalStateException("게임 정산이 완료되지 않았습니다.");
        }

        finishHand(null);

        SwingUtilities.invokeLater(() -> {
            TexasHoldem_End.showResult(
                winner,
                playerName,
                computerName
            );
        });

        System.out.println("===== SHOWDOWN 종료 =====");

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
            GUI_00.g4,
            "최종 승패 판정 오류: " + ex.getMessage()
        );

        ex.printStackTrace();
    }
}
    public static void finishHand(String message) {
        roundFinished = true;
        enableActions(false);
        if (TexasHoldem.money != null) {
            if (GUI_00.g4_money_user != null) {
                GUI_00.g4_money_user.updateMoney(TexasHoldem.money.getUserMoney());
            }

            if (GUI_00.g4_money_com != null) {
                GUI_00.g4_money_com.updateMoney(TexasHoldem.money.getComMoney());
            }
        }
    }

    public static void updateDisplays() {
        if (GUI_00.g4_money != null) {
            for (int i = 0; i < 4; i++) {
                GUI_00.g4_money.setStageMoney(
                    i, stageCom[i], stageUser[i]
                );
            }
        }

        if (TexasHoldem.money != null) {
            if (GUI_00.g4_money_user != null) {
                GUI_00.g4_money_user.updateMoney(
                    TexasHoldem.money.getUserMoney()
                );
            }

            if (GUI_00.g4_money_com != null) {
                GUI_00.g4_money_com.updateMoney(
                    TexasHoldem.money.getComMoney()
                );
            }
        }
    }

    public static void enableActions(boolean enabled) {
        TexasHoldem.setUserActionsEnabled(enabled);
    }
}