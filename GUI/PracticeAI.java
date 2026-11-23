
package GUI;

import GUI_1.Card;
import GUI_1.GameState;
import GUI_1.Player;
import GUI_4.Action;
import GUI_4.ComputerAI;
import GUI_4.DecisionResult;

import java.util.ArrayList;
import java.util.List;

public class PracticeAI {

    public enum Difficulty {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }

    private static Difficulty difficulty = Difficulty.INTERMEDIATE;

    private static DecisionResult lastDecision;

    private static final ComputerAI computerAI = new ComputerAI();

    // 마지막 AI 판단 결과
    public static DecisionResult getLastDecision() {
        return lastDecision;
    }

    // 난이도 설정
    public static void setDifficulty(Difficulty level) {
        if (level != null) {
            difficulty = level;
        }
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    // 난이도별 판단 적용
    private static DecisionResult applyDifficulty(
            DecisionResult result,
            GameState gameState,
            Player computer,
            Player user) {

        if (result == null || !result.isSuccess()) {
            return result;
        }

        if (result.isAllIn()) {
            return result;
        }

        // 초급: 레이즈 대신 콜 또는 체크
        if (difficulty == Difficulty.BEGINNER) {

            if (result.getAction() == Action.RAISE) {

                int callCost =
                    Math.max(
                        0,
                        user.getBetAmount() - computer.getBetAmount()
                    );

                if (callCost > 0) {
                    return DecisionResult.success(
                        Action.CALL,
                        callCost
                    );
                }

                return DecisionResult.success(
                    Action.CHECK,
                    0
                );
            }

            return result;
        }

        // 중급: 기본 AI 판단
        if (difficulty == Difficulty.INTERMEDIATE) {
            return result;
        }

        // 고급: 기본 AI 판단
        if (difficulty == Difficulty.ADVANCED) {
            return result;
        }

        return result;
    }

    /**
     * 연습 모드 컴퓨터 행동 결정
     *
     * stage:
     * 1 = FLOP
     * 2 = TURN
     * 3 = RIVER
     */
    public static DecisionResult decide(int stage) {

        try {

            // 1. 게임 상태 생성
            GameState gameState = new GameState();

            if (stage == 1) {
                gameState.setStage(GameState.Stage.FLOP);
            } else if (stage == 2) {
                gameState.setStage(GameState.Stage.TURN);
            } else if (stage == 3) {
                gameState.setStage(GameState.Stage.RIVER);
            } else {
                lastDecision = DecisionResult.failure(
                    "잘못된 게임 단계입니다."
                );
                return lastDecision;
            }

            // 2. 카드 정보 확인
            List<Card.CardData> computerCards = new ArrayList<>();
            List<Card.CardData> communityCards = new ArrayList<>();

            if (Practice.choosed_card == null
                    || Practice.choosed_card.size() < 4) {

                lastDecision = DecisionResult.failure(
                    "카드 정보가 부족합니다."
                );

                return lastDecision;
            }

            // 컴퓨터 카드 2장
            System.out.println("===== 카드 데이터 확인 =====");
for (int i = 0; i < Practice.choosed_card.size(); i++) {
    System.out.println(i + "번 카드: [" + Practice.choosed_card.get(i) + "]");
}
System.out.println("===========================");
            computerCards.add(
                convertCard(Practice.choosed_card.get(0))
            );

            computerCards.add(
                convertCard(Practice.choosed_card.get(1))
            );

            // 커뮤니티 카드 수
            int communityCount;

            if (stage == 1) {
                communityCount = 3;
            } else if (stage == 2) {
                communityCount = 4;
            } else {
                communityCount = 5;
            }

            if (Practice.choosed_card.size() < 4 + communityCount) {

                lastDecision = DecisionResult.failure(
                    "커뮤니티 카드가 부족합니다."
                );

                return lastDecision;
            }

            // 커뮤니티 카드 변환
            for (int i = 0; i < communityCount; i++) {
                communityCards.add(
                    convertCard(
                        Practice.choosed_card.get(i + 4)
                    )
                );
            }

            // 3. 플레이어 생성
            int comBet = Practice_Betting.combetting;
            int userBet = Practice_Betting.userbetting;

            Player computer = new Player(
                "COM",
                Practice_Betting.commoney + comBet
            );

            Player user = new Player(
                "USER",
                Practice_Betting.usermoney + userBet
            );

            // 현재 베팅 금액 반영
            if (comBet > 0) {
                computer.bet(comBet);
            }

            if (userBet > 0) {
                user.bet(userBet);
            }

            // 컴퓨터 카드 등록
            for (Card.CardData card : computerCards) {
                computer.receiveCard(card);
            }

            // 4. 커뮤니티 카드 등록
            for (Card.CardData card : communityCards) {
                gameState.addCommunityCard(card);
            }

            // 5. 팟 크기 등록
            int potSize =
                Practice_Betting.comtotal
                + Practice_Betting.usertotal;

            if (potSize > 0) {
                gameState.addPot(potSize);
            }

            // 6. AI 판단
            DecisionResult result = computerAI.decide(
                gameState,
                computer,
                user
            );

            // 7. 난이도 적용
            if (result != null && result.isSuccess()) {
                result = applyDifficulty(
                    result,
                    gameState,
                    computer,
                    user
                );
            }

            // 8. 결과 저장
            lastDecision = result;

            // 9. 행동 버튼 표시
            if (result != null && result.isSuccess()) {
                displayAction(result);
            }

            return lastDecision;

        } catch (Exception e) {

            e.printStackTrace();

            lastDecision = DecisionResult.failure(
                "AI 실행 중 오류가 발생했습니다: "
                + e.getMessage()
            );

            return lastDecision;
        }
    }

    /**
     * 카드 문자열을 CardData로 변환
     *
     * 형식: suitIndex,rankIndex
     */
    private static Card.CardData convertCard(String cardData) {

        String[] parts = cardData.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                "잘못된 카드 형식"
            );
        }

