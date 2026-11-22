package GUI_4;

/**
 * [기능]
 * 레이즈를 선택했을 때 추가 지불 금액을 계산한다.
 *
 * [입력값]
 * potSize                 : 콜하기 전 현재 팟 금액
 * callCost                : 컴퓨터가 추가로 지불해야 하는 콜 비용
 * computerChips           : 컴퓨터 현재 보유 칩
 * opponentMatchableAmount : 상대방이 추가로 대응할 수 있는 금액
 * minRaise                : 최소 추가 레이즈 금액
 *
 * [반환값]
 * int : 추가 레이즈 금액
 *
 * [주의사항]
 * 1. 계산 공식은 (현재 팟 + 콜 비용) * 50%이다.
 * 2. 계산 금액은 콜 비용을 제외한 추가 레이즈 금액이다.
 * 3. 실제 총 지불 금액은 콜 비용 + 추가 레이즈 금액이다.
 * 4. 컴퓨터가 콜 비용을 지불할 수 없으면 0을 반환한다.
 * 5. 컴퓨터의 추가 지불 가능 금액을 초과하지 않는다.
 * 6. 상대방의 추가 대응 가능 금액을 초과하지 않는다.
 * 7. 최소 레이즈 금액을 우선 고려한다.
 * 8. 최소 레이즈에 미달하는 금액은 올인 여부를 별도로 검증해야 한다.
 * 9. 실제 올인 및 불완전 올인 판단은 AllInHandler에서 담당한다.
 * 10. 실제 칩 차감과 베팅 실행은 게임 시스템에서 담당한다.
 *
 * [추가 설명]
 * opponentMatchableAmount는 현재 콜 비용을 제외하고
 * 상대방이 추가로 대응할 수 있는 금액이다.
 *
 * 해당 금액의 정확한 산정 기준은 게임 시스템 담당자와 협의한다.
 *
 * 반환된 금액이 최소 레이즈 금액보다 작다면
 * 불완전 올인 가능 여부를 별도로 확인해야 한다.
 */
public class RaiseAmountCalculator {

    /**
     * 레이즈 추가 금액 계산
     */
    public int calculateRaiseAmount(
            int potSize,
            int callCost,
            int computerChips,
            int opponentMatchableAmount,
            int minRaise
    ) {

        // 1. 입력값 검사
        if (potSize < 0
                || callCost < 0
                || computerChips < 0
                || opponentMatchableAmount < 0
                || minRaise < 0) {

            throw new IllegalArgumentException(
                    "팟, 콜 비용, 보유 칩, 상대방 대응 금액, 최소 레이즈 금액은 음수일 수 없습니다."
            );
        }

        // 2. 콜 비용을 지불할 수 없는 경우
        if (callCost > computerChips) {
            return 0;
        }

        // 3. 콜 비용을 제외한 컴퓨터의 추가 지불 가능 금액
        int computerRaiseLimit = computerChips - callCost;

        // 4. 실제 추가 레이즈 가능 한도 확인
        int maxRaiseAmount = Math.min(
                computerRaiseLimit,
                opponentMatchableAmount
        );

        // 추가 레이즈가 불가능한 경우
        if (maxRaiseAmount <= 0) {
            return 0;
        }

        // 5. 팟의 50%를 기준으로 추가 레이즈 금액 계산
        // long을 사용하여 덧셈 및 계산 과정의 오버플로 방지
        long calculatedAmount =
                ((long) potSize + callCost) / 2;

        // 6. 최소 레이즈 금액 확인
        if (calculatedAmount < minRaise) {
            calculatedAmount = minRaise;
        }

        // 7. 최대 추가 지불 가능 금액을 초과하지 않도록 제한
        if (calculatedAmount > maxRaiseAmount) {
            calculatedAmount = maxRaiseAmount;
        }

        // 8. 최종 추가 레이즈 금액 반환
        return (int) calculatedAmount;
    }
}