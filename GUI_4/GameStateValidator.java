package GUI_4;

import GUI_1.GameState;

/**
 * [기능]
 * 컴퓨터 AI에 전달된 게임 정보의 유효성을 검사한다.
 *
 * [입력값]
 * gameState       : 현재 게임 상태
 * winRate         : 컴퓨터 예상 승률 (0~100)
 * callCost        : 컴퓨터가 추가로 지불해야 하는 콜 비용
 * computerMoney   : 컴퓨터 보유 칩
 * userMoney       : 사용자 보유 칩
 *
 * [반환값]
 * ValidationResult : 검증 성공 여부와 오류 메시지
 *
 * [주의사항]
 * 실제 베팅 정보는 게임 시스템과 연동하기 전까지
 * 임시 입력값을 사용한다.
 *
 * 실제 칩 차감과 베팅 실행은 담당하지 않는다.
 */
import GUI_1.IndianPokerManager;
import GUI_1.Card;
import java.util.Map;

public class GameStateValidator {
    
    /**
     * 검증 결과 저장 클래스
     */
    public static class ValidationResult {

        private final boolean valid;
        private final String errorMessage;

        private ValidationResult(
                boolean valid,
                String errorMessage
        ) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        // 검증 성공 여부
        public boolean isValid() {
            return valid;
        }

        // 오류 메시지
        public String getErrorMessage() {
            return errorMessage;
        }
    }


    /**
     * 게임 정보 유효성 검사
     */
    public ValidationResult validateGameState(
            GameState gameState,
            double winRate,
            int callCost,
            int computerMoney,
            int userMoney
    ) {

        // 1. 게임 상태 존재 여부
        if (gameState == null) {
            return failure("게임 상태 정보가 없습니다.");
        }

        // 2. 게임 단계 검사
        if (gameState.getStage() == null) {
            return failure("게임 단계 정보가 없습니다.");
        }

        // 3. 게임 종료 여부
        if (gameState.isGameOver()) {
            return failure("이미 종료된 게임입니다.");
        }

        // 4. 쇼다운 단계 검사
        if (gameState.getStage() == GameState.Stage.SHOWDOWN) {
            return failure(
                    "쇼다운 단계에서는 새로운 행동을 결정할 수 없습니다."
            );
        }

        // 5. 팟 금액 검사
        if (gameState.getPot() < 0) {
            return failure("팟 금액은 음수일 수 없습니다.");
        }

        // 6. 승률 검사
        if (Double.isNaN(winRate)
                || Double.isInfinite(winRate)
                || winRate < 0.0
                || winRate > 100.0) {

            return failure(
                    "승률은 0~100 사이의 유효한 값이어야 합니다."
            );
        }

        // 7. 콜 비용 검사
        if (callCost < 0) {
            return failure("콜 비용은 음수일 수 없습니다.");
        }

        // 8. 컴퓨터 보유 칩 검사
        if (computerMoney < 0) {
            return failure("컴퓨터 보유 칩은 음수일 수 없습니다.");
        }

        // 9. 사용자 보유 칩 검사
        if (userMoney < 0) {
            return failure("사용자 보유 칩은 음수일 수 없습니다.");
        }

        // 10. 콜 비용과 팟 금액 합산 범위 검사
        // 향후 팟 오즈 및 레이즈 금액 계산 시 오버플로 방지
        long totalPot = (long) gameState.getPot() + callCost;

        if (totalPot > Integer.MAX_VALUE) {
            return failure(
                    "팟 금액과 콜 비용의 합이 처리 가능한 범위를 초과했습니다."
            );
        }

        // 모든 검사 통과
        return success();
    }


    /**
     * 검증 성공 결과 생성
     */
    private ValidationResult success() {
        return new ValidationResult(true, null);
    }


    /**
     * 검증 실패 결과 생성
     */
    private ValidationResult failure(String message) {

        return new ValidationResult(false, message);
    }

    /**
     * 인디언 포커 전용 게임 상태 검증
     */
    public ValidationResult validateIndianPoker(
            IndianPokerManager manager,
            double winRate,
            int callCost,
            int computerMoney,
            int userMoney
    ) {

        if (manager == null) {
            return failure("인디언 포커 게임 정보가 없습니다.");
        }

        if (manager.isGameOver()) {
            return failure("이미 종료된 게임입니다.");
        }

        if (manager.isBettingCompleted()) {
            return failure("이미 베팅이 완료된 게임입니다.");
        }

        if (manager.getComputerView().get("opponent_card")
                == null) {
            return failure("상대방 공개 카드가 없습니다.");
        }

        if (Double.isNaN(winRate)
                || Double.isInfinite(winRate)
                || winRate < 0.0
                || winRate > 100.0) {
            return failure("승률이 올바르지 않습니다.");
        }

        if (callCost < 0 || computerMoney < 0 || userMoney < 0) {
            return failure("베팅 금액 또는 보유 칩이 올바르지 않습니다.");
        }

        if (manager.getPot() < 0) {
            return failure("팟 금액은 음수일 수 없습니다.");
        }

        long totalPot = (long) manager.getPot() + callCost;

        if (totalPot > Integer.MAX_VALUE) {
            return failure("팟 금액의 범위를 초과했습니다.");
        }

        return success();
    }


}