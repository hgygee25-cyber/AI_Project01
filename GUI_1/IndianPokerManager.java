package GUI_1;

import java.util.HashMap;
import java.util.Map;

public class IndianPokerManager {

    // =====================================
    // ① 멤버 변수 및 게임 상수
    // =====================================

    private static final int INITIAL_CHIPS = 50000;      // 초기 지급 칩
    private static final int ANTE = 100;                 // 기본 참가비 (앤티)
    private static final int ACE_FOLD_PENALTY = 500;     // [특수 룰] A 카드 보유 시 폴드(포기) 패널티

    private Card deck;
    private Player player;
    private Player computer;

    private int pot;                     // 현재 판에 걸린 총 팟(상금) 금액
    private int playerBet;               // [팀원 연동용] 현재 라운드 사용자 추가 베팅액
    private int computerBet;             // [팀원 연동용] 현재 라운드 컴퓨터 추가 베팅액

    private boolean gameOver;            // 현재 판 종료 여부 (true: 종료, false: 진행 중)
    private boolean cardsDealt;          // 카드 분배 완료 여부
    private boolean bettingCompleted;    // [팀원 연동용] 양측 베팅 종료 여부
    private int playerInitialChips = INITIAL_CHIPS;     
    
    // =====================================
    // ② 생성자
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] 초기화된 IndianPokerManager 객체
     * [기능] 새로운 인디언 포커 게임 세션 생성 및 첫 라운드 시작
     */
    // 기본 생성자
    public IndianPokerManager() {
        this(INITIAL_CHIPS);
    }

    // 사용자 실제 칩을 전달받는 생성자
    public IndianPokerManager(int playerChips) {
        if (playerChips < ANTE) {
            throw new IllegalArgumentException(
                "사용자 보유 칩이 참가비보다 적습니다."
            );
        }

        this.playerInitialChips = playerChips;
        startGame();
    }

    // =====================================
    // ③ 게임 초기화 및 다음 판 시작
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] void
     * [기능] 게임을 맨 처음부터 새로 시작할 때 호출 (양측 칩 50,000으로 초기화 후 첫 판 진행)
     */
    public void startGame() {
        // 사용자는 기존에 저장된 실제 칩으로 시작
        player = new Player("Player", playerInitialChips);

        // 컴퓨터는 기본 지급 칩으로 시작
        computer = new Player("Computer", INITIAL_CHIPS);

        nextRound();
    }

    /*
     * [입력값] 없음
     * [출력값] boolean - true: 다음 판 정상 시작 / false: 한쪽의 칩이 ANTE(100) 미만이라 게임 진행 불가
     *
     * [기능]
     * 1. 기존 양측 보유 칩을 유지한 채 카드와 베팅 상태만 초기화 (Player.reset() 활용)
     * 2. 덱 새로 생성 및 셔플
     * 3. 참가비(ANTE) 지불 가능 여부 검증 후 차감 및 팟 적립
     * 4. 양측에 카드 1장씩 분배
     */
    public boolean nextRound() {
        if (player.getChips() < ANTE || computer.getChips() < ANTE) {
            gameOver = true;
            return false;
        }

        deck = new Card();
        deck.shuffle();

        player.reset();
        computer.reset();

        pot = 0;
        playerBet = 0;
        computerBet = 0;

        gameOver = false;
        cardsDealt = false;
        bettingCompleted = false;

        player.bet(ANTE);
        computer.bet(ANTE);
        pot += ANTE * 2;

        dealCards();
        return true;
    }

    // =====================================
    // ④ 카드 분배
    // =====================================

    /*
     * [입력값] 없음
     * [출력값] void
     * [예외] IllegalStateException - 이미 카드가 분배된 상태에서 재호출 시 발생
     * [기능] 사용자와 컴퓨터에게 덱에서 카드 한 장씩 분배
     */
    public void dealCards() {
        if (cardsDealt) {
            throw new IllegalStateException("카드가 이미 분배되었습니다.");
        }

        player.receiveCard(deck.drawCard());
        computer.receiveCard(deck.drawCard());

        cardsDealt = true;
    }

    // =====================================
    // ⑤ 사용자 화면(GUI) 전달용 정보
    // =====================================

    /*
     * [입력값] 없음
     *
     * [출력값] Map<String, Object>
     * - "opponent_card" (Card.CardData) : 상대방(컴퓨터)의 카드
     * - "own_card" (Card.CardData)      : 자신의 카드 (진행 중엔 null, 종료 후 공개)
     * - "player_chips" (Integer)        : 사용자 현재 보유 칩
     * - "computer_chips" (Integer)      : 컴퓨터 현재 보유 칩
     * - "player_bet" (Integer)          : [팀원 연동용] 사용자 현재 라운드 베팅액
     * - "computer_bet" (Integer)        : [팀원 연동용] 컴퓨터 현재 라운드 베팅액
     * - "pot" (Integer)                 : 현재 총 팟 금액
     * - "betting_completed" (Boolean)   : 베팅 완료 여부
     * - "game_over" (Boolean)           : 게임 종료 여부
     *
     * [기능] 사용자 화면(GUI) 및 팀원 베팅 UI 갱신에 필요한 상태 데이터 반환
     */
    public Map<String, Object> getPlayerView() {
        Map<String, Object> view = new HashMap<>();

        view.put("opponent_card", computer.getCards().get(0));
        view.put("own_card", gameOver ? player.getCards().get(0) : null);
        view.put("player_chips", player.getChips());
        view.put("computer_chips", computer.getChips());
        view.put("player_bet", playerBet);
        view.put("computer_bet", computerBet);
        view.put("pot", pot);
        view.put("betting_completed", bettingCompleted);
        view.put("game_over", gameOver);

        return view;
    }

    // =====================================
    // ⑥ 컴퓨터 AI 판단용 정보
    // =====================================

    /*
     * [입력값] 없음
     *
     * [출력값] Map<String, Object>
     * - "opponent_card" (Card.CardData) : 상대방(사용자)의 카드 (AI 베팅 판단 근거)
     * - "own_card" (Card.CardData)      : 컴퓨터 자신의 카드 (종료 전까지 null로 은닉)
     * - "player_chips" (Integer)        : 사용자 현재 보유 칩
     * - "computer_chips" (Integer)      : 컴퓨터 현재 보유 칩
     * - "player_bet" (Integer)          : 사용자 현재 라운드 베팅액
     * - "computer_bet" (Integer)        : 컴퓨터 현재 라운드 베팅액
     * - "pot" (Integer)                 : 현재 총 팟 금액
     *
     * [기능] 컴퓨터 AI가 베팅(CALL/RAISE/FOLD)을 결정할 때 참조할 정보 반환
     */
    public Map<String, Object> getComputerView() {
        Map<String, Object> view = new HashMap<>();

        view.put("opponent_card", player.getCards().get(0));
        view.put("own_card", gameOver ? computer.getCards().get(0) : null);
        view.put("player_chips", player.getChips());
        view.put("computer_chips", computer.getChips());
        view.put("player_bet", playerBet);
        view.put("computer_bet", computerBet);
        view.put("pot", pot);

        return view;
    }



    // =====================================
    // ⑦ [팀원 연동 필수] 베팅 시스템 연결 인터페이스
    // =====================================

    /*
     * [팀원 연동 필수 - 1] 사용자 베팅 금액 반영
     *
     * [입력값]
     * amount (int) - 사용자가 추가로 베팅할 금액 (CALL 차액 또는 RAISE 추가액)
     *
     * [출력값]
     * boolean - true: 베팅 정상 반영 / false: 보유 칩 부족 또는 잘못된 금액으로 실패
     *
     * [기능]
     * 팀원 베팅 컨트롤러에서 사용자 베팅이 확정되면 호출하여
     * 사용자 칩 차감(Player.bet), playerBet 누적, 전체 pot 증가를 일괄 처리
     */

    public boolean applyPlayerBet(int amount) {
        if (gameOver || bettingCompleted) {
            throw new IllegalStateException("베팅할 수 없는 게임 상태입니다.");
        }

        if (amount <= 0 || player.getChips() < amount) {
            return false;
        }

        if ((long) playerBet + amount > Integer.MAX_VALUE
                || (long) pot + amount > Integer.MAX_VALUE) {
            return false;
        }

        player.bet(amount);
        playerBet += amount;
        pot += amount;

        return true;
    }


    /*
     * [팀원 연동 필수 - 2] 컴퓨터 베팅 금액 반영
     *
     * [입력값]
     * amount (int) - 컴퓨터가 추가로 베팅할 금액
     *
     * [출력값]
     * boolean - true: 베팅 정상 반영 / false: 컴퓨터 보유 칩 부족 등으로 실패
     *
     * [기능]
     * 팀원 베팅 컨트롤러에서 컴퓨터 베팅이 확정되면 호출하여
     * 컴퓨터 칩 차감(Player.bet), computerBet 누적, 전체 pot 증가를 일괄 처리
     */
    public boolean applyComputerBet(int amount) {
        if (gameOver || bettingCompleted) {
            throw new IllegalStateException("베팅할 수 없는 게임 상태입니다.");
        }

        if (amount <= 0 || computer.getChips() < amount) {
            return false;
        }

        if ((long) computerBet + amount > Integer.MAX_VALUE
                || (long) pot + amount > Integer.MAX_VALUE) {
            return false;
        }

        computer.bet(amount);
        computerBet += amount;
        pot += amount;

        return true;
    }


    /*
     * [팀원 연동 필수 - 3] 라운드 베팅 종료 확정
     *
     * [입력값] 없음
     *
     * [출력값]
     * boolean - true: 베팅 종료 처리 완료 / false: 양측 베팅 금액 불일치로 종료 불가
     *
     * [기능]
     * 양측 베팅 금액이 일치(playerBet == computerBet)하거나,
     * 한쪽이 올인(Player.isAllIn() == true)하여 더 이상 베팅할 수 없을 때 베팅을 종료함.
     * 호출 성공 후 showdown() 실행 가능 상태가 됨.
     */

    public boolean completeBetting() {
        if (gameOver) {
            return false;
        }

        boolean isMatched = (playerBet == computerBet);
        boolean isAnyoneAllIn =
                (player.isAllIn() || computer.isAllIn());

        if (!isMatched && !isAnyoneAllIn) {
            return false;
        }

        // 올인으로 베팅 금액이 서로 다르면 초과 금액 반환
        if (playerBet > computerBet) {
            int refund = playerBet - computerBet;

            player.addChips(refund);
            pot -= refund;
            playerBet = computerBet;

        } else if (computerBet > playerBet) {
            int refund = computerBet - playerBet;

            computer.addChips(refund);
            pot -= refund;
            computerBet = playerBet;
        }

        bettingCompleted = true;
        return true;
    }


    /*
     * [팀원 연동 필수 - 4] 폴드(포기) 선언 시 즉시 승패 정산
     *
     * [입력값]
     * foldedPlayer (Player) - 포기를 선언한 플레이어 객체 (getPlayer() 또는 getComputer())
     *
     * [출력값]
     * Map<String, Object> - showdown()과 동일한 규격의 최종 게임 결과 데이터
     * - "winner" (String)              : "PLAYER_WIN" 또는 "COMPUTER_WIN"
     * - "end_reason" (String)          : "FOLD" (포기로 인한 종료)
     * - "ace_penalty_applied" (Boolean): A 카드 폴드 패널티 적용 여부
     * - "player_card" (Card.CardData)  : 공개된 사용자 카드
     * - "computer_card" (Card.CardData): 공개된 컴퓨터 카드
     * - "pot" (Integer)                : 패널티 포함 최종 지급된 총 팟 금액
     * - "player_chips" (Integer)       : 정산 후 사용자 최종 칩
     * - "computer_chips" (Integer)     : 정산 후 컴퓨터 최종 칩
     *
     * [기능]
     * 1. 이름(getName) 기반으로 포기한 플레이어를 안전하게 식별 및 fold() 상태 변경
     * 2. [인디언 포커 특수 룰] 포기한 사람의 카드가 'A'일 경우 추가 패널티 차감 후 pot에 합산
     * 3. 승자에게 최종 pot 전액 지급 및 게임 종료(gameOver = true) 처리
     */
    public Map<String, Object> finishByFold(Player foldedPlayer) {
        if (gameOver) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
        if (foldedPlayer == null ||
           (!foldedPlayer.getName().equals(player.getName()) && !foldedPlayer.getName().equals(computer.getName()))) {
            throw new IllegalArgumentException("유효하지 않은 플레이어 객체입니다.");
        }

        boolean isPlayerFolded = foldedPlayer.getName().equals(player.getName());
        Player actualFoldedPlayer = isPlayerFolded ? player : computer;
        actualFoldedPlayer.fold();

        Card.CardData playerCard = player.getCards().get(0);
        Card.CardData computerCard = computer.getCards().get(0);
        Card.CardData foldedCard = isPlayerFolded ? playerCard : computerCard;

        boolean acePenaltyApplied = false;
        if ("A".equals(foldedCard.getRank())) {
            int actualPenalty = Math.min(actualFoldedPlayer.getChips(), ACE_FOLD_PENALTY);
            if (actualPenalty > 0) {
                actualFoldedPlayer.bet(actualPenalty);
                pot += actualPenalty;
                acePenaltyApplied = true;
            }
        }

        if (isPlayerFolded) {
            awardChipsToComputer(pot);
        } else {
            awardChipsToPlayer(pot);
        }

        gameOver = true;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("winner", isPlayerFolded ? "COMPUTER_WIN" : "PLAYER_WIN");
        resultMap.put("end_reason", "FOLD");
        resultMap.put("ace_penalty_applied", acePenaltyApplied);
        resultMap.put("player_card", playerCard);
        resultMap.put("computer_card", computerCard);
        resultMap.put("pot", pot);
        resultMap.put("player_chips", player.getChips());
        resultMap.put("computer_chips", computer.getChips());

        return resultMap;
    }

    // =====================================
    // ⑧ 쇼다운 및 최종 승패 판정
    // =====================================

    /*
     * [입력값] 없음
     *
     * [출력값]
     * Map<String, Object> - GUI 결과 화면 출력용 데이터
     * - "winner" (String)              : "PLAYER_WIN" / "COMPUTER_WIN" / "DRAW"
     * - "end_reason" (String)          : "SHOWDOWN" (정상 승부로 인한 종료)
     * - "ace_penalty_applied" (Boolean): false
     * - "player_card" (Card.CardData)  : 공개된 사용자 카드
     * - "computer_card" (Card.CardData): 공개된 컴퓨터 카드
     * - "pot" (Integer)                : 정산된 총 팟 금액
     * - "player_chips" (Integer)       : 정산 후 사용자 최종 칩
     * - "computer_chips" (Integer)     : 정산 후 컴퓨터 최종 칩
     *
     * [예외]
     * IllegalStateException - 게임 종료, 카드 미분배, 또는 베팅 미완료 시 발생
     *
     * [기능]
     * 1. 베팅 완료 여부(bettingCompleted) 검증
     * 2. IndianPokerJudge를 통한 양측 카드 승패 판정
     * 3. 승자에게 팟 지급 (무승부 시 팟 절반씩 분배)
     * 4. 게임 종료(gameOver = true) 처리 및 결과 반환
     */
    public Map<String, Object> showdown() {
        if (gameOver) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
        if (!cardsDealt) {
            throw new IllegalStateException("카드가 분배되지 않았습니다.");
        }
        if (!bettingCompleted) {
            throw new IllegalStateException("아직 베팅이 완료되지 않았습니다. completeBetting()을 먼저 호출하세요.");
        }

        Card.CardData playerCard = player.getCards().get(0);
        Card.CardData computerCard = computer.getCards().get(0);

        Player winner = IndianPokerJudge.determineWinner(
            playerCard,
            computerCard,
            player,
            computer
        );

        String result;

        if (winner == player) {
            result = "PLAYER_WIN";
            awardChipsToPlayer(pot);
        } else if (winner == computer) {
            result = "COMPUTER_WIN";
            awardChipsToComputer(pot);
        } else {
            result = "DRAW";
            int halfPot = pot / 2;
            awardChipsToPlayer(halfPot);
            awardChipsToComputer(pot - halfPot);
        }

        gameOver = true;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("winner", result);
        resultMap.put("end_reason", "SHOWDOWN");
        resultMap.put("ace_penalty_applied", false);
        resultMap.put("player_card", playerCard);
        resultMap.put("computer_card", computerCard);
        resultMap.put("pot", pot);
        resultMap.put("player_chips", player.getChips());
        resultMap.put("computer_chips", computer.getChips());

        return resultMap;
    }

    // =====================================
    // ⑨ 내부 칩 지급 도우미 (Player.java 무수정 연동용)
    // =====================================

    /*
     * [입력값] amount (int) - 사용자에게 지급할 칩 금액
     * [출력값] void
     * [기능] 팀원의 Player.java에 addChips() 메서드가 없으므로, 기존 보유 카드와
     *        상태를 유지한 채 합산된 칩으로 Player 객체를 안전하게 갱신함
     */
    private void awardChipsToPlayer(int amount) {
        if (amount <= 0) return;

        player.addChips(amount);
    }

    private void awardChipsToComputer(int amount) {
        if (amount <= 0) return;

        computer.addChips(amount);
    }

    // =====================================
    // ⑩ 내부 게임 상태 조회 (Getter)
    // =====================================

    /* [입력값] 없음 | [출력값] boolean - 현재 판 종료 여부 반환 */
    public boolean isGameOver() {
        return gameOver;
    }

    /* [입력값] 없음 | [출력값] boolean - 베팅 완료 여부 반환 */
    public boolean isBettingCompleted() {
        return bettingCompleted;
    }

    /* [입력값] 없음 | [출력값] int - 현재 총 팟 금액 반환 */
    public int getPot() {
        return pot;
    }

    /* [입력값] 없음 | [출력값] int - [팀원 연동용] 사용자 현재 라운드 추가 베팅액 반환 */
    public int getPlayerBet() {
        return playerBet;
    }

    /* [입력값] 없음 | [출력값] int - [팀원 연동용] 컴퓨터 현재 라운드 추가 베팅액 반환 */
    public int getComputerBet() {
        return computerBet;
    }

    /* [입력값] 없음 | [출력값] Player - 사용자 객체 반환 */
    public Player getPlayer() {
        return player;
    }

    /* [입력값] 없음 | [출력값] Player - 컴퓨터 객체 반환 */
    public Player getComputer() {
        return computer;
    }
}
