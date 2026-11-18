package GUI_1;
import java.util.*;

public class HandEvaluator {

    /*
     * [기능]
     * 텍사스 홀덤 족보 판별
     *
     * [입력값]
     * 카드 5~7장 (List<Card.CardData>)
     *
     * [출력값]
     * Map<String, Object>
     * rank: 족보 등급 (int)
     * name: 족보 이름 (String)
     * tiebreak: 비교용 숫자 (List<Integer>)
     * best_cards: 최강의 5장 (List<Card.CardData>)
     */

    private static final Map<String, Integer> RANK_VALUES =
        new HashMap<>();

    private static final String[] HAND_NAMES = {
        "High Card",
        "One Pair",
        "Two Pair",
        "Three of a Kind",
        "Straight",
        "Flush",
        "Full House",
        "Four of a Kind",
        "Straight Flush",
        "Royal Flush"
    };

    private static final Set<String> SUITS = new HashSet<>(
        Arrays.asList("Spades", "Hearts", "Diamonds", "Clubs")
    );

    static {
        String[] ranks = {
            "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K", "A"
        };

        for (int i = 0; i < ranks.length; i++) {
            RANK_VALUES.put(ranks[i], i + 2);
        }
    }

    // 족보 판정 내부 자료형
    private static class HandScore {

        int rank;
        List<Integer> tiebreak;
        List<Card.CardData> bestCards;

        HandScore(
            int rank,
            List<Integer> tiebreak,
            List<Card.CardData> bestCards
        ) {
            this.rank = rank;
            this.tiebreak = new ArrayList<>(tiebreak);
            this.bestCards = new ArrayList<>(bestCards);
        }
    }

    // [입력값] cards: 카드 목록
    //          minCount: 최소 카드 수
    //          maxCount: 최대 카드 수
    // [출력값] 검증된 카드 목록
    // [예외] 잘못된 카드 또는 중복 카드
    public static List<Card.CardData> validateCards(
        List<Card.CardData> cards,
        int minCount,
        int maxCount
    ) {

        if (cards == null ||
            cards.size() < minCount ||
            cards.size() > maxCount) {

            throw new IllegalArgumentException(
                "카드 개수가 올바르지 않습니다."
            );
        }

        Set<String> unique = new HashSet<>();

        for (Card.CardData card : cards) {

            if (card == null ||
                !SUITS.contains(card.getSuit()) ||
                !RANK_VALUES.containsKey(card.getRank())) {

                throw new IllegalArgumentException(
                    "존재하지 않는 카드입니다."
                );
            }

            String key = card.getSuit() + "_" + card.getRank();
            //System.out.println("3.. key            "+ key);
            /*if (!unique.add(key)) {
                throw new IllegalArgumentException(
                    "중복된 카드가 존재합니다."
                );
            }*/ 
        }

        return new ArrayList<>(cards);
    }

    // [입력값] 카드 5장
    // [출력값] HandScore
    private static HandScore evaluateFive(
        List<Card.CardData> cards
    ) {

        List<Integer> values = new ArrayList<>();
        Set<String> suits = new HashSet<>();

        int[] counts = new int[15];

        for (Card.CardData card : cards) {

            int value = RANK_VALUES.get(card.getRank());

            values.add(value);
            suits.add(card.getSuit());

            counts[value]++;
        }

        values.sort(Collections.reverseOrder());

        boolean flush = suits.size() == 1;

        List<Integer> unique = new ArrayList<>();

        for (int value : values) {
            if (!unique.contains(value)) {
                unique.add(value);
            }
        }

        boolean straight = false;
        int straightHigh = 0;

        if (unique.size() == 5) {

            if (unique.get(0) - unique.get(4) == 4) {

                straight = true;
                straightHigh = unique.get(0);

            } else if (unique.equals(
                Arrays.asList(14, 5, 4, 3, 2)
            )) {

                straight = true;
                straightHigh = 5;
            }
        }

        List<Integer> groups = new ArrayList<>();

        for (int i = 2; i <= 14; i++) {
            if (counts[i] > 0) {
                groups.add(i);
            }
        }

        groups.sort((a, b) -> {

            if (counts[a] != counts[b]) {
                return Integer.compare(counts[b], counts[a]);
            }

            return Integer.compare(b, a);
        });

        int first = groups.get(0);
        int firstCount = counts[first];

        int second = groups.size() > 1 ? groups.get(1) : 0;
        int secondCount = groups.size() > 1 ? counts[second] : 0;

        if (flush && straight && straightHigh == 14) {
            return new HandScore(9, Arrays.asList(14), cards);
        }

        if (flush && straight) {
            return new HandScore(8, Arrays.asList(straightHigh), cards);
        }

        if (firstCount == 4) {
            return new HandScore(
                7, Arrays.asList(first, second), cards
            );
        }

        if (firstCount == 3 && secondCount == 2) {
            return new HandScore(
                6, Arrays.asList(first, second), cards
            );
        }

        if (flush) {
            return new HandScore(5, values, cards);
        }

        if (straight) {
            return new HandScore(
                4, Arrays.asList(straightHigh), cards
            );
        }

        if (firstCount == 3) {

            List<Integer> score = new ArrayList<>();
            score.add(first);

            for (int value : values) {
                if (value != first) {
                    score.add(value);
                }
            }

            return new HandScore(3, score, cards);
        }

        if (firstCount == 2 && secondCount == 2) {
            return new HandScore(
                2,
                Arrays.asList(first, second, groups.get(2)),
                cards
            );
        }

        if (firstCount == 2) {

            List<Integer> score = new ArrayList<>();
            score.add(first);

            for (int value : values) {
                if (value != first) {
                    score.add(value);
                }
            }

            return new HandScore(1, score, cards);
        }

        return new HandScore(0, values, cards);
    }

