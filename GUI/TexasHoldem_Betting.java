package GUI;

import javax.swing.*;
import java.util.Map;
import GUI_1.WinnerJudge;

/** TexasHoldem 전용 베팅/라운드 진행 컨트롤러. Practice 클래스는 사용하지 않는다. */
public  class TexasHoldem_Betting {
    public static  int[] stageUser = new int[4];
    public static  int[] stageCom = new int[4];
    public static boolean roundFinished;

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
        computerDecision();
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
        // 컴퓨터가 이미 건 금액에 맞춰 사용자가 콜했으므로 라운드 베팅이 완료된다.
        completeStreet();
    }

    public static void check() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        if (money.getUserBet() != money.getComBet()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "상대 베팅 금액이 있습니다. CALL 또는 FOLD를 선택하세요.");
            return;
        }
        // 간단한 컴퓨터 플레이: 사용자가 체크하면 컴퓨터도 체크한다.
        completeStreet();
    }

    public static void raise() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        String input = JOptionPane.showInputDialog(GUI_00.g4,
                "추가로 베팅할 금액을 입력하세요.", "1,000");
        if (input == null) return;
        int amount;
        try {
            amount = Integer.parseInt(input.replace(",", "").trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(GUI_00.g4, "숫자로 입력해 주세요.");
            return;
        }
        if (amount < 1000 || amount > money.getUserMoney()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "최소 1,000칩부터 보유 칩 이하로 입력해 주세요.");
            return;
        }
        int callDue = Math.max(0, money.getComBet() - money.getUserBet());
        if (callDue + amount > money.getUserMoney()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "콜 금액과 추가 베팅 금액의 합이 보유 칩을 초과합니다.");
            return;
        }
        int projectedUserBet = money.getUserBet() + callDue + amount;
        int projectedComputerDue = projectedUserBet - money.getComBet();
        if (projectedComputerDue > money.getComMoney()) {
            JOptionPane.showMessageDialog(GUI_00.g4, "컴퓨터가 해당 금액을 콜할 수 없습니다. 더 낮은 금액으로 레이즈해 주세요.");
            return;
        }
        if (callDue > 0 && !money.callUser()) return;
        if (!money.betUser(amount)) return;
        addCurrentStage(1, callDue + amount);
        int computerDue = money.getUserBet() - money.getComBet();
        if (computerDue > 0) {
            money.betCom(computerDue);
            addCurrentStage(2, computerDue);
        }
        completeStreet();
    }

    public static void fold() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        int answer = JOptionPane.showConfirmDialog(GUI_00.g4,
                "이번 판을 포기하시겠습니까? 현재 팟은 컴퓨터가 가져갑니다.",
                "FOLD", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        money.settle("COMPUTER_WIN", "FOLD", TexasHoldem.probabilityGraph.userTarget);
        TexasHoldem_End.showResult("COMPUTER_WIN", "FOLD", "- ");
        finishHand(null);
    }

    public static void allIn() {
        TexasHoldemMoney money = currentMoney();
        if (money == null) return;
        int callDue = Math.max(0, money.getComBet() - money.getUserBet());
        int totalToMatch = money.getUserBet() + money.getUserMoney();
        if (totalToMatch < money.getComBet()
                || totalToMatch > money.getComBet() + money.getComMoney()) {
            JOptionPane.showMessageDialog(GUI_00.g4,
                    "현재 버전은 사이드 팟을 지원하지 않습니다. 컴퓨터가 전액 콜할 수 있는 금액으로 올인해 주세요.");
            return;
        }
        int amount = money.getUserMoney();
        if (amount <= 0 || !money.allInUser()) return;
        addCurrentStage(3, amount);
        int computerDue = money.getUserBet() - money.getComBet();
        if (computerDue > 0 && !money.betCom(computerDue)) return;
        if (computerDue > 0) addCurrentStage(4, computerDue);
        // 올인 이후 남은 커뮤니티 카드를 모두 공개하고 승패를 판정한다.
        runOutBoard();
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
        money.resetStreetBets();
        if (TexasHoldem.count == 1) {
            TexasHoldem.count = 2;
            TexasHoldem_Basic_up_card.community_card();
            TexasHoldem.percent();
            computerDecision();
        } else if (TexasHoldem.count == 2) {
            TexasHoldem.count = 3;
            TexasHoldem_Basic_upup_card.community_card();
            TexasHoldem.percent();
            computerDecision();
        } else {
            showdown();
            return;
        }
        enableActions(true);
    }

    // 단순 컴퓨터 전략: 현재 계산된 승률이 사용자보다 높으면 1,000칩 베팅,
    // 그렇지 않으면 CHECK한다. 복잡한 AI 전략이 아닌 기본 동작 모델이다.
    public static void computerDecision() {
        TexasHoldemMoney money = TexasHoldem.money;
        if (money == null || money.isGameEnded()) return;
        double userChance = TexasHoldem.probabilityGraph.userTarget;
        double comChance = TexasHoldem.probabilityGraph.comTarget;
        int stage = Math.min(3, Math.max(1, TexasHoldem.count));
        if (comChance > userChance && money.getComMoney() >= 1000) {
            if (money.betCom(1000)) {
                stageCom[stage] += 1000;
                updateDisplays();
            }
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
        if (money == null || money.isGameEnded()) return;
        try {
            Map<String, Object> result = WinnerJudge.compare(
                    TexasHoldem.usercards, TexasHoldem.comcards, TexasHoldem.communitycards);
            String winner = (String) result.get("winner");
            Map<?, ?> playerHand = (Map<?, ?>) result.get("player_hand");
            Map<?, ?> computerHand = (Map<?, ?>) result.get("computer_hand");
            String playerName = String.valueOf(playerHand.get("name"));
            String computerName = String.valueOf(computerHand.get("name"));
            money.settle(winner, playerName, TexasHoldem.probabilityGraph.userTarget);
            TexasHoldem_End.showResult(winner, playerName, computerName);
            finishHand(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(GUI_00.g4, "최종 승패 판정에 실패했습니다: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void finishHand(String message) {
        roundFinished = true;
        enableActions(false);
        if (GUI_00.g4_money_user != null && TexasHoldem.money != null)
            GUI_00.g4_money_user.updateMoney(TexasHoldem.money.getUserMoney());
        if (message != null) JOptionPane.showMessageDialog(GUI_00.g4, message);
    }

    public static void updateDisplays() {
        if (GUI_00.g4_money == null) return;
        for (int i = 0; i < 4; i++) GUI_00.g4_money.setStageMoney(i, stageCom[i], stageUser[i]);
        if (GUI_00.g4_money_user != null && TexasHoldem.money != null)
            GUI_00.g4_money_user.updateMoney(TexasHoldem.money.getUserMoney());
    }

    public static void enableActions(boolean enabled) {
        TexasHoldem.setUserActionsEnabled(enabled);
    }
}
