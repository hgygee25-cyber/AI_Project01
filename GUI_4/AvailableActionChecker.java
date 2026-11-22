package GUI_4;

/**
 * [기능]
 * 현재 게임 상황에서 컴퓨터가 선택할 수 있는
 * 기본 행동을 확인한다.
 *
 * [입력값]
 * callCost      : 컴퓨터가 추가로 지불해야 하는 콜 비용
 * computerMoney : 컴퓨터의 현재 보유 칩
 *
 * [반환값]
 * AvailableActions : 가능한 행동과 올인 가능 여부
 *
 * [주의사항]
 * 1. CHECK, FOLD, CALL 가능 여부를 판단한다.
 * 2. 올인 CALL 가능 여부를 판단한다.
 * 3. RAISE와 올인 RAISE는 다른 클래스에서 검증한다.
 * 4. 실제 칩 차감과 베팅 실행은 게임 시스템에서 담당한다.
 */
public class AvailableActionChecker {

    /**
     * 행동 가능 여부 저장 클래스
     */
    public static class AvailableActions {

        private final boolean canFold;
        private final boolean canCheck;
        private final boolean canCall;
        private final boolean canRaise;

        private final boolean canAllInCall;
        private final boolean canAllInRaise;

        private AvailableActions(
                boolean canFold,
                boolean canCheck,
                boolean canCall,
                boolean canRaise,
                boolean canAllInCall,
                boolean canAllInRaise
        ) {

            this.canFold = canFold;
            this.canCheck = canCheck;
            this.canCall = canCall;
            this.canRaise = canRaise;
            this.canAllInCall = canAllInCall;
            this.canAllInRaise = canAllInRaise;
        }

        public boolean canFold() {
            return canFold;
        }

        public boolean canCheck() {
            return canCheck;
        }

        public boolean canCall() {
            return canCall;
        }

        public boolean canRaise() {
            return canRaise;
        }

        public boolean canAllInCall() {
            return canAllInCall;
        }

        public boolean canAllInRaise() {
            return canAllInRaise;
        }
    }


    /**
     * 현재 가능한 행동 확인
     */
    public AvailableActions checkAvailableActions(
            int callCost,
            int computerMoney
    ) {

        // 1. 입력값 검사
        if (callCost < 0 || computerMoney < 0) {
            throw new IllegalArgumentException(
                    "콜 비용과 보유 칩은 음수일 수 없습니다."
            );
        }

        // 2. CHECK: 추가로 지불할 금액이 없는 경우
        boolean canCheck = callCost == 0;

        // 3. CALL: 콜 비용 전액을 지불할 수 있는 경우
        boolean canCall = callCost > 0
                && callCost <= computerMoney;

        // 4. 올인 CALL: 보유 칩이 콜 비용보다 적은 경우
        boolean canAllInCall = callCost > computerMoney
                && computerMoney > 0;

        // 5. FOLD: 게임이 진행 중이라는 전제에서 가능
        boolean canFold = callCost > 0;

        /*
         * 6. RAISE 관련 판단
         *
         * 최소 레이즈 금액과 상대방 대응 가능 금액은
         * 다른 베팅 관련 클래스에서 검증한다.
         *
         * 따라서 이 클래스에서는 임의로
         * RAISE 가능 여부를 확정하지 않는다.
         */
        // 2차 수정 사항: 기존 false 고정에서 -> 콜 비용을 내고도 칩이 남고, 상대도 남은 칩이 있을 때 레이즈 허용으로 변경
        boolean canRaise = (computerMoney > callCost); 
        boolean canAllInRaise = (computerMoney > callCost);


        // 7. 가능한 행동 정보 반환
        return new AvailableActions(
                canFold,
                canCheck,
                canCall,
                canRaise,
                canAllInCall,
                canAllInRaise
        );
    }
}