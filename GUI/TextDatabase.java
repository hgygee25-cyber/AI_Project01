
package GUI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import login.GUI_01;

public class TextDatabase {
    // 1. 데이터 파일 경로
    public static final String data = "data";
    public static final String chipfile = "data/user_chips.txt";
    public static final String recordss = "data/texas_records.txt";
    public static String com_money = "data/com_money.txt";

    // 2. 사용자 정보 가져오기
    public static String getLoginId() {
        return GUI_01.loginId;
    }

    public static String getUserName() {
        return GUI_01.result;
    }

    // 3. 로그인 확인
    public static boolean isLoggedIn() {
        return GUI_01.loginId != null && !GUI_01.result.trim().isEmpty();
    }

    // 4. 데이터 폴더 및 파일 준비
    public static void prepareFile(String filePath) {
        try {
            File folder = new File(data);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 6. 사용자 칩 불러오기
    public static int getUserChips() {

        if (!isLoggedIn()) {
            return 0;
        }

        prepareFile(chipfile);
        String userName = getUserName();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(chipfile), StandardCharsets.UTF_8)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t", -1);

                if (data.length != 2) {
                    continue;
                }

                if (data[0].equals(userName)) {
                    return Integer.parseInt(data[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 처음 이용하는 사용자는 50,000칩으로 시작
        saveUserChips(50000);
        return 50000;
    }

    // 7. 사용자 칩 저장
    public static void saveUserChips(int chips) {
        if (!isLoggedIn()) {
            System.out.println("로그인 정보가 없어 칩을 저장할 수 없습니다.");
            return;
        }

        if (chips < 0) {
            chips = 0;
        }

        prepareFile(chipfile);
        String userName = getUserName();
        ArrayList<String> lines = new ArrayList<>();
        boolean updated = false;

        try {
            // 기존 데이터 읽기
            try (BufferedReader br = Files.newBufferedReader(Paths.get(chipfile), StandardCharsets.UTF_8)) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\\t", -1);

                    if (data.length == 2 && data[0].equals(userName)) {
                        lines.add(userName + "\t" + chips);
                        updated = true;
                    } else {
                        lines.add(line);
                    }
                }
            }

            // 처음 저장하는 사용자
            if (!updated) {
                lines.add(userName + "\t" + chips);
            }

            Files.write(Paths.get(chipfile), lines, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 8. 게임 결과 저장
    public static void saveGameRecord(String result, String handName, double winProbability, int chipsBefore, int chipsAfter, int pot) {
        if (!isLoggedIn()) {
            System.out.println("로그인 정보가 없어 게임 기록을 저장할 수 없습니다.");
            return;
        }

        prepareFile(recordss);
        String userName = getUserName();
        long time = System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        String line = userName + "\t" + date + "\t" + result + "\t" + handName + "\t" + winProbability + "\t" + chipsBefore + "\t" + chipsAfter + "\t" + pot;

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(recordss), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 9. 현재 로그인 사용자의 게임 기록 불러오기
    public static ArrayList<GameRecord> getGameRecords() {
        ArrayList<GameRecord> records = new ArrayList<>();

        if (!isLoggedIn()) {
            return records;
        }

        prepareFile(recordss);
        String userName = getUserName();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(recordss), StandardCharsets.UTF_8)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t", -1);

                if (data.length != 8) {
                    continue;
                }

                if (!data[0].equals(userName)) {
                    continue;
                }

                try {
                    GameRecord record = new GameRecord(data[0], Long.parseLong(data[1]), data[2], data[3], Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                    records.add(record);
                } catch (Exception e) {
                    System.out.println("잘못된 게임 기록을 건너뜁니다: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    // 10. 게임 기록 데이터 클래스
    public static class GameRecord {
        public String userName;
        public long time;
        public String result;
        public String handName;
        public double winProbability;
        public int chipsBefore;
        public int chipsAfter;
        public int pot;

        public GameRecord(String userName, long time, String result, String handName, double winProbability, int chipsBefore, int chipsAfter, int pot) {
            this.userName = userName;
            this.time = time;
            this.result = result;
            this.handName = handName;
            this.winProbability = winProbability;
            this.chipsBefore = chipsBefore;
            this.chipsAfter = chipsAfter;
            this.pot = pot;
        }

        public String getUserName() {
            return userName;
        }

        public long getTime() {
            return time;
        }

        public String getResult() {
            return result;
        }

        public String getHandName() {
            return handName;
        }

        public double getWinProbability() {
            return winProbability;
        }

        public int getChipsBefore() {
            return chipsBefore;
        }

        public int getChipsAfter() {
            return chipsAfter;
        }

        public int getPot() {
            return pot;
        }
    }

    // COM 칩 불러오기
    public static int getComMoney() {
        prepareFile(com_money);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(com_money), StandardCharsets.UTF_8)) {
            String line = br.readLine();
            if (line != null && !line.trim().isEmpty()) {
                return Math.max(0, Integer.parseInt(line.trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 50000;
    }
    // COM 칩 저장하기
    public static void saveComMoney(int chips) {
        prepareFile(com_money);
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(com_money), StandardCharsets.UTF_8)) {
            bw.write(String.valueOf(Math.max(0, chips)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // COM 칩 초기화하기
    public static void clearComMoney() {
        prepareFile(com_money);
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(com_money), StandardCharsets.UTF_8)) {
            bw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
