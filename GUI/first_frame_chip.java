package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class first_frame_chip extends JPanel{
    public first_frame_chip() {
        setBounds(10, 10, 280,80);
        setLayout(null);
        setOpaque(false);

        JLabel label00 = new JLabel("");
        label00.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label00.setForeground(Color.white);
        label00.setBounds(100, 15, 180, 15);
        add(label00);
        
        
    }    
}
