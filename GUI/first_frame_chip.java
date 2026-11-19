
package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import GUI_3.PaymentApi;

public class first_frame_chip extends JPanel {
    public JLabel label00;
    public JButton chargeButton;
    public JButton historyButton;
    public JButton cancelButton;
    public JLabel label06;
    public first_frame_chip() {
        setBackground(Color.black);
        setBounds(0, 0, 1072,598);
        setLayout(null);
        
        label00 = new JLabel("가상 칩 충전");
        label00.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label00.setForeground(Color.white);
        label00.setBounds(10, 5, 180, 25);
        add(label00);

        // 충전 버튼
        chargeButton = new JButton("10,000 가상 칩 충전");
        chargeButton.setBounds(10, 35, 200, 35);
        add(chargeButton);
        chargeButton.addActionListener(e -> chargeChip());

        // 결제 내역 버튼
        historyButton = new JButton("결제 내역 조회");
        historyButton.setBounds(10, 75, 200, 35);
        add(historyButton);

        historyButton.addActionListener(e -> showHistory());

        // 결제 취소 버튼
        cancelButton = new JButton("가상 결제 취소");
        cancelButton.setBounds(10, 115, 200, 35);
        add(cancelButton);

        cancelButton.addActionListener(e -> cancelPayment());

        label06 = new JLabel("BACK");
        label06.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label06.setForeground(new Color(235, 200, 140));
        label06.setBounds(20, 550, 200,50);
        label06.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(label06);
        
        label06.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                GUI_00.gg.setVisible(true);
                GUI_00.gg1.setVisible(false);
                GUI_00.g.setVisible(false);
            }
        });
    }

    public void chargeChip() {
        String username = JOptionPane.showInputDialog(this, "사용자 이름을 입력하세요.");

        if (username == null || username.trim().isEmpty()) {
            return;
        }

        try {
            String result = PaymentApi.createPayment(username.trim(), 10000);
            long paymentId = extractPaymentId(result);
            JPanel paymentPanel = new JPanel(new GridLayout(0, 1, 5, 8));

            JLabel title = new JLabel("가상 칩 결제");
            title.setFont(new Font("맑은 고딕", Font.BOLD, 20));

            paymentPanel.add(title);
            paymentPanel.add(new JLabel("사용자 : " + username.trim()));
            paymentPanel.add(new JLabel("상품명 : 텍사스 홀덤 가상 칩"));
            paymentPanel.add(new JLabel("충전 수량 : 10,000 칩"));
            paymentPanel.add(new JLabel("결제 금액 : 테스트용"));
            paymentPanel.add(new JLabel("주문 번호 : " + paymentId));

            Object[] options = {"결제 진행", "취소"};

            int choice = JOptionPane.showOptionDialog(this, paymentPanel, "결제하기", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                String approveResult =PaymentApi.approvePayment(paymentId);
                JOptionPane.showMessageDialog(this, "가상 결제가 완료되었습니다.\n\n"+ PaymentApi.formatPayment(approveResult));
            } else if (choice == 1) {
                String cancelResult = PaymentApi.cancelPayment(paymentId);
                JOptionPane.showMessageDialog(this, "결제가 취소되었습니다.\n\n" + PaymentApi.formatPayment(cancelResult));
            } else {
            return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "결제 처리 실패: " + ex.getMessage());
        }
    }
    public long extractPaymentId(String json) {
        Pattern pattern = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        throw new IllegalStateException("결제 ID를 찾을 수 없습니다.");
    }
    public void showHistory() {
        String username = JOptionPane.showInputDialog(this, "조회할 사용자 이름을 입력하세요.");

        if (username == null || username.trim().isEmpty()) {
            return;
        }

        try {
            String result = PaymentApi.getPayments(username.trim());
            JOptionPane.showMessageDialog(this, "결제 내역\n\n" + PaymentApi.formatPayments(result));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog( this, "조회 실패: " + ex.getMessage());
        }
    }

    public void cancelPayment() {
        String input = JOptionPane.showInputDialog(this, "취소할 결제 ID를 입력하세요.");

        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            long paymentId = Long.parseLong(input.trim());
            String result = PaymentApi.cancelPayment(paymentId);
            JOptionPane.showMessageDialog(this, "가상 결제 취소 결과\n\n" + PaymentApi.formatPayment(result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "결제 ID는 숫자로 입력해야 합니다.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "취소 실패: " + ex.getMessage());
        }
    }
}