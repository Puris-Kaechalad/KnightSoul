// // package com.mycompany.knightsoul;

// // import java.awt.BorderLayout;
// // import java.awt.Dimension;
// // import java.awt.Graphics;
// // import java.awt.event.MouseAdapter;
// // import java.awt.event.MouseEvent;
// // import java.util.Random;

// // import javax.swing.JPanel;

// // public class MatchBox extends JPanel {
// //     private int rows = 4, cols = 6;
// //     private int selectRow = -1, selectCol = -1;
// //     private int panelSize = 100; 
// //     private Tile[][] box = new Tile[rows][cols];
// //     private int iconCount;

// //     public MatchBox() {
// //         setPreferredSize(new Dimension(cols * panelSize, rows * panelSize));
// //         setLayout(new BorderLayout()); 
// //         drawMatchBox();

// //         addMouseListener(new MouseAdapter() {
// //             @Override
// //             public void mouseClicked(MouseEvent e) {
// //                 int col = e.getX() / panelSize;
// //                 int row = e.getY() / panelSize;
// //                 handleTileClick(row, col);
// //             }
// //         });
// //     }

// //     private void drawMatchBox() {
// //         Random random = new Random();
// //         for (int row = 0; row < rows; row++) {
// //             for (int col = 0; col < cols; col++) {
// //                 int randomKeyword = random.nextInt(5); // Generates a random icon type
// //                 box[row][col] = new Tile(randomKeyword);

// //                 // Ensure there are no initial matches on the board
// //                 while (isStreak(row, col)) {
// //                     box[row][col] = new Tile(random.nextInt(5));
// //                 }
// //             }
// //         }
// //         repaint();
// //     }

// //     private boolean isStreak(int row, int col) {
// //         int currentIcon = box[row][col].getIconType();

// //         // Horizontal check
// //         if (col >= 2 && box[row][col - 1].getIconType() == currentIcon &&
// //             box[row][col - 2].getIconType() == currentIcon) {
// //             return true;
// //         }

// //         // Vertical check
// //         if (row >= 2 && box[row - 1][col].getIconType() == currentIcon &&
// //             box[row - 2][col].getIconType() == currentIcon) {
// //             return true;
// //         }

// //         return false;
// //     }

// //     private void handleTileClick(int row, int col) {
// //         if (selectRow == -1 && selectCol == -1) {
// //             selectRow = row;
// //             selectCol = col;
// //         } else {
// //             if (checkPosition(row, col)) {
// //                 swapTiles(row, col, selectRow, selectCol);
// //                 checkAndRefill();
// //             }
// //             selectRow = selectCol = -1;
// //         }
// //         repaint();
// //     }

// //     private boolean checkPosition(int row, int col) {
// //         return (Math.abs(row - selectRow) == 1 && col == selectCol) ||
// //                (Math.abs(col - selectCol) == 1 && row == selectRow);
// //     }

// //     private void swapTiles(int row1, int col1, int row2, int col2) {
// //         Tile temp = box[row1][col1];
// //         box[row1][col1] = box[row2][col2];
// //         box[row2][col2] = temp;
// //     }

// //     private void checkForMatches() {
// //         boolean[][] toRemove = new boolean[rows][cols];
// //         // Vertical match check
// //         for (int col = 0; col < cols; col++) {
// //             for (int row = 0; row <= rows - 3; row++) {
// //                 int currentIcon = box[row][col].getIconType();
// //                 if (currentIcon != -1 &&
// //                     currentIcon == box[row + 1][col].getIconType() && 
// //                     currentIcon == box[row + 2][col].getIconType()) {
                    
// //                     toRemove[row][col] = true;
// //                     toRemove[row + 1][col] = true;
// //                     toRemove[row + 2][col] = true;
// //                 }
// //             }
// //         }

// //         // Horizontal match check
// //         for (int row = 0; row < rows; row++) {
// //             for (int col = 0; col <= cols - 3; col++) {
// //                 int currentIcon = box[row][col].getIconType();
// //                 if (currentIcon != -1 &&
// //                     currentIcon == box[row][col + 1].getIconType() && 
// //                     currentIcon == box[row][col + 2].getIconType()) {
                    
// //                     toRemove[row][col] = true;
// //                     toRemove[row][col + 1] = true;
// //                     toRemove[row][col + 2] = true;
// //                 }
// //             }
// //         }

// //         // Remove matched tiles by setting them to empty
// //         for (int row = 0; row < rows; row++) {
// //             for (int col = 0; col < cols; col++) {
// //                 if (toRemove[row][col]) {
// //                     box[row][col] = new Tile(-1); // Set as empty
// //                 }
// //             }
// //         }
// //     }

// //     // Method to refill empty spots with new tiles after removing matches
// //     private void refill() {
// //         Random random = new Random();

// //         for (int col = 0; col < cols; col++) {
// //             int emptyRow = rows - 1;

