
package GUI;

import javax.swing.*;
import java.awt.event.*;
import GUI_4.DecisionResult;

public class Practice_Betting {

    static int combetting = 0;
    static int userbetting = 0;

    static int usermoney = 50000 - Practice_money_User.update();
    static int commoney = 49000;

    static int comtotal = 0;
    static int usertotal = 0;

    static boolean gameEnd = false;

    // 화면에 표시된 베팅 금액을 기준으로 잔액 동기화
    public static void syncMoney() {
        usertotal = Practice_money.updateTotalUser();

        Practice_money.updateTotalcom();
        comtotal = Practice_money.sum_com;

        usermoney = 50000 - usertotal;
        commoney = 50000 - comtotal;

        Practice_money_User.update_usermoney();
    }

    // 현재 단계의 컴퓨터 베팅 금액 가져오기
    private static int getComputerStageBet(int stage) {
        switch (stage) {
            case 1:
                return Practice_money.parseMoney(
                    Practice_money.comflopmoney.getText()
                );
            case 2:
                return Practice_money.parseMoney(
                    Practice_money.comturnmoney.getText()
                );
            case 3:
                return Practice_money.parseMoney(
                    Practice_money.comrivermoney.getText()
                );
            default:
                return 0;
        }
    }

    // 현재 단계의 사용자 베팅 금액 표시
    private static void setUserStageBet(int stage, int amount) {
        switch (stage) {
            case 1:
                Practice_money.flopmoney.setText(String.valueOf(amount));
                break;
            case 2:
                Practice_money.turnmoney.setText(String.valueOf(amount));
                break;
            case 3:
                Practice_money.rivermoney.setText(String.valueOf(amount));
                break;
        }
    }

    // 컴퓨터 베팅 금액 표시
    private static void setComputerStageBet(int stage, int amount) {
        switch (stage) {
            case 1:
                Practice_money.comflopmoney.setText(String.valueOf(amount));
                break;
            case 2:
                Practice_money.comturnmoney.setText(String.valueOf(amount));
                break;
            case 3:
                Practice_money.comrivermoney.setText(String.valueOf(amount));
                break;
        }
    }

    // AI가 결정한 행동을 실제 컴퓨터 베팅에 반영
    public static boolean applyComputerDecision(int stage) {

        DecisionResult decision = PracticeAI.getLastDecision();

        if (decision == null || !decision.isSuccess()) {
            System.out.println("AI 판단 결과가 없습니다.");
            return false;
        }

        if (stage < 1 || stage > 3) {
            return false;
        }


        int amount = 0;

        if (decision.isAllIn()) {
            amount = commoney;
        } else {
            switch (decision.getAction()) {
                case CHECK:
                    amount = 0;
                    break;

                case CALL:
                case RAISE:
                    amount = decision.getAmount();
                    break;

                case FOLD:
                    gameEnd = true;
                    return true;

                default:
                    return false;
            }
        }

        if (amount < 0 || amount > commoney) {
            System.out.println("AI 베팅 금액 오류: " + amount);
            return false;
        }

        int currentBet = getComputerStageBet(stage);
        combetting = currentBet + amount;
        commoney -= amount;
        comtotal += amount;

        setComputerStageBet(stage, combetting);

        setComputerStageBet(stage, amount);

        Practice_money.updateTotalcom();
        Practice_money_User.update_usermoney();

        System.out.println("AI 행동: " + decision.getAction());
        System.out.println("AI 베팅: " + amount);
        System.out.println("COM 잔액: " + commoney);

        return true;
    }

