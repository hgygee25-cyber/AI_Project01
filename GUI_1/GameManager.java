package GUI_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    // =====================================
    // ① 멤버 변수
    // =====================================

    private Card deck;                   // 카드 덱
    private Player player;               // 사용자
    private Player computer;             // 컴퓨터

    private GameState gameState;         // 게임 상태 관리

    // =====================================
    // ② 생성자
    // =====================================

    // [입력값] 없음
    // [출력값] 초기화된 GameManager 객체
    // [기능] 게임에 필요한 객체 생성 및 개인 카드 분배
    public GameManager() {

        deck = new Card();
        deck.shuffle();

        // 양측 플레이어 생성
        player = new Player("Player", 50000);
        computer = new Player("Computer", 50000);

        // 게임 상태 초기화
        gameState = new GameState();

        // 개인 카드 분배
        dealPrivateCards();
    }

    // =====================================
    // ③ 카드 관리
    // =====================================

    // [입력값] 없음
    // [출력값] 없음
    // [기능] 양측에 개인 카드 2장씩 분배
    public void dealPrivateCards() {

        // 이미 카드가 분배되었다면 중복 분배 방지
        if (!player.getCards().isEmpty() ||
            !computer.getCards().isEmpty()) {

            throw new IllegalStateException(
                "개인 카드가 이미 분배되었습니다."
            );
        }

        for (int i = 0; i < 2; i++) {

            // 사용자에게 카드 1장 분배
            player.receiveCard(deck.drawCard());

            // 컴퓨터에게 카드 1장 분배
            computer.receiveCard(deck.drawCard());
        }
    }

    // [입력값] 없음
    // [출력값] 없음
    // [기능] 덱에서 번 카드 1장 제거
    private void burnCard() {

        deck.drawCard();
    }

    // =====================================
    // ④ 게임 단계 진행
    // =====================================

    // [입력값] 없음
    // [출력값] 다음 게임 단계(GameState.Stage)
    // [기능] 베팅 라운드 종료 후 다음 단계 진행
    public GameState.Stage advanceStage() {

        if (gameState.isGameOver()) {
            throw new IllegalStateException(
                "이미 종료된 게임입니다."
            );
        }

        GameState.Stage currentStage = gameState.getStage();

        switch (currentStage) {

            case PREFLOP:

                // 프리플랍 종료 후 플랍 진행
                revealCommunityCards(3);

                gameState.setStage(GameState.Stage.FLOP);
                break;

            case FLOP:

                // 플랍 종료 후 턴 진행
                revealCommunityCards(1);

                gameState.setStage(GameState.Stage.TURN);
                break;

            case TURN:

                // 턴 종료 후 리버 진행
                revealCommunityCards(1);

                gameState.setStage(GameState.Stage.RIVER);
                break;

            case RIVER:

                // 리버 종료 후 쇼다운 진행
                gameState.setStage(GameState.Stage.SHOWDOWN);

                showdown();
                break;

            case SHOWDOWN:

                throw new IllegalStateException(
                    "이미 쇼다운 단계입니다."
                );
        }

        return gameState.getStage();
    }

    // =====================================
    // ⑤ 커뮤니티 카드 공개
    // =====================================

    // [입력값] count(int) - 공개할 카드 수
    // [출력값] 없음
    // [기능] 번 카드를 제거한 후 커뮤니티 카드 공개
    private void revealCommunityCards(int count) {

        burnCard();

        for (int i = 0; i < count; i++) {

            // 덱에서 카드를 뽑아 커뮤니티 카드에 추가
            gameState.addCommunityCard(deck.drawCard());
        }
    }

    // =====================================
    // ⑥ 쇼다운 및 승패 판정
    // =====================================

    // [입력값] 없음
    // [출력값] 승패 결과(Map<String, Object>)
    // [기능] 양측 족보를 비교하여 승패 판정
    public Map<String, Object> showdown() {

        if (gameState.isGameOver()) {
            throw new IllegalStateException(
                "이미 종료된 게임입니다."
            );
        }

        if (gameState.getStage() != GameState.Stage.SHOWDOWN) {
            throw new IllegalStateException(
                "쇼다운 단계가 아닙니다."
            );
        }

        // WinnerJudge를 통해 최종 승패 판정
        Map<String, Object> result = WinnerJudge.compare(
            player.getCards(),
            computer.getCards(),
            gameState.getCommunityCards()
        );

        // 승패 결과 추출
        String winner = (String) result.get("winner");

        // 게임 상태에 승자 기록
        gameState.finishGame(winner);

        result.put("pot", gameState.getPot());

        return result;
    }

    // =====================================
    // ⑦ 올인 이후 자동 카드 공개
    // =====================================

    // [입력값] 없음
    // [출력값] 없음
    // [기능] 남은 커뮤니티 카드를 자동으로 공개하고 쇼다운 진행
    public void runoutBoard() {

        if (gameState.isGameOver()) {
            throw new IllegalStateException(
                "이미 종료된 게임입니다."
            );
        }

        while (gameState.getStage() != GameState.Stage.SHOWDOWN) {

            advanceStage();
        }
    }

    // =====================================
    // ⑧ 게임 상태 반환
    // =====================================

    // [입력값] reveal(boolean) - 컴퓨터 카드 공개 여부
    // [출력값] 게임 상태(Map<String, Object>)
    public Map<String, Object> getGameState(boolean reveal) {

        Map<String, Object> state = new HashMap<>();

        // 쇼다운 이후에는 컴퓨터 카드 공개
        boolean showComputerCards =
            reveal || gameState.isGameOver();

        state.put("stage", gameState.getStage());

        // 사용자 카드 반환
        state.put("player_cards", player.getCards());

        // 컴퓨터 카드는 공개 조건에 따라 반환
        state.put(
            "computer_cards",
            showComputerCards ? computer.getCards() : null
        );

        // 커뮤니티 카드 반환
        state.put(
            "community_cards",
            gameState.getCommunityCards()
        );

        // 칩과 팟은 기존 Player 및 GameState에서 조회
        state.put("player_chips", player.getChips());
        state.put("computer_chips", computer.getChips());
        state.put("pot", gameState.getPot());

        state.put("game_over", gameState.isGameOver());
        state.put("winner", gameState.getWinner());

        return state;
    }

    // =====================================
    // ⑨ 새로운 핸드 시작
    // =====================================

    // [입력값] 없음
    // [출력값] 없음
    // [기능] 새로운 게임을 위한 카드 및 상태 초기화
    public void startNewHand() {

        if (gameState.isGameOver() == false) {
            throw new IllegalStateException(
                "현재 게임이 종료되지 않았습니다."
            );
        }

        if (player.getChips() <= 0 ||
            computer.getChips() <= 0) {

            throw new IllegalStateException(
                "칩이 없는 플레이어가 있습니다."
            );
        }

        // 기존 플레이어의 카드 및 상태 초기화
        player.reset();
        computer.reset();

        // 새로운 카드 덱 생성 및 셔플
        deck = new Card();
        deck.shuffle();

        // 게임 상태 초기화
        gameState.reset();

        // 새로운 개인 카드 분배
        dealPrivateCards();
    }

    // =====================================
    // ⑩ Getter
    // =====================================

    // [출력값] 사용자 객체
    public Player getPlayer() {
        return player;
    }

    // [출력값] 컴퓨터 객체
    public Player getComputer() {
        return computer;
    }

    // [출력값] 게임 상태 객체
    public GameState getGameStateObject() {
        return gameState;
    }
}