        int suitIndex = Integer.parseInt(parts[0].trim());
        int rankIndex = Integer.parseInt(parts[1].trim());

        String[] suits = {
            "Spades",
            "Hearts",
            "Diamonds",
            "Clubs"
        };

        String[] ranks = {
            "A", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K"
        };

        if (suitIndex < 0 || suitIndex >= suits.length
                || rankIndex < 0 || rankIndex >= ranks.length) {

            throw new IllegalArgumentException(
                "카드 번호가 올바르지 않습니다."
            );
        }

        return new Card.CardData(
            suits[suitIndex],
            ranks[rankIndex]
        );
    }

    /**
     * 컴퓨터 행동 버튼 표시
     */
    private static void displayAction(DecisionResult result) {

        Action action = result.getAction();

        Practice.com_button1.setEnabled(
            action == Action.CALL
        );

        Practice.com_button2.setEnabled(
            action == Action.RAISE
        );

        Practice.com_button3.setEnabled(
            action == Action.FOLD
        );

        Practice.com_button4.setEnabled(
            result.isAllIn()
        );

        Practice.com_button5.setEnabled(
            action == Action.CHECK
        );
    }

    /**
     * AI 행동 설명
     */
    public static String getExplanation(DecisionResult result) {

        if (result == null || !result.isSuccess()) {
            return "AI 판단에 실패했습니다.";
        }

        if (result.isAllIn()) {
            return "보유 칩을 모두 사용하는 올인 판단입니다.";
        }

        switch (result.getAction()) {

            case FOLD:
                return "현재 승률과 팟 오즈를 고려해 폴드를 선택했습니다.";

            case CHECK:
                return "추가로 지불할 콜 비용이 없어 체크를 선택했습니다.";

            case CALL:
                return "예상 승률이 팟 오즈보다 높아 콜을 선택했습니다.";

            case RAISE:
                return "높은 예상 승률을 바탕으로 레이즈를 선택했습니다.";

            default:
                return "행동을 판단할 수 없습니다.";
        }
    }
}
