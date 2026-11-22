package GUI_4;

/**
 * [기능]
 * 컴퓨터가 선택할 수 있는 행동 종류를 정의한다.
 *
 * [입력값]
 * 없음.
 *
 * [반환값]
 * FOLD, CHECK, CALL, RAISE
 *
 * [주의사항]
 * 실제 행동 실행과 칩 차감은 게임 엔진에서 처리한다.
 * 올인은 별도 행동으로 구분할지 게임 엔진과 협의한다.
 */
public enum Action {
    FOLD,
    CHECK,
    CALL,
    RAISE
}