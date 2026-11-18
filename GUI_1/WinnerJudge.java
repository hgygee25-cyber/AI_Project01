
package GUI_1;

import java.util.*;

import GUI_1.Card.CardData;

public class WinnerJudge {

    /*
     * [기능]
     * 플레이어와 컴퓨터의 족보 비교 및 승패 판정
     *
     * [입력값]
     * playerCards: 플레이어 개인 카드 2장
     * computerCards: 컴퓨터 개인 카드 2장
     * communityCards: 커뮤니티 카드 5장
     *
     * [출력값]
     * winner: PLAYER_WIN / COMPUTER_WIN / DRAW
     * player_hand: 플레이어 족보 정보
     * computer_hand: 컴퓨터 족보 정보
     */

    public static Map<String, Object> compare(
        ArrayList<String> playerCards,
        ArrayList<String> computerCards,
        ArrayList<String> communityCards
    ) {

        // 1. 입력값 검사
        if (playerCards == null ||
            computerCards == null ||
            communityCards == null ||
            playerCards.size() != 2 ||
            computerCards.size() != 2 ||
            communityCards.size() != 5) {

            throw new IllegalArgumentException(
                "카드 구성이 올바르지 않습니다."
            );
        }

        // 2. 전체 카드 9장 구성
        ArrayList<String> allCards = new ArrayList<>();

        allCards.addAll(playerCards);
        allCards.addAll(computerCards);
        allCards.addAll(communityCards);

        // 3. 중복 카드 검사
        Set<String> uniqueCards = new HashSet<>(allCards);

        if (uniqueCards.size() != 9) {
            throw new IllegalArgumentException(
                "중복된 카드가 존재합니다."
            );
        }

        // 4. 플레이어 최종 7장 구성
        ArrayList<String> playerAll = new ArrayList<>(playerCards);
        playerAll.addAll(communityCards);

        // 5. 컴퓨터 최종 7장 구성
        ArrayList<String> computerAll = new ArrayList<>(computerCards);
        computerAll.addAll(communityCards);

        // 6. 족보 판별
        Map<String, Object> playerHand =
            HandEvaluator.evaluate(playerAll);

        Map<String, Object> computerHand =
            HandEvaluator.evaluate(computerAll);

        // 7. 족보 비교
        int comparison = HandEvaluator.compareResults(
            playerHand,
            computerHand
        );

        String winner;

        if (comparison > 0) {
            winner = "PLAYER_WIN";

        } else if (comparison < 0) {
            winner = "COMPUTER_WIN";

        } else {
            winner = "DRAW";
        }

        // 8. 최종 결과 반환
        Map<String, Object> result = new HashMap<>();

        result.put("winner", winner);
        result.put("player_hand", playerHand);
        result.put("computer_hand", computerHand);

        return result;
    }

    public static Map<String, Object> compare(List<CardData> cards, List<CardData> cards2,
            List<CardData> communityCards) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }
}
