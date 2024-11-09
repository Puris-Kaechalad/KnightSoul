package com.mycompany.knightsoul;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Tile{
    private static String iconPath = "/img/Icon/";
    private static String[] icon = {"Sword.png", "Sheild.png", "Heart.png", "DoubleSword.png"};
    private Image iconImage;
    private int iconType;

    public Tile(int keyword){
        this.iconType = (keyword);
        this.iconImage =  setIcon(iconType);
    }

    public Image getIcon() {
        return iconImage;
    }

    public int getIconType(){
        return iconType;
    }
    
    public Image setIcon(int keyword) {
        switch (keyword) {
            case 0:
                return new ImageIcon( getClass().getResource(iconPath + icon[keyword])).getImage();
            case 1:
                return new ImageIcon( getClass().getResource(iconPath + icon[keyword])).getImage();
            case 2:
                return new ImageIcon( getClass().getResource(iconPath + icon[keyword])).getImage();
            case 3:
                return new ImageIcon( getClass().getResource(iconPath + icon[keyword])).getImage();
            default:
                return null;
        }
    }

    public void draw(Graphics g, int x, int y, int size) {
        if (iconImage != null) {
            g.drawImage(iconImage, x, y, size, size, null);
        }
    }
}
