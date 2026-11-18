package GUI;

import java.util.ArrayList;
import java.util.Arrays;

public class Practice_Preflop_Percent {
    private double userProbability;
    private double comProbability;
    private double drawProbability;

    private long userWin;
    private long comWin;
    private long draw;
    private long totalCases;
    private Practice_Preflop_Percent() {
    }

    // 프리플랍 승률 계산 시작
    public static Practice_Preflop_Percent calculate(ArrayList<String> userCards, ArrayList<String> comCards) {
        Practice_Preflop_Percent result = new Practice_Preflop_Percent();

        // 1. 입력값 검사
        if (userCards == null || comCards == null || userCards.size() != 2 || comCards.size() != 2) {
            throw new IllegalArgumentException("각 플레이어는 카드 2장을 가져야 합니다.");
        }

        // 2. 전체 카드 생성
        ArrayList<String> deck = new ArrayList<>();
        boolean[] used = new boolean[52];
        ArrayList<String> allCards = new ArrayList<>();

        allCards.addAll(userCards);
        allCards.addAll(comCards);

        for (String card : allCards) {
            if (card == null) {
                throw new IllegalArgumentException("카드 정보가 null입니다.");
            }

            String[] data = card.split(",");
            if (data.length != 2) {
                throw new IllegalArgumentException("잘못된 카드 형식: " + card);
            }

            int suit;
            int rank;

            try {
                suit = Integer.parseInt(data[0].trim());
                rank = Integer.parseInt(data[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("카드 숫자가 올바르지 않습니다: " + card);
            }

            if (suit < 0 || suit > 3 || rank < 1 || rank > 13) {
                throw new IllegalArgumentException("카드 범위를 벗어났습니다: " + card);
            }

            int index = suit * 13 + (rank - 1);

            if (used[index]) {
                throw new IllegalArgumentException("중복된 카드가 있습니다: " + card);
            }

            used[index] = true;
        }

        // 3. 이미 배분된 카드를 제외한 덱 생성
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                int index = suit * 13 + (rank - 1);
                if (!used[index]) {
                    deck.add(suit + "," + rank);
                }
            }
        }

        // 남은 카드가 48장인지 검사
        if (deck.size() != 48) {
            throw new IllegalStateException("남은 카드 수가 올바르지 않습니다.");
        }

        // 4. 사용자와 컴퓨터의 홀 카드 변환
        int[] user = convertCards(userCards);
        int[] com = convertCards(comCards);

        // 5. 모든 커뮤니티 카드 5장 조합 탐색
        int[] board = new int[5];

        for (int i = 0; i < deck.size() - 4; i++) {
            board[0] = convertCard(deck.get(i));

            for (int j = i + 1; j < deck.size() - 3; j++) {
                board[1] = convertCard(deck.get(j));

                for (int k = j + 1; k < deck.size() - 2; k++) {
                    board[2] = convertCard(deck.get(k));

                    for (int l = k + 1; l < deck.size() - 1; l++) {
                        board[3] = convertCard(deck.get(l));

                        for (int m = l + 1; m < deck.size(); m++) {
                            board[4] = convertCard(deck.get(m));

                            // 사용자 최종 카드 7장
                            int[] userFinal = new int[7];

                            userFinal[0] = user[0];
                            userFinal[1] = user[1];

                            // 컴퓨터 최종 카드 7장
                            int[] comFinal = new int[7];

                            comFinal[0] = com[0];
                            comFinal[1] = com[1];

                            for (int n = 0; n < 5; n++) {
                                userFinal[n + 2] = board[n];
                                comFinal[n + 2] = board[n];
                            }

                            // 6. 각 플레이어의 족보 점수 계산
                            long userScore = evaluate(userFinal);
                            long comScore = evaluate(comFinal);

                            // 7. 승패 판정
                            if (userScore > comScore) {
                                result.userWin++;
                            } else if (userScore < comScore) {
                                result.comWin++;
                            } else {
                                result.draw++;
                            }
                            result.totalCases++;
                        }
                    }
                }
            }
        }

        // 8. 확률 계산
        if (result.totalCases > 0) {
            result.userProbability = (double) result.userWin / result.totalCases * 100;
            result.comProbability = (double) result.comWin / result.totalCases * 100;
            result.drawProbability = (double) result.draw / result.totalCases * 100;
        }

        return result;
    }

    // 카드 문자열을 숫자로 변환
    private static int convertCard(String card) {
        String[] data = card.split(",");

        int suit = Integer.parseInt(data[0].trim());
        int rank = Integer.parseInt(data[1].trim());
        return suit * 13 + (rank - 1);
    }

    // 카드 2장 변환
    private static int[] convertCards(ArrayList<String> cards) {
        int[] result = new int[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            result[i] = convertCard(cards.get(i));
        }
        return result;
    }

    // 카드의 무늬 가져오기
    private static int getSuit(int card) {
        return card / 13;
    }

    // 카드의 숫자 가져오기
    // A = 14, 2~K = 2~13
    private static int getRank(int card) {
        int rank = (card % 13) + 1;

        if (rank == 1) {
            return 14;
        }
        return rank;
    }

