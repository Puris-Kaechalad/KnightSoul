package com.mycompany.knightsoul;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

class Game extends JPanel {
    private double playerHp = 100;
    private double playerShield = 100;
    private int botHp = 100;
    private int botShield = 100;
    private StateCtrl gameState;
    private MatchBox matchBox;

    private JLabel playerHpBar;
    private JLabel playerShieldBar;   
    private JLabel botHpBar;
    private JLabel botShieldBar;
    private JLabel stateLabel;   

    private MovingObject attackAnimation;
    private MovingObject botAttackAnimation;
    private int playerPosX = 100;
    private int botPosX = 1000;

    private boolean isGameOver = false;
    private boolean isBotRespawning = false;

    private ImageIcon playerCharactor = new ImageIcon(getClass().getResource("/img/player.png"));
    private ImageIcon botCharactor = new ImageIcon(getClass().getResource("/img/bot.png"));
    private ImageIcon hpBarIcon = new ImageIcon(getClass().getResource("/img/Icon/Heart.png"));
    private ImageIcon shieldBarIcon = new ImageIcon(getClass().getResource("/img/Icon/Sheild.png"));
    private ImageIcon background = new ImageIcon(getClass().getResource("/img/background1.jpg"));
    private ImageIcon bottomBackground = new ImageIcon(getClass().getResource("/img/Bottombackground.jpg"));

    public Game() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        matchBox = new MatchBox(this);
        gameState = new StateCtrl();        

        // Top section: Characters
        JPanel topPanel = createTopPanel();
        add(topPanel);