    // 사용자 베팅 처리
    public static String betting(String who, int stage) {

        if (who == null || stage < 1 || stage > 3) {
            return null;
        }

        if (gameEnd) {
            return "GAME END";
        }

        syncMoney();

        // 이미 AI가 반영한 현재 단계 베팅액
        combetting = getComputerStageBet(stage);
        userbetting = 0;

        switch (who) {

            // CALL
            case "call":

                if (combetting > usermoney) {
                    JOptionPane.showMessageDialog(
                        null,
                        "보유 칩이 부족합니다. 올인을 선택해주세요."
                    );
                    return "CANCEL";
                }

                userbetting = combetting;
                break;

            // CHECK
            case "check":

                if (combetting > 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "컴퓨터가 베팅했습니다. 콜하거나 레이즈 또는 폴드해야 합니다."
                    );
                    return "CANCEL";
                }

                userbetting = 0;
                break;

            // RAISE
            case "raise":

                int inputMoney;

                while (true) {
                    String input = JOptionPane.showInputDialog(
                        null,
                        "레이즈할 금액을 입력하세요.\n"
                        + "(컴퓨터 베팅액보다 크게 입력)\n"
                        + "컴퓨터 베팅액: " + combetting
                        + "\n현재 보유액: " + usermoney,
                        "RAISE 금액 입력",
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (input == null) {
                        return "CANCEL";
                    }

                    try {
                        inputMoney = Integer.parseInt(input.trim());

                        if (inputMoney <= combetting) {
                            JOptionPane.showMessageDialog(
                                null,
                                "컴퓨터 베팅액보다 큰 금액을 입력해야 합니다."
                            );
                        } else if (inputMoney > usermoney) {
                            JOptionPane.showMessageDialog(
                                null,
                                "보유 칩보다 많이 베팅할 수 없습니다."
                            );
                        } else {
                            break;
                        }

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "숫자를 입력해주세요."
                        );
                    }
                }

                userbetting = inputMoney;
                break;

            // FOLD
            case "fold":

                gameEnd = true;
                userbetting = 0;
                break;

            // ALL-IN
            case "allin":

                userbetting = usermoney;
                gameEnd = true;
                break;

            default:
                return "존재하지 않는 버튼입니다.";
        }

        // 사용자 칩 차감
        usermoney -= userbetting;
        usertotal += userbetting;

        // 단계별 사용자 베팅 금액 표시
        setUserStageBet(stage, userbetting);

        // 전체 금액 갱신
        Practice_money.updateTotalUser();
        Practice_money.updateTotalcom();

        usertotal = Practice_money.updateTotalUser();
        comtotal = Practice_money.sum_com;

        Practice_money_User.update_usermoney();

        System.out.println("현재 단계: " + stage);
        System.out.println("AI 베팅: " + combetting);
        System.out.println("사용자 베팅: " + userbetting);
        System.out.println("사용자 잔액: " + usermoney);
        System.out.println("컴퓨터 잔액: " + commoney);

        // 폴드 또는 올인 시 버튼 비활성화
        if (who.equals("fold") || who.equals("allin")) {
            disableButtons();
        }

        // 리버 단계에서는 컴퓨터 카드를 공개하고 결과창 표시
        if (stage == 3 || who.equals("fold") || who.equals("allin")) {
            revealComputerCards();
        }

        if (who.equals("fold")) {
            return "USER FOLD";
        }

        if (who.equals("allin")) {
            return "ALL-IN";
        }

        return "COM : " + combetting + " / USER : " + userbetting;
    }

    // 컴퓨터 카드 공개 및 결과창 표시
    private static void revealComputerCards() {

        if (Practice.com_choose == null
                || Practice.com_choose.size() < 2) {
            GUI_00.g1_2_7.setVisible(true);
            return;
        }

        String[] first = String.valueOf(
            Practice.com_choose.get(0)
        ).split(",");

        String[] second = String.valueOf(
            Practice.com_choose.get(1)
        ).split(",");

        if (first.length < 2 || second.length < 2) {
            GUI_00.g1_2_7.setVisible(true);
            return;
        }

        Timer flipTimer = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Practice_card_back.flipToBack(
                    Practice.card_com1,
                    first[0].trim(),
                    first[1].trim()
                );

                Practice_card_back.flipToBack(
                    Practice.card_com2,
                    second[0].trim(),
                    second[1].trim()
                );
            }
        });

        flipTimer.setRepeats(false);
        flipTimer.start();

        Timer resultTimer = new Timer(1800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_00.g1_2_7.setVisible(true);
            }
        });

        resultTimer.setRepeats(false);
        resultTimer.start();
    }

    // 버튼 비활성화
    private static void disableButtons() {

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
    }
}
