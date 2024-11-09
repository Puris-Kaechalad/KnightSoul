// package com.mycompany.knightsoul;

// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.FlowLayout;
// import java.awt.Font;
// import java.awt.Graphics;
// import java.awt.GridLayout;
// import java.util.Timer;
// import java.util.TimerTask;

// import javax.swing.BoxLayout;
// import javax.swing.ImageIcon;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.SwingConstants;

// class Game extends JPanel {
//     private double playerHp = 100;
//     private double playerShield = 100;
//     private int botHp = 100;
//     private int botShield = 100;
//     private StateCtrl gameState; 
    
//     private JLabel playerHpBar;
//     private JLabel playerShieldBar;   
//     private JLabel botHpBar;
//     private JLabel botShieldBar;
//     private JLabel stateLabel;   

//     private MovingObject attackAnimation;
//     private MovingObject botAttackAnimation;
//     private int playerPosX = 100;
//     private int botPosX    = 1000;

//     private boolean isGameOver = false;

//     private ImageIcon playerCharactor = new ImageIcon(getClass().getResource("/img/player.png"));
//     private ImageIcon botCharactor = new ImageIcon(getClass().getResource("/img/bot.png"));
//     private ImageIcon hpBarIcon = new ImageIcon(getClass().getResource("/img/Icon/Heart.png"));
//     private ImageIcon shieldBarIcon = new ImageIcon(getClass().getResource("/img/Icon/Sheild.png"));
//     private ImageIcon background = new ImageIcon(getClass().getResource("/img/background1.jpg"));
//     private ImageIcon bottomBackground = new ImageIcon(getClass().getResource("/img/Bottombackground.jpg"));

//     public Game() {
//         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//         gameState = new StateCtrl();

//         // Top section: Characters
//         JPanel topPanel = createTopPanel();
//         add(topPanel);

//         // Bottom section: Match-3 grid and icons
//         JPanel bottomPanel = createBottomPanel();
//         add(bottomPanel);
//     }

//     // Creates the top panel
//     private JPanel createTopPanel() {
//         JPanel topPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns

//         // Player Panel
//         JPanel playerPanel = new JPanel();
//         playerPanel.setOpaque(false);

//         //state display here
//         JPanel stagePanel = new JPanel();
//         stagePanel.setOpaque(false);
//         stateLabel = new JLabel("Stage: " + gameState.getStageCount());
//         stateLabel.setFont(new Font("Arial", Font.BOLD, 30));
//         stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
//         stateLabel.setForeground(Color.WHITE);
//         stagePanel.add(stateLabel);

//         // Opponent Panel
//         JPanel opponentPanel = new JPanel();
//         opponentPanel.setOpaque(false); 
       
//         topPanel.setOpaque(false);
//         topPanel.add(playerPanel);
//         topPanel.add(stagePanel);
//         topPanel.add(opponentPanel);

//         return topPanel;
//     }
    
//     // Creates the bottom panel
//     private JPanel createBottomPanel() {
//         JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 460));

//         // Left icons (Player's health and shield)
//         JPanel leftPanel = new JPanel(new GridLayout(2, 2));
//         JLabel playerHeart = new JLabel(hpBarIcon);
//         playerHpBar = new JLabel(String.valueOf((int) playerHp));  // แสดงค่าเริ่มต้นของ HP ผู้เล่น
//         JLabel playerSh = new JLabel(shieldBarIcon);
//         playerShieldBar = new JLabel(String.valueOf((int) playerShield));  // แสดงค่าเริ่มต้นของเกราะผู้เล่น

//         leftPanel.add(playerHeart);
//         leftPanel.add(playerHpBar);     
//         leftPanel.add(playerSh);
//         leftPanel.add(playerShieldBar);

//         // Center match-3 grid
//         JPanel centerPanel = new JPanel();
//         centerPanel.setPreferredSize(new Dimension(450, 300));  
//         MatchBox matchBox = new MatchBox(this);
//         centerPanel.add(matchBox);