    // [입력값] 카드 5~7장
    // [출력값] 족보 정보 Map
    public static Map<String, Object> evaluate(
        List<Card.CardData> cards
    ) {

        cards = validateCards(cards, 5, 7);

        HandScore best = null;

        int size = cards.size();

        // 5장 조합 생성
        for (int a = 0; a < size - 4; a++) {
            for (int b = a + 1; b < size - 3; b++) {
                for (int c = b + 1; c < size - 2; c++) {
                    for (int d = c + 1; d < size - 1; d++) {
                        for (int e = d + 1; e < size; e++) {

                            List<Card.CardData> combo =
                                Arrays.asList(
                                    cards.get(a),
                                    cards.get(b),
                                    cards.get(c),
                                    cards.get(d),
                                    cards.get(e)
                                );

                            HandScore current = evaluateFive(combo);

                            if (best == null ||
                                compareScores(current, best) > 0) {
                                best = current;
                            }
                        }
                    }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();

        result.put("rank", best.rank);
        result.put("name", HAND_NAMES[best.rank]);
        result.put("tiebreak", best.tiebreak);
        result.put("best_cards", best.bestCards);

        return result;
    }
        // ⭐ [추가] ArrayList<String>을 그대로 입력받는 오버로딩 메서드
    public static Map<String, Object> evaluate(ArrayList<String> stringCards) {
        if (stringCards == null) {
            throw new IllegalArgumentException("카드 리스트가 null입니다.");
        }
        List<Card.CardData> convertedCards = new ArrayList<>();
        for (String str : stringCards) {
            String[] parts = str.split("[_, ]");

            if (parts.length != 2) {
                throw new IllegalArgumentException("카드 형식 오류 : " + str);
            }

            int suitNum = Integer.parseInt(parts[0]);
            int rankNum = Integer.parseInt(parts[1]);

            String suitName = "";
            String rankName = "";

            // 무늬 변환
            if (suitNum == 0) suitName = "Spades";
            else if (suitNum == 1) suitName = "Hearts";
            else if (suitNum == 2) suitName = "Diamonds";
            else if (suitNum == 3) suitName = "Clubs";
            else throw new IllegalArgumentException("잘못된 무늬 번호");

            // 숫자 변환
            if (rankNum == 1) rankName = "A";
            else if (rankNum >= 2 && rankNum <= 10) rankName = String.valueOf(rankNum);
            else if (rankNum == 11) rankName = "J";
            else if (rankNum == 12) rankName = "Q";
            else if (rankNum == 13) rankName = "K";
            else throw new IllegalArgumentException("잘못된 카드 숫자");

            convertedCards.add(new Card.CardData(suitName, rankName));
        }

        return evaluate(convertedCards);
    }


    // [입력값] 두 족보 점수
    // [출력값] 양수: 첫 번째 승리 / 음수: 두 번째 승리 / 0: 무승부
    public static int compareScores(
        HandScore first,
        HandScore second
    ) {

        if (first.rank != second.rank) {
            return Integer.compare(first.rank, second.rank);
        }

        for (int i = 0; i < first.tiebreak.size(); i++) {

            int comparison = Integer.compare(
                first.tiebreak.get(i),
                second.tiebreak.get(i)
            );

            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }

    // [입력값] 두 족보 정보 Map
    // [출력값] 승패 비교 결과(int)
    public static int compareResults(
        Map<String, Object> first,
        Map<String, Object> second
    ) {

        int firstRank = (Integer) first.get("rank");
        int secondRank = (Integer) second.get("rank");

        if (firstRank != secondRank) {
            return Integer.compare(firstRank, secondRank);
        }

        @SuppressWarnings("unchecked")
        List<Integer> firstTie =
            (List<Integer>) first.get("tiebreak");

        @SuppressWarnings("unchecked")
        List<Integer> secondTie =
            (List<Integer>) second.get("tiebreak");

        for (int i = 0; i < firstTie.size(); i++) {

            int comparison = Integer.compare(
                firstTie.get(i),
                secondTie.get(i)
            );

            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }
}
