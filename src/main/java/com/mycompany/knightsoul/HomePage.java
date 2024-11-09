package com.mycompany.knightsoul;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HomePage extends JPanel {
    private Image backgroundImage;
    private Image logoImage;
    private Image characterImage;

    public HomePage() {
        backgroundImage = new ImageIcon(getClass().getResource("/img/background3.jpg")).getImage();
        logoImage = new ImageIcon(getClass().getResource("/img/logoGame.png")).getImage();
        characterImage = new ImageIcon(getClass().getResource("/img/player.png")).getImage();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // start button
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(100, 0, 0, 0);  
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(startButton, gbc);
        startButton.addActionListener((actionEvent) -> {
            SwipFrame();
        });
    }
    
    private void SwipFrame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.remove(this);

        Game game = new Game();
        frame.add(game);
        frame.revalidate();
        frame.repaint();

        game.requestFocusInWindow();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // background
        int width = getWidth();
        int height = getHeight();
        g.drawImage(backgroundImage, 0, 0, width, height, this);
        
        // logo
        int logoWidth = (int) (width * 0.4);
        int logoHeight = logoImage.getHeight(this) * logoWidth / logoImage.getWidth(this);
        g.drawImage(logoImage, (width - logoWidth) / 2, 100, logoWidth, logoHeight, this);
        
        //character
        int chaWidth = (int) (width * 0.2);
        int chaHeight = characterImage.getHeight(this) * chaWidth / characterImage.getWidth(this);
        g.drawImage(characterImage, (width - chaWidth) / 6, 300, chaWidth, chaHeight, this);
    }

}