    // 7장 카드의 족보 점수 계산
    // HandEvaluator를 사용하지 않음
    private static long evaluate(int[] cards) {
        int[] rankCount = new int[15];
        int[] suitCount = new int[4];

        for (int card : cards) {
            int rank = getRank(card);
            int suit = getSuit(card);

            rankCount[rank]++;
            suitCount[suit]++;
        }

        // 1. 스트레이트 플러시
        for (int suit = 0; suit < 4; suit++) {
            if (suitCount[suit] >= 5) {
                boolean[] suitedRanks = new boolean[15];
                for (int card : cards) {
                    if (getSuit(card) == suit) {
                        suitedRanks[getRank(card)] = true;
                    }
                }
                int high = getStraightHigh(suitedRanks);

                if (high > 0) {
                    return makeScore(8, high);
                }
            }
        }

        // 2. 포카드
        for (int rank = 14; rank >= 2; rank--) {
            if (rankCount[rank] == 4) {
                int kicker = 0;
                for (int r = 14; r >= 2; r--) {
                    if (r != rank && rankCount[r] > 0) {
                        kicker = r;
                        break;
                    }
                }
                return makeScore(7, rank, kicker);
            }
        }

        // 3. 풀하우스
        int three = 0;

        for (int rank = 14; rank >= 2; rank--) {
            if (rankCount[rank] >= 3) {
                three = rank;
                break;
            }
        }

        if (three > 0) {
            int pair = 0;

            for (int rank = 14; rank >= 2; rank--) {
                if (rank != three && rankCount[rank] >= 2) {
                    pair = rank;
                    break;
                }
            }

            if (pair > 0) {
                return makeScore(6, three, pair);
            }
        }

        // 4. 플러시
        for (int suit = 0; suit < 4; suit++) {
            if (suitCount[suit] >= 5) {
                int[] flushRanks = new int[7];
                int count = 0;

                for (int card : cards) {
                    if (getSuit(card) == suit) {
                        flushRanks[count++] = getRank(card);
                    }
                }

                Arrays.sort(flushRanks);
                return makeScore(5,
                        flushRanks[count - 1],
                        flushRanks[count - 2],
                        flushRanks[count - 3],
                        flushRanks[count - 4],
                        flushRanks[count - 5]);
            }
        }

        // 5. 스트레이트
        boolean[] ranks = new boolean[15];
        for (int rank = 2; rank <= 14; rank++) {
            ranks[rank] = rankCount[rank] > 0;
        }
        int straightHigh = getStraightHigh(ranks);
        if (straightHigh > 0) {
            return makeScore(4, straightHigh);
        }

        // 6. 트리플
        if (three > 0) {
            int[] kickers = new int[2];
            int index = 0;

            for (int rank = 14; rank >= 2; rank--) {
                if (rank != three && rankCount[rank] > 0) {
                    kickers[index++] = rank;
                    if (index == 2) {
                        break;
                    }
                }
            }
            return makeScore(3, three, kickers[0], kickers[1]);
        }

        // 7. 투페어
        int firstPair = 0;
        int secondPair = 0;

        for (int rank = 14; rank >= 2; rank--) {
            if (rankCount[rank] >= 2) {
                if (firstPair == 0) {
                    firstPair = rank;
                } else {
                    secondPair = rank;
                    break;
                }
            }
        }

        if (secondPair > 0) {
            int kicker = 0;

            for (int rank = 14; rank >= 2; rank--) {
                if (rank != firstPair && rank != secondPair && rankCount[rank] > 0) {
                    kicker = rank;
                    break;
                }
            }
            return makeScore(2, firstPair, secondPair, kicker);
        }

        // 8. 원페어
        if (firstPair > 0) {
            int[] kickers = new int[3];
            int index = 0;

            for (int rank = 14; rank >= 2; rank--) {
                if (rank != firstPair && rankCount[rank] > 0) {
                    kickers[index++] = rank;
                    if (index == 3) {
                        break;
                    }
                }
            }

            return makeScore(1,
                    firstPair,
                    kickers[0],
                    kickers[1],
                    kickers[2]);
        }

        // 9. 하이카드
        int[] highCards = new int[5];
        int index = 0;

        for (int rank = 14; rank >= 2; rank--) {
            if (rankCount[rank] > 0) {
                highCards[index++] = rank;
                if (index == 5) {
                    break;
                }
            }
        }

        return makeScore(0,
                highCards[0],
                highCards[1],
                highCards[2],
                highCards[3],
                highCards[4]);
    }

    // 스트레이트의 가장 높은 숫자 찾기
    private static int getStraightHigh(boolean[] ranks) {
        // A, 2, 3, 4, 5 처리
        if (ranks[14] && ranks[2] && ranks[3] && ranks[4] && ranks[5]) {
            // 5-high straight
        }

        for (int high = 14; high >= 6; high--) {
            boolean straight = true;

            for (int rank = high; rank > high - 5; rank--) {
                if (!ranks[rank]) {
                    straight = false;
                    break;
                }
            }

            if (straight) {
                return high;
            }
        }

        if (ranks[14] && ranks[2] && ranks[3] && ranks[4] && ranks[5]) {
            return 5;
        }
        return 0;
    }

    // 족보 등급과 숫자를 하나의 점수로 변환
    private static long makeScore(int category, int... values) {
        long score = category;
        for (int i = 0; i < 5; i++) {
            int value = 0;
            if (i < values.length) {
                value = values[i];
            }
            score = score * 15 + value;
        }
        return score;
    }

    // 사용자 승률
    public double getUserProbability() {
        return userProbability;
    }

    // 컴퓨터 승률
    public double getComProbability() {
        return comProbability;
    }

    // 무승부 확률
    public double getDrawProbability() {
        return drawProbability;
    }

    // 전체 계산 결과
    public String getResult() {

        return "사용자 승리 횟수: " + userWin
                + "\n컴퓨터 승리 횟수: " + comWin
                + "\n무승부 횟수: " + draw
                + "\n전체 계산 횟수: " + totalCases
                + "\n사용자 승률: " + userProbability + "%"
                + "\n컴퓨터 승률: " + comProbability + "%"
                + "\n무승부 확률: " + drawProbability + "%";
    }
}