// //             // Move existing tiles down
// //             for (int row = rows - 1; row >= 0; row--) {
// //                 if (box[row][col].getIconType() != -1) { // Only move if it's a valid tile
// //                     if (emptyRow != row) { // Move down to the lowest empty spot
// //                         box[emptyRow][col] = box[row][col];
// //                         box[row][col] = new Tile(-1); // Mark old spot as empty
// //                     }
// //                     emptyRow--; // Move empty row up
// //                 }
// //             }

// //             // Fill empty spaces at the top with new random tiles
// //             for (int row = emptyRow; row >= 0; row--) {
// //                 box[row][col] = new Tile(random.nextInt(5)); // New tile with random icon type
// //             }
// //         }
// //     }

// //     // Method to repeatedly check for matches and refill until no more matches are found
// //     private void checkAndRefill() {
// //         boolean matchesFound;
// //         do {
// //             checkForMatches();  // Check for matches
// //             matchesFound = hasMatches(); // Check if there are any matches left
// //             if (matchesFound) {
// //                 refill(); // Refill only if there are matches
// //             }
// //         } while (matchesFound); // Repeat until no matches are found
// //         repaint();
// //     }

// //     // Helper method to check if there are any matches left
// //     private boolean hasMatches() {
// //         for (int row = 0; row < rows; row++) {
// //             for (int col = 0; col < cols; col++) {
// //                 if (isStreak(row, col)) {
// //                     return true; // Return true if there is a match
// //                 }
// //             }
// //         }
// //         return false; // No matches found
// //     }

// //     @Override
// //     protected void paintComponent(Graphics g) {
// //         super.paintComponent(g);
// //         for (int row = 0; row < rows; row++) {
// //             for (int col = 0; col < cols; col++) {
// //                 box[row][col].draw(g, col * panelSize, row * panelSize, panelSize);
// //             }
// //         }
// //     }
// // }


// package com.mycompany.knightsoul;

// import java.awt.BorderLayout;
// import java.awt.Dimension;
// import java.awt.Graphics;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.util.Random;

// import javax.swing.JPanel;

// public class MatchBox extends JPanel {
//     private int rows = 4, cols = 6;
//     private int selectRow = -1, selectCol = -1;
//     private int panelSize = 100; 
//     private Tile[][] box = new Tile[rows][cols];
//     private int iconCount;

//     public MatchBox() {
//         setPreferredSize(new Dimension(cols * panelSize, rows * panelSize));
//         setLayout(new BorderLayout()); 
//         drawMatchBox();

//         addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 int col = e.getX() / panelSize;
//                 int row = e.getY() / panelSize;
//                 handleTileClick(row, col);
//             }
//         });
//     }

//     //---------------------------------------------------

//     private void drawMatchBox() {
//         Random random = new Random();
//         for (int row = 0; row < rows; row++) {
//             for (int col = 0; col < cols; col++) {
//                 int randomKeyword = random.nextInt(5);
//                 box[row][col] = new Tile(randomKeyword);

//                 while (isStreak(row, col)) {
//                     box[row][col] = new Tile(random.nextInt(5));
//                 }
//             }
//         }
//         repaint();
//     }

//     //Random and match  
//     private boolean isStreak(int row, int col) {
//         int currentIcon = box[row][col].getIconType();
    
//         // Horizontal check
//         if (col >= 2 && 
//             box[row][col - 1].getIconType() == currentIcon && 
//             box[row][col - 2].getIconType() == currentIcon) {
//             return true;
//         }
    
//         // Vertical check
//         if (row >= 2 && 
//             box[row - 1][col].getIconType() == currentIcon && 
//             box[row - 2][col].getIconType() == currentIcon) {
//             return true;
//         }
    
//         return false;
//     }

//     //---------------------------end draw-------------------------------

//     //------------------------check and swap-----------------------------

//     //check where clicked
//     private void handleTileClick(int row, int col) {
//         if (selectRow == -1 && selectCol == -1) {
//             selectRow = row;
//             selectCol = col;
//         } else {
//             if (checkPosition(row, col)) {
//                 swapTiles(row, col, selectRow, selectCol);
//                 checkAndRefill();
//             }
//             selectRow = selectCol = -1;
//         }
//         repaint();
//     }

//     private boolean checkPosition(int row, int col) {
//         return (Math.abs(row - selectRow) == 1 && col == selectCol) ||
//                (Math.abs(col - selectCol) == 1 && row == selectRow);
//     }

//     private void swapTiles(int row1, int col1, int row2, int col2) {
//         Tile temp = box[row1][col1];
//         box[row1][col1] = box[row2][col2];
//         box[row2][col2] = temp;
//     }

//     //------------------------end check and swap------------------------------------

//     // เพิ่มฟังก์ชัน applyMatchEffect ที่คำนวณการเปลี่ยนแปลง HP และ Shield ของผู้เล่นและบอท
//     private int[] applyMatchEffect(int row, int col, int iconType) {
//         int playerHpChange = 0;
//         int playerShieldChange = 0;
//         int botHpChange = 0;

