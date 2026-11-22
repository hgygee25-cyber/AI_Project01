package GUI_4;

/**
 * [기능]
 * 컴퓨터의 보유 칩이 부족하거나
 * 전체 보유 칩을 베팅하는 상황을 판단한다.
 *
 * [입력값]
 * computerChips : 컴퓨터 현재 보유 칩
 * callCost : 컴퓨터가 추가로 지불해야 하는 콜 비용
 * raiseAmount : 콜 비용을 제외한 추가 레이즈 금액
 * opponentChips : 상대방의 현재 보유 칩
 *
 * [반환값]
 * boolean : 올인 처리 필요 여부
 *
 * [주의사항]
 * 1. 실제 총 지불 금액은 콜 비용 + 추가 레이즈 금액이다.
 * 2. 총 지불 금액이 컴퓨터 보유 칩과 같으면 올인이다.
 * 3. 콜 비용이 컴퓨터 보유 칩보다 많으면 올인 CALL 상황이다.
 * 4. 콜 비용이 보유 칩보다 많은 상황에서는 추가 레이즈할 수 없다.
 * 5. 추가 레이즈 금액은 상대방의 추가 대응 가능 금액을 초과할 수 없다.
 * 6. 불완전 올인 여부는 RaiseValidator 및 게임 규칙과 함께 확인한다.
 * 7. 실제 칩 차감과 올인 실행은 게임 엔진에서 담당한다.
 *
 * [추가 설명]
 * opponentChips는 상대방의 현재 베팅액을 제외한
 * 남은 보유 칩을 의미한다.
 *
 * 상대방의 추가 대응 가능 금액과 정확히 일치하는지는
 * 게임 시스템 연동 시 확인해야 한다.
 */
public class AllInHandler {

    /**
     * 올인 처리 필요 여부 판단
     */
    public boolean handleAllIn(
            int computerChips,
            int callCost,
            int raiseAmount,
            int opponentChips
    ) {

        // 1. 입력값 검사
        if (computerChips < 0
                || callCost < 0
                || raiseAmount < 0
                || opponentChips < 0) {

            throw new IllegalArgumentException(
                    "칩과 베팅 금액은 음수일 수 없습니다."
            );
        }

        // 2. 보유 칩이 없는 경우
        if (computerChips == 0) {
            return false;
        }

        // 3. 콜 비용이 보유 칩보다 많은 경우
        // 추가 레이즈 없이 올인 CALL만 가능하다.
        if (callCost > computerChips) {
            return raiseAmount == 0;
        }

        // 4. 실제 총 지불 금액 계산
        long totalPayment = (long) callCost + raiseAmount;

        // 5. 보유 칩을 초과하는 베팅 방지
        if (totalPayment > computerChips) {
            return false;
        }

        // 6. 추가 레이즈 금액이 상대방 보유 칩을 초과하는지 확인
        if (raiseAmount > opponentChips) {
            return false;
        }

        // 7. 보유 칩을 전부 사용하는 경우 올인
        return totalPayment == computerChips;
    }
}