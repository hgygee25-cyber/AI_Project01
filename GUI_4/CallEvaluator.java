package GUI_4;

import GUI_4.Action;

/**
 * [기능]
 * 예상 승률과 팟 오즈를 비교하여
 * 콜 또는 폴드의 적절성을 판단한다.
 *
 * [입력값]
 * winRate : 예상 승률 (0~100)
 * potOdds : 팟 오즈 (0~100)
 *
 * [반환값]
 * Action : CALL 또는 FOLD
 *
 * [주의사항]
 * 체크가 가능한 상황은 이 클래스에서 처리하지 않는다.
 * 승률이 팟 오즈 이하이면 FOLD한다.
 * 잘못된 입력은 예외로 처리한다.
 * 무승부 지분을 포함한 승률 기준을 사용한다.
 */
public class CallEvaluator {

    /**
     * 승률과 팟 오즈를 비교하여 CALL 또는 FOLD 결정
     */
    public Action evaluateCall(
            double winRate,
            double potOdds
    ) {

        // 잘못된 입력값 검사
        if (Double.isNaN(winRate)
                || Double.isInfinite(winRate)
                || winRate < 0.0
                || winRate > 100.0) {

            throw new IllegalArgumentException(
                    "승률은 0~100 사이의 유효한 값이어야 합니다."
            );
        }

        if (Double.isNaN(potOdds)
                || Double.isInfinite(potOdds)
                || potOdds < 0.0
                || potOdds > 100.0) {

            throw new IllegalArgumentException(
                    "팟 오즈는 0~100 사이의 유효한 값이어야 합니다."
            );
        }

        // 승률이 팟 오즈보다 낮거나 같으면 FOLD
        if (winRate <= potOdds) {
            return Action.FOLD;
        }

        // 승률이 팟 오즈보다 높으면 CALL
        return Action.CALL;
    }
}