package GUI_4;

/**
 * 콜에 필요한 비용을 기준으로 팟 오즈를 계산한다.
 *
 * potSize : 콜하기 전 현재 팟 금액
 * callCost : 컴퓨터가 추가로 지불해야 하는 콜 비용
 *
 * 반환값 : 팟 오즈 (0~100 백분율)
 *
 * 잘못된 입력은 Double.NaN으로 반환한다.
 */
public class PotOddsCalculator {

    public double calculatePotOdds(int potSize, int callCost) {

        // 음수 입력 검사
        if (potSize < 0 || callCost < 0) {
            return Double.NaN;
        }

        // 콜 비용이 0이면 계산하지 않는다.
        if (callCost == 0) {
            return 0.0;
        }

        // 팟 오즈 계산
        return (double) callCost
                / ((double) potSize + callCost) * 100.0;
    }
}