//         // Right icons (Opponent's health and shield)
//         JPanel rightPanel = new JPanel(new GridLayout(2, 2));
//         JLabel opponentHeart = new JLabel(hpBarIcon);
//         botHpBar = new JLabel(String.valueOf((int) botHp));  // แสดงค่าเริ่มต้นของ HP บอท
//         JLabel opponentShield = new JLabel(shieldBarIcon);
//         botShieldBar = new JLabel(String.valueOf((int) botShield));  // แสดงค่าเริ่มต้นของเกราะบอท

//         rightPanel.add(opponentHeart);
//         rightPanel.add(botHpBar);
//         rightPanel.add(opponentShield);
//         rightPanel.add(botShieldBar);

//         bottomPanel.setOpaque(false);
//         bottomPanel.add(leftPanel);
//         bottomPanel.add(centerPanel);
//         bottomPanel.add(rightPanel);
//         return bottomPanel;
//     }

//     public void botAttackAfterDelay() {
//         Timer timer = new Timer();
//         timer.schedule(new TimerTask() {
//             @Override
//             public void run() {
//                 // ตรวจสอบว่าตัวละครผู้เล่นได้กลับไปยังจุดเริ่มต้นแล้วหรือไม่
//                 if (attackAnimation == null || !attackAnimation.isRunning()) {
//                     playerAttacked(25); // ให้บอทโจมตีหลังจากดีเลย์
//                 }
//             }
//         }, 2000); // ดีเลย์ 2 วินาทีก่อนให้บอทโจมตี
//     }

//     // Method to update on GUI
//     private void updateStats() {
//         playerHpBar.setText(String.valueOf((int) playerHp));
//         playerShieldBar.setText(String.valueOf((int) playerShield));
//         botHpBar.setText(String.valueOf(botHp));
//         botShieldBar.setText(String.valueOf(botShield));
//         stateLabel.setText("Stage: " + gameState.getStageCount()); // อัปเดตด่านที่แสดงบนหน้าจอ
//     }

//     public void playerAttacked(double amount) {
//         if(playerShield <= 0){
//             playerHp -= amount;
//         }
//         else{
//             playerShield -= 25;
//         }
//         botAttackAnimation = new MovingObject(this, botPosX, playerPosX + 300);
//         updateStats();
//         checkCharacterStatus();
//     }

//     public void playerHealing(double amount){
//         if(playerHp >= 100){
//             return;
//         }
//         else{
//             playerHp += amount;
//         }
//         updateStats();
//     }

//     public void playerShieldUp(double amount){
//         if(playerShield >= 100){
//             return;
//         }
//         else{
//             playerShield += amount;
//         }
//         updateStats();
//     }
    
//     public void botAttacked(double amount) {
//         if(botShield <= 0){
//             botHp -= amount;
//         }
//         else{
//             botShield -= 25;
//         }
//         attackAnimation = new MovingObject(this, playerPosX, botPosX - 300);
//         updateStats();
//         checkCharacterStatus();
//     }

//     private void checkCharacterStatus() {
//         if (playerHp <= 0) {
//             System.out.println("Player is dead.");
//             playerDied();
//         }

//         if (botHp <= 0) {
//             // บอทตาย - ลบบอทตัวเก่าแล้วเริ่มดีเลย์เพื่อสร้างบอทใหม่
//             System.out.println("Bot is dead.");
//             botCharactor = null;  // ลบภาพบอทออกไป
//             repaint();
//             respawnBotAfterDelay();
//         }
//     }
    
//     //----------------------bot died----------------------
//     private void respawnBotAfterDelay() {
//         Timer timer = new Timer();
//         timer.schedule(new TimerTask() {
//             @Override
//             public void run() {
//                 respawnBot();  // สร้างบอทใหม่หลังจากดีเลย์
//             }
//         }, 3000); // ดีเลย์ 3 วินาที
//     }
    
