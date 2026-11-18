package GUI_1;
import java.util.ArrayList;
import java.util.List;

public class Player {

    /*
     * [클래스명] Player
     *
     * [기능]
     * 플레이어의 기본 정보, 카드, 칩 및 베팅 상태 관리
     *
     * [Java 변환 주의]
     * Python의 list[tuple[str, str]]는
     * Card.CardData 자료형으로 대체
     */

    private String name;
    private int chips;
    private List<Card.CardData> cards;

    private boolean folded;
    private boolean allIn;
    private int betAmount;

    // [생성자]
    // 입력값: name(String) - 플레이어 이름
    //        chips(int) - 초기 보유 칩 (기본값 50000)
    // 출력값: 없음
    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.cards = new ArrayList<>();

        this.folded = false;
        this.allIn = false;
        this.betAmount = 0;
    }

    // Python의 기본 매개변수 chips=50000 대체 생성자
    // 입력값: name(String) - 플레이어 이름
    // 출력값: 없음
    public Player(String name) {
        this(name, 50000);
    }

    // [입력값] card(Card.CardData) - 전달받은 카드 1장
    // [출력값] 없음
    public void receiveCard(Card.CardData card) {
        cards.add(card);
    }

    // [입력값] amount(int) - 베팅할 칩
    // [출력값] 없음
    // [예외] 금액이 0 이하이거나 보유 칩보다 많으면 예외 발생
    public void bet(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "베팅 금액은 양수여야 합니다."
            );
        }

        if (amount > chips) {
            throw new IllegalArgumentException(
                "보유 칩이 부족합니다."
            );
        }

        chips -= amount;
        betAmount += amount;

        // 보유 칩이 0이면 올인 상태로 변경
        if (chips == 0) {
            allIn = true;
        }
    }

    // [입력값] 없음
    // [출력값] 없음
    // 기능: 폴드 상태 변경
    public void fold() {
        folded = true;
    }

    // [입력값] 없음
    // [출력값] 없음
    // 기능: 새로운 라운드를 위한 상태 초기화
    // 주의: 보유 칩은 초기화하지 않음
    public void reset() {
        cards.clear();
        folded = false;
        allIn = false;
        betAmount = 0;
    }

    // ===== Getter =====

    // 출력값: 플레이어 이름(String)
    public String getName() {
        return name;
    }

    // 출력값: 보유 칩(int)
    public int getChips() {
        return chips;
    }

    // 출력값: 보유 카드 목록(List<Card.CardData>)
    public List<Card.CardData> getCards() {
        return new ArrayList<>(cards);
    }

    // 출력값: 폴드 여부(boolean)
    public boolean isFolded() {
        return folded;
    }

    // 출력값: 올인 여부(boolean)
    public boolean isAllIn() {
        return allIn;
    }

    // 출력값: 현재 베팅 금액(int)
    public int getBetAmount() {
        return betAmount;
    }
}