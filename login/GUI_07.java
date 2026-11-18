package login;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI_07 {
	public GUI_07(JTextField textfield1, String email) {
		String filePath = "data/login.txt";
    	int a = 0;
    	String email_cor = textfield1.getText() + "@" +email;
    	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    		
    		int count = GUI_04.array.size()-1;
    		while(count >= 0) {
    			Object A = GUI_04.array.get(count);
    			GUI_10 array2 = GUI_10.parse((String) A);
    			count--;
    			
    			if( array2 != null && array2.email.equals(email_cor)) {
    				JOptionPane.showMessageDialog(null, array2.id);
    				System.out.println("여기임??22222222");
					break;
    			}
    		}
       	} catch (FileNotFoundException e1) {
    	    System.out.println("파일을 찾을 수 없습니다: " + filePath);
    	} catch (IOException e1) {
    	    e1.printStackTrace();
    	}
	}
}
