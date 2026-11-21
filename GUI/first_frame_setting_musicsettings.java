package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class first_frame_setting_musicsettings extends JPanel {
    private String username;
    private int selectedMusic;
    private int volume;
    private boolean musicEnabled;

    private JRadioButton music1;
    private JRadioButton music2;
    private JRadioButton music3;
    private JCheckBox musicOn;
    private JSlider volumeSlider;
    private JLabel volumeLabel;

    private Color GOLD = new Color(210, 170, 100);
    private Color DARK = new Color(20, 22, 24);
    private Color PANEL = new Color(32, 34, 36);
    private Color GRAY = new Color(150, 150, 150);

    public first_frame_setting_musicsettings(String username) {

        this.username = username;

        setBounds(0, 0, 1072, 598);
        setLayout(null);
        setBackground(DARK);

        String[] setting = first_frame_setting_musicdata.load(username);

        selectedMusic = 1;
        volume = 70;
        musicEnabled = true;

        try {
            selectedMusic = Integer.parseInt(setting[0]);
            volume = Integer.parseInt(setting[1]);
            musicEnabled = Boolean.parseBoolean(setting[2]);
        } catch (Exception e) {
            selectedMusic = 1;
            volume = 70;
            musicEnabled = true;
        }

        if (selectedMusic < 1 || selectedMusic > 3) {
            selectedMusic = 1;
        }

        if (volume < 0 || volume > 100) {
            volume = 70;
        }

        // 제목
        JLabel title = new JLabel("MUSIC SETTINGS");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setForeground(GOLD);
        title.setBounds(50, 30, 400, 40);
        add(title);

        JLabel subtitle = new JLabel("CUSTOMIZE YOUR BACKGROUND MUSIC");
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        subtitle.setForeground(GRAY);
        subtitle.setBounds(52, 68, 400, 20);
        add(subtitle);

        JLabel line = new JLabel();
        line.setBounds(25, 105, 1022, 1);
        line.setOpaque(true);
        line.setBackground(new Color(130, 100, 65));
        add(line);

        // 음악 선택 패널
        JPanel musicPanel = new JPanel(null);
        musicPanel.setBounds(50, 135, 600, 330);
        musicPanel.setBackground(PANEL);
        musicPanel.setBorder(new LineBorder(new Color(90, 75, 55), 1));
        add(musicPanel);

        JLabel musicTitle = new JLabel("BACKGROUND MUSIC");
        musicTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        musicTitle.setForeground(GOLD);
        musicTitle.setBounds(25, 20, 300, 30);
        musicPanel.add(musicTitle);

        ButtonGroup group = new ButtonGroup();

        music1 = makeRadio("MUSIC 01", 30, 80);
        music2 = makeRadio("MUSIC 02", 30, 145);
        music3 = makeRadio("MUSIC 03", 30, 210);

        group.add(music1);
        group.add(music2);
        group.add(music3);

        musicPanel.add(music1);
        musicPanel.add(music2);
        musicPanel.add(music3);

        if (selectedMusic == 1) {
            music1.setSelected(true);
        } else if (selectedMusic == 2) {
            music2.setSelected(true);
        } else {
            music3.setSelected(true);
        }

        music1.addActionListener(e -> changeMusic(1));
        music2.addActionListener(e -> changeMusic(2));
        music3.addActionListener(e -> changeMusic(3));

        // 음량 조절 패널
        JPanel volumePanel = new JPanel(null);
        volumePanel.setBounds(680, 135, 335, 330);
        volumePanel.setBackground(PANEL);
        volumePanel.setBorder(new LineBorder(new Color(90, 75, 55), 1));
        add(volumePanel);

        JLabel volumeTitle = new JLabel("VOLUME");
        volumeTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        volumeTitle.setForeground(GOLD);
        volumeTitle.setBounds(25, 20, 200, 30);
        volumePanel.add(volumeTitle);

        volumeLabel = new JLabel(volume + "%");
        volumeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        volumeLabel.setForeground(GOLD);
        volumeLabel.setBounds(25, 90, 100, 35);
        volumePanel.add(volumeLabel);

        volumeSlider = new JSlider(0, 100, volume);
        volumeSlider.setBounds(20, 145, 290, 50);
        volumeSlider.setBackground(PANEL);
        volumeSlider.setForeground(GOLD);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumePanel.add(volumeSlider);

        volumeSlider.addChangeListener(e -> {
            volume = volumeSlider.getValue();
            volumeLabel.setText(volume + "%");
            first_frame_setting_music.setVolume(volume);

            if (!volumeSlider.getValueIsAdjusting()) {
                saveSetting();
            }
        });

        // 음악 ON/OFF
        musicOn = new JCheckBox("BACKGROUND MUSIC ON");
        musicOn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        musicOn.setForeground(GOLD);
        musicOn.setBackground(PANEL);
        musicOn.setFocusPainted(false);
        musicOn.setBounds(20, 225, 280, 35);
        musicOn.setSelected(musicEnabled);
        volumePanel.add(musicOn);

        musicOn.addActionListener(e -> {
            musicEnabled = musicOn.isSelected();

            if (musicEnabled) {
                playSelectedMusic();
            } else {
                first_frame_setting_music.stop();
            }

            saveSetting();
        });

        // 음악 재생 안 함
        JButton noMusicButton = makeButton("NO MUSIC");
        noMusicButton.setBounds(20, 270, 140, 40);
        volumePanel.add(noMusicButton);

        noMusicButton.addActionListener(e -> {
            musicEnabled = false;
            musicOn.setSelected(false);
            first_frame_setting_music.stop();
            saveSetting();
        });

        // 뒤로 가기
        JButton backButton = makeButton("BACK");
        backButton.setBounds(830, 485, 190, 45);
        add(backButton);

        backButton.addActionListener(e -> {
            GUI_00.gg6.setVisible(false);
            GUI_00.gg5.setVisible(true);
            GUI_00.contentpane.setComponentZOrder(GUI_00.gg5, 0);
            GUI_00.contentpane.revalidate();
            GUI_00.contentpane.repaint();
        });

        // 설정 저장
        JButton saveButton = makeButton("SAVE SETTINGS");
        saveButton.setBounds(610, 485, 200, 45);
        add(saveButton);

        saveButton.addActionListener(e -> {
            saveSetting();
            JOptionPane.showMessageDialog(this, "음악 설정이 저장되었습니다.");
        });
    }

    // 음악 선택 버튼
    public JRadioButton makeRadio(String text, int x, int y) {
        JRadioButton button = new JRadioButton(text);
        button.setBounds(x, y, 500, 40);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(PANEL);
        button.setFocusPainted(false);
        return button;
    }

    // 일반 버튼
    public JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        button.setForeground(GOLD);
        button.setBackground(PANEL);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(GOLD, 1));
        return button;
    }

    // 음악 변경
    public void changeMusic(int number) {
        selectedMusic = number;
        musicEnabled = true;
        musicOn.setSelected(true);
        playSelectedMusic();
        saveSetting();
    }

    // 선택한 음악 재생
    public void playSelectedMusic() {
        first_frame_setting_music.setVolume(volume);
        first_frame_setting_music.play("music/music" + selectedMusic + ".wav");
    }

    // 설정 저장
    public void saveSetting() {
        first_frame_setting_musicdata.save(username, selectedMusic, volume, musicEnabled);
    }
}
