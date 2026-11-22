package GUI_4;

/**
 * [기능]
 * 컴퓨터 AI의 행동 결정 결과와 오류 정보를 저장한다.
 *
 * [입력값]
 * success         : 행동 결정 성공 여부
 * action          : 결정된 행동
 * amount          : 실제 추가 지불해야 하는 총액
 * errorMessage    : 오류 메시지
 * allIn           : 올인 여부
 * incompleteRaise : 불완전 올인 여부
 *
 * [반환값]
 * 행동 결정 결과 객체
 *
 * [주의사항]
 * 실제 칩 차감과 베팅 실행은 게임 시스템에서 담당한다.
 * amount는 실제로 추가 지불해야 하는 금액이다.
 */
public class DecisionResult {

    // 행동 결정 결과
    private final boolean success;

    // 결정된 행동
    private final Action action;

    // 실제 추가 지불해야 하는 총금액
    private final int amount;

    // 오류 메시지
    private final String errorMessage;

    // 올인 여부
    private final boolean allIn;

    // 불완전 올인 여부
    private final boolean incompleteRaise;


    // 외부에서 직접 생성하지 않고 아래 메서드를 통해 생성한다.
    private DecisionResult(
            boolean success,
            Action action,
            int amount,
            String errorMessage,
            boolean allIn,
            boolean incompleteRaise
    ) {

        this.success = success;
        this.action = action;
        this.amount = amount;
        this.errorMessage = errorMessage;
        this.allIn = allIn;
        this.incompleteRaise = incompleteRaise;
    }


    /**
     * 일반적인 행동 결정 결과 생성
     */
    public static DecisionResult success(
            Action action,
            int amount
    ) {

        return success(action, amount, false, false);
    }


    /**
     * 올인 정보를 포함한 행동 결정 결과 생성
     */
    public static DecisionResult success(
            Action action,
            int amount,
            boolean allIn,
            boolean incompleteRaise
    ) {

        // 행동 검사
        if (action == null) {
            throw new IllegalArgumentException(
                    "행동이 지정되지 않았습니다."
            );
        }

        // 금액 검사
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "베팅 금액은 음수일 수 없습니다."
            );
        }

        // FOLD와 CHECK는 금액이 0이어야 한다.
        if ((action == Action.FOLD || action == Action.CHECK)
                && amount != 0) {

            throw new IllegalArgumentException(
                    "폴드와 체크의 베팅 금액은 0이어야 합니다."
            );
        }

        // 불완전 레이즈는 올인 RAISE에서만 허용한다.
        if (incompleteRaise
                && (!allIn || action != Action.RAISE)) {

            throw new IllegalArgumentException(
                    "불완전 레이즈는 올인 RAISE에서만 가능합니다."
            );
        }

        // 올인 CALL과 RAISE만 허용한다.
        if (allIn
                && action != Action.CALL
                && action != Action.RAISE) {

            throw new IllegalArgumentException(
                    "올인은 CALL 또는 RAISE에서만 가능합니다."
            );
        }

        return new DecisionResult(
                true,
                action,
                amount,
                null,
                allIn,
                incompleteRaise
        );
    }


    /**
     * 오류 발생 결과 생성
     */
    public static DecisionResult failure(String errorMessage) {

        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = "원인을 확인할 수 없는 오류가 발생했습니다.";
        }

        return new DecisionResult(
                false,
                null,
                0,
                errorMessage,
                false,
                false
        );
    }


    // ===== Getter =====

    public boolean isSuccess() {
        return success;
    }

    public Action getAction() {
        return action;
    }

    public int getAmount() {
        return amount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isAllIn() {
        return allIn;
    }

    public boolean isIncompleteRaise() {
        return incompleteRaise;
    }
}