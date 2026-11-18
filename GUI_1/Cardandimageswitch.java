package GUI_1;

import javax.swing.ImageIcon;

public class Cardandimageswitch {
    public static int suittonum(String suit) {
        switch(suit) {
            case "Spades": return 0;
            case "Hearts": return 1;
            case "Diamonds": return 2;
            case "Clubs": return 3;
            default: return -1;
        }
    }

    public static int ranktonum(String rank) {
        switch(rank) {
            case "A": return 1;
            case "2" : return 2;
            case "3" : return 3;
            case "4" : return 4;
            case "5" : return 5;
            case "6" : return 6;
            case "7" : return 7;
            case "8" : return 8;
            case "9" : return 9;
            case "10" : return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            default: return -1;
        }
    }

    public static ImageIcon Card(int suit, int rank) {
        System.out.println("card and image switch 클래스에 들어옴");
        System.out.println("card and image switch 클래스 -------- suit: "+suit+", rank: "+ rank);
        int suit_num = (suit);
        int rank_num = (rank);
        System.out.println("card and image switch 클래스 -------- suit_num: "+suit_num+", rank_num: "+ rank_num);

        String path = "image_card/"+suit_num+"."+rank_num+".png";
        System.out.println("card and image switch 클래스 -------- path: "+ path);
        ImageIcon icon = new ImageIcon(path);
        if (icon.getIconWidth() <= 0) {
            System.out.println("이미지를 찾을 수 없습니다: " + path);
        }
        System.out.println("이미지 경로: " + path);
        System.out.println("이미지 로딩 여부: " + (icon.getIconWidth() > 0));

        return icon;
    }
    
    public static ImageIcon BackCard() {
        String path = "images/4.0.png";
        return new ImageIcon(path);
    }
}
