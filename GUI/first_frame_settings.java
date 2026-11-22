
package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class first_frame_settings extends JPanel {
    private final String username;
    public JLabel previewLabel;
    public String selectedImage;
    public JButton[] imageButtons;
    public String[] images = {
        "image/user3.png",
        "image/이모지1.png",
        "image/이모지2.png",
        "image/이모지3.png",
        "image/이모지4.png",
        "image/이모지5.png"
    };

    public final Color GOLD = new Color(210, 170, 100);
    public final Color DARK = new Color(20, 22, 24);
    public final Color PANEL = new Color(32, 34, 36);
    public final Color GRAY = new Color(100, 100, 100);

    public first_frame_settings(String username) {
        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(DARK);

        //
        this.username = username;
        selectedImage = first_frame_settings_1.loadImage(username);
        //
        JLabel title = new JLabel("PROFILE SETTINGS");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setForeground(GOLD);
        title.setBounds(50, 30, 400, 40);
        add(title);

        JLabel subtitle = new JLabel("CUSTOMIZE YOUR PLAYER PROFILE");
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        subtitle.setForeground(GRAY);
        subtitle.setBounds(52, 68, 350, 20);
        add(subtitle);

        JLabel line = new JLabel();
        line.setBounds(25, 105, 1022, 1);
        line.setOpaque(true);
        line.setBackground(new Color(130, 100, 65));
        add(line);

        JPanel selectPanel = new JPanel(null);
        selectPanel.setBounds(50, 135, 500, 330);
        selectPanel.setBackground(PANEL);
        selectPanel.setBorder(new LineBorder(new Color(90, 75, 55), 1));
        add(selectPanel);

        JLabel selectTitle = new JLabel("AVATAR COLLECTION");
        selectTitle.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        selectTitle.setForeground(GOLD);
        selectTitle.setBounds(25, 20, 300, 30);
        selectPanel.add(selectTitle);

        JLabel selectSub = new JLabel("SELECT YOUR FAVORITE IMAGE");
        selectSub.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
        selectSub.setForeground(GRAY);
        selectSub.setBounds(25, 48, 300, 20);
        selectPanel.add(selectSub);

        imageButtons = new JButton[images.length];
        for (int i = 0; i < images.length; i++) {
            final String path = images[i];

            JButton button = new JButton();
            button.setBounds(55 + (i % 3) * 130, 90 + (i / 3) * 105, 75, 75);
            button.setIcon(createIcon(path, 62));
            button.setBackground(new Color(25, 27, 29));
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setBorder(new LineBorder(GRAY, 2));

            button.addActionListener(e -> {
                selectedImage = path;
                updatePreview(path);
                updateSelection();
            });

            imageButtons[i] = button;
            selectPanel.add(button);
        }

        JPanel previewPanel = new JPanel(null);
        previewPanel.setBounds(585, 135, 430, 330);
        previewPanel.setBackground(PANEL);
        previewPanel.setBorder(new LineBorder(new Color(90, 75, 55), 1));
        add(previewPanel);

        JLabel previewTitle = new JLabel("PROFILE PREVIEW");
        previewTitle.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        previewTitle.setForeground(GOLD);
        previewTitle.setBounds(25, 20, 300, 30);
        previewPanel.add(previewTitle);

        JPanel imageFrame = new JPanel(new GridBagLayout());
        imageFrame.setBounds(135, 85, 160, 160);
        imageFrame.setBackground(new Color(25, 27, 29));
        imageFrame.setBorder(new LineBorder(GOLD, 3));
        previewPanel.add(imageFrame);

        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(JLabel.CENTER);
        imageFrame.add(previewLabel);

        updatePreview(selectedImage);

        JLabel selectedText = new JLabel("SELECTED AVATAR");
        selectedText.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        selectedText.setForeground(GOLD);
        selectedText.setHorizontalAlignment(JLabel.CENTER);
        selectedText.setBounds(80, 260, 270, 25);
        previewPanel.add(selectedText);

        JButton uploadButton = makeButton("＋  UPLOAD IMAGE");
        uploadButton.setBounds(50, 485, 230, 45);
        add(uploadButton);

        uploadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("프로필 이미지 선택");
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                selectedImage = file.getAbsolutePath();

                updatePreview(selectedImage);
                updateSelection();
            }
        });

        JButton cancelButton = makeButton("CANCEL");
        cancelButton.setBounds(650, 485, 160, 45);
        add(cancelButton);

        cancelButton.addActionListener(e -> {
            GUI_00.gg5.setVisible(false);
            GUI_00.gg.setVisible(true);
        });

        JButton applyButton = new JButton("APPLY");
        applyButton.setBounds(830, 485, 190, 45);
        applyButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        applyButton.setForeground(DARK);
        applyButton.setBackground(GOLD);
        applyButton.setFocusPainted(false);
        applyButton.setBorder(new LineBorder(GOLD, 1));
        add(applyButton);

        applyButton.addActionListener(e -> {
            String savedPath = first_frame_settings_1.saveImage(username, selectedImage);

            if (savedPath == null) {
                JOptionPane.showMessageDialog(this, "이미지 저장에 실패했습니다.");
                return;
            }

            selectedImage = savedPath;
            first_frame_store2.label44.setIcon(createIcon(selectedImage, 80));

            GUI_00.gg5.setVisible(false);
            GUI_00.gg.setVisible(true);

            GUI_00.contentpane.revalidate();
            GUI_00.contentpane.repaint();
        });


        JButton button5 = makeButton("OTHER OPTION");
        button5.setBounds(800, 60, 230, 40);
        add(button5);

        button5.addActionListener(e -> {
            GUI_00.gg5.setVisible(false);
            GUI_00.gg6.setVisible(true);
            GUI_00.contentpane.setComponentZOrder(GUI_00.gg6, 0);
            GUI_00.contentpane.revalidate();
            GUI_00.contentpane.repaint();
        });

        updateSelection();
    }

    public JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setForeground(GOLD);
        button.setBackground(PANEL);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(GOLD, 1));

        return button;
    }

    public void updateSelection() {

    for (int i = 0; i < images.length; i++) {

        if (selectedImage.equals(images[i])) {
            imageButtons[i].setBorder(
                new LineBorder(GOLD, 3)
            );
        } else {
            imageButtons[i].setBorder(
                new LineBorder(GRAY, 1)
            );
        }
    }
}

    public void updatePreview(String path) {
        previewLabel.setIcon(createIcon(path, 120));
    }

    public ImageIcon createIcon(String path, int size) {
        ImageIcon icon = new ImageIcon(path);
        if (icon.getIconWidth() <= 0) {
            return new ImageIcon();
        }
        Image image = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }
}