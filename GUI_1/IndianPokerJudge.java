package GUI_1;


public class IndianPokerJudge {

    /*
     * [클래스명] IndianPokerJudge
     *
     * [기능]
     * 인디언 포커의 카드 숫자 비교 및 승자 판정
     *
     * [기존 코드 연동]
     * Card.CardData 사용
     * Player 객체 사용
     *
     * [카드 숫자 기준]
     * A = 14, K = 13, Q = 12, J = 11
     * 2~10 = 해당 숫자
     */

    /*
     * [함수명] getCardValue
     *
     * [입력값]
     * card(Card.CardData) - 비교할 카드
     *
     * [출력값]
     * int - 카드의 숫자
     *
     * [기능]
     * 문자열로 저장된 카드 숫자를 정수로 변환
     */
    private static int getCardValue(Card.CardData card) {

        if (card == null) {
            throw new IllegalArgumentException(
                "카드가 존재하지 않습니다."
            );
        }

        String rank = card.getRank();

        switch (rank) {

            case "A":
                return 14;

            case "K":
                return 13;

            case "Q":
                return 12;

            case "J":
                return 11;

            default:

                try {
                    int value = Integer.parseInt(rank);

                    if (value < 2 || value > 10) {
                        throw new IllegalArgumentException(
                            "잘못된 카드 숫자입니다."
                        );
                    }

                    return value;

                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                        "잘못된 카드 숫자입니다."
                    );
                }
        }
    }

    /*
     * [함수명] compareCards
     *
     * [입력값]
     * first(Card.CardData) - 첫 번째 카드
     * second(Card.CardData) - 두 번째 카드
     *
     * [출력값]
     * int
     *  1 : 첫 번째 카드 승리
     *  0 : 무승부
     * -1 : 두 번째 카드 승리
     *
     * [기능]
     * 두 카드의 숫자 비교
     */
    public static int compareCards(
        Card.CardData first,
        Card.CardData second
    ) {

        int firstValue = getCardValue(first);
        int secondValue = getCardValue(second);

        return Integer.compare(firstValue, secondValue);
    }

    /*
     * [함수명] determineWinner
     *
     * [입력값]
     * firstCard(Card.CardData) - 첫 번째 카드
     * secondCard(Card.CardData) - 두 번째 카드
     * firstPlayer(Player) - 첫 번째 플레이어
     * secondPlayer(Player) - 두 번째 플레이어
     *
     * [출력값]
     * Player - 승리한 플레이어
     * null - 무승부
     *
     * [기능]
     * 카드 비교 결과에 따라 승자 반환
     */
    public static Player determineWinner(
        Card.CardData firstCard,
        Card.CardData secondCard,
        Player firstPlayer,
        Player secondPlayer
    ) {

        if (firstPlayer == null || secondPlayer == null) {
            throw new IllegalArgumentException(
                "플레이어 정보가 존재하지 않습니다."
            );
        }

        int result = compareCards(firstCard, secondCard);

        if (result > 0) {
            return firstPlayer;
        }

        if (result < 0) {
            return secondPlayer;
        }

        return null;
    }

    /*
     * [함수명] isDraw
     *
     * [입력값]
     * first(Card.CardData) - 첫 번째 카드
     * second(Card.CardData) - 두 번째 카드
     *
     * [출력값]
     * boolean
     * true : 무승부
     * false : 승패가 존재함
     *
     * [기능]
     * 두 카드의 숫자가 동일한지 확인
     */
    public static boolean isDraw(
        Card.CardData first,
        Card.CardData second
    ) {

        return compareCards(first, second) == 0;
    }
}

