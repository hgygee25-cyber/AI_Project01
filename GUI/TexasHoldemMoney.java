package GUI;

public class TexasHoldemMoney {
    public int userMoney;
    public static int comMoney;
    // 현재 베팅 라운드에서 각자 낸 금액
    public int userBet;
    public int comBet;
    public int pot;
    public int chipsBefore;
    public boolean gameEnded;

    public TexasHoldemMoney() {
        userMoney = TextDatabase.getUserChips();
        comMoney = TextDatabase.getComMoney();
        startNewHand();
    }

    public void startNewHand() {
        userBet = 0;
        comBet = 0;
        pot = 0;
        chipsBefore = userMoney;
        gameEnded = false;
    }

    public boolean betUser(int amount) {
        if (gameEnded || amount <= 0 || amount > userMoney) return false;
        userMoney -= amount;
        userBet += amount;
        pot += amount;
        return true;
    }

    public boolean betCom(int amount) {
        if (gameEnded || amount <= 0 || amount > comMoney) return false;
        comMoney -= amount;
        comBet += amount;
        pot += amount;
        return true;
    }

    public boolean callUser() {
        int difference = comBet - userBet;
        if (difference <= 0) return true;
        if (difference > userMoney) return false;
        return betUser(difference);
    }

    public boolean callCom() {
        int difference = userBet - comBet;
        if (difference <= 0) return true;
        if (difference > comMoney) return false;
        return betCom(difference);
    }

    public boolean allInUser() {
        return userMoney > 0 && betUser(userMoney);
    }

    public boolean allInCom() {
        return comMoney > 0 && betCom(comMoney);
    }

    // 다음 베팅 단계로 넘어갈 때 현재 라운드의 콜 기준만 초기화한다.
    // 이미 낸 칩은 pot에 남아 있으므로 총 팟은 유지된다.
    public void resetStreetBets() {
        userBet = 0;
        comBet = 0;
    }

    // 콜 금액이 서로 맞았는지 확인
    public boolean isBetMatched() {
        return userBet == comBet;
    }

    public void settle(String result, String handName, double winProbability) {
        if (gameEnded || result == null) return;
        int finalPot = pot;
        if ("PLAYER_WIN".equals(result)) {
            userMoney += pot;
        } else if ("COMPUTER_WIN".equals(result)) {
            comMoney += pot;
        } else if ("DRAW".equals(result)) {
            int userShare = pot / 2;
            userMoney += userShare;
            comMoney += pot - userShare;
        } else {
            System.out.println("알 수 없는 승패 결과: " + result);
            return;
        }
        gameEnded = true;
        TextDatabase.saveUserChips(userMoney);
        TextDatabase.saveGameRecord(result, handName, winProbability,
                chipsBefore, userMoney, finalPot);
    }

    public int getUserMoney() { return userMoney; }
    public int getComMoney() { return comMoney; }
    public int getUserBet() { return userBet; }
    public int getComBet() { return comBet; }
    public int getPot() { return pot; }
    public int getChipsBefore() { return chipsBefore; }
    public boolean isGameEnded() { return gameEnded; }
}
