
package GUI_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HandProbability {

    private double userProbability;
    private double comProbability;
    private double drawProbability;

    private long totalCases;
    private long userWins;
    private long comWins;
    private long draws;

    public HandProbability(
        double userProbability,
        double comProbability,
        double drawProbability,
        long totalCases,
        long userWins,
        long comWins,
        long draws
    ) {
        this.userProbability = userProbability;
        this.comProbability = comProbability;
        this.drawProbability = drawProbability;

        this.totalCases = totalCases;
        this.userWins = userWins;
        this.comWins = comWins;
        this.draws = draws;
    }

    // 승률 계산 시작
    public static HandProbability calculate(
        ArrayList<String> userCards,
        ArrayList<String> comCards,
        ArrayList<String> communityCards
    ) {

        if (userCards == null || comCards == null
                || communityCards == null) {
            throw new IllegalArgumentException("카드 정보가 없습니다.");
        }
        System.out.println("2.. 클래스... userCards: "+ userCards);
        System.out.println("2.. 클래스... comCards: "+ comCards);
        System.out.println("2.. 클래스... communityCards: "+ communityCards);
        System.out.println("");
        System.out.println("2.. 클래스... size_userCards: "+ userCards.size());
        System.out.println("2.. 클래스... size_comCards: "+ comCards.size());
        System.out.println("2.. 클래스... size_communityCards: "+ communityCards.size());
        if (userCards.size() != 2 || comCards.size() != 2
                                    || communityCards.size() > 5) {
            throw new IllegalArgumentException("카드 개수가 올바르지 않습니다.");
        }

        // 모든 카드 중복 확인
        Set<String> usedCards = new HashSet<>();

        ArrayList<String> allCards = new ArrayList<>();

        allCards.addAll(userCards);
        allCards.addAll(comCards);
        allCards.addAll(communityCards);

        for (String card : allCards) {
            if (card == null || !usedCards.add(card)) {
                throw new IllegalArgumentException("카드가 중복되었거나 올바르지 않습니다.");
            }
        }
                
        // 전체 카드 52장 생성
        ArrayList<String> deck = new ArrayList<>();

        for (int suit = 0; suit < 4; suit++) {

            for (int rank = 1; rank <= 13; rank++) {

                String card = suit + "_" + rank;

                if (!usedCards.contains(card)) {
                    deck.add(card);
                }
            }
        }


        // 결과 저장
        long[] result = new long[3];

        int need = 5 - communityCards.size();

        search(
            0,
            need,
            deck,
            new ArrayList<String>(),
            userCards,
            comCards,
            communityCards,
            result
        );

        long total = result[0] + result[1] + result[2];

        if (total == 0) {
            throw new IllegalStateException("계산할 카드 조합이 없습니다.");
        }

        return new HandProbability(
            result[0] * 100.0 / total,
            result[1] * 100.0 / total,
            result[2] * 100.0 / total,
            total,
            result[0],
            result[1],
            result[2]
        );
    }

    // 카드 조합 탐색
    private static void search(
        int start,
        int need,
        ArrayList<String> deck,
        ArrayList<String> selected,
        ArrayList<String> userCards,
        ArrayList<String> comCards,
        ArrayList<String> communityCards,
        long[] result
    ) {

        if (selected.size() == need) {

            ArrayList<String> finalBoard =new ArrayList<>(communityCards);
            finalBoard.addAll(selected);
            ArrayList<String> userFinal = new ArrayList<>(userCards);
            userFinal.addAll(finalBoard);
            ArrayList<String> comFinal = new ArrayList<>(comCards);
            comFinal.addAll(finalBoard);

            // 문자열 카드를 CardData로 변환
            Map<String, Object> userResult = 
                HandEvaluator.evaluate(userFinal);

            Map<String, Object> comResult =
                HandEvaluator.evaluate(comFinal);
            int comparison =
                HandEvaluator.compareResults(
                    userResult,
                    comResult
                );

            if (comparison > 0) {
                result[0]++;
            }
            else if (comparison < 0) {
                result[1]++;
            }
            else {
                result[2]++;
            }

            return;
        }

        for (int i = start;
                i <= deck.size() - (need - selected.size());
                i++) {

            selected.add(deck.get(i));

            search(
                i + 1,
                need,
                deck,
                selected,
                userCards,
                comCards,
                communityCards,
                result
            );

            selected.remove(selected.size() - 1);
        }
    }

    // String 카드를 CardData로 변환
    private static ArrayList<Card.CardData> convertCards(
        ArrayList<String> cards
    ) {

        ArrayList<Card.CardData> result = new ArrayList<>();

        for (String card : cards) {

            String[] data = card.split("_");

            if (data.length != 2) {
                throw new IllegalArgumentException(
                    "카드 데이터 형식이 올바르지 않습니다: " + card
                );
            }

            result.add(
                new Card.CardData(data[0], data[1])
            );
        }

        return result;
    }

    public double getUserProbability() {
        return userProbability;
    }

    public double getComProbability() {
        return comProbability;
    }

    public double getDrawProbability() {
        return drawProbability;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public long getUserWins() {
        return userWins;
    }

    public long getComWins() {
        return comWins;
    }

    public long getDraws() {
        return draws;
    }

    public String getResult() {

        return String.format(
            "사용자 승률: %.2f%%\n" +
            "컴퓨터 승률: %.2f%%\n" +
            "무승부 확률: %.2f%%\n" +
            "전체 계산 횟수: %d",
            userProbability,
            comProbability,
            drawProbability,
            totalCases
        );
    }
}