//         switch (iconType) {
//             case 0: // Sword icon
//                 botHpChange -= 10;
//                 break;
//             case 1: // Shield icon
//                 playerShieldChange += 10;
//                 break;
//             case 2: // Heart icon
//                 playerHpChange += 10;
//                 break;
//             case 3: // Double sword icon
//                 botHpChange -= 20;
//                 break;
//             case 4: // Golden Heart icon
//                 playerHpChange += 20;
//                 break;
//             default:
//                 break;
//         }

//         return new int[]{playerHpChange, playerShieldChange, botHpChange};
//     }

//     private int[] checkForMatches() {
//         boolean[][] toRemove = new boolean[rows][cols];
//         int playerHpChange = 0;
//         int playerShieldChange = 0;
//         int botHpChange = 0;
    
//         // Vertical match check
//         for (int col = 0; col < cols; col++) {
//             for (int row = 0; row <= rows - 3; row++) {
//                 int currentIcon = box[row][col].getIconType();
//                 if (currentIcon != -1 &&
//                     currentIcon == box[row + 1][col].getIconType() && 
//                     currentIcon == box[row + 2][col].getIconType()) {
    
//                     // Mark tiles for removal
//                     toRemove[row][col] = true;
//                     toRemove[row + 1][col] = true;
//                     toRemove[row + 2][col] = true;
    
//                     // Apply match effect for this icon type
//                     int[] changes = applyMatchEffect(row, col, currentIcon);
//                     playerHpChange += changes[0];
//                     playerShieldChange += changes[1];
//                     botHpChange += changes[2];
//                 }
//             }
//         }

//         // Horizontal match check
//         for (int row = 0; row < rows; row++) {
//             for (int col = 0; col <= cols - 3; col++) {
//                 int currentIcon = box[row][col].getIconType();
//                 if (currentIcon != -1 &&
//                     currentIcon == box[row][col + 1].getIconType() && 
//                     currentIcon == box[row][col + 2].getIconType()) {
    
//                     // Mark tiles for removal
//                     toRemove[row][col] = true;
//                     toRemove[row][col + 1] = true;
//                     toRemove[row][col + 2] = true;
    
//                     // Apply match effect for this icon type
//                     int[] changes = applyMatchEffect(row, col, currentIcon);
//                     playerHpChange += changes[0];
//                     playerShieldChange += changes[1];
//                     botHpChange += changes[2];
//                 }
//             }
//         }
    
//         // Remove matched tiles by setting them to empty (-1)
//         for (int row = 0; row < rows; row++) {
//             for (int col = 0; col < cols; col++) {
//                 if (toRemove[row][col]) {
//                     box[row][col] = new Tile(-1); // Set tile as empty
//                 }
//             }
//         }
    
//         return new int[]{playerHpChange, playerShieldChange, botHpChange};
//     }

//     public void checkAndRefill() {
//         boolean matchesFound;
//         int playerHpChangeTotal = 0;
//         int playerShieldChangeTotal = 0;
//         int botHpChangeTotal = 0;

//         do {
//             int[] matchChanges = checkForMatches();
//             playerHpChangeTotal += matchChanges[0];
//             playerShieldChangeTotal += matchChanges[1];
//             botHpChangeTotal += matchChanges[2];
            
//             matchesFound = hasMatches();
//             if (matchesFound) {
//                 refill();
//             }
//         } while (matchesFound);

//         updatePlayerHp(playerHpChangeTotal);
//         updatePlayerShield(playerShieldChangeTotal);
//         updateBotHp(botHpChangeTotal);
//         repaint();
//     }

//     private void updatePlayerHp(int amount) {
//         System.out.println("Player HP change: " + amount);
//     }

//     private void updatePlayerShield(int amount) {
//         System.out.println("Player Shield change: " + amount);
//     }

//     private void updateBotHp(int amount) {
//         System.out.println("Bot HP change: " + amount);
//     }

//     private void refill() {
//         Random random = new Random();
//         for (int col = 0; col < cols; col++) {
//             int emptyRow = rows - 1;

//             for (int row = rows - 1; row >= 0; row--) {
//                 if (box[row][col].getIconType() != -1) {
//                     if (emptyRow != row) {
//                         box[emptyRow][col] = box[row][col];
//                         box[row][col] = new Tile(-1);
//                     }
//                     emptyRow--;
//                 }
//             }

//             for (int row = emptyRow; row >= 0; row--) {
//                 box[row][col] = new Tile(random.nextInt(5));
//             }
//         }
//     }

//     private boolean hasMatches() {
//         for (int row = 0; row < rows; row++) {
//             for (int col = 0; col < cols; col++) {
//                 if (isStreak(row, col)) {
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         for (int row = 0; row < rows; row++) {
//             for (int col = 0; col < cols; col++) {
//                 box[row][col].draw(g, col * panelSize, row * panelSize, panelSize);
//             }
//         }
//     }
// }
