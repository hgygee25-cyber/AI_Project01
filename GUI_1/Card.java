package GUI_1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Card {

    /*
     * [클래스명] Card
     *
     * [기능]
     * 텍사스 홀덤에서 사용하는 52장의 카드 덱 관리
     *
     * [Java 변환 주의]
     * Python의 클래스 변수 → Java의 static final 상수
     */

    private static final List<String> SUITS = Arrays.asList(
        "Spades", "Hearts", "Diamonds", "Clubs"
    );

    private static final List<String> RANKS = Arrays.asList(
        "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    );

    // [자료형] List<CardData>
    // Python의 list[tuple[str, str]] 대체
    private List<CardData> cards;

    // [생성자]
    // 입력값: 없음
    // 반환값: 없음
    public Card() {
        this.cards = new ArrayList<>();
        createCard();
    }

    // 카드 한 장의 무늬와 숫자를 저장하는 내부 클래스
    public static class CardData {
        private final String suit;
        private final String rank;

        // 입력값: suit(무늬), rank(숫자)
        // 반환값: 없음
        public CardData(String suit, String rank) {
            this.suit = suit;
            this.rank = rank;
        }

        // 출력값: 카드 무늬(String)
        public String getSuit() {
            return suit;
        }

        // 출력값: 카드 숫자(String)
        public String getRank() {
            return rank;
        }
    }

    // [입력값] 없음
    // [출력값] 없음
    // 기능: 52장의 카드 생성
    public void createCard() {
        cards.clear();

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                cards.add(new CardData(suit, rank));
            }
        }
    }

    // [입력값] 없음
    // [출력값] 없음
    // 기능: 카드 순서 무작위 변경
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // [입력값] 없음
    // [출력값] CardData 객체 1개
    // 예시: Spades, A
    // [예외] 카드가 없으면 IllegalStateException 발생
    public CardData drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("남은 카드가 없습니다.");
        }

        return cards.remove(cards.size() - 1);
    }

    // [입력값] 없음
    // [출력값] 남은 카드 수(int)
    // 예시: 47
    public int remainingCards() {
        return cards.size();
    }
}
