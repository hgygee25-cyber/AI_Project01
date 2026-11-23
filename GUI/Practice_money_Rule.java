
package GUI;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practice_money_Rule {

    static int stage;

    public Practice_money_Rule(String who) {

        if (who == null) return;

        stage = Practice.count;

        if (stage < 1 || stage > 3) return;

        // 사용자 베팅 처리
        String result = Practice_Betting.betting(who, stage);

        // 취소 또는 오류
        if (result == null
                || "CANCEL".equals(result)
                || "GAME END".equals(result)
                || result.startsWith("존재하지 않는")) {
            return;
        }

        // 사용자 폴드
        if ("USER FOLD".equals(result)) {
            endGame();
            return;
        }

        // 사용자 올인
        if ("ALL-IN".equals(result)) {
            completeAllIn(stage);
            return;
        }

        // 다음 단계로 이동
        if (stage == 1) {

            Practice.count = 2;

            Timer timer = new Timer(700, e -> {
                Practice_Basic_up_card.community_card();
                Practice.percent();

                // 다음 단계 AI 판단
                runAI(2);
            });

            timer.setRepeats(false);
            timer.start();

        } else if (stage == 2) {

            Practice.count = 3;

            Timer timer = new Timer(700, e -> {
                Practice_Basic_upup_card.community_card();
                Practice.percent();

                // 다음 단계 AI 판단
                runAI(3);
            });

            timer.setRepeats(false);
            timer.start();

        } else if (stage == 3) {

            endGame();
        }
    }

    // AI 판단 및 베팅 적용
    private void runAI(int nextStage) {

        DecisionResultHolder holder = new DecisionResultHolder();

        holder.success = PracticeAI.decide(nextStage).isSuccess();

        if (!holder.success) {
            System.out.println("AI 판단 실패");
            return;
        }

        boolean applied =
            Practice_Betting.applyComputerDecision(nextStage);

        if (!applied) {
            System.out.println("AI 베팅 적용 실패");
            return;
        }

        // AI 행동에 따라 사용자 버튼 활성화
        enableUserButtons();
    }

    // 올인 시 남은 커뮤니티 카드 공개
    private void completeAllIn(int currentStage) {

        if (currentStage == 1) {

            Practice_Basic_up_card.community_card();

            Timer timer = new Timer(700, e -> {
                Practice_Basic_upup_card.community_card();
                endGame();
            });

            timer.setRepeats(false);
            timer.start();

        } else if (currentStage == 2) {

            Practice_Basic_upup_card.community_card();

            Timer timer = new Timer(700, e -> {
                endGame();
            });

            timer.setRepeats(false);
            timer.start();

        } else {

            endGame();
        }
    }

    // 사용자 버튼 활성화
    private void enableUserButtons() {

        Practice.button1.setEnabled(true);
        Practice.button2.setEnabled(true);
        Practice.button3.setEnabled(true);
        Practice.button4.setEnabled(true);
        Practice.button5.setEnabled(true);
    }

    // 게임 종료
    public static void endGame() {

        Practice.button1.setEnabled(false);
        Practice.button2.setEnabled(false);
        Practice.button3.setEnabled(false);
        Practice.button4.setEnabled(false);
        Practice.button5.setEnabled(false);

        Practice.com_button1.setEnabled(false);
        Practice.com_button2.setEnabled(false);
        Practice.com_button3.setEnabled(false);
        Practice.com_button4.setEnabled(false);
        Practice.com_button5.setEnabled(false);

        GUI_00.g1_2_7.setVisible(true);
        GUI_00.g1_2_4.setVisible(false);
    }

    // 내부 결과 보관
    private static class DecisionResultHolder {
        boolean success;
    }
}
