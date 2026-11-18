
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
    private static final String DATA_FOLDER = "data";
    private static final String CHIPS_FILE = "data/user_chips.txt";
    private static final String RECORDS_FILE = "data/texas_records.txt";

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
    private static void prepareFile(String filePath) {
        try {
            File folder = new File(DATA_FOLDER);

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

        prepareFile(CHIPS_FILE);
        String userName = getUserName();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(CHIPS_FILE), StandardCharsets.UTF_8)) {
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

        prepareFile(CHIPS_FILE);
        String userName = getUserName();
        ArrayList<String> lines = new ArrayList<>();
        boolean updated = false;

        try {
            // 기존 데이터 읽기
            try (BufferedReader br = Files.newBufferedReader(Paths.get(CHIPS_FILE), StandardCharsets.UTF_8)) {
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

            Files.write(Paths.get(CHIPS_FILE), lines, StandardCharsets.UTF_8);
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

        prepareFile(RECORDS_FILE);
        String userName = getUserName();
        long time = System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        String line = userName + "\t" + date + "\t" + result + "\t" + handName + "\t" + winProbability + "\t" + chipsBefore + "\t" + chipsAfter + "\t" + pot;

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(RECORDS_FILE), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
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

        prepareFile(RECORDS_FILE);
        String userName = getUserName();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(RECORDS_FILE), StandardCharsets.UTF_8)) {
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
        private String userName;
        private long time;
        private String result;
        private String handName;
        private double winProbability;
        private int chipsBefore;
        private int chipsAfter;
        private int pot;

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
}
