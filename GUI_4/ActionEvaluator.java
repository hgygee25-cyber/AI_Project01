
package GUI_4;

import GUI_4.Action;
import GUI_4.DecisionResult;
import GUI_4.AvailableActionChecker.AvailableActions;

/**
 * [기능]
 * 분석 결과와 가능한 행동을 종합하여 최종 행동을 결정한다.
 *
 * [입력값]
 * winRate : 예상 승률 (0~100)
 * potOdds : 팟 오즈 (0~100)
 * callCost : 추가 콜 비용
 * availableActions : 현재 가능한 행동 목록
 * raiseAmount : 검증된 추가 레이즈 금액
 * allInRaise : 올인 레이즈 여부
 * incompleteRaise : 불완전 올인 여부
 *
 * [반환값]
 * DecisionResult : 최종 행동 및 관련 정보
 *
 * [주의사항]
 * 1. 콜 비용이 0이면 CHECK한다.
 * 2. 승률이 팟 오즈 이하이면 FOLD한다.
 * 3. 승률이 팟 오즈보다 높고 70% 이상이며
 *    레이즈가 가능하면 RAISE한다.
 * 4. 그 외에는 CALL 또는 올인 CALL을 검토한다.
 * 5. amount는 실제 추가 지불해야 하는 총액이다.
 * 6. raiseAmount는 사전에 계산 및 검증된 금액이어야 한다.
 * 7. 실제 칩 차감은 게임 시스템에서 담당한다.
 *
 * [게임 시스템 연동 시 주의사항]
 * raiseAmount에는 콜 비용이 포함되지 않는다.
 * RAISE의 실제 지불 금액은
 * callCost + raiseAmount이다.
 *
 * 불완전 올인 여부는 게임 엔진의 최소 레이즈 규칙과
 * 재레이즈 권한을 확인한 결과여야 한다.
 */
public class ActionEvaluator {

    private static final double RAISE_THRESHOLD = 70.0;

    /**
     * 최종 행동 결정
     */
    public DecisionResult evaluateAction(
            double winRate,
            double potOdds,
            int callCost,
            AvailableActions availableActions,
            int raiseAmount,
            boolean allInRaise,
            boolean incompleteRaise
    ) {

        // 1. 입력값 검사
        if (Double.isNaN(winRate)
                || Double.isInfinite(winRate)
                || winRate < 0.0
                || winRate > 100.0) {

            return DecisionResult.failure(
                    "승률이 올바르지 않습니다."
            );
        }

        if (Double.isNaN(potOdds)
                || Double.isInfinite(potOdds)
                || potOdds < 0.0
                || potOdds > 100.0) {

            return DecisionResult.failure(
                    "팟 오즈가 올바르지 않습니다."
            );
        }

        if (callCost < 0 || raiseAmount < 0) {
            return DecisionResult.failure(
                    "베팅 금액은 음수일 수 없습니다."
            );
        }

        if (availableActions == null) {
            return DecisionResult.failure(
                    "가능한 행동 정보가 없습니다."
            );
        }

        // 2. 콜 비용이 0일 때: 승률이 70% 이상이면 선제 베팅(RAISE), 아니면 CHECK
        if (callCost == 0) {
            if (winRate >= RAISE_THRESHOLD && availableActions.canRaise() && raiseAmount > 0) {
                return DecisionResult.success(
                        Action.RAISE,
                        raiseAmount,
                        allInRaise,
                        incompleteRaise
                );
            }

            if (!availableActions.canCheck()) {
                return DecisionResult.failure("현재 CHECK를 할 수 없습니다.");
            }

            return DecisionResult.success(Action.CHECK, 0);
        }


        // 3. 승률이 팟 오즈 이하이면 FOLD
        if (winRate <= potOdds) {

            if (!availableActions.canFold()) {
                return DecisionResult.failure(
                        "현재 FOLD를 할 수 없습니다."
                );
            }

            return DecisionResult.success(
                    Action.FOLD,
                    0
            );
        }

        
        // 4. 승률이 70% 이상이고, 레이즈가 가능하며, 유효한 레이즈 금액(> 0)이 산출되었을 때만 RAISE 실행
        // (상대방 남은 칩이 0원이라 추가 레이즈 금액이 0원인 경우, 에러를 내지 않고 자연스럽게 아래 5번 CALL로 넘어감)
        if (winRate >= RAISE_THRESHOLD
                && availableActions.canRaise()
                && raiseAmount > 0) {

            // 불완전 레이즈는 올인에서만 허용
            if (incompleteRaise && !allInRaise) {
                return DecisionResult.failure(
                        "불완전 레이즈는 올인 상황에서만 가능합니다."
                );
            }

            // 실제 총 지불 금액 계산
            long totalPayment = (long) callCost + raiseAmount;

            if (totalPayment > Integer.MAX_VALUE) {
                return DecisionResult.failure(
                        "총 베팅 금액이 처리 가능한 범위를 초과했습니다."
                );
            }

            return DecisionResult.success(
                    Action.RAISE,
                    (int) totalPayment,
                    allInRaise,
                    incompleteRaise
            );
        }


        // 5. 레이즈 조건을 충족하지 못하면 CALL 검토

        if (availableActions.canCall()) {

            return DecisionResult.success(
                    Action.CALL,
                    callCost
            );
        }

        // 6. 일반 CALL이 불가능하면 올인 CALL 검토
        if (availableActions.canAllInCall()) {

            // 올인 CALL 금액은 컴퓨터의 남은 보유 칩이다.
            // 정확한 금액은 ComputerAI에서 확인 후 전달해야 한다.
            return DecisionResult.failure(
                    "올인 CALL 금액 확인이 필요합니다."
            );
        }

        // 7. 가능한 행동이 없는 경우
        return DecisionResult.failure(
                "현재 실행할 수 있는 적절한 행동이 없습니다."
        );
    }
}
