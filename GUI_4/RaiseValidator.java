package GUI_4;

/**
 * [기능]
 * 계산된 레이즈 금액이 게임 규칙에 적합한지 검증한다.
 *
 * [입력값]
 * raiseAmount             : 콜 비용을 제외한 추가 레이즈 금액
 * callCost                : 컴퓨터가 추가로 지불해야 하는 콜 비용
 * computerChips           : 컴퓨터 현재 보유 칩
 * opponentMatchableAmount : 상대방이 추가로 대응할 수 있는 금액
 * minRaise                : 최소 추가 레이즈 금액
 *
 * [반환값]
 * boolean : 기본 레이즈 금액 검증 통과 여부
 *
 * [주의사항]
 * 1. 실제 총 지불 금액은 콜 비용 + 추가 레이즈 금액이다.
 * 2. 실제 총 지불 금액은 컴퓨터 보유 칩을 초과할 수 없다.
 * 3. 상대방의 추가 대응 가능 금액을 초과할 수 없다.
 * 4. 정상적인 레이즈는 최소 레이즈 금액을 충족해야 한다.
 * 5. 컴퓨터가 보유 칩을 전부 사용하는 경우
 *    최소 레이즈 미달이라도 불완전 올인 검토 대상으로 인정한다.
 * 6. 실제 올인 및 불완전 올인의 허용 여부는 AllInHandler와
 *    게임 시스템의 베팅 규칙을 통해 추가 확인해야 한다.
 * 7. 실제 칩 차감과 베팅 실행은 게임 시스템에서 담당한다.
 *
 * [추가 설명]
 * opponentMatchableAmount는 현재 콜 비용을 제외하고
 * 상대방이 추가로 대응할 수 있는 금액이다.
 */
public class RaiseValidator {

    /**
     * 계산된 레이즈 금액 검증
     */
    public boolean validateRaiseAmount(
            int raiseAmount,
            int callCost,
            int computerChips,
            int opponentMatchableAmount,
            int minRaise
    ) {

        // 1. 금액의 음수 여부 검사
        if (raiseAmount < 0
                || callCost < 0
                || computerChips < 0
                || opponentMatchableAmount < 0
                || minRaise < 0) {

            return false;
        }

        // 2. 추가 레이즈 금액이 0이면 레이즈 불가능
        if (raiseAmount == 0) {
            return false;
        }

        // 3. 컴퓨터가 콜 비용조차 지불할 수 없는 경우
        if (callCost > computerChips) {
            return false;
        }

        // 4. 실제 총 지불 금액 계산
        long totalPayment = (long) callCost + raiseAmount;

        // 5. 컴퓨터 보유 칩 초과 여부 검사
        if (totalPayment > computerChips) {
            return false;
        }

        // 6. 상대방 대응 가능 금액 초과 여부 검사
        if (raiseAmount > opponentMatchableAmount) {
            return false;
        }

        // 7. 컴퓨터가 보유 칩을 전부 사용하는지 확인
        boolean isAllIn = totalPayment == computerChips;

        // 8. 최소 레이즈 금액 충족 여부 검사
        if (raiseAmount < minRaise) {

            // 최소 레이즈 미달이라면 올인 상황이어야 한다.
            if (!isAllIn) {
                return false;
            }
        }

        // 모든 기본 검증 통과
        return true;
    }
}