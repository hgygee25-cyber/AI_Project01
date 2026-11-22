package GUI_1;

public class OneCardJudge {

    /*
     * [클래스명] OneCardJudge
     *
     * [기능]
     * 1. 원카드 제출 가능 여부 판정 (무늬 또는 숫자 일치 여부)
     * 2. 특수 카드(2, A, J, K) 효과 실행
     *
     * [특수 카드 규칙]
     * - 2 : 다음 플레이어 카드 2장 뽑기 (즉시 적용)
     * - A : 다음 플레이어 카드 3장 뽑기 (즉시 적용)
     * - J : 다음 플레이어 턴 건너뛰기 (즉시 적용)
     * - K : 한 번 더 내기 (1대1 게임이므로 J와 동일하게 턴 유지)
     */

    /*
     * [함수명] canPlayCard
     *
     * [입력값]
     * targetCard (Card.CardData) - 플레이어가 제출하려는 카드
     * centerCard (Card.CardData) - 현재 중앙에 공개된 맨 위 카드
     *
     * [출력값]
     * boolean
     * - true  : 무늬 또는 숫자가 일치하여 제출 가능
     * - false : 무늬와 숫자가 모두 달라 제출 불가
     *
     * [기능]
     * 중앙 카드와 제출 카드의 무늬(suit) 또는 숫자(rank)가 일치하는지 판별
     */
    public static boolean canPlayCard(
        Card.CardData targetCard,
        Card.CardData centerCard
    ) {
        if (targetCard == null || centerCard == null) {
            throw new IllegalArgumentException("카드 정보가 존재하지 않습니다.");
        }

        boolean sameSuit = targetCard.getSuit().equals(centerCard.getSuit());
        boolean sameRank = targetCard.getRank().equals(centerCard.getRank());

        return sameSuit || sameRank;
    }

    /*
     * [함수명] applySpecialEffect
     *
     * [입력값]
     * playedCard (Card.CardData) - 방금 제출된 카드
     * manager (OneCardManager)   - 덱 뽑기 및 턴 제어를 수행할 게임 매니저 객체
     *
     * [출력값]
     * String - 실행된 효과 결과 코드
     * - "ATTACK_2"  : 상대방에게 2장 지급 후 턴 교대
     * - "ATTACK_3"  : 상대방에게 3장 지급 후 턴 교대
     * - "SKIP_TURN" : 상대방 턴 건너뛰기 (현재 플레이어 턴 유지)
     * - "NORMAL"    : 일반 카드 처리 (상대방에게 턴 교대)
     * - "DRAW_GAME" : 공격 효과 중 덱과 중앙 카드 모두 소진되어 무승부 종료
     *
     * [기능]
     * 제출된 카드의 숫자를 확인하여 특수 카드(2, A, J, K) 효과를 즉시 실행
     */
    public static String applySpecialEffect(
        Card.CardData playedCard,
        OneCardManager manager
    ) {
        if (playedCard == null || manager == null) {
            throw new IllegalArgumentException("카드 또는 매니저 정보가 존재하지 않습니다.");
        }

        String rank = playedCard.getRank();

        switch (rank) {
            case "2":
                return applyAttackEffect(manager, 2);

            case "A":
                return applyAttackEffect(manager, 3);

            case "J":
            case "K":
                return applySkipEffect(manager);

            default:
                manager.nextTurn();
                return "NORMAL";
        }
    }

    /*
     * [함수명] applyAttackEffect (기존 applyTwoEffect 확장)
     *
     * [입력값]
     * manager (OneCardManager) - 게임 매니저 객체
     * drawCount (int)          - 상대방에게 뽑게 할 카드 장수 (2 또는 3)
     *
     * [출력값]
     * String - "ATTACK_2", "ATTACK_3", 또는 덱 완전 소진 시 "DRAW_GAME"
     *
     * [기능]
     * 1. 다음 플레이어(상대방)에게 지정된 장수만큼 카드 지급
     * 2. 도중 덱과 재활용할 중앙 카드가 모두 떨어지면 즉시 무승부 처리
     * 3. 정상 지급 후 다음 플레이어에게 턴을 넘김
     */
    private static String applyAttackEffect(
        OneCardManager manager,
        int drawCount
    ) {
        boolean success = manager.drawCardsForOpponent(drawCount);

        if (!success) {
            return "DRAW_GAME";
        }

        manager.nextTurn();
        return (drawCount == 2) ? "ATTACK_2" : "ATTACK_3";
    }

    /*
     * [함수명] applySkipEffect (기존 applyJackEffect 확장)
     *
     * [입력값]
     * manager (OneCardManager) - 게임 매니저 객체
     *
     * [출력값]
     * String - "SKIP_TURN"
     *
     * [기능]
     * J(점프) 또는 K(한 번 더) 효과로 다음 플레이어의 턴을 건너뛰고
     * 현재 플레이어가 연속으로 턴을 진행할 수 있도록 상태 초기화
     */
    private static String applySkipEffect(OneCardManager manager) {
        manager.keepCurrentTurn();
        return "SKIP_TURN";
    }
}
