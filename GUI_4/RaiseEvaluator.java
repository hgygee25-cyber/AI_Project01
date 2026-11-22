package GUI_4;

/**
 * [기능]
 * 승률 및 게임 상황을 바탕으로 레이즈 여부를 판단한다.
 *
 * [입력값]
 * winRate : 예상 승률
 * potSize : 현재 팟
 * callCost : 콜 비용
 * canRaise : 레이즈 가능 여부
 *
 * [반환값]
 * boolean : 레이즈 선택 여부
 *
 * [주의사항]
 * 초기 레이즈 기준은 70%로 설정하되,
 * 추후 조정할 수 있도록 상수로 관리한다.
 */
public class RaiseEvaluator {

    private static final double RAISE_THRESHOLD = 0.70;

    public boolean evaluateRaise(
            double winRate,
            int potSize,
            int callCost,
            boolean canRaise
    ) {

        // TODO: 레이즈 적절성 판단

        return false;
    }
}