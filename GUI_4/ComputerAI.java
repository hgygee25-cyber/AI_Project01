package GUI_4;

// [팀원 연동 필수 1] 팀원 파일 6개 상단에 'package game;' 추가 후 아래처럼 불러옴
import GUI_1.Card;
import GUI_1.GameState;
import GUI_1.Player;
import GUI_1.WinnerJudge;

import GUI_4.AvailableActionChecker;
import GUI_4.AvailableActionChecker.AvailableActions;
import GUI_4.GameStateValidator;
import GUI_4.GameStateValidator.ValidationResult;
import GUI_4.PotOddsCalculator;
import GUI_4.AllInHandler;
import GUI_4.RaiseAmountCalculator;
import GUI_4.RaiseValidator;
import GUI_4.ActionEvaluator;
import GUI_1.IndianPokerManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComputerAI {

    // 기존에 작성하신 하위 분석/판단/베팅 모듈 인스턴스화
    private final GameStateValidator stateValidator = new GameStateValidator();
    private final AvailableActionChecker actionChecker = new AvailableActionChecker();
    private final PotOddsCalculator potOddsCalculator = new PotOddsCalculator();
    private final RaiseAmountCalculator raiseCalculator = new RaiseAmountCalculator();
    private final RaiseValidator raiseValidator = new RaiseValidator();
    private final AllInHandler allInHandler = new AllInHandler();
    private final ActionEvaluator actionEvaluator = new ActionEvaluator();

    // 시뮬레이션 횟수 (속도와 정확도의 균형점: 500회)
    private static final int SIMULATION_COUNT = 500;
    // 최소 레이즈 기준 금액 (기본 칩 50,000 기준 1,000으로 설정, 팀원과 협의 후 조정)
    private static final int DEFAULT_MIN_RAISE = 1000;

    /**
     * [팀원 연동 필수 2] GameManager에서 컴퓨터 턴이 왔을 때 호출하는 메인 메서드
     * 입력값: 현재 GameState, 컴퓨터 Player 객체, 상대방(사용자) Player 객체
     * 반환값: DecisionResult (행동 및 지불해야 할 총 금액 포함)
     */
    public DecisionResult decide(GameState gameState, Player computer, Player user) {
        if (gameState == null || computer == null || user == null) {
            return DecisionResult.failure("게임 상태 또는 플레이어 정보가 누락되었습니다.");
        }

        // 1. 콜 비용 및 칩 정보 계산
        // (상대가 이번 라운드에 낸 돈 - 컴퓨터가 낸 돈 = 맞춰야 할 콜 비용)
        int callCost = Math.max(0, user.getBetAmount() - computer.getBetAmount());
        int computerChips = computer.getChips();
        int userChips = user.getChips();
        int potSize = gameState.getPot();

        // 2. 몬테카를로 시뮬레이션으로 현재 승률(0.0 ~ 100.0) 계산
        double winRate = calculateWinRate(computer.getCards(), gameState.getCommunityCards());

        // 3. [Analysis 단계] 상태 유효성 검사
        ValidationResult validation = stateValidator.validateGameState(
                gameState, winRate, callCost, computerChips, userChips
        );
        if (!validation.isValid()) {
            return DecisionResult.failure(validation.getErrorMessage());
        }

        // 4. [Analysis 단계] 가능한 행동 및 팟 오즈 계산
        AvailableActions availableActions = actionChecker.checkAvailableActions(callCost, computerChips);
        double potOdds = potOddsCalculator.calculatePotOdds(potSize, callCost);

        
        // 5. [Betting 단계] 레이즈 가능 시 레이즈 금액 계산 및 검증
        int raiseAmount = 0;
        boolean isAllInRaise = false;
        boolean isIncompleteRaise = false;

        if (availableActions.canRaise()) {
            // 상대방이 콜 비용을 지불한 상태이므로 남은 보유 칩(userChips) 전체가 추가 대응 가능 한도임
            int opponentMatchable = userChips;
            int minRaise = Math.max(DEFAULT_MIN_RAISE, callCost);

            int calculatedRaise = raiseCalculator.calculateRaiseAmount(
                    potSize, callCost, computerChips, opponentMatchable, minRaise
            );

            if (raiseValidator.validateRaiseAmount(calculatedRaise, callCost, computerChips, opponentMatchable, minRaise)) {
                raiseAmount = calculatedRaise;
                isAllInRaise = allInHandler.handleAllIn(computerChips, callCost, raiseAmount, opponentMatchable);
                isIncompleteRaise = isAllInRaise && (raiseAmount < minRaise);
            }
        }


        // 6. [Decision 단계] 최종 행동 결정
        DecisionResult result = actionEvaluator.evaluateAction(
                winRate, potOdds, callCost, availableActions, raiseAmount, isAllInRaise, isIncompleteRaise
        );

        // 7. [예외 보완] ActionEvaluator에서 일반 CALL이 안 되어 올인 CALL로 넘어온 경우 처리
        if (!result.isSuccess() && availableActions.canAllInCall()) {
            return DecisionResult.success(Action.CALL, computerChips, true, false);
        }

        return result;
    }

    /**
     * 인디언 포커 전용 컴퓨터 행동 결정
     */
    public DecisionResult decide(IndianPokerManager manager) {

        if (manager == null) {
            return DecisionResult.failure("게임 정보가 없습니다.");
        }

        Player computer = manager.getComputer();
        Player user = manager.getPlayer();

        // 컴퓨터는 상대방에게 공개된 카드만 확인한다.
        Card.CardData opponentCard =
                (Card.CardData) manager.getComputerView()
                        .get("opponent_card");

        if (opponentCard == null) {
            return DecisionResult.failure("상대방 카드가 없습니다.");
        }

        int computerChips = computer.getChips();
        int userChips = user.getChips();

        // 앤티를 제외한 실제 베팅 금액으로 콜 비용 계산
        int callCost = Math.max(
                0,
                manager.getPlayerBet() - manager.getComputerBet()
        );

        int potSize = manager.getPot();

        // 인디언 포커 전용 확률 계산
        double winRate = calculateIndianPokerWinRate(opponentCard);

        // 게임 상태 검증
        ValidationResult validation =
                stateValidator.validateIndianPoker(
                        manager,
                        winRate,
                        callCost,
                        computerChips,
                        userChips
                );

        if (!validation.isValid()) {
            return DecisionResult.failure(
                    validation.getErrorMessage()
            );
        }

        // 가능한 행동 확인
        AvailableActions availableActions =
                actionChecker.checkAvailableActions(
                        callCost,
                        computerChips
                );

        // 팟 오즈 계산
        double potOdds =
                potOddsCalculator.calculatePotOdds(
                        potSize,
                        callCost
                );

        // 레이즈 금액 계산
        int raiseAmount = 0;
        boolean isAllInRaise = false;
        boolean isIncompleteRaise = false;

        if (availableActions.canRaise()) {

            int opponentMatchable = userChips;
            int minRaise = Math.max(
                    DEFAULT_MIN_RAISE,
                    callCost
            );

            int calculatedRaise =
                    raiseCalculator.calculateRaiseAmount(
                            potSize,
                            callCost,
                            computerChips,
                            opponentMatchable,
                            minRaise
                    );

            if (calculatedRaise > 0
                    && raiseValidator.validateRaiseAmount(
                            calculatedRaise,
                            callCost,
                            computerChips,
                            opponentMatchable,
                            minRaise
                    )) {

                raiseAmount = calculatedRaise;

                isAllInRaise = allInHandler.handleAllIn(
                        computerChips,
                        callCost,
                        raiseAmount,
                        opponentMatchable
                );

                isIncompleteRaise =
                        isAllInRaise && raiseAmount < minRaise;
            }
        }

        // 최종 행동 결정
        DecisionResult result =
                actionEvaluator.evaluateAction(
                        winRate,
                        potOdds,
                        callCost,
                        availableActions,
                        raiseAmount,
                        isAllInRaise,
                        isIncompleteRaise
                );

        // 올인 CALL 처리
        if (!result.isSuccess()
                && availableActions.canAllInCall()) {

            return DecisionResult.success(
                    Action.CALL,
                    computerChips,
                    true,
                    false
            );
        }

        return result;
    }


    /**
     * 인디언 포커 전용 기대 지분율 계산
     *
     * 상대방 공개 카드 한 장을 제외한
     * 나머지 51장을 기준으로 계산한다.
     */
    private double calculateIndianPokerWinRate(
            Card.CardData opponentCard
    ) {

        int opponentValue =
                getIndianPokerCardValue(opponentCard.getRank());

        if (opponentValue < 2 || opponentValue > 14) {
            return Double.NaN;
        }

        int higherCards = (14 - opponentValue) * 4;

        // 같은 숫자의 카드는 공개된 한 장을 제외하고 3장 남음
        double expectedPoints = higherCards + 3 * 0.5;

        return expectedPoints / 51.0 * 100.0;
    }


    /**
     * 인디언 포커 카드 숫자 변환
     */
    private int getIndianPokerCardValue(String rank) {

        switch (rank) {
            case "A": return 14;
            case "K": return 13;
            case "Q": return 12;
            case "J": return 11;

            default:
                try {
                    return Integer.parseInt(rank);
                } catch (NumberFormatException e) {
                    return -1;
                }
        }
    }


    /**
     * 몬테카를로 시뮬레이션 기반 승률 계산 (0.0 ~ 100.0 백분율 반환)
     * 팀원의 WinnerJudge.compare()를 직접 호출하여 정확도 100% 보장
     */
    private double calculateWinRate(List<Card.CardData> myCards, List<Card.CardData> communityCards) {
        if (myCards == null || myCards.size() != 2) {
            return 0.0;
        }

        // 1. 이미 공개된 카드(내 카드 2장 + 커뮤니티 카드)를 제외한 나머지 덱 생성
        List<Card.CardData> remainingDeck = createRemainingDeck(myCards, communityCards);
        int neededCommunityCards = 5 - communityCards.size();
        double winPoints = 0.0;

        // 2. SIMULATION_COUNT(500회) 만큼 가상 대결 반복
        for (int i = 0; i < SIMULATION_COUNT; i++) {
            Collections.shuffle(remainingDeck); // 남은 카드 섞기
            int deckIndex = 0;

            // 가상 상대방 카드 2장 뽑기
            List<Card.CardData> virtualOpponentCards = Arrays.asList(
                    remainingDeck.get(deckIndex++),
                    remainingDeck.get(deckIndex++)
            );

            // 부족한 커뮤니티 카드 채워서 총 5장 만들기
            List<Card.CardData> virtualCommunity = new ArrayList<>(communityCards);
            for (int j = 0; j < neededCommunityCards; j++) {
                virtualCommunity.add(remainingDeck.get(deckIndex++));
            }

            // [팀원 연동 필수 3] 팀원의 WinnerJudge를 호출해 가상 승패 판정
            // 주의: WinnerJudge.compare(playerCards, computerCards, communityCards) 순서 준수
            Map<String, Object> judgeResult = WinnerJudge.compare(
                    virtualOpponentCards, myCards, virtualCommunity
            );

            String winner = (String) judgeResult.get("winner");
            if ("COMPUTER_WIN".equals(winner)) {
                winPoints += 1.0;
            } else if ("DRAW".equals(winner)) {
                winPoints += 0.5; // 무승부는 0.5승으로 계산
            }
        }

        return (winPoints / SIMULATION_COUNT) * 100.0;
    }

    /**
     * 전체 52장 중 이미 사용된 카드를 제외한 남은 카드 목록 생성
     */
    private List<Card.CardData> createRemainingDeck(List<Card.CardData> myCards, List<Card.CardData> communityCards) {
        Set<String> usedKeys = new HashSet<>();
        for (Card.CardData c : myCards) {
            usedKeys.add(c.getSuit() + "_" + c.getRank());
        }
        for (Card.CardData c : communityCards) {
            usedKeys.add(c.getSuit() + "_" + c.getRank());
        }

        List<Card.CardData> deck = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                if (!usedKeys.contains(suit + "_" + rank)) {
                    deck.add(new Card.CardData(suit, rank));
                }
            }
        }
        return deck;
    }
}
