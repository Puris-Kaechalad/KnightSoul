package com.mycompany.knightsoul;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class MainFrame extends JFrame {
    private static JPanel currentPanel;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1800, 900);
        frame.setTitle("Knight Soul");
        // frame.setLayout(new GridLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        currentPanel = new HomePage();
        frame.add(currentPanel);
        
        frame.setVisible(true);
    }

}