//     private void respawnBot() {
//         if (botAttackAnimation != null) {
//             botAttackAnimation.stopRun();  // หยุดการเคลื่อนไหวหากยังมีการทำงานอยู่
//             botAttackAnimation = null; // รีเซ็ตตัวแปรให้เป็น null เพื่อป้องกันการเคลื่อนไหวที่ค้างอยู่
//         }

//         gameState.increaseStage();

//         botHp = 100;
//         botShield = 100;
//         playerHp = 100;  // รีเซ็ต HP ของผู้เล่น
//         playerShield = 100;  // รีเซ็ตเกราะของผู้เล่น
        
//         // โหลดภาพบอทกลับมาใหม่
//         botCharactor = new ImageIcon(getClass().getResource("/img/bot.png"));
        
//         // อัปเดตสถิติใหม่และรีเฟรชหน้าจอ
//         updateStats();
//         repaint();
//         System.out.println("New bot has respawned.");
//     }
    
//     //-----------------------player died---------------------
//     private void playerDied() {
//         System.out.println("Player is dead. GAME OVER.");
//         isGameOver = true;  // ตั้งค่าสถานะว่าผู้เล่นตายแล้ว
//         playerCharactor = null; // ลบภาพผู้เล่นออกจากหน้าจอ
//         repaint(); // รีเฟรชหน้าจอเพื่อแสดงผลว่าไม่มีตัวละครผู้เล่น

//         // ดีเลย์ก่อนกลับไปที่หน้าหลัก
//         returnToMainMenuAfterDelay();
//     }

//     private void returnToMainMenuAfterDelay() {
//         Timer timer = new Timer();
//         timer.schedule(new TimerTask() {
//             @Override
//             public void run() {
//                 // โค้ดสำหรับกลับไปที่หน้าหลัก
//                 returnToMainMenu();
//             }
//         }, 5000); // ดีเลย์ 5 วินาทีก่อนกลับไปที่หน้าหลัก
//     }

//     // Method to handle returning to main menu
//     private void returnToMainMenu() {
//         // สมมติว่าเป็นการเปลี่ยนหน้าไปที่หน้าหลัก (Main Menu)    
//         // โค้ดสำหรับกลับไปยังหน้าหลักควรอยู่ที่นี่
//         System.out.println("Returning to Main Menu...");
//         // ตัวอย่างการใช้งานสามารถใช้วิธีการเปลี่ยน JPanel หรือหน้าต่างของเกม
//         // เช่นการใช้ CardLayout หรือการรีเซ็ตค่าต่างๆ เพื่อแสดงหน้าหลัก
//     }

//     // public void setShapeColor(Color color){
//     //     this.shapeColor = color; 
//     // }

//     @Override
//     protected void paintComponent(Graphics g){
//         super.paintComponent(g);

//         g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight()/2, null);
//         g.drawImage(bottomBackground.getImage(), 0, getHeight()/2, getWidth(), getHeight()/2, null);

//         if (playerCharactor != null && !isGameOver) {
//             if (attackAnimation != null && attackAnimation.isRunning()) {
//                 g.drawImage(playerCharactor.getImage(), attackAnimation.getX(), 5, getWidth() / 3, getHeight() / 2, null);
//             } else {
//                 g.drawImage(playerCharactor.getImage(), playerPosX, 5, getWidth() / 3, getHeight() / 2, null);
//             }
//         }

//         // Draw the bot character
//         if (botCharactor != null) {
//             if (botAttackAnimation != null && botAttackAnimation.isRunning()) {
//                 g.drawImage(botCharactor.getImage(), botAttackAnimation.getX(), 150, getWidth() / 5, getHeight() / 3, null);
//             } else {
//                 g.drawImage(botCharactor.getImage(), botPosX, 150, getWidth() / 5, getHeight() / 3, null);
//             }
//         }

//         if (isGameOver) {
//             g.setColor(Color.white);
//             g.setFont(g.getFont().deriveFont(124f));  // ตั้งขนาดตัวอักษร
//             g.drawString("GAME OVER", getWidth() / 4, getHeight() / 2);  // แสดงข้อความที่กลางหน้าจอ
//         }
//     }
// }
