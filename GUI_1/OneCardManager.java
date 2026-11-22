package GUI_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneCardManager {

    // =====================================
    // ① 멤버 변수 및 게임 상수
    // =====================================

    private static final int INITIAL_CARD_COUNT = 5; // 초기 지급 카드 매수

    private List<Card.CardData> deck;         // 뽑을 카드 덱 (재활용 셔플을 위해 List로 관리)
    private List<Card.CardData> centerCards;  // 중앙에 제출된 카드 더미 (마지막 요소가 현재 공개 카드)

    private Player player1;                   // 플레이어 1 (사용자)
    private Player player2;                   // 플레이어 2 (상대방 또는 컴퓨터)
    private Player currentPlayer;             // 현재 턴을 진행 중인 플레이어
    private Player winner;                    // 최종 승리자 (진행 중이거나 무승부면 null)

    private boolean gameOver;                 // 게임 종료 여부 (true: 종료, false: 진행 중)
    private boolean isDraw;                   // 덱 완전 소진으로 인한 무승부 여부
    private boolean hasDrawnThisTurn;         // 이번 턴에 카드를 1장 뽑았는지 여부 (중복 뽑기 방지용)

    // =====================================
    // ② 생성자
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] 초기화된 OneCardManager 객체
     * [기능] 기본 선공(플레이어 1)으로 원카드 게임 객체 초기화
     */
    public OneCardManager() {
        startGame(1);
    }

    /*
     * [입력값] firstPlayerNumber (int) - 선공 플레이어 번호 (1 또는 2)
     * [출력값] 초기화된 OneCardManager 객체
     * [기능] 지정된 선공 플레이어로 원카드 게임 객체 초기화
     */
    public OneCardManager(int firstPlayerNumber) {
        startGame(firstPlayerNumber);
    }

    // =====================================
    // ③ 게임 초기화 및 선공 설정
    // =====================================

    /*
     * [입력값] firstPlayerNumber (int) - 선공 플레이어 번호 (1: Player1, 2: Player2)
     * [출력값] void
     *
     * [기능]
     * 1. 팀원의 Card 클래스로 52장 생성 및 셔플 후 내부 deck 리스트로 적재
     * 2. 플레이어 1, 2 객체 생성 및 상태 변수 초기화
     * 3. 선공 플레이어 설정 (setFirstPlayer)
     * 4. 각 플레이어에게 초기 카드 5장씩 배분 (dealInitialCards)
     * 5. 중앙 카드 1장 공개 (최초 중앙 카드는 특수 카드라도 효과 미발동)
     */
    public void startGame(int firstPlayerNumber) {
        Card baseDeck = new Card();
        baseDeck.shuffle();

        deck = new ArrayList<>();
        while (baseDeck.remainingCards() > 0) {
            deck.add(baseDeck.drawCard());
        }

        centerCards = new ArrayList<>();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");

        winner = null;
        gameOver = false;
        isDraw = false;
        hasDrawnThisTurn = false;

        setFirstPlayer(firstPlayerNumber);
        dealInitialCards();

        // 최초 중앙 카드 1장 공개 (특수 카드 효과 발동 안 함)
        centerCards.add(deck.remove(deck.size() - 1));
    }

    /*
     * [입력값] playerNumber (int) - 1 또는 2
     * [출력값] void
     * [예외] IllegalArgumentException - 1 또는 2 이외의 값 입력 시 발생
     * [기능] 게임 시작 전 선공 플레이어를 지정함
     */
    public void setFirstPlayer(int playerNumber) {
        if (playerNumber == 1) {
            currentPlayer = player1;
        } else if (playerNumber == 2) {
            currentPlayer = player2;
        } else {
            throw new IllegalArgumentException("선공 플레이어는 1 또는 2만 선택할 수 있습니다.");
        }
    }

    /*
     * [입력값] 없음
     * [출력값] void
     * [기능] 양 플레이어에게 초기 카드 5장씩 교대로 배분
     */
    public void dealInitialCards() {
        for (int i = 0; i < INITIAL_CARD_COUNT; i++) {
            player1.receiveCard(deck.remove(deck.size() - 1));
            player2.receiveCard(deck.remove(deck.size() - 1));
        }
    }

    // =====================================
    // ④ 카드 제출 처리
    // =====================================

    /*
     * [입력값]
     * cardIndex (int) - 현재 플레이어(currentPlayer) 손패에서 제출할 카드의 인덱스 (0부터 시작)
     *
     * [출력값]
     * Map<String, Object> - 제출 처리 결과 정보
     * - "success" (Boolean)          : 제출 성공 여부 (false면 규칙 위반으로 제출 거부됨)
     * - "message" (String)           : 처리 결과 안내 메시지
     * - "effect" (String)            : 발동된 특수 효과 ("NORMAL", "ATTACK_2", "ATTACK_3", "SKIP_TURN", "WIN", "DRAW_GAME")
     * - "played_card" (Card.CardData): 제출된 카드 (실패 시 null)
     * - "game_over" (Boolean)        : 게임 종료 여부
     * - "winner" (String)            : 승리자 이름 (종료 전이거나 무승부면 null)
     *
     * [기능]
     * 1. 제출 가능한 카드인지 OneCardJudge.canPlayCard()로 검증
     * 2. 손패에서 해당 카드를 제거하고 중앙 카드 더미(centerCards) 맨 위에 추가
     * 3. 손패가 0장이 되었는지 최우선 검사 (특수 카드여도 승리 판정 우선)
     * 4. 승리가 아닐 경우 OneCardJudge.applySpecialEffect() 호출하여 효과 실행 및 턴 전환
     */
    public Map<String, Object> playCard(int cardIndex) {
        Map<String, Object> response = new HashMap<>();

        if (gameOver) {
            response.put("success", false);
            response.put("message", "이미 종료된 게임입니다.");
            return response;
        }

        List<Card.CardData> hand = currentPlayer.getCards();
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            response.put("success", false);
            response.put("message", "잘못된 카드 선택입니다.");
            return response;
        }

        Card.CardData selectedCard = hand.get(cardIndex);
        Card.CardData topCenterCard = getTopCenterCard();

        // 1. 무늬 또는 숫자 일치 여부 판정
        if (!OneCardJudge.canPlayCard(selectedCard, topCenterCard)) {
            response.put("success", false);
            response.put("message", "중앙 카드와 무늬 또는 숫자가 일치하지 않습니다.");
            return response;
        }

        // 2. 손패에서 카드 제거 후 중앙 더미에 제출
        removeCardFromPlayer(currentPlayer, cardIndex);
        centerCards.add(selectedCard);

        // 3. [최우선 규칙] 마지막 카드가 특수 카드라도 보유 카드 0장이 되면 즉시 승리 판정
        if (checkWinner()) {
            endGame(currentPlayer, false);
            response.put("success", true);
            response.put("message", winner.getName() + " 승리!");
            response.put("effect", "WIN");
            response.put("played_card", selectedCard);
            response.put("game_over", true);
            response.put("winner", winner.getName());
            return response;
        }

        // 4. 특수 카드 효과 실행 및 턴 진행
        String effectResult = OneCardJudge.applySpecialEffect(selectedCard, this);

        response.put("success", true);
        // [개선] 공격 효과 중 덱이 완전히 고갈되어 무승부 종료된 경우 안내 메시지 분기
        response.put("message", "DRAW_GAME".equals(effectResult) 
                ? "덱이 모두 소진되어 무승부로 종료됩니다." 
                : "카드를 제출했습니다.");
        response.put("effect", effectResult);
        response.put("played_card", selectedCard);
        response.put("game_over", gameOver);
        response.put("winner", (winner != null) ? winner.getName() : null);

        return response;

    }


    // =====================================
    // ⑤ 카드 뽑기 및 턴 넘기기
    // =====================================

    /*
     * [입력값] 없음
     *
     * [출력값]
     * Map<String, Object> - 카드 뽑기 결과 정보
     * - "success" (Boolean)          : 뽑기 성공 여부 (이미 이번 턴에 뽑았으면 false)
     * - "message" (String)           : 처리 결과 안내 메시지
     * - "drawn_card" (Card.CardData) : 새로 뽑은 카드 (실패 또는 무승부 종료 시 null)
     * - "can_play_drawn" (Boolean)   : 방금 뽑은 카드를 즉시 제출할 수 있는지 여부
     * - "game_over" (Boolean)        : 덱 완전 소진으로 인한 무승부 종료 여부
     *
     * [기능]
     * 1. 이번 턴에 아직 카드를 뽑지 않았는지 검증 (한 턴에 1장만 뽑기 허용)
     * 2. 덱이 비어있다면 recycleDeck()으로 중앙 카드 더미 재활용 시도
     * 3. 재활용할 카드조차 없다면 무승부(endGame) 처리
     * 4. 카드를 1장 뽑아 손패에 추가하고, 즉시 제출 가능한지 여부(can_play_drawn) 반환
     *    (참고: 뽑은 후 제출 가능하면 playCard()를 호출하고, 낼 카드가 없으면 passTurn() 호출)
     */
    public Map<String, Object> drawCard() {
        Map<String, Object> response = new HashMap<>();

        if (gameOver) {
            response.put("success", false);
            response.put("message", "이미 종료된 게임입니다.");
            return response;
        }

        if (hasDrawnThisTurn) {
            response.put("success", false);
            response.put("message", "이번 턴에는 이미 카드를 뽑았습니다. 카드를 내거나 턴을 넘기세요.");
            return response;
        }

        Card.CardData drawn = pullOneCardFromDeck();

        // 덱과 중앙 카드가 모두 고갈되어 재활용 불가능한 경우 무승부 종료
        if (drawn == null) {
            endGame(null, true);
            response.put("success", false);
            response.put("message", "더 이상 뽑을 카드가 없어 무승부로 종료됩니다.");
            response.put("drawn_card", null);
            response.put("can_play_drawn", false);
            response.put("game_over", true);
            return response;
        }

        currentPlayer.receiveCard(drawn);
        hasDrawnThisTurn = true;

        boolean canPlay = OneCardJudge.canPlayCard(drawn, getTopCenterCard());

        response.put("success", true);
        response.put("message", canPlay ? "카드를 뽑았습니다. 즉시 제출할 수 있습니다." : "카드를 뽑았습니다. 제출 가능한 카드가 없으면 턴을 넘기세요.");
        response.put("drawn_card", drawn);
        response.put("can_play_drawn", canPlay);
        response.put("game_over", false);

        return response;
    }

    /*
     * [입력값] 없음
     * [출력값] boolean - true: 정상적으로 턴 넘김 / false: 아직 카드를 뽑지 않아 턴을 넘길 수 없음
     * [기능] 카드를 1장 뽑은 후에도 낼 카드가 없을 때 플레이어가 턴을 종료하기 위해 호출
     */
    public boolean passTurn() {
        if (gameOver || !hasDrawnThisTurn) {
            return false;
        }

        nextTurn();
        return true;
    }

    /*
     * [입력값] count (int) - 상대방에게 지급할 공격 카드 장수 (2 또는 3)
     * [출력값] boolean - true: 정상 지급 완료 / false: 도중 덱 완전 소진으로 무승부 종료됨
     * [기능] OneCardJudge에서 '2' 또는 'A' 공격 효과 발동 시 호출되어 상대방에게 카드를 지급함
     */
    public boolean drawCardsForOpponent(int count) {
        Player opponent = getOpponentPlayer();

        for (int i = 0; i < count; i++) {
            Card.CardData drawn = pullOneCardFromDeck();
            if (drawn == null) {
                endGame(null, true);
                return false;
            }
            opponent.receiveCard(drawn);
        }

        return true;
    }

    // =====================================
    // ⑥ 덱 재활용 및 내부 카드 이동 도우미
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] Card.CardData - 덱에서 뽑은 카드 1장 (재활용 후에도 카드가 전혀 없으면 null)
     * [기능] 덱이 비어있으면 recycleDeck()을 먼저 수행한 뒤 맨 위 카드 1장을 꺼냄
     */
    private Card.CardData pullOneCardFromDeck() {
        if (deck.isEmpty()) {
            boolean recycled = recycleDeck();
            if (!recycled) {
                return null;
            }
        }

        return deck.remove(deck.size() - 1);
    }

    /*
     * [입력값] 없음
     * [출력값] boolean - true: 중앙 카드 재활용 성공 / false: 재활용할 중앙 카드가 부족함
     *
     * [기능]
     * 1. 현재 중앙에 공개된 맨 마지막 카드 1장은 그대로 유지
     * 2. 나머지 버려진 중앙 카드들을 모두 deck으로 이동 후 셔플(Collections.shuffle)
     */
    public boolean recycleDeck() {
        if (centerCards.size() <= 1) {
            return false; // 맨 위 공개 카드 1장뿐이므로 재활용할 카드가 없음
        }

        Card.CardData topCard = centerCards.remove(centerCards.size() - 1);

        deck.addAll(centerCards);
        centerCards.clear();
        centerCards.add(topCard);

        Collections.shuffle(deck);
        return true;
    }

    /*
     * [입력값]
     * targetPlayer (Player) - 카드를 제거할 플레이어 객체
     * cardIndex (int)       - 제거할 카드의 인덱스
     * [출력값] void
     * [기능] 팀원의 Player.java에 개별 카드 삭제 메서드가 없으므로,
     *        해당 인덱스의 카드만 제외한 나머지 카드로 손패를 재구성함
     */
    private void removeCardFromPlayer(Player targetPlayer, int cardIndex) {
        List<Card.CardData> currentHand = targetPlayer.getCards();
        targetPlayer.reset();

        for (int i = 0; i < currentHand.size(); i++) {
            if (i != cardIndex) {
                targetPlayer.receiveCard(currentHand.get(i));
            }
        }
    }

    // =====================================
    // ⑦ 턴 전환 및 승리/종료 판정
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] void
     * [기능] 상대방 플레이어로 턴을 변경하고, 이번 턴 뽑기 상태(hasDrawnThisTurn)를 초기화
     */
    public void nextTurn() {
        currentPlayer = getOpponentPlayer();
        hasDrawnThisTurn = false;
    }

    /*
     * [입력값] 없음
     * [출력값] void
     * [기능] J 또는 K 특수 카드 효과로 인해 현재 플레이어가 한 번 더 진행할 수 있도록 뽑기 상태만 초기화
     */
    public void keepCurrentTurn() {
        hasDrawnThisTurn = false;
    }

    /*
     * [입력값] 없음
     * [출력값] boolean - true: 현재 플레이어의 손패가 0장이 되어 승리 조건 충족 / false: 미충족
     * [기능] 현재 플레이어의 보유 카드 수가 0장인지 확인
     */
    public boolean checkWinner() {
        return currentPlayer.getCards().isEmpty();
    }

    /*
     * [입력값]
     * winPlayer (Player) - 승리한 플레이어 (무승부 시 null)
     * drawFlag (boolean) - 무승부 여부 (true: 무승부, false: 정상 승패)
     * [출력값] void
     * [기능] 게임을 종료 상태로 전환하고 최종 승자 또는 무승부 상태를 확정함
     */
    public void endGame(Player winPlayer, boolean drawFlag) {
        this.winner = winPlayer;
        this.isDraw = drawFlag;
        this.gameOver = true;
    }

    // =====================================
    // ⑧ 화면(GUI) 연동용 상태 조회 및 Getter
    // =====================================

    /*
     * [입력값] 없음
     *
     * [출력값] Map<String, Object> - 화면(GUI) 갱신용 전체 게임 상태 데이터
     * - "top_center_card" (Card.CardData)     : 중앙에 공개된 맨 위 카드
     * - "deck_remaining" (Integer)            : 현재 뽑을 덱에 남은 카드 장수
     * - "current_player" (String)             : 현재 턴인 플레이어 이름
     * - "player1_cards" (List<Card.CardData>) : 플레이어 1 보유 카드 목록
     * - "player2_cards" (List<Card.CardData>) : 플레이어 2 보유 카드 목록
     * - "has_drawn_this_turn" (Boolean)       : 현재 턴에 카드를 이미 뽑았는지 여부
     * - "game_over" (Boolean)                 : 게임 종료 여부
     * - "is_draw" (Boolean)                   : 무승부 여부
     * - "winner" (String)                     : 최종 승자 이름 (없으면 null)
     *
     * [기능] GUI 화면에 카드, 턴 표시, 버튼 활성화 상태를 그리기 위한 통합 정보 반환
     */
    public Map<String, Object> getGameState() {
        Map<String, Object> state = new HashMap<>();

        state.put("top_center_card", getTopCenterCard());
        state.put("deck_remaining", deck.size());
        state.put("current_player", currentPlayer.getName());
        state.put("player1_cards", player1.getCards());
        state.put("player2_cards", player2.getCards());
        state.put("has_drawn_this_turn", hasDrawnThisTurn);
        state.put("game_over", gameOver);
        state.put("is_draw", isDraw);
        state.put("winner", (winner != null) ? winner.getName() : null);

        return state;
    }

    /* [입력값] 없음 | [출력값] Card.CardData - 중앙 카드 더미의 맨 위 카드 반환 */
    public Card.CardData getTopCenterCard() {
        return centerCards.get(centerCards.size() - 1);
    }

    /* [입력값] 없음 | [출력값] Player - 현재 턴 플레이어 객체 반환 */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /* [입력값] 없음 | [출력값] Player - 현재 턴이 아닌 상대방 플레이어 객체 반환 */
    public Player getOpponentPlayer() {
        return (currentPlayer == player1) ? player2 : player1;
    }

    /* [입력값] 없음 | [출력값] Player - 플레이어 1 객체 반환 */
    public Player getPlayer1() {
        return player1;
    }

    /* [입력값] 없음 | [출력값] Player - 플레이어 2 객체 반환 */
    public Player getPlayer2() {
        return player2;
    }

    /* [입력값] 없음 | [출력값] boolean - 게임 종료 여부 반환 */
    public boolean isGameOver() {
        return gameOver;
    }

    /* [입력값] 없음 | [출력값] boolean - 무승부 여부 반환 */
    public boolean isDraw() {
        return isDraw;
    }

    /* [입력값] 없음 | [출력값] Player - 최종 승리자 객체 반환 (무승부/진행 중이면 null) */
    public Player getWinner() {
        return winner;
    }
}
