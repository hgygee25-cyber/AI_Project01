package GUI_1;


import java.util.ArrayList;
import java.util.List;

public class GameState {

    // [게임 단계] 텍사스 홀덤 진행 순서
    public enum Stage {
        PREFLOP,   // 개인 카드 분배 완료
        FLOP,      // 커뮤니티 카드 3장 공개
        TURN,      // 커뮤니티 카드 4장 공개
        RIVER,     // 커뮤니티 카드 5장 공개
        SHOWDOWN   // 최종 승패 판정
    }

    // 현재 게임 단계
    private Stage stage;

    // 커뮤니티 카드 목록
    private final List<Card.CardData> communityCards;

    // 전체 베팅 금액
    private int pot;

    // 게임 종료 여부
    private boolean gameOver;

    // 승자 정보
    private String winner;

    // [생성자]
    // 입력값: 없음
    // 출력값: 초기화된 게임 상태
    public GameState() {

        stage = Stage.PREFLOP;
        communityCards = new ArrayList<>();

        pot = 0;
        gameOver = false;
        winner = null;
    }

    // [입력값] card(Card.CardData)
    // [출력값] 없음
    // 기능: 커뮤니티 카드 추가
    public void addCommunityCard(Card.CardData card) {

        if (card == null) {
            throw new IllegalArgumentException(
                "잘못된 카드입니다."
            );
        }

        if (communityCards.size() >= 5) {
            throw new IllegalStateException(
                "커뮤니티 카드는 5장까지만 가능합니다."
            );
        }

        communityCards.add(card);
    }

    // [입력값] amount(int) - 팟에 추가할 금액
    // [출력값] 없음
    // 기능: 전체 팟 금액 증가
    public void addPot(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "팟에 추가할 금액은 양수여야 합니다."
            );
        }

        pot += amount;
    }

    // [입력값] nextStage(Stage)
    // [출력값] 없음
    // 기능: 게임 단계 변경
    public void setStage(Stage nextStage) {

        if (nextStage == null) {
            throw new IllegalArgumentException(
                "게임 단계가 올바르지 않습니다."
            );
        }

        stage = nextStage;
    }

    // [입력값] winner(String)
    // [출력값] 없음
    // 기능: 게임 종료 및 승자 기록
    public void finishGame(String winner) {

        this.winner = winner;
        this.gameOver = true;
        this.stage = Stage.SHOWDOWN;
    }

    // [입력값] 없음
    // [출력값] 없음
    // 기능: 새로운 게임을 위한 상태 초기화
    public void reset() {

        stage = Stage.PREFLOP;
        communityCards.clear();

        pot = 0;
        gameOver = false;
        winner = null;
    }

    // ===== Getter =====

    // [출력값] 현재 게임 단계
    public Stage getStage() {
        return stage;
    }

    // [출력값] 커뮤니티 카드 목록
    public List<Card.CardData> getCommunityCards() {
        return new ArrayList<>(communityCards);
    }

    // [출력값] 전체 팟 금액
    public int getPot() {
        return pot;
    }

    // [출력값] 게임 종료 여부
    public boolean isGameOver() {
        return gameOver;
    }

    // [출력값] 승자 정보
    public String getWinner() {
        return winner;
    }
}