        // Bottom section: Match-3 grid and icons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel);
    }

    // Creates the top panel
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns

        // Player Panel
        JPanel playerPanel = new JPanel();
        playerPanel.setOpaque(false);

        //state display here
        JPanel stagePanel = new JPanel();
        stagePanel.setOpaque(false);
        stateLabel = new JLabel("Stage: " + gameState.getStageCount());
        stateLabel.setFont(new Font("Arial", Font.BOLD, 30));
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stateLabel.setForeground(Color.WHITE);
        stagePanel.add(stateLabel);

        // Opponent Panel
        JPanel opponentPanel = new JPanel();
        opponentPanel.setOpaque(false); 
       
        topPanel.setOpaque(false);
        topPanel.add(playerPanel);
        topPanel.add(stagePanel);
        topPanel.add(opponentPanel);

        return topPanel;
    }
    
    // Creates the bottom panel
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 460));

        // Left icons (Player's health and shield)
        JPanel leftPanel = new JPanel(new GridLayout(2, 2));
        JLabel playerHeart = new JLabel(hpBarIcon);
        playerHpBar = new JLabel(String.valueOf((int) playerHp)); 
        JLabel playerSh = new JLabel(shieldBarIcon);
        playerShieldBar = new JLabel(String.valueOf((int) playerShield));

        leftPanel.add(playerHeart);
        leftPanel.add(playerHpBar);     
        leftPanel.add(playerSh);
        leftPanel.add(playerShieldBar);

        // Center match-3 grid
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(450, 300));  
        centerPanel.add(matchBox);

        // Right icons (Opponent's health and shield)
        JPanel rightPanel = new JPanel(new GridLayout(2, 2));
        JLabel opponentHeart = new JLabel(hpBarIcon);
        botHpBar = new JLabel(String.valueOf((int) botHp));
        JLabel opponentShield = new JLabel(shieldBarIcon);
        botShieldBar = new JLabel(String.valueOf((int) botShield));

        rightPanel.add(opponentHeart);
        rightPanel.add(botHpBar);
        rightPanel.add(opponentShield);
        rightPanel.add(botShieldBar);

        bottomPanel.setOpaque(false);
        bottomPanel.add(leftPanel);
        bottomPanel.add(centerPanel);
        bottomPanel.add(rightPanel);
        return bottomPanel;
    }

    // Method to update on GUI
    private void updateStats() {
        playerHpBar.setText(String.valueOf((int) playerHp));
        playerShieldBar.setText(String.valueOf((int) playerShield));
        botHpBar.setText(String.valueOf(botHp));
        botShieldBar.setText(String.valueOf(botShield));
        stateLabel.setText("Stage: " + gameState.getStageCount());
    }

    public void botAttackAfterDelay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (attackAnimation == null || !attackAnimation.isRunning()) {
                    playerAttacked(25);
                }
            }
        }, 2000);
    }

    public void playerAttacked(double amount) {
        if(botHp <= 0){
            return;
        }
        else if(playerShield <= 0){
            playerHp -= amount;
        } else {
            playerShield -= 25;
        }
        botAttackAnimation = new MovingObject(this, botPosX, playerPosX + 300);
        updateStats();
        checkCharacterStatus();
    }
    
    public void playerHealing(double amount){
        if(playerHp >= 100){
            return;
        }
        else{
            playerHp += amount;
        }
        updateStats();
    }
        
    public void playerShieldUp(double amount){
        if(playerShield >= 100){
            return;
        }
        else{
            playerShield += amount;
        }
        updateStats();
    }

    public void botAttacked(double amount) {
        if(botHp <= 0){
            return;
        }
        else if(botShield <= 0){
            botHp -= amount;
        } else {
            botShield -= 25;
        }
        attackAnimation = new MovingObject(this, playerPosX, botPosX - 300);
        updateStats();
        checkCharacterStatus();
    }

    private void checkCharacterStatus() {
        if (playerHp <= 0) {
            System.out.println("Player is dead.");
            playerDied();
        }

        if (botHp <= 0 && !isBotRespawning) {
            System.out.println("Bot is dead.");
            isBotRespawning = true;
            botCharactor = null;
            repaint();
            respawnBotAfterDelay();
        }
    }
    
    private void respawnBotAfterDelay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                respawnBot();
            }
        }, 3000);
    }
    
    private void respawnBot() {
        if (botAttackAnimation != null) {
            botAttackAnimation.stopRun();
            botAttackAnimation = null;
        }

        gameState.increaseStage();
        isBotRespawning = false; //new bot and set to defult

        botHp = 100;
        botShield = 100;
        playerHp = 100;
        playerShield = 100;

        botCharactor = new ImageIcon(getClass().getResource("/img/bot.png"));
        
        updateStats();
        repaint();
        System.out.println("New bot has respawned.");
    }

    private void playerDied() {
        System.out.println("Player is dead. GAME OVER.");
        isGameOver = true;
        playerCharactor = null;
        repaint();

        returnToMainMenuAfterDelay();
    }

    private void returnToMainMenuAfterDelay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                returnToMainMenu();
            }
        }, 5000);
    }

    private void returnToMainMenu() {
        System.out.println("Returning to Main Menu...");
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.remove(this);

        HomePage home = new HomePage();
        frame.add(home);
        frame.revalidate();
        frame.repaint();

        home.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight()/2, null);
        g.drawImage(bottomBackground.getImage(), 0, getHeight()/2, getWidth(), getHeight()/2, null);

        if (playerCharactor != null && !isGameOver) {
            if (attackAnimation != null && attackAnimation.isRunning()) {
                g.drawImage(playerCharactor.getImage(), attackAnimation.getX(), 5, getWidth() / 3, getHeight() / 2, null);
            } else {
                g.drawImage(playerCharactor.getImage(), playerPosX, 5, getWidth() / 3, getHeight() / 2, null);
            }
        }

        if (botCharactor != null) {
            if (botAttackAnimation != null && botAttackAnimation.isRunning()) {
                g.drawImage(botCharactor.getImage(), botAttackAnimation.getX(), 150, getWidth() / 5, getHeight() / 3, null);
            } else {
                g.drawImage(botCharactor.getImage(), botPosX, 150, getWidth() / 5, getHeight() / 3, null);
            }
        }

        if (isGameOver) {
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(124f));
            g.drawString("GAME OVER", getWidth() / 4, getHeight() / 2);
        }
    }
